package pages.Cart;

import base.gui.controls.browser.Generic;
import base.gui.controls.browser.SelectBox;
import base.gui.controls.browser.TextBox;
import base.pages.browser.BrowserBasePage;
import base.test.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.Logz;

public class QuickOrderPage<T extends RemoteWebDriver> extends BrowserBasePage {

    private By txtFieldSearch = By.xpath("//input[@placeholder='Enter SKU or Item Name']");
    private By txtBoxQuantity=By.id("quantity");
    private By btnAddItem=By.xpath("//button[text()="+ BaseTest.getStringfromBundleFile("addItem")+"]");
    private By btnCheckOut=By.xpath("//button[text()="+BaseTest.getStringfromBundleFile("proceedToCheckout")+"]");
    private By drpSelectOrderType = By.xpath("//div[@class='cart-detailed']//button[contains(text(),"+ BaseTest.getStringfromBundleFile("selectOrderType")+"]");
    private By drpSelectItem=By.xpath("//div[@class='tt-dataset-3']");

    public QuickOrderPage(RemoteWebDriver driver) throws Exception {
        super(driver);
        Logz.step(getAddItemButton().elementIsDisplayedAndEnabled()?"This is Quick Order Page":"Quick Order Page is not displayed");
    }

    private TextBox getTxtFieldSearch() throws Exception{
        return new TextBox(driver, txtFieldSearch,"Search text field");
    }
    private TextBox getQuantityTextBox() throws Exception{
        return new TextBox(driver, txtBoxQuantity,"Quantity text field");
    }
    private TextBox getAddItemButton() throws Exception{
        return new TextBox(driver, btnAddItem,"Add Item");
    }
    private Generic getCheckOutButton() throws Exception{
        return new Generic(driver, btnCheckOut," Proceed to Checkout button");
    }
    private Generic getSelectItemDropdown() throws Exception{
        return new Generic(driver, drpSelectItem,"SelectOrderType drop down");
    }
    private Generic getDropdownSelectOrderType() throws Exception{
        return new Generic(driver, drpSelectOrderType,"SelectOrderType drop down");
    }
    private SelectBox getOrderTypeValue(String option) throws Exception{
        return new SelectBox(driver, By.xpath("//div[@class = 'btn-group open']//a[text() = '"+option+"']"),"SelectOrderType option PreAuth");
    }

    public void selectOrderType(String orderType) throws Exception {
        getDropdownSelectOrderType().getControl().click();
        Logz.step("Clicked on SelectOrderType drop down link");
        getOrderTypeValue(orderType).getControl().click();
        Logz.step("Selected orderType : " + orderType);
    }

    public CheckoutPage selectItemAndProceedToCheckout(String ItemName, String Quantity) throws Exception {
        getTxtFieldSearch().setText(ItemName);
        Logz.step("Entered Item name: " + ItemName);
        getSelectItemDropdown().getControl().click();
        Logz.step("Selected Item Item name: " + ItemName);
        getQuantityTextBox().setText(Quantity);
        Logz.step("Entered Item Quantity: " + Quantity);
        getAddItemButton().getControl().click();
        getCheckOutButton().getControl().click();
        return new CheckoutPage(driver);
    }
}







