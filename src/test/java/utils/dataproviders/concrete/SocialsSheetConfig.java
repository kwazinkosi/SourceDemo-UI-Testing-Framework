package utils.dataproviders.concrete;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

import utils.dataproviders.interfaces.SheetConfig;
import utils.dataproviders.models.SocialsTestData;

public class SocialsSheetConfig implements SheetConfig<SocialsTestData> {

	private enum Columns {

		TEST_CASE_ID(0), SOCIAL_MEDIA_NAME(1), EXPECTED_TITLE(3);

		final int index;

		Columns(int index) {
			this.index = index;
		}
	}

	@Override
	public String[] getExpectedHeaders() {
		return new String[] { "TestCaseID", "SocialMediaName", "ExpectedTitle"};
	}

	@Override
	public SocialsTestData mapRow(Row row) {
		return new SocialsTestData().setTestCaseId(getString(row, Columns.TEST_CASE_ID))
				.setSocialMediaName(getString(row, Columns.SOCIAL_MEDIA_NAME))
				.setExpectedTitle(getString(row, Columns.EXPECTED_TITLE));
	}

	private String getString(Row row, Columns column) {
		Cell cell = row.getCell(column.index);
		if (cell == null)
			return "";

		DataFormatter formatter = new DataFormatter();
		return formatter.formatCellValue(cell).trim();
	}

	@Override
	public void validateRequiredFields(SocialsTestData data, int rowNumber) {
		// TODO Auto-generated method stub

	}

}
