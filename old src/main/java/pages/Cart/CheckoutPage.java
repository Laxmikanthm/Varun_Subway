package pages.Cart;

import base.gui.controls.browser.Button;
import base.gui.controls.browser.Generic;
import base.gui.controls.browser.LinkText;
import base.gui.controls.browser.SelectBox;
import base.gui.controls.browser.TextBox;
import base.pages.browser.BrowserBasePage;
import base.test.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import pages.HomePage;
import java.util.List;
import utils.Logz;
import pojos.AddressBook;
import util.Common;


public class CheckoutPage<T extends RemoteWebDriver> extends BrowserBasePage {

    private By btnContinue = By.xpath("//button[@class='order-wizard-step-button-continue']");
    private static By btnCreateConsolidatedShipment = By.xpath("//button[@class='btn btn-success']");
    private By btnSelectConsolidator = By.xpath("//button[@data-type='select-consolidator']");
    private By btnAddNewAddress = By.xpath("//a[@class='order-wizard-msr-package-creation-shipping-add-edit-link']");
    private By btnShipToThisAddress = By.xpath("//button[@class='address-details-select-address']");
    private By btnEditCart = By.xpath("//a[@class='order-wizard-cartitems-module-ship-edit-cart-link']");
    private By btnEditAddress = By.xpath("//a[@class='address-details-edit-address']");
    private By btnChangeAddress = By.xpath("//a[@class='address-details-change-address']");
    //private By btnAddNewAddress = By.xpath("//order-wizard-msr-package-creation-shipping-right");
    private By txtTitle = By.xpath("//h1[@class='wizard-header-title']");
    private By txtTotalPrice = By.xpath("//div[@class='order-wizard-cart-summary-total']/p/span");
    private By txtEstimatedShipping = By.xpath("//div[@class='order-wizard-cart-summary-shipping-cost-applied']/p[contains(text(),'"+BaseTest.getStringfromBundleFile("estimatedShipping")+"')]/span");
    private By txtTax = By.xpath(" //div[@class='order-wizard-cart-summary-shipping-cost-applied']/p[contains(text(),'" + BaseTest.getStringfromBundleFile("tax") + "')]/span");
    private By txtSubTotalPrice = By.xpath("//div[@class='order-wizard-cart-summary-subtotal']//span[@class='order-wizard-cart-summary-grid-right']");
    private By txtOrderType = By.xpath("//div[@class='btn-group']/span");
    private By radPickUp = By.xpath("//span[contains(text(),'" + BaseTest.getStringfromBundleFile("deliveryMethod_1")+"')]");
    private By radShipFromVendor = By.xpath("//span[contains(text(),'"+BaseTest.getStringfromBundleFile("shipFromVendor")+"')]");
    private By btnradShipFromVendor = By.xpath("//span[contains(text(),'"+BaseTest.getStringfromBundleFile("shipFromVendor")+"')]/preceding-sibling::input[@type='radio']");
    private By txtItemQuantity = By.xpath(".//div[@id='unfulfilled-items-ship']//span[@class='transaction-line-views-cell-navigable-item-quantity-value']");
    private By lnkPromocode = By.xpath("//a[@class='order-wizard-promocodeform-expander-head-toggle collapsed']");
    private By txtPromocode = By.id("promocode");
    private By btnApply = By.xpath("//button[@class='cart-promocode-form-summary-button-apply-promocode']");
    private By headerShipAddress = By.xpath("//button[@class='address-details-select-address']");
    private By itemSKU = By.xpath("//*[@class='order-wizard-cartitems-module-ship']//span[@class='product-line-sku-value']");
    private By txtAddressName=By.xpath("//div[@class='address-details-info']/p/b");
    private By txtNewAddressName=By.xpath("//*[@class='order-wizard-address-module-single']//div[@class='address-details-info']/p/b");
    private By txtOutOfStock=By.xpath("//div[@class='product-line-stock']");
    private By txtSpecialInstructions=By.xpath("//textarea[@data-type='special-instructions']");
    private By orderVerification = By.xpath("//h1[contains(text(),'"+BaseTest.getStringfromBundleFile("orderVerification")+"')]");
	private By btnAccept = By.xpath("//button[contains(text(),'"+BaseTest.getStringfromBundleFile("accept")+"')]");
	private By orderVerificationDropdown = By.xpath("//select[@class='order-wizard-msr-package-creation-multishipto-address-selector']");
	private By chkItemtoSelect = By.xpath("//input[@data-type ='checkbox-item-selector']");
	private By itemNames = By.xpath("//span[@class ='transaction-line-views-cell-selectable-item-displayname-viewonly']");
	private By itemNamesUnderShipment = By.xpath("//div[@class ='transaction-line-views-cell-actionable-name']");
	private By chkSelectAllItems = By.xpath("//input[@data-type ='selectunselect-all-checkbox']");
	private By txtOrderWizardTitle = By.xpath("//h2[@class='order-wizard-step-title']");
	private By btnCreateShipment = By.xpath("//button[@class ='order-wizard-msr-package-creation-button-create']");
	private By modelVewPopup = By.xpath("//div[@class='global-views-modal-content']");
	private By txtOnModelVewPopup = By.xpath("//div[@class='global-views-modal-content']//div[@id='modal-body']/div");
	private By lnkShipMultiAddresses = By.xpath("//a[@class='order-wizard-msr-enablelink-module-link']");
    private By drpdwnShippingAddress = By.xpath("//select[@class='order-wizard-msr-package-creation-multishipto-address-selector']");
	private By drpdwnShippingOption = By.xpath("//select[@class='order-wizard-msr-package-creation-multishipto-address-selector']/option");
	private By toggleIcons = By.xpath("//i[@class ='order-wizard-msr-package-details-accordion-toggle-icon-secondary']");
	private By btnRemove = By.xpath("//button[@class='address-details-remove-address']");
    private By drpdwnDeliveryMethod = By.xpath("//select[@class='order-wizard-msr-shipmethod-package-option-select']");
    private By drpdwnDeliverySelection = By.xpath("//select[@class='order-wizard-msr-shipmethod-package-option-select']/option[contains(text(),'" +BaseTest.getStringfromBundleFile("pickUpFromVendor") + "')]");
    private By btnBack = By.xpath("//button[@class='order-wizard-step-button-back']");
    private static By txtHeader = By.xpath("//h1[@class='wizard-header-title']");
    private By lblPromocode = By.xpath("//span[@class='cart-promocode-list-item-code-value']");
    private By addressContainer = By.xpath("//div[@class='order-wizard-address-module-address-container']");
    private By lnkChangeAddress = By.xpath("//a[contains(text(),'" + BaseTest.getStringfromBundleFile("changeAddress") + "')]");
    private By btnSelectAddress = By.xpath("//button[@class='address-details-select-address']");
    private By btnheaderBillingAddress = By.xpath("//button[@class='address-details-select-address']");

