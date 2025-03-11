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

		productDetailsPage = landingPage.viewProductDetails(0);
	}

	@Test(priority = 0, description = "Ensure product details are displayed correctly", retryAnalyzer = RetryAnalyzer.class)
	public void testProductDetailsDisplayed() {

		Assert.assertTrue(productDetailsPage.isPageDisplayed(), "Product details page not displayed");
		Assert.assertTrue(productDetailsPage.getProduct().isProductImageDisplayed(), "Product image not displayed");
		Assert.assertFalse(productDetailsPage.getProductName().isEmpty(), "Product name is empty");
		Assert.assertFalse(productDetailsPage.getProductDescription().isEmpty(), "Product description is empty");
		Assert.assertFalse(productDetailsPage.getProductPrice().isEmpty(), "Product price is empty");

		LoggingManager.info("ProductPageTests::testProductDetailsDisplayed PASSED!");
		System.out.println("ProductPageTests::testProductDetailsDisplayed PASSED!");
	}

	@Test(priority = 1, description = "Verify that adding and removing an item from the cart works correctly")
	public void testAddRemoveItemFromCart() {

		productDetailsPage.addItemToCart();
		Assert.assertTrue(productDetailsPage.isRemoveButtonVisible(), "Remove button not visible after adding to cart");

		productDetailsPage.removeItemFromCart();
		Assert.assertTrue(productDetailsPage.isAddToCartVisible(), "Add to cart button not visible after removal");
		LoggingManager.info("ProductPageTests::testAddRemoveItemFromCart PASSED!");
		System.out.println("ProductPageTests::testAddRemoveItemFromCart PASSED!");
	}

	@Test(priority = 2, description = "Ensure navigation back to products page functions properly", retryAnalyzer = RetryAnalyzer.class)
	public void testNavigateBackToProducts() {

		LandingPage landingPage = productDetailsPage.navigateBackToProducts();
		Assert.assertTrue(landingPage.isPageDisplayed(), "Did not return to landing page");
		LoggingManager.info("ProductPageTests::testNavigateBackToProducts PASSED!");
		System.out.println("ProductPageTests::testNavigateBackToProducts PASSED!");

	}
}