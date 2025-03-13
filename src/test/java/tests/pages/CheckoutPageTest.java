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
import utils.dataproviders.SourceDemoDataProviders;
import utils.dataproviders.models.CheckoutTestData;

public class CheckoutPageTest extends BaseTest {

	private CheckoutPage checkoutPage;
	private static final String PRODUCT_NAME = "Sauce Labs Backpack";
	private WebDriver driver;

	@BeforeMethod(dependsOnMethods = { "setup" })
	public void classSetUp() {

		logStep("Setting up test: Adding item to cart and navigating to checkout page");
		driver = DriverFactory.getDriver();
		landingPage.addItemToCart(PRODUCT_NAME);
		checkoutPage = landingPage.getCart().navigateToCart().proceedToCheckout();
		logStep("Navigated to checkout page");
	}

	@Test(priority = 0, description = "Validate checkout form submissions with various test data", dataProvider = "checkoutData", dataProviderClass = SourceDemoDataProviders.class)
	public void testCheckoutFormSubmissions(CheckoutTestData data) {
		
		// Step 1: Extract test data
		String testCaseID = data.getTestCaseId();
		String firstName = data.getFirstName();
		String lastName = data.getLastName();
		String zipCode = data.getZipCode();
		String expectedResult = data.getExpectedResult();
		
		ExtentTest testCase = createNestedStep("Testing checkout form submission for test case: " + data.getTestCaseId());

		// Step 2: Enter shipping information
		checkoutPage.enterShippingInformation(firstName, lastName, zipCode);
		testCase.info("Entered shipping information: First Name=" + firstName + ", Last Name=" + lastName + ", Zip Code="
				+ zipCode);

		testCase.info("Submitting form");
		// Step 3: Verify expected result
		if (expectedResult.equalsIgnoreCase("success")) {
			// Step 3a: Verify successful submission
			BasePage overviewPage = (CheckoutOverviewPage) checkoutPage.continueToOverview();
			Assert.assertTrue(overviewPage.isPageDisplayed(), "Checkout overview page not displayed for valid data");
			testCase.info("Checkout overview page displayed successfully");

			LoggingManager
					.info(testCaseID + " (expected success) in CheckoutPageTests::testCheckoutFormSubmissions PASSED!");
			System.out.println(
					testCaseID + " (expected success) in CheckoutPageTests::testCheckoutFormSubmissions PASSED!");
		} else {
			// Step 3b: Verify error message
			checkoutPage.continueToOverview();
			Assert.assertEquals(checkoutPage.getErrorMessage(), expectedResult,
					"Unexpected error message for test case: " + testCaseID);
			testCase.info("Error message displayed: " + checkoutPage.getErrorMessage());

			LoggingManager
					.info(testCaseID + " (expected error) in CheckoutPageTests::testCheckoutFormSubmissions PASSED!");
			System.out.println(
					testCaseID + " (expected error) in CheckoutPageTests::testCheckoutFormSubmissions PASSED!");
		}

		logStepWithScreenshot("Checkout form submission verified for test case: " + testCaseID);
	}

	@Test(priority = 1, description = "Ensure canceling checkout redirects back to cart", retryAnalyzer = RetryAnalyzer.class)
	public void testCancelCheckoutReturnsToCart() {
		logStep("Testing cancellation of checkout");

		// Step 1: Cancel checkout
		CartPage cartPage = checkoutPage.cancelCheckout();
		logStep("Checkout canceled");

		// Step 2: Verify navigation back to cart page
		Assert.assertTrue(cartPage.isPageDisplayed(), "Should return to cart page after cancellation");
		logStep("Navigated back to cart page");

		logStepWithScreenshot("Checkout cancellation verified successfully");
		LoggingManager.info("CheckoutPageTests::testCancelCheckoutReturnsToCart PASSED!");
		System.out.println("CheckoutPageTests::testCancelCheckoutReturnsToCart PASSED!");
	}

	@Test(priority = 2, description = "Verify form clear functionality in the checkout page")
	public void testFormClearFunctionality() {
		logStep("Testing form clear functionality");

		// Step 1: Enter shipping information
		checkoutPage.enterShippingInformation("Test", "User", "67890");
		logStep("Entered shipping information: First Name=Test, Last Name=User, Zip Code=67890");

		// Step 2: Clear form
		checkoutPage.clearForm();
		logStep("Form cleared");

		// Step 3: Verify fields are empty
		Assert.assertTrue(checkoutPage.getEnteredFirstName().isEmpty(), "First name field not cleared");
		Assert.assertTrue(checkoutPage.getEnteredLastName().isEmpty(), "Last name field not cleared");
		Assert.assertTrue(checkoutPage.getEnteredPostalCode().isEmpty(), "Postal code field not cleared");
		logStep("All form fields are empty");

		logStepWithScreenshot("Form clear functionality verified successfully");
		LoggingManager.info("CheckoutPageTests::testFormClearFunctionality PASSED!");
		System.out.println("CheckoutPageTests::testFormClearFunctionality PASSED!");
	}

	@Test(priority = 3, description = "Test handling of special characters in postal code fields", retryAnalyzer = RetryAnalyzer.class)
	public void testSpecialCharactersInPostalCode() {
		logStep("Testing special characters in postal code");

		// Step 1: Enter shipping information with special characters
		checkoutPage.enterShippingInformation("John", "Doe", "!@#$%");
		logStep("Entered shipping information: First Name=John, Last Name=Doe, Zip Code=!@#$%");

		// Step 2: Continue to overview
		checkoutPage.continueToOverview();
		logStep("Continued to checkout overview");

		// Step 3: Verify overview page is displayed
		Assert.assertTrue(new CheckoutOverviewPage(driver).isPageDisplayed(),
				"Special characters in postal code should be accepted");
		logStep("Checkout overview page displayed successfully");

		logStepWithScreenshot("Special characters in postal code verified successfully");
		LoggingManager.info("CheckoutPageTests::testSpecialCharactersInPostalCode PASSED!");
		System.out.println("CheckoutPageTests::testSpecialCharactersInPostalCode PASSED!");
	}
}