package pages;

import base.gui.controls.browser.*;
import base.gui.controls.browser.Button;
import base.pages.browser.BrowserBasePage;
import base.test.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.Cart.ProductDescriptionPage;
import pages.Cart.ProductListPage;
import pages.Cart.SearchResultsPage;
import pages.Cart.ShoppingCartPage;
import pages.MyAccountOverview.*;
import pages.ShopByArea.*;
import utils.Logz;
import java.sql.Timestamp;
import java.util.List;
import pojos.AddressBook;
import util.Common;

public class HomePage<T extends RemoteWebDriver> extends BrowserBasePage {

	// Home - page
	public By drpSelectOrdertype = By.xpath("//ul/li//button[contains(text(),' "+ BaseTest.getStringfromBundleFile("dropdownSelectOrderType") + " ')]");
	public By drpOrdertype = By.xpath("//button[contains(text(),'" + BaseTest.getStringfromBundleFile("orderTypePreAuth")+ "') or contains(text(),'" + BaseTest.getStringfromBundleFile("orderType313") + "')]");
	private By drpMenu = By.xpath("//ul[@class = 'dropdown-menu']");
	private By lnkRequestQuote = By.xpath("//a[@title='" + BaseTest.getStringfromBundleFile("linkRequestQuote") + "']");
	private By lnkQuickOrder = By.xpath("//a[@title= '" + BaseTest.getStringfromBundleFile("linkQuickOrder") + " ']");
	private By iconSearch = By.xpath("//button/i[@class= '" + BaseTest.getStringfromBundleFile("iconSearch") + "']");
	private By lnkSwitchStore = By.xpath("//span[text() = '"+ BaseTest.getStringfromBundleFile("linkSwitch") +"']");
	private By lnkShoppingCart = By.xpath("//div[@class='header-menu-cart-dropdown']/div/a");
	private By lnkViewCart = By.xpath("//div[@class='header-mini-cart-buttons-left']/a");
	private static By lnkShopByArea = By.xpath(".//a[text()=' Shop by Area ']");
	private By lnkRemoveCart = By.xpath("//div[@class='cart-lines-item-actions-desktop']//a[contains(text(),' " + BaseTest.getStringfromBundleFile("Remove") + " ')]");
	private By txtCartIsEmpty = By.xpath("//div[@class='header-mini-cart-empty']/a");
	private By cartItemsCount=By.xpath("//span[@class='header-mini-cart-menu-cart-legend']");

	// Search icon
	private By txtFieldSearch = By.xpath("//input[@class='itemssearcher-input typeahead tt-input']");
	private By btnGo = By.xpath("//button[text()='" + BaseTest.getStringfromBundleFile("buttonGo") + "']");
	private By txtInputTextArea = By.xpath("//input[contains(@class,'tt-input')]");
	private By lnkHeaderShop = By.xpath("//nav[@class='header-menu-secondary-nav']//a[text()=' "+ BaseTest.getStringfromBundleFile("linkHeaderShop") + " ']");
	private By drpSearch = By.xpath("//div[@class='site-search-content-input']//span[@class='tt-dropdown-menu' and contains(@style,'display: block')]");
	private By searchList = By.xpath("//div[@class='tt-suggestion']//div[@class='itemssearcher-item-results-content']//div[@class='itemssearcher-item-results-title']");
    //private By tblsearchResults = By.xpath("//span[@class='facets-facet-browse-title-alt' and contains(text(),'" + BaseTest.getStringfromBundleFile("itemNumber")+ "')]");
    private By tblsearchResults = By.xpath("//img[@class='facets-item-cell-grid-image')]");

