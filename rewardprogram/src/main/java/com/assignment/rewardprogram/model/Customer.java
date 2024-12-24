package com.assignment.rewardprogram.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

// Creating Customer table to hold the customer information
@Entity
public class Customer {
	@Id
	private Long id;
	private String name;

	public Customer() {
	}

	public Customer(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
