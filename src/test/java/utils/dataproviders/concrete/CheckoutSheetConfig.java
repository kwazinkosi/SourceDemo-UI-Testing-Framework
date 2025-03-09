package utils.dataproviders.concrete;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import utils.dataproviders.interfaces.SheetConfig;
import utils.dataproviders.models.CheckoutTestData;

public class CheckoutSheetConfig implements SheetConfig<CheckoutTestData> {

	private enum Columns {

		TEST_CASE_ID(0), FIRST_NAME(1), LAST_NAME(2), ZIP_CODE(3), EXPECTED_RESULT(4);

		final int index;

		Columns(int index) {
			this.index = index;
		}
	}

	@Override
	public String[] getExpectedHeaders() {
		return new String[] { "TestCaseID", "FirstName", "LastName", "ZipCode", "ExpectedResult"};
	}

	@Override
	public CheckoutTestData mapRow(Row row) {
		return new CheckoutTestData()
				.setTestCaseId(getString(row, Columns.TEST_CASE_ID))
				.setFirstName(getString(row, Columns.FIRST_NAME))
				.setLastName(getString(row, Columns.LAST_NAME))
				.setZipCode(getString(row, Columns.ZIP_CODE))
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
	public void validateRequiredFields(CheckoutTestData data, int rowNumber) {
		// TODO Auto-generated method stub

	}

}
