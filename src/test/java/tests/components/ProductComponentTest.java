//package tests.components;
//
//import org.testng.Assert;
//import org.testng.annotations.Test;
//
//import components.ProductComponent;
//import pages.LandingPage;
//import pages.ProductDetailsPage;
//import tests.base.BaseTest;
//
//public class ProductComponentTest extends BaseTest {
//    
//    @Test
//    public void verifyProductDetailsConsistency() {
//    	
//        LandingPage landingPage = loginWithStandardUser();
//        ProductComponent listProduct = landingPage.getProductByIndex(0);
//        String listName = listProduct.getProductName();
//        String listPrice = listProduct.getProductPrice();
//        
//        // Verify details match detail page
//        ProductDetailsPage detailsPage = listProduct.viewDetails();
//        Assert.assertEquals(detailsPage.getProductName(), listName,
//            "Product name should match between list and detail views");
//        Assert.assertEquals(detailsPage.getProductPrice(), listPrice,
//            "Product price should match between list and detail views");
//    }
//
//    @Test
//    public void verifyAddRemoveButtonStates() {
//        LandingPage landingPage = loginWithStandardUser();
//        ProductComponent product = landingPage.getProductByIndex(0);
//        
//        // Initial state
//        Assert.assertTrue(product.isAddToCartVisible(),
//            "Add to cart button should be visible initially");
//            
//        // After adding
//        product.addToCart();
//        Assert.assertTrue(product.isRemoveButtonVisible(),
//            "Remove button should appear after adding to cart");
//    }
//}