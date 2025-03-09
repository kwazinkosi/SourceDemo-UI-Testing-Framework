package tests.base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import pages.LandingPage;
import pages.LoginPage;
import utils.ConfigReader;
import utils.DriverFactory;

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
		// Load config ONCE but keep as static variables
		driverFactory = new DriverFactory();
		browserName = ConfigReader.getProperty("browser_name");
		browserMode = ConfigReader.getProperty("browser_mode");
		baseUrl = ConfigReader.getProperty("base_url");
		validUsername = ConfigReader.getProperty("valid_login_username");
		validPassword = ConfigReader.getProperty("valid_login_password");

	}

	@BeforeMethod
	public void setup() {

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
	
//        if(driver != null) {
//            driver.quit();
//            driver = null; // Prevent stale references
//        }
    }
}