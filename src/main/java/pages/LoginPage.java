package pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class LoginPage extends BasePage {

	@FindBy(id = "user-name")
	private WebElement usernameField;

	@FindBy(id = "password")
	private WebElement passwordField;
	
	@FindBy(xpath = "//h3[@data-test ='error']")
	private WebElement error;

	@FindBy(xpath = "login-button")
	private WebElement loginButton;

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	
	public void enterUsername(String username) {
		sendKeys(usernameField, username);
	}

	public void enterPassword(String password) {
		sendKeys(passwordField, password);
	}
	
	
	public BasePage login(String username, String password) {
	    
		enterUsername(username);
	    enterPassword(password);
	    clickElement(loginButton);
	    
	    if (isElementDisplayed(error)) {
	        return this; // Return LoginPage instance on error
	    }
	    return new LandingPage(driver); // Return LandingPage on success
	}

	public String getErrorMessage() {
		
	    return isElementDisplayed(error) ? error.getText() : "";
	}

	@Override
	public boolean isPageDisplayed() {
		return isElementDisplayed(usernameField) && isElementDisplayed(passwordField)
				&& isElementDisplayed(loginButton);
	}
}