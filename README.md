# E-Commerce Spring Boot Application

## Overview
This project is a robust, enterprise-grade Spring Boot application designed to serve as the backend for an online e-commerce platform. It provides secure, scalable, and efficient RESTful APIs to manage product catalogs, categories, and customer orders. The application strictly adheres to a layered architecture (Controller, Service, Repository) and enforces rigorous data access and data transfer patterns.

## Architecture & Design Principles

* **Layered Architecture:** Clear separation of concerns between web traffic routing (Controllers), business logic (Services), and database interactions (Repositories).
* **Data Transfer Object (DTO) Pattern:** Exposes only necessary data to the client, protecting internal entity states and database structures. Object mapping is automated safely at compile-time using **MapStruct**.
* **Generic Response Wrappers:** Utilizes generic types (e.g., `ProductWrapper<T>`) to provide consistent API response structures across the application while maintaining type safety for both Entities and DTOs.
* **Strict JPQL Enforcement:** Bypasses Spring Data JPA's derived query generation in favor of 100% explicitly defined JPQL `@Query` annotations. This provides complete visibility and fine-grained control over database execution plans.

## Key Technical Features

* **Dynamic Data Filtering:** Implements advanced JPQL `IS NULL` conditional logic to handle highly dynamic search queries. A single, highly optimized query can dynamically filter products by combination of Category, Minimum Price, Maximum Price, and Search Keywords without requiring programmatic Criteria APIs.
* **Efficient Pagination & Sorting:** Utilizes Spring's `Pageable` interface integrated directly into explicit JPQL queries to ensure the database only fetches the required chunk of data, preventing memory exhaustion on large datasets.
* **Optimized Database Joins:** Implements `JOIN FETCH` operations to mitigate the N+1 select problem, ensuring parent-child entity relationships (e.g., Products and Categories) are retrieved in a single, highly performant SQL transaction.
* **Monolithic Frontend Integration:** Serves static resources, utilizing Bootstrap and jQuery to consume the backend APIs and render a responsive user interface.

## Tech Stack

* **Core:** Java 17+, Spring Boot
* **Data Access:** Spring Data JPA, Hibernate, Explicit JPQL
* **Mapping:** MapStruct
* **Build Tool:** Maven
* **Frontend:** HTML, CSS, Bootstrap, jQuery 3.6.0

## API Endpoints Overview

* `GET /categories` - Retrieves a list of all available product categories.
* `GET /deals_of_the_day/{number_of_products}` - Retrieves a paginated, limited list of featured products formatted as DTOs.
* `GET /products` - A highly dynamic endpoint retrieving products. Supports optional query parameters:
  * `category` (String)
  * `minPrice` (Decimal)
  * `maxPrice` (Decimal)
  * `search` (String)

## Setup and Installation

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/Naveen-Sails/Assessment_2_SpringBoot]
   ```
2. **Build the project:**
   Ensure Maven is installed, then run the following command to compile the code and generate the MapStruct implementations:
   ```bash
   mvn clean install
   ```
3. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```
   *(Alternatively, use the provided `Makefile` if configured: `make run`)*
4. **Access the Application:**
   The application and frontend interface will be available at `http://localhost:8081`.
