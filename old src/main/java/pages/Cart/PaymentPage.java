package pages.Cart;

import base.gui.controls.browser.*;
import base.pages.browser.BrowserBasePage;
import base.test.BaseTest;
import com.amazonaws.services.dynamodbv2.xspec.L;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.CommonMethodsPage;
import util.Common;
import utils.Logz;

import java.security.PublicKey;
import java.util.List;

public class PaymentPage<T extends RemoteWebDriver> extends BrowserBasePage {
	//private static By drpPaymentMethod = By.xpath("//select[@class='order-wizard-paymentmethod-selector-module-options']");
    //private static By drpPaymentMethod = By.xpath("//select[@class='order-wizard-msr-shipmethod-package-option-select']");
    private static By drpPaymentMethod = By.xpath("//select[@class='order-wizard-paymentmethod-selector-module-options']");
	private By btnBack = By.xpath("//button[contains(text(),'" + BaseTest.getStringfromBundleFile("back") + "')]");
	private By btnContinue = By.xpath("//button[contains(text(),'"+BaseTest.getStringfromBundleFile("continue")+"')]");
	private By lnkEdit = By.xpath("//a[contains(text(),'" + BaseTest.getStringfromBundleFile("edit") + "')]");
	private By lnkChangeAddress = By.xpath("//a[contains(text(),'" + BaseTest.getStringfromBundleFile("changeAddress") + "')]");
	private By chkSplitPayment = By.id("split-check");
	private By chkShippingAddress = By.xpath("//label[contains(text(),'"+BaseTest.getStringfromBundleFile("checkbox")+"')]/input");
	private By btnSplitAmongAllResturants = By.xpath("//button[@data-action='add_all']");
	private By drpSelectResturant = By.xpath("//div[@id='modal-body']//select");
	private By resturantList = By.xpath("//table[@id='percentage_input']//tr[not(@class)]");
	private By tblSelectedRestaurants = By.id("percentage_input");
	private By btnSelectAddress = By.xpath("//button[@class='address-details-select-address']");
	private By headerBillingAddress = By.xpath("//button[@class='address-details-select-address']");
	private By btnAddToPayment = By.xpath("//button[@class='btn btn-success' and @data-action='select_restaurant']");
	private By lnkDelete = By.xpath("//a[@data-action='remove-element']");
	private By txtStoreId = By.xpath("//table[@id='percentage_input']//tr/td[@class='padding-horiz-s']");
	private By btnAccept = By.xpath("//button[@class='btn btn-primary selectConsolidator']");
	private By txtAddressName = By.xpath("//div[@class='address-details-info']/p/b");
	private By drpResturantSelect = By.xpath("//select[@class='list-header-filter-input']/option");
	private By tblList = By.xpath("//table[@id='percentage_input']//tr/td[@class='padding-horiz-s']");
	private By txtSubTotalPrice = By.xpath("//div[@class='order-wizard-cart-summary-subtotal']//span[@class='order-wizard-cart-summary-grid-right']");
	private By btnAddNewAddress = By.xpath("//a[@class='order-wizard-address-module-new-button']");
	private By btnPlaceOrder = By.xpath("//div[@class='order-wizard-submitbutton-container']//button[text()='"+BaseTest.getStringfromBundleFile("placeOrder")+"']");

	public CheckoutPage checkoutPage;

	public PaymentPage(RemoteWebDriver driver) throws Exception {
		super(driver);
		//Logz.step(getPaymentMethodDropdown().elementIsDisplayedAndEnabled()?"This is Payment page":"Payment Page is not displayed");
	}

