# Technical Assesment
___
## Clients Microservice
### Technologies and dependencies
* Java 17
* Spring Boot 3
* MySQL 8
* PostgreSQL 16
* Flyway
* WebClient
* Lombok
* Mapstruct
___
### Execute this application
To run this application you need a MySQL 8 and Postgresql 16 database.

Before running the containers you need to run: `mvn clean package` in ***./clients-service*** and ***./accounts-service***

After that, to run the containers, you can execute the following command in the root folder:
>`docker-compose up`

To run Tests you can execute the following command in the local environment: 
>`mvn test`

After that you can initialize the microservices, the initial schema is automatically created with flyway

### Clients Microservice
* The endpoints are at: http://localhost:8080/api/v1/clients
* You can see the documentation at: http://localhost:8080/swagger-ui/index.html

### Accounts Microservice
* The endpoints of Account are at: http://localhost:9090/api/v1/accounts
* The endpoints of Movement are at: http://localhost:9090/api/v1/movements
* The endpoint of Report is at: http://localhost:9090/api/v1/reports
* You can see the documentation at: http://localhost:9090/swagger-ui/index.html
