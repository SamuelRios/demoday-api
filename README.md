# DemoDay API

**DemoDay API** is the backend service for a university application designed to manage users and project presentation events.  
This repository contains the API built with **Java Spring Boot**, following RESTful principles and clean architecture practices.

---

## Technologies Used

- **Java** - Main programming language  
- **Spring Boot** - Framework for application configuration and setup  
- **Spring Web** - RESTful route and controller management  
- **Spring Data JPA** - Data persistence layer  
- **Hibernate** - ORM for entity mapping  
- **Maven / Gradle** - Dependency management  
- **PostgreSQL** - Relational database

---

## Requirements Document

[Access the system requirements document](https://docs.google.com/document/d/1WxX1Q8yOPc812UFwMu00FkKZp7S3j5BIyUZi6pdiwp0/edit?usp=sharing)

---

## API Routes exemple

### **POST /createuser**

This route is used to create a new user in the system.

#### 📥 Request Parameters

The request must include a JSON body with the user data to be created.

| Name | Type | Description | Required |
|------|------|--------------|-----------|
| `cpf` | String | User’s CPF (unique identifier) | Yes |
| `name` | String | User’s name | Yes |
| `email` | String | User’s email address | Yes |
| `university` | String | User’s university | No |
| `type` | Integer | User type. `0` = Student, `1` = Professor | Yes |
| `password` | String | User password (at least 6 characters) | Yes |

**Example request body:**
```json
{
  "name": "Joaquim Joarez",
  "email": "testejoaqui@exemplo.com",
  "cpf": "12345678910",
  "university": "Universidade Federal da Bahia",
  "password": "123456"
}
