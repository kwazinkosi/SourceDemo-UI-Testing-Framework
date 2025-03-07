//package tests.components;
//
//import org.testng.Assert;
//import org.testng.annotations.Test;
//
//import components.ProductComponent;
//import pages.LandingPage;
//import tests.base.BaseTest;
//
//public class CartComponentTest extends BaseTest {
//    
//    @Test
//    public void verifyCartBadgeUpdatesOnItemAddRemove() {
//        // Test setup
//        LandingPage landingPage = loginWithStandardUser();
//        ProductComponent product = landingPage.getProductByIndex(0);
//        
//        // Add item
//        product.addProductToCart();
//        Assert.assertEquals(landingPage.getCart().getCurrentCartCount(), 1, 
//            "Cart count should update after adding item");
//        
//        // Remove item
//        product.removeProductFromCart();
//        Assert.assertEquals(landingPage.getCart().getCurrentCartCount(), 0,
//            "Cart count should update after removing item");
//    }
//
//    @Test
//    public void verifyCartPersistsAcrossNavigation() {
//        LandingPage landingPage = loginWithStandardUser();
//        landingPage.addItemToCart(0);
//        
//        // Navigate away and back
//        landingPage.getMenu().openMenu().selectLink("All Items");
//        Assert.assertEquals(landingPage.getCart().getCurrentCartCount(), 1,
//            "Cart count should persist after navigation");
//    }
//}