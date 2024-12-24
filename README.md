# RewardPointsProgram

Problem Statement
===============================
A retailer offers a rewards program to its customers, awarding points based on each recorded purchase. <br />
  
A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every dollar spent between $50 and $100 in each transaction. <br />
(e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points). <br />
  
Given a record of every transaction during a three month period, calculate the reward points earned for each customer per month and total. <br />

Technical Details
================================
Programming Language : Java<br />
Framework : Spring Boot<br />
Database : H-2 Database(In memory database)<br />

Design Details
================================
Database Configuration
-----------------------
We are using H2 Database for this operation. H2 is a in-memory database.<br />
Created two Database tables: Customer and Transaction.<br />
Customer Table holds the customer information like customerId and name.<br />
Transaction Table holds the Transaction information like id, amountSpent, transactionDate and customer_id.<br />
Configured the database related configurations in application.properties under resources.<br />
Configured a data.sql file under resources to load data into the database tables.<br />

Programming Approach
-----------------------
Created individual packages for the service, model, repository, controller, DTOs, exception handling and Tests.<br />
Under model layer, created two entity classes: Customer and transaction to hold the parameters and created setter and getter methods.<br />
Under repository layer, created two interfaces which extends the JPARepository for the repository methods. Under RewardProgramRepository interface added a new method "findByCustomerIdAndTransactionDateBetween" which takes the input as customerId,startDate,endDate and returns the records between the given timeframe.<br />
Under Service layer, created methods for the business logic. Created method to calculate the reward points for the given customer and fetching the transaction details from the repository layer.<br />
Under controller layer, created a rest api using postmapping annotation and under this method we are making a call to service layer and fetching the reward points based on the input provided for the api.<br />
Under DTO package, created two DTO classes to hold the input request and the output response.<br />
Under exception handling package, created a GlobalExceptionHandler class to capture the exception scenarios and handled those scenarios using valid responses.<br />
Under the path src/test/java, created two test classes RewardProgramControllerTest and RewardProgramServiceTest for testing various test scenarios for the controller and service layer.

API Details
==========================
URL : http://localhost:8080/rewards/{customerId}?startDate={startDate}&endDate={endDate} <br />
Type : GET

Sample Input Request :
```
http://localhost:8080/rewards/1?startDate=2024-10-10&endDate=2025-11-10
```
Sample Output Response:
```
{
    "customerId": 1,
    "customerName": "Hemanth",
    "totalRewardPoints": 90,
    "noOfTransactions": 1,
    "monthlyPoints": {
        "NOVEMBER": 90
    }
}
```
Test Data used and Testing Scenarios
==========================================

Test Data:
----------------------
```
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
```
Test scenarios:
-----------------------------
1) Valid scenario

Request:
```
http://localhost:8080/rewards/1?startDate=2024-08-10&endDate=2025-11-10
```
Response:
```
{
    "customerId": 1,
    "customerName": "Hemanth",
    "totalRewardPoints": 260,
    "noOfTransactions": 3,
    "monthlyPoints": {
        "OCTOBER": 130,
        "SEPTEMBER": 40,
        "NOVEMBER": 90
    }
}
```
2) Invalid Request/Empty Request 

Request:
```
	http://localhost:8080/rewards/?startDate=2024-08-10&endDate=2025-11-10
```
Response:
```
{
    "timeStamp": "2024-12-24T16:45:49.4460909",
    "message": "No resource present for the given customerId"
}
```

3) Invalid Date format

Request : 
```
http://localhost:8080/rewards/1?startDate=2024-08-010&endDate=2025-11-10
```
Response:
```
{
    "timeStamp": "2024-12-24T16:53:26.1090814",
    "message": "Enter date in the format yyyy-MM-dd"
}
```

4) Start date later than end date

Request:
```
http://localhost:8080/rewards/1?startDate=2026-08-01&endDate=2025-11-10
```
Response:
```
{
    "timeStamp": "2024-12-24T16:54:09.4197632",
    "message": "Start date cannot be later than end date"
}
```
	
5) Customer Id not found

Request:
```
http://localhost:8080/rewards/10?startDate=2026-08-01&endDate=2025-11-10
```
Response:
```
{
    "timeStamp": "2024-12-24T16:54:59.2952509",
    "message": "Start date cannot be later than end date"
}
```