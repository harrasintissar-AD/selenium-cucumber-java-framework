# Selenium Cucumber Java Automation Framework

<!-- Optional GitHub Actions Badge -->

<!-- Replace YOUR_USERNAME and YOUR_REPOSITORY -->

![CI](https://github.com/harrasintissar-AD/selenium-cucumber-java-framework/blob/main/.github/workflows/automation-tests.yml)

## Overview

A custom-built QA Automation Framework developed using **Java**, **Selenium WebDriver**, **Cucumber BDD**, and **Maven**.

This project demonstrates industry-standard automation testing practices including:

* Page Object Model (POM)
* Thread-safe WebDriver management using ThreadLocal
* Event-driven screenshot capture
* Custom HTML reporting
* Element highlighting for improved visual reporting
* Configuration-driven test execution
* Dockerized ParaBank test environment
* GitHub Actions CI/CD integration
* Automated report artifact publishing

The framework was designed to provide maintainable, scalable, and readable automated tests while generating detailed execution evidence for debugging and analysis.

---

## Technology Stack

* Java 17
* Selenium WebDriver
* Cucumber BDD
* TestNG
* Maven
* WebDriverManager
* Docker
* GitHub Actions
* Git & GitHub

---

## Key Features

### Test Automation

* Page Object Model (POM) architecture
* Reusable page actions and utilities
* Configuration-driven execution
* Support for headless execution
* Clean separation of framework components

### Reporting

* Custom HTML report generation
* Per-step screenshots
* Embedded Base64 screenshots
* Self-contained reports
* Scenario execution status
* Highlighted UI elements in screenshots

### Framework Design

* Thread-safe WebDriver management
* Event-based screenshot capture using Cucumber listeners
* Automatic cleanup of previous execution artifacts
* Modular and maintainable project structure

### DevOps & CI/CD

* Automated GitHub Actions workflow
* Dockerized ParaBank deployment
* Maven dependency caching
* Automated artifact publishing
* Linux-based test execution

---

## Project Architecture

### Driver Management

`DriverFactory`

Responsible for:

* WebDriver initialization
* Browser configuration
* Thread-safe execution using `ThreadLocal<WebDriver>`
* Driver lifecycle management

### Configuration Management

`ConfigReader`

Responsible for:

* Loading framework configuration
* Reading environment-specific properties
* Managing execution settings

### Test Lifecycle

`Hooks`

Responsible for:

* Test setup
* Test teardown
* Report initialization
* Execution artifact cleanup

### Step Event Processing

`StepListener`

Responsible for:

* Listening to Cucumber step events
* Capturing screenshots after each executed step
* Sanitizing filenames
* Forwarding screenshots to the reporting engine

### Reporting

`ReportManager`

Responsible for:

* HTML report generation
* Screenshot embedding
* Scenario status reporting
* Report file creation

### Utilities

#### ScreenshotUtil

Captures full-page screenshots and stores them in the framework output directories.

#### HighlightUtil

Highlights web elements before screenshot capture to improve report readability and debugging.

---

## Project Structure

```text
src
├── main
│   └── java
│       ├── driver
│       │   └── DriverFactory.java
│       │
│       ├── pages
│       │   ├── LoginPage.java
│       │   └── RegisterPage.java
│       │
│       └── utils
│           ├── ConfigReader.java
│           └── HighlightUtil.java
│
├── test
│   └── java
│       ├── hooks
│       │   ├── Hooks.java
│       │   └── StepListener.java
│       │
│       ├── reports
│       │   └── ReportManager.java
│       │
│       ├── runners
│       │   ├── LoginTest.java
│       │   ├── SmokeTest.java
│       │   └── TestRunner.java
│       │
│       ├── stepdefinitions
│       │   ├── LoginSteps.java
│       │   └── RegisterSteps.java
│       │
│       └── utils
│           ├── ScreenshotUtil.java
│           └── TestUserStorage.java
│
└── resources
    ├── features
    └── config
```

---

## Automated Test Scenarios

### Registration

* Customer registration using valid information
* Validation of successful registration message
* Verification of account creation flow

### Login

* Customer login using valid credentials
* Verification of successful authentication
* Validation of account overview page

More scenarios can easily be added following the Page Object Model structure.

---

## Reporting

The framework generates a dedicated HTML report for each executed scenario.

### Report Features

* Scenario execution status
* Step-by-step screenshots
* Embedded Base64 images
* Portable HTML files
* Improved readability through element highlighting

### Report Locations

```text
target/
├── execution-report/
│   ├── Customer_logs_in_with_valid_credentials.html
│   └── Customer_successfully_registers_a_new_account.html
│
└── screenshots/
```

---

## Test Environment

Tests are executed against a Dockerized ParaBank application, providing:

* Consistent test environments
* Easy setup
* Repeatable execution
* Reduced environment-related issues

---

## CI/CD Pipeline

The framework includes a GitHub Actions workflow that automatically executes the Selenium test suite whenever changes are pushed to the `main` branch.

### Pipeline Capabilities

* Automatic test execution
* Java 17 environment setup
* Maven dependency caching
* Dockerized ParaBank deployment
* Automated Selenium execution
* HTML report generation
* Execution report artifact publishing
* Automatic environment cleanup

### Pipeline Flow

1. Checkout repository
2. Configure Java 17
3. Restore Maven cache
4. Pull ParaBank Docker image
5. Start ParaBank container
6. Verify application availability
7. Execute Selenium Cucumber tests
8. Generate HTML execution reports
9. Upload reports as GitHub Actions artifacts
10. Stop and remove Docker containers

### Workflow Location

```text
.github/workflows/automation-test.yml
```

After every successful or failed execution, generated reports can be downloaded directly from the GitHub Actions workflow artifacts.

---

## Running the Tests

Execute the entire suite:

```powershell
mvn test
```

Run with custom properties:

```powershell
mvn test -Dbrowser=chrome -Dheadless=true
```

Example configuration:

```properties
browser=chrome
headless=false
base.url=http://localhost:8080/parabank
```

---

## Automation Best Practices Demonstrated

* Page Object Model (POM)
* Thread-safe parallel execution
* Configuration-driven execution
* Event-driven reporting
* Separation of concerns
* Reusable utilities
* Self-contained execution reports
* Automated screenshot capture
* CI/CD integration with GitHub Actions
* Docker-based environment provisioning
* Automated artifact publishing
* Maintainable project architecture

---

## DevOps Features

The framework demonstrates modern QA automation practices by integrating test execution into a CI/CD pipeline.

Implemented capabilities include:

* GitHub Actions workflow automation
* Dockerized application deployment
* Automated test execution on Ubuntu runners
* Maven dependency caching
* Automatic execution report publishing
* Environment cleanup after execution

These features enable repeatable, reliable, and scalable automated testing without requiring manual environment setup.

---

## Future Enhancements

* API automation module
* Jenkins integration
* Parallel test execution
* Multi-environment support (QA/UAT/PROD)
* Cross-browser execution in CI
* Allure or ExtentReports integration
* Data-driven testing
* Environment profiles

---

## Skills Demonstrated

* Java Automation Development
* Selenium WebDriver
* Cucumber BDD
* Test Framework Design
* HTML Report Generation
* Maven Build Management
* Docker
* GitHub Actions CI/CD
* Linux-Based Test Execution
* Automated Artifact Management
* Git & GitHub
* Software Testing Best Practices
* QA Automation Architecture

---

## Execution Report Sample

> ![user_registers_with_valid_details_STEP_2.png](target/screenshots/user_registers_with_valid_details_STEP_2.png)
> ![user_should_see_registration_successful_message_STEP_4.png](target/screenshots/user_should_see_registration_successful_message_STEP_4.png)

Suggested screenshots:

* Registration execution report
* Login execution report
* Highlighted success message screenshot
* GitHub Actions successful pipeline run

---

## Author

**Intissar Harras**

QA Automation Engineer specializing in:

* Java
* Selenium WebDriver
* Cucumber BDD
* API Testing
* Test Automation Framework Design

This project was created as part of my professional QA Automation portfolio and demonstrates practical experience building automation frameworks from scratch, implementing custom reporting solutions, Dockerized test environments, and CI/CD automation using GitHub Actions.
