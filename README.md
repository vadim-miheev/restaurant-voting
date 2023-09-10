# Restaurant voting
Voting system (Backend) for deciding where to have lunch

## Business requirements:

Build a voting system for deciding where to have lunch.

- 2 types of users: admin and regular users
- Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
- Menu changes each day (admins do the updates)
- Users can vote for a restaurant they want to have lunch at today
- Only one vote counted per user
- If user votes again the same day:
  - If it is before 11:00 we assume that he changed his mind.
  - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides a new menu each day.

## Stack
- [JDK 17](http://jdk.java.net/17/)
- Maven
- Spring Boot 3.1.2
- Spring MVC
- Spring Data JPA (Hibernate)
- Spring Security
- Lombok
- Jackson
- JUnit 5
- Swagger/OpenAPI 3.0
- H2

## Run
Execute in root directory:
```
mvn spring-boot:run
```

## [REST API documentation](http://83.166.240.102:8080/swagger-ui/index.html)  
Credentials:
```
Admin: admin / admin
User:  user / user
```