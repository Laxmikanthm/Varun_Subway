package pages.Cart;

import base.gui.controls.browser.Generic;
import base.gui.controls.browser.LinkText;
import base.pages.browser.BrowserBasePage;
import com.amazonaws.services.dynamodbv2.xspec.L;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import utils.Logz;

import java.util.List;

public class SearchResultsPage<T extends RemoteWebDriver> extends BrowserBasePage {

    private static By txtResultsSearch = By.xpath("//h1[@class='facets-facet-browse-title']");
    private By txtSearchItem = By.xpath("//a[@class='facets-item-cell-grid-title']/span");
    private String itemName;

    public ProductDescriptionPage productDescriptionPage;

    public SearchResultsPage(RemoteWebDriver driver) throws Exception {
        super(driver);
        Logz.step(getTextResultsSearch().elementIsDisplayedAndEnabled()?"This is search results Page":"Search Results Page is not displayed");
    }

    private Generic getTextResultsSearch() throws Exception {
        return new Generic(driver,txtResultsSearch,"Results Search");
    }
    private Generic getSearchItem() throws Exception {
        return new Generic(driver,txtSearchItem,"Search Result Number");
    }

    public List<WebElement> getSearchItems() throws Exception{
        return getTextResultsSearch().getWebElements(txtSearchItem,"Search Results");
    }
    private LinkText getItemName(String name) throws Exception {
        return new LinkText(driver,  By.xpath("//a[@class='facets-item-cell-grid-title']//span[contains(text(),'"+name+"')]"),"Item Name");
    }

    private LinkText getNonItemByName(String name)throws Exception {
        return new LinkText(driver, By.xpath("//a[@class='facets-item-cell-grid-title']//span[not(contains(text(),'"+name+"'))]"),"Item Name");
    }

    public ProductDescriptionPage navigateToPDPwithOutItemName(String Name) throws Exception {
        Assert.assertTrue(getItemName(Name).elementIsDisplayedAndEnabled(),"Search Results page has no Item displayed");
        getNonItemByName(Name).click();
        return new ProductDescriptionPage(driver);
    }

    public ProductDescriptionPage navigateToPDPwithItemName(String Name) throws Exception {
        getItemName(Name).click();
        Logz.step("Clicked on Item");
        return new ProductDescriptionPage(driver);
    }

    public ProductDescriptionPage clickOnItemName(String itemName) throws Exception {
        getItemName(itemName).click();
        Logz.step("Clicked on :'"+itemName+"' - Name");
        return new ProductDescriptionPage(driver);
    }

    public ProductDescriptionPage clickItemName() throws Exception{
        itemName = (getSearchItems().get(0)).getText();
        productDescriptionPage = clickOnItemName(itemName);
        Logz.step("Clicked on the Item Name");
        productDescriptionPage.validateItemName(itemName);
        return productDescriptionPage;
    }

    public ProductDescriptionPage navigateToPDPwithItemNumber() throws Exception {
        Assert.assertTrue(getTextResultsSearch().elementIsDisplayedAndEnabled(),"Search Results are not displayed");
        getSearchItem().getControl().click();
        Logz.step("Clicked on Search Item");
        return new ProductDescriptionPage(driver);
    }
}
