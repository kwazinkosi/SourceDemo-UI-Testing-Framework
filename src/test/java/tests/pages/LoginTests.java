package tests.pages;

import org.testng.annotations.Test;


import org.testng.annotations.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pages.LandingPage;
import pages.LoginPage;
import tests.base.BaseTest;
import utils.ConfigReader;
import utils.DriverFactory;
import utils.dataproviders.SourceDemoDataProviders;
import pages.BasePage;

public class LoginTests extends BaseTest {

	private String urlToLandingPage;
	//Driver is init in BaseTest
	
	@BeforeClass
	public void setUp() {
		urlToLandingPage =ConfigReader.getProperty("base_url");
	}
	
    
    @Test(priority =0, dataProvider = "loginData", dataProviderClass = SourceDemoDataProviders.class)
    public void testLoginFunctionality(String username, String password, String message, String expectedResult) {
        
    	LoginPage loginPage = new LoginPage(driver);
        
        // Navigate to login page
        loginPage.navigateTo(urlToLandingPage);
        Assert.assertTrue(loginPage.isPageDisplayed(), "Login page not displayed");

        // Perform login
        BasePage resultPage = loginPage.login(username, password);

        if ("success".equals(expectedResult)) {
            // Verify successful login
            Assert.assertTrue(resultPage instanceof LandingPage, "Not redirected to landing page");
            LandingPage landingPage = (LandingPage) resultPage;
            Assert.assertTrue(landingPage.isPageDisplayed(), "Landing page not displayed after login");
        } else {
            // Verify error state
            Assert.assertTrue(resultPage instanceof LoginPage, "Unexpected page after failed login");
            Assert.assertEquals(loginPage.getErrorMessage(), message);
        }
    }
    
 
}