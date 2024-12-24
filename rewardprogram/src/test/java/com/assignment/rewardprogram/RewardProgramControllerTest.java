package com.assignment.rewardprogram;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.assignment.rewardprogram.DTO.RewardResponseDTO;
import com.assignment.rewardprogram.controller.RewardProgramController;
import com.assignment.rewardprogram.service.RewardProgramService;

public class RewardProgramControllerTest {

	@Mock
	private RewardProgramService rewardProgramService;

	private RewardProgramController rewardProgramController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		rewardProgramController = new RewardProgramController(rewardProgramService);
	}

	@SuppressWarnings("deprecation")
	@Test
	//Test case 1: Valid Scenario
	void testGetRewardPoints_Success() {

		Long customerId = 1L;
		LocalDate startDate = LocalDate.of(2024, 11, 01);
		LocalDate endDate = LocalDate.of(2024, 11, 30);
		RewardResponseDTO responseDTO = new RewardResponseDTO(customerId, "Hemanth", 90, 1, Map.of("November", 90));

		when(rewardProgramService.calculateRewardPoints(customerId, startDate, endDate)).thenReturn(responseDTO);

		ResponseEntity<?> response = rewardProgramController.getRewardPoints(customerId, startDate, endDate);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(responseDTO, response.getBody());
	}

	@Test
	//Test case 2: CustomerId not Found
	void testGetRewardPoints_CustomerNotFound() {
		Long customerId = 10L;
		LocalDate startDate = LocalDate.of(2024, 11, 01);
		LocalDate endDate = LocalDate.of(2024, 11, 30);

		when(rewardProgramService.calculateRewardPoints(customerId, startDate, endDate))
				.thenThrow(new IllegalArgumentException("Customer not found"));

		assertThrows(IllegalArgumentException.class, () -> {
			rewardProgramController.getRewardPoints(customerId, startDate, endDate);
		});
	}

	@Test
	//Test Case 3: Invalid Date Range
	void testGetRewardPoints_InvalidDateRange() {
		Long customerId = 1L;
		LocalDate startDate = LocalDate.of(2024, 12, 01);
		LocalDate endDate = LocalDate.of(2024, 11, 30);

		assertThrows(IllegalArgumentException.class, () -> {
			rewardProgramController.getRewardPoints(customerId, startDate, endDate);
		});
	}

	@Test
	// Test Case 4: Null/Empty CustomerId 
	void testGetRewardPoints_NullCustomerId() {
		Long customerId = null;
		LocalDate startDate = LocalDate.of(2024, 11, 01);
		LocalDate endDate = LocalDate.of(2024, 11, 30);

		assertThrows(NullPointerException.class, () -> {
			rewardProgramController.getRewardPoints(customerId, startDate, endDate);
		});
	}

	@Test
	//Test Case 5: Null/Empty Start and End Dates
	void testGetRewardPoints_NullDates() {
		Long customerId = 1L;
		LocalDate startDate = null;
		LocalDate endDate = null;

		assertThrows(NullPointerException.class, () -> {
			rewardProgramController.getRewardPoints(customerId, startDate, endDate);
		});
	}

	@Test
	//Test Case6: Invalid Date Format
	void testGetRewardPoints_InvalidDateFormat() {
		Long customerId = 1L;
		String startDate = "invalid-date";
		String endDate = "invalid-date";

		assertThrows(DateTimeParseException.class, () -> {
			rewardProgramController.getRewardPoints(customerId, LocalDate.parse(startDate), LocalDate.parse(endDate));
		});
	}

	@Test
	//Test Case7: Invalid CustomerId Format
	void testGetRewardPoints_InvalidCustomerIdFormat() {
		Long customerId = -1L;
		LocalDate startDate = LocalDate.of(2024, 11, 01);
		LocalDate endDate = LocalDate.of(2024, 11, 30);

		assertThrows(IllegalArgumentException.class, () -> {
			rewardProgramController.getRewardPoints(customerId, startDate, endDate);
		});
	}
}
