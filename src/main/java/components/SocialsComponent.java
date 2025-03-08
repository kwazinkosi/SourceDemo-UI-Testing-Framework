package components;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import java.util.List;

public class SocialsComponent {
	
    @FindBy(xpath = "//ul[@class='social']//a")
    private List<WebElement> socialLinks;

    public SocialsComponent(WebDriver driver) {
        PageFactory.initElements(driver, this);
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