	// Account_Overview
	private By accountOverview = By.xpath("//i[@class='header-profile-welcome-carret-icon']");
	private By chkEmailPreference = By.xpath("//div[@class='profile-emailpreferences-controls']//label[@class='profile-emailpreferences-label]'//input[@id='emailsubscribe']");
	private By txtEmailPreference = By.xpath("//div[@class='profile-emailpreferences-alert-placeholder']//div[@class='global-views-message global-views-message-success alert']");
	private By btnUpdate = By.xpath("//div[@class='profile-emailpreferences-controls-submit']//button[@class='profile-emailpreferences-submit']");
	private By txtAccountBalance = By.xpath("//div[@class='balance-indicator-details-outstanding-balance']//span[@class='balance-indicator-details-outstanding-label']");
	private By lblCompanyName = By.xpath("//div[@class='profile-information-row']/label[contains(text(),'"+BaseTest.getStringfromBundleFile("labelCompanyName")+"')]");
	private By lblEmail = By.xpath("//div[@class='profile-information-row']/label[contains(text(),'"+BaseTest.getStringfromBundleFile("labelEmail")+"')]");
	private By lblPhoneNumber = By.xpath("//div[@class='profile-information-row']/label[contains(text(),'"+BaseTest.getStringfromBundleFile("labelPhoneNumber")+"')]");
	//private By btnAddNewAddress = By.xpath("//div[@class='address-list-button-container']//a[@class='address-list-button-info-cards-new']");
	private By btnAddNewAddress = By.xpath("//a[contains(text(),'Add New Address')]");
	private By chkSelectAll = By.xpath("//div/label/input[@id= ' " + BaseTest.getStringfromBundleFile("checkBoxSelectAll") + " ']");
	private By btnAddToCart = By.xpath("//div/button[contains(text(),' " + BaseTest.getStringfromBundleFile("buttonAddToCart") + " ')]");
	private By btnViewCart = By.xpath("//div/a[contains(text(),' " + BaseTest.getStringfromBundleFile("buttonViewCart") + " ')]");
	private By btnProceedToCheckout = By.xpath("//div/a[@id= ' " + BaseTest.getStringfromBundleFile("buttonProceedToCheckout") + " ']");
	private By txtPageNotFound = By.xpath("//div/h1[contains(text()= ' " + BaseTest.getStringfromBundleFile("textPageNotFound") + " ']");
	private By editName = By.xpath("//div/input[@id= ' " + BaseTest.getStringfromBundleFile("editName") + " ']");
	private By btnChildUpdate = By.xpath("//div/button[contains(text(),' " + BaseTest.getStringfromBundleFile("buttonChildUpdate") + " ')]");
	private By newFullName = By.xpath("//p[@class='address-details-container-multiselect-address-title");
	private By editFullName = By.xpath("//input[@id='in-modal-fullname']");
    //private By btnUpdateAddress=By.xpath("//button[@class='address-edit-form-button-submit']");
	private By btnSaveAddress=By.xpath("//button[contains(text(),'Save Address')]");
	private By btnUpdateAddress=By.xpath("//button[contains(text(),'Update Address')]");
	private By btnCancelAddress=By.xpath("//button[contains(text(),'Cancel')]");
	private By txtFullName =By.id("in-modal-fullname");
	private By txtAddress =By.id("in-modal-addr1");
	private By txtCity =By.id("in-modal-city");
	private By txtZipCode =By.id("in-modal-zip");
	private By txtPhoneNum =By.id("in-modal-phone");
	private By chkResidentialAddress = By.id("in-modal-isresidential");
	private By chkDefaultShipping = By.id("in-modal-defaultshipping");
	private By selectBoxState =By.xpath("//select[@class='input-xlarge global-views-states-group-select']");
	private By txtAddressName=By.xpath("//div[@class='address-details-info']/p/b");
	private By searchResultsTitles=By.xpath("//a[@class='facets-item-cell-grid-title']/span");
	private By lnkSearchresultsTitles=By.xpath("//a[@class='facets-item-cell-grid-title']");
	private By btnRemove = By.xpath("//button[@class='global-views-confirmation-confirm-button' and contains(text(),'"+BaseTest.getStringfromBundleFile("yes")+"')]");
	private By txtProfileInfoHeader=By.xpath("//h2[@class='profile-information-header']");
	private By lnkHeaderLogo=By.xpath("//a[@class='header-logo']");
	private By lnkActOverview=By.xpath("//a[@class='header-menu-myaccount-overview-anchor']");
	private static By lnkHomePage = By.xpath("//div[@class='home']//a[@name='orderhistory']");
    //private static By lnkHome = By.xpath("//a[text()='Home Page']");
	private By lnkExteriorThumbnail = By.xpath("//div[@class='facets-category-cell-thumbnail']//img[@alt='" + BaseTest.getStringfromBundleFile("exterior") + "']");
	private By lnkHome = By.xpath("//p[@class='quick-order-empty-cart']/a");
	private By tblPurchases = By.xpath("//tbody[@class='order-history-list']");
	private By hdrTable = By.xpath("//h1[text()='Tables']");
	private By lnkShop = By.xpath("//a[@class='header-menu-level1-anchor header-menu-shop-anchor']");

	public ShoppingCartPage shoppingCartPage;
	public CommonMethodsPage commonMethodsPage;
	private String item;

	public HomePage(RemoteWebDriver driver) throws Exception {
		super(driver);
		Logz.step(getHome().elementIsDisplayedAndEnabled()?"EOS home page is displayed":"Home Page is not displayed");
	}

