package tests.pages;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import tests.base.BaseTest;
import utils.LoggingManager;

public class CheckoutCompletePageTest extends BaseTest {

	private CheckoutCompletePage completePage;
	private static final String PRODUCT_NAME = "Sauce Labs Backpack";

	@BeforeMethod
	public void setUp() {
		// Complete checkout to reach success page

		CheckoutOverviewPage page = (CheckoutOverviewPage) landingPage.addItemToCart(PRODUCT_NAME).getCart()
				.navigateToCart().proceedToCheckout().enterShippingInformation("John", "Doe", "12345")
				.continueToOverview();
		completePage = page.finishCheckout();
	}

	@Test(priority = 0)
	public void testPageDisplayState() {
		// Verify URL and core elements
		Assert.assertTrue(completePage.isPageDisplayed(), "Checkout complete page not properly displayed");

		// Verify visual elements
		Assert.assertEquals(completePage.getPageTitle(), "Checkout: Complete!", "Page title mismatch");

		LoggingManager.info("CheckoutCompletePageTest::testPageDisplayState PASSED!");
		System.out.println("CheckoutCompletePageTest::testPageDisplayState PASSED!");
	}

	@Test(priority = 1)
	public void testSuccessMessageContent() {
		String actualMessage = completePage.getSuccessMessage();
		Assert.assertEquals(actualMessage, CheckoutCompletePage.SUCCESS_MESSAGE,
				"Success message text mismatch.\nExpected: " + CheckoutCompletePage.SUCCESS_MESSAGE + "\nActual: "
						+ actualMessage);

		LoggingManager.info("CheckoutCompletePageTest::testSuccessMessageContent PASSED!");
		System.out.println("CheckoutCompletePageTest::testSuccessMessageContent PASSED!");
	}

	@Test(priority = 2)
	public void testOrderSuccessStatus() {
		Assert.assertTrue(completePage.isOrderSuccessful(), "Order success status verification failed");

		LoggingManager.info("CheckoutCompletePageTest::testOrderSuccessStatus PASSED!");
		System.out.println("CheckoutCompletePageTest::testOrderSuccessStatus PASSED!");
	}

	@Test(priority = 3)
	public void testNavigationBackToProducts() {
		LandingPage landingPage = completePage.navigateBackToHome();

		// Verify return to inventory
		Assert.assertTrue(landingPage.isPageDisplayed(), "Failed to return to landing page");
		Assert.assertTrue(landingPage.getCart().getCurrentCartCount() == 0,
				"Cart should be empty after completed order");

		LoggingManager.info("CheckoutCompletePageTest::testNavigationBackToProducts PASSED!");
		System.out.println("CheckoutCompletePageTest::testNavigationBackToProducts PASSED!");
	}

	@Test(priority = 4)
	public void testPageRefreshPersistsState() {

		driver.navigate().refresh();
		completePage.waitForPageLoad();

		Assert.assertTrue(completePage.isPageDisplayed(), "Page state not persisted after refresh");
		Assert.assertTrue(completePage.isOrderSuccessful(), "Order success status lost after refresh");

		LoggingManager.info("CheckoutOverviewPage::testPageRefreshPersistsState PASSED!");
		System.out.println("CheckoutOverviewPage::testPageRefreshPersistsState PASSED!");
	}
}