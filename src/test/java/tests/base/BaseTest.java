package tests.base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import listeners.ReportListener;
import pages.LandingPage;
import pages.LoginPage;
import utils.ConfigReader;
import utils.DriverFactory;
import utils.LoggingManager;
import utils.ScreenshotUtil;

//BaseTest.java
public class BaseTest {

	protected static String browserName;
	protected static String browserMode;
	protected static String baseUrl;
	protected static String validUsername;
	protected static String validPassword;
	protected static DriverFactory driverFactory;
	
	protected LandingPage landingPage;
	protected WebDriver driver;
	
	@BeforeSuite
	public void suiteSetup() {
		
		LoggingManager.configureLogging();
		driverFactory = new DriverFactory();
		browserName = ConfigReader.getProperty("browser_name");
		browserMode = ConfigReader.getProperty("browser_mode");
		baseUrl = ConfigReader.getProperty("base_url");
		validUsername = ConfigReader.getProperty("valid_login_username");
		validPassword = ConfigReader.getProperty("valid_login_password");

	}

	@SuppressWarnings("null")
	@BeforeMethod
	@Parameters("browser") // Parameter from XML
	public void setup(@Optional String browser) {
		
		// Use the provided browser parameter, otherwise fallback to config file browser
		if (browser != null || !browser.isEmpty()) {
			browserName = browser; 
		}
		System.out.println("Running tests on browser: " + browser);
		LoggingManager.info("Running tests on browser: " +browser);

		driver = driverFactory.initDriver(browserName, browserMode);
		// Initialize pages
		LoginPage loginPage = new LoginPage(driver);
		loginPage.navigateTo(baseUrl);

		// Perform login
		landingPage = (LandingPage) loginPage.login(validUsername, validPassword);
		landingPage.waitForPageLoad();
	}

	@AfterMethod
	public void teardown() {
		DriverFactory.quitDriver();
    }
	
	
	// ----------------------
    // Step Logging Methods
    // ----------------------
    protected void logStep(String message) {
        ExtentTest extentTest = ReportListener.getTest();
        if (extentTest != null) {
            extentTest.log(Status.INFO, message);
        }
    }

    protected void logStepWithScreenshot(String message) {
        ExtentTest extentTest = ReportListener.getTest();
        if (extentTest != null && driver != null) {
            String screenshotPath = ScreenshotUtil.captureScreenshot(driver, "step_screenshot");
            try {
                extentTest.log(Status.INFO, message + "<br>Screenshot: " + 
                    extentTest.addScreenCaptureFromPath(screenshotPath));
            } catch (Exception e) {
                extentTest.log(Status.INFO, message + "<br>Failed to attach screenshot: " + e.getMessage());
            }
        } else {
            logStep(message);
        }
    }
}