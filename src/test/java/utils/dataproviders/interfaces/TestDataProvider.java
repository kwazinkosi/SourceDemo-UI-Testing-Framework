package utils.dataproviders.interfaces;

import java.util.List;
import exceptions.DataProviderException;

public interface TestDataProvider<T> {
    List<T> getTestData(String dataSource) throws DataProviderException;
    List<T> getTestData(String dataSource, String sheetName) throws DataProviderException;
}