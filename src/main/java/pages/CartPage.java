package pages;

import components.ProductComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;
import java.util.stream.Collectors;

public class CartPage extends BasePage {

    @FindBy(xpath = "//div[@class='cart_item']")
    private List<WebElement> cartItems;
    
    @FindBy(id = "checkout")
    private WebElement checkoutButton;
    
    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingButton;

    private List<ProductComponent> productComponents;

    public CartPage(WebDriver driver) {
        super(driver);
        initializeProductComponents();
    }

    private void initializeProductComponents() {
        
    	productComponents = cartItems.stream()
            .map(item -> new ProductComponent(driver, item))
            .collect(Collectors.toList());
    }

    public CartPage removeProduct(String productName) {
        productComponents.stream()
            .filter(p -> p.getProductName().equals(productName))
            .findFirst()
            .ifPresentOrElse(
                ProductComponent::removeProductFromCart,
                () -> { throw new RuntimeException("Product not found in cart: " + productName); }
            );
        return this;
    }

    
    public List<ProductComponent> getCartItems() {
    	
        return cartItems.stream()
            .map(item -> new ProductComponent(driver, item))
            .collect(Collectors.toList());
    }

    public int getCartItemCount() {
        return getCartItems().size();
    }

    public CheckoutPage proceedToCheckout() {
        clickElement(checkoutButton);
        return new CheckoutPage(driver);
    }

    public LandingPage continueShopping() {
        clickElement(continueShoppingButton);
        return new LandingPage(driver);
    }

	public CartPage verifyEmptyState() {
		
		if(cartItems.isEmpty() || cartItems ==null) {
			
		}
		return null;
	}

}
