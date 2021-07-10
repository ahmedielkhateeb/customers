# Customer Application

A new Spring boot application.

## Getting Started

This project is a starting point for a Spring boot application.


# How to run 
- Setup Docker environment on your machine following the steps on the official documentation https://docs.docker.com/get-docker/
- install JDK 8 and Maven and add both to your environments variables
- download the project from the repository as a Zip and unZip it
- open terminal Or cmd and go to the project root location
- run " mvnw clean package -DskipTests " without quotes
- run " docker-compose up " without quotes

# Accessing the Application
- you can access the Application throw http://localhost:8088/

# Provided Apis
- http://localhost:8088/customer/countryCode/{code}/state/{state}
- code allowed values is {any,237,251,212,258,256}
- state allowed values is {any,valid,invalid}
- for example to get all customers use http://localhost:8088/customer/countryCode/any/state/any
- for example to get all customers with invalid phone numbers use http://localhost:8088/customer/countryCode/any/state/invalid
- for example to get all customers with valid phone numbers use http://localhost:8088/customer/countryCode/any/state/valid
- for example to get all customers with country code 212 use http://localhost:8088/customer/countryCode/212/state/any
- for example to get all customers with country code 212 and have valid phone numbers use http://localhost:8088/customer/countryCode/212/state/valid
- for example to get all customers with country code 212 and have invalid phone numbers use http://localhost:8088/customer/countryCode/212/state/invalid