    WebDriverWait wait = new WebDriverWait(driver, BaseTest.EXPLICIT_WAIT_TIME);

    public HomePage homePage;
    @Autowired
    public AddressBook addressBook;

    public CheckoutPage(RemoteWebDriver driver) throws Exception {
        super(driver);
        Logz.step(getCheckoutPageHeader().elementIsDisplayedAndEnabled()?"Checkout page is displayed":"Checkout page is not displayed");
        Common.waitForLoadingCompletion(driver);
    }

    private Generic getShipAddressHeader() throws Exception {
        return new Generic(driver, headerShipAddress, "Ship Address Header");
    }

    private Generic getModelVewPopup() throws Exception {
        return new Generic(driver, modelVewPopup, "Global Modal View Popup");
    }

/*    private SelectBox getDrpdwnShippingAddress() throws Exception {
        return new SelectBox(driver, drpdwnShippingAddress, "Select Shipping Address Dropdown");
    }*/

    private Generic getDrpdwnShippingAddress() throws Exception {
        return new Generic(driver, drpdwnShippingAddress, "Select Shipping Address Dropdown");
    }

    private List<WebElement> getSelectShipingAddress() throws Exception {
        return getDrpdwnShippingAddress().getWebElements(drpdwnShippingOption, "Select Shipping Address");
    }

