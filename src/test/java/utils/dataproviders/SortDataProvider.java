package utils.dataproviders;

import org.testng.annotations.DataProvider;

public class SortDataProvider {

	public enum SortOption {
		PRICE_LOW_HIGH("lohi"), PRICE_HIGH_LOW("hilo"), A_TO_Z("az"), Z_TO_A("za");

		private final String value;
		SortOption(String value) {
			this.value = value;
		}
		public String getValue() {
			return value;
		}
	}

	@DataProvider(name = "sortOptions")
	public static Object[][] sortingOptions() {
		return new Object[][] { { SortOption.PRICE_LOW_HIGH }, { SortOption.PRICE_HIGH_LOW }, { SortOption.A_TO_Z },
				{ SortOption.Z_TO_A } };
	}
}
