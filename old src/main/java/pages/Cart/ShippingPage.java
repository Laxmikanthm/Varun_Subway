package pages.Cart;

import base.gui.Control;
import base.gui.controls.browser.Button;
import base.gui.controls.browser.Generic;
import base.pages.browser.BrowserBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.Logz;


public class ShippingPage<T extends RemoteWebDriver> extends BrowserBasePage {

    private By btnShipToASingleAddress = By.xpath("//a[@class='order-wizard-msr-enablelink-module-link']");
    private By btnAddOrEditAddress = By.xpath("//a[@class='order-wizard-msr-package-creation-shipping-add-edit-link']");
    private By btnEditCart = By.xpath("//a[@class='order-wizard-cart-summary-edit-cart-link']");
    private By btnSelectConsolidator = By.xpath("//div[@class='cart-add-to-cart-button']/button");
    private static By btnContinueButton = By.xpath("//button[@class='order-wizard-step-button-continue']");
    private By btnBack = By.xpath("//button[@class='order-wizard-step-button-back']");
    private By btnCreateShipment = By.xpath("//button[@class='order-wizard-msr-package-creation-button-create']");
    private By btnShipToThisAddress = By.xpath("//button[@class='address-details-select-address']");


    public ShippingPage(RemoteWebDriver driver) throws Exception {
        super(driver);
        Logz.step(getButtonContinue().elementIsDisplayedAndEnabled()?"This is Shipping page":"Shipping Page is not displayed");
    }

    private Generic getButtonShipToASingleAddress() throws Exception {
        return new Generic(driver, btnShipToASingleAddress, "I Want To Ship To a Single Address");
    }
    private Generic getButtonAddOrEditAddress() throws Exception {
        return new Generic(driver, btnAddOrEditAddress, "Add Or Edit Address");
    }
    private Generic getButtonEditCart() throws Exception {
        return new Generic(driver, btnEditCart, "Edit Cart");
    }
    private Button getButtonSelectConsolidator() throws Exception {
        return new Button(driver, btnSelectConsolidator, "Select Consolidator");
    }
    private Button getButtonContinue() throws Exception {
        return new Button(driver, btnContinueButton, "Continue");
    }
    private Button getButtonBack() throws Exception {
        return new Button(driver, btnBack, "Back");
    }
    private Button getButtonCreateShipment() throws Exception {
        return new Button(driver, btnCreateShipment, "Create Shipment");
    }
    private Button getButtonShipToThisAddress() throws Exception {
        return new Button(driver, btnShipToThisAddress, "Ship To This Address");
    }

    public void editCart() throws  Exception {
        getButtonEditCart().getControl().click();
        Logz.step("Clicked on EditCart link.");
    }

    public void navigateToPaymentPage() throws Exception {
        getButtonContinue().getControl().click();
        Logz.step("Clicked on Continue button");
    }
}