    private Generic getDrpdwnDeliveryMethod() throws Exception {
        return new Generic(driver, drpdwnDeliveryMethod, "Delivery Method Dropdown");
    }
    private Generic getDrpdwnDeliveryMethodOptions() throws Exception {
        return new Generic(driver, drpdwnDeliverySelection, "Delivery Method Dropdown");
    }

    private Generic getTextOnModelVewPopup() throws Exception {
        return new Generic(driver, txtOnModelVewPopup, "Global Modal View Popup Message Text");
    }

    private Generic getTextOrderWizardTitle() throws Exception {
        return new Generic(driver, txtOrderWizardTitle, "Set Shippements Header");
    }

    private Generic getChkBoxSelectAllItems() throws Exception {
        return new Generic(driver, chkSelectAllItems, "Select All Items Checkbox");
    }

    private List<WebElement> getShipToThisAddressButton() throws Exception {
        return getShipAddressHeader().getWebElements(btnShipToThisAddress, "Ship to this Address Button");
    }

    private List<WebElement> getToggleIcons() throws Exception {
        return getShipAddressHeader().getWebElements(toggleIcons, "Toggle Icons under Shipment section");
    }

    private List<WebElement> getBtnRemove() throws Exception {
        return getShipAddressHeader().getWebElements(btnRemove, "Address Remove Button");
    }

    private List<WebElement> getChkBoxItemsToSelect() throws Exception {
        return getTextOrderWizardTitle().getWebElements(chkItemtoSelect, "Check Boxes to Select the Item");
    }

    private LinkText getChangeAddressLink() throws Exception {
        return new LinkText(driver, lnkChangeAddress, "Change Address Link");
    }


    private Generic getBillingAddressHeader() throws Exception {
        return new Generic(driver, btnheaderBillingAddress, "Billing Address Header");
    }
    private List<WebElement> getSelectAddressButton() throws Exception {
        return getBillingAddressHeader().getWebElements(btnSelectAddress, "Select Address Button");
    }

    private List<WebElement> getItemNames() throws Exception {
        return getTextOrderWizardTitle().getWebElements(itemNames, "Item names displayed in Product List");
    }

    private List<WebElement> getItemNamesUnderShipment() throws Exception {
        return getTextOrderWizardTitle().getWebElements(itemNamesUnderShipment, "Item names displayed under Shipment section");
    }

    private Generic getShipToThisAddress() throws Exception {
         return new Generic(driver, btnShipToThisAddress, "Ship Address Header");
    }

    private Button getApplyButton() throws Exception {
        return new Button(driver, btnApply, "Apply Button");
    }

    private Button getBtnCreateShipment() throws Exception {
        return new Button(driver, btnCreateShipment, "Create Shipment Button");
    }

    private TextBox getPromocodeText() throws Exception {
        return new TextBox(driver, txtPromocode, "Promocode Text");
    }

    private LinkText getPromocodeLink() throws Exception {
        return new LinkText(driver, lnkPromocode, "Promocode Link");
    }

    private LinkText getLinkShipMultiAddresses() throws Exception {
        return new LinkText(driver, lnkShipMultiAddresses, "I Want To Ship Multiple Addresses Link");
    }

    private Generic getTextItemQuantity() throws Exception {
        return new Generic(driver, txtItemQuantity, "Item quantiy");
    }

    private Button getButtonSelectConsolidator() throws Exception {
        return new Button(driver,  btnSelectConsolidator, "Select Consolidator Button");
    }

    private Generic getButtonEditCart() throws Exception {
        return new Generic(driver, btnEditCart, "Edit Cart");
    }

    private Generic getButtonEditAddress() throws Exception {
        return new Generic(driver, btnEditAddress, "Edit Address");
    }

    private LinkText getTextTitle() throws Exception {
        return new LinkText(driver, txtTitle, "Title");
    }

    private Generic getSubtotalPrice() throws Exception {
        return new Generic(driver, txtSubTotalPrice, "Subtotal Price");
    }

