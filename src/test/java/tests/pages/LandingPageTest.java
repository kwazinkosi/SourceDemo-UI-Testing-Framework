package tests.pages;

import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import components.CartComponent;
import components.ProductComponent;
import components.SocialsComponent;
import pages.LandingPage;
import pages.LandingPage.SortOption;
import pages.ProductDetailsPage;
import tests.base.BaseTest;
import utils.dataproviders.SortDataProvider;
import utils.dataproviders.SourceDemoDataProviders;

public class LandingPageTest extends BaseTest {

	private LandingPage landingPage;
	private static final String LANDING_PAGE_URL = "https://your-app-url.com/inventory.html";

	@BeforeMethod
	public void setUpTest() {

		landingPage = new LandingPage(driver);
		landingPage.navigateTo(LANDING_PAGE_URL);
		landingPage.waitForPageLoad();
	}

	@AfterMethod
	public void cleanUp() {
		landingPage.getMenu().resetAppState();
	}

	@Test(priority = 1)
	public void testLandingPageComponentsDisplayed() {
		Assert.assertTrue(landingPage.isPageDisplayed(), "Landing page not displayed");
		Assert.assertTrue(landingPage.getMenu().isDisplayed(), "Menu component missing");
		Assert.assertTrue(landingPage.getCart().isBadgeDisplayed(), "Cart badge missing");
		Assert.assertTrue(landingPage.getSocials().isDisplayed(), "Social media links on footer are missing");

	}

	@Test(priority = 2, dataProvider = "sortOptions", dataProviderClass = SortDataProvider.class)
	public void testSortingFunctionality(SortOption option) {

		landingPage.selectSortOption(option);

		boolean isSorted = (option == SortOption.A_TO_Z || option == SortOption.Z_TO_A)
				? landingPage.verifyProductSortingByName(option)
				: landingPage.verifyProductSortingByPrice(option);

		Assert.assertTrue(isSorted, "Sorting failed for: " + option.name());
	}

	@Test(priority = 3)
	public void testAddAndRemoveItemFromCartUpdatesCartCount() {

		CartComponent cart = landingPage.getCart();
		int initialCount = cart.getCurrentCartCount();

		landingPage = landingPage.addItemToCart(0);
		int countAfterAddition = cart.getCurrentCartCount();
		Assert.assertEquals(initialCount, countAfterAddition - 1);

		landingPage = landingPage.removeItemFromCart(0);
		int countAfterRemoval = cart.getCurrentCartCount();
		Assert.assertEquals(initialCount, countAfterRemoval);
	}

	@Test(priority = 4)
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
	}

	@Test(priority = 5, dataProvider = "MenuItemsData", dataProviderClass = SourceDemoDataProviders.class)
	public void testMenuNavigation(String testCaseID, String menuItem, String expectedDestUrl, String expectedPageTitle,
			String expectedResult) {
		landingPage.getMenu().openMenu().selectMenuItem(menuItem);

		String actualUrl = driver.getCurrentUrl();
		Assert.assertTrue(actualUrl.contains(expectedDestUrl),
				"Navigation to " + menuItem + " failed, Link might be disabled");

	}

	@Test(priority = 5, dataProvider = "SocialsData", dataProviderClass = SourceDemoDataProviders.class)
	public void testSocialMediaLinks(String testCaseID, String socialMediaName, String expectedTitle) {

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