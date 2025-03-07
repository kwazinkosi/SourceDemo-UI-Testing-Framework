package utils.dataproviders.interfaces;

import org.apache.poi.ss.usermodel.Row;

public interface SheetConfig<T> {
    String[] getExpectedHeaders();
    T mapRow(Row row);
    void validateRequiredFields(T data, int rowNumber);
}