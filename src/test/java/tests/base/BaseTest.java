package tests.base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.LandingPage;
import pages.LoginPage;
import utils.ConfigReader;
import utils.DriverFactory;

public class BaseTest {
    protected WebDriver driver;
    protected LandingPage landingPage;
    protected LoginPage loginPage;

    @BeforeMethod
    public void setup() {
        // Initialize fresh driver for each test
        String browserName = ConfigReader.getProperty("browser_name");
        String browserMode = ConfigReader.getProperty("browser_mode");
        driver = new DriverFactory().initDriver(browserName, browserMode);
        
        // Initialize fresh page objects
        loginPage = new LoginPage(driver);
        landingPage = new LandingPage(driver);
        
        // Perform login
        loginPage.navigateTo(ConfigReader.getProperty("base_url"));
        landingPage = (LandingPage) loginPage.login(
            ConfigReader.getProperty("valid_login_username"),
            ConfigReader.getProperty("valid_login_password")
        );
        landingPage.waitForPageLoad();
    }

    @AfterMethod
    public void teardown() {
        if(driver != null) {
            driver.quit();
            driver = null; // Prevent stale references
        }
    }
}