package com.assignment.rewardprogram.DTO;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RewardRequestDTO {
	// Creating DTO object to hold the input request from the user.
	private Long customerId;
	//Using JsonFormat annotation to hold the date in the format yyyy-MM-dd
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDate;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate endDate;

}
