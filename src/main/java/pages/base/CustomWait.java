package pages.base;

import java.time.Duration;
import java.util.function.Function;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;


public class CustomWait {
    
	private final WebDriver driver;
    private static final Duration DEFAULT_POLLING = Duration.ofMillis(500);
    
    public CustomWait(WebDriver driver) {
        this.driver = driver;
    }
    
   
    
    public <T> T until(Function<WebDriver, T> condition, int timeoutInSeconds) {
    	
        return new FluentWait<>(driver)
            .withTimeout(Duration.ofSeconds(timeoutInSeconds))
            .pollingEvery(DEFAULT_POLLING)
            .ignoring(NoSuchElementException.class)
            .ignoring(StaleElementReferenceException.class)
            .ignoring(ElementNotInteractableException.class)
            .withMessage(() -> String.format("Timeout after %d seconds waiting for condition", timeoutInSeconds))
            .until(condition);
    }
    

    public void waitForPageLoad(int timeoutInSeconds) {
    	
        until(driver -> {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            return js.executeScript("return document.readyState").equals("complete");
        }, timeoutInSeconds);
    }
    
    
}