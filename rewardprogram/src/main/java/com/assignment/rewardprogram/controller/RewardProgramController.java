package com.assignment.rewardprogram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.rewardprogram.DTO.RewardRequestDTO;
import com.assignment.rewardprogram.DTO.RewardResponseDTO;
import com.assignment.rewardprogram.service.RewardProgramService;

@RestController
public class RewardProgramController {
	private static final Logger logger = LoggerFactory.getLogger(RewardProgramController.class);
	private RewardProgramService rewardProgramService;

	public RewardProgramController(RewardProgramService rewardProgramService) {
		super();
		this.rewardProgramService = rewardProgramService;
	}

	@PostMapping("/rewards")
	public ResponseEntity<?> getRewardPoints(@RequestBody RewardRequestDTO rewardRequest) {
		logger.info("Inside Rewards Program Controller");
		//Input Validation: checking for the null values
		if (rewardRequest.getCustomerId() == null || rewardRequest.getStartDate() == null ||rewardRequest.getEndDate() == null) {
			throw new NullPointerException("Invalid Request");
		}
		//Input Validation: startDate Validation
		if (rewardRequest.getStartDate().isAfter(rewardRequest.getEndDate())) {
			throw new IllegalArgumentException("Start date cannot be later than end date");
		}
		//Calling the method from service layer to calculate the rewards
			RewardResponseDTO result = rewardProgramService.calculateRewardPoints(rewardRequest.getCustomerId(),
					rewardRequest.getStartDate(), rewardRequest.getEndDate());
			logger.info("Result of Reward points: "+result);
			return ResponseEntity.ok(result);
	}

}
