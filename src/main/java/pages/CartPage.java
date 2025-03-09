package pages;

import components.ProductComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

public class CartPage extends BasePage {

	@FindBy(css = ".cart_list")
	private WebElement cartContainer;

	@FindBy(xpath = ".//div[@class='cart_item']")
	private List<WebElement> cartItems;

	@FindBy(id = "checkout")
	private WebElement checkoutButton;

	@FindBy(id = "continue-shopping")
	private WebElement continueShoppingButton;

	private List<ProductComponent> productComponents;

	public CartPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		initializeProductComponents();
	}

	private void initializeProductComponents() {

		productComponents = cartItems.stream().map(item -> new ProductComponent(driver, item))
				.collect(Collectors.toList());
	}

	public boolean containsProduct(String productName) {

		return productComponents.stream().anyMatch(p -> p.getProductName().equalsIgnoreCase(productName));
	}

	public List<ProductComponent> getCartItems() {

		initializeProductComponents(); // Refresh components
		return productComponents;
	}

	public int getCartItemCount() {
		return getCartItems().size();
	}

	public CartPage removeProduct(String productName) {

		productComponents.stream().filter(p -> p.getProductName().equals(productName)).findFirst()
				.ifPresentOrElse(ProductComponent::removeProductFromCart, () -> {
					throw new RuntimeException("Product not found in cart: " + productName);
				});
		initializeProductComponents();
		System.out.println("Removed: " + productName);
		return this;
	}

	public ProductComponent getProductByName(String name) {

		return productComponents.stream().filter(p -> p.getProductName().equalsIgnoreCase(name)).findFirst()
				.orElseThrow();
	}

	public CheckoutPage proceedToCheckout() {
		clickElement(checkoutButton);
		return new CheckoutPage(driver);
	}

	public LandingPage continueShopping() {
		clickElement(continueShoppingButton);
		return new LandingPage(driver);
	}

	public boolean isCartEmpty() {
		return getCartItemCount() == 0;
	}
}