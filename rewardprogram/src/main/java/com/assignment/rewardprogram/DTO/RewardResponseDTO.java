package com.assignment.rewardprogram.DTO;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RewardResponseDTO {
	// Creating DTO object to hold the output response from the user.
	private Long customerId;
	private String customerName;
	private int totalRewardPoints;
	private int noOfTransactions;
	private Map<String, Integer> monthlyPoints;

}
