# Drone Management System

## Overview
This project is a Drone Management System built using Java, Spring Boot, and Maven. It allows for the registration, loading, and monitoring of drones.

## Prerequisites
- Java 11 or higher
- Maven 3.6.0 or higher

## Build Instructions
To build the project, navigate to the project directory and run:
```sh
mvn clean install
```
## Run Instructions
To run the project, navigate to the project directory and run:
```sh
mvn spring-boot:run
```
The application will be accessible at `http://localhost:8080`.

## API Documentation
The API documentation is available at `http://localhost:8080/swagger-ui/index.html`.

## Endpoints
- Register Drone: POST /drones
- Load Drone with Medication: POST /drones/{droneId}/load
- Get Loaded Medications: GET /drones/{droneId}/medications
- Get Available Drones for Loading: GET /drones/available
- Get Drone Battery Level: GET /drones/{droneId}/battery

## Test Instructions
To run the tests, navigate to the project directory and run:
```sh
mvn test
```
## H2 Database
The H2 database console is available at `http://localhost:8080/h2-console`. The JDBC URL is `jdbc:h2:mem:testdb`.
username: `sa`
password: `password`

## License
This project is licensed under the MIT License.
