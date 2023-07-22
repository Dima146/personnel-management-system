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
* JUnit 5 and Mockito (all the layers of architecture covered with tests)
* Thymeleaf (a server-side Java template engine to generate HTML pages)
* Bootstrap 5.2.2
* Database: MySQL 8.0.32
* Build tool: Maven

### Installation
Steps to run the application:
1. Clone the project https://github.com/Dima146/personnel-management-system.git.


2. Execute *personnel-management-system.sql* in */sql-scripts* to create and populate the database.


3. Build the project in a project directory `mvn package` or `mvn install`.

 
4. Run the application: `mvn spring-boot:run` or `java -jar target/personnel-management-system-1.0.0-SNAPSHOT`.


5. Use an access URL: `http://localhost:8080/personnel-management-system`



[//]: # (* JDK version: 17)

[//]: # (* Spring JDBC Template to access data stored in a relational database)

[//]: # (* Spring Web MVC - a framework that built on model-view-controller pattern for developing web applications)

[//]: # (* Spring Security - a framework that provides authentication, authorization, and protection against common attacks)

[//]: # (* Spring Boot 3.0.6 RELEASE - a tool that makes developing web application Java Spring Framework faster and easier)

[//]: # (* Hibernate Validator - an implementation of Jakarta Bean Validation for data validation)

[//]: # (* JUnit 5 and Mockito for testing &#40;all the layers of architecture covered with tests&#41;)

[//]: # (* Thymeleaf - a server-side Java template engine to generate HTML pages)

[//]: # (* Bootstrap 5.2.2)

[//]: # (* Database: MySQL 8.0.32)

[//]: # (* Build tool: Maven)