package pages.MyAccountOverview;

import base.gui.controls.browser.Button;
import base.gui.controls.browser.Generic;
import base.gui.controls.browser.LinkText;
import base.gui.controls.browser.SelectBox;
import base.gui.controls.browser.TextBox;
import base.pages.browser.BrowserBasePage;
import base.test.BaseTest;
import edu.emory.mathcs.backport.java.util.Arrays;
import org.apache.velocity.util.ArrayListWrapper;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.Cart.ShoppingCartPage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import pages.CommonMethodsPage;
import pages.HomePage;
import util.Common;
import utils.Logz;

public class PurchaseHistoryPage<T extends RemoteWebDriver> extends BrowserBasePage {

	public RemoteWebDriver remoteWebDriver;
	public CommonMethodsPage commonMethodsPage;
	// links
	private By 	lnkOpen = By.xpath("//a[@class='order-history-list-header-button-open']");
	private By lnkAll = By.xpath("//a[@class='order-history-list-header-button-all']");
	// input
	private By inputFromDate = By.xpath("//input[@id='from']");
	private By inputToDate = By.xpath("//input[@id='to']");
	// button
	private By btnToggleSort = By.xpath("//button[@data-action='toggle-sort'");
	// selectItem
	private static By drpSelectSortType = By.xpath("//select[@name='sort']");
	// Tables
	private By tblPurchases = By.xpath("//tbody[@class='order-history-list']");
	// Tables_s
	private By tblPurchaseLink = By.xpath("(//td[@class='recordviews-actionable-title']/a)");
	private By lnkReorder = By.xpath("//a[@class='order-history-item-actions-reorder']");
	private By lnkReorderAllItems = By.xpath("//div[@class='order-history-summary-row-fluid']/a[contains(text(),'"+BaseTest.getStringfromBundleFile("linkReorderAllItems")+"')]");
	private By lnkDownArrow = By.xpath("//a[@class='order-history-details-accordion-head-toggle-secondary collapsed' and contains(text(),'"+BaseTest.getStringfromBundleFile("ProductLink")+"')]/i");
	private By txtReorderItem = By.xpath("(//span[@class='product-line-sku-value'])[1]");
	private By btnViewCart = By.xpath("//div/a[contains(text(),' " + BaseTest.getStringfromBundleFile("buttonViewCart") + " ')]");
	private By btnAddToCart = By.xpath("//button[@class='reorder-items-actions-add-to-cart']");
	private By txtALLReorderItem = By.xpath("//span[@class='product-line-sku-value']");
	private static By purchaseHistoryDate = By.xpath("//div[@id='content']//table/tbody/tr/td[@class='recordviews-actionable-date']");
	private By txtPurchaseNo = By.xpath("//td[@class='recordviews-actionable-title']/a/span");
	private By txtAmount = By.xpath("//td[@class='recordviews-actionable-value']/span[2]");
	private By onlinePurchaseNumber = By.xpath("//span[@class='order-history-details-order-number']");
	private By purchaseHistoryHeader = By.xpath("//header[@class='order-history-list-header']");
	private By purchaseHistoryList = By.xpath("//div[@class='order-history-list-recordviews-container']");
	private By labelMyAccount = By.xpath("//h2[@class='myaccount-layout-title']");
	private By addToCart = By.xpath("//button[contains(text(),'" + BaseTest.getStringfromBundleFile("addToCart") + "')]");
	private By lnkSpgCart = By.xpath("//div[@class='header-menu-cart-dropdown']/div/a");
	private By lnkView = By.xpath("//div[@class='header-mini-cart-buttons-left']/a");
	private By tblPurchaseHistory=By.xpath("//table[@class='order-history-list-recordviews-actionable-table']");

	public PurchaseHistoryPage(RemoteWebDriver driver) throws Exception {
		super(driver);
		Logz.step(getSelectSortType().elementIsDisplayedAndEnabled()?"Purchase History page is loaded":"Purchase History is not loaded");
	}

	public CommonMethodsPage gotoCommonMethodsPage() throws Exception {
		return new CommonMethodsPage(driver);
	}

