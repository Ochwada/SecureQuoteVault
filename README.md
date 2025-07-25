 # 💡 Secure Quote Vault App

## Background

In modern web applications, it's essential to **secure data access** and **differentiate users by roles**. Whether you're 
building a banking app, a school system, or a dashboard, you need secure authentication and role-based authorization.

**Secure Quote Vault** — is a Spring Boot application that allows users to **sign up**, **sign in**, and **access
random quotes** from an **external quote API**. Only authenticated users will be allowed to retrieve a quote, and the 
system will support multiple roles like `USER`, `ADMIN`, `MODERATOR`, and `SUPER_ADMIN`.

#### Goals :

* Securing an API using **JWT tokens**
* Using **MongoDB Atlas** for storing user data
* Building a **cleanly structured Spring Boot app**
* Using **RestTemplate** to fetch data from an external API
* **Dockerize** the full stack using multistage builds and Docker Compose
* Externalizing configs using a `.env` file

This is a complete **end-to-end full-stack backend project**. 

---

## 📦 Functionalities

* Allows user **registration** and **login**
* Uses **JWT tokens** to protect endpoints
* Provides a `GET /api/quotes/random` endpoint that:
    * Fetches a **random quote** from the external API: [https://dummyjson.com/quotes/random](https://dummyjson.com/quotes/random)
    * Returns the quote to the user
* Uses **MongoDB Atlas** to persist users
* Differentiates users by role (`USER`, `ADMIN`, `MODERATOR`, `SUPER_ADMIN`)
* Is fully **Dockerized** with Dockerfile and Docker Compose
* Loads configurations via `.env`

## Dependencies

* Spring Web
* Spring Security
* Spring Data MongoDB
* Lombok
* Spring Boot DevTools
* Validation