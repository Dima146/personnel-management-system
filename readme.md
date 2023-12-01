<h2 align="center">Personnel Management System</h2>

### Description
Web application for employee information management. Working with the program is based on adding, changing and deleting information about workers. Search by first name and/or last name is also available. The system is organized as a table sorted by person's last name. Access to the functionality of the application is provided after **logging in**.
After **registration**, specific functions are available to the user depending on the authority.
The administrator has access to all the functionality of the program, while a user with the manager role is limited to adding a new employee and changing information about him.

*The application is not limited to the current state and will be expanded by adding new features in the future.*

### Used Technologies

* JDK version: 17
* Spring Web MVC
* Spring Security
* Spring JDBC Template (access data stored in a relational database)
* Spring Boot 3.0.6 RELEASE
* Hibernate Validator
* JUnit 5 and Mockito
* Thymeleaf (a server-side Java template engine to generate HTML pages)
* Logback logger
* Build tool: Maven
* Database: MySQL 8.0.32
* Bootstrap 5.2.2

#### Additional information
* The application is organised into the three-tier architecture. Each layer has its own logic.
* Thymeleaf is used for generating HTML pages.
* The error/exception handling mechanism has been implemented.
* The application supports the session-based user authentication.
* The custom password validation rules have been implemented.
* DTO pattern is used for carrying data between the layers.
* All the layers of the application architecture are covered with tests.

### Installation
Steps to run the application:
1. Clone the project https://github.com/Dima146/personnel-management-system.git.

2. Execute *personnel-management-system.sql* in */sql-scripts* to create and populate the database.

3. Build the project in a project directory `mvn package` or `mvn install`.

4. Run the application: `mvn spring-boot:run` or `java -jar target/personnel-management-system-1.0.0-SNAPSHOT`.

5. Use an access URL: `http://localhost:8080/personnel-management-system`