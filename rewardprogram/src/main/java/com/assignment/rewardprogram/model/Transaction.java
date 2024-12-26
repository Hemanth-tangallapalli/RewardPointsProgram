package com.assignment.rewardprogram.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Creating Transaction table to hold the transaction details
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

	@Id
	private Long id;
	private Double amountSpent;
	private LocalDate transactionDate;

	@ManyToOne
	private Customer customer;

}
