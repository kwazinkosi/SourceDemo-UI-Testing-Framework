package tests.pages;

import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import components.CartComponent;
import components.MenuComponent;
import components.ProductComponent;
import components.SocialsComponent;
import pages.LandingPage;
import pages.LandingPage.SortOption;
import pages.LoginPage;
import pages.ProductDetailsPage;
import tests.base.BaseTest;
import utils.ConfigReader;
import utils.LoggingManager;
import utils.dataproviders.SortDataProvider;
import utils.dataproviders.SourceDemoDataProviders;
import utils.dataproviders.models.MenuItemsTestData;
import utils.dataproviders.models.SocialsTestData;

public class LandingPageTest extends BaseTest {

	private LandingPage landingPage;
	private LoginPage loginPage;
	private static final String validUsername = ConfigReader.getProperty("valid_login_username");
	private static final String validPassword = ConfigReader.getProperty("valid_login_password");
	private static final String LANDING_PAGE_URL = ConfigReader.getProperty("base_url");

	
	@BeforeMethod
	public void setUpTest() {
		loginPage = new LoginPage(driver);
		loginPage.navigateTo(LANDING_PAGE_URL);
		

		landingPage = (LandingPage) loginPage.login(validUsername, validPassword);
		landingPage.waitForPageLoad();
	}

	@AfterMethod
	public void cleanUp() {
		landingPage.getMenu().resetAppState();
	}

	@Test(priority = 0)
	public void testLandingPageComponentsDisplayed() {
		
		Assert.assertTrue(landingPage.isPageDisplayed(), "Landing page not displayed");
		Assert.assertTrue(landingPage.getMenu().isDisplayed(), "Menu component missing");
		Assert.assertTrue(landingPage.getCart().isBadgeDisplayed(), "Cart badge missing");
		Assert.assertTrue(landingPage.getSocials().isDisplayed(), "Social media links on footer are missing");
		LoggingManager.info("LandingPageTests::testLandingPageComponentsDisplayed PASSED!");
		System.out.println("LandingPageTests::testLandingPageComponentsDisplayed PASSED!");

	}

	@Test(priority = 1, dataProvider = "sortOptions", dataProviderClass = SortDataProvider.class)
	public void testSortingFunctionality(SortOption option) {

		landingPage.selectSortOption(option);

		boolean isSorted = (option == SortOption.A_TO_Z || option == SortOption.Z_TO_A)
				? landingPage.verifyProductSortingByName(option)
				: landingPage.verifyProductSortingByPrice(option);

		Assert.assertTrue(isSorted, "Sorting failed for: " + option.name());

		LoggingManager.info(option.toString()+" sorting in LandingPageTests::testSortingFunctionality PASSED!");
		System.out.println(option.toString()+" sorting in LandingPageTests::testSortingFunctionality PASSED!");
	}

	@Test(priority = 2)
	public void testAddAndRemoveItemFromCartUpdatesCartCount() {

		CartComponent cart = landingPage.getCart();
		int initialCount = cart.getCurrentCartCount();

		landingPage = landingPage.addItemToCart(0);
		int countAfterAddition = cart.getCurrentCartCount();
		Assert.assertEquals(countAfterAddition,  initialCount + 1);

		landingPage.removeItemFromCart(0);
		int countAfterRemoval = cart.getCurrentCartCount();
		Assert.assertEquals(initialCount, countAfterRemoval);

		LoggingManager.info("LandingPageTests::testAddAndRemoveItemFromCartUpdatesCartCount PASSED!");
		System.out.println("LandingPageTests::testAddAndRemoveItemFromCartUpdatesCartCount PASSED!");
	}

	@Test(priority = 3)
	public void testProductDetailsNavigation() {
		
		ProductComponent product = landingPage.getProductByIndex(0);
		ProductDetailsPage detailsPage = landingPage.viewProductDetails(0);

		SoftAssert softAssert = new SoftAssert();

		softAssert.assertTrue(detailsPage.isPageDisplayed(), "Details page not opened");
		softAssert.assertEquals(detailsPage.getProductName(), product.getProductName(), "Product name mismatch");
		softAssert.assertEquals(detailsPage.getProductDescription(), product.getProductDescription(),
				"Product description mismatch");
		softAssert.assertEquals(detailsPage.getProductPrice(), product.getProductPrice(), "Product price mismatch");

		softAssert.assertAll(); // Ensure all assertions are checked
		LoggingManager.info("LandingPageTests::testProductDetailsNavigation PASSED!");
		System.out.println("LandingPageTests::testProductDetailsNavigation PASSED!");
	}

	@Test(priority = 4, dataProvider = "menuItemsData", dataProviderClass = SourceDemoDataProviders.class)
	public void testMenuNavigation(MenuItemsTestData data) {
		
		String testCaseID = data.getTestCaseId();
		String menuItem =data.getMenuItem();
		String expectedDestUrl =data.getDestinationUrl();
		MenuComponent menu =landingPage.getMenu().openMenu();

		Assert.assertTrue(landingPage.getMenu().isDisplayed());
		menu.selectMenuItem(menuItem);
		String actualUrl = driver.getCurrentUrl().equals("https://www.saucedemo.com/inventory.html")?"":driver.getCurrentUrl();
		
		Assert.assertTrue(actualUrl.contains(expectedDestUrl), "Navigation to " + menuItem + " failed, Link might be disabled");
		LoggingManager.info(testCaseID + " in LandingPageTests::testMenuNavigation PASSED!");
		System.out.println(testCaseID + " in LandingPageTests::testMenuNavigation PASSED!");

	}

	@Test(priority = 5, dataProvider = "socialsData", dataProviderClass = SourceDemoDataProviders.class)
	public void testSocialMediaLinks(SocialsTestData data) {

		String testCaseID = data.getTestCaseId();
		String socialMediaName = data.getSocialMediaName();
		String expectedTitle = data.getExpectedTitle();
		SocialsComponent socials = landingPage.getSocials();

		socials.clickOnSocialLink(socialMediaName);

		Set<String> windows = driver.getWindowHandles();

		// Verify that we have more than one window open
		Assert.assertTrue(windows.size() > 1, "No new window was opened when clicking " + socialMediaName);

		String originalWindow = driver.getWindowHandle();
		// Switch to the new window
		windows.stream().filter(handle -> !handle.equals(originalWindow)).findFirst()
				.ifPresent(newWindow -> driver.switchTo().window(newWindow));

		Assert.assertTrue(driver.getTitle().contains(expectedTitle), socialMediaName + " link validation failed");

		LoggingManager.info(testCaseID + " in LandingPageTests::testSocialMediaLinks PASSED!");
		System.out.println(testCaseID + " in LandingPageTests::testSocialMediaLinks PASSED!");
		driver.close();
		driver.switchTo().window(originalWindow);
	}

	@Test(priority = 7)
	public void testCartPersistsAfterRefresh() {
		int initialCartCount = landingPage.getCart().getCurrentCartCount();

		landingPage.addItemToCart(0);
		driver.navigate().refresh();
		landingPage.waitForPageLoad();

		int updatedCartCount = landingPage.getCart().getCurrentCartCount();
		Assert.assertEquals(updatedCartCount, initialCartCount + 1, "Cart state did not persist after refresh");
	}

}