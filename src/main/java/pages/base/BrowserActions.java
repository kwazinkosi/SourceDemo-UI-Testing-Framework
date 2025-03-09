package pages.base;

import org.openqa.selenium.WebDriver;

import utils.LoggingManager;

public class BrowserActions {
    private final WebDriver driver;

    public BrowserActions(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateTo(String url) {
        try {
            driver.get(url);
            LoggingManager.info("Navigated to URL:"+ url);
        } catch (Exception e) {
            LoggingManager.error("Failed to navigate to URL: "+ url, e);
            throw e;
        }
    }

    public void refreshPage() {
        try {
            driver.navigate().refresh();
            LoggingManager.info("Page refreshed successfully");
        } catch (Exception e) {
            LoggingManager.error("Failed to refresh page", e);
            throw e;
        }
    }
}
