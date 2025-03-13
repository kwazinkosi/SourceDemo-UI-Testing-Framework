package tests.pages;

import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import components.CartComponent;
import components.MenuComponent;
import components.SocialsComponent;
import listeners.RetryAnalyzer;
import pages.LandingPage.SortOption;
import pages.ProductDetailsPage;
import tests.base.BaseTest;
import utils.DriverFactory;
import utils.LoggingManager;
import utils.dataproviders.SortDataProvider;
import utils.dataproviders.SourceDemoDataProviders;
import utils.dataproviders.models.MenuItemsTestData;
import utils.dataproviders.models.SocialsTestData;

public class LandingPageTest extends BaseTest {

	private static final String PRODUCT_1 = "Sauce Labs Backpack";
	private static final String PRODUCT_1_DESCRIPTION = "arry.allTheThings() with the sleek, streamlined Sly Pack that melds uncompromising style with unequaled laptop and tablet protection.";
	private static final String PRODUCT_1_PRICE = "$29.99";
	private WebDriver driver;

	@BeforeMethod(dependsOnMethods = { "setup" })
	public void testSetup() {
		driver = DriverFactory.getDriver();

		logStep("Driver initialized for test setup.");
	}

	@AfterMethod
	public void cleanUp() {
		landingPage.getMenu().resetAppState();
		logStep("Application state reset after test.");
	}

	@Test(priority = 0, description = "Ensure all landing page components are displayed correctly", retryAnalyzer = RetryAnalyzer.class)
	public void testLandingPageComponentsDisplayed() {
		logStep("Verifying landing page components are displayed");

		// Step 1: Verify landing page is displayed
		Assert.assertTrue(landingPage.isPageDisplayed(), "Landing page not displayed");
		logStep("Landing page is displayed");

		// Step 2: Verify menu component
		Assert.assertTrue(landingPage.getMenu().isDisplayed(), "Menu component missing");
		logStep("Menu component is displayed");

		// Step 3: Verify cart badge
		Assert.assertTrue(landingPage.getCart().isBadgeDisplayed(), "Cart badge missing");
		logStep("Cart badge is displayed");

		// Step 4: Verify social media links
		Assert.assertTrue(landingPage.getSocials().isDisplayed(), "Social media links on footer are missing");
		logStep("Social media links are displayed");

		logStepWithScreenshot("All landing page components are displayed correctly");
		LoggingManager.info("LandingPageTests::testLandingPageComponentsDisplayed PASSED!");
		System.out.println("LandingPageTests::testLandingPageComponentsDisplayed PASSED!");
	}

	@Test(priority = 1, description = "Verify sorting functionality for products", dataProvider = "sortOptions", dataProviderClass = SortDataProvider.class)
	public void testSortingFunctionality(SortOption option) {
		boolean isSorted = false;
		logStep("Testing sorting functionality for: " + option.name());

		// Step 1: Select sorting option
		landingPage.selectSortOption(option);
		logStep("Selected sorting option: " + option.name());

		// Step 2: Verify sorting
		if (option == SortOption.A_TO_Z || option == SortOption.Z_TO_A) {
			logStep("Verifying product sorting by name");
			isSorted = landingPage.verifyProductSortingByName(option);
		} else {
			logStep("Verifying product sorting by price");
			isSorted = landingPage.verifyProductSortingByPrice(option);
		}

		Assert.assertTrue(isSorted, "Sorting failed for: " + option.name());

		logStepWithScreenshot("Products sorted successfully by " + option.name());
		LoggingManager.info(option.toString() + " sorting in LandingPageTests::testSortingFunctionality PASSED!");
		System.out.println(option.toString() + " sorting in LandingPageTests::testSortingFunctionality PASSED!");
	}

	@Test(priority = 2, description = "Check if adding/removing an item updates cart count correctly")
	public void testAddAndRemoveItemFromCartUpdatesCartCount() {
		ExtentTest addStep = createNestedStep("Add Item to Cart");
		// Step 1: Get initial cart count
		CartComponent cart = landingPage.getCart();
		int initialCount = cart.getCurrentCartCount();
		addStep.info("Initial cart count: " + initialCount);

		// Step 2: Add item to cart
		landingPage = landingPage.addItemToCart(0);
		int countAfterAddition = cart.getCurrentCartCount();
		Assert.assertEquals(countAfterAddition, initialCount + 1, "Cart count did not increase after adding item");
		addStep.info("Item added to cart. Updated cart count: " + countAfterAddition);

		// Step 3: Remove item from cart
		ExtentTest removeStep = createNestedStep("Remove Item from Cart");
		landingPage.removeItemFromCart(0);
		int countAfterRemoval = cart.getCurrentCartCount();
		Assert.assertEquals(initialCount, countAfterRemoval, "Cart count did not decrease after removing item");
		removeStep.pass("Item removed from cart. Final cart count: " + countAfterRemoval);

		logStepWithScreenshot("Cart count updated correctly after adding/removing items");
		LoggingManager.info("LandingPageTests::testAddAndRemoveItemFromCartUpdatesCartCount PASSED!");
		System.out.println("LandingPageTests::testAddAndRemoveItemFromCartUpdatesCartCount PASSED!");
	}

