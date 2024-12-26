package com.assignment.rewardprogram.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Creating Customer table to hold the customer information
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
	@Id
	private Long id;
	private String name;

}
