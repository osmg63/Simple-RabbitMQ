# 🛒 Order Service with RabbitMQ

This is a simple microservice-style project created for learning RabbitMQ with Spring Boot. It demonstrates how to use message queues to communicate between services and send a confirmation email after processing an order.

## 🔧 Technologies Used

- Java 17+
- Spring Boot
- Spring Web
- Spring Data JPA
- RabbitMQ
- Mail Sender (JavaMailSender)
- H2 / PostgreSQL 

## 🧩 Features

- Create an order via REST API
- Use RabbitMQ to trigger:
  - Stock check
  - Product availability
  - User validation
- After successful processing, send a confirmation email

