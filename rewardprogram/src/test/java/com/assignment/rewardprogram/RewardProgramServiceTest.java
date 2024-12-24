package com.assignment.rewardprogram;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.assignment.rewardprogram.DTO.RewardResponseDTO;
import com.assignment.rewardprogram.model.Customer;
import com.assignment.rewardprogram.model.Transaction;
import com.assignment.rewardprogram.repository.CustomerRepository;
import com.assignment.rewardprogram.repository.RewardProgramRepository;
import com.assignment.rewardprogram.service.RewardProgramService;

public class RewardProgramServiceTest {

	@Mock
	private RewardProgramRepository rewardProgramRepository;
	@Mock
	private CustomerRepository customerRepository;

	private RewardProgramService rewardProgramService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		rewardProgramService = new RewardProgramService(rewardProgramRepository, customerRepository);
	}

	@Test
	void testCalculateRewardPoints_Success() {
		Long customerId = 1L;
		LocalDate startDate = LocalDate.of(2024, 10, 01);
		LocalDate endDate = LocalDate.of(2024, 11, 30);
		Customer customer = new Customer(customerId, "Hemanth");
		List<Transaction> transactions = List.of(new Transaction(1L, 120.0, LocalDate.of(2024, 11, 01), customer),
				new Transaction(2L, 140.0, LocalDate.of(2024, 10, 02), customer));
		when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
		when(rewardProgramRepository.findByCustomerIdAndTransactionDateBetween(customerId, startDate, endDate))
				.thenReturn(transactions);
		RewardResponseDTO result = rewardProgramService.calculateRewardPoints(customerId, startDate, endDate);

		assertNotNull(result);
		assertEquals(220, result.getTotalRewardPoints());
		assertTrue(result.getMonthlyPoints().containsKey("OCTOBER"));
	}

	@Test
	void testCalculateRewardPoints_CustomerNotFound() {
		Long customerId = 10L;
		LocalDate startDate = LocalDate.of(2024, 10, 01);
		LocalDate endDate = LocalDate.of(2024, 11, 30);
		when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

		assertThrows(IllegalArgumentException.class, () -> {
			rewardProgramService.calculateRewardPoints(customerId, startDate, endDate);
		});
	}

	@Test
	void testCalculateRewardPoints_NoTransactions() {
		Long customerId = 1L;
		LocalDate startDate = LocalDate.of(2025, 10, 01);
		LocalDate endDate = LocalDate.of(2025, 11, 30);
		Customer customer = new Customer(customerId, "Hemanth");
		when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
		when(rewardProgramRepository.findByCustomerIdAndTransactionDateBetween(customerId, startDate, endDate))
				.thenReturn(Collections.emptyList());
		RewardResponseDTO result = rewardProgramService.calculateRewardPoints(customerId, startDate, endDate);

		assertEquals(0, result.getTotalRewardPoints());
	}

}
