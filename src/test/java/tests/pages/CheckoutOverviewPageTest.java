package tests.pages;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import tests.base.BaseTest;


public class CheckoutOverviewPageTest extends BaseTest {

    private CheckoutOverviewPage overviewPage;
    private static final String PRODUCT_1 = "Sauce Labs Backpack";
    private static final String PRODUCT_2 = "Sauce Labs Bike Light";
    private static final double PRODUCT_1_PRICE = 29.99;
    private static final double PRODUCT_2_PRICE = 9.99;

    @BeforeMethod
    public void setUp() {
        // Setup test environment
        LoginPage loginPage = new LoginPage(driver);
        LandingPage landingPage = (LandingPage) loginPage.login("standard_user", "secret_sauce");
        
        overviewPage =landingPage.addItemToCart(PRODUCT_1)
                   .addItemToCart(PRODUCT_2)
                   .getCart()
                   .navigateToCart()
                   .proceedToCheckout()
                   .enterShippingInformation("John", "Doe", "12345")
                   .continueToOverview();
        
//        overviewPage = new CheckoutOverviewPage(driver);
    }

    @Test(priority = 0)
    public void testOrderSummaryDetailsDisplayed() {
        
    	Assert.assertTrue(overviewPage.isPageDisplayed(), "Checkout overview page not displayed");
        Assert.assertFalse(overviewPage.getPaymentInformation().isEmpty(), "Payment information missing");
        Assert.assertFalse(overviewPage.getShippingInformation().isEmpty(), "Shipping information missing");
        Assert.assertTrue(overviewPage.getCartItemCount() == 2, "Incorrect number of items in cart");
    }

    @Test(priority = 1)
    public void testPriceCalculations() {
        
    	double expectedSubtotal = PRODUCT_1_PRICE + PRODUCT_2_PRICE;
        double actualSubtotal = overviewPage.getSubtotal();
        
        double tax = overviewPage.getTax();
        double total = overviewPage.getTotal();
        
        Assert.assertEquals(actualSubtotal, expectedSubtotal, 0.001, "Subtotal calculation incorrect");
        Assert.assertTrue(tax > 0, "Tax amount not calculated");
        Assert.assertEquals(total, actualSubtotal + tax, 0.001, "Total calculation incorrect");
    }

    @Test(priority = 2)
    public void testTotalCalculationMethod() {
        
    	Assert.assertTrue(overviewPage.verifyTotalCalculation(), 
            "Total amount does not match subtotal + tax");
    }

    @Test(priority = 3)
    public void testNavigationToCartPage() {
        
    	CartPage cartPage = overviewPage.cancelCheckout();
        Assert.assertTrue(cartPage.isPageDisplayed(), "Cart page not displayed after cancellation");
    }

    @Test(priority = 4)
    public void testNavigationToCompletePage() {
        
    	CheckoutCompletePage completePage = overviewPage.finishCheckout();
        Assert.assertTrue(completePage.isPageDisplayed(), "Checkout complete page not displayed");
    }
}