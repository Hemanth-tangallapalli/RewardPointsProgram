package com.assignment.rewardprogram.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

//Creating Transaction table to hold the transaction details
@Entity
public class Transaction {

	@Id
	private Long id;
	private Double amountSpent;
	private LocalDate transactionDate;

	@ManyToOne
	private Customer customer;

	public Transaction() {
	}

	public Transaction(Long id, Double amountSpent, LocalDate transactionDate, Customer customer) {
		super();
		this.id = id;
		this.amountSpent = amountSpent;
		this.transactionDate = transactionDate;
		this.customer = customer;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getAmountSpent() {
		return amountSpent;
	}

	public void setAmountSpent(Double amountSpent) {
		this.amountSpent = amountSpent;
	}

	public LocalDate getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
