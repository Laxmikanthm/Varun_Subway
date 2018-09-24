package pages.ShopByArea;

import base.gui.controls.browser.Generic;
import base.gui.controls.browser.LinkText;
import base.pages.browser.BrowserBasePage;
import base.test.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.Logz;
import org.testng.Assert;

public class ShopByAreaPage <T extends RemoteWebDriver> extends BrowserBasePage {
    private By lnkBackroomThumbnail =  By.xpath("//div[@class='facets-category-cell-thumbnail']//img[@alt='"+BaseTest.getStringfromBundleFile("backroom").intern()+"']");
    private By lnkExteriorThumbnail =  By.xpath("//div[@class='facets-category-cell-thumbnail']//img[@alt='" + BaseTest.getStringfromBundleFile("exterior").intern() + "']");
    private By lnkServiceAreaThumbnail =  By.xpath("//div[@class='facets-category-cell-thumbnail']//img[@alt='" + BaseTest.getStringfromBundleFile("serviceArea").intern() + "']");
    private By lnkCustomerAreaThumbnail =  By.xpath("//div[@class='facets-category-cell-thumbnail']//img[@alt='" + BaseTest.getStringfromBundleFile("customerArea").intern() + "']");
    private By lnkBackroom = By.xpath("//a[@title='Category']//../div//a[@title='"+ BaseTest.getStringfromBundleFile("backroom").intern()+"']");
    private By lnkExterior = By.xpath("//a[@title='Category']//../div//a[@title='"+ BaseTest.getStringfromBundleFile("exterior").intern()+"']");
    private By lnkServiceArea = By.xpath("//a[@title='Category']//../div//a[@title='"+ BaseTest.getStringfromBundleFile("serviceArea").intern()+"']");
    private By lnkCustomerArea = By.xpath("//a[@title='Category']//../div//a[@title='"+ BaseTest.getStringfromBundleFile("customerArea").intern()+"']");
    private static  By txtPageHeader = By.xpath("//div[@class='facets-browse-category-heading-main-description']/h1");

    public ShopByAreaPage(RemoteWebDriver driver) throws Exception{
        super(driver);
        Logz.step(getPageHeader().elementIsDisplayedAndEnabled()?"ShopByArea page is displayed":"ShopByArea page is not displayed");
    }

    private LinkText getBackroomLink() throws Exception{
        return new LinkText(driver,lnkBackroom,"Backroom");
    }
    private LinkText getExteriorLink() throws Exception{
        return new LinkText(driver,lnkExterior,"Exterior");
    }
    private LinkText getServcieAreaLink() throws Exception{
        return new LinkText(driver,lnkServiceArea,"Service Area");
    }
    private LinkText getCustomerAreaLink() throws Exception{
        return new LinkText(driver,lnkCustomerArea,"Customer Area");
    }
    private LinkText getBackroomThumbnailLink() throws Exception{
        return new LinkText(driver,lnkBackroomThumbnail,"Backroom Thumbnail");
    }
    private LinkText getExteriorThumbnailLink() throws Exception{
        return new LinkText(driver,lnkExteriorThumbnail,"Exterior Thumbnail");
    }
    private LinkText getServiceAreaThumbnailLink() throws Exception{
        return new LinkText(driver,lnkServiceAreaThumbnail,"Service Area Thumbnail");
    }
    private LinkText getCustomerAreaThumbnailLink() throws Exception{
        return new LinkText(driver,lnkCustomerAreaThumbnail,"Customer Area Thumbnail");
    }
    private Generic getPageHeader() throws Exception{
        return new Generic(driver, txtPageHeader, "Page Header");
    }

    public void verifyBackRoomLink() throws Exception{
        Assert.assertTrue((getBackroomLink().getControl().isDisplayed()),"BackRoom link didn't get displayed under Shop By Area");
        Logz.step("BackRoom link is displayed under Shop By Area");
    }

    public void verifyExteriorLink() throws Exception{
        Assert.assertTrue(getExteriorLink().getControl().isDisplayed(),"Exterior link didn't get displayed under Shop By Area");
        Logz.step("External  link is displayed under Shop By Area");
    }

    public void verifyServiceAreaLink() throws Exception{
        Assert.assertTrue(getServcieAreaLink().getControl().isDisplayed(),"Service Area link didn't get displayed under Shop By Area");
        Logz.step("Service Area link is displayed under Shop By Area");
    }

    public void verifyCustomerAreaLink() throws Exception{
        Assert.assertTrue(getCustomerAreaLink().getControl().isDisplayed(),"Customer Area link didn't get displayed under Shop By Area");
        Logz.step("Customer Area link is displayed under Shop By Area");
    }

    private void verifyBackRoomThumbnailLink() throws Exception{
        Assert.assertTrue(getBackroomThumbnailLink().getControl().isDisplayed(),"BackRoom Thumbnail link didn't get displayed under Shop By Area");
        Logz.step("BackRoom Thumbnail link is displayed under Shop By Area");
    }

    private void verifyExteriorThumbnailLink() throws Exception{
        Assert.assertTrue(getExteriorThumbnailLink().getControl().isDisplayed(),"Exterior Thumbnail link didn't get displayed under Shop By Area");
        Logz.step("Exterior Thumbnail link is displayed under Shop By Area");
    }

    private void verifyServiceAreaThumbnailLink() throws Exception{
        Assert.assertTrue(getServiceAreaThumbnailLink().getControl().isDisplayed(),"Service Area Thumbnail link didn't get displayed under Shop By Area");
        Logz.step("Service Area Thumbnail link is displayed under Shop By Area");
    }

    private void verifyCustomerAreaThumbnailLink() throws Exception{
        Assert.assertTrue(getCustomerAreaThumbnailLink().getControl().isDisplayed(),"CustomerArea Thumbnail link didn't get displayed under Shop By Area");
        Logz.step("customerArea Thumbnail link is displayed under Shop By Area");
    }

    public ShopByAreaPage verifyThumbnailsForAreas() throws Exception{
        verifyBackRoomThumbnailLink();
        verifyExteriorThumbnailLink();
        verifyServiceAreaThumbnailLink();
        verifyCustomerAreaThumbnailLink();
        Logz.step("Thumbnails for all the areas are verified");
        return new ShopByAreaPage(driver);
    }
}
