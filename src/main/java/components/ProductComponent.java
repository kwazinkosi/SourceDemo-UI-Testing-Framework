package components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import constants.WaitTime;
import pages.BasePage;
import pages.ProductDetailsPage;

public class ProductComponent extends BasePage {

    
    @FindBy(xpath = ".//div[@data-test='inventory-item-name']")
    private WebElement productNameElement;

    @FindBy(xpath = ".//div[@data-test='inventory-item-desc']")
    private WebElement productDescriptionElement;

    @FindBy(xpath = ".//div[@data-test='inventory-item-price']")
    private WebElement productPriceElement;

    @FindBy(xpath = ".//button[contains(text(), 'Remove')]")
    private WebElement removeProductBtn; // only exists once a product has been added to cart

    @FindBy(xpath = ".//button[contains(text(), 'Add to cart')]")
    private WebElement addProductBtn; // only exists once a product has been added to cart

    @FindBy(xpath = ".//img[contains(@src, 'jpg')]")
    private WebElement imageLink;

    private final WebElement rootElement;

    public ProductComponent(WebDriver driver, WebElement rootElement) {
        super(driver);
        this.rootElement = rootElement;
        // Initialize elements within the rootElement context
        PageFactory.initElements(rootElement, this);
    }

    public void removeProductFromCart() {
        try {
            customWait.until(ExpectedConditions.elementToBeClickable(removeProductBtn), WaitTime.NORMAL);
            removeProductBtn.click();
        } catch (Exception e) {
            System.out.println("Exception occurred while removing product from cart: " + e.getMessage());
        }
    }

    public void addProductToCart() {
        try {
            customWait.until(ExpectedConditions.elementToBeClickable(addProductBtn), WaitTime.NORMAL);
            addProductBtn.click();
        } catch (Exception e) {
            System.out.println("Exception occurred while adding product to cart: " + e.getMessage());
        }
    }

    public boolean isRemoveButtonVisible() {
        return isElementDisplayed(removeProductBtn);
    }

    public boolean isAddCartButtonVisible() {
        return isElementDisplayed(addProductBtn);
    }

    public String getProductPrice() {
        return getText(productPriceElement);
    }

    public String getProductName() {
        return getText(productNameElement);
    }

    public String getProductDescription() {
        return getText(productDescriptionElement);
    }

    public boolean isProductImageDisplayed() {
        return isElementDisplayed(imageLink);
    }
	

    public ProductDetailsPage viewDetails() {
        try {
//            clickElement(imageLink);
            return new ProductDetailsPage(driver);
        } catch (Exception e) {
            System.out.println("Exception occurred while viewing product details: " + e.getMessage());
            return null;
        }
    }
}