	private Generic getPurchaseHistoryList() throws Exception {
		return new Generic(driver, purchaseHistoryList, "Purchase History List");
	}
	private Generic getPurchaseHistoryHeader() throws Exception {
		return new Generic(driver, purchaseHistoryHeader, "Purchase History Header");
	}
	private Generic getOnlinePurchaseNumber() throws Exception {
		return new Generic(driver, onlinePurchaseNumber, "Online Purchase Number");
	}
	private Generic getLinkOpen() throws Exception {
		return new Generic(driver, lnkOpen, "Open");
	}
	private Generic getTablePurchaseHistory() throws Exception {
		return new Generic(driver, tblPurchaseHistory, "Purchase History Table");
	}
	private Generic getLinkAll() throws Exception {
		return new Generic(driver, lnkAll, "All");
	}
	private LinkText getLinkDownArrow() throws Exception{
		return new LinkText(driver,lnkDownArrow,"Down Arrow");
	}
	private TextBox getInputFromDate() throws Exception {
		return new TextBox(driver, inputFromDate, "From Date");
	}
	private TextBox getInputToDate() throws Exception {
		return new TextBox(driver, inputToDate, "To Date");
	}
	private Button getButtonToggleSort() throws Exception {
		return new Button(driver, btnToggleSort, "ToggleSort");
	}
	private SelectBox getSelectSortType() throws Exception {
		return new SelectBox(driver, drpSelectSortType, "SelectSort");
	}
	private Generic getTablePurchase() throws Exception {
		return new Generic(driver, tblPurchases, "Table Puchases");
	}
	private Generic getMyAccountLabel() throws Exception{
		return new Generic(driver,labelMyAccount,"My Account Label");
	}
	private List<WebElement> getTablePurchaseLink() throws Exception {
		return getLinkOpen().getWebElements(tblPurchaseLink, "Table Puchases");
	}
	private Generic getTextReorderItem() throws Exception {
		return new Generic(driver, txtReorderItem, "Reorder Item Text Message");
	}
	private Button getButtonViewCart() throws Exception {
		return new Button(driver, btnViewCart, "Search View Cart Button");
	}
	private Generic getButtonAddToCart() throws Exception {
		return new Generic(driver, btnAddToCart, "Search Add To  Cart Button");
	}
	private List<WebElement> getLinkReorder() throws Exception {
		return getMyAccountLabel().getWebElements(lnkReorder, "Link Reorder");
	}
	private Generic getLinkReorderAll() throws Exception {
		return new Generic(driver,lnkReorderAllItems , "Link Reorder");
	}
	private Generic getAddToCart() throws Exception {
		return new Generic(driver, addToCart, "Add To Cart Items");
	}
	private List<WebElement> getButtonAddToCartList() throws Exception {
		return getLinkOpen().getWebElements(btnAddToCart, "Search Add To Cart List");
	}
	public List<WebElement> getDates() throws Exception {
		return getLinkOpen().getWebElements(purchaseHistoryDate, "Dates");
	}
	public List<WebElement> getPurchaseNumbers() throws Exception {
		return getTablePurchaseHistory().getWebElements(txtPurchaseNo, "Purchase Numbers");
	}
	public List<WebElement> getPurchaseAmount() throws Exception {
		return getTablePurchaseHistory().getWebElements(txtAmount, "Amount");
	}
	private Generic getAllTextReorderItem() throws Exception {
		return new Generic(driver, txtALLReorderItem, "Search Add To  Cart Button");
	}
	private List<WebElement> getAllAddToCartTextList() throws Exception {
		return getAllTextReorderItem().getWebElements(txtALLReorderItem, "Search Add To Cart Text");
	}
	private LinkText getCart() throws Exception {
		return new LinkText(driver, lnkSpgCart, "Cart");
	}
	private LinkText getView() throws Exception {
		return new LinkText(driver, lnkView, "View Cart");
	}
	public void clickOpenLink() throws Exception {
		getLinkOpen().getControl().click();
		Logz.step("Clicked on Open ");
	}

	public void clickAllLink() throws Exception {
		getLinkAll().getControl().click();
		Logz.step("clicked on ALL link");
	}

	public PurchaseHistoryPage setFromInputDate(String fromDate) throws Exception {
		getInputFromDate().setText(fromDate);
		Logz.step("Entered from Date value");
		return new PurchaseHistoryPage(driver);
	}

	public PurchaseHistoryPage setToInputDate(String toDate) throws Exception {
		getInputToDate().setText(toDate);
		Logz.step("Entered To Date value");
		return new PurchaseHistoryPage(driver);
	}

	public PurchaseHistoryPage clickToggleSort() throws Exception {
		getButtonToggleSort().click();
		Logz.step("clicked on Toggle Sort");
		return new PurchaseHistoryPage(driver);
	}

	private void selectSortType(String sortValue) throws Exception {
		SelectBox selectSortType = getSelectSortType();
		WebElement elmSort = selectSortType.getControl();
		selectSortType.selectFromDropDown(sortValue);
		if(!sortValue.equals("Sort By Date")) {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.stalenessOf(elmSort));
		}
		//Thread.sleep(10000);

