package components;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import constants.WaitTime;
import pages.base.CustomWait;

import java.util.List;

public class SocialsComponent {
	
    private final WebDriver driver;
    private final CustomWait customWait;

    @FindBy(xpath = "//ul[@class='social']//a")
    private List<WebElement> socialLinks;

    public SocialsComponent(WebDriver driver) {
        this.driver = driver;
        this.customWait = new CustomWait(driver);
    }

    

    public void clickOnSocialLink(String socialName) {
        WebElement socialElement = socialLinks.stream()
            .filter(social -> social.getText().equals(socialName))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException("Social link with name " + socialName + " not found"));
        
        socialElement.click();
    }
    
    public boolean isDisplayed() {
    	
        return socialLinks.stream().allMatch(WebElement::isDisplayed);
    }
}