	private Generic getTableStoreId() throws Exception {
		return new Generic(driver, txtStoreId, "Restaurant Number");
	}
	private Button getAcceptButton() throws Exception {
		return new Button(driver, btnAccept, "Accept Button");
	}
	private Button getAddToPaymentButton() throws Exception {
		return new Button(driver, btnAddToPayment, "Add to Payment Buttom");
	}
	private LinkText getDeleteLink() throws Exception {
		return new LinkText(driver, lnkDelete, "Delete Link");
	}
	private Generic getBillingAddressHeader() throws Exception {
		return new Generic(driver, headerBillingAddress, "Billing Address Header");
	}
	private List<WebElement> getSelectAddressButton() throws Exception {
		return getBillingAddressHeader().getWebElements(btnSelectAddress, "Select Address Button");
	}
	private Generic getRestaurantList() throws Exception {
		return new Generic(driver, tblSelectedRestaurants, "Restaurant List");
	}
	private List<WebElement> getRestaurantSplitList() throws Exception {
		return getRestaurantList().getWebElements(resturantList, "Restaurant List");
	}
	private SelectBox getSelectRestaurantDropdown() throws Exception {
		return new SelectBox(driver, drpSelectResturant, "Select Restaurant Dropdown");
	}
	private Button getSplitAmongAllRestaurantsButton() throws Exception {
		return new Button(driver, btnSplitAmongAllResturants, "Split Among All Restaurants");
	}
	private SelectBox getPaymentMethodDropdown() throws Exception {
		return new SelectBox(driver, drpPaymentMethod, "Payment Method Dropdown");
	}
	private Button getBackButton() throws Exception {
		return new Button(driver, btnBack, "Back Button");
	}
	private Button getContinueButton() throws Exception {
		return new Button(driver, btnContinue, "Continue Button");
	}
	private LinkText getEditLink() throws Exception {
		return new LinkText(driver, lnkEdit, "Edit Link");
	}
	private LinkText getChangeAddressLink() throws Exception {
		return new LinkText(driver, lnkChangeAddress, "Change Address Link");
	}

	private CheckBox getSplitPaymentCheckbox() throws Exception {
		return new CheckBox(driver, chkSplitPayment, "Split Payment Checkbox");
	}

	private CheckBox getShippingAddressCheckbox() throws Exception {
		return new CheckBox(driver, chkShippingAddress, "Shipping Address Checkbox");
	}
	private Generic getAddressName() throws Exception {
		return new Generic(driver, txtAddressName, "Address Name");
	}
	private Generic getTableList() throws Exception {
		return new Generic(driver, tblList, "Table List");
	}
	private Generic getDrpdwnRestaurantSelect() throws Exception {
		return new Generic(driver, drpResturantSelect, "Select Drop down List");
	}
	private Generic getSubtotalPrice() throws Exception {
		return new Generic(driver, txtSubTotalPrice, "Subtotal Price");
	}
	private Button getAddNewAddressButton() throws Exception{
		return new Button(driver, btnAddNewAddress, "Add New Address Button");
	}

	public void selectPaymentMethodDropdown(String Name) throws Exception {
		Common.waitForOperations(driver, drpPaymentMethod);
		getPaymentMethodDropdown().selectFromDropDown(Name);
		Logz.step("Selected from Dropdown");
	}

	public CheckoutPage selectBackButton() throws Exception {
		getBackButton().click();
		Logz.step("Clicked on Back Button");
		return new CheckoutPage(driver);
	}