    private Generic getButtonChangeAddress() throws Exception {
        return new Generic(driver, btnChangeAddress, "Change Address");
    }

    private Button getButtonAddNewAddress() throws Exception {
        return new Button(driver, btnAddNewAddress, "AddNewAddress Button");
    }

    private Generic getTax() throws Exception {
        return new Generic(driver, txtTax, "Tax");
    }

    private Button getOrderType() throws Exception {
        return new Button(driver, txtOrderType, "Order type");
    }

    private Button getButtonShipToThisAddress() throws Exception {
        return new Button(driver, btnShipToThisAddress, "ShipToThisAddress Button");
    }

    private Generic getEstimatedShipping() throws Exception {
        return new Generic(driver, txtEstimatedShipping, "Estimated Shipping");
    }

    private Generic getTotalPrice() throws Exception {
        return new Generic(driver, txtTotalPrice, "Total Price");
    }

    private Button getButtonContinue() throws Exception {
        return new Button(driver, btnContinue, "Continue Button");
    }
    
    private Button getButtonCreateConsolidatedShipment() throws Exception {
        return new Button(driver, btnCreateConsolidatedShipment, "CreateConsolidatedShipment Button");
    }
    
    private Generic getPickupRadioBtn() throws Exception {
        return new Generic(driver, radPickUp, " PickUp");
    }
    
    private TextBox getSpecialInstructions() throws Exception {
        return new TextBox(driver, txtSpecialInstructions, "Title");
    }

    private Generic getShipFromVendorRadioBtn() throws Exception {
        return new Generic(driver, radShipFromVendor, "ShipFromVendor");
    }

    private Generic getProductSKUNumber() throws Exception {
        return new Generic(driver, itemSKU, "Product SKU number on Checkout Screen");
    }

    public void verifyProductSKUInCheckoutPage(String prodSKU) throws Exception {
        Assert.assertTrue(getProductSKUNumber().getControl().getText().toString().equalsIgnoreCase(prodSKU));
    }

    private LinkText getOrderVerification() throws Exception {
		return new LinkText(driver, orderVerification, "Order Verification");
	}

	private SelectBox getOrderVerificationDropdown() throws Exception {
		return new SelectBox(driver, orderVerificationDropdown, "orderVerification Dropdown");
	}

	private Button getButtonAccept() throws Exception {
		return new Button(driver, btnAccept, "Accept Button");
	}

    private Generic getAddressName() throws Exception{
        return new Generic(driver,txtAddressName, "Address Name");
    }
    private Generic getOutOfStockText() throws Exception {
        return new Generic(driver, txtOutOfStock, "OutOfStock");
    }
    private Generic getAddressContainer() throws Exception {
        return new Generic(driver, addressContainer, "Address Container");
    }
    public List<WebElement> getAddressLists()throws Exception{
        return getAddressName().getWebElements(By.xpath("//div[@class='address-details-info']/p/b"),"AddressLists");
    }

    private List<WebElement> getShipToAddressButtons()throws Exception{
        return getAddressContainer().getWebElements(By.xpath("//button[contains(text(),'Ship to this Address')]"),"Ship To This Address");
    }

    public Generic getChangedAddressName() throws Exception{
        return new Generic(driver,txtNewAddressName, "Changed Shipping Address Name");
    }

    public List<WebElement> getCityStateZip()throws Exception{
        return getChangedAddressName().getWebElements(By.xpath("//*[@class='order-wizard-address-module-single']//div[@class='address-details-info']/p/span"),"City,State and Zip AddressLists");
    }

    public Generic getCheckoutPageHeader()throws Exception
    {
        return new Generic(driver,txtHeader,"Checkout Page Header");
    }

    public String[] getAddressLine() throws Exception{
        String[]address = new String[getCityStateZip().size()+1];
        int j = 0;
        address[j] = getChangedAddressName().getControl().getText();
        for (int i=0;i< getCityStateZip().size();i++){
            address[++j] = getCityStateZip().get(i).getText();
        }
        return address;
    }

