# 🚀 Insider Careers Automation Tests

This project contains automated tests for **Insider Careers** using **Selenium**, **TestNG**, and **ExtentReports**.  
It supports both **local execution** and **Docker-based execution** with Selenium containers.

---

## 🛠️ Tech Stack
- Java 17
- Maven 3.8.5
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

Maven 3.8.5

Chrome / Edge browsers

Run all tests with default browser

```
mvn clean test

```
Run with a specific browser
```
mvn clean test -Dbrowser=chrome
mvn clean test -Dbrowser=edge
mvn clean test -Dbrowser=firefox
```
### Reports

ExtentReports → reports/

TestNG default → target/surefire-reports

## 🐳 Running Tests with Docker
This setup runs Selenium + TestNG + ExtentReports tests inside Docker containers.

### 1. First build and run tests
```
docker-compose up --build --exit-code-from test-runner --abort-on-container-exit
```
First run may take some time as Maven dependencies are downloaded.

ExtentReports HTML report is generated in ./reports/

Logs is generated in ./logs/

### 2. Re-run tests (if containers are already built)
```
docker-compose run --rm test-runner
```
### 3. Viewing Tests on Localhost
The selenium-chrome container is not headless and supports VNC.

Selenium Grid UI: http://localhost:4444

To watch Chrome in real time: Click the camera icon (VNC) in the Grid UI.
```
Username: selenium
Password: secret
```
### 4. Reports in Docker
Reports are saved to mounted volumes:

ExtentReports → ./reports/InsiderTestReport_<timestamp>.html