	public ReviewYourOrderPage selectContinueButton() throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, BaseTest.EXPLICIT_WAIT_TIME);
		wait.until(ExpectedConditions.elementToBeClickable(btnContinue));

		getContinueButton().click();
		Logz.step("Clicked on Continue Button");
        Common.waitForLoadingCompletion(driver);
		//Common.waitForOperations(driver,btnPlaceOrder);
		return new ReviewYourOrderPage(driver);
	}

	public void selectEditLink() throws Exception {
		getEditLink().click();
		Logz.step("Clicked on Edit Link");
	}

	public void selectChangeAddressLink() throws Exception {
		LinkText lnkChangeAddr = getChangeAddressLink();
		if (lnkChangeAddr.elementIsDisplayedAndEnabled()) {
			lnkChangeAddr.click();
			Logz.step("Clicked on change Address Link");
			getSelectAddressButton().get(0).click();
		} else {
			getSelectAddressButton().get(0).click();
		}
	}

	public void selectSplitPaymentCheckbox() throws Exception {
		getSplitPaymentCheckbox().check();

		Logz.step("Clicked on Split Payment Checkbox");
	}

	public void selectShippingAddressCheckbox() throws Exception {
		CheckBox shippingAddrChkBox = getShippingAddressCheckbox();
		if (!shippingAddrChkBox.isChecked())
		{
			shippingAddrChkBox.getControl().click();
			Logz.step("Clicked on Shipping Address Checkbox");
		}
		else
        {
            Logz.step("Shipping Address Checkbox is already checked");
        }
	}

	public void verifyPaymentMethodHasOnlyInvoice() throws Exception {
		SelectBox paymentMethodDropDown = getPaymentMethodDropdown();
		int dropdownSize = paymentMethodDropDown.getListOptions().size();
		if (dropdownSize == 1) {
			String dropdownText = paymentMethodDropDown.getListOptions().get(0).getText();
			Assert.assertEquals(dropdownText, " " + BaseTest.getStringfromBundleFile("invoice") + " ", "Invoice payment method is not available");
		} else {
			Logz.step("Payment method contains more than one payment type");
		}
	}

	public void verifySplitPaymentOptionAvailable() throws Exception {
		Assert.assertTrue(getSplitPaymentCheckbox().elementIsDisplayedAndEnabled(),
				"splitPayment checkbox is available");
		Logz.step("splitPayment checkbox is not available");

	}
	public void verifySplitPaymentOptionNotAvailable() throws Exception {
		Assert.assertEquals(checkForSplitPaymentOption(), false, "splitPayment checkbox is available");
		Logz.step("splitPayment checkbox is not available");
	}

	private boolean checkForSplitPaymentOption() throws Exception {
		try {
			getSplitPaymentCheckbox().elementIsDisplayedAndEnabled();
		} catch (NoSuchElementException e) {
			return false;
		}
		return true;
	}

	public void verifySplitPaymentPopup() throws Exception {
		selectSplitPaymentCheckbox();
		WebDriverWait wait = new WebDriverWait( driver, BaseTest.EXPLICIT_WAIT_TIME);
		wait.until(ExpectedConditions.visibilityOfElementLocated(btnSplitAmongAllResturants));
		Assert.assertTrue(getSplitAmongAllRestaurantsButton().elementIsDisplayedAndEnabled(),"Split Payment Popup is not displayed");
		Logz.step("Split Payment Popup is available");
	}

	public void verifySplitPaymentAmongRestaurants() throws Exception {
		verifySplitPaymentPopup();
		int restaurantListSize = getSelectRestaurantDropdown().getListOptions().size();
		Logz.step("List of Restaurants Before Selecting Split Among All Restaurants: " + restaurantListSize);
		getSplitAmongAllRestaurantsButton().click();
		int splitListSize = getRestaurantSplitList().size();
		Logz.step("List of Restaurants After Selecting Split Among All Restaurants: " + splitListSize);
		Assert.assertEquals(restaurantListSize, splitListSize, "Restaurant List is not same");
		Logz.step("Restaurant List is same");
		getAcceptButton().click();
	}

	public void verifySplitRestaurantsAddAndDelete() throws Exception {

		List<WebElement> restaurantsList;
		
		Generic drpdwnRestaurantSelect;

		while (((drpdwnRestaurantSelect = getDrpdwnRestaurantSelect()).getWebElements(drpResturantSelect, "Select Drop down List").size()) > 0) {
			restaurantsList = drpdwnRestaurantSelect.getWebElements(drpResturantSelect, "Select Drop down List");
			String selectedRestaurant = restaurantsList.get(0).getText();
			getAddToPaymentButton().click();
			List<WebElement> addedRestaurants = getTableList().getWebElements(tblList, "Table List");
			boolean matched = addedRestaurants.stream().anyMatch(ele -> ele.getText().equalsIgnoreCase(selectedRestaurant));
			Assert.assertTrue(matched, "Selected restaurant is not added to the list");
			Logz.step(selectedRestaurant + " restaurant added to payment");
			if (((restaurantsList.size()) - 1) == 0) {
				break;
			}
		}
		List<WebElement> restaurantsTableList;

		while ((restaurantsTableList = getTableStoreId().getWebElements(txtStoreId, "Restaurant Number")).size() > 0) {
			String deletedRestaurant = restaurantsTableList.get(0).getText();
			getDeleteLink().click();
			List<WebElement> restaurantList = drpdwnRestaurantSelect.getWebElements(drpResturantSelect, "Select Drop down List");
			boolean matched = restaurantList.stream().anyMatch(ele -> ele.getText().equalsIgnoreCase(deletedRestaurant));
			Assert.assertTrue(matched, "Selected restaurant is not deleted from the list");
			Logz.step(deletedRestaurant + " restaurant is deleted");
			if (((restaurantsTableList.size()) - 1) == 0) {
				break;
			}
		}
	}

	public void verifyAddSplitRestaurantAndAccept() throws Exception {
		
		Generic drpDwnRestaurantSelect = getDrpdwnRestaurantSelect();
		
		List<WebElement> restaurantsList;
        restaurantsList = drpDwnRestaurantSelect.getWebElements(drpResturantSelect, "Select Drop down List");
        int iIndex = restaurantsList.size();

		while (iIndex > 0) {
			String selectedRestaurant = restaurantsList.get(0).getText();
			getAddToPaymentButton().click();
			Common.waitForLoadingCompletion(driver);
			List<WebElement> addedRestaurants = getTableList().getWebElements(tblList, "Table List");
			boolean matched = addedRestaurants.stream().anyMatch(ele -> ele.getText().equalsIgnoreCase(selectedRestaurant));
			Assert.assertTrue(matched, "Selected restaurant is not added to the list");
			Logz.step(selectedRestaurant + " restaurant added to payment");
			if (((restaurantsList.size()) - 1) == 0) {
				break;
			}
            restaurantsList = drpDwnRestaurantSelect.getWebElements(drpResturantSelect, "Select Drop down List");
        }
            getAcceptButton().click();
			WebDriverWait wait = new WebDriverWait(driver,BaseTest.EXPLICIT_WAIT_TIME);
			wait.until(ExpectedConditions.elementToBeClickable(chkSplitPayment));
			Assert.assertTrue(getSplitPaymentCheckbox().isChecked(), "Split Payment Checkbox is not Checked");
			Logz.step("Split Payment Checkbox is checked");

	}

	public void verifySplitPaymentAmongRestaurantsNotUnderLitigation(int restaurantListSize) throws Exception {

		Logz.step("Total Number of Restaurants available are " + restaurantListSize);
		getSplitAmongAllRestaurantsButton().click();
		int splitListSize = getRestaurantSplitList().size();
		Logz.step("Split List Size of Reestaurants is " + splitListSize);
		Assert.assertNotEquals(restaurantListSize, splitListSize,
				"Restaurant  not under litigation is available in dropdown");
		Logz.step("Restaurant  not under litigation is not available in dropdown");
	}

	public String[] getPaymentPageAddressLine() throws Exception {
		checkoutPage = new CheckoutPage(driver);
		String[] arrPaymentPageAddress = checkoutPage.getAddressLine();
		return arrPaymentPageAddress;
	}

	public float verifySubTotal() throws Exception {
		String totalFinal = getSubtotalPrice().getControl().getText();
		totalFinal = totalFinal.replaceAll("[$,]", "");
		float total = Float.parseFloat(totalFinal);
		return total;
	}

	public PaymentPage selectBillingAddress() throws Exception{

		if(getAddNewAddressButton().elementIsDisplayedAndEnabled()){
			getSelectAddressButton().get(0).click();
			Logz.step("Clicked on Select Address Button");
		}else{
			Logz.step("Billing address is selected");
		}
		return new PaymentPage(driver);
	}
}