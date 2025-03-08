package utils.dataproviders;

import utils.dataproviders.factory.DataProviderFactory;
import utils.dataproviders.interfaces.TestDataProvider;
import utils.dataproviders.models.CheckoutTestData;
import utils.dataproviders.models.LoginTestData;
import utils.dataproviders.models.MenuItemsTestData;
import utils.dataproviders.models.SocialsTestData;
import exceptions.DataProviderException;
import org.testng.annotations.DataProvider;
import java.util.List;
public class SourceDemoDataProviders {

    // Configuration constants
    private static final String TEST_DATA_FILE = "/data/test-data.xlsx";
    private static final String DATA_FORMAT = "excel";
    

    @DataProvider(name = "loginData")
    public static Object[][] getLoginData() {
       
    	try {
            TestDataProvider<LoginTestData> provider = DataProviderFactory.createProvider(
            		LoginTestData.class, 
                DATA_FORMAT
            );

            List<LoginTestData> testData = provider.getTestData(TEST_DATA_FILE, "LoginData");
            
            return testData.stream()
                .map(data -> new Object[]{data})
                .toArray(Object[][]::new);
            
        } catch (Exception e) {
            throw new DataProviderException("Failed to load login test data", e);
        }
    }
    
    @DataProvider(name = "menuItemsData")
    public static Object[][] getMenuItemData() {
        
    	try {
            TestDataProvider<MenuItemsTestData> provider = DataProviderFactory.createProvider(
            		MenuItemsTestData.class, 
                DATA_FORMAT
            );

            List<MenuItemsTestData> testData = provider.getTestData(TEST_DATA_FILE, "MenuItemsData");
            
            return testData.stream()
                .map(data -> new Object[]{data})
                .toArray(Object[][]::new);
            
        } catch (Exception e) {
            throw new DataProviderException("Failed to load menuItem test data", e);
        }
    }
    
    @DataProvider(name = "socialsData")
    public static Object[][] getSocialsData() {
        
    	try {
            TestDataProvider<SocialsTestData> provider = DataProviderFactory.createProvider(
            		SocialsTestData.class, 
                DATA_FORMAT
            );

            List<SocialsTestData> testData = provider.getTestData(TEST_DATA_FILE, "SocialsData");
            
            return testData.stream()
                .map(data -> new Object[]{data})
                .toArray(Object[][]::new);
            
        } catch (Exception e) {
            throw new DataProviderException("Failed to load socials test data", e);
        }
    }
    
    @DataProvider(name = "checkoutData")
    public static Object[][] getCheckoutData() {
        
    	try {
            TestDataProvider<CheckoutTestData> provider = DataProviderFactory.createProvider(
            		CheckoutTestData.class, 
                DATA_FORMAT
            );

            List<CheckoutTestData> testData = provider.getTestData(TEST_DATA_FILE, "CheckoutData");
            
            return testData.stream()
                .map(data -> new Object[]{data})
                .toArray(Object[][]::new);
            
        } catch (Exception e) {
            throw new DataProviderException("Failed to load checkout test data", e);
        }
    }
}