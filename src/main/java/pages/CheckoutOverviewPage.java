package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import constants.WaitTime;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class CheckoutOverviewPage extends BasePage {

    @FindBy(xpath = "//div[@data-test='payment-info-value']")
    private WebElement paymentInformation;
    
    @FindBy(xpath = "//div[@data-test='shipping-info-value']")
    private WebElement shippingInformation;
    
    @FindBy(xpath = "//div[@class='summary_subtotal_label']")
    private WebElement subtotalAmount;
    
    @FindBy(xpath = "//div[@class='summary_tax_label']")
    private WebElement taxAmount;
    
    @FindBy(xpath = "//div[@class='summary_total_label']")
    private WebElement totalAmount;
    
    @FindBy(id = "cancel")
    private WebElement cancelButton;
    
    @FindBy(id = "finish")
    private WebElement finishButton;

    public CheckoutOverviewPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isPageDisplayed() {
        return customWait.until(d -> 
            d.getCurrentUrl().contains("checkout-step-two.html") && 
            isElementDisplayed(finishButton),
            WaitTime.NORMAL
        );
    }

    public String getPaymentInformation() {
        return getText(paymentInformation);
    }

    public String getShippingInformation() {
        return getText(shippingInformation);
    }

    public double getSubtotal() {
        return parseCurrency(getText(subtotalAmount));
    }

    public double getTax() {
        return parseCurrency(getText(taxAmount));
    }

    public double getTotal() {
        return parseCurrency(getText(totalAmount));
    }

    public CheckoutCompletePage finishCheckout() {
        clickElement(finishButton);
        return new CheckoutCompletePage(driver);
    }

    public CartPage cancelCheckout() {
        clickElement(cancelButton);
        return new CartPage(driver);
    }

    public boolean verifyTotalCalculation() {
        return Math.abs((getSubtotal() + getTax()) - getTotal()) < 0.01;
    }

    private double parseCurrency(String valueText) {
        try {
            String numericValue = valueText.replaceAll("[^\\d.]", "");
            return NumberFormat.getNumberInstance(Locale.US)
                              .parse(numericValue)
                              .doubleValue();
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse currency value: " + valueText, e);
        }
    }
}