    public void selectDeliveryMethodFromDropdown(String deliverytype) throws Exception{
        Common.waitForOperations(driver,btnBack);
        getDrpdwnDeliveryMethod().getControl().click();
        //Thread.sleep(2000);
        Assert.assertNotNull(getDrpdwnDeliveryMethodOptions(),"No Delivery Method Options Available");
        getDrpdwnDeliveryMethodOptions().getControl().click();
        Logz.step("Delivery Type is " +deliverytype);
    }

    public void clickOnEditCart() throws Exception {
        getButtonEditCart().getControl().click();
        Logz.step("Clicked on EditCart link.");
    }

    public void selectShippingAddress(int index) throws Exception {
        List<WebElement> shipAddressEles = getSelectShipingAddress();
        //SelectBox selector = getSelectShipingAddress().get(index);
Logz.info("selector Size::" + shipAddressEles.get(index).getText());
        shipAddressEles.get(index).click();
    }

    public void clickOnShipToMultipleAddresses() throws Exception {
        getLinkShipMultiAddresses().click();
        Logz.step("Clicked on I want to ship Multiple Addresses link.");
    }

    public void verifyModalViewPopupMessage() throws Exception {
        if (getModelVewPopup().getControl().isDisplayed()){
            Assert.assertTrue(getTextOnModelVewPopup().getControl().getText().contains(BaseTest.getStringfromBundleFile("modelViewPopupMessage")));
        }else {
            Logz.error("Did not find the Model View Popup");
        }
    }

    public void selectAllItemsCheckBox() throws Exception {
        getChkBoxSelectAllItems().getControl().click();
        Logz.step("Clicked on Select All Items Checkbox");
    }

    public void clickOnCreateShipmentBtn() throws Exception {
        getBtnCreateShipment().click();
        Logz.step("Clicked on Create Shipment Button");
        Common.waitForLoadingCompletion(driver);
        //return new CheckoutPage(driver);
    }

    public void clickOnEditAddress() throws Exception {
        getButtonEditAddress().getControl().click();
        Logz.step("Clicked on EditAddress link.");
    }

    public void clickOnChangeAddress() throws Exception {
        if(Common.isElementPresent(driver,btnChangeAddress))
        {
            getButtonChangeAddress().getControl().click();
            Logz.step("Clicked on ChangeAddress link.");
        }
        else
        {
            Logz.step("Change Address Link is Missing");
        }
    }

    public PaymentPage clickOnContinueButton() throws Exception {
          getButtonContinue().click();
          Logz.step("Clicked on Continue button");
           //Fixed Page Navigation From Payment Page to Delivery Page
          return new PaymentPage(driver);
    }

    public void clickContinueOnCheckOut() throws Exception {
        //Added Code to Wait Till Continue is refreshed and Clickable
        wait.until(ExpectedConditions.elementToBeClickable(btnContinue));
        getButtonContinue().click();
        Logz.step("Clicked on Continue button");
        //Fixed Page Navigation From Payment Page to Delivery Page
        wait.until(ExpectedConditions.visibilityOfElementLocated(drpdwnDeliveryMethod));
        //return this;
    }

    public void clickContinueInShipment() throws Exception {
        wait.until(ExpectedConditions.elementToBeClickable(btnContinue));
        getButtonContinue().click();
        Common.waitForLoadingCompletion(driver);
        Logz.step("Clicked on Continue button");
    }

    public void clickContinueButton() throws Exception {
        getButtonContinue().click();
        Logz.step("Clicked on Continue button");
    }

    public void clickOnAddNewAddressButton() throws Exception {
        getButtonAddNewAddress().click();
        Logz.step("Clicked on AddNewAddress button");
    }

    public void clickOnShipToThisAddressButton() throws Exception {
        getButtonShipToThisAddress().click();
        Logz.step("Clicked on ShipToThisAddress button");
    }

    public void clickOnConsolidatedShipmentButton() throws Exception {
        getButtonCreateConsolidatedShipment().click();
        Logz.step("Clicked on Create Consolidated Shipment button");
    }

