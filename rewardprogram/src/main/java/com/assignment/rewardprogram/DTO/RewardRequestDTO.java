package com.assignment.rewardprogram.DTO;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class RewardRequestDTO {
	// Creating DTO object to hold the input request from the user.
	private Long customerId;
	//Using JsonFormat annotation to hold the date in the format yyyy-MM-dd
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDate;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate endDate;
	public RewardRequestDTO(Long customerId, LocalDate startDate, LocalDate endDate) {
		super();
		this.customerId = customerId;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	@Override
	public String toString() {
		return "RewardRequestDTO [customerId=" + customerId + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}
	

}