	// Header Account Method- All Links
	private LinkText getAccountType(String option) throws Exception {
		return new LinkText(driver,By.xpath("//a[contains(text(),'" + option + "')]"), "Select Link");
	}
	private Button getYesButton()throws Exception {
		return new Button(driver,btnRemove,"Remove Yes Button");
	}
	public List<WebElement> getProductsFromCart() throws Exception {
		return getAccountOverview().getWebElements(lnkRemoveCart, "Products");
	}
	private Generic getCartItemsCount()throws Exception {
		return new Generic(driver,cartItemsCount,"CartItems Count");
	}
	private LinkText getHome()throws Exception {
		return new LinkText(driver,lnkHomePage,"Home Page");
	}
	private LinkText getHomePage()throws Exception {
		return new LinkText(driver,lnkHome,"Home");
	}

	private LinkText getCartIsEmptyText() throws Exception {
		return new LinkText(driver, txtCartIsEmpty, "Cart Is Empty");
	}
	private LinkText getPageTitle() throws Exception {
		return new LinkText(driver, By.xpath("//title[text()='"+ BaseTest.getStringfromBundleFile("homePageTitle")+"']"), "Home Page Title");
	}
	private LinkText getLinkHeaderLogo() throws Exception {
		return new LinkText(driver, lnkHeaderLogo, "Home Page Header Logo");
	}
	private Generic getProfileInfoHeader()throws Exception{
		return new Generic(driver,txtProfileInfoHeader,"Profile Information Header");
	}
	private Generic getAccountOverview() throws Exception {
		return new Generic(driver, accountOverview, "Header Profile Menu");
	}
	private LinkText getTextAccountBalance() throws Exception {
		return new LinkText(driver, txtAccountBalance, "Search BalanceTextMessage");
	}
	private CheckBox getCheckBoxEmail() throws Exception {
		return new CheckBox(driver, chkEmailPreference, "Search for the checkbox");
	}
	private LinkText gettextEmailPreference() throws Exception {
		return new LinkText(driver, txtEmailPreference, "Search for the text Email is updated");
	}
	private Button getButtonUpdate() throws Exception {
		return new Button(driver, btnUpdate, "Search for Update Button");
	}
	private Generic getCompanyNameLabel() throws Exception {
		return new Generic(driver, lblCompanyName, "Company Label");
	}
	private Generic getPhoneNumberLabel() throws Exception {
		return new Generic(driver, lblPhoneNumber, "Phone Number");
	}
	private Generic getEmailLabel() throws Exception {
		return new Generic(driver, lblEmail, "Email Label");
	}
	private CheckBox getCheckBoxSelectAll() throws Exception {
		return new CheckBox(driver, chkSelectAll, "Search for the select all checkbox");
	}
	private Button getButtonAddToCart() throws Exception {
		return new Button(driver, btnAddToCart, "Search Add To Cart Button");
	}
	private Button getButtonViewCart() throws Exception {
		return new Button(driver, btnViewCart, "Search Vie Cart Button");
	}
	private LinkText getPageNotFound() throws Exception {
		return new LinkText(driver, txtPageNotFound, "Search for Page Not Found");
	}
	private Button getProceedToCheckout() throws Exception {
		return new Button(driver, btnProceedToCheckout, "Search Proceed To Checkout Button");
	}
	private Button getEditButton(String addressName) throws Exception {
		return new Button(driver, By.xpath("//div[@class='address-details-info']//p[@class='address-details-container-multiselect-address-title']//b[contains(text(),'"+addressName+"')]/../../../following-sibling::p[@class='address-details-actions']//a[@class='address-details-edit-address']"), "Search for Edit Button");
	}
	private LinkText getEditName() throws Exception {
		return new LinkText(driver, editName, "Edit Name");
	}
	private Button getChildUpdatebutton() throws Exception {
		return new Button(driver, btnChildUpdate, "Search Child Window Update Button");
	}
	private LinkText getTextEditFullName() throws Exception {
		return new LinkText(driver, editFullName, "Search Edit Full Name");
	}
	private LinkText getTextNewFullName() throws Exception {
		return new LinkText(driver, newFullName, "Search Edit Full Name");
	}
	private Button getAddNewAddress() throws Exception {
		return new Button(driver, btnAddNewAddress, "button Add NewAddress");
	}
	private Button getSaveAddressButton() throws Exception {
		return new Button(driver, btnSaveAddress, "button save Address");
	}
	private Button getUpdateAddressButton() throws Exception {
		return new Button(driver, btnUpdateAddress, "button update Address");
	}
	private TextBox getFullNameText() throws Exception {
		return new TextBox(driver, txtFullName, "Full name Field");
	}
	private TextBox getAddressText() throws Exception {
		return new TextBox (driver, txtAddress, "textAddressField");
	}
	private TextBox getCityText() throws Exception {
		return new TextBox (driver, txtCity , "textCity Field");
	}
	private TextBox  getZipCodeText() throws Exception {
		return new TextBox (driver, txtZipCode , "ZipCode Field");
	}
	private TextBox getPhoneNumText() throws Exception {
		return new TextBox (driver, txtPhoneNum , "Phone Number Field");
	}
	private CheckBox getResidentialAddressChkbox() throws Exception{
		return new CheckBox(driver,chkResidentialAddress,"Residential Address Checkbox");
	}
	private CheckBox getDefaultShippingChkbox() throws Exception{
		return new CheckBox(driver,chkDefaultShipping,"Default Shipping Checkbox");
	}
	private SelectBox getState() throws Exception {
		return new SelectBox(driver,selectBoxState ,"SelectOrderType option PreAuth");
	}
	private TextBox getSearchFieldText() throws Exception {
		return new TextBox(driver, txtInputTextArea, "Input Text Field");
	}
	private List<WebElement> getSearchResultsTitles()throws Exception {
		return getAccountOverview().getWebElements(searchResultsTitles,"SearchResults Titles");
	}
	private List<WebElement> getLinkSearchresultsTitles()throws Exception {
		return getAccountOverview().getWebElements(lnkSearchresultsTitles,"SearchResults Title Links");
	}
	public Button getSelectOrdertypeDropdown() throws Exception {
		return new Button(driver, drpSelectOrdertype, "SelectOrderType drop down when no value is selected");
	}
	public Button getOrdertypeDropdown() throws Exception {
		return new Button(driver, drpOrdertype, "OrderType drop down with value Pre-Auth/313 selected");
	}
	private Generic getDropDownMenuForOrderType() throws Exception {
		return new Generic(driver, drpMenu, "Order Type");
	}
	public SelectBox getOrderTypeValue(String option) throws Exception {
		return new SelectBox(driver, By.xpath("//div[@class = 'btn-group open']//a[text() = '" + option + "']"), "OrderType Value");
	}
	private LinkText getRequestQuote() throws Exception {
		return new LinkText(driver, lnkRequestQuote, "RequestQuote link");
	}
	private LinkText getShoppingCart() throws Exception {
		return new LinkText(driver, lnkShoppingCart, "Cart");
	}
	private LinkText getViewCart() throws Exception {
		return new LinkText(driver, lnkViewCart, "View Cart");
	}
	private LinkText getQuickOrder() throws Exception {
		return new LinkText(driver, lnkQuickOrder, "QuickOrder link");
	}
	private Generic getSearchIcon() throws Exception {
		return new Generic(driver, iconSearch, "Search Icon");
	}
	public Generic getSwitchStore() throws Exception {
		return new Generic(driver, lnkSwitchStore, "SwithStore link");
	}
	private LinkText getLinkHeaderShop() throws Exception {
		return new LinkText(driver, lnkHeaderShop, "Shop Header link");
	}
	public Generic getHeaderProfileMenu() throws Exception {
		return new Generic(driver, accountOverview, "Header Profile Menu");
	}
	public LinkText getSelectHeaderProfileLink(String linkName) throws Exception {
		return new LinkText(driver, By.xpath("//a[contains(text(),'"+linkName+"')]"), "Header profile link to click" + linkName);
	}
	private TextBox getTxtFieldSearch() throws Exception {
		return new TextBox(driver, txtFieldSearch, "Search text field");
	}
	private Button getButtonGo() throws Exception {
		return new Button(driver, btnGo, "Search field, Go button");
	}
	private LinkText getHeadermenu(String menuName) throws Exception {
		return new LinkText(driver, By.xpath("//li/a[contains(text(),'" + menuName + "')]"), "Header Menu mousehoured" + menuName);
	}
	private LinkText getLinkActOverview() throws Exception {
		return new LinkText(driver, lnkActOverview, "Header Account Overview");
	}
	private Generic getSearchDropDown() throws Exception {
		return new Generic(driver, drpSearch, "Search DropDown");
	}
	private List<WebElement> getSearchList() throws Exception {
		return getSearchDropDown().getWebElements(searchList, "Search List");
	}
	private LinkText getShopByAreaLink() throws Exception {
		return new LinkText(driver, lnkShopByArea, "shopByArea");
	}
	private Button getRemoveBtn(String addressName) throws Exception {
		return new Button(driver, By.xpath("//div[@class='address-details-info']//p[@class='address-details-container-multiselect-address-title']//b[contains(text(),'"+addressName+"')]/../../../following-sibling::p[@class='address-details-actions']//button[@class='address-details-remove-address']"), "remove button");
	}
	private Generic getAddressName() throws Exception{
		return new Generic(driver,  txtAddressName, "Address Name");
	}
	private LinkText getSelectWishListLink(String linkName) throws Exception {
		return new LinkText(driver, By.xpath("//a[contains(text(),'"+linkName+"')]"), "Header profile link to click " + linkName);
	}
	public List<WebElement> getAddressLists()throws Exception{
		return getAddressName().getWebElements(By.xpath("//div[@class='address-details-info']/p/b"),"AddressLists");
	}
	private LinkText getShop() throws Exception {
		return new LinkText(driver, lnkShop, "Cart");
	}


