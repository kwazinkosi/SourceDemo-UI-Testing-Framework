package tests.pages;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.Assert;
import pages.LandingPage;
import pages.LoginPage;
import tests.base.BaseTest;
import utils.LoggingManager;
import utils.dataproviders.SourceDemoDataProviders;
import utils.dataproviders.models.LoginTestData;
import pages.BasePage;

public class LoginPageTest extends BaseTest {

	private LoginPage loginPage;

	@BeforeMethod
	@Override
	public void setup() {
		driver = driverFactory.initDriver(browserName, browserMode);
		loginPage = new LoginPage(driver);
	}

	@Test(priority = 0, description = "Verify login functionality with different credentials", dataProvider = "loginData", dataProviderClass = SourceDemoDataProviders.class)
	public void testLoginFunctionality(LoginTestData data) {

		loginPage.navigateTo(baseUrl);
		String username = data.getUsername();
		String password = data.getPassword();
		String expectedResult = data.getExpectedResult();
		String message = data.getMessage();
		String TCID = data.getTestCaseId();

		Assert.assertTrue(loginPage.isPageDisplayed(), "Login page not displayed");

		// Perform login
		BasePage resultPage = loginPage.login(username, password);

		if ("success".equals(expectedResult)) {
			// Verify successful login
			Assert.assertTrue(resultPage instanceof LandingPage, "Not redirected to landing page");
			LandingPage landingPage = (LandingPage) resultPage;
			Assert.assertTrue(landingPage.isPageDisplayed(), "Landing page not displayed after login");
			LoggingManager.info(TCID + " in LoginPageTest::testLoginFunctionality PASSED!");
			System.out.println(TCID + " in LoginPageTest::testLoginFunctionality PASSED!");

		} else {
			// Verify error state
			Assert.assertTrue(resultPage instanceof LoginPage, "Unexpected page after failed login");
			Assert.assertEquals(loginPage.getErrorMessage(), message);
			LoggingManager.info(TCID + " in LoginPageTest::testLoginFunctionality PASSED!");
			System.out.println(TCID + " in LoginPageTest::testLoginFunctionality PASSED!");
		}
	}

}