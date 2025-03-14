package tests.pages;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
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
	@Parameters("browser") // Parameter from XML
	public void setup(@Optional String browser) {
		// Use the provided browser parameter, otherwise fallback to config file defined browser name
		if (browser != null && !browser.isEmpty()) {
		    browserName = browser;
		} else {
//		    browserName = ConfigReader.getProperty("browser"); // Replace with your config method if needed
		}
		System.out.println("Running tests on browser: " + browserName);
		LoggingManager.info("Running tests on browser: " +browserName);
		logStep("Running tests on browser:: " + browserName);
		driver = driverFactory.initDriver(browserName, browserMode);
		loginPage = new LoginPage(driver);
	}

	@Test(priority = 0, description = "Verify login functionality with different credentials", dataProvider = "loginData", dataProviderClass = SourceDemoDataProviders.class)
	public void testLoginFunctionality(LoginTestData data) {
		
		ExtentTest loginProcess = createNestedStep("Login Process");
		// Step 1: Navigate to login page
	    loginProcess.info("Navigating to login page: " + baseUrl);
	    loginPage.navigateTo(baseUrl);
	    Assert.assertTrue(loginPage.isPageDisplayed(), "Login page not displayed");

	    // Step 2: Extract test data
	    String username = data.getUsername();
	    String password = data.getPassword();
	    String expectedResult = data.getExpectedResult();
	    String message = data.getMessage();
	    String TCID = data.getTestCaseId();

	    // Step 3: Log test case details
	    loginProcess.info("Executing test case: " + TCID);
	    loginProcess.info("Username: " + username + ", Password: " + password);
	    loginProcess.info("Expected Result: " + expectedResult + ", Message: " + message);

	    // Step 4: Perform login
	    loginProcess.info("Entering username: " + username);
	    loginProcess.info("Entering password: " + password);
	    BasePage resultPage = loginPage.login(username, password);

	    // Step 5: Verify login result
	    ExtentTest verification = createNestedStep("Result Verification");
	    if ("success".equals(expectedResult)) {
	        // Step 5a: Verify successful login
	    	verification.info("Verifying successful login");
	        Assert.assertTrue(resultPage instanceof LandingPage, "Not redirected to landing page");
	        LandingPage landingPage = (LandingPage) resultPage;
	        Assert.assertTrue(landingPage.isPageDisplayed(), "Landing page not displayed after login");
	        logStepWithScreenshot("Login successful. Redirected to landing page.");
	    } else {
	        // Step 5b: Verify failed login
	    	verification.info("Verifying failed login");
	        Assert.assertTrue(resultPage instanceof LoginPage, "Unexpected page after failed login");
	        Assert.assertEquals(loginPage.getErrorMessage(), message, "Error message mismatch");
	        logStepWithScreenshot("Login failed. Error message: " + loginPage.getErrorMessage());
	    }

	    // Step 6: Log test result
	    logStep("Test case " + TCID + " PASSED!");
	    LoggingManager.info(TCID + " in LoginPageTest::testLoginFunctionality PASSED!");
	    System.out.println(TCID + " in LoginPageTest::testLoginFunctionality PASSED!");
	}

}