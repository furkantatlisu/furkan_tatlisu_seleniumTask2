# 🚀 Insider Careers Automation Tests

This project contains automated tests for **Insider Careers** using **Selenium**, **TestNG**, and **ExtentReports**.  
It supports both **local execution** and **Docker-based execution** with Selenium containers.

---

## 🛠️ Tech Stack
- Java 17
- Maven
- Selenium 4
- TestNG
- ExtentReports
- Log4j2
- Docker + Docker Compose

---

## 📂 Key Files & Folders
- `src/test/java/tests/` → BaseTest and test classes
- `src/main/java/utils/` → DriverFactory, ConfigReader, utilities
- `src/test/resources/config.properties` → Test configuration
- `reports/` → ExtentReports output (HTML)
- `pom.xml` → Maven dependencies & build config
- `Dockerfile` → Test runner container definition
- `docker-compose.yml` → Selenium + test runner setup

---

## 📄 Configurations

Default configuration is in **`src/test/resources/config.properties`**:

```properties
base.url=https://useinsider.com
browser=chrome
timeout=20
qualityAssurance.url=https://useinsider.com/careers/quality-assurance/
```

## ▶️ Running Tests Locally
Install dependencies

Java 17

Maven

Chrome / Edge browsers

Run all tests

```
mvn clean test

```
Run with a specific browser
```
mvn clean test -Dbrowser=chrome

```
### Reports

ExtentReports → reports/

TestNG default → target/surefire-reports

## 🐳 Running Tests with Docker
This setup uses Selenium standalone Chrome inside Docker.

### 1. Build & run everything
```
docker-compose up --build --exit-code-from test-runner --abort-on-container-exit
```


### 3. Reports in Docker
Reports are saved to mounted volumes:

ExtentReports → ./reports/InsiderTestReport_<timestamp>.html

TestNG reports → ./target/surefire-reports