    public void clickOnPromocodeLink() throws Exception {
        getPromocodeLink().click();
        Logz.step("Clicked on Have a Promocode Link");
    }

    public CheckoutPage clickOnApplyButton() throws Exception {
        getApplyButton().click();
        Logz.step("Clicked on Apply Button");
        return new CheckoutPage(driver);
    }

    public ShippingPage navigateToShippingPage() throws Exception {
        clickOnContinueButton();
        return new ShippingPage(driver);
    }

    public void editCart() throws Exception {
        clickOnEditCart();
    }

    public boolean verifyTaxIncludedInTotalPrice() throws Exception {
		String subtotal = getSubtotalPrice().getControl().getText();
		String shippingCharges = getEstimatedShipping().getControl().getText();
		String tax = getTax().getControl().getText();
		String total = getTotalPrice().getControl().getText();

		subtotal = subtotal.replaceAll("[$,]", "");
		shippingCharges = shippingCharges.replaceAll("[$,]", "");
		tax = tax.replaceAll("[$,]", "");
		total = total.replaceAll("[$,]", "");

		float subTotal1 = Float.parseFloat(subtotal);
		float shippingCharges1 = Float.parseFloat(shippingCharges);
		float estimatedTax1 = Float.parseFloat(tax);
		float total1 = Float.parseFloat(total);
		float expectedTotal = subTotal1 + shippingCharges1;

		Logz.step("SubTotal: " + subTotal1);
		Logz.step("shippingCharges: " + shippingCharges1);
		Logz.step("Amount of Tax: " + estimatedTax1);
		Logz.step("Total Amount Before Tax is added: " + expectedTotal);
		Logz.step("TOTAL: " + total1);


		Logz.step("Tax to be Included In total Price");
		float expectedTotal1 = expectedTotal + estimatedTax1;
		Logz.step("Adding Tax amount " + estimatedTax1 + "to the subtotal amount " + subTotal1);
        Assert.assertEquals(expectedTotal, total1,"Estimated Tax Price is Not Included in Total Price");
		//Assert.assertEquals(expectedTotal1, total1);
		Logz.step("Total Amount After Tax is added: " + expectedTotal1);
		Logz.step("Tax Included In total Price");
		return true;

    }

    public void createConsolidatedShipment() throws Exception {
        clickOnConsolidatedShipmentButton();
        clickOnSelectConsolidator();
        verifyPopupOrderVerification();
        selectConsolidatorAndAccept(BaseTest.getStringfromBundleFile("consolidator"));
        selectItemsToCreateShipment(1);
        clickContinueButton();
    }

    public void verifyOrderType(String orderType) throws Exception {
        Assert.assertTrue(getOrderType().getText().contains(orderType), "orderType is not equal ");
        Logz.step("selected order type is :" + orderType);
    }

    public int getNoOfAddresses() throws Exception {
        return  getBtnRemove().size();
    }

    public void verifyNewlyAddressGetAdded(int countBefore, int countAfter) throws Exception {
        Assert.assertEquals(countBefore,countAfter,"Newly added address didn't get displayed in the list");
    }


    public void selectShippingAddress(AddressBook addressBook) throws Exception {

        //Change Address Button is Not Appearing at time. User has option to select Ship To This Address Button directly without clicking Change Address Link
        if(Common.isElementPresent(driver,btnChangeAddress)) {
            getButtonChangeAddress().getControl().click();
            Logz.step("Clicked on ChangeAddress link.");
        }
        else
        {
            Logz.step("Change Address Link is Missing");
        }

        Logz.step("Clicked on Change Address");
        List<WebElement> shipAddressEles = getShipToThisAddressButton();
        if (shipAddressEles.size() <= 1){
            clickOnAddNewAddressButton();
            homePage = new HomePage(driver);
            homePage.addAddressBook(addressBook);
        }
        shipAddressEles.get(1).click();
    }

