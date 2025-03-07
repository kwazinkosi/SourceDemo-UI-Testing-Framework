package components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import constants.WaitTime;
import pages.CartPage;
import pages.base.CustomWait;

public class CartComponent {
	
    private final WebDriver driver;
    private final CustomWait customWait;

    @FindBy(id = "shopping_cart_container")
    private WebElement cartIcon;

    @FindBy(xpath ="//span[@class='shopping_cart_badge']")
    private WebElement cartBadge;
     
    public CartComponent(WebDriver driver) {
        this.driver = driver;
        this.customWait = new CustomWait(driver);
    }

    public CartPage navigateToCart() {
    	
        customWait.until(ExpectedConditions.elementToBeClickable(cartIcon), WaitTime.NORMAL);
        cartIcon.click();
        customWait.waitForPageLoad(WaitTime.NORMAL);
        return new CartPage(driver);
    }
    
    public int getCurrentCartCount() {
    	
    	int qty = Integer.parseInt(cartBadge.getText());
    	return qty;
    }

	public boolean isBadgeDisplayed() {
		return cartIcon.isDisplayed();
	}
}