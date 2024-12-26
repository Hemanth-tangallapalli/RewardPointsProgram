package com.assignment.rewardprogram;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.assignment.rewardprogram.DTO.RewardResponseDTO;
import com.assignment.rewardprogram.controller.RewardProgramController;
import com.assignment.rewardprogram.service.RewardProgramService;

@SpringBootTest
@AutoConfigureMockMvc
public class RewardProgramControllerIntegrationTest {

	@InjectMocks
	private RewardProgramController rewardProgramController;

	@Mock
	private RewardProgramService rewardProgramService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	//Test case 1: Valid Scenario
	void testGetRewardPoints_Success() throws Exception {
		Long customerId = 1L;
		LocalDate startDate = LocalDate.of(2024, 11, 01);
		LocalDate endDate = LocalDate.of(2024, 11, 30);
		RewardResponseDTO responseDTO = new RewardResponseDTO(customerId, "Hemanth", 90, 1, Map.of("NOVEMBER", 90));

		when(rewardProgramService.calculateRewardPoints(customerId, startDate, endDate)).thenReturn(responseDTO);

		mockMvc.perform(get("/rewards/{customerId}", customerId).param("startDate", "2024-11-01")
				.param("endDate", "2024-11-30").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.customerId").value(customerId))
				.andExpect(jsonPath("$.totalRewardPoints").value(90))
				.andExpect(jsonPath("$.customerName").value("Hemanth"));
	}

	@Test
	//Test case 2: Scenario where transaction amount is 50
	void testGetRewardPoints_ForTransaction50() throws Exception {
		Long customerId = 1L;
		LocalDate startDate = LocalDate.of(2024, 07, 01);
		LocalDate endDate = LocalDate.of(2024, 07, 30);
		RewardResponseDTO responseDTO = new RewardResponseDTO(customerId, "Hemanth", 0, 1, Map.of("JULY", 0));

		when(rewardProgramService.calculateRewardPoints(customerId, startDate, endDate)).thenReturn(responseDTO);

		mockMvc.perform(get("/rewards/{customerId}", customerId).param("startDate", "2024-07-01")
				.param("endDate", "2024-07-30").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.customerId").value(customerId))
				.andExpect(jsonPath("$.totalRewardPoints").value(0))
				.andExpect(jsonPath("$.customerName").value("Hemanth"));
	}

	@Test
	//Test case 3: Scenario where transaction amount is 100
	void testGetRewardPoints_ForTransaction100() throws Exception {
		Long customerId = 1L;
		LocalDate startDate = LocalDate.of(2024, 06, 01);
		LocalDate endDate = LocalDate.of(2024, 06, 30);
		RewardResponseDTO responseDTO = new RewardResponseDTO(customerId, "Hemanth", 0, 1, Map.of("JUNE", 0));

		when(rewardProgramService.calculateRewardPoints(customerId, startDate, endDate)).thenReturn(responseDTO);

		mockMvc.perform(get("/rewards/{customerId}", customerId).param("startDate", "2024-06-01")
				.param("endDate", "2024-06-30").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.customerId").value(customerId))
				.andExpect(jsonPath("$.totalRewardPoints").value(50))
				.andExpect(jsonPath("$.customerName").value("Hemanth"));
	}

	@Test
	//Test case 4: CustomerId not Found
	void testGetRewardPoints_InvalidCustomerId() throws Exception {
		Long customerId = 100L;
		LocalDate startDate = LocalDate.of(2024, 06, 01);
		LocalDate endDate = LocalDate.of(2024, 06, 30);
		when(rewardProgramService.calculateRewardPoints(customerId, startDate, endDate))
				.thenThrow(new IllegalArgumentException("Customer not Found"));

		mockMvc.perform(get("/rewards/{customerId}", customerId).param("startDate", "2024-06-01")
				.param("endDate", "2024-06-30").accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Customer not found"));
	}

	@Test
	//Test Case 5: Invalid Date Range
	void testGetRewardPoints_InvalidDateRange() throws Exception {
		Long customerId = 1L;

		mockMvc.perform(get("/rewards/{customerId}", customerId).param("startDate", "2025-06-01")
				.param("endDate", "2024-06-30").accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Start date cannot be later than end date"));
	}
	
	@Test
	//Test Case 6: Invalid Date parameters or missing Date parameters
	void testGetRewardPoints_MissingParameter() throws Exception {
		Long customerId = 1L;

		mockMvc.perform(get("/rewards/{customerId}", customerId)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Enter date in the format yyyy-MM-dd"));
	}
	
}
