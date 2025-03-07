package utils.dataproviders;

import utils.dataproviders.factory.DataProviderFactory;
import utils.dataproviders.interfaces.TestDataProvider;
import utils.dataproviders.models.LoginTestData;
import utils.dataproviders.models.MenuItemsTestData;
import exceptions.DataProviderException;
import org.testng.annotations.DataProvider;
import java.util.List;
public class SourceDemoDataProviders {

    // Configuration constants
    private static final String TEST_DATA_FILE = "/testdata/test-data.xlsx";
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
    
    @DataProvider(name = "MenuItemsData")
    public static Object[][] getMenuItemData() {
        
    	try {
            TestDataProvider<MenuItemsTestData> provider = DataProviderFactory.createProvider(
            		MenuItemsTestData.class, 
                DATA_FORMAT
            );

            List<MenuItemsTestData> testData = provider.getTestData(TEST_DATA_FILE, "Following");
            
            return testData.stream()
                .map(data -> new Object[]{data})
                .toArray(Object[][]::new);
            
        } catch (Exception e) {
            throw new DataProviderException("Failed to load activity test data", e);
        }
    }
}