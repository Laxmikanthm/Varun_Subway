package pages.MyAccountOverview;

import base.gui.controls.browser.Generic;
import base.gui.controls.browser.LinkText;
import base.gui.controls.browser.SelectBox;
import base.pages.browser.BrowserBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.Logz;

public class ReturnsPage<T extends RemoteWebDriver> extends BrowserBasePage {

    private static By drpSelectSort = By.xpath("//select[@class='list-header-view-accordion-body-select']");
    private By txtFieldTitle = By.xpath("//header[@class='return-authorization-list-header']/h2");

    public ReturnsPage(RemoteWebDriver driver) throws Exception {
        super(driver);
        Logz.step(getSelectSortFromDropdown().elementIsDisplayedAndEnabled()?"Returns page is displayed":"Returns page is not displayed");
    }

//    private LinkText getTextFieldTile() throws  Exception {
//        return new LinkText(driver, txtFieldTitle, "Title");
//    }
    private SelectBox getSortTypeValue(String option) throws Exception {
        return new SelectBox(driver, By.xpath("//select[@class='list-header-view-accordion-body-select']/option[text()='" + option + "']"), "Sort");
    }
    private Generic getSelectSortFromDropdown() throws Exception {
        return new Generic(driver, drpSelectSort, "Select Sort");
    }

    public void selectSortFormDropDown(String value) throws Exception {
        getSelectSortFromDropdown().getControl().click();
        Logz.step("Clicked on Sort drop down link");
        getSortTypeValue(value).getControl().click();
        Logz.step("Selected value : " + value);
    }
}
