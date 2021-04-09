package com.hardik.themyscira.request;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class BinaryItemCreationRequest {
	
	private final String itemName;
	private final Boolean deletable;

}
