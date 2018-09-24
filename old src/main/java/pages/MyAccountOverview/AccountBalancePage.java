package pages.MyAccountOverview;

import base.gui.controls.browser.LinkText;
import base.pages.browser.BrowserBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AccountBalancePage <T extends RemoteWebDriver> extends BrowserBasePage {

    private By lnkPrintStatement = By.xpath("//a[@class='balance-print-button']");
    private By lnkDocuments = By.xpath("//a[text()='Documents']");
    private By lnkInvoices = By.xpath("//a[@data-id='invoices']");
    private By lnkAccountBalance = By.xpath("//a[@data-id='balance']");

    public AccountBalancePage(RemoteWebDriver driver) throws Exception{
        super(driver);
    }

    private LinkText getPrintStatementLink() throws Exception{
        return new LinkText(driver,lnkPrintStatement,"Print Statement");
    }

    private LinkText getDocumentsLink() throws Exception{
        return new LinkText(driver,lnkDocuments,"Documents");
    }

    private LinkText getInvoicesLink() throws Exception{
        return new LinkText(driver,lnkInvoices,"Invoices");
    }

    private LinkText getAccountBalanceLink() throws Exception{
        return new LinkText(driver,lnkAccountBalance,"Account Balance");
    }

    public AccountBalancePage clickPrintStatement() throws Exception{
      getPrintStatementLink().click();
      return new AccountBalancePage(driver);
    }

    public AccountBalancePage clickDocument() throws Exception{
        getDocumentsLink().click();
        return new AccountBalancePage(driver);
    }

    public AccountBalancePage clickInvoice() throws Exception{
        getInvoicesLink().click();
        return new AccountBalancePage(driver);
    }

    public AccountBalancePage clickAccountBalance() throws Exception{
        getAccountBalanceLink().click();
        return new AccountBalancePage(driver);
    }

}
