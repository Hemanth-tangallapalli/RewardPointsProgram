package com.assignment.rewardprogram.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.assignment.rewardprogram.DTO.RewardResponseDTO;
import com.assignment.rewardprogram.model.Customer;
import com.assignment.rewardprogram.model.Transaction;
import com.assignment.rewardprogram.repository.CustomerRepository;
import com.assignment.rewardprogram.repository.RewardProgramRepository;

@Service
public class RewardProgramService {

	private static final Logger logger = LoggerFactory.getLogger(RewardProgramService.class);
	private final RewardProgramRepository rewardProgramRepository;
	private final CustomerRepository customerRepository;

	public RewardProgramService(RewardProgramRepository rewardProgramRepository,
			CustomerRepository customerRepository) {
		super();
		this.rewardProgramRepository = rewardProgramRepository;
		this.customerRepository = customerRepository;
	}

	public RewardResponseDTO calculateRewardPoints(Long customerId, LocalDate startDate, LocalDate endDate) throws IllegalArgumentException{
		// Method for calculating the Reward points for the given customer.
		logger.info("Calculating the Rewardpoints for the given CustomerId:" + customerId);
		//Fetching the customer Details and validating whether the customer present or not.
		Optional<Customer> customerOpt = customerRepository.findById(customerId);
		if (customerOpt.isEmpty()) {
			throw new IllegalArgumentException("Customer not found");
		}
		//Fetching the transaction details from Database based on the customerId and the date range
		List<Transaction> transactions = rewardProgramRepository.findByCustomerIdAndTransactionDateBetween(customerId,
				startDate, endDate);
		Customer customer = customerOpt.get();
		int totalPoints = 0;
		for (Transaction transaction : transactions) {
			Double amountSpent = transaction.getAmountSpent();
			logger.debug("Transaction amount:" + amountSpent);
			if (amountSpent > 100) {
				totalPoints += (amountSpent - 100) * 2;
				amountSpent = (double) 100;
			}
			if (amountSpent > 50) {
				totalPoints += amountSpent - 50;
			}
		}
		logger.debug("Total reward points for the given Customer:" + totalPoints);
		return new RewardResponseDTO(customer.getId(), customer.getName(), totalPoints,transactions.size());
	}

}
