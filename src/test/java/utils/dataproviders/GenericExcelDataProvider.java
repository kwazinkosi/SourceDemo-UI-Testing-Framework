package utils.dataproviders;

import java.util.ArrayList;
import java.util.List;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.*;
import utils.dataproviders.interfaces.SheetConfig;
import utils.dataproviders.interfaces.TestDataProvider;
import exceptions.DataProviderException;

public class GenericExcelDataProvider<T> implements TestDataProvider<T> {
    private final SheetConfig<T> config;

    public GenericExcelDataProvider(SheetConfig<T> config) {
        this.config = config;
    }

    @Override
    public List<T> getTestData(String dataSource, String sheetName) {
        
    	List<T> testDataList = new ArrayList<>(); 
        try (InputStream is = getClass().getResourceAsStream(dataSource);
             Workbook workbook = WorkbookFactory.create(is)) {
            
            Sheet sheet = getSheet(workbook, sheetName);
            validateHeaders(sheet);
            
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header row
                if (isEmptyRow(row)) continue;

                T data = config.mapRow(row);
                config.validateRequiredFields(data, row.getRowNum() + 1);
                testDataList.add(data);
            }
            
        } catch (Exception e) {
            throw new DataProviderException("Error processing Excel file: " + dataSource, e);
        }
        return testDataList;
    }

    private Sheet getSheet(Workbook workbook, String sheetName) {
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            throw new DataProviderException("Sheet not found: " + sheetName);
        }
        return sheet;
    }

    private void validateHeaders(Sheet sheet) {
        
    	Row headerRow = sheet.getRow(0);
        if (headerRow == null) {
            throw new DataProviderException("Missing header row in sheet: " + sheet.getSheetName());
        }

        List<String> actualHeaders = new ArrayList<>();
        for (Cell cell : headerRow) {
            actualHeaders.add(cell.getStringCellValue().trim());
        }

        for (String expected : config.getExpectedHeaders()) {
            if (!actualHeaders.contains(expected)) {
                throw new DataProviderException("Missing expected column: " + expected);
            }
        }
    }

    private boolean isEmptyRow(Row row) {
       
    	for (Cell cell : row) {
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }

	@Override
	public List<T> getTestData(String dataSource) throws DataProviderException {
		 
		return getTestData(dataSource, null);
	}

	
}
