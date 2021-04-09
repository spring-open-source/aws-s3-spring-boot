package com.hardik.themyscira.bootstrap;

import java.time.LocalDateTime;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.hardik.themyscira.storage.configuration.AmazonS3Configuration;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@EnableConfigurationProperties(AmazonS3Configuration.class)
@Slf4j
public class AmazonS3BucketBootstrap implements ApplicationListener<ApplicationContextEvent> {

	private final AmazonS3 amazonS3;

	private final AmazonS3Configuration amazonS3Configuration;

	@Override
	public void onApplicationEvent(ApplicationContextEvent event) {
		final var s3Configuration = amazonS3Configuration.getStorage();
		if (!amazonS3.listBuckets().parallelStream()
				.anyMatch(bucket -> bucket.getName().equals(s3Configuration.getBucketName()))) {
			log.info("CREATING NEW BUCKET " + s3Configuration.getBucketName() + ": " + LocalDateTime.now());
			amazonS3.createBucket(
					new CreateBucketRequest(s3Configuration.getBucketName(), s3Configuration.getRegion()));
			log.info("CREATED BUCKET " + s3Configuration.getBucketName() + ": " + LocalDateTime.now());
		} else
			log.info("BUCKET " + s3Configuration.getBucketName() + " ALREADY EXISTS!");
	}

}
