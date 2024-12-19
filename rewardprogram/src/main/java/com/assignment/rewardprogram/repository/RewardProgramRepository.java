package com.assignment.rewardprogram.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.rewardprogram.model.Transaction;

@Repository
public interface RewardProgramRepository extends JpaRepository<Transaction, Integer> {

	List<Transaction> findByCustomerIdAndTransactionDateBetween(Long customerId, LocalDate startDate,
			LocalDate endDate);

}
