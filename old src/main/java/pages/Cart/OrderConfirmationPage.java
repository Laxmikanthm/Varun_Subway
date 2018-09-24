package pages.Cart;
import base.gui.controls.browser.*;
import base.pages.browser.BrowserBasePage;
import base.test.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import pages.CommonMethodsPage;

import util.Common;
import utils.Logz;

import java.util.List;


public class OrderConfirmationPage <T extends RemoteWebDriver> extends BrowserBasePage{

    private By orderNumber= By.xpath("//p[@class='requestquote-wizard-module-confirmation-body' and contains(text(),'"+BaseTest.getStringfromBundleFile("yourQuoteRequest")+"')]/a");
    private By lnkSalesorderNumber= By.xpath("//p[@class='order-wizard-confirmation-module-body']/a");

    private static By btnContinueShopping= By.xpath("//a[@class='order-wizard-confirmation-module-continue']");
    private By btnDownloadPDF= By.xpath("//a[@class='order-wizard-confirmation-module-download-pdf']");

    public CommonMethodsPage commonMethodsPage;

    public OrderConfirmationPage(RemoteWebDriver driver) throws Exception {
        super(driver);
        commonMethodsPage = gotoCommonMethodsPage();
        Logz.step(getContinueShoppingLink().elementIsDisplayedAndEnabled()?"This is Order Confirmation Page":"Order Confirmation Page is not displayed");
    }

    //This method will return the commonMethodsPage driver object
    public CommonMethodsPage gotoCommonMethodsPage() throws Exception {
        return new CommonMethodsPage(driver);
    }

    private TextBox getOrderNumber() throws Exception {
        return new TextBox(driver,orderNumber,"Order Number");
    }

    private TextBox getSalesOrderNumber() throws Exception {
        return new TextBox(driver,lnkSalesorderNumber,"Order Number");
    }

    private Button getContinueShoppingLink() throws Exception {
        return new Button(driver, btnContinueShopping,"Continue Shopping Button");
    }
    private Button getDownloadPDFLink() throws Exception {
        return new Button(driver, btnDownloadPDF,"Download Button");
    }


    public void verifySalesOrderNumber() throws Exception {
      String number = getSalesOrderNumber().getText();
      Assert.assertTrue(Common.isElementPresent(driver,lnkSalesorderNumber),"Order number is not displayed");
      Logz.step("sales order number is:" + number);
    }

    public void verifyContinueShoppingLink() throws Exception {
        Assert.assertTrue(Common.isElementPresent(driver,btnContinueShopping),"Continue Shopping Link is not Available");
        Logz.step("Continue Shopping Link is Available");
    }

    public void verifyDownloadPDFLink() throws Exception {
        Assert.assertTrue(Common.isElementPresent(driver,btnDownloadPDF),"Download PDF Link is not Available");
        Logz.step("Download PDF Link is Available");
    }

    public void verifyOrderConfirmation() throws Exception {
        verifySalesOrderNumber();
        verifyContinueShoppingLink();
        verifyDownloadPDFLink();
    }

    public void verifyQuoteOrderConfirmation() throws Exception {
        verifySalesOrderNumber();
    }

}
