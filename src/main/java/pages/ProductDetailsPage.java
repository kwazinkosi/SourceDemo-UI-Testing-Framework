package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import components.ProductComponent;
import constants.WaitTime;

public class ProductDetailsPage extends BasePage {

    // Page elements
    @FindBy(xpath = "//button[@id='back-to-products']")
    private WebElement backToProductsButton;
    
    @FindBy(xpath = "//button[contains(@id, 'add-to-cart')]")
    private WebElement addToCartButton;
    
    @FindBy(xpath = "//div[@class='inventory_details_container']")
    private WebElement productDetailsContainer;

    // Component
    private final ProductComponent productComponent;

    public ProductDetailsPage(WebDriver driver) {
        super(driver);
        this.productComponent = new ProductComponent(driver, productDetailsContainer);
        
    }

    public LandingPage navigateBackToProducts() {
        clickElement(backToProductsButton);
        return new LandingPage(driver);
    }

    public ProductDetailsPage addItemToCart() {
        clickElement(addToCartButton);
        return this;
    }

    public ProductDetailsPage removeItemFromCart() {
        productComponent.removeProductFromCart();
        return this;
    }

    public boolean isRemoveButtonVisible() {
        return productComponent.isRemoveButtonVisible();
    }
    
    public boolean isAddToCartVisible() {
        return isElementDisplayed(addToCartButton);
    }
    
    public String getProductPrice() {
        return productComponent.getProductPrice();
    }

    public String getProductName() {
        return productComponent.getProductName();
    }

    public String getProductDescription() {
        return productComponent.getProductDescription();
    }
    
    public ProductComponent getProduct() {
    	return this.productComponent;
    }
    
    @Override
    public boolean isPageDisplayed() {
        
    	return customWait.until(d -> 
            isElementDisplayed(productDetailsContainer) && 
            d.getCurrentUrl().contains("inventory-item.html"),
            WaitTime.NORMAL
        );
    }
}