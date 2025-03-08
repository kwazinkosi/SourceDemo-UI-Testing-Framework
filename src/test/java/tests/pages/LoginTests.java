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

public class LoginTests extends BaseTest {

	

	@BeforeMethod
    @Override
    public void setup() {
        driver = driverFactory.initDriver(browserName, browserMode);
        loginPage = new LoginPage(driver);
        loginPage.navigateTo(LOGIN_PAGE_URL);
       
    }
	
	@Test(priority = 0, dataProvider = "loginData", dataProviderClass = SourceDemoDataProviders.class)
	public void testLoginFunctionality(LoginTestData data) {

		String username = data.getUsername();
		String password = data.getPassword();
		String expectedResult = data.getExpectedResult();
		String message = data.getMessage();
		String TCID = data.getTestCaseId();
		LoginPage loginPage = new LoginPage(driver);

		// Navigate to login page
		loginPage.navigateTo(LOGIN_PAGE_URL);
		Assert.assertTrue(loginPage.isPageDisplayed(), "Login page not displayed");

		// Perform login
		BasePage resultPage = loginPage.login(username, password);

		if ("success".equals(expectedResult)) {
			// Verify successful login
			Assert.assertTrue(resultPage instanceof LandingPage, "Not redirected to landing page");
			LandingPage landingPage = (LandingPage) resultPage;
			Assert.assertTrue(landingPage.isPageDisplayed(), "Landing page not displayed after login");
			LoggingManager.info(TCID + " in LoginTests::testLoginFunctionality PASSED!");
			System.out.println(TCID + " in LoginTests::testLoginFunctionality PASSED!");

		} else {
			// Verify error state
			Assert.assertTrue(resultPage instanceof LoginPage, "Unexpected page after failed login");
			Assert.assertEquals(loginPage.getErrorMessage(), message);
			LoggingManager.info(TCID + " in LoginTests::testLoginFunctionality PASSED!");
			System.out.println(TCID + " in LoginTests::testLoginFunctionality PASSED!");
		}
	}

}