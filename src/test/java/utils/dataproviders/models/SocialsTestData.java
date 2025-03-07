package utils.dataproviders.models;


import utils.dataproviders.interfaces.TestDataModel;

public class SocialsTestData implements TestDataModel {
    
	private String testCaseId; 
    private String socialMediaName;
    private String expectedTitle;

   
    @Override
    public String getTestCaseId() {
        return testCaseId;
    }

    public SocialsTestData setTestCaseId(String testCaseId) {
        this.testCaseId = testCaseId;
        return this;
    }

    @Override
    public String getExpectedResult() {
		return expectedTitle;
        
    }

	public String getSocialMediaName() {
		return socialMediaName;
	}

	public SocialsTestData setSocialMediaName(String socialMediaName) {
		this.socialMediaName = socialMediaName;
        return this;
	}

	public String getExpectedTitle() {
		return expectedTitle;
	}

	public SocialsTestData setExpectedTitle(String expectedTitle) {
		this.expectedTitle = expectedTitle;
        return this;
	}

    
}