	@Test(priority = 3, description = "Ensure product details page is accessible from landing page", retryAnalyzer = RetryAnalyzer.class)
	public void testProductDetailsNavigation() {
		logStep("Testing navigation to product details page");

		// Step 1: Navigate to product details page
		ProductDetailsPage detailsPage = landingPage.viewProductDetails(0);
		logStep("Navigated to product details page");

		// Step 2: Verify product details
		ExtentTest nestedStep = createNestedStep("Verifying product details");
		nestedStep.log(Status.INFO, "Starting verification of product details");

		SoftAssert softAssert = new SoftAssert();
		softAssert.assertTrue(detailsPage.isPageDisplayed(), "Details page not opened");
		nestedStep.log(Status.INFO, "Product details page is displayed");

		softAssert.assertEquals(detailsPage.getProductName(), PRODUCT_1, "Product name mismatch");
		nestedStep.log(Status.INFO, "Product name verified");

		softAssert.assertEquals(detailsPage.getProductDescription(), PRODUCT_1_DESCRIPTION,
				"Product description mismatch");
		nestedStep.log(Status.INFO, "Product description verified");

		softAssert.assertEquals(detailsPage.getProductPrice(), PRODUCT_1_PRICE, "Product price mismatch");
		nestedStep.log(Status.INFO, "Product price verified");

		softAssert.assertAll();
		nestedStep.log(Status.INFO, "All assertions passed for product details page");

		logStep("All assertions passed for product details page.");
		logStepWithScreenshot("Product details verified successfully");
		LoggingManager.info("LandingPageTests::testProductDetailsNavigation PASSED!");
		System.out.println("LandingPageTests::testProductDetailsNavigation PASSED!");
	}

	@Test(priority = 4, description = "Validate navigation through menu items", dataProvider = "menuItemsData", dataProviderClass = SourceDemoDataProviders.class)
	public void testMenuNavigation(MenuItemsTestData data) {

		String testCaseID = data.getTestCaseId();
		String menuItem = data.getMenuItem();
		String expectedDestUrl = data.getDestinationUrl();
		MenuComponent menu = landingPage.getMenu().openMenu();

		logStep("Testing menu navigation for: " + menuItem);

		// Step 1: Open menu
		Assert.assertTrue(landingPage.getMenu().isDisplayed(), "Menu not displayed");
		logStep("Menu opened successfully");

		// Step 2: Select menu item
		menu.selectMenuItem(menuItem);
		logStep("Selected menu item: " + menuItem);

		// Step 3: Verify navigation
		String actualUrl = driver.getCurrentUrl().equals("https://www.saucedemo.com/inventory.html") ? ""
				: driver.getCurrentUrl();
		Assert.assertTrue(actualUrl.contains(expectedDestUrl),
				"Navigation to " + menuItem + " failed, Link might be disabled");
		logStep("Successfully navigated to: " + expectedDestUrl);

		logStepWithScreenshot(testCaseID + ": Menu navigation verified successfully");
		LoggingManager.info(testCaseID + " in LandingPageTests::testMenuNavigation PASSED!");
		System.out.println(testCaseID + " in LandingPageTests::testMenuNavigation PASSED!");
	}

	@Test(priority = 5, description = "Ensure social media links on the landing page are functional", dataProvider = "socialsData", dataProviderClass = SourceDemoDataProviders.class)
	public void testSocialMediaLinks(SocialsTestData data) {

		String testCaseID = data.getTestCaseId();
		String socialMediaName = data.getSocialMediaName();
		String expectedTitle = data.getExpectedTitle();
		SocialsComponent socials = landingPage.getSocials();
		ExtentTest clickStep = createNestedStep("Click Social Link");
		
	
		clickStep.info("Testing social media link: " + socialMediaName);

		// Step 1: Click on social media link
		socials.clickOnSocialLink(socialMediaName);
		clickStep.pass("Clicked on social media link: " + socialMediaName);

		// Step 2: Verify new window opens
		ExtentTest windowStep = createNestedStep("Window Handling");
		Set<String> windows = driver.getWindowHandles();
		Assert.assertTrue(windows.size() > 1, "No new window was opened when clicking " + socialMediaName);
		windowStep.info("New window opened successfully");

		// Step 3: Switch to new window and verify title
		String originalWindow = driver.getWindowHandle();
		windows.stream().filter(handle -> !handle.equals(originalWindow)).findFirst()
				.ifPresent(newWindow -> driver.switchTo().window(newWindow));
		Assert.assertTrue(driver.getTitle().contains(expectedTitle), socialMediaName + " link validation failed");
		windowStep.pass("Switched successfully, title of new window: " + driver.getTitle());

		// Step 4: Close new window and switch back
		driver.close();
		driver.switchTo().window(originalWindow);
		windowStep.info("Closed new window and switched back to original window");

		logStepWithScreenshot(testCaseID + ": Social media link verified successfully");
		LoggingManager.info(testCaseID + " in LandingPageTests::testSocialMediaLinks PASSED!");
		System.out.println(testCaseID + " in LandingPageTests::testSocialMediaLinks PASSED!");
	}

	@Test(priority = 6, description = "Verify cart persistence after page refresh")
	public void testCartPersistsAfterRefresh() {
		logStep("Testing cart persistence after page refresh");

		// Step 1: Get initial cart count
		int initialCartCount = landingPage.getCart().getCurrentCartCount();
		logStep("Initial cart count: " + initialCartCount);

		// Step 2: Add item to cart
		landingPage.addItemToCart(0);
		logStep("Item added to cart");

		// Step 3: Refresh page
		driver.navigate().refresh();
		landingPage.waitForPageLoad();
		logStep("Page refreshed");

		// Step 4: Verify cart count
		int updatedCartCount = landingPage.getCart().getCurrentCartCount();
		Assert.assertEquals(updatedCartCount, initialCartCount + 1, "Cart state did not persist after refresh");
		logStep("Cart count after refresh: " + updatedCartCount);

		logStepWithScreenshot("Cart state persisted after refresh");
		LoggingManager.info("LandingPageTests::testCartPersistsAfterRefresh PASSED!");
		System.out.println("LandingPageTests::testCartPersistsAfterRefresh PASSED!");
	}

}