package tests.base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import utils.ConfigReader;
import utils.DriverFactory;

public class BaseTest {
    protected WebDriver driver;
    
    private String browserName;
    private String browserMode;
    DriverFactory driverFactory;
    @BeforeSuite
    public void setup() {
    	driverFactory = new DriverFactory();
    	browserName = ConfigReader.getProperty("browser_name");
    	browserMode = ConfigReader.getProperty("browser_mode");
    	
    }
    
    @BeforeMethod
    public void initDriver() {
        driver = driverFactory.initDriver(browserName, browserMode);
    }
    
    @AfterMethod
    public void teardown() {
        if(driver != null) {
            driver.quit();
        }
    }
    
}
