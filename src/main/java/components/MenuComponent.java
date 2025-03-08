package components;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import constants.WaitTime;
import pages.base.CustomWait;


public class MenuComponent {
    @FindBy(xpath = "//button[@id='react-burger-menu-btn']")
    private WebElement menuButton;
    
    @FindBy(xpath = "//a[@class='bm-item menu-item']")
    private java.util.List<WebElement> menuItems;
    
    @FindBy(xpath = "//button[@id='react-burger-cross-btn']")
    private WebElement closeMenuButton;

    private final CustomWait customWait;

    
    public MenuComponent(WebDriver driver) {
        
        this.customWait = new CustomWait(driver);
        PageFactory.initElements(driver, this); 
    }

    public MenuComponent openMenu() {
        customWait.until(ExpectedConditions.elementToBeClickable(menuButton), WaitTime.NORMAL);
        menuButton.click();
        return this;
    }

    public void selectMenuItem(String menuItemText) {
        
    	if(!closeMenuButton.isDisplayed())
    		openMenu();
        
        // Find matching menu item
        WebElement targetItem = menuItems.stream()
            .filter(item -> item.getText().equalsIgnoreCase(menuItemText))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException("Menu item not found: " + menuItemText));
        
        customWait.until(ExpectedConditions.elementToBeClickable(targetItem), 5);
        targetItem.click();
        
        // Wait for page transition
        customWait.waitForPageLoad(WaitTime.NORMAL);
    }

    public MenuComponent verifyPageTitleContains(String text) {
        
    	customWait.until(d -> d.getTitle().contains(text), WaitTime.NORMAL);
        return this;
    }
    
	public boolean isDisplayed() {
		try {
			return menuButton.isDisplayed();
		}
		catch(Exception e) {
			System.out.println("Exception: "+e.getMessage());
		}
		return false;
	}

	public void resetAppState() {
		// TODO Auto-generated method stub
		
	}
}