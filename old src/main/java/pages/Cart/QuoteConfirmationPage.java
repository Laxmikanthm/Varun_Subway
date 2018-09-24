package pages.Cart;

import base.gui.controls.browser.*;
import base.pages.browser.BrowserBasePage;
import base.test.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
//import org.testng.Assert;
import org.testng.Assert;
import pages.HomePage;
import pages.MyAccountOverview.PurchaseHistoryPage;
import util.Common;
import utils.Logz;

public class QuoteConfirmationPage <T extends RemoteWebDriver> extends BrowserBasePage {

    private static By txtquoteConfirmation = By.xpath("//div[@class='requestquote-wizard-module-confirmation']/h2");
    private By QuoteOrderNum=By.xpath("//p[@class='requestquote-wizard-module-confirmation-body' and contains(text(),'"+BaseTest.getStringfromBundleFile("yourQuoteRequest")+"')]/a");
    private By lnkYourOrder= By.xpath("//a[@class='requestquote-wizard-module-confirmation-continue']");

    public QuoteConfirmationPage(RemoteWebDriver driver) throws Exception {
        super(driver);
//        Logz.step(getQuoteConfirmation().elementIsDisplayedAndEnabled()?"This is Quick Order Page":"Quick Order Page is not displayed");
    }

    private Generic getQuoteConfirmation() throws Exception {
        return new Generic(driver, txtquoteConfirmation, "Quote Confirmation");
    }
    private LinkText getYourOrderLink() throws Exception {
        return new LinkText(driver,lnkYourOrder,"Your Order Link");
    }

    private Button getQuoteOrderNumber() throws Exception {
    	Common.waitForOperations(driver, QuoteOrderNum);
        return new Button(driver, QuoteOrderNum,"Quote Order Number");
    }

    public PurchaseHistoryPage verifyQuoteSubmit() throws Exception {
    	Common.waitForOperations(driver, txtquoteConfirmation);
//        Assert.assertTrue(getQuoteConfirmation().elementIsDisplayedAndEnabled());
        getYourOrderLink().getControl().click();
        return new PurchaseHistoryPage(driver);
    }

    public void verifyQuoteOrderConfirmation() throws Exception {
        String Order=getQuoteOrderNumber().getControl().getText();
        Assert.assertNotNull(getQuoteOrderNumber().getControl().getText(),"Sales Order Number is Null");
        Logz.step("Sales order Number is:" + Order);

    }
}
