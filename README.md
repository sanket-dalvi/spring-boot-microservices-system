
---

# **Spring Boot Microservices System with Eureka, API Gateway, Load Balancer, and Docker**

## **Overview**

This repository showcases a **Spring Boot Microservices architecture** built using modern cloud-native patterns. It features:

- **Service Discovery** via **Eureka**
- **API Gateway** using **Spring Cloud Gateway**
- **Load Balancing** with **Spring Cloud LoadBalancer**
- **Containerization** with **Docker** and **Docker Compose**

The primary goal of this project is to demonstrate how to structure and deploy microservices in a **scalable**, **resilient**, and **maintainable** way. Rather than just focusing on individual REST API creation, the system shows how all components in a **microservices ecosystem** work together to build a complete application with robust routing, dynamic service discovery, and fault tolerance.

### **Core Components**

The system consists of the following services:

1. **Eureka Server**: A **Service Registry** for all microservices to register and discover each other.
2. **API Gateway (Spring-Cloud API Gateway)**: Routes incoming client requests to appropriate microservices. It provides a single entry point into the system, reducing complexity for consumers.
3. **Product Quote Service**: Handles all operations related to generating product quotes.
4. **Order Service**: Manages order creation and processing.
5. **Document Generation Service**: Responsible for generating documents associated with orders.

### **Why Build This System?**

Spring Boot and microservices are often discussed in the context of creating simple REST APIs. However, building a **real-world application** with Spring Boot and microservices requires understanding the **complete ecosystem** that includes service discovery, API routing, load balancing, and maintaining a scalable architecture.

The main goal of this project is to:

- Show how to **integrate** various components such as **Spring Cloud Eureka**, **Spring Cloud Gateway**, and **Spring Cloud LoadBalancer** into a **distributed architecture**.
- Build a **scalable and maintainable system** with the ability to handle increasing loads by adding more instances of each microservice.
- Use **Docker** and **Docker Compose** to easily set up, run, and scale services in isolated environments.

---

## **System Architecture**

Below is a high-level diagram that illustrates the communication and flow between different components in the system:

![alt text](<system design.svg>)

### **Component Details:**

1. **Eureka Server (Service Registry)**:
   - Each microservice registers itself with **Eureka** at startup.
   - Services can dynamically discover each other using the Eureka registry.

2. **API Gateway (Spring-Cloud API Gateway)**:
   - The **API Gateway** acts as the entry point for external requests. All incoming traffic first reaches the **API Gateway**, which routes it to the appropriate microservice.
   - The **API Gateway** uses **Spring Cloud Gateway** and **Spring Cloud LoadBalancer** to route requests and balance traffic across multiple instances of microservices.

3. **Microservices**:
   - **Product Quote Service**: Exposes endpoints for retrieving product quotes.
   - **Order Service**: Exposes endpoints to manage orders.
   - **Document Generation Service**: Generates documents such as invoices or reports.

---

## **Technologies Used**

- **Spring Boot**: A Java-based framework to build microservices.
- **Spring Cloud**: For distributed systems infrastructure (including Eureka for service discovery, Gateway for API routing, and Load Balancer for distributing traffic).
- **Docker**: For containerization of microservices, allowing them to run in isolated environments.
- **Gradle**: A build automation tool to manage dependencies and build the microservices.
- **Spring Cloud LoadBalancer**: For client-side load balancing.
- **Spring Cloud Eureka**: A service registry for microservices to register and discover each other.

---

## **How to Run the System**

### **Step 1: Pre-requisites**

Before running the system, ensure you have the following:

- **Docker Desktop** installed and running.
- **Docker Compose** for orchestrating multi-container applications.
- **Java 23** and **Gradle** for building and running Spring Boot applications.

Make sure you have **Docker Desktop** running and accessible. You can verify this by running:

```bash
docker --version
docker-compose --version
```
If you don't have **Gradle** installed globally, don't worry. We use Gradle Wrapper (`gradlew`) to build the services, which does not require a global Gradle installation.

### **Step 2: Build the Services**

Each microservice needs to be built before running. Navigate to the individual service directory and build it using **Gradle**:

