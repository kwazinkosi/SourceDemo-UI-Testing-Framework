package utils.dataproviders.concrete;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

import utils.dataproviders.interfaces.SheetConfig;
import utils.dataproviders.models.MenuItemsTestData;

public class MenuItemsSheetConfig implements SheetConfig<MenuItemsTestData> {

	private enum Columns {
		
		TEST_CASE_ID(0), MENU_ITEM(1), DESTINATION_URL(2), EXPECTED_TITLE(3), EXPECTED_RESULT(4);

		final int index;

		Columns(int index) {
			this.index = index;
		}
	}

	@Override
	public String[] getExpectedHeaders() {
		return new String[] { "TestCaseID", "MenuItem", "DestinationUrl", "ExpectedTitle", "ExpectedResult" };
	}

	@Override
	public MenuItemsTestData mapRow(Row row) {
		return new MenuItemsTestData().setTestCaseId(getString(row, Columns.TEST_CASE_ID))
				.setMenuItem(getString(row, Columns.MENU_ITEM))
				.setDestinationUrl(getString(row, Columns.DESTINATION_URL))
				.setExpectedTitle(getString(row, Columns.EXPECTED_TITLE))
				.setExpectedResult(getString(row, Columns.EXPECTED_RESULT));
	}

	private String getString(Row row, Columns column) {
		Cell cell = row.getCell(column.index);
		if (cell == null)
			return "";

		DataFormatter formatter = new DataFormatter();
		return formatter.formatCellValue(cell).trim();
	}

	@Override
	public void validateRequiredFields(MenuItemsTestData data, int rowNumber) {
		// TODO Auto-generated method stub

	}

}