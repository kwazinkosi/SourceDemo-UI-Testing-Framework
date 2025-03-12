package utils;

import java.time.Duration;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

class WebDriverInitializationException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L; // Added to remove warning

	public WebDriverInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}

public class DriverFactory {

	public WebDriver driver;
	private static final ThreadLocal<WebDriver> tldriver = new ThreadLocal<>();

    private static final int IMPLICIT_WAIT_MS = 2000;

    private static final String HEADLESS_MODE = "headless";


    public WebDriver initDriver(String bName, String bMode) {
        
    	try {
            String browserName = bName;
            String browserMode = bMode;

            LoggingManager.info("Initializing " + browserName + " browser in " + browserMode + " mode");

            WebDriver webDriver = createWebDriver(browserName, browserMode);
            tldriver.set(webDriver); //It will create a copy of WebDriver for each thread

            configureDriver();
            return getDriver();

        } catch (Exception e) {
            LoggingManager.error("Failed to initialize WebDriver: " + e.getMessage(), e);
            throw new WebDriverInitializationException("Failed to initialize WebDriver", e);
        }
    }


    private static WebDriver createWebDriver(String browserName, String browserMode) {
        
    	switch (browserName) {
            case "chrome":
                return createChromeDriver(browserMode);
            case "firefox":
                return createFirefoxDriver(browserMode);
            case "edge":
                return createEdgeDriver(browserMode);
            default:
                throw new IllegalArgumentException("Unsupported browser type: " + browserName);
        }
    }

    private static WebDriver createChromeDriver(String browserMode) {
        
    	ChromeOptions options = new ChromeOptions();
        if (HEADLESS_MODE.equals(browserMode)) {
            options.addArguments("--headless", "--disable-gpu", "--ignore-certificate-errors", "--disable-features=PasswordChangeNotification",
            	    "--disable-component-update");
        }
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--disable-notifications");
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE); // Handle alerts
        return new ChromeDriver(options);
    }

    private static WebDriver createFirefoxDriver(String browserMode) {
        
    	FirefoxOptions options = new FirefoxOptions();
    	
        if (HEADLESS_MODE.equals(browserMode)) {
            options.addArguments("--headless", "-ignore-certificate-errors");
        }
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE); // Handle alerts
        return new FirefoxDriver(options);
    }

    private static WebDriver createEdgeDriver(String browserMode) {
        
    	EdgeOptions options = new EdgeOptions();
        if (HEADLESS_MODE.equals(browserMode)) {
            options.addArguments("--headless", "--disable-gpu", "--ignore-certificate-errors");
        }
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE); // Handle alerts
        return new EdgeDriver(options);
    }

    private static void configureDriver() {
        
    	try {
    		getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(IMPLICIT_WAIT_MS));
    		getDriver().manage().deleteAllCookies();
    		getDriver().manage().window().maximize();
            LoggingManager.info("WebDriver configured successfully");
        } catch (Exception e) {
            LoggingManager.error("Failed to configure WebDriver: " + e.getMessage(), e);
            throw new WebDriverInitializationException("Failed to configure WebDriver", e);
        }
    }

    public static synchronized WebDriver getDriver() {
        
        return tldriver.get();
    }

    public static void quitDriver() {
        
    	try {
            WebDriver currentDriver = getDriver();
            if (currentDriver != null) {
                currentDriver.quit(); // Close the browser
                LoggingManager.info("WebDriver quit successfully");
            }
        } catch (Exception e) {
            LoggingManager.error("Error while quitting WebDriver: " + e.getMessage(), e);
        } finally {
            tldriver.remove(); // Remove the driver from the thread
        }
    }
}