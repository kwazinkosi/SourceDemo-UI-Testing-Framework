//package tests.components;
//
//import org.testng.Assert;
//import org.testng.annotations.Test;
//
//import components.MenuComponent;
//import pages.LandingPage;
//import pages.LoginPage;
//import tests.base.BaseTest;
//import utils.ConfigReader;
//
//public class MenuComponentTest extends BaseTest {
//    
//    @Test
//    public void verifyMenuLinksNavigation() {
//        LandingPage landingPage = loginWithStandardUser();
//        MenuComponent menu = landingPage.getMenu();
//        
//        // Test About link
//        menu.openMenu().selectLink("About");
//        Assert.assertTrue(driver.getCurrentUrl().contains("saucelabs.com"),
//            "Should navigate to About page");
//        
//        // Test Logout
//        driver.navigate().back();
//        menu.openMenu().selectLink("Logout");
//        Assert.assertTrue(new LoginPage(driver).isPageDisplayed(),
//            "Should return to login page after logout");
//    }
//
//    @Test
//    public void verifyResetAppStateFunctionality() {
//        LandingPage landingPage = loginWithStandardUser();
//        landingPage.addItemToCart(0);
//        
//        landingPage.getMenu().openMenu().selectLink("Reset App State");
//        Assert.assertEquals(landingPage.getCart().getCurrentCartCount(), 0,
//            "Cart should be empty after reset");
//    }
//}