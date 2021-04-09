package com.hardik.themyscira.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hardik.themyscira.service.BinaryItemService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ExpiredBinaryItemDeletionScheduler {
	
	private final BinaryItemService binaryItemService;

	@Scheduled(cron = "0 0 0 * * ?")
	public void expiredBinaryItemDeletionJob() {
		binaryItemService.deleteExpiredBinaryItems();
	}

}