	public ProductDescriptionPage clickOnSpecifiedProductFromSearchResults(String name)throws Exception {
		searchItemNameInSearchIcon(name);
		List<WebElement> prodTitles=getSearchResultsTitles();
		Assert.assertTrue(prodTitles.size()>0,"No Matched Search results found");
		for(int i=0;i<prodTitles.size();i++)
		{
			if(prodTitles.get(i).getText().equalsIgnoreCase(name)) {
				getLinkSearchresultsTitles().get(i).click();
				break;
			}
			if(i==prodTitles.size()) Logz.error("Specified Product is not displayed under search results");
		}
		return new ProductDescriptionPage(driver);
	}

	public ShopByAreaPage clickShopByArea() throws Exception {
		getShopByAreaLink().click();
		Logz.step("Clicked on Shop By Area");
		Common.waitForOperations(driver,lnkExteriorThumbnail);
		return new ShopByAreaPage(driver);
	}

	//This method will return the commonMethodsPage driver object
	public CommonMethodsPage gotoCommonMethodsPage() throws Exception {
		return new CommonMethodsPage(driver);
	}

	public RequestQuotePage clickRequestAQuote() throws Exception{
	    getRequestQuote().click();
	    return new RequestQuotePage(driver);
    }

	// Ordertype selection
	public HomePage selectOrderType(String orderType) throws Exception {
		try {
			commonMethodsPage=gotoCommonMethodsPage();

			if (Common.isElementPresent(driver,drpSelectOrdertype)) {
				getSelectOrdertypeDropdown().getControl().click();
				Logz.step("Clicked on Select Order Type");
			} else if (Common.isElementPresent(driver,drpOrdertype)) {
				getOrdertypeDropdown().getControl().click();
				Logz.step("Clicked on OrderType");
			}
			getOrderTypeValue(orderType).getControl().click();
			Logz.step("Selected orderType : " + orderType);
		} catch (Exception ex) {
			Logz.error("Did not find the Order type drop down to select the order type value");
			Logz.error(ex.getMessage());
			throw new Exception(ex);
		}
		return new HomePage(driver);
	}

