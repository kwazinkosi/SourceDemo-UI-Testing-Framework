package tests.pages;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import tests.base.BaseTest;
import utils.LoggingManager;
import components.ProductComponent;
import listeners.RetryAnalyzer;

public class CartPageTest extends BaseTest {

	private CartPage cartPage;
	private static final String PRODUCT_1 = "Sauce Labs Backpack";
	private static final String PRODUCT_2 = "Sauce Labs Bike Light";
	private static final String PRODUCT_1_PRICE = "$29.99";
	private static final String PRODUCT_2_PRICE = "$9.99";

	@BeforeMethod
	public void setUp() {
		   logStep("Setting up test: Adding items to cart and navigating to cart page");
	        landingPage.addItemToCart(PRODUCT_1).addItemToCart(PRODUCT_2);
	        cartPage = landingPage.getCart().navigateToCart();
	        logStep("Navigated to cart page");
	}

	@Test(priority = 0, description = "Verify removing a product from the cart works correctly", retryAnalyzer = RetryAnalyzer.class)
	public void testRemoveProductFromCart() {
	    logStep("Testing removal of product from cart");

	    // Step 1: Get initial cart count
	    int initialCount = cartPage.getCartItemCount();
	    logStep("Initial cart count: " + initialCount);

	    // Step 2: Remove product from cart
	    cartPage.removeProduct(PRODUCT_1);
	    logStep("Removed product: " + PRODUCT_1);

	    // Step 3: Verify cart count and product removal
	    Assert.assertEquals(cartPage.getCartItemCount(), initialCount - 1, "Cart count should decrease after removal");
	    Assert.assertFalse(cartPage.containsProduct(PRODUCT_1), "Removed product should no longer be in cart");
	    logStep("Cart count after removal: " + cartPage.getCartItemCount());

	    logStepWithScreenshot("Product removed from cart successfully");
	    LoggingManager.info("CartPageTest::testRemoveProductFromCart PASSED!");
	    System.out.println("CartPageTest::testRemoveProductFromCart PASSED!");
	}

	@Test(priority = 1, description = "Check if removing a non-existent product throws an exception", expectedExceptions = RuntimeException.class)
	public void testRemoveNonExistentProductThrowsException() {
	    logStep("Testing removal of non-existent product");

	    try {
	        // Step 1: Attempt to remove non-existent product
	        cartPage.removeProduct("Invalid Product");
	        logStep("Attempted to remove non-existent product: Invalid Product");
	    } catch (RuntimeException e) {
	        // Step 2: Verify exception message
	        Assert.assertEquals(e.getMessage(), "Product not found in cart: Invalid Product",
	                "Unexpected exception message");
	        logStep("Exception thrown with message: " + e.getMessage());

	        LoggingManager.info("CartPageTest::testRemoveNonExistentProductThrowsException PASSED!");
	        System.out.println("CartPageTest::testRemoveNonExistentProductThrowsException PASSED!");
	        throw e;
	    }
	}

	@Test(priority = 2, description = "Ensure proceeding to checkout navigates to the checkout page")
	public void testProceedToCheckoutNavigatesToCheckoutPage() {
	    logStep("Testing navigation to checkout page");

	    // Step 1: Verify cart is not empty
	    Assert.assertTrue(cartPage.getCartItemCount() > 0, "Cart should not be empty before checkout");
	    logStep("Cart contains " + cartPage.getCartItemCount() + " items");

	    // Step 2: Proceed to checkout
	    CheckoutPage checkoutPage = cartPage.proceedToCheckout();
	    logStep("Navigated to checkout page");

	    // Step 3: Verify checkout page is displayed
	    Assert.assertTrue(checkoutPage.isPageDisplayed(), "Checkout page should be displayed");
	    logStep("Checkout page is displayed");

	    logStepWithScreenshot("Navigation to checkout page verified successfully");
	    LoggingManager.info("CartPageTest::testProceedToCheckoutNavigatesToCheckoutPage PASSED!");
	    System.out.println("CartPageTest::testProceedToCheckoutNavigatesToCheckoutPage PASSED!");
	}

