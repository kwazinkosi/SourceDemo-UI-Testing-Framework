package tests.base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import pages.LandingPage;
import pages.LoginPage;
import utils.ConfigReader;
import utils.DriverFactory;

public class BaseTest {
    protected WebDriver driver;
    
    protected String browserName;
    protected String browserMode;
    
    protected LandingPage landingPage;
    protected LoginPage loginPage;
	

    protected static final String validUsername = ConfigReader.getProperty("valid_login_username");
    protected static final String validPassword = ConfigReader.getProperty("valid_login_password");
    protected static final String LOGIN_PAGE_URL = ConfigReader.getProperty("base_url");

    protected DriverFactory driverFactory;
    @BeforeSuite
    public void suiteSetup() {
    	driverFactory = new DriverFactory();
    	browserName = ConfigReader.getProperty("browser_name");
    	browserMode = ConfigReader.getProperty("browser_mode");
    	
    }
    
    @BeforeMethod
    public void setup() {
        driver = driverFactory.initDriver(browserName, browserMode);
        
        loginPage = new LoginPage(driver);
		loginPage.navigateTo(LOGIN_PAGE_URL);
		
		landingPage = (LandingPage) loginPage.login(validUsername, validPassword);
		landingPage.waitForPageLoad();
    }
    
    @AfterMethod
    public void teardown() {
        if(driver != null) {
            driver.quit();
        }
    }
    
}
