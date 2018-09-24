package pages.MyAccountOverview;

import base.gui.controls.browser.Generic;
import base.gui.controls.browser.LinkText;
import base.pages.browser.BrowserBasePage;
import base.test.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import utils.Logz;

public class ProfileInformationPage<T extends RemoteWebDriver> extends BrowserBasePage {

    private static By pageHeaderTitle = By.xpath("//*[@class='profile-information']/h2");
    private By txtRestaurantNumber = By.xpath("//div[@class='profile-information-row']/label[contains(text(), '"+ BaseTest.getStringfromBundleFile("labelCompanyName")+"')]//following-sibling::div");

    public ProfileInformationPage(RemoteWebDriver driver) throws Exception {
        super(driver);
        Logz.step(getPageHeaderTitle().elementIsDisplayedAndEnabled()?"Profile Information page is loaded":"Profile Information page is not loaded");

    }

    private LinkText getPageHeaderTitle() throws Exception{
        return new LinkText(driver, pageHeaderTitle,"Pgae Header - Profile Information");
    }
    private Generic getRestaurantNumber() throws Exception{
        return new Generic(driver, txtRestaurantNumber,"Restaurant Number followed by Company Name Label");
    }

    private boolean isRestaurantNumberDisplayedCorrect(String storeNumber) throws Exception {
        boolean flag = false;
        try {
            if (storeNumber.contains(getRestaurantNumber().getControl().getText())) {
                Logz.step("Restaurant Number : "+ storeNumber +" is displayed under Company Name Label.");
                flag = true;
            }
        } catch(Exception e)
        {
            Logz.error("Restaurant Number : "+ storeNumber +" is not displayed under Company Name Label.");
            flag = false;
        }
        return flag;
    }

    public void verifyStoreNoInProfileInfoPage(String storeNumber) throws Exception{

        Assert.assertTrue(storeNumber.contains(getRestaurantNumber().getControl().getText()),"Restaurant is not displayed");
        //Assert.assertTrue(isRestaurantNumberDisplayedCorrect(storeNumber),"Restaurant is not displayed");
        Logz.step("Restaurant is displayed");
    }
}
