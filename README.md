# Application Description
The LectureApp is a Spring Boot application is a basic CRUD app that manages
students, courses, and their respective registrations. 
Once you've set up the project, all endpoints are accessible at `localhost:8080/api`.
In order to use the courses-controller endpoints you first need a bearer token
which is retrieved by registering and logging in.

# Dependencies
Below is a list of dependencies used in the project along with their versions:

- **Spring Boot Dependencies:**
    - Spring Boot Starter Data JPA
    - Spring Boot Starter Web
    - Spring Boot Starter Security
    - Spring Boot Starter Actuator

- **Springdoc OpenAPI for Swagger UI:**
    - `org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0`

- **JSON Web Tokens (JWT) Handling:**
    - JJWT (Java JWT): `io.jsonwebtoken:jjwt:0.12.5`
    - JJWT Implementation: `io.jsonwebtoken:jjwt-impl:0.12.5` (runtime only)
    - JJWT Jackson Integration: `io.jsonwebtoken:jjwt-jackson:0.12.5` (runtime only)

- **Lombok for Boilerplate Code Reduction:**
    - Lombok: `org.projectlombok:lombok` (compile only and annotation processor)

- **MySQL JDBC Connector:**
    - MySQL Connector/J: `com.mysql:mysql-connector-j` (runtime only)

- **Testing Framework:**
    - Spring Boot Starter Test

# Getting Started

After cloning the project you should create a database using MySQL.
To create a new database, run the following commands at the `mysql` prompt:
```agsl
mysql> create database db_example; -- Creates the new database
mysql> create user 'springuser'@'%' identified by 'ThePassword'; -- Creates the user
mysql> grant all on db_example.* to 'springuser'@'%'; -- Gives all privileges to the new user on the newly created database
```
After creating the database you should add the following configuration to the application properties:
```agsl
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/db_example
spring.datasource.username=springuser
spring.datasource.password=ThePassword
```

After doing these steps you are ready to use the app. 