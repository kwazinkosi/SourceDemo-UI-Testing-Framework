package tests.pages;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import tests.base.BaseTest;
import utils.dataproviders.SourceDemoDataProviders;

public class CheckoutPageTest extends BaseTest {

	private CheckoutPage checkoutPage;
	private static final String PRODUCT_NAME = "Sauce Labs Backpack";

	@BeforeMethod
	public void setUp() {
		// Setup test environment
		LoginPage loginPage = new LoginPage(driver);
		LandingPage landingPage = (LandingPage) loginPage.login("standard_user", "secret_sauce");
		landingPage.addItemToCart(PRODUCT_NAME);
		checkoutPage = landingPage.getCart().navigateToCart().proceedToCheckout();
	}

	
	@Test(dataProvider = "checkoutData", dataProviderClass = SourceDemoDataProviders.class)
	public void testCheckoutFormSubmissions(String testCaseID, String firstName, String lastName, String zipCode,
			String expectedResult) {
		
		checkoutPage.enterShippingInformation(firstName, lastName, zipCode);

		if (expectedResult.equals("success")) {
			CheckoutOverviewPage overviewPage = checkoutPage.continueToOverview();
			Assert.assertTrue(overviewPage.isPageDisplayed(), "Checkout overview page not displayed for valid data");
		} else {
			Assert.assertEquals(checkoutPage.getErrorMessage(), expectedResult,
					"Unexpected error message for test case: " + testCaseID);
		}
	}

	@Test
	public void testCancelCheckoutReturnsToCart() {
		CartPage cartPage = checkoutPage.cancelCheckout();
		Assert.assertTrue(cartPage.isPageDisplayed(), "Should return to cart page after cancellation");
	}

	@Test
	public void testFormClearFunctionality() {
		checkoutPage.enterShippingInformation("Test", "User", "67890").clearForm();

		Assert.assertTrue(checkoutPage.getEnteredFirstName().isEmpty(), "First name field not cleared");
		Assert.assertTrue(checkoutPage.getEnteredLastName().isEmpty(), "Last name field not cleared");
		Assert.assertTrue(checkoutPage.getEnteredPostalCode().isEmpty(), "Postal code field not cleared");
	}

	@Test
	public void testInputFieldPersistence() {
		checkoutPage.enterShippingInformation("Alice", "Smith", "90210");

		Assert.assertEquals(checkoutPage.getEnteredFirstName(), "Alice", "First name input not persisted");
		Assert.assertEquals(checkoutPage.getEnteredLastName(), "Smith", "Last name input not persisted");
		Assert.assertEquals(checkoutPage.getEnteredPostalCode(), "90210", "Postal code input not persisted");
	}

	@Test
	public void testSpecialCharactersInPostalCode() {
		checkoutPage.enterShippingInformation("John", "Doe", "!@#$%").continueToOverview();

		Assert.assertTrue(new CheckoutOverviewPage(driver).isPageDisplayed(),
				"Special characters in postal code should be accepted");
	}
}