package pages;

import components.*;
import constants.WaitTime;
import pages.base.BrowserActions;
import pages.base.CustomWait;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import utils.LoggingManager;


public class BasePage {
	
    protected final WebDriver driver;
    protected final CustomWait customWait;
    protected final BrowserActions browserActions;
    
    // Common Components
    protected final SocialsComponent socials;
    protected final MenuComponent menu;
    protected final CartComponent cart;

    public BasePage(WebDriver driver) {
    	
        this.driver = driver;
        this.customWait = new CustomWait(driver);
        this.browserActions = new BrowserActions(driver); 
        this.socials = new SocialsComponent(driver);
        this.menu = new MenuComponent(driver);
        this.cart = new CartComponent(driver);
        PageFactory.initElements(driver, this);
    }

    // Common Component Methods
    public SocialsComponent getSocials() {
        
    	return socials;
    }

    public MenuComponent getMenu() {
        
    	return menu;
    }

    public CartComponent getCart() {
        
    	return cart;
    }
    
    public void navigateTo(String url) {
    	browserActions.navigateTo(url);
    }
    
    protected void clickElement(WebElement element) {
        
    	executeWithLogging(element, "click", () -> {
            customWait.until(ExpectedConditions.elementToBeClickable(element), WaitTime.NORMAL);
            element.click();
            checkForAlerts();
        });
    }

    protected void sendKeys(WebElement element, String text) {
       
    	executeWithLogging(element, "send keys to", () -> {
            customWait.until(ExpectedConditions.elementToBeClickable(element), WaitTime.NORMAL);
            element.clear();
            element.sendKeys(text);
        });
    }

    // Improved Alert Handling
    private void checkForAlerts() {
        
    	try {
            Alert alert = customWait.until(ExpectedConditions.alertIsPresent(), WaitTime.NORMAL);
            String alertText = alert.getText();
            LoggingManager.info("Alert detected: " + alertText);
            alert.accept();
        } catch (TimeoutException e) {
            // No alert present
        }
    }

    // Enhanced Verification Methods
    protected boolean isElementDisplayed(WebElement element) {
        
    	try {
            customWait.until(ExpectedConditions.visibilityOf(element), WaitTime.NORMAL);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    
    public void waitForPageLoad() {
    	
        customWait.until(driver -> 
            ((JavascriptExecutor) driver).executeScript("return document.readyState")
                .equals("complete"),
            WaitTime.SLOW
        );
    }

    public void waitForElementStale(WebElement element) {
    	
    	try {
            
            customWait.until(ExpectedConditions.stalenessOf(element), WaitTime.NORMAL);
        } catch (StaleElementReferenceException e) {
            // If the element is already stale, it means the page has updated, so it's fine.
        	System.out.println("the page has updated");
        }
    }
    
    public boolean isPageDisplayed() {
        
    	return customWait.until(d -> !d.getTitle().isEmpty() && 
            !d.getTitle().contains("Error"), 
            WaitTime.NORMAL
        );
    }

    // Utility Methods
    protected void executeWithLogging(WebElement element, String action, Runnable operation) {
        
    	String elementInfo = element.toString();
        try {
            LoggingManager.debug("Attempting to "+action+" element: " +elementInfo);
            operation.run();
            LoggingManager.info("Successfully performed "+action+" on element: "+elementInfo);
        } catch (Exception e) {
            LoggingManager.error("Failed to "+action+" element: "+ elementInfo+" "+ e.getMessage());
            throw e;
        }
    }

    public String getText(WebElement element) {
    	return element.getText();
    }
    
	public String getPageTitle() {
		
		return driver.getTitle();
	}
}