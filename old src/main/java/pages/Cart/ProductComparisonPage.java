package pages.Cart;

import base.gui.controls.browser.Generic;
import base.pages.browser.BrowserBasePage;
import base.test.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import utils.Logz;

import java.util.List;

/**
 * Created by E003776 on 12/1/2017.
 */
public class ProductComparisonPage<T extends RemoteWebDriver> extends BrowserBasePage {

    private static By lnkBackToShopping=By.xpath("//a[@data-action='go-back']");
    
    private By txtItemNames=By.xpath("//strong[@class='font--dark']");

    public ProductComparisonPage(RemoteWebDriver driver) throws Exception {
        super(driver);
        Logz.step(getBackToShoppingLink().elementIsDisplayedAndEnabled()?"Product Comparison Page is displayed":"Product Comparision Page is not displayed");
    }

    private Generic getBackToShoppingLink()throws Exception{
        return new Generic(driver,lnkBackToShopping,"Back To Shopping Link");
    }
    private List<WebElement> getComparisonItems() throws Exception{
        return getBackToShoppingLink().getWebElements(txtItemNames,"Items Which are added to Comparison");
    }
    private Generic getItemComparisonOptions(String name, int index) throws Exception {
        return new Generic(driver, By.xpath("//a[@title='"+name+"']//following::a['"+index+"']"), "Item Options on Comparison page");
    }

    public void verifyItemOptionsOnComparisonPage(String[] items) throws Exception{
    	List<WebElement> cmpItems = getComparisonItems();
        int noOfItems = cmpItems.size();
        for (int i=0;i<noOfItems;i++){
            String comparisonItem = cmpItems.get(i).getText();
            Assert.assertEquals(comparisonItem,items[i]);
            getItemComparisonOptions(comparisonItem,0).getControl().getText().equalsIgnoreCase(BaseTest.getStringfromBundleFile("linkViewProductDetails"));
            getItemComparisonOptions(comparisonItem,1).getControl().getText().equalsIgnoreCase(BaseTest.getStringfromBundleFile("linkAddToCart"));
            getItemComparisonOptions(comparisonItem,2).getControl().getText().equalsIgnoreCase(BaseTest.getStringfromBundleFile("linkRemoveFromComparison"));
        }
    }
}
