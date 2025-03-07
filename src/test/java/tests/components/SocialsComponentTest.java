//package tests.components;
//
//import org.testng.Assert;
//import org.testng.annotations.Test;
//
//import components.SocialsComponent;
//import pages.CartPage;
//import pages.LandingPage;
//import pages.LoginPage;
//import tests.base.BaseTest;
//
//public class SocialsComponentTest extends BaseTest {
//    
//    @Test
//    public void verifySocialLinksOpenCorrectPages() {
//        LandingPage landingPage = loginWithStandardUser();
//        SocialsComponent socials = landingPage.getSocials();
//        
//        // Test Twitter link
//        Assert.assertTrue(socials.verifySocialLink("twitter", "Twitter"),
//            "Twitter link should open correct page");
//        
//        // Test LinkedIn link
//        Assert.assertTrue(socials.verifySocialLink("linkedin", "LinkedIn"),
//            "LinkedIn link should open correct page");
//    }
//
//    @Test
//    public void verifySocialLinksInNewTabs() {
//        LandingPage landingPage = loginWithStandardUser();
//        int initialWindows = driver.getWindowHandles().size();
//        
//        landingPage.getSocials().clickSocialLink("facebook");
//        Assert.assertEquals(driver.getWindowHandles().size(), initialWindows + 1,
//            "Social links should open in new tabs");
//    }
//}