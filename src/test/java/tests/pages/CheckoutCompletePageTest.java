package tests.pages;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import tests.base.BaseTest;

public class CheckoutCompletePageTest extends BaseTest {

    private CheckoutCompletePage completePage;
    private static final String PRODUCT_NAME = "Sauce Labs Backpack";

    @BeforeMethod
    public void setUp() {
        // Complete checkout to reach success page
        LoginPage loginPage = new LoginPage(driver);
        LandingPage landingPage = (LandingPage) loginPage.login("standard_user", "secret_sauce");
        
        landingPage.addItemToCart(PRODUCT_NAME)
                   .getCart()
                   .navigateToCart()
                   .proceedToCheckout()
                   .enterShippingInformation("John", "Doe", "12345")
                   .continueToOverview()
                   .finishCheckout();
        
        completePage = new CheckoutCompletePage(driver);
    }

    @Test
    public void testPageDisplayState() {
        // Verify URL and core elements
        Assert.assertTrue(completePage.isPageDisplayed(), 
            "Checkout complete page not properly displayed");
        
        // Verify visual elements
        Assert.assertEquals(completePage.getPageTitle(), "Checkout: Complete!",
            "Page title mismatch");
    }

    @Test
    public void testSuccessMessageContent() {
        String actualMessage = completePage.getSuccessMessage();
        Assert.assertEquals(actualMessage, CheckoutCompletePage.SUCCESS_MESSAGE,
            "Success message text mismatch.\nExpected: " + CheckoutCompletePage.SUCCESS_MESSAGE +
            "\nActual: " + actualMessage);
    }

    @Test
    public void testOrderSuccessStatus() {
        Assert.assertTrue(completePage.isOrderSuccessful(),
            "Order success status verification failed");
    }

    @Test
    public void testNavigationBackToProducts() {
        LandingPage landingPage = completePage.navigateBackToHome();
        
        // Verify return to inventory
        Assert.assertTrue(landingPage.isPageDisplayed(),
            "Failed to return to landing page");
        Assert.assertTrue(landingPage.getCart().getCurrentCartCount() == 0,
            "Cart should be empty after completed order");
    }

    @Test
    public void testPageRefreshPersistsState() {
    	
        driver.navigate().refresh();
        completePage.waitForPageLoad();
        
        Assert.assertTrue(completePage.isPageDisplayed(),
            "Page state not persisted after refresh");
        Assert.assertTrue(completePage.isOrderSuccessful(),
            "Order success status lost after refresh");
    }
}