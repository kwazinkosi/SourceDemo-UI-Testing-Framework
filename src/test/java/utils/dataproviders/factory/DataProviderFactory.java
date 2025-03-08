package utils.dataproviders.factory;

import utils.dataproviders.interfaces.SheetConfig;
import utils.dataproviders.interfaces.TestDataModel;
import utils.dataproviders.interfaces.TestDataProvider;
import utils.dataproviders.models.CheckoutTestData;
import utils.dataproviders.models.LoginTestData;
import utils.dataproviders.models.MenuItemsTestData;
import utils.dataproviders.models.SocialsTestData;

import java.util.Map;

import utils.dataproviders.GenericExcelDataProvider;
import utils.dataproviders.concrete.CheckoutSheetConfig;
import utils.dataproviders.concrete.LoginSheetConfig;
import utils.dataproviders.concrete.MenuItemsSheetConfig;
import utils.dataproviders.concrete.SocialsSheetConfig;


public class DataProviderFactory {
	
    private static final Map<Class<? extends TestDataModel>, Object> CONFIG_MAP = Map.of(
    		
        LoginTestData.class, new LoginSheetConfig(),
        MenuItemsTestData.class, new MenuItemsSheetConfig(),
        SocialsTestData.class, new SocialsSheetConfig(),
        CheckoutTestData.class, new CheckoutSheetConfig()
    );

    @SuppressWarnings("unchecked")
    public static <T> TestDataProvider<T> createProvider(Class<T> dataClass, String format) {
    	
        SheetConfig<T> config = (SheetConfig<T>) CONFIG_MAP.get(dataClass);
        if (config == null) {
            throw new IllegalArgumentException("No config found for: " + dataClass);
        }
        return new GenericExcelDataProvider<>(config);
    }
}