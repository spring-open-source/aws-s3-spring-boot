package com.hardik.themyscira.storage.configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "com.hardik.themyscira")
public class AmazonS3Configuration {

	private Configuration storage = new Configuration();

	@Data
	public class Configuration {
		private String accessKey;
		private String secretKey;
		private String region;
		private String bucketName;
	}
}