# Selenium Cucumber Java Automation Framework

A scalable and maintainable UI automation testing framework built using **Java, Selenium WebDriver, Cucumber (BDD), Maven, and TestNG**, following industry best practices such as **Page Object Model (POM)** and **Behavior Driven Development (BDD)**.

This framework includes **CI/CD integration using GitHub Actions**, automatic screenshot capture after every step, and detailed HTML execution reporting for easier debugging and stakeholder visibility.

---

## 🚀 Features

* ✅ Selenium WebDriver for browser automation
* ✅ Java-based automation framework
* ✅ Cucumber BDD with Gherkin syntax
* ✅ Page Object Model (POM) design pattern
* ✅ TestNG execution and reporting
* ✅ Maven dependency management
* ✅ GitHub Actions CI/CD integration
* ✅ Automatic screenshot capture after every step
* ✅ Custom HTML execution reports
* ✅ Headless browser execution support
* ✅ Cross-environment execution support

---

## 🛠 Tech Stack

* **Java 17**
* **Selenium WebDriver**
* **Cucumber (BDD)**
* **TestNG**
* **Maven**
* **WebDriverManager**
* **Git & GitHub**
* **GitHub Actions**
* **Eclipse IDE**

---

## 📁 Project Structure

```text
src
├── main
│   ├── java
│   │   ├── driver
│   │   │   └── DriverFactory.java
│   │   │
│   │   └── pages
│   │       └── LoginPage.java
│   │
│   └── resources
│
├── test
│   ├── java
│   │   ├── hooks
│   │   ├── reports
│   │   ├── runners
│   │   ├── stepDefinitions
│   │   └── utils
│   │
│   └── resources
│       ├── config
│       └── features
│
├── screenshots
├── target
└── .github
    └── workflows
```

### Structure Overview

* **driver** → WebDriver initialization and browser configuration
* **pages** → Page Object Model (POM) classes
* **hooks** → Cucumber hooks (`@Before`, `@After`)
* **reports** → Custom HTML execution reporting logic
* **runners** → Test execution configuration
* **stepDefinitions** → Cucumber step implementations
* **utils** → Reusable helper/utility classes
* **features** → Gherkin feature files
* **config** → Environment/configuration files
* **screenshots** → Automatically captured screenshots during execution
* **.github/workflows** → GitHub Actions CI/CD pipeline configuration

---

## ▶️ How to Run the Project

### Clone Repository

```bash
git clone https://github.com/harrasintissar-AD/selenium-cucumber-java-framework.git
```

### Navigate to Project

```bash
cd selenium-cucumber-java-framework
```

### Run Tests

```bash
mvn clean test
```

---

## ⚙️ Headless Execution

For CI/CD execution, Chrome runs in **headless mode**.

Enable/disable headless mode in:

```text
DriverFactory.java
```

Example:

```java
options.addArguments("--headless=new");
```

Comment the line to execute tests in normal browser mode.

---

## 📊 Reporting

The framework generates detailed execution reports automatically.

### HTML Execution Report

Contains:

* Scenario execution results
* Step-by-step screenshots
* Pass/Fail execution status
* Visual debugging support

Generated at:

```text
target/execution-report/
```

### Screenshots

Screenshots are automatically captured **after every test step** to improve debugging and provide visual context.

Generated at:

```text
screenshots/
```

---

## 🔄 CI/CD Integration

This project includes a **GitHub Actions pipeline** that:

* Automatically runs tests on every push to `main`
* Supports manual execution using `workflow_dispatch`
* Executes tests in headless mode
* Uploads execution artifacts
* Generates reports and screenshots automatically


---

## 👩‍💻 Author

**Intissar Harras_Software Automation Test Engineer**

Specialized in:

**Java | Selenium | Cucumber | API Testing | Test Automation | CI/CD**
