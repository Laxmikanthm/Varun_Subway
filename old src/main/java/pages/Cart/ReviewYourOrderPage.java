package pages.Cart;

/**
 * Created by E001891 on 12/7/2017.
 */

import base.gui.controls.browser.*;
import base.pages.browser.BrowserBasePage;
import base.test.BaseTest;
import com.amazonaws.services.dynamodbv2.xspec.L;
import controls.TextBoxJS;
import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.CommonMethodsPage;
import util.Common;
import utils.Logz;
import java.util.List;

public class ReviewYourOrderPage<T extends RemoteWebDriver> extends BrowserBasePage{

	private By btnBack = By.xpath("//button[text()='" + BaseTest.getStringfromBundleFile("back") + "']");
	private By btnBottomPlaceOrder = By.xpath("//div[@class='order-wizard-step-button-container']//button[text()='"+ BaseTest.getStringfromBundleFile("bottomPlaceOrder") + "']");
	private By btnPlaceOrder = By.xpath("//div[@class='order-wizard-submitbutton-container']//button[text()='"+ BaseTest.getStringfromBundleFile("placeOrder") + "']");
	private By btnEdit = By.xpath("//a[@class='address-details-edit-address']");
	private By btnEditCart = By.xpath("//a[@class='order-wizard-cartitems-module-ship-edit-cart-link']");
	private By drpDeliveryMethod = By.id("delivery-options");
	private By drpPromoCode = By.xpath("//a[@class='order-wizard-promocodeform-expander-head-toggle collapsed']");
	private By lnkCancel = By.id("//a[text()='" + BaseTest.getStringfromBundleFile("cancel") + "']");
	private By btnAccept = By.xpath("//button[contains(text(),'" + BaseTest.getStringfromBundleFile("accept") + "')]");
	private By btnApply = By.xpath("//button[text()='" + BaseTest.getStringfromBundleFile("apply") + "']");
	private By pageHeader = By.xpath("//h2[text()='" + BaseTest.getStringfromBundleFile("reviewOrderPage")+ "']");// region
																													// Constructor
	private static By reviewPageHeader = By.xpath("//h2[@class='order-wizard-step-title']");
	private By txtItemQuantity = By.xpath("//span[@class='transaction-line-views-cell-navigable-item-quantity-value']");
	private By txtShipPhonenumber = By.xpath("//div[@class='address-details-container' and @data-manage='shipaddress']//p[@class='address-details-phone']");
	private By txtBillPhoneNumber = By.xpath("//div[@class='address-details-container' and @data-manage='billaddress']//p[@class='address-details-phone']");
	private By txtEditPhoneNumber = By.xpath("//input[@id='in-modal-phone']");
	private By btnUpdateAddress = By.xpath("//button[@class='address-edit-form-button-submit']");
	private By itemSKU = By.xpath("//*[@class='order-wizard-cartitems-module-ship']//span[@class='product-line-sku-value']");
	private By drpPaymentMethod = By.xpath("//select [@class='order-wizard-paymentmethod-selector-module-options']");
	private By btnSubmit = By.xpath("//button[@class='order-wizard-submitbutton-module-button']");
	private By txtaddressLine = By.xpath("//*[@class='module-rendered']//div[@class='address-details-info']/p/b");
	private By paymentType = By.xpath("//div[@class='global-views-format-payment-method']//p['global-views-format-payment-method-invoice']");
	private By txtSubTotalPrice = By.xpath("//div[@class='order-wizard-cart-summary-subtotal']//span[@class='order-wizard-cart-summary-grid-right']");
	private By backToEditBilling = By.xpath("//a[contains(text(),' " + BaseTest.getStringfromBundleFile("backToEditBillingInfo") + " ')]");

	public CommonMethodsPage commonMethodsPage;

	public ReviewYourOrderPage(RemoteWebDriver driver) throws Exception {
		super(driver);
		Logz.step(getReviewPageHeader().elementIsDisplayedAndEnabled() ? "This is Review Your Order Page": "Review Your Order Page is not displayed");
		commonMethodsPage = gotoCommonMethodsPage();
	}

	// This method will return the commonMethodsPage driver object
	public CommonMethodsPage gotoCommonMethodsPage() throws Exception {
		return new CommonMethodsPage(driver);
	}

	private SelectBox getPaymentMethodDropdown() throws Exception {
		return new SelectBox(driver, drpPaymentMethod, "Payment Method Dropdown");
	}

	private Generic getTextItemQuantity() throws Exception {
		return new Generic(driver, txtItemQuantity, "Item Quantity");
	}

