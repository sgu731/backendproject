# Shop API

A RESTful e-commerce backend built with Spring Boot and PostgreSQL.

## Tech Stack

- Java 17
- Spring Boot 3
- Spring Security + JWT
- JdbcTemplate
- PostgreSQL
- Flyway
- Swagger (OpenAPI)
- Docker Compose
- Bucket4j
- JUnit5

---

## Features

### Authentication

- JWT Login
- BCrypt Password Encoder

### Product

- Create Product (ADMIN)
- Update Product (ADMIN)
- Soft Delete Product (ADMIN)
- Product Pagination
- Product Search
- Price Filtering
- Sorting

### Order

- Create Order
- Query Orders
- Inventory Deduction

### Audit Log

Track:

- Create Product
- Update Product
- Delete Product

### Rate Limiting

Token Bucket algorithm implemented with Bucket4j.

---

## Start PostgreSQL

```bash
docker compose up -d
```

## Run Application 
```
mvn spring-boot:run
```

##Swagger
```
http://localhost:8080/swagger-ui.html
```

##Default Users
```
ADMIN

username:
admin
password:
admin123

USER

username:
user
password:
admin123
```