- For **Windows**:
  - Open a command prompt (CMD) or PowerShell and run:
    ```bash
    gradlew.bat build
    ```

- For **Mac/Linux**:
  - Open a terminal and run:
    ```bash
    ./gradlew build
    ```

This will create the necessary artifacts for each service (JAR files).

### **Step 3: Start the System with Docker Compose**

Clone the repository and navigate to the project directory where the `docker-compose.yml` file is located. Then, execute the following command to build and run the containers:

- For **Windows**:
  - Open a command prompt (CMD) or PowerShell in the project directory and run:
    ```bash
    docker-compose up --build
    ```

- For **Mac/Linux**:
  - Open a terminal in the project directory and run:
    ```bash
    docker-compose up --build
    ```

The command will build the Docker images for all services and bring them up, including:

- **Eureka Server** at `http://localhost:8761`
- **API Gateway** at `http://localhost:8080` (API Gateway will route requests to the relevant microservices)
- **Product Quote Service** at `http://localhost:8081`
- **Order Service** at `http://localhost:8086`
- **Document Generation Service** at `http://localhost:8091`

### **Step 4: Access Eureka Dashboard**

Once the services are running, you can access the Eureka Dashboard, which displays the registered services, at:

```
http://localhost:8761/eureka/apps
```

This will show a list of all registered services, such as `api-gateway`, `product-quote-service`, `order-service`, and `docgen-service`.

---

### **Platform-Specific Details**

1. **Windows**:
   - Use `gradlew.bat` for building the services and running the project.
   - You can open **CMD** or **PowerShell** to execute the commands.
   - Ensure that Docker and Docker Compose are properly installed and Docker Desktop is running.

2. **Mac/Linux**:
   - Use `./gradlew` for building the services and running the project.
   - Ensure that Docker and Docker Compose are properly installed and Docker Desktop is running.
   - You can use the **Terminal** to execute the commands.

---

### **Additional Tips for Running Docker Compose on Windows:**

- Ensure Docker is running with **Linux containers**, not Windows containers. You can switch this in Docker Desktop settings.
- If you're running into issues with **file permissions** or mounting volumes, make sure that Docker has permission to access the file system correctly on Windows.

---

## **Important Configuration Properties**

Each service has a basic configuration to integrate with Eureka, define the server port, and handle logging.

### **API Gateway Configuration**:

```properties
spring.application.name=api-gateway
server.port=8080

eureka.client.service-url.defaultZone=http://localhost:8761/eureka/

# API Gateway Routes Configuration
spring.cloud.gateway.routes[0].id=product-quote-service
spring.cloud.gateway.routes[0].uri=lb://product-quote-service
spring.cloud.gateway.routes[0].predicates[0]=Path=/product/**
spring.cloud.gateway.routes[0].filters[0]=RewritePath=/product/(?<segment>.*), /$\{segment}

# Logging configuration
logging.level.root=INFO
logging.file.name=logs/api-gateway.log
```

### **Service Configuration (Example for Product Quote Service)**:

```properties
spring.application.name=product-quote-service
server.port=8081

eureka.client.service-url.defaultZone=http://localhost:8761/eureka/

spring.cloud.loadbalancer.enabled=true

# Logging configuration
logging.level.root=INFO
logging.file.name=logs/product-quote-service.log
```

---

## **Scaling the System**

### **Without Scaling (Single Instance per Service)**

By default, when running services through **Docker Compose**, each service runs in a single instance. To run the system normally, execute:

```bash
docker-compose up --build
```

This will start all the containers with one instance of each service. The **API Gateway** will route requests to the respective services using **Spring Cloud Gateway** and **Spring Cloud LoadBalancer**.

### **With Scaling (Multiple Instances per Service)**

To test **load balancing** across multiple instances of a service, you can scale individual services using the `--scale` option in Docker Compose.

For example, to run **3 instances** of the `product-quote-service`, use the following command:

```bash
docker-compose up --scale product-quote-service=3
```

This will start **3 instances** of the `product-quote-service`, and **Spring Cloud LoadBalancer** will distribute the incoming requests among these instances.

### **Scaling Example:**

