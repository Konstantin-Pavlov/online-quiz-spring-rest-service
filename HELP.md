# Online Quiz Platform

## Overview

The Online Quiz Platform is a web application that allows users to create, solve, and rate quizzes. It also provides user statistics and quiz leaderboards.

## Prerequisites

- Java 21
- Maven
- H2 Database (embedded)
- Postman (for API testing)

## Project Structure

- `src/main/java/kg/attractor/online_quiz_platform`: Contains the main application code.
- `src/main/resources`: Contains configuration files and database changelogs.
- `pom.xml`: Maven configuration file.
- `json_with_endpoints_for_postman.json`: Postman collection for testing the API endpoints.

## Setting Up the Project

1. **Clone the repository:**

   ```sh
   git clone <repository-url>
   cd Online_Quiz_Platform

2. **Build the project:**
 ```sh
mvn clean install
````
3. **Run the application:**

```sh
mvn spring-boot:run
```

The application will start on port 8089 by default.

## Technologies Used

### Java 21
The project is built using **Java 21**, which provides the latest features and improvements in the Java programming language. Java 21 includes enhancements in performance, security, and developer productivity.

### Spring Boot
**Spring Boot** is used to create stand-alone, production-grade Spring-based applications. It simplifies the configuration and setup of Spring applications with its opinionated defaults and auto-configuration capabilities.

### Spring Data JDBC
**Spring Data JDBC** is used for data access. It provides a simple and consistent approach to data access using JDBC, leveraging Spring's powerful data access features.

### H2 Database
**H2** is an embedded, in-memory database that is used for development and testing purposes. It allows for quick setup and teardown of the database without the need for an external database server.

### Liquibase
**Liquibase** is a database schema change management tool. It allows for versioning of database changes and ensures that the database schema is consistent across different environments. The database changelogs are defined in `src/main/resources/db_liquid/changelog`.

### Lombok
**Lombok** is a Java library that helps reduce boilerplate code by generating common methods like `getters`, `setters`, `equals`, `hashCode`, and `toString` at compile time using annotations.

### Spring Security
**Spring Security** is used for authentication and authorization. It provides a comprehensive security framework that supports various authentication mechanisms, including basic authentication used in this project.

### Jakarta Bean Validation
**Jakarta Bean Validation** is used for validating request payloads. It ensures that the data received in the API requests meets the defined constraints and provides meaningful error messages when validation fails.

### Springdoc OpenAPI
**Springdoc OpenAPI** is used for generating API documentation. It automatically generates OpenAPI 3.0 documentation for the RESTful APIs defined in the project, which can be accessed at [http://localhost:8089/swagger-ui.html](http://localhost:8089/swagger-ui.html).

### Maven
**Maven** is used as the build tool for the project. It manages dependencies, compiles the code, runs tests, and packages the application.


## API Endpoints

The API endpoints are documented in the `json_with_endpoints_for_postman.json` file. This file can be imported into Postman to test the API.

## Importing Postman Collection

1. Open Postman.
2. Click on **Import** in the top left corner.
3. Select the `json_with_endpoints_for_postman.json` file from the project directory.
4. Click **Import**.


## Using the Postman Collection

The Postman collection contains the following endpoints:

<details>
  <summary><strong>Register Controller</strong></summary>

&nbsp;&nbsp;&nbsp;&nbsp;**Register User**  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**Method**: POST  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**URL**: `http://localhost:8089/api/register`

</details>

<details>
  <summary><strong>Quiz Controller</strong></summary>

&nbsp;&nbsp;&nbsp;&nbsp;**Create Quiz**  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**Method**: POST  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**URL**: `http://localhost:8089/api/quizzes`

&nbsp;&nbsp;&nbsp;&nbsp;**Solve Quiz**  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**Method**: POST  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**URL**: `http://localhost:8089/api/quizzes/100/solve`

&nbsp;&nbsp;&nbsp;&nbsp;**Rate Quiz**  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**Method**: POST  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**URL**: `http://localhost:8089/api/quizzes/1/rate`

&nbsp;&nbsp;&nbsp;&nbsp;**Get Quizzes**  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**Method**: GET  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**URL**: `http://localhost:8089/api/quizzes`

&nbsp;&nbsp;&nbsp;&nbsp;**Get Quiz By ID**  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**Method**: GET  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**URL**: `http://localhost:8089/api/quizzes/1`

&nbsp;&nbsp;&nbsp;&nbsp;**Get Quiz Results**  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**Method**: GET  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**URL**: `http://localhost:8089/api/quizzes/1/results`

&nbsp;&nbsp;&nbsp;&nbsp;**Get Quizzes Rates**  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**Method**: GET  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**URL**: `http://localhost:8089/api/quizzes/rates`

&nbsp;&nbsp;&nbsp;&nbsp;**Get Quiz Leaderboard**  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**Method**: GET  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**URL**: `http://localhost:8089/api/quizzes/1/leaderboard`

</details>

<details>
  <summary><strong>User Controller</strong></summary>

&nbsp;&nbsp;&nbsp;&nbsp;**Get Users**  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**Method**: GET  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**URL**: `http://localhost:8089/api/users`

&nbsp;&nbsp;&nbsp;&nbsp;**Get User By ID**  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**Method**: GET  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**URL**: `http://localhost:8089/api/users/1`

&nbsp;&nbsp;&nbsp;&nbsp;**Get User Statistics**  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**Method**: GET  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**URL**: `http://localhost:8089/api/users/10/statistics`

</details>


## Security
The application uses Spring Security for authentication and authorization. Basic authentication is used for securing the API endpoints.

## License
This project is licensed under the MIT License.