	// To Verify Order type drop down displayed without any value
	public boolean isOrderTypeDropdownSelected() throws Exception {
		boolean flag = false;
		try {
			if (getOrdertypeDropdown().getControl().isDisplayed()) {
				flag = true;
			} else if (getSelectOrdertypeDropdown().getControl().isDisplayed()) {
				flag = false;
			}
		} catch (Exception e) {
			Logz.error(" Did not find the Order type dropdown");
		}
		return flag;
	}

	// Clicking on link present under header Profile menu
	public ProfileInformationPage clickHeaderMenuMyAccountLink(String linkName) throws Exception {
		getAccountOverview().getControl().click();
		Common.waitForLoadingCompletion(driver);
		Logz.step("Clicked on Header Profile Menu link");
		getSelectHeaderProfileLink(linkName).click();
		Logz.step("Header Profile Menu - Selected/clicked link name: " + linkName);
		return new ProfileInformationPage(driver);
		//Need to implement switch case for all pages inprogress
//		switch (linkName)
//		{
//			case "Reorder Items":return new ReorderItemsPage(driver);
//					break;
//			case "Profile Information":return new ProfileInformationPage(driver);
//					break;
//			case "Purchases History":return new PurchaseHistoryPage(driver);
//					break;
//			case "My list":return new MyListPage(driver);
//					break;
//
//			default:return new HomePage(driver);
//		}
	}

	// Clicking on link present under header Profile menu
	public MyListPage clickHeaderMenuMyAccountLinkAndMyList(String linkName) throws Exception {
		getHeaderProfileMenu().getControl().click();
		Logz.step("Clicked on Header Profile Menu link");
        Common.waitForLoadingCompletion(driver);
		getSelectHeaderProfileLink(linkName).getControl().click();
		Logz.step("Header Profile Menu - Selected/clicked link name: " + linkName);
//		switch (linkName)
//		{
//			case "Reorder Items":return new ReorderItemsPage(driver);
//					break;
//			case "Profile Information":return new ProfileInformationPage(driver);
//					break;
//			case "Purchases History":return new PurchaseHistoryPage(driver);
//					break;
//			case "My list":return new MyListPage(driver);
//					break;
//
//			default:return new HomePage(driver);
//
//		}
		return new MyListPage(driver);
	}

	public MyAccountPage clickAccountOverviewLink() throws Exception {
		getAccountOverview().getControl().click();
		Logz.step("Clicked on Header Profile Menu link");
		getLinkActOverview().click();
		Logz.step("Clicked on Account Overview");
		return new MyAccountPage(driver);
	}