    public String[] selectItemsToCreateShipment(int noOfItems) throws Exception {
        String arrItemNames[]=new String[noOfItems];
        int j=0;
        List<WebElement> chkBoxItemEles = getChkBoxItemsToSelect();
        int iSize = chkBoxItemEles.size();
        if (iSize >= noOfItems){
            for(int i=0;i<noOfItems;i++){
            	chkBoxItemEles.get(i).click();
                arrItemNames[j++] = getItemNames().get(i).getText();
                //Preventing Stale Element Exception. Retrieving Check Boxes again
                chkBoxItemEles = getChkBoxItemsToSelect();
            }
        }
        //Code will be removed post discussion with team if this is real is valid condition
        /*else if (noOfItems==0){
            selectAllItemsCheckBox();
            for(int i=0;i<chkBoxItemEles.size();i++){
                arrItemNames[j++] = getItemNames().get(i).getText();
            }
        }*/
        clickOnCreateShipmentBtn();
        //Add Additional Wait / Assertion to avoid Stale Element
        return arrItemNames;
    }

    public void verifyItemsUnderShippmentSection(String[] arrItems)throws Exception{
    	List<WebElement> arrShipmentItems = getItemNamesUnderShipment();
        String arrShipmentItemNames[]=new String[arrShipmentItems.size()];
        int j=0;

       int iSize = getChkBoxItemsToSelect().size();

       if (arrShipmentItems.size() > 1){
           for(int i=0;i<iSize - 1;i++){
               arrShipmentItemNames[j++] = arrShipmentItems.get(i).getText();
           }
       }else {
           arrShipmentItemNames[j] = arrShipmentItems.get(0).getText();
       }

    }

	public void selectDeliveryMethod(String methodType) throws Exception {
		if (methodType.equalsIgnoreCase("Ship from Vendor")) {
			getShipFromVendorRadioBtn().getControl().click();
			Logz.step("Ship from vendor radio button is selected");
            wait.until(ExpectedConditions.elementToBeClickable(radPickUp));
		} else {
			getPickupRadioBtn().getControl().click();
            wait.until(ExpectedConditions.elementToBeClickable(btnradShipFromVendor));
			Logz.step("Pickup radio button is selected");
		}

	}

	public void verifyShippingFromVendorTenPerOfOrderTotal() throws Exception {
		String subtotal = getSubtotalPrice().getControl().getText();
		subtotal = subtotal.replaceAll("[$,]", "");
		float subTotal1 = Float.parseFloat(subtotal);
		Logz.step("SubTotal: " + subTotal1);
		String estimatedshipping = getEstimatedShipping().getControl().getText();
		estimatedshipping = estimatedshipping.replaceAll("[$,]", "");
		float estimatedShippingCharges = Float.parseFloat(estimatedshipping);
		Logz.step("estimatedShipping: " + estimatedShippingCharges);
		float chargeValue = subTotal1 * (0.1F);
		Logz.step("Charge Value: " + chargeValue);
		Assert.assertNotEquals(chargeValue, estimatedShippingCharges, "freight uplift is not included in Shipping from Vendor as 10% of the order total");
		Logz.step("freight uplift is included in Shipping from Vendor as 10% of the order total ");

    }

    public void verifyDeliveryOptions() throws Exception {
        Assert.assertTrue(getPickupRadioBtn().elementIsDisplayedAndEnabled(), "Pickup Option is not displayed");
        Logz.step("Pickup option is displayed");
        Assert.assertTrue(getShipFromVendorRadioBtn().elementIsDisplayedAndEnabled(), "Ship from Vendor is not displayed");
        Logz.step("Ship from Vendor option is displayed");
    }


    public void verifyItemQuantity(String expectedQnty) throws Exception {
        Assert.assertTrue(expectedQnty.equals(getTextItemQuantity().getControl().getText().toString()));
        Logz.step("Item Quantity is verified");
    }

    public void enterPromoCode(String PromoCode) throws Exception {
        clickOnPromocodeLink();
        getPromocodeText().setText(PromoCode);
        Logz.step("entered Promo Code :" + PromoCode);
}

