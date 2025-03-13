package tests.pages;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import listeners.RetryAnalyzer;
import pages.*;
import tests.base.BaseTest;
import utils.LoggingManager;

public class CheckoutOverviewPageTest extends BaseTest {

	private CheckoutOverviewPage overviewPage;
	private static final String PRODUCT_1 = "Sauce Labs Backpack";
	private static final String PRODUCT_2 = "Sauce Labs Bike Light";
	private static final double PRODUCT_1_PRICE = 29.99;
	private static final double PRODUCT_2_PRICE = 9.99;

	@BeforeMethod
	public void setUp() {

		logStep("Setting up test: Adding items to cart, checking them out and navigating to checkout overview page");
		overviewPage = (CheckoutOverviewPage) landingPage.addItemToCart(PRODUCT_1).addItemToCart(PRODUCT_2).getCart()
				.navigateToCart().proceedToCheckout().enterShippingInformation("John", "Doe", "12345")
				.continueToOverview();
		logStep("Navigated to checkout overview page");

	}

	@Test(priority = 0, description = "Verify that order summary details are displayed correctly", retryAnalyzer = RetryAnalyzer.class)
	public void testOrderSummaryDetailsDisplayed() {
		logStep("Testing order summary details display");

		// Step 1: Verify checkout overview page is displayed
		Assert.assertTrue(overviewPage.isPageDisplayed(), "Checkout overview page not displayed");
		logStep("Checkout overview page is displayed");

		// Step 2: Verify payment information is displayed
		Assert.assertFalse(overviewPage.getPaymentInformation().isEmpty(), "Payment information missing");
		logStep("Payment information: " + overviewPage.getPaymentInformation());

		// Step 3: Verify shipping information is displayed
		Assert.assertFalse(overviewPage.getShippingInformation().isEmpty(), "Shipping information missing");
		logStep("Shipping information: " + overviewPage.getShippingInformation());

		// Step 4: Verify cart item count
		Assert.assertTrue(overviewPage.getCartItemCount() == 2, "Incorrect number of items in cart");
		logStep("Cart contains " + overviewPage.getCartItemCount() + " items");

		logStepWithScreenshot("Order summary details verified successfully");
		LoggingManager.info("CheckoutOverviewPage::testOrderSummaryDetailsDisplayed PASSED!");
		System.out.println("CheckoutOverviewPage::testOrderSummaryDetailsDisplayed PASSED!");
	}

	@Test(priority = 1, description = "Ensure price calculations in the checkout overview are correct", retryAnalyzer = RetryAnalyzer.class)
	public void testPriceCalculations() {
		logStep("Testing price calculations in checkout overview");

		// Step 1: Calculate expected subtotal
		ExtentTest subtotalStep = createNestedStep("Subtotal Calculation");
		double expectedSubtotal = PRODUCT_1_PRICE + PRODUCT_2_PRICE;

		// Step 2: Verify subtotal
		double actualSubtotal = overviewPage.getSubtotal();
		subtotalStep.info("Expected: " + expectedSubtotal);
		subtotalStep.info("Actual: " + actualSubtotal);

		Assert.assertEquals(actualSubtotal, expectedSubtotal, 0.001, "Subtotal calculation incorrect");
		subtotalStep.pass("Subtotal verified");

		// Step 3: Verify tax
		ExtentTest taxStep = createNestedStep("Tax Calculation");

		double tax = overviewPage.getTax();
		taxStep.info("Tax: " + tax);
		Assert.assertTrue(tax > 0, "Tax amount not calculated");
		taxStep.pass("Tax verified");

		// Step 4: Verify total
		ExtentTest totalStep = createNestedStep("Total Calculation");
		double total = overviewPage.getTotal();
		totalStep.info("Total: " + total);
		Assert.assertEquals(total, actualSubtotal + tax, 0.001, "Total calculation incorrect");
		totalStep.pass("Total verified");

		logStepWithScreenshot("Price calculations verified successfully");
		LoggingManager.info("CheckoutOverviewPage::testPriceCalculations PASSED!");
		System.out.println("CheckoutOverviewPage::testPriceCalculations PASSED!");
	}

	@Test(priority = 2, description = "Check the total price calculation method in checkout overview")
	public void testTotalCalculationMethod() {
		logStep("Testing total price calculation method");

		// Step 1: Verify total calculation
		Assert.assertTrue(overviewPage.verifyTotalCalculation(), "Total amount does not match subtotal + tax");
		logStep("Total calculation verified successfully");

		logStepWithScreenshot("Total price calculation method verified successfully");
		LoggingManager.info("CheckoutOverviewPage::testTotalCalculationMethod PASSED!");
		System.out.println("CheckoutOverviewPage::testTotalCalculationMethod PASSED!");
	}

	@Test(priority = 3, description = "Ensure navigation to cart page from checkout overview works", retryAnalyzer = RetryAnalyzer.class)
	public void testNavigationToCartPage() {
		logStep("Testing navigation back to cart page");

		// Step 1: Cancel checkout and navigate to cart page
		CartPage cartPage = overviewPage.cancelCheckout();
		logStep("Checkout canceled");

		// Step 2: Verify cart page is displayed
		Assert.assertTrue(cartPage.isPageDisplayed(), "Cart page not displayed after cancellation");
		logStep("Navigated back to cart page");

		logStepWithScreenshot("Navigation to cart page verified successfully");
		LoggingManager.info("CheckoutOverviewPage::testNavigationToCartPage PASSED!");
		System.out.println("CheckoutOverviewPage::testNavigationToCartPage PASSED!");
	}

	@Test(priority = 4, description = "Verify successful navigation to order completion page", retryAnalyzer = RetryAnalyzer.class)
	public void testNavigationToCompletePage() {

		logStep("Testing navigation to order completion page");
		// Step 1: Finish checkout
		CheckoutCompletePage completePage = overviewPage.finishCheckout();
		logStep("Checkout completed");

		// Step 2: Verify checkout complete page is displayed
		Assert.assertTrue(completePage.isPageDisplayed(), "Checkout complete page not displayed");
		logStep("Navigated to checkout complete page");

		logStepWithScreenshot("Navigation to order completion page verified successfully");
		LoggingManager.info("CheckoutOverviewPage::testNavigationToCompletePage PASSED!");
		System.out.println("CheckoutOverviewPage::testNavigationToCompletePage PASSED!");
	}
}