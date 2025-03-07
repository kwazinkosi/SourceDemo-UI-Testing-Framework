package utils.dataproviders.models;

import utils.dataproviders.interfaces.TestDataModel;

public class LoginTestData implements TestDataModel {
    
	private String testCaseId; 
    private String username;
    private String password;
    private String message;
    private String expectedResult;

   
    @Override
    public String getTestCaseId() {
        return testCaseId;
    }

    public LoginTestData setTestCaseId(String testCaseId) {
        this.testCaseId = testCaseId;
        return this;
    }

    @Override
    public String getExpectedResult() {
        return expectedResult;
    }

    public LoginTestData setExpectedStatusCode(String expectedResult) {
        this.expectedResult = expectedResult;
        return this;
    }

	public String getUsername() {
		return username;
	}

	public LoginTestData setUsername(String username) {
		this.username = username;
        return this;
	}

	public String getPassword() {
		return password;
	}

	public LoginTestData setPassword(String password) {
		this.password = password;
        return this;
	}

	public String getMessage() {
		return message;
	}

	public LoginTestData setMessage(String message) {
		this.message = message;
        return this;
	}

	public LoginTestData setExpectedResult(String expectedResult) {
		this.expectedResult = expectedResult;
        return this;
	}
    
}