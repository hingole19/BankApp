# BankApp
Simple Banking Application 

## Requirements
- Java 11
- Spring Boot
- Maven
- H2 Database

## IDE 
>IntelliJ Idea || STS || Eclipse

## Framework
Spring Boot framework of Java.

## Dependencies

- Spring web
- H2 database
- Lombok
- Spring Data JPA
- Spring Security

## APIS
- Register a new user:
    - URL : POST - /api/auth/register
    - Request Body : User registration details
  - Login:
    - URL : POST - /api/auth/login
    - Request Body : User login credentials
    - Response Body : JWT token for authentication
  - Current user's account balance:
    - URL : GET - /api/balance/checkbalance
  - To Withdrawal:
    - URL : POST - /api/transaction/debit
    - Request Body : Withdrawal details (amount)
  - To Deposit:
    - URL : POST - /api/transaction/credit
    - Request Body : Deposit details (amount)
  
