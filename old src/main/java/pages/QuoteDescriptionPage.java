package pages;

import base.gui.controls.browser.Button;
import base.gui.controls.browser.Generic;
import base.pages.browser.BrowserBasePage;
import base.test.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import pages.Cart.ReviewYourOrderPage;
import utils.Logz;
import java.util.List;

public class QuoteDescriptionPage<T extends RemoteWebDriver> extends BrowserBasePage {

    private static By btnReviewAndOrder= By.xpath("//a[@class='quote-details-button-review-and-order']");
    //private By consolidateItemValue= By.xpath("//div[@class='transaction-line-views-selected-option' and @name='Consolidate']//span[@class='transaction-line-views-selected-option-value']");
	private By consolidateItemValue= By.xpath("//div[@class='quote-details-row']//span[contains(text(),'" + BaseTest.getStringfromBundleFile("Consolidator") + "')]/following-sibling::span");
    private By txtSubTotalPrice = By.xpath("//p[@class='quote-details-summary-grid-float']//span[@class='quote-details-summary-amount-total']");
    
    public QuoteDescriptionPage(RemoteWebDriver driver) throws Exception {
        super(driver);
        Logz.step(getReviewAndOrderButton().elementIsDisplayedAndEnabled()?"Quote Description page is loaded":"Quote Description Page is not loaded");
    }

    private Button getReviewAndOrderButton() throws Exception {
        return new Button(driver,btnReviewAndOrder,"Review and Order");
    }
    private Generic getSubtotalPrice() throws Exception {
        return new Generic(driver, txtSubTotalPrice, "Subtotal Price");
    }
 
    private List<WebElement> quotesItemList() throws Exception {
		return getConsolidateItemText().getWebElements(consolidateItemValue, "Table Rows");
	}
    private Generic getConsolidateItemText() throws Exception {
		return new Generic(driver,consolidateItemValue, "Consolidate Item Value");
	}
    
    public void verifyReviewOrderButtondisabled() throws Exception{
    	Assert.assertEquals(Boolean.parseBoolean(getReviewAndOrderButton().getAttribute("disabled")), true);
        Logz.step("Review and Order Button is disabled");
    }
    
    public void verifyReviewOrderButtonNotPresent() throws Exception{
    	Assert.assertEquals(checkForReviewAndOrderButton(), false, "Review and Order Button was found and displayed");
        Logz.step("Review and Order Button is Not shown");
    }

	private boolean checkForReviewAndOrderButton() throws Exception {
		try {
    	getReviewAndOrderButton().getText();
    	} catch (NoSuchElementException e) {
    		return false;
    	}
		return true;
	}
    
    public ReviewYourOrderPage clickReviewOrder() throws Exception{
        Assert.assertNotEquals(getReviewAndOrderButton().getAttribute("disabled"),"true","Review Order Button is Not Enabled");
        getReviewAndOrderButton().click();
        Logz.step("Clicked on Review and Order Button");
        return new ReviewYourOrderPage(driver);
    }
    
    public float verifySubTotal() throws Exception {
		String totalFinal = getSubtotalPrice().getControl().getText();
		totalFinal = totalFinal.replaceAll("[$,]", "");
		float total = Float.parseFloat(totalFinal);
		return total;
    }

	public void verifyQuoteConsolidate() throws Exception {
		Logz.step("Quote is clicked");
		String sText = getConsolidateItemText().getControl().getText();
		Assert.assertTrue(sText != "",  "Consolidator Value is blank");
		Logz.step("Item is consolidated");
	}
}
