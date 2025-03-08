package pages;

import components.ProductComponent;
import exceptions.ProductNotFoundException;

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

    public ProductComponent getProductByName(String name) {

		return productComponents.stream()
				.filter(p -> p.getProductName()
				.equalsIgnoreCase(name))
				.findFirst()
				.orElseThrow(() -> new ProductNotFoundException(name));
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

	public boolean containsProduct(String productName) {
	    
		return getCartItems()
				.stream()
				.anyMatch(p -> p.getProductName()
				.equals(productName));
	}

	public boolean isCartEmpty() {
	    
		return getCartItemCount() == 0 && isElementDisplayed(cartItems.get(0));
	}
}
