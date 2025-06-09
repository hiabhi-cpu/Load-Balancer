
# Load Balancer (Spring Boot)

A simple load balancer implementation in Java using Spring Boot. It listens for HTTP requests, forwards them to backend servers, and returns the response to the client. Includes a basic backend server for testing, and supports forwarding to multiple instances.

## 🔧 Features

- Accepts incoming HTTP requests on a specified port
- Forwards requests to backend servers
- Returns the backend response to the original client
- Logs all incoming and forwarded requests
- Configurable backend endpoint
- Basic health check loop on server startup

## 📁 Project Structure

```bash
Load-Balancer/
├── backend/ # Simple backend Spring Boot server
└── loadbalancer/ # Load balancer service
```

## 🚀 Getting Started

### ✅ Prerequisites

- Java 17+
- Maven

---

## ⚙️ Running the Backend Servers

To simulate multiple backend nodes, run the **backend** application on multiple ports.

### 1. Open multiple terminals or use background processes:

#### Terminal 1: Run on port 8080

```bash
cd backend
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8080
```

#### Terminal 2: Run on port 8081
```bash
cd backend
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8081
```

#### Terminal 2: Run on port 8082
```bash
cd backend
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8082
```

#### Each backend server will return a message like:

```bash
Hello from Backend Server 8080
```

(You can customize the response in the controller based on the port.)

## 🖥️ Running the Load Balancer

#### Step 1: Go to the load balancer folder

```bash
cd loadbalancer
```

Step 2: Start the Load Balancer (e.g., on port 8000)

```bash
mvn spring-boot:run
```
The load balancer listens for requests and forwards them to one of the backend servers.

## 🔄 Load Balancing Logic
Currently supports basic round-robin forwarding. Each incoming request is sent to the next backend server in sequence:

```java
List<String> backends = List.of("http://localhost:8081", "http://localhost:8082", "http://localhost:8083");
```
You can customize this list and logic to implement:

- Weighted load balancing

- Health check aware routing

- Least response time