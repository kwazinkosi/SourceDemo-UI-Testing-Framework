package utils.dataproviders.concrete;

import org.apache.poi.ss.usermodel.*;

import utils.dataproviders.interfaces.SheetConfig;
import utils.dataproviders.models.LoginTestData;
import exceptions.DataProviderException;

public class LoginSheetConfig implements SheetConfig<LoginTestData> {
    
	private enum Columns {
        TEST_CASE_ID(0), USERNAME(1), PASSWORD(2), 
        MESSAGE(3), EXPECTED_RESULT(4);
    	
        final int index;
        Columns(int index) { this.index = index; }
    }

    @Override
    public String[] getExpectedHeaders() {
        return new String[]{"TestCaseID", "Username", "Password", "Message", "ExpectedResult"};
    }

    @Override
    public LoginTestData mapRow(Row row) {
    	
        return new LoginTestData()
            .setTestCaseId(getString(row, Columns.TEST_CASE_ID))
            .setUsername(getString(row, Columns.USERNAME))
            .setPassword(getString(row, Columns.PASSWORD))
            .setMessage(getString(row, Columns.MESSAGE))
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
	    public void validateRequiredFields(LoginTestData data, int rowNumber) {
	        
		 	if (data.getTestCaseId() == null || data.getTestCaseId().isEmpty()) {
	            throw new DataProviderException("Missing TestCaseID in row " + rowNumber);
	        }
	        if (data.getExpectedResult() == null || data.getExpectedResult().isEmpty()) {
	            throw new DataProviderException("Missing ExpectedResult in row " + rowNumber);
	        }
	    }


}