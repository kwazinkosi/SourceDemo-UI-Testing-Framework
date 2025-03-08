package utils.dataproviders;

import org.testng.annotations.DataProvider;

import pages.LandingPage.SortOption; // Import the enum from LandingPage

public class SortDataProvider {
    @DataProvider(name = "sortOptions")
    public static Object[][] sortingOptions() {
        return new Object[][] { 
            { SortOption.PRICE_LOW_HIGH }, 
            { SortOption.PRICE_HIGH_LOW }, 
            { SortOption.A_TO_Z },
            { SortOption.Z_TO_A } 
        };
    }
}