	private Generic getTxtAddressLine() throws Exception {
		return new Generic(driver, txtaddressLine, "Ship to Address Line");
	}

	private List<WebElement> getCityStateZip() throws Exception {
		return getTxtAddressLine().getWebElements(
				By.xpath("//*[@class='module-rendered']//div[@class='address-details-info']/p/span"),
				"City,State and Zip Address Lists");
	}

	private Button getUpdateAddress() throws Exception {
		return new Button(driver, btnUpdateAddress, "Update Address");
	}

	private Button getSubmitButton() throws Exception {
		return new Button(driver, btnSubmit, "Submit Button");
	}

	private TextBox getShipPhoneNumber() throws Exception {
		return new TextBox(driver, txtShipPhonenumber, "Ship Phone Number");
	}

	private TextBox getBillPhoneNumber() throws Exception {
		return new TextBox(driver, txtBillPhoneNumber, "Ship Phone Number");
	}

	private TextBoxJS getEditPhoneNumber() throws Exception {
		return new TextBoxJS(driver, txtEditPhoneNumber, "Edit Phone Number");
	}

	private LinkText getEditButton() throws Exception {
		return new LinkText(driver, btnEdit, "edit");
	}

	private Button getPlaceOrderButton() throws Exception {
		return new Button(driver, btnPlaceOrder, "button Place Order");
	}

    private Button getButtonAccept() throws Exception {
        return new Button(driver, btnAccept, "Accept Button");
    }

	private Button getBottomPlaceOrderButton() throws Exception {
		return new Button(driver, btnBottomPlaceOrder, "bottom Place Order");
	}

	private LinkText getEditCartButton() throws Exception {
		return new LinkText(driver, btnEditCart, "edit");
	}

	private Button getBackButton() throws Exception {
		return new Button(driver, btnBack, "Back button");
	}

	private SelectBox getDeliveryMethod() throws Exception {
		return new SelectBox(driver, drpDeliveryMethod, "selectConsolidator drop down");
	}

	private SelectBox getDeliveryType(String option) throws Exception {
		return new SelectBox(driver, By.xpath("//select[@id='quantity']/option[text()='" + option + "']"),
				"DeliveryType ");
	}

	private Generic getAcceptBtn() throws Exception {
		return new Generic(driver, btnAccept, "Accept button");
	}

	private Button getApplyButton() throws Exception {
		return new Button(driver, btnApply, "Back button");
	}

	private Generic getPromoCodeButton() throws Exception {
		return new Generic(driver, drpPromoCode, "selectConsolidator drop down");
	}

	private Generic getPageHeader() throws Exception {
		return new Generic(driver, pageHeader, "review your order header");
	}

	private Generic getReviewPageHeader() throws Exception {
		return new Generic(driver, reviewPageHeader, "review your order page header");
	}

	private LinkText getCancelLink() throws Exception {
		return new LinkText(driver, lnkCancel, "Cancel link");
	}

	private TextBox txtPromoCode() throws Exception {
		return new TextBox(driver, btnEditCart, "edit");
	}

	private Generic getItemSKU() throws Exception {
		return new Generic(driver, itemSKU, "Product SKU in Review Order page");
	}

	private TextBox getPaymentType() throws Exception {
		return new TextBox(driver, paymentType, "Ship Phone Number");
	}

	private Generic getSubtotalPrice() throws Exception {
		return new Generic(driver, txtSubTotalPrice, "Subtotal Price");
	}

	private Generic geBackToEditBilling() throws Exception {
		return new Generic(driver, backToEditBilling, "Subtotal Price");
	}

	public ReviewYourOrderPage verifyProdSKU(String itemSKU) throws Exception {
		String ProdSKU = getItemSKU().getControl().getText();
		Logz.step("Product SKU is " + ProdSKU);
		Assert.assertEquals(ProdSKU,itemSKU,"Product SKU and Item SKU are not Equal");
		Logz.step("Item SKU Is verified");
		return new ReviewYourOrderPage(driver);
	}

	public void clickOnEdit() throws Exception {
		getEditButton().click();
		Logz.step("Clicked on Edit");
	}

	public void clickOnEditCart() throws Exception {
		getEditCartButton().getControl().click();
		Logz.step("Clicked on Edit cart");
	}

	public String[] getReviewOrderAddressLine() throws Exception {
		String[] address = new String[getCityStateZip().size()+1];
		int j = 0;
		address[j] = getTxtAddressLine().getControl().getText();
		for (int i = 0; i < getCityStateZip().size(); i++) {
			address[++j] = getCityStateZip().get(i).getText();
		}
		return address;
	}

