A Spring Boot application that lets its user execute 4 types of API requests, such as POST/GET/PUT/DELETE via either Postman or in a HTML website

Logging and exceptions for the EmployeeController, integration tests and authentication (with OAuth2 and JWT) have also been implemented.

If someone were to run this program and wants to execute API requests via HTML, please visit http://localhost:8080/main-page and enter the following details in the popup
Username: username
Password: password

In Postman, someone could first request a JWT token by doing http://localhost:8080/token, then use that token to do...
1. POST request via http://localhost:8080/employees
2. GET request via http://localhost:8080/employees
3. PUT request via http://localhost:8080/employees/{empId}
4. DELETE request via http://localhost:8080/employees/{empId}