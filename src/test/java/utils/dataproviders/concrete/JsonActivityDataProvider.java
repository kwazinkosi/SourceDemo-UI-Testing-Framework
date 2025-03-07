package utils.dataproviders.concrete;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;

import utils.dataproviders.interfaces.TestDataProvider;
import utils.dataproviders.models.LoginTestData;

public class JsonActivityDataProvider implements TestDataProvider<LoginTestData> {

    @Override
    public List<LoginTestData> getTestData(String dataSource) {
        ObjectMapper mapper = new ObjectMapper();
        try {
			return mapper.readValue(
			    getClass().getResourceAsStream(dataSource),
			    new TypeReference<List<LoginTestData>>(){}
			);
		} catch (StreamReadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabindException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }

	@Override
	public List<LoginTestData> getTestData(String dataSource, String sheetName) {
		// TODO Auto-generated method stub
		return null;
	}
}