	public TransactionHistoryPage clickHeaderMenuLinkAndTranHistory(String linkName) throws Exception {
		getHeaderProfileMenu().getControl().click();
		Logz.step("Clicked on Header Profile Menu link");
		getSelectHeaderProfileLink(linkName).getControl().click();
		Logz.step("Header Profile Menu - Selected/clicked link name: " + linkName);
		return new TransactionHistoryPage(driver);
	}

	public QuotePage clickQuotesHeaderMenu(String linkName) throws Exception {
		getHeaderProfileMenu().getControl().click();
		Logz.step("Clicked on Header Profile Menu link");
		getSelectHeaderProfileLink(linkName).getControl().click();
		Logz.step("Header Profile Menu - Selected/clicked link name: " + linkName);

		return new QuotePage(driver);
	}

//	public HomePage verifyMakeMyListIsEmpty()throws Exception
//	{
//		commonMethodsPage.makeMyListItemsToEmpty();
//		return new HomePage(driver);
//	}

	public ProductListPage clickShopHeaderMenu() throws Exception {
		getLinkHeaderShop().getControl().click();
		Logz.step("Clicked on Header Link Shop");
		return new ProductListPage(driver);
	}

	// Product Search
	public void enterProductName(String productName) throws Exception {
		getTxtFieldSearch().setText(productName);
		Logz.step("Entered Product name:" + productName);
	}

	public void verifyLanguageInCurrentUrl() throws Exception {
		Assert.assertTrue(getRequestQuote().getText().toString().equalsIgnoreCase(BaseTest.getStringfromBundleFile("linkRequestQuote")), "Language is not verified");
		Logz.step("Language is verified");
	}

	public void verifyDomainInCurrentUrl() throws Exception {
		Assert.assertTrue(driver.getCurrentUrl().contains("eos-qe.test.subway.com"));
		Logz.step("Domain is verified");
	}

	// Selecting/Clicking on Menu and Submenu Items
	public ProductListPage selectSubMenuItem(String menuName, String subMenuName) throws Exception {
		Actions actions = new Actions(driver);
		actions.moveToElement(getHeadermenu(menuName).getControl()).perform();
		actions.moveToElement(getHeadermenu(subMenuName).getControl()).click().perform();
		WebDriverWait wait = new WebDriverWait(driver,20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(hdrTable));
		return new ProductListPage(driver);
	}

	public void clickOnSearchIcon() throws Exception {
		getSearchIcon().getControl().click();
		Assert.assertTrue(getTxtFieldSearch().elementIsDisplayedAndEnabled(), "Search field is not displayed");
	}

	public void enterTextOnSearchField(String text) throws Exception {
		getTxtFieldSearch().setText(text);
	}

	public ProductListPage searchForSpecificItem(String itemName) throws Exception{
		clickOnSearchIcon();
		enterTextOnSearchField(itemName);
		getButtonGo().click();
		return new ProductListPage(driver);
	}

	public void verifySearchDropdownAfterEnterText(String text) throws Exception {
		clickSearchIcon();
		Assert.assertTrue(getSearchFieldText().elementIsDisplayedAndEnabled(), "Search field is not displayed");
		Logz.step("Search Field is displayed");
		Assert.assertTrue(getButtonGo().elementIsDisplayedAndEnabled(), "Go button is not displayed");
		Logz.step("Go button is displayed");
		getTxtFieldSearch().setText(text);
		Assert.assertTrue(getSearchDropDown().elementIsDisplayedAndEnabled(), "Search dropdown is not displayed");
		Logz.step("Search Dropdown is Displayed");
		verifySearchDropdown();
	}

	public SearchResultsPage searchItemNameInSearchIcon(String itemName) throws Exception {
		clickSearchIcon();

		Assert.assertTrue(getTxtFieldSearch().elementIsDisplayedAndEnabled(), "Search field is not displayed");
		Logz.step("Search Field is displayed");
		Assert.assertTrue(getButtonGo().elementIsDisplayedAndEnabled(), "Go button is not displayed");
		Logz.step("Go Button is displayed");
		getTxtFieldSearch().setText(itemName);
		clickGo();
		WebDriverWait wait = new WebDriverWait(driver,BaseTest.EXPLICIT_WAIT_TIME);
		//wait.until(ExpectedConditions.visibilityOfElementLocated(tblsearchResults));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("compare-item")));
		return new SearchResultsPage(driver);
	}

	public void clickSearchIcon() throws Exception{
		getSearchIcon().getControl().click();
		Logz.step("Clicked on Search Icon");
		Common.waitForElementToBeDisplayed(driver,txtFieldSearch);
		//return new HomePage(driver);
	}