	@Test(priority = 3, description = "Verify that continuing shopping redirects back to the landing page")
	public void testContinueShoppingReturnsToLandingPage() {
	    logStep("Testing navigation back to landing page");

	    // Step 1: Continue shopping
	    LandingPage landingPage = cartPage.continueShopping();
	    logStep("Navigated back to landing page");

	    // Step 2: Verify landing page is displayed
	    Assert.assertTrue(landingPage.isPageDisplayed(), "Should return to landing page");
	    logStep("Landing page is displayed");

	    // Step 3: Verify cart count remains unchanged
	    Assert.assertEquals(landingPage.getCart().getCurrentCartCount(), 2, "Cart count should remain unchanged");
	    logStep("Cart count after navigation: " + landingPage.getCart().getCurrentCartCount());

	    logStepWithScreenshot("Navigation back to landing page verified successfully");
	    LoggingManager.info("CartPageTest::testContinueShoppingReturnsToLandingPage PASSED!");
	    System.out.println("CartPageTest::testContinueShoppingReturnsToLandingPage PASSED!");
	}

	@Test(priority = 4, description = "Ensure the cart shows an empty state after all items are removed")
	public void testCartEmptyStateAfterRemovingAllItems() {
	    logStep("Testing empty state after removing all items");

	    // Step 1: Remove all items from cart
	    cartPage.removeProduct(PRODUCT_1).removeProduct(PRODUCT_2);
	    logStep("Removed all items from cart");

	    // Step 2: Verify cart is empty
	    Assert.assertEquals(cartPage.getCartItemCount(), 0, "Cart should be empty");
	    Assert.assertTrue(cartPage.isCartEmpty(), "Empty state not displayed");
	    logStep("Cart is empty");

	    logStepWithScreenshot("Empty state verified successfully");
	    LoggingManager.info("CartPageTest::testCartEmptyStateAfterRemovingAllItems PASSED!");
	    System.out.println("CartPageTest::testCartEmptyStateAfterRemovingAllItems PASSED!");
	}

	@Test(priority = 5, description = "Validate that the cart item count matches the number of added items", retryAnalyzer = RetryAnalyzer.class)
	public void testCartItemCountMatchesAddedItems() {
	    logStep("Testing cart item count matches added items");

	    // Step 1: Verify cart contains 2 items
	    Assert.assertEquals(cartPage.getCartItemCount(), 2, "Cart should contain 2 items");
	    logStep("Cart contains " + cartPage.getCartItemCount() + " items");

	    logStepWithScreenshot("Cart item count verified successfully");
	    LoggingManager.info("CartPageTest::testCartItemCountMatchesAddedItems PASSED!");
	    System.out.println("CartPageTest::testCartItemCountMatchesAddedItems PASSED!");
	}

	@Test(priority = 6, description = "Check if the cart contains the correct products after adding them", retryAnalyzer = RetryAnalyzer.class)
	public void testCartContainsCorrectProducts() {
	    logStep("Testing cart contains correct products");

	    // Step 1: Verify cart contains PRODUCT_1 and PRODUCT_2
	    Assert.assertTrue(cartPage.containsProduct(PRODUCT_1), "Product 1 missing from cart");
	    Assert.assertTrue(cartPage.containsProduct(PRODUCT_2), "Product 2 missing from cart");
	    logStep("Cart contains: " + PRODUCT_1 + " and " + PRODUCT_2);

	    logStepWithScreenshot("Cart contents verified successfully");
	    LoggingManager.info("CartPageTest::testCartContainsCorrectProducts PASSED!");
	    System.out.println("CartPageTest::testCartContainsCorrectProducts PASSED!");
	}

	@Test(priority = 7, description = "Ensure product details in cart match the items added", retryAnalyzer = RetryAnalyzer.class)
	public void testProductDetailsMatchAddedItems() {
	   
		logStep("Testing product details in cart match added items");

	    // Step 1: Verify product details for PRODUCT_1
	    ProductComponent product = cartPage.getProductByName(PRODUCT_1);
	    Assert.assertEquals(product.getProductPrice(), PRODUCT_1_PRICE, "Product price mismatch");
	    Assert.assertTrue(product.isRemoveButtonVisible(), "Remove button not visible");
	    logStep("Product details for " + PRODUCT_1 + " verified");

	    // Step 2: Verify product details for PRODUCT_2
	    ProductComponent product2 = cartPage.getProductByName(PRODUCT_2);
	    Assert.assertEquals(product2.getProductPrice(), PRODUCT_2_PRICE, "Product 2 price mismatch");
	    logStep("Product details for " + PRODUCT_2 + " verified");

	    logStepWithScreenshot("Product details verified successfully");
	    LoggingManager.info("CartPageTest::testProductDetailsMatchAddedItems PASSED!");
	    System.out.println("CartPageTest::testProductDetailsMatchAddedItems PASSED!");
	}
}