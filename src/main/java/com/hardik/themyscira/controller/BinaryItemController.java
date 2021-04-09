package com.hardik.themyscira.controller;

import java.io.IOException;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.hardik.themyscira.entity.BinaryItem;
import com.hardik.themyscira.request.BinaryItemCreationRequest;
import com.hardik.themyscira.service.BinaryItemService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class BinaryItemController {

	private final BinaryItemService binaryItemService;

	@PostMapping("/themyscira/v1")
	public BinaryItem bindaryItemCreationHandler(
			@RequestPart(name = "file", required = false) MultipartFile multipartFile,
			@RequestPart(name = "data", required = true) final String binaryItemCreationRequest)
			throws JsonMappingException, JsonProcessingException {
		final var parsedbinaryItemCreationRequest = new ObjectMapper()
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
				.registerModule(new ParameterNamesModule()).registerModule(new Jdk8Module())
				.registerModule(new JavaTimeModule())
				.readValue(binaryItemCreationRequest, BinaryItemCreationRequest.class);
		return binaryItemService.create(multipartFile, parsedbinaryItemCreationRequest);
	}

	@DeleteMapping("/themyscira/v1/{itemId}")
	public void bindaryItemDeletionHandler(@PathVariable(name = "itemId", required = true) final UUID itemId) {
		binaryItemService.delete(itemId);
	}

	@GetMapping("/themyscira/v1/{itemId}")
	public String bindaruItemRetreivalHandler(@PathVariable(name = "itemId", required = true) final UUID itemId)
			throws IOException {
		return binaryItemService.retreive(itemId);
	}

}
