package com.assignment.rewardprogram.controller;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.rewardprogram.DTO.RewardRequestDTO;
import com.assignment.rewardprogram.DTO.RewardResponseDTO;
import com.assignment.rewardprogram.service.RewardProgramService;
import com.fasterxml.jackson.annotation.JsonFormat;

@RestController
public class RewardProgramController {
	private static final Logger logger = LoggerFactory.getLogger(RewardProgramController.class);
	private RewardProgramService rewardProgramService;

	public RewardProgramController(RewardProgramService rewardProgramService) {
		super();
		this.rewardProgramService = rewardProgramService;
	}

	@GetMapping("/rewards/{customerId}")
	public ResponseEntity<?> getRewardPoints(@PathVariable Long customerId,
			@RequestParam @JsonFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
			@RequestParam @JsonFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
		logger.info("Inside Rewards Program Controller");

		// Input Validation: checking for the null values
		if (customerId == null || startDate == null || endDate == null) {
			throw new NullPointerException("Invalid Request");
		}
		RewardRequestDTO rewardRequest = new RewardRequestDTO(customerId, startDate, endDate);
		// Input Validation: startDate Validation
		if (rewardRequest.getStartDate().isAfter(rewardRequest.getEndDate())) {
			throw new IllegalArgumentException("Start date cannot be later than end date");
		}
		// Calling the method from service layer to calculate the rewards
		RewardResponseDTO result = rewardProgramService.calculateRewardPoints(rewardRequest.getCustomerId(),
				rewardRequest.getStartDate(), rewardRequest.getEndDate());
		logger.info("Result of Reward points: " + result);
		return ResponseEntity.ok(result);
	}

}
