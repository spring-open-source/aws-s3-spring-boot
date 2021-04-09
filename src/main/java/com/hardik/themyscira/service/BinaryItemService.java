package com.hardik.themyscira.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.util.Base64;
import com.hardik.themyscira.entity.BinaryItem;
import com.hardik.themyscira.repository.BinaryItemRepository;
import com.hardik.themyscira.request.BinaryItemCreationRequest;
import com.hardik.themyscira.storage.StorageService;
import com.hardik.themyscira.storage.configuration.AmazonS3Configuration;

import lombok.AllArgsConstructor;
import net.bytebuddy.utility.RandomString;

@Service
@AllArgsConstructor
@EnableConfigurationProperties(AmazonS3Configuration.class)
public class BinaryItemService {

	private final BinaryItemRepository binaryItemRepository;

	private final StorageService storageService;

	private final AmazonS3Configuration amazonS3Configuration;

	public BinaryItem create(MultipartFile multipartFile, BinaryItemCreationRequest parsedbinaryItemCreationRequest) {
		if (multipartFile == null || multipartFile.isEmpty())
			throw new RuntimeException();

		final var itemKey = "ITEMS/" + RandomString.make(15).toUpperCase();

		storageService.save(itemKey, multipartFile);

		final var binaryItem = new BinaryItem();
		binaryItem.setBucketName(amazonS3Configuration.getStorage().getBucketName());
		binaryItem.setDeletable(parsedbinaryItemCreationRequest.getDeletable());
		binaryItem.setItemName(parsedbinaryItemCreationRequest.getItemName());
		binaryItem.setItemKey(itemKey);

		return binaryItemRepository.save(binaryItem);
	}

	public void delete(UUID itemId) {
		final var binaryItem = binaryItemRepository.findById(itemId).orElseThrow(() -> new RuntimeException());
		binaryItemRepository.delete(binaryItem);
	}

	public String retreive(UUID itemId) throws IOException {
		final var binaryItem = binaryItemRepository.findById(itemId).orElseThrow(() -> new RuntimeException());
		final var s3BinaryItem = storageService.getFile(binaryItem.getItemKey());
		return Base64.encodeAsString(s3BinaryItem.getObjectContent().readAllBytes());
	}

	public void deleteExpiredBinaryItems() {
		final var binaryItems = binaryItemRepository.findAll().parallelStream()
				.filter(binaryItem -> binaryItem.isDeletable() == true
						&& binaryItem.getCreatedAt().plusDays(31).isBefore(LocalDateTime.now()))
				.collect(Collectors.toList());
		binaryItemRepository.deleteAll(binaryItems);
	}

}