	public void clickOnPlaceOrder() throws Exception {
		if (Common.isElementPresent(driver,btnPlaceOrder)) {
			getPlaceOrderButton().click();
		} else {
			Logz.error("!!!Did not find the Place Order Button to click on it");
		}
	}

	public void clickOnBottomPlaceOrder() throws Exception {
		getBottomPlaceOrderButton().getControl().click();
		Logz.step("Clicked on Place Order button");
	}

	public void selectDeliveryMethod(String option) throws Exception {
		getDeliveryMethod().getControl().click();
		Logz.step("Clicked on Delivery Method drop down ");
		getDeliveryType(option).getControl().click();
		Logz.step("Selected option : " + option);
	}

	public void selectPromoCode(String PromoCode) throws Exception {
		getPromoCodeButton().getControl().click();
		Logz.step("Clicked on PromoCode drop down ");
		txtPromoCode().setText(PromoCode);
		Logz.step("Selected PromoCode : " + PromoCode);
		getApplyButton().getControl().click();
		Logz.step("Clicked on Apply Button");
	}

	public void verifyUserNavigateToReviewOrderPage() throws Exception {
		Assert.assertTrue(getPageHeader().elementIsDisplayedAndEnabled());
		Logz.step("Review Order Page is Opened");
	}

	public void clickOnCancel() throws Exception {
		getCancelLink().getControl().click();
		Logz.step("Clicked on cancel");
	}

	public OrderConfirmationPage clickOnAccept() throws Exception {
		//Wait Till Accept Button is Visible
		Common.waitForOperations(driver, btnAccept);
		if (getAcceptBtn().getControl().isDisplayed()) {
			getAcceptBtn().getControl().click();
		} else {
			Logz.error("!!!Did not find the Accept button with terms and conditions pop up");
		}
		return new OrderConfirmationPage(driver);
	}

	public void verifyItemQuantity(String qnty) throws Exception {
		Assert.assertTrue(qnty.equals(getTextItemQuantity().getControl().getText().toString()));
		Logz.step("Item Quantity is Verified");
	}

	public void EditAddress() throws Exception
	{
		String beforeupdatePhoneNumber = getShipPhoneNumber().getText();
		Logz.step("Original Number before change: " + beforeupdatePhoneNumber);

		clickOnEdit();

        WebDriverWait wait = new WebDriverWait(driver,BaseTest.EXPLICIT_WAIT_TIME);
        wait.until(ExpectedConditions.visibilityOfElementLocated(txtShipPhonenumber));

		String Number = RandomStringUtils.randomNumeric(10);
		Logz.step("New Number: " + Number);
		getEditPhoneNumber().setText(Number);
		Logz.step("New Phone Number is set");
		Logz.step("Clicked on Update Address Button");
        //Corrected Edit Phone Number Method Call
		String updatedPhoneNumber = getEditPhoneNumber().getText();
        getUpdateAddress().click();
		Logz.step("Updated phone number: " + updatedPhoneNumber);
		Assert.assertNotEquals(updatedPhoneNumber, beforeupdatePhoneNumber, "User Cannot Edit Address");
		Logz.step("User is able to edit address");

	}

	public void verifyOrderSummary(float reviewTotal) throws Exception {

		String subTotal = getSubtotalPrice().getControl().getText();
		subTotal = subTotal.replaceAll("[$,]", "");
		float subtotal = Float.parseFloat(subTotal);
Logz.info("SubTotal:::" + subTotal);
Logz.info("SubTotal:::" + reviewTotal);
		Assert.assertEquals(subtotal, reviewTotal, "Total Price is Not correct in Review Page");
		Logz.step("Subtotal value in Review Your Order Page" + subtotal);
	}

	public void verifyPaymentMethodHasOnlyInvoice() throws Exception {
		Common.waitForOperations(driver, drpPaymentMethod);
		List<WebElement> paymentMethodDrpDwnLst = getPaymentMethodDropdown().getListOptions();
		int dropdownSize = paymentMethodDrpDwnLst.size();
		if (dropdownSize == 1) {
			String dropdownText = paymentMethodDrpDwnLst.get(0).getText();
			Assert.assertEquals(dropdownText.trim(), "" + BaseTest.getStringfromBundleFile("invoice") + "".trim(),
					"Invoice payment method is not available");
			Logz.step("Payment Method contains only Invoice");
		} else {
			Logz.step("payment Method contains moreThan one payment type");
		}
	}

	public OrderConfirmationPage clickSubmitButton() throws Exception {
		getSubmitButton().click();
		Logz.step("Clicked on Submit Button");
		return new OrderConfirmationPage(driver);
	}
}
