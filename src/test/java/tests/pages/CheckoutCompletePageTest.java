package tests.pages;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import listeners.RetryAnalyzer;
import pages.*;
import tests.base.BaseTest;
import utils.DriverFactory;
import utils.LoggingManager;

public class CheckoutCompletePageTest extends BaseTest {

	private CheckoutCompletePage completePage;
	private static final String PRODUCT_NAME = "Sauce Labs Backpack";
	private WebDriver driver;

	@BeforeMethod
	public void setUp() {
		// Complete checkout to reach success page
		logStep("Setting up test: Adding item to cart and navigating to checkout complete page");
		driver = DriverFactory.getDriver();
		CheckoutOverviewPage page = (CheckoutOverviewPage) landingPage.addItemToCart(PRODUCT_NAME).getCart()
				.navigateToCart().proceedToCheckout().enterShippingInformation("John", "Doe", "12345")
				.continueToOverview();
		completePage = page.finishCheckout();
		logStep("Navigated to checkout complete page");
	}

	@Test(priority = 0, description = "Ensure checkout complete page is displayed properly", retryAnalyzer = RetryAnalyzer.class)
	public void testPageDisplayState() {
		logStep("Testing checkout complete page display state");

		// Step 1: Verify page is displayed
		Assert.assertTrue(completePage.isPageDisplayed(), "Checkout complete page not properly displayed");
		logStep("Checkout complete page is displayed");

		// Step 2: Verify page title
		Assert.assertEquals(completePage.getPageTitle(), "Checkout: Complete!", "Page title mismatch");
		logStep("Page title verified: " + completePage.getPageTitle());

		logStepWithScreenshot("Checkout complete page display state verified successfully");
		LoggingManager.info("CheckoutCompletePageTest::testPageDisplayState PASSED!");
		System.out.println("CheckoutCompletePageTest::testPageDisplayState PASSED!");
	}

	@Test(priority = 1, description = "Verify success message content on the checkout complete page", retryAnalyzer = RetryAnalyzer.class)
	public void testSuccessMessageContent() {
		logStep("Testing success message content");

		// Step 1: Get success message
		String actualMessage = completePage.getSuccessMessage();
		logStep("Success message: " + actualMessage);

		// Step 2: Verify success message
		Assert.assertEquals(actualMessage, CheckoutCompletePage.SUCCESS_MESSAGE,
				"Success message text mismatch.\nExpected: " + CheckoutCompletePage.SUCCESS_MESSAGE + "\nActual: "
						+ actualMessage);
		logStep("Success message content verified");

		logStepWithScreenshot("Success message content verified successfully");
		LoggingManager.info("CheckoutCompletePageTest::testSuccessMessageContent PASSED!");
		System.out.println("CheckoutCompletePageTest::testSuccessMessageContent PASSED!");
	}

	@Test(priority = 2, description = "Ensure order success status is displayed correctly", retryAnalyzer = RetryAnalyzer.class)
	public void testOrderSuccessStatus() {
		logStep("Testing order success status");

		// Step 1: Verify order success status
		Assert.assertTrue(completePage.isOrderSuccessful(), "Order success status verification failed");
		logStep("Order success status verified");

		logStepWithScreenshot("Order success status verified successfully");
		LoggingManager.info("CheckoutCompletePageTest::testOrderSuccessStatus PASSED!");
		System.out.println("CheckoutCompletePageTest::testOrderSuccessStatus PASSED!");
	}

	@Test(priority = 3, description = "Validate navigation back to the products page from order complete", retryAnalyzer = RetryAnalyzer.class)
	public void testNavigationBackToProducts() {

		// Step 1: Navigate back to products page
		ExtentTest navStep = createNestedStep("Navigation");
		navStep.info("Navigating back to products");
		LandingPage landingPage = completePage.navigateBackToHome();

		// Step 2: Verify landing page is displayed
		Assert.assertTrue(landingPage.isPageDisplayed(), "Failed to return to landing page");
		navStep.pass("Landing page is displayed");
		navStep.info("Navigated back to products page");

		// Step 3: Verify cart is empty
		ExtentTest cartStep = createNestedStep("Cart Validation");
		cartStep.info("Cart count: " + landingPage.getCart().getCurrentCartCount());
		Assert.assertTrue(landingPage.getCart().getCurrentCartCount() == 0,
				"Cart should be empty after completed order");
		logStep("Cart is empty");
		cartStep.pass("Cart empty verified");
		
		logStepWithScreenshot("Navigation back to products page verified successfully");
		LoggingManager.info("CheckoutCompletePageTest::testNavigationBackToProducts PASSED!");
		System.out.println("CheckoutCompletePageTest::testNavigationBackToProducts PASSED!");
	}

	@Test(priority = 4, description = "Check if refreshing the checkout complete page persists the state")
	public void testPageRefreshPersistsState() {

		logStep("Testing page refresh persistence");
		// Step 1: Refresh the page
		driver.navigate().refresh();
		completePage.waitForPageLoad();
		logStep("Page refreshed");

		// Step 2: Verify page state persists
		Assert.assertTrue(completePage.isPageDisplayed(), "Page state not persisted after refresh");
		Assert.assertTrue(completePage.isOrderSuccessful(), "Order success status lost after refresh");
		logStep("Page state and order success status persisted after refresh");

		logStepWithScreenshot("Page refresh persistence verified successfully");
		LoggingManager.info("CheckoutCompletePageTest::testPageRefreshPersistsState PASSED!");
		System.out.println("CheckoutCompletePageTest::testPageRefreshPersistsState PASSED!");
	}
}