package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import constants.WaitTime;

public class CheckoutPage extends BasePage {

    @FindBy(id = "first-name")
    private WebElement firstNameInput;
    
    @FindBy(id = "last-name")
    private WebElement lastNameInput;
    
    @FindBy(id = "postal-code")
    private WebElement postalCodeInput;

    @FindBy(id = "cancel")
    private WebElement cancelButton;
    
    @FindBy(id = "continue")
    private WebElement continueButton;
    
    @FindBy(xpath = "//h3[@data-test='error']")
    private WebElement errorMessage;

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isPageDisplayed() {
        return customWait.until(d -> 
            d.getCurrentUrl().contains("checkout-step-one.html") && 
            isElementDisplayed(firstNameInput),
            WaitTime.NORMAL
        );
    }

    public CheckoutPage enterShippingInformation(String firstName, String lastName, String postalCode) {
        
    	enterFirstName(firstName)
            .enterLastName(lastName)
            .enterPostalCode(postalCode);
        return this;
    }

    public CheckoutPage enterFirstName(String firstName) {
        sendKeys(firstNameInput, firstName);
        return this;
    }

    public CheckoutPage enterLastName(String lastName) {
        
    	sendKeys(lastNameInput, lastName);
        return this;
    }

    public CheckoutPage enterPostalCode(String postalCode) {
        
    	sendKeys(postalCodeInput, postalCode);
        return this;
    }

    public CheckoutOverviewPage continueToOverview() {
        
    	clickElement(continueButton);
        return new CheckoutOverviewPage(driver);
    }

    public CartPage cancelCheckout() {
        
    	clickElement(cancelButton);
        return new CartPage(driver);
    }

    public String getErrorMessage() {
        return isElementDisplayed(errorMessage) ? errorMessage.getText() : "";
    }

    public String getEnteredFirstName() {
        return firstNameInput.getAttribute("value");
    }

    public String getEnteredLastName() {
        return lastNameInput.getAttribute("value");
    }

    public String getEnteredPostalCode() {
        return postalCodeInput.getAttribute("value");
    }

    public CheckoutPage clearForm() {
        firstNameInput.clear();
        lastNameInput.clear();
        postalCodeInput.clear();
        return this;
    }
}