	public SearchResultsPage clickGo() throws Exception{
		getButtonGo().getControl().click();
		Logz.step("Clicked on Go button");
		return new SearchResultsPage(driver);
	}
	public SearchResultsPage searchItemNumber() throws Exception {
		clickSearchIcon();
		Assert.assertTrue(getButtonGo().elementIsDisplayedAndEnabled(), "Go button is not displayed");
		getTxtFieldSearch().setText(BaseTest.getStringfromBundleFile("item_number"));
		getButtonGo().click();
		Logz.step("Clicked on GO button");
		return new SearchResultsPage(driver);
	}

	public ShoppingCartPage navigateToShoppingCartPage() throws Exception {
		//Common.waitForStale(driver, getCartItemsCount().getControl(),10);
		WebDriverWait wait = new WebDriverWait(driver,15);
		wait.until(Common.textNotEqual(driver,cartItemsCount,"0"));
		getShoppingCart().click();
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(lnkShoppingCart)));
		getViewCart().click();
		return new ShoppingCartPage(driver);
	}

	public ProductDescriptionPage clickSearchItem() throws Exception {
		item = getSearchList().get(0).getText();
		getSearchList().get(0).click();
		Logz.step("Clicked on Search Item");
		return new ProductDescriptionPage(driver);
	}

	public void verifySearchDropdown() throws Exception {
		List<WebElement> searchEles = getSearchList();
		for (int i = 0; i < searchEles.size(); i++) {
			WebElement we = searchEles.get(i);
			Assert.assertTrue((we.getText().toLowerCase()).contains(BaseTest.getStringfromBundleFile("searchBoxText").toLowerCase()), "Search Item is not Displayed");
			Logz.step(we.getText() + " is the search item");
		}
	}

	public void verifySearchItem() throws Exception {
		ProductDescriptionPage pdpPage = clickSearchItem();
		pdpPage.verifySearchItem(item);
	}

	// Account Overview
	public MyListPage selectAccountTypeWishList(String option) throws Exception {
		getAccountOverview().getControl().click();
		getSelectWishListLink(option).click();
		Logz.step("Selected account type" + option);
		return new MyListPage(driver);
	}
	
	public void selectAccountType(String option) throws Exception {
		getHeaderProfileMenu().getControl().click();
		Logz.step("Clicked on Header Profile Menu link");
		getSelectHeaderProfileLink(option).click();
		Logz.step("Header Profile Menu - Selected/clicked link name: " +option);
	}

	public PurchaseHistoryPage selectAccountTypeForPurchaseHistory(String option) throws Exception {
		getAccountOverview().getControl().click();
		getSelectHeaderProfileLink(option).getControl().click();
		Logz.step("Selected account type" + option);
		Common.waitForOperations(driver,tblPurchases);
		return new PurchaseHistoryPage(driver);
	}

	public QuotePage selectAccountQuoteType(String option) throws Exception {
		getAccountOverview().getControl().click();
		getAccountType(option).getControl().click();
		Logz.step("Selected account type" + option);
		return new QuotePage(driver);
	}

	public void verifyAccountBalance() throws Exception {
		String Balance = getTextAccountBalance().getText();
		if (Balance.contains("Outstanding Balance")) {
			Logz.step("Account Balance is Viewed");
		} else {
			Logz.step("Account Balance is not displayed");
		}
	}

	public void emailPreference() throws Exception {
		getCheckBoxEmail().getControl().click();
		if (getCheckBoxEmail().isChecked()) {
			getButtonUpdate().getControl().click();
		} else {
			Logz.step("Checkbox is not Selected");
		}
		String text = gettextEmailPreference().getText();
		if (text.contains("Email Preferences successfully saved!")) {
			Logz.step("Email is successfully updated");
		} else {
			Logz.step("Email is not updated");
		}
	}

	public void verifyProfileInformation() throws Exception {
		Assert.assertEquals(getProfileInfoHeader().getControl().getText().trim(),BaseTest.getStringfromBundleFile("ProfileInformation").trim());
		Assert.assertTrue(getCompanyNameLabel().elementIsDisplayedAndEnabled() && getPhoneNumberLabel().elementIsDisplayedAndEnabled() && getEmailLabel().elementIsDisplayedAndEnabled(),"Profile Information is not displayed");
		Logz.step("Profile Information Page is displayed");
	}

	public void editAddressBook(String Name) throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(txtFullName));
		getFullNameText().setText(Name);
		Logz.step("Full Name is edited");
		getUpdateAddressButton().click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(btnCancelAddress));
		Logz.step("Clicked on save button");
	}

	public void verifyEditAndRemoveAddress(String Name)throws Exception {
		getEditButton(Name).click();
		Logz.step("Address Book page is opened");
		String fullName = getFullNameText().getAttribute("value");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		fullName = fullName+"_"+timestamp;
		editAddressBook(fullName);
		removeAddedAddress(fullName);
	}

	public void addAddressBook(AddressBook addressBook) throws Exception {
		getAddNewAddress().click();
		Logz.step("new Address page is opened to add new address");
		Common.waitForOperations(driver,txtFullName);
		getFullNameText().setText(addressBook.getFullName());
        getAddressText().setText(addressBook.getAddress());
        getCityText().setText(addressBook.getCity());
        getState().selectOptionFromDropDown(addressBook.getState());
        getZipCodeText().setText(addressBook.getZipCode());
        getPhoneNumText().setText(addressBook.getPhoneNum());
		getResidentialAddressChkbox().check();
		//getDefaultShippingChkbox().check();
		getSaveAddressButton().click();
		Common.waitForLoadingCompletion(driver);
		WebDriverWait wait = new WebDriverWait(driver, 2);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(btnCancelAddress));
	}

	public void clickOnRemove(String addressName) throws Exception {
		getRemoveBtn(addressName).click();
		Logz.step("Clicked on remove button to remove address");
	}

	public void removeAddedAddress(String addressName)throws  Exception {
		for(WebElement we : getAddressLists()) {
			if((we.getText()).equals(addressName)) {
				 clickOnRemove(addressName);
				Logz.step("Clicked on remove button");
				 getYesButton().click();
				Logz.step("Clicked on Yes button to remove address");
				 break;
			 }
		}
	}

	public ReorderItemsPage clickReorderItems(String linkName) throws Exception {
		getHeaderProfileMenu().getControl().click();
		Logz.step("Clicked on Header Profile Menu link");
		getSelectHeaderProfileLink(linkName).getControl().click();
		Logz.step("Header Profile Menu - Selected/clicked link name: " + linkName);
		return new ReorderItemsPage(driver);
	}

	public HomePage removeAllItemsFromCart() throws  Exception {
        //Get Size of the Cart
Logz.step("Cart Size::" + getCartItemsCount().getControl().getText());
		int cartItemssize = Integer.parseInt(getCartItemsCount().getControl().getText().trim());

		if(cartItemssize>0)
		{
			getShoppingCart().click();
			Logz.step("Clearing Cart Before Adding Items to Cart");
			getViewCart().click();
			Logz.step("View Button is clicked");
            getProductsFromCart().forEach(WebElement::click);
            //Wait Till Page is Loaded
            Common.waitForLoadingCompletion(driver);
			(new WebDriverWait(driver, BaseTest.EXPLICIT_WAIT_TIME)).until(ExpectedConditions.visibilityOfElementLocated(lnkHome));
            cartItemssize = Integer.parseInt(getCartItemsCount().getControl().getText().trim());
            //Confirming Cart is cleaned up
            Assert.assertTrue(cartItemssize == 0, "Cart is Not Cleaned Up");
            Logz.step("All items are removed from cart");
            //Click on Home Page Link Once Cart is Cleared
            clickHomePageLink();
		}

		return new HomePage(driver);
	}

	private void clickHomePageLink() throws Exception{
		getHomePage().click();
		Logz.step("Clicked on Homepage Link");
	}

	public void verifyLoggedWithSingleRestOwner() throws  Exception {
		String value = driver.getTitle();
		String bundleFile = BaseTest.getStringfromBundleFile("homePageTitle");
		Logz.step("Expected value: " + bundleFile);
		Logz.step("value printed as: " + value);
		Assert.assertTrue(value.equalsIgnoreCase(BaseTest.getStringfromBundleFile("homePageTitle")),
				 "User is not able to successfully log-in to Subway EOS system and confirm that prompt saying 'Select Your Restaurant' appears");
		Logz.step(" User is able to successfully log-in to Subway EOS system and confirm that no prompt saying 'Select Your Restaurant' appears");
	}

	public void verifyOrderTypeDropDown() throws Exception {
		Assert.assertTrue(getDropDownMenuForOrderType().elementIsDisplayedAndEnabled(),"Select Order Type drop down is available");
		Logz.step("Select Order Type drop down is not available");
	}

	public ProductListPage navigateProductListPage() throws Exception{
		/*driver.navigate().to(BaseTest.getStringfromBundleFile("shopMenu"));*/
		getShop().click();
		Logz.step("Navigated to search page");
		return new ProductListPage(driver);
	}

	/*public void navigateToShoppingCartPage() throws Exception {
		WebDriverWait wait = new WebDriverWait(driver,15)
		wait.until(Common.textNotEqual(getCartItemsCount().getControl(),"0"));
		getShpCart().click();
		WebDriverWait wait = new WebDriverWait(driver,5);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(lnkCartView)));
		getCartView().click();
		return new ShoppingCartPage(driver);
	}*/

}
