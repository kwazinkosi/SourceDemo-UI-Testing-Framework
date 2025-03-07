package tests.pages;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LandingPage;
import pages.ProductDetailsPage;
import tests.base.BaseTest;

public class ProductDetailsPageTest extends BaseTest {

    private ProductDetailsPage productDetailsPage;

    @BeforeMethod
    public void setUpTest() {
        LandingPage landingPage = new LandingPage(driver);
        landingPage.navigateTo("https://your-app-url.com/inventory.html");
        productDetailsPage = landingPage.viewProductDetails(0);
    }

    @Test
    public void testProductDetailsDisplayed() {
    	
        Assert.assertTrue(productDetailsPage.isPageDisplayed(), "Product details page not displayed");
        Assert.assertTrue(productDetailsPage.getProduct().isProductImageDispalyed(), "Product image not displayed");
        Assert.assertFalse(productDetailsPage.getProductName().isEmpty(), "Product name is empty");
        Assert.assertFalse(productDetailsPage.getProductDescription().isEmpty(), "Product description is empty");
        Assert.assertFalse(productDetailsPage.getProductPrice().isEmpty(), "Product price is empty");
    }

    @Test
    public void testAddRemoveItemFromCart() {
    	
        productDetailsPage.addItemToCart();
        Assert.assertTrue(productDetailsPage.isRemoveButtonVisible(), "Remove button not visible after adding to cart");

        productDetailsPage.removeItemFromCart();
        Assert.assertTrue(productDetailsPage.isAddToCartVisible(), "Add to cart button not visible after removal");
    }

    @Test
    public void testNavigateBackToProducts() {
        LandingPage landingPage = productDetailsPage.navigateBackToProducts();
        Assert.assertTrue(landingPage.isPageDisplayed(), "Did not return to landing page");
    }
}