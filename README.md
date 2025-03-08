# SourceDemo-UI-Testing-Framework

This is a Selenium-based Page Object Model (POM) framework designed for automated testing of sourcedemo.com

## Prerequisites

Before you begin, ensure you have the following installed on your system:

- **Java Development Kit (JDK) 11 or above**
- **Apache Maven 3.6.0 or above**
- **Git**

## Installation

Follow the steps below to clone and set up the project on your local machine.

### 1. Clone the Repository

Open your terminal or command prompt and run the following command:

```sh
git clone https://github.com/kwazinkosi/SourceDemo-UI-Testing-Framework.git
```
### 2. Navigate to the Project Directory
Once the repository is cloned, navigate to the project's root directory:

```sh
cd SourceDemo
```
### 3. Open the Command Line
Ensure you're in the project's root directory, where the pom.xml file is located.
- Run the following commands.


### 4. Run Tests
You can run the test suite using Maven by executing the following command:

```sh
mvn test
```
### Running in eclipse
#### Adding Jars in Eclipse

- Right-click on the project and select Properties.
- Go to Java Build Path.
- Click the Libraries tab.
- Click Add JARs or Add External JARs and select the JARs from the libs folder.
- Apply and close the dialog.
- Run as testNG or click on drodown of the run button and then select "takealot-automation_test.xml"

## Project Structure


```plaintext
SourceDemo/
│
├── src/main/java
│   ├── components
│   │   ├── BaseComponent.java
│   │   ├── MenuComponent.java
│   │   ├── ProductComponent.java
│   │   ├── CartComponent.java
│   │   └── SocialsComponent.java
│   │
│   ├── config
│   │   └── ConfigReader.java
│   │
│   ├── interfaces
│   │   └── 
│   │
│   ├── pages
│   │   ├── BasePage.java
│   │   ├── LandingPage.java
│   │   ├── CartPage.java
│   │   ├── ProductDetailsPage.java
│   │   ├── CheckoutOverviewPage.java
│   │   ├── CheckoutCompletePage.java
│   │   ├── LoginPage.java
│   │   └── CheckoutPage.java
│   │
│   └── utils
│       ├── ConfigReader.java
│       ├── LoggingManager.java
│       ├── DriverFactory.java
│       └── ScreenshotUtil.java
│
├── src/main/test/resources/data/
│   └── testdata.xlsx
│
├── src/test/java
│   └── tests/
│       ├── BasePageTest.java
│       ├── LandingPage.java
│       ├── CartPageTest.java
│       ├── ProductDetailsPageTest.java
│       ├── CheckoutOverviewPageTest.java
│       ├── CheckoutCompletePageTest.java
│       ├── LoginPageTest.java
│       └── CheckoutPageTest.java
│
├── logs/
│   └── soucerdemo.log
│
├── reports
│
├── screenshots
│
├── config.properties
│
│
├── log4j2.xml
│
├── pom.xml
│
├── readme.md
│
└── test.xml
 
 ```

### Configuration
- **Test Data:** Located in the src/test/resources directory.
- **Config Files:** Configuration files like config.properties are located in the SourceDemo/ directory.

### Logging
Logging is managed by LoggingManager, which outputs logs to both the console and a file in the logs/ directory.

### Reporting
Test reports include Extent Reports and default reports generated by the Surefire plugin. They can be found in the reports and the target/surefire-reports directories, respectively, after test execution.

### Troubleshooting
If you encounter any issues:

Ensure all dependencies are correctly installed.
Check the log files in the logs/ directory for detailed error messages.


