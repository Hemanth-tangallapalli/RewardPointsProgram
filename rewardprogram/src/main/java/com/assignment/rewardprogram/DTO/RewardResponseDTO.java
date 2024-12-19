package com.assignment.rewardprogram.DTO;

public class RewardResponseDTO {
	// Creating DTO object to hold the output response from the user.
	private Long customerId;
	private String customerName;
	private int totalRewardPoints;
	private int noOfTransactions;
	public RewardResponseDTO(Long customerId, String customerName, int totalRewardPoints,int noOfTransactions) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.totalRewardPoints = totalRewardPoints;
		this.noOfTransactions = noOfTransactions;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public int getTotalRewardPoints() {
		return totalRewardPoints;
	}
	public void setTotalRewardPoints(int totalRewardPoints) {
		this.totalRewardPoints = totalRewardPoints;
	}
	public int getNoOfTransactions() {
		return noOfTransactions;
	}
	public void setNoOfTransactions(int noOfTransactions) {
		this.noOfTransactions = noOfTransactions;
	}
	@Override
	public String toString() {
		return "RewardResponseDTO [customerId=" + customerId + ", customerName=" + customerName + ", totalRewardPoints="
				+ totalRewardPoints + "]";
	}
	

}
