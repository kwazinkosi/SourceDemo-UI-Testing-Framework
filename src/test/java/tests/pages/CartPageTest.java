package tests.pages;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import tests.base.BaseTest;
import utils.LoggingManager;
import components.ProductComponent;

public class CartPageTest extends BaseTest {

	private CartPage cartPage;
	private static final String PRODUCT_1 = "Sauce Labs Backpack";
	private static final String PRODUCT_2 = "Sauce Labs Bike Light";
	private static final String PRODUCT_1_PRICE = "$29.99";
	private static final String PRODUCT_2_PRICE = "$9.99";

	@BeforeMethod
	public void setUp() {
		landingPage.addItemToCart(PRODUCT_1).addItemToCart(PRODUCT_2);
		cartPage = landingPage.getCart().navigateToCart();
	}

	@Test(priority = 0)
	public void testRemoveProductFromCart() {
		int initialCount = cartPage.getCartItemCount();

		cartPage.removeProduct(PRODUCT_1);

		Assert.assertEquals(cartPage.getCartItemCount(), initialCount - 1, "Cart count should decrease after removal");
		Assert.assertFalse(cartPage.containsProduct(PRODUCT_1), "Removed product should no longer be in cart");

		LoggingManager.info("CartPageTest::testRemoveProductFromCart PASSED!");
		System.out.println("CartPageTest::testRemoveProductFromCart PASSED!");
	}

	@Test(priority = 1, expectedExceptions = RuntimeException.class, expectedExceptionsMessageRegExp = "Product not found in cart: Invalid Product")
	public void testRemoveNonExistentProductThrowsException() {
		try {
			cartPage.removeProduct("Invalid Product");
		} catch (RuntimeException e) {
			Assert.assertEquals(e.getMessage(), "Product not found in cart: Invalid Product",
					"Unexpected exception message");

			LoggingManager.info("CartPageTest::testRemoveNonExistentProductThrowsException PASSED!");
			System.out.println("CartPageTest::testRemoveNonExistentProductThrowsException PASSED!");
			throw e;
		}
	}

	@Test(priority = 2)
	public void testProceedToCheckoutNavigatesToCheckoutPage() {

		Assert.assertTrue(cartPage.getCartItemCount() > 0, "Cart should not be empty before checkout");
		CheckoutPage checkoutPage = cartPage.proceedToCheckout();
		Assert.assertTrue(checkoutPage.isPageDisplayed(), "Checkout page should be displayed");

		LoggingManager.info("CartPageTest::testProceedToCheckoutNavigatesToCheckoutPage PASSED!");
		System.out.println("CartPageTest::testProceedToCheckoutNavigatesToCheckoutPage PASSED!");
	}

	@Test(priority = 3)
	public void testContinueShoppingReturnsToLandingPage() {

		LandingPage landingPage = cartPage.continueShopping();
		Assert.assertTrue(landingPage.isPageDisplayed(), "Should return to landing page");
		Assert.assertEquals(landingPage.getCart().getCurrentCartCount(), 2, "Cart count should remain unchanged");
		LoggingManager.info("CartPageTest::testContinueShoppingReturnsToLandingPage PASSED!");
		System.out.println("CartPageTest::testContinueShoppingReturnsToLandingPage PASSED!");
	}

	@Test(priority = 4)
	public void testCartEmptyStateAfterRemovingAllItems() {

		cartPage.removeProduct(PRODUCT_1).removeProduct(PRODUCT_2);

		Assert.assertEquals(cartPage.getCartItemCount(), 0, "Cart should be empty");
		Assert.assertTrue(cartPage.isCartEmpty(), "Empty state not displayed");

		LoggingManager.info("CartPageTest::testCartEmptyStateAfterRemovingAllItems PASSED!");
		System.out.println("CartPageTest::testCartEmptyStateAfterRemovingAllItems PASSED!");
	}

	@Test(priority = 5)
	public void testCartItemCountMatchesAddedItems() {

		Assert.assertEquals(cartPage.getCartItemCount(), 2, "Cart should contain 2 items");

		LoggingManager.info("CartPageTest::testCartItemCountMatchesAddedItems PASSED!");
		System.out.println("CartPageTest::testCartItemCountMatchesAddedItems PASSED!");
	}

	@Test(priority = 6)
	public void testCartContainsCorrectProducts() {

		Assert.assertTrue(cartPage.containsProduct(PRODUCT_1), "Product 1 missing from cart");
		Assert.assertTrue(cartPage.containsProduct(PRODUCT_2), "Product 2 missing from cart");

		LoggingManager.info("CartPageTest::testCartContainsCorrectProducts PASSED!");
		System.out.println("CartPageTest::testCartContainsCorrectProducts PASSED!");
	}

	@Test(priority = 7)
	public void testProductDetailsMatchAddedItems() {

		ProductComponent product = cartPage.getProductByName(PRODUCT_1);
		ProductComponent product2 = cartPage.getProductByName(PRODUCT_2);
		Assert.assertEquals(product.getProductPrice(), PRODUCT_1_PRICE, "Product price mismatch");
		Assert.assertTrue(product.isRemoveButtonVisible(), "Remove button not visible");

		Assert.assertEquals(product2.getProductPrice(), PRODUCT_2_PRICE, "Product 2 price mismatch");

		LoggingManager.info("CartPageTest::testProductDetailsMatchAddedItems PASSED!");
		System.out.println("CartPageTest::testProductDetailsMatchAddedItems PASSED!");
	}
}