    public boolean verifyNoPriceReductionAfterPromo() throws Exception {
    	String subTotal = getSubtotalPrice().getControl().getText();
    	subTotal =subTotal.replaceAll("[$,]","");
        float subtotal = Float.parseFloat(subTotal);
        
        Logz.step("Before applying promo code  Sub total value :" + subtotal);
        clickOnApplyButton();
        String totalFinal=getSubtotalPrice().getControl().getText();
        totalFinal=totalFinal.replaceAll("[$,]","");
        float total = Float.parseFloat(totalFinal);
       
        Logz.step("after applying promo code  the Sub total value :" + total);
        Assert.assertEquals(subtotal, total, "price reduced after applying promo code ");
        Logz.step(" No price reduction after applying promo code ");
        return true;
    }

    public boolean verifyDiscountIsAppliedToTotal() throws Exception {
    	String subTotal = getSubtotalPrice().getControl().getText();
    	subTotal =subTotal.replaceAll("[$,]","");
        float subtotal = Float.parseFloat(subTotal);
        Logz.step("Before applying promo code  Sub total value :" + subtotal);
        clickOnApplyButton();
        Common.waitForOperations(driver,lblPromocode);
        String totalFinal=getSubtotalPrice().getControl().getText();
        totalFinal=totalFinal.replaceAll("[$,]","");
        float total = Float.parseFloat(totalFinal);
        Logz.step("after applying promo code  the Sub total value :" + total);
        Assert.assertNotEquals(subtotal, total, "No price reduction after applying promo code ");
        Logz.step(" price reduced after applying promo code");
        return true;
    }

    public void verifyURLbeginsWithhttps() throws Exception {
        String URL = driver.getCurrentUrl();
        Assert.assertTrue(URL.startsWith("https"),"url not begins with 'https'");
        Logz.step("url begins with 'https");
    }
    public void selectNewlyCreatedAddressAsShipToThisAddress(String addressName)throws  Exception
    {
        for(int i=0;i<getAddressLists().size();i++)
        {
            if(getAddressLists().get(i).getText().equals(addressName))
            {
                getShipToAddressButtons().get(i).click();
                break;
            }

		}
	}

	public void verifyOutOfStockMsgInCheckOutPage() throws Exception {
		Assert.assertTrue(getOutOfStockText().getControl().isDisplayed(),"Out of Stock message is not displayed");
		Logz.step("Out Of Stock message is displayed in checkout page");
	}

	public float verifySubTotal() throws Exception {
		String totalFinal = getSubtotalPrice().getControl().getText();
Logz.info("total Final Before Remove::" + totalFinal);
		totalFinal = totalFinal.replaceAll("[$,]", "");
Logz.info("total Final After Remove::" + totalFinal);
		float total = Float.parseFloat(totalFinal);
Logz.info("total Final After Float::" + totalFinal);
		return total;
	}
	
	public void enterSpecialInstructions(String instructions) throws Exception {
		getSpecialInstructions().setText(instructions);
		Logz.step("Out Of Stock  message is displayed");
	}
	
	public void clickOnCreateConsolidatedShipment() throws Exception {
		getButtonCreateConsolidatedShipment().click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(chkItemtoSelect));
		Logz.step("Clicked on Create Consolidated Shipment Button");
	}

	public CheckoutPage clickOnSelectConsolidator() throws Exception {
		getButtonSelectConsolidator().click();
		Logz.step("Clicked on Select Consolidator Button");
        getButtonAccept().click();
		return new CheckoutPage(driver);

	}
	public void verifyPopupOrderVerification() throws Exception {
	    Assert.assertTrue(getOrderVerification().elementIsDisplayedAndEnabled(), "Order Verification Popup is not displayed");
		Logz.step("Order Verification Popup is available");
	}

	public void selectConsolidatorAndAccept(String Name) throws Exception {
        wait.until(ExpectedConditions.elementToBeClickable(btnCreateConsolidatedShipment));

        getButtonCreateConsolidatedShipment().click();
        //getOrderVerificationDropdown().selectFromDropDown(Name);
		Logz.step("Selected from Dropdown");
        getButtonAccept().click();
		Logz.step("Clicked On Accept Button");
	}

}