```bash
docker-compose up --scale product-quote-service=3 --scale order-service=2
```

This will create:

- **3 instances** of the `product-quote-service`.
- **2 instances** of the `order-service`.

You can use this approach to simulate load balancing and ensure the system scales as needed.

---

## **Challenges Faced**

### **Challenge 1: Incorrect API Gateway Routing Configuration**

Initially, the **API Gateway** was set up to route traffic to the microservices but was not properly handling the response due to missing path rewriting. The following configuration was used:

```properties
spring.cloud.gateway.routes[0].uri=lb://product-quote-service
spring.cloud.gateway.routes[0].predicates[0]=Path=/product/**
```

While the request was successfully routed to `product-quote-service`, the response failed, and I was seeing a **connection refused** error. This issue occurred because **Spring Cloud Gateway** was not rewriting the path correctly when forwarding the request.

### **Solution:**

The issue was resolved by adding the `RewritePath` filter, ensuring the path was correctly rewritten before sending it to the microservice.

```properties
spring.cloud.gateway.routes[0].filters[0]=RewritePath=/product/(?<segment>.*), /$\{segment}
```

By adding this configuration, the API Gateway correctly handled the response and forwarded it to the client.

---

## **Diagrams and Graphs**

To better understand the system, below are placeholders for various diagrams and images:

1. **Microservices Architecture**:  
   ![Microservices Architecture](path_to_architecture_diagram.png)

2. **Service Communication Flow**:  
   ![Service Communication Flow](path_to_service_communication_diagram.png)

3. **Docker Container Setup**:  
   ![Docker Containers](path_to_docker_container_diagram.png)

---

Absolutely! You can add these two questions and their answers in the **Key Learnings** or **Surprise Learnings** section, to explain why you initially considered Zuul and Ribbon but later switched to Spring Cloud Gateway and Spring Cloud LoadBalancer due to their advantages. Here’s a concise and focused version of the section to answer your questions:

---

### **Key Learnings**

#### **1. Why Did I Initially Consider Zuul and Ribbon?**
At the start of the project, **Zuul** and **Ribbon** were popular choices for API Gateway and load balancing in Spring Cloud applications. Both were widely used in microservices architectures for routing and client-side load balancing.

- **Zuul** was the go-to solution for API Gateway functionality, providing features like routing, security, and rate limiting.
- **Ribbon** was the standard for client-side load balancing, helping distribute traffic among different instances of services.

#### **2. Why Did I Switch to Spring Cloud Gateway and Spring Cloud LoadBalancer?**
However, as I explored newer Spring Cloud solutions, I discovered that **Spring Cloud Gateway** and **Spring Cloud LoadBalancer** offer better performance, scalability, and ease of use compared to Zuul and Ribbon. 

- **Spring Cloud Gateway** is the **preferred alternative to Zuul** because it is:
  - Non-blocking and **asynchronous**, offering better scalability.
  - **More modern**, with more flexible and powerful routing configurations.
  - Actively developed and **better integrated** with the Spring ecosystem.
  
- **Spring Cloud LoadBalancer** is a **simpler, more lightweight alternative to Ribbon**. It provides:
  - **Native support** in Spring Cloud with easy configuration and integration.
  - No complex setups or custom configurations, unlike Ribbon, which required additional complexity.
  - It’s also **actively maintained**, while Ribbon is in maintenance mode.

By opting for **Spring Cloud Gateway** and **Spring Cloud LoadBalancer**, I was able to ensure a more **modern, efficient, and scalable microservices system**. These solutions are well-suited for future-proofing applications and ensuring better performance and long-term support.

---

## **Conclusion**

This project demonstrates how to build and deploy a **Spring Boot-based microservices architecture** using modern cloud-native patterns such as **service discovery** with **Eureka**, **API

 Gateway** routing with **Spring Cloud Gateway**, and **load balancing** using **Spring Cloud LoadBalancer**. It also highlights the importance of **scalability** and how **Docker** and **Docker Compose** make it easy to manage multiple instances of services.

By following this approach, you can easily build, scale, and maintain large distributed systems using Spring Boot and Docker.

---
