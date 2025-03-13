package tests.pages;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import listeners.RetryAnalyzer;
import pages.LandingPage;
import pages.ProductDetailsPage;
import tests.base.BaseTest;
import utils.LoggingManager;

public class ProductDetailsPageTest extends BaseTest {

	private ProductDetailsPage productDetailsPage;

	@BeforeMethod(dependsOnMethods = { "setup" })
	public void setUpTest() {

		logStep("Setting up test: Navigating to product details page");
		productDetailsPage = landingPage.viewProductDetails(0);
		logStep("Navigated to product details page");
	}

	@Test(priority = 0, description = "Ensure product details are displayed correctly", retryAnalyzer = RetryAnalyzer.class)
	public void testProductDetailsDisplayed() {
		logStep("Verifying product details are displayed correctly");

		// Step 1: Verify product details page is displayed
		Assert.assertTrue(productDetailsPage.isPageDisplayed(), "Product details page not displayed");
		logStep("Product details page is displayed");

		// Step 2: Verify product image is displayed
		Assert.assertTrue(productDetailsPage.getProduct().isProductImageDisplayed(), "Product image not displayed");
		logStep("Product image is displayed");

		// Step 3: Verify product name is not empty
		Assert.assertFalse(productDetailsPage.getProductName().isEmpty(), "Product name is empty");
		logStep("Product name: " + productDetailsPage.getProductName());

		// Step 4: Verify product description is not empty
		Assert.assertFalse(productDetailsPage.getProductDescription().isEmpty(), "Product description is empty");
		logStep("Product description: " + productDetailsPage.getProductDescription());

		// Step 5: Verify product price is not empty
		Assert.assertFalse(productDetailsPage.getProductPrice().isEmpty(), "Product price is empty");
		logStep("Product price: " + productDetailsPage.getProductPrice());

		logStepWithScreenshot("All product details are displayed correctly");
		LoggingManager.info("ProductPageTests::testProductDetailsDisplayed PASSED!");
		System.out.println("ProductPageTests::testProductDetailsDisplayed PASSED!");
	}

	@Test(priority = 1, description = "Verify that adding and removing an item from the cart works correctly")
	public void testAddRemoveItemFromCart() {
		logStep("Testing add/remove item from cart functionality");

		// Step 1: Add item to cart
		productDetailsPage.addItemToCart();
		Assert.assertTrue(productDetailsPage.isRemoveButtonVisible(), "Remove button not visible after adding to cart");
		logStep("Item added to cart. Remove button is visible");

		// Step 2: Remove item from cart
		productDetailsPage.removeItemFromCart();
		Assert.assertTrue(productDetailsPage.isAddToCartVisible(), "Add to cart button not visible after removal");
		logStep("Item removed from cart. Add to cart button is visible");

		logStepWithScreenshot("Add/remove item from cart functionality verified successfully");
		LoggingManager.info("ProductPageTests::testAddRemoveItemFromCart PASSED!");
		System.out.println("ProductPageTests::testAddRemoveItemFromCart PASSED!");
	}

	@Test(priority = 2, description = "Ensure navigation back to products page functions properly", retryAnalyzer = RetryAnalyzer.class)
	public void testNavigateBackToProducts() {
		logStep("Testing navigation back to products page");

		// Step 1: Navigate back to products page
		LandingPage landingPage = productDetailsPage.navigateBackToProducts();
		logStep("Navigated back to products page");

		// Step 2: Verify landing page is displayed
		Assert.assertTrue(landingPage.isPageDisplayed(), "Did not return to landing page");
		logStep("Landing page is displayed");

		logStepWithScreenshot("Navigation back to products page verified successfully");
		LoggingManager.info("ProductPageTests::testNavigateBackToProducts PASSED!");
		System.out.println("ProductPageTests::testNavigateBackToProducts PASSED!");
	}
}