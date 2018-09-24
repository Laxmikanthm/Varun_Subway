package pages;

import base.gui.controls.browser.*;
import base.pages.browser.BrowserBasePage;
import base.test.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import pojos.Login;
import utils.Logz;

public class LoginPage<T extends RemoteWebDriver> extends BrowserBasePage {

    private static By drpSelectDataSource = By.id("ddlDataSource");
    private By drpSelectSSOItem = By.id("ddlSsoItems");
    private By txtBoxUserID = By.id("txtUserId");
    private By btnLoginDirectly = By.id("btnGetAndSubmitUser");
    private By lnkHeaderLogo = By.xpath("//a[@class='header-logo']");
    private By lnkSelectRestaurant = By.xpath("//a[@class='header-profile-welcome-link']/span[contains(text(),'"+ BaseTest.getStringfromBundleFile("linkSwitch")+"')]");

    public LoginPage(RemoteWebDriver driver) throws Exception {
        super(driver);
        Logz.step(getDropdownSelectDataSource().elementIsDisplayedAndEnabled()?"This is Login page":"Login Page is not displayed");
    }

    private Generic getDropdownSelectDataSource() throws Exception {
        return new Generic(driver, drpSelectDataSource, "select DataSource drop down");
    }
    private SelectBox getDataSource(String option) throws Exception {
        return new SelectBox(driver, By.xpath("//select[@id='ddlDataSource']/option[text()='" + option + "']"), "Data Source ");
    }
    private Generic getDropdownSelectSSOItem() throws Exception {
        return new Generic(driver, drpSelectSSOItem, "select SSO Item drop down");
    }
    private SelectBox getSSOItem(String option) throws Exception {
        return new SelectBox(driver, By.xpath("//select[@id='ddlSsoItems']/option[text()='" + option + "']"), "SSO Item");
    }
    private TextBox getUserIdTextBox() throws Exception {
        return new TextBox(driver, txtBoxUserID, "UserID text field");
    }
    private Button getLoginDirectlyButton() throws Exception {
        return new Button(driver, btnLoginDirectly, " Login Directly Button ");
    }

    private LinkText getHeaderLogoLink() throws Exception
    {
        return new LinkText(driver,lnkHeaderLogo,"Header Logo");
    }

    private LinkText getSelectRestaurantLink() throws Exception
    {
        return new LinkText(driver,lnkSelectRestaurant,"Select Restaurant");
    }

    private void selectUserId(String userId) throws Exception {
        getUserIdTextBox().setText(userId);
        Logz.step("Clicked on Submit Login directly button ");
    }

    private void selectDataSource(String value) throws Exception {
        getDropdownSelectDataSource().getControl().click();
        Logz.step("Clicked on Data Source drop down link");
        getDataSource(value).getControl().click();
        Logz.step("Selected value : " + value);
    }

    private void selectSSOItem(String value) throws Exception {
        getDropdownSelectSSOItem().getControl().click();
        Logz.step("Clicked on SSO Item drop down link");
        getSSOItem(value).getControl().click();
        Logz.step("Selected value : " + value);
    }

    public SwitchRestaurantPage SSOLogin(Login login) throws Exception {
        try {
            selectDataSource(login.getDataSource());
            selectSSOItem(login.getSSOItem());
            selectUserId(login.getUserId());
            getLoginDirectlyButton().click();
            if (driver.getTitle().equalsIgnoreCase("User id is required")){
                throw new Exception("Please Enter the Valid User Id!!! ");
            }
        }
        catch(Exception e){
            throw new Exception("Please Enter the Valid User Id!!! ");
            }
        return new SwitchRestaurantPage(driver);
    }

    public HomePage SSOLoginForSingleRestaurant(Login login) throws Exception {
        selectDataSource(login.getDataSource());
        selectSSOItem(login.getSSOItem());
        selectUserId(login.getUserId());
        getLoginDirectlyButton().getControl().click();
        driver.navigate().refresh();
        return new HomePage(driver);
    }
}

