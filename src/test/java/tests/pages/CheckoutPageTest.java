package tests.pages;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import tests.base.BaseTest;
import utils.LoggingManager;
import utils.dataproviders.SourceDemoDataProviders;
import utils.dataproviders.models.CheckoutTestData;

public class CheckoutPageTest extends BaseTest {

	private CheckoutPage checkoutPage;
	private static final String PRODUCT_NAME = "Sauce Labs Backpack";

	@BeforeMethod
	public void setUp() {
		
		landingPage.addItemToCart(PRODUCT_NAME);
		checkoutPage = landingPage.getCart().navigateToCart().proceedToCheckout();
	}

	
	@Test(priority =0, dataProvider = "checkoutData", dataProviderClass = SourceDemoDataProviders.class)
	public void testCheckoutFormSubmissions(CheckoutTestData data) {
		
		String testCaseID = data.getTestCaseId();
		String firstName = data.getFirstName();
		String lastName = data.getLastName();
		String zipCode = data.getZipCode();
		String expectedResult = data.getExpectedResult();
		
		checkoutPage.enterShippingInformation(firstName, lastName, zipCode);

		if (expectedResult.equals("success")) {
			BasePage overviewPage = (CheckoutOverviewPage) checkoutPage.continueToOverview();
			Assert.assertTrue(overviewPage.isPageDisplayed(), "Checkout overview page not displayed for valid data");

			LoggingManager.info(testCaseID + " (expected success) in CheckoutPageTests::testCheckoutFormSubmissions PASSED!");
			System.out.println(testCaseID + " (expected success) in CheckoutPageTests::testCheckoutFormSubmissions PASSED!");
		} else {
			BasePage page = (CartPage) checkoutPage.continueToOverview();
			Assert.assertEquals(checkoutPage.getErrorMessage(), expectedResult,
					"Unexpected error message for test case: " + testCaseID);

			LoggingManager.info(testCaseID + " (expected error) in CheckoutPageTests::testCheckoutFormSubmissions PASSED!");
			System.out.println(testCaseID + " (expected error) in CheckoutPageTests::testCheckoutFormSubmissions PASSED!");
		}
	}

	@Test(priority = 1)
	public void testCancelCheckoutReturnsToCart() {
		
		CartPage cartPage = checkoutPage.cancelCheckout();
		Assert.assertTrue(cartPage.isPageDisplayed(), "Should return to cart page after cancellation");

		LoggingManager.info("CheckoutPageTests::testCancelCheckoutReturnsToCart PASSED!");
		System.out.println("CheckoutPageTests::testCancelCheckoutReturnsToCart PASSED!");
	}

	@Test(priority =2)
	public void testFormClearFunctionality() {
		checkoutPage.enterShippingInformation("Test", "User", "67890").clearForm();

		Assert.assertTrue(checkoutPage.getEnteredFirstName().isEmpty(), "First name field not cleared");
		Assert.assertTrue(checkoutPage.getEnteredLastName().isEmpty(), "Last name field not cleared");
		Assert.assertTrue(checkoutPage.getEnteredPostalCode().isEmpty(), "Postal code field not cleared");
		LoggingManager.info("CheckoutPageTests::testFormClearFunctionality PASSED!");
		System.out.println("CheckoutPageTests::testFormClearFunctionality PASSED!");
	}

	@Test(priority = 3)
	public void testSpecialCharactersInPostalCode() {
		checkoutPage.enterShippingInformation("John", "Doe", "!@#$%").continueToOverview();

		Assert.assertTrue(new CheckoutOverviewPage(driver).isPageDisplayed(),
				"Special characters in postal code should be accepted");
	}
}