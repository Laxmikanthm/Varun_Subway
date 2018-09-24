package pages.ShopByArea;

import base.gui.controls.browser.LinkText;
import base.pages.browser.BrowserBasePage;
import base.test.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import pages.Cart.ProductListPage;
import utils.Logz;
import org.testng.Assert;


public class CustomerAreaPage <T extends RemoteWebDriver> extends BrowserBasePage{

    private By linkTables = By.xpath("");

    public CustomerAreaPage(RemoteWebDriver driver) throws Exception{
        super(driver);
        Logz.step("This is Customer Area Page");
    }

    private LinkText getTablesLink() throws Exception
    {
        return new LinkText(driver,linkTables,"Tables Link");
    }

    public ProductListPage clickTablesPage() throws Exception
    {
        getTablesLink().click();
        return new ProductListPage(driver);
    }

}