		// need to complete select require value from dropdown
	}

	private void clickPurchaseLink() throws Exception{
		getTablePurchaseLink().get(0).click();
		Logz.step("Clicked on the purchase order");
	}

	private void clickonReorder() throws Exception{
		getLinkReorder().get(0).click();
		Logz.step("Clicked on ReOrder link");
	}

	public void reorderSingleItem() throws Exception {
		clickPurchaseLink();
		clickonReorder();
	}

	public void  reorderAllItems() throws Exception {
		clickPurchaseLink();
		Generic lnkReorderAll = getLinkReorderAll();
		List<WebElement> list = lnkReorderAll.getWebElements(lnkReorder, "Link Reorder All");
		int beforeReordering = list.size();
		Logz.step("Number of items before reorder: " + beforeReordering);
		lnkReorderAll.getControl().click();
		List<WebElement> list1 = getAddToCart().getWebElements(addToCart, "Add To Cart Items");
		int afterReorderAllItems = list1.size();
		Logz.step("Number of items added to cart: " + afterReorderAllItems);
		list1.forEach(WebElement::click);
		Assert.assertEquals(beforeReordering, afterReorderAllItems);
	}

	public void verifySortByPurchaseNo(String sortType) throws Exception {
		List<WebElement> listProductDate = driver.findElements(txtPurchaseNo);
		if (sortType.equalsIgnoreCase("Sort By Amount"))
			for (int i = 0; i < listProductDate.size(); i++) {
				for (int j = i + 1; j > i; j--) {
					if (Integer.parseInt(listProductDate.get(i).getText()) <= Integer
							.parseInt(listProductDate.get(j).getText())) {
						Logz.step("Products are sorted by PurchaseNo, low to high");
					} else {
						Logz.step("Products are not sorted based on purchase number");
					}
				}
			}
	}

	public void verifySortByAmount(String sortType) throws Exception {

		List<WebElement> listProductDate = driver.findElements(txtAmount);
		if (sortType.equalsIgnoreCase("Sort By Amount"))
			for (int i = 0; i < listProductDate.size(); i++) {
				for (int j = i + 1; j > i; j--) {
					String[] s = listProductDate.get(i).getText().split("\\$");
					String[] t = listProductDate.get(j).getText().split("\\$");
					if (Double.parseDouble(s[1]) <= Double.parseDouble(t[1])) {
						Logz.step("Products are sorted by Amount");
					} else {
						Logz.step("Products are not sorted based on the amount");
					}
				}
			}

	}

	public void verifyOnlinePurchaseNumber() throws Exception {
		String number = getOnlinePurchaseNumber().getControl().getText();
		Logz.step("Online Purchase number is:" + number);
	}

	private List<String> readValuesToArray(String value, String splitValue) throws Exception {
		List<String> list = new ArrayList<>();
		List<WebElement> elements;
		if (value.equalsIgnoreCase("Date")) {
			elements = getDates();
		} else if (value.equalsIgnoreCase("Number")) {
			elements = getPurchaseNumbers();
		} else {
			elements = getPurchaseAmount();
		}
		for (WebElement el : elements) {
			if (splitValue == "$") {
				String[] s = el.getText().split("\\$");
				list.add(s[1]);
			} else {
				list.add(el.getText().toString());
			}
		}
		return list;
	}

	public PurchaseHistoryPage verifySortedValues(String sortValue) throws Exception {
		commonMethodsPage = gotoCommonMethodsPage();
		if (sortValue.equalsIgnoreCase(BaseTest.getStringfromBundleFile("sortByDate"))) {
			selectSortType(sortValue);
			List<String> ar = readValuesToArray("Date", "");
			String[] s = (String[]) ar.toArray(new String[ar.size()]);
			List<Date> results = commonMethodsPage.sortDates("DESC",s);
			Assert.assertEquals(ar, results,"dates are not sorted as expected");

		} else if (sortValue.equalsIgnoreCase(BaseTest.getStringfromBundleFile("sortByNumber"))) { {
				selectSortType(sortValue);
				List<String> ar = readValuesToArray("Number", "");
				String[] s = (String[]) ar.toArray(new String[ar.size()]);
				List<String> results = Arrays.asList(commonMethodsPage.sortedResults(s,"DESC"));
				Assert.assertEquals(ar, results, "purchase number is not sorted properly in descending order" + ar);
			}
		} else if (sortValue.equalsIgnoreCase(BaseTest.getStringfromBundleFile("sortByAmount"))) {
			selectSortType(sortValue);
			List<String> ar = readValuesToArray("Amount", "$");
			String[] s = (String[]) ar.toArray(new String[ar.size()]);
			List<String> results = Arrays.asList(commonMethodsPage.sortedResults(s,"DESC"));
			Assert.assertEquals(ar,results,"amount is not sorted properly in descending order" + ar);
		}
		return new PurchaseHistoryPage(driver);
	}

	public void verifyPurchaseList() throws Exception {
		Assert.assertTrue(getPurchaseHistoryList().elementIsDisplayedAndEnabled(), "Purchase History is not displayed");
		Logz.step("Purchase History is displayed");
	}

	private void clickOnCart() throws Exception{
		getCart().click();
		Logz.step("Clicked on Cart");
	}
	public ShoppingCartPage navigateToShoppingCartPage() throws Exception {
		clickOnCart();
		WebDriverWait wait = new WebDriverWait(driver,5);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(lnkView)));
		getView().click();
		return new ShoppingCartPage(driver);
	}
}
