package tests.pages;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import tests.base.BaseTest;
import components.ProductComponent;

public class CartPageTest extends BaseTest {

    private CartPage cartPage;
    private static final String PRODUCT_1 = "Sauce Labs Backpack";
    private static final String PRODUCT_2 = "Sauce Labs Bike Light";

    @BeforeMethod
    public void setUp() {
        // Login and add items to cart
        LoginPage loginPage = new LoginPage(driver);
        LandingPage landingPage = (LandingPage) loginPage.login("standard_user", "secret_sauce");
        landingPage.addItemToCart(PRODUCT_1)
                   .addItemToCart(PRODUCT_2);
        cartPage = landingPage.getCart().navigateToCart();
    }

    @Test(priority =0)
    public void testRemoveProductFromCart() {
        int initialCount = cartPage.getCartItemCount();
        
        cartPage.removeProduct(PRODUCT_1);
        
        Assert.assertEquals(cartPage.getCartItemCount(), initialCount - 1, 
            "Cart count should decrease after removal");
        Assert.assertFalse(cartPage.containsProduct(PRODUCT_1), 
            "Removed product should no longer be in cart");
    }

    @Test(priority =1, expectedExceptions = RuntimeException.class, 
          expectedExceptionsMessageRegExp = "Product not found in cart: Invalid Product")
    public void testRemoveNonExistentProductThrowsException() {
        cartPage.removeProduct("Invalid Product");
    }

    @Test(priority = 2)
    public void testProceedToCheckoutNavigatesToCheckoutPage() {
        
    	CheckoutPage checkoutPage = cartPage.proceedToCheckout();
        Assert.assertTrue(checkoutPage.isPageDisplayed(), 
            "Checkout page should be displayed");
    }

    @Test(priority = 3)
    public void testContinueShoppingReturnsToLandingPage() {
        
    	LandingPage landingPage = cartPage.continueShopping();
        Assert.assertTrue(landingPage.isPageDisplayed(), 
            "Should return to landing page");
    }

    @Test(priority = 4)
    public void testCartEmptyStateAfterRemovingAllItems() {
        cartPage.removeProduct(PRODUCT_1)
                .removeProduct(PRODUCT_2);
        
        Assert.assertEquals(cartPage.getCartItemCount(), 0, 
            "Cart should be empty");
        Assert.assertTrue(cartPage.isCartEmpty(), 
            "Empty state not displayed");
    }

    @Test(priority = 5)
    public void testCartItemCountMatchesAddedItems() {
        Assert.assertEquals(cartPage.getCartItemCount(), 2, 
            "Cart should contain 2 items");
    }

    @Test(priority = 6)
    public void testCartContainsCorrectProducts() {
    	
    	
        Assert.assertTrue(cartPage.containsProduct(PRODUCT_1), 
            "Product 1 missing from cart");
        Assert.assertTrue(cartPage.containsProduct(PRODUCT_2), 
            "Product 2 missing from cart");
    }

    @Test(priority = 7)
    public void testProductDetailsMatchAddedItems() {
    	
        ProductComponent product = cartPage.getProductByName(PRODUCT_1);
        Assert.assertEquals(product.getProductPrice(), "$29.99", 
            "Product price mismatch");
        Assert.assertTrue(product.isRemoveButtonVisible(), 
            "Remove button not visible");
    }
}