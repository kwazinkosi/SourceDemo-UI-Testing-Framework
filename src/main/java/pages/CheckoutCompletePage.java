package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import constants.WaitTime;

public class CheckoutCompletePage extends BasePage {

    @FindBy(xpath = "//span[@class='title']")
    private WebElement pageTitle;
    
    @FindBy(xpath = "//h2[@class='complete-header']")
    private WebElement completionHeader;
    
    @FindBy(id = "back-to-products")
    private WebElement backHomeButton;

    public static final String SUCCESS_MESSAGE = "Thank you for your order!";
    public static final String COMPLETION_URL_FRAGMENT = "checkout-complete.html";

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isPageDisplayed() {
        return customWait.until(d -> 
            d.getCurrentUrl().contains(COMPLETION_URL_FRAGMENT) &&
            isElementDisplayed(completionHeader) &&
            getPageTitle().equalsIgnoreCase("Checkout: Complete!"),
            WaitTime.NORMAL
        );
    }

    public LandingPage navigateBackToHome() {
        executeWithLogging(backHomeButton, "Navigate back to home", () -> {
            clickElement(backHomeButton);
            waitForPageLoad();
        });
        return new LandingPage(driver);
    }

    public String getSuccessMessage() {
        return getText(completionHeader);
    }

    public boolean isOrderSuccessful() {
        return getSuccessMessage().equals(SUCCESS_MESSAGE);
    }

    public String getPageTitle() {
        return getText(pageTitle);
    }
}