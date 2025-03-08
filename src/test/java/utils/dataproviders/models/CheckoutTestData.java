package utils.dataproviders.models;

import utils.dataproviders.interfaces.TestDataModel;

public class CheckoutTestData implements TestDataModel {
    
	private String testCaseId; 
    private String firstName;
    private String lastName;
    private String zipCode;
    private String expectedResult;
   
    @Override
    public String getTestCaseId() {
        return testCaseId;
    }
    
    @Override
    public String getExpectedResult() {
		return expectedResult;
        
    }
    
	public String getFirstName() {
		return firstName;
	}
	
	public CheckoutTestData setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public CheckoutTestData setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}
	
	public String getZipCode() {
		return zipCode;
	}
	
	public CheckoutTestData setZipCode(String zipCode) {
		this.zipCode = zipCode;
		return this;
	}
	
	public CheckoutTestData setTestCaseId(String testCaseId) {
		
		this.testCaseId = testCaseId;
		return this;
	}
	
	public CheckoutTestData setExpectedResult(String expectedResult) {
		this.expectedResult = expectedResult;
		return this;
	}
}
