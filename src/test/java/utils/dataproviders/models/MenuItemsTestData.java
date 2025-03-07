package utils.dataproviders.models;

import utils.dataproviders.interfaces.TestDataModel;

public class MenuItemsTestData implements TestDataModel {
    
	private String testCaseId;
	private String menuItem;
    private String destinationUrl;  // "follow" or "unfollow"
    private String expectedTitle;    // "user", "tag", "author"
    private String expectedResult; 
	
    @Override
	public String getTestCaseId() {
		
		return testCaseId;
	}

	@Override
	public String getExpectedResult() {
		
		return expectedResult;
	}
	
    public String getMenuItem() {
		return menuItem;
	}

	public String getDestinationUrl() {
		return destinationUrl;
	}

	public String getExpectedTitle() {
		return expectedTitle;
	}

	// Fluent setters and getters
    public MenuItemsTestData setTestCaseId(String testCaseId) {
        
    	this.testCaseId = testCaseId;
        return this;
    }

	public MenuItemsTestData setMenuItem(String menuItem) {
		this.menuItem = menuItem;
        return this;
	}

	public MenuItemsTestData setDestinationUrl(String destinationUrl) {
		this.destinationUrl = destinationUrl;
        return this;
	}

	public MenuItemsTestData setExpectedTitle(String expectedTitle) {
		this.expectedTitle = expectedTitle;
        return this;
	}

	public MenuItemsTestData setExpectedResult(String expectedResult) {
		this.expectedResult = expectedResult;
        return this;
	}

	
	
}
