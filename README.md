# RewardPointsProgram

Problem Statement
===============================
A retailer offers a rewards program to its customers, awarding points based on each recorded purchase.  
  
A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every dollar spent between $50 and $100 in each transaction. 
(e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points). 
  
Given a record of every transaction during a three month period, calculate the reward points earned for each customer per month and total. 

Technical Details
================================
Programming Language : Java
Framework : Spring Boot
Database : H-2 Database(In memory database)

Design Details
================================
Database Configuration
-----------------------
I am using H2 Database for this operation. H2 is a in-memory database.
Created two Database tables: Customer and Transaction.
Customer Table holds the customer information like customerId and name.
Transaction Table holds the Transaction information like id, amountSpent, transactionDate and customer_id.
Configured the database related configurations in application.properties under resources.
Configured a data.sql file under resources to load data into the database tables.

Programming Approach
-----------------------
Created individual packages for the service, model, repository, controller, DTOs and exception handling.
Under model layer, created two entity classes: Customer and transaction to hold the parameters and created setter and getter methods.
Under repository layer, created two interfaces which extends the JPARepository for the repository methods. Under RewardProgramRepository interface added a new method "findByCustomerIdAndTransactionDateBetween" which takes the input as customerId,startDate,endDate and returns the records between the given timeframe.
Under Service layer, created methods for the business logic. Created method to calculate the reward points for the given customer and fetching the transaction details from the repository layer.
Under controller layer, created a rest api using postmapping annotation and under this method we are making a call to service layer and fetching the reward points based on the input provided for the api.
Under DTO package, created two DTO classes to hold the input request and the output response.
Under exception handling package, created a GlobalExceptionHandler class to capture the exception scenarios and handled those scenarios using valid responses.

API Details
==========================
URL : http://localhost:8080/rewards
Type : POST

Sample Input Request :

{
"customerId":1,
"startDate":"2024-08-04",
"endDate":"2025-11-10"
}

Sample Output Response:

{
    "customerId": 1,
    "customerName": "Hemanth",
    "totalRewardPoints": 290,
    "noOfTransactions": 4,
    "monthlyPoints": {
        "OCTOBER": 130,
        "AUGUST": 30,
        "SEPTEMBER": 40,
        "NOVEMBER": 90
    }
}

Test Data used and Testing Scenarios
==========================================

Test Data:
----------------------
INSERT INTO customer (id, name) VALUES (1,'Hemanth');
INSERT INTO customer (id, name) VALUES (2,'Harish');
INSERT INTO customer (id, name) VALUES (3,'Anvesh');

INSERT INTO transaction (id,amount_spent,transaction_date,customer_id) VALUES (1,120.00,'2024-11-01',1);
INSERT INTO transaction (id,amount_spent,transaction_date,customer_id) VALUES (2,140.00,'2024-10-02',1);
INSERT INTO transaction (id,amount_spent,transaction_date,customer_id) VALUES (3,90.00,'2024-09-03',1);
INSERT INTO transaction (id,amount_spent,transaction_date,customer_id) VALUES (4,80.00,'2024-08-04',1);
INSERT INTO transaction (id,amount_spent,transaction_date,customer_id) VALUES (5,150.00,'2024-10-01',2);
INSERT INTO transaction (id,amount_spent,transaction_date,customer_id) VALUES (6,220.00,'2024-09-01',2);
INSERT INTO transaction (id,amount_spent,transaction_date,customer_id) VALUES (7,320.00,'2024-08-01',2);
INSERT INTO transaction (id,amount_spent,transaction_date,customer_id) VALUES (8,90.00,'2024-11-01',3);
INSERT INTO transaction (id,amount_spent,transaction_date,customer_id) VALUES (9,120.00,'2024-12-01',3);
INSERT INTO transaction (id,amount_spent,transaction_date,customer_id) VALUES (10,190.00,'2024-08-01',3);
INSERT INTO transaction (id,amount_spent,transaction_date,customer_id) VALUES (11,240.00,'2024-07-01',3);

Test scenarios:
-----------------------------
1) Valid scenario

Request:
	{
	"customerId":1,
	"startDate":"2024-08-04",
	"endDate":"2024-11-10"
	}
Response:
	{
		"customerId": 1,
		"customerName": "Hemanth",
		"totalRewardPoints": 290,
		"noOfTransactions": 4,
		"monthlyPoints": {
			"OCTOBER": 130,
			"AUGUST": 30,
			"SEPTEMBER": 40,
			"NOVEMBER": 90
		}
	}

2) Invalid Request/Empty Request 

Request:
	{}
Response:
	Invalid Request

3) Invalid Date format

Request : 
	{
	"customerId":1,
	"startDate":"2024-008-04",
	"endDate":"2024-11-10"
	}
Response:
	Enter date in the format yyyy-MM-dd

4) Start date later than end date

Request:
	{
	"customerId":1,
	"startDate":"2025-08-04",
	"endDate":"2024-11-10"
	}
Response:
	Start date cannot be later than end date
	
5) Customer Id not found

Request:
	{
	"customerId":10,
	"startDate":"2024-08-04",
	"endDate":"2024-11-10"
	}
Response:
	Customer not found