package components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import constants.WaitTime;
import pages.BasePage;
import pages.ProductDetailsPage;
import pages.base.CustomWait;
import utils.DriverFactory;

public class ProductComponent extends BasePage {

	@FindBy(xpath = ".//div[@data-test='inventory-item-name']")
	private WebElement productNameElement;

	@FindBy(xpath = ".//div[@data-test='inventory-item-desc']")
	private WebElement productDescriptionElement;

	@FindBy(xpath = ".//div[@data-test='inventory-item-price']")
	private WebElement productPriceElement;

	@FindBy(xpath = "//div[@class='inventory_details_container']")
	private WebElement productDetailsContainer;

	@FindBy(xpath = "//Button[normalize-space()='Remove']")
	private WebElement removeProductBtn; // only exist once a product has been added to cart

	@FindBy(xpath = "//Button[normalize-space()='Add to cart']")
	private WebElement AddProductBtn; // only exist once a product has been added to cart

	@FindBy(xpath = "//img[contains(@src, 'jpg')]")
	private WebElement imageLink;

	private final WebElement rootElement;

	private WebDriver driver;
	
	private CustomWait customWait;
	
	public ProductComponent(WebDriver driver, WebElement rootElement) {

		super(driver);
		this.rootElement = rootElement;
		this.customWait = new CustomWait(driver);
		PageFactory.initElements(driver, this);
	}

	public void removeProductFromCart() {

		try {
			customWait.until(ExpectedConditions.elementToBeClickable(removeProductBtn), WaitTime.NORMAL);
			removeProductBtn.click();
		} catch (Exception e) {
			System.out.println("Exception occured whilst removing product cart.");
		}
	}

	public void addProductToCart() {

		try {
			
			customWait.until(ExpectedConditions.elementToBeClickable(AddProductBtn), WaitTime.NORMAL);
			AddProductBtn.click();
		} catch (Exception e) {
			System.out.println("Exception occured whilst adding product cart.");
		}
	}

	public boolean isRemoveButtonVisible() {
		return removeProductBtn.isDisplayed();
	}

	public boolean isAddCartButtonVisible() {
		return AddProductBtn.isDisplayed();
	}

	public String getProductPrice() {
		return productPriceElement.getText();
	}

	public String getProductName() {
		return productNameElement.getText();
	}

	public String getProductDescription() {
		return productDescriptionElement.getText();
	}

	public Boolean isProductImageDispalyed() {
		
		return imageLink.isDisplayed();
	}
	
	public ProductDetailsPage viewDetails() {

		try {
			return new ProductDetailsPage(DriverFactory.getDriver());
		} catch (Exception e) {
			System.out.println("Exception occured: " + e.getMessage());
		}
		return null;
	}
}
