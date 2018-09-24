package pages.Cart;


import base.gui.controls.browser.Generic;
import base.gui.controls.browser.LinkText;
import base.gui.controls.browser.SelectBox;
import base.pages.browser.BrowserBasePage;
import base.test.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import pages.CommonMethodsPage;
import pages.MyAccountOverview.MyListPage;
import util.Common;
import utils.Logz;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ProductListPage<T extends RemoteWebDriver> extends BrowserBasePage {
	public static String itemName;
	private By iconDisplayGrid = By.xpath("//*[@class='icon-display-grid']");
	private By lnkProduct = By.xpath("//div[@class='facets-item-cell-grid']");
	private By drpSelectProducts = By.xpath("//div[@class='facets-facet-browse-list-header-filter-column']");
	private static By drpPagination = By.xpath("//select [@class='facets-item-list-show-selector']");
	private By drpPrice = By.xpath("//select [@class='facets-item-list-sort-selector']");
	//private By lnkVendor = By.xpath("//h4[@class='facets-faceted-navigation-item-facet-group-title' and contains(text(),'"+BaseTest.getStringfromBundleFile("vendorName")+"')]");
	private By lnkVendor = By.xpath("//a[@title='Vendor']");
	private By lnkListView = By.xpath("//a[@title='List']/i");
	private By lnkTableView = By.xpath("//a[@title='Table']/i");
	private By lnkGridView = By.xpath("//a[@title='Grid']/i");
	private By lnkFeature = By.xpath("//a[@title='feature']/i");
	private By featureList = By.xpath("//div[@class='facets-faceted-navigation-item-facet-group-wrapper collapse in']/div/ul");
	private By facetList = By.xpath("//li/a");
	private By lnkTextTitle = By.xpath("//div[@class='facets-browse-category-heading-main-description']/h1");
	private By searchTitle = By.xpath("//div[@class='facets-facet-browse-narrowedby']/a");
	private By lnkProducts = By.xpath("//a[@class='facets-item-cell-grid-title']/span");
	private By lnkPageLayoutIsTableView = By.xpath("//div[@class='facets-item-cell-table']");
	private By lnkPageLayoutIsGridView = By.xpath("//div[@class='facets-item-cell-grid']");
	private By lnkPageLayoutIsListView = By.xpath("//div[@class='facets-item-cell-list']");
	private By lnkProductPrice = By.xpath("//span[@class='product-views-price-lead']");
	private By chkAddToCompare = By.xpath("//*[@id='compare']");
	private By addToCompareErrorMsg = By.xpath("//*[@class='global-views-modal-content']/div[@id='modal-body']/p");
	private By lnkGoToCompare = By.xpath("//a[text() = 'Go to Compare']");
	private By dataQtyHeader = By.xpath("//*[@class = 'facets-facet-browse-title']");
	private By noOfProducts = By.xpath("//*[@class = 'facets-item-cell-grid-title']/span");
	private By btnSliderRight = By.xpath("//button[@class='facets-faceted-navigation-item-range-slider-bar-left']");
	private By sliderPrice = By.xpath("//div[@class='facets-faceted-navigation-item-range-slider']");
	private By depthIn = By.xpath("//div[@data-facet-id='" + BaseTest.getStringfromBundleFile("depthIn") + "']//following-sibling::li/a");
	private By heightIn = By.xpath("//div[@data-facet-id='" + BaseTest.getStringfromBundleFile("heightIn") + "']//following-sibling::li/a");
	private By lnkFilteredProductTitles=By.xpath("//a[@class='facets-item-cell-grid-title']");
	private By clearAllIcon=By.xpath("//i[@class='facets-faceted-navigation-facets-clear-icon']");
	private By accountOverview = By.xpath("//i[@class='header-profile-welcome-carret-icon']");
    private By OptionSelected =By.xpath("//select[@class='facets-item-list-show-selector']");
    private By priceLevel=By.xpath("//div[@data-facet-id='" + BaseTest.getStringfromBundleFile("price")+"']");
	private By imgLoadingIndicator = By.id("loadingIndicator");
    private By lblItemNM = By.xpath("//a[@class='facets-item-cell-grid-title']/span[text()='"+itemName+"']/parent::a//following-sibling::div//span[@class='product-views-price-lead']");
    String sLblItem = "//a[@class='facets-item-cell-grid-title']/span[text()='**']/parent::a//following-sibling::div//span[@class='product-views-price-lead']";

    public ProductDescriptionPage productDescriptionPage;
	public CommonMethodsPage commonMethodsPage;

	public ProductListPage(RemoteWebDriver driver) throws Exception {
		super(driver);
		Logz.step(getPaginationDropdown().elementIsDisplayedAndEnabled()?"This is Product List Page":"Product List Page is not displayed");
		Common.waitForLoadingCompletion(driver);
	}
	
	//This method will return the commonMethodsPage driver object
	public CommonMethodsPage gotoCommonMethodsPage() throws Exception {
		return new CommonMethodsPage(driver);
	}

	private Generic getSliderPrice() throws Exception {
		return new Generic(driver, sliderPrice, "Price");
	}
	private Generic getPageTitle() throws Exception {
		return new Generic(driver, lnkTextTitle, "Title");
	}
	private Generic getChkBoxlist() throws Exception {
		return new Generic(driver, chkAddToCompare, "Check Boox");
	}
	public List<WebElement> getListAddToCompare() throws Exception {
		return getChkBoxlist().getWebElements(chkAddToCompare, "Add To Compare");
	}
	private List<WebElement> getFilteredProductTitles()throws Exception {
		return getPageTitle().getWebElements(lnkFilteredProductTitles,"Filtered Results Product Titles");
	}
	public List<WebElement> getProducts() throws Exception {
		return getDisplayGridIcon().getWebElements(lnkProducts, "Products");
	}
	private Generic getHeaderText(String name) throws Exception {
		return new Generic(driver, By.xpath("//div[@data-facet-id='" + name + "']/a/h4"), "Header text");
	}

    /*private Generic getProductItem() throws Exception {
        return new Generic(driver, productItem, "Header text");
    }*/

	public Generic getSpecificItemPrice(String itemName) throws Exception {
		return new Generic(driver, By.xpath("//a[@class='facets-item-cell-grid-title']/span[text()='"+itemName+"']/parent::a//following-sibling::div//span[@class='product-views-price-lead']"), "Header text");
	}

	private Generic getHeaderTextForPrice() throws Exception {
		return new Generic(driver, priceLevel, "Header text");
	}

	private Generic getFacetList() throws Exception {
		return new Generic(driver, facetList, "Facet List");
	}
	private Generic getHeaderExpandCollapseIcon(String name) throws Exception {
		return new Generic(driver, By.xpath("//div[@data-facet-id='" + name + "']/a/i"), "Header expand icon");
	}
	private Generic getHeaderExpandCollapseStatus(String name) throws Exception {
		return new Generic(driver, By.xpath("//div[@data-facet-id='" + name + "']/a"), "ExpandCollapseIconStatus");
	}
	private Generic getSpecificFilterOptions(String name) throws Exception {
		return new Generic(driver, By.xpath("//div[@data-facet-id='" + name + "']/a"), "Specific Filtered Options");
	}
	private Generic getclearAllIcon()throws Exception {
		return new Generic(driver,clearAllIcon,"Clear All icon");
	}
	private List<WebElement> getFilterOptions(String name)throws Exception {
		return getPageTitle().getWebElements(By.xpath("//div[@data-facet-id='" + name + "']//following-sibling::li"),"FilterOptions");
	}
	private SelectBox getSortSelectorDropDown() throws Exception {
		return new SelectBox(driver, drpPrice, "SortSelector");
	}
	public LinkText getGoToCompareLink() throws Exception {
		return new LinkText(driver, lnkGoToCompare, "GoToCompare Link");
	}
	public List<WebElement> getProductList() throws Exception {
		return getPageTitle().getWebElements(lnkProductPrice, "Products");
	}

	// private LinkText getTableName(String name)throws Exception
	// {return new LinkText(driver,By.xpath("//span[text()='"+name+"']"),"TableName");
	// }

	private SelectBox getPaginationDropdown() throws Exception {
		return new SelectBox(driver, drpPagination, "Pagination Dropdown");
	}
	private SelectBox getPriceDropdown() throws Exception {
		return new SelectBox(driver, drpPrice, "Price Dropdown");
	}
	private LinkText getVendorLink() throws Exception {
		return new LinkText(driver, lnkVendor, "VendorLink");
	}
	private LinkText getVendorOptions(String name) throws Exception {
		return new LinkText(driver, By.xpath("//ul[@class='facets-faceted-navigation-item-facet-optionlist']//li//a[@title='"+name+"']"), "Vendor Options");
	}
	private LinkText getListView() throws Exception {
		return new LinkText(driver, lnkListView, "List View");
	}
	private Generic getHeaderExpandCollapseStatusFacet() throws Exception {
		return new Generic(driver, By.xpath("//div[@data-facet-id='restaurant-area']/a"), "ExpandCollapseIconStatus");
	}
	private LinkText getTableView() throws Exception {
		return new LinkText(driver, lnkTableView, "Table View");
	}
	private LinkText getGridView() throws Exception {
		return new LinkText(driver, lnkGridView, "Grid view");
	}
	private LinkText getPageLayoutIsTableView() throws Exception {
		return new LinkText(driver, lnkPageLayoutIsTableView, "Page layout is Table View");
	}
	private LinkText getPageLayoutIsGridView() throws Exception {
		return new LinkText(driver, lnkPageLayoutIsGridView, "Page Layout is Grid view");
	}
	private LinkText getPageLayoutIsListView() throws Exception {
		return new LinkText(driver, lnkPageLayoutIsListView, "Page Layout is List view");
	}
	private Generic getSelectProductsDropDown() throws Exception {
		return new Generic(driver, drpSelectProducts, "Products DropDown");
	}
	private SelectBox getProductsOption(String option) throws Exception {
		return new SelectBox(driver, By.xpath("//select[@class = 'facets-item-list-show-selector']//option[text() = '" + option + "']"), "Select Products");
	}
	private SelectBox getProductOption() throws Exception {
		return new SelectBox(driver,OptionSelected,"Option selected");
	}
	private LinkText getQuickOrder() throws Exception {
		return new LinkText(driver, lnkProduct, "Product link");
	}
	private Generic getDisplayGridIcon() throws Exception {
		return new Generic(driver, iconDisplayGrid, "Display Grid Icon on Shop page");
	}
	private LinkText getItemName(String name) throws Exception {
		return new LinkText(driver, By.xpath("//a[@class='facets-item-cell-grid-title']/span[contains(text(),'"+name+"')]"), "Item Name");
	}
/*	public List<WebElement> getItemNamesList(String name) throws Exception {
		return getDataQtyHeader().getWebElements(By.xpath("//a[@class='facets-item-cell-grid-title']/span[contains(text(),'"+name+"')]"), "No Of Items");
	}*/
	private Generic getAddToCompareErrorMsg() throws Exception {
		return new Generic(driver, addToCompareErrorMsg, "Add To Compare Error Message");
	}
	private LinkText getLinkFeature() throws Exception {
		return new LinkText(driver, lnkFeature, "linkFeature");
	}
	private Generic getDataQtyHeader() throws Exception {
		return new Generic(driver, dataQtyHeader, "Product Quantity Header");
	}
	public Generic getFeatureList() throws Exception {
		return new Generic(driver, featureList, "feature List");
	}
	public Generic getProductCountOnPLP() throws Exception {
		return new Generic(driver, noOfProducts, "No Of Products on Product Listing Page ");
	}
	public Generic getSearchTitle() throws Exception {
		return new Generic(driver, searchTitle, "search Title ");
	}
	private Generic getTestItemList() throws Exception {
		return new Generic(driver, lnkProduct, "Add To Compare Error Message");
	}
	private Generic getLinkProductPrice() throws Exception {
		return new Generic(driver,lnkProductPrice , "Product Prices");
	}
	private Generic getbuttonSliderRight() throws Exception {
		return new Generic(driver,btnSliderRight , "Product Prices");
	}
	public List<WebElement> getProductListItems() throws Exception {
		return getDataQtyHeader().getWebElements(noOfProducts, "No Of Products");
	}
	private Generic getbuttonSliderRight(String option) throws Exception {
		return new Generic(driver,By.xpath("//div[@data-facet-id='"+option+"']//button[@class='facets-faceted-navigation-item-range-slider-bar-left']") , "Product Prices");
	}
	private LinkText getTableName(String name) throws Exception {
		return new LinkText(driver, By.xpath("//span[text()='" + name + "']"), "TableName");
	}
	private Generic getHeaderMenu() throws Exception {
		return new Generic(driver, accountOverview, "Header Profile Menu");
	}
	private LinkText getSelectHeaderLink(String linkName) throws Exception {
		return new LinkText(driver, By.xpath("//a[contains(text(),'"+linkName+"')]"), "Header profile link to click" + linkName);
	}
	public Generic getHeaderProfileMenu() throws Exception {
		return new Generic(driver, accountOverview, "Header Profile Menu");
	}
	public LinkText getSelectHeaderProfileLink(String linkName) throws Exception {
		return new LinkText(driver, By.xpath("//a[contains(text(),'"+linkName+"')]"), "Header profile link to click" + linkName);
	}
	public LinkText getVendorMenu() throws Exception {
		return new LinkText(driver,lnkVendor, "Vendor Link");
	}
//	public List<WebElement> getItemsPrice() throws Exception {
//		return getDataQtyHeader().getWebElements(itemPrice, "No Of Price tags");
//	}

	public void verifyProdutsDisplayedInPLP() throws Exception {
		int number = Integer.parseInt(getDataQtyHeader().getControl().getAttribute("data-quantity"));
		Logz.step("The items available are " + number + " ");
		List<WebElement> list = getProductCountOnPLP().findElements(noOfProducts);
		int value = list.size();
		Logz.step("The items available are " + value + " ");
		Assert.assertEquals(number, value);
		Logz.step("Verified items for order type 313");
		// Assert.assertEquals(Integer.parseInt(getDataQtyHeader().getControl().getAttribute("data-quantity")),getProductCountOnPLP().getSize());
	}

	public ProductDescriptionPage clickOnItemName(String itemName) throws Exception {
        //Click on First Element From List
        getItemName(itemName).click();
        Logz.step("Clicked on :'"+itemName+"' - Name");
		//waitForPageToLoad();
		return new ProductDescriptionPage(driver);
	}

	public ProductDescriptionPage clickTableName(String itemName) throws Exception {
		getItemName(itemName).click();
		Logz.step("Clicked on " +itemName+ " from PLP Page");
		return new ProductDescriptionPage(driver);
	}

	public void validateProductsPerPage(String size) throws Exception {
		List<WebElement> list = getTestItemList().findElements(lnkProduct);
		int size1 = Integer.parseInt(size);
		Assert.assertEquals(list.size(), size1);
		Logz.step("displayed products count is "+list.size());
	}

	public void selectProductsPerPage(String option) throws Exception {
		getProductsOption(option).getControl().click();
/*		WebDriverWait wait = new WebDriverWait(driver,BaseTest.EXPLICIT_WAIT_TIME);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(imgLoadingIndicator));*/
		Common.waitForLoadingCompletion(driver);
		String selectedOption =getProductOption().getSelectedText();
		Assert.assertEquals(selectedOption,option ,"Selected show" + option + "products per page is not selected");
		Logz.step("Selected show" + option + "products per page");
	}

	public ProductDescriptionPage clickOnProduct(String Name) throws Exception {
		getTableName(Name).click();
		Logz.step("Clicked on Table Name");
		return new ProductDescriptionPage(driver);
	}

	public void selectPagination(String Name) throws Exception {
		getPaginationDropdown().selectFromDropDown(Name);
		Logz.step("Selected from Dropdown");
	}

	public void selectPriceSorting(String Name) throws Exception {
		SelectBox drpPriceDrpDwn = getPriceDropdown();
		drpPriceDrpDwn.getControl().click();
		drpPriceDrpDwn.selectFromDropDown(Name);
		Logz.step("Selected from Price Dropdown");
	}

	public ProductListPage clickVendorLink() throws Exception {
		//Common.waitForOperations(driver, lnkVendor);
		getVendorMenu().click();
		//getVendorLink().click();
		Logz.step("Clicked on Vendor Link");
		return new ProductListPage(driver);
	}

	public ProductListPage clickVendorOptions(String name) throws Exception {
		clickVendorLink();
		getVendorOptions(name).click();
		Logz.step("Clicked on Vendor Options:::" + name);
		return new ProductListPage(driver);
	}

	public void clickOnListView() throws Exception {
		getListView().click();
		Logz.step("Clicked on List View");
	}

	public void clickOnTableView() throws Exception {
		getTableView().click();
		Logz.step("Clicked on Table View");
	}

	public void clickOnGridView() throws Exception {
		getGridView().click();
		Logz.step("Clicked on Grid View");
	}

	public ProductDescriptionPage clickingOnItem() throws Exception {
		itemName = getProductListItems().get(0).getText();
		getProductListItems().get(0).click();
		Logz.step("Clicked on the Item Name:::" + itemName);

		return new ProductDescriptionPage(driver);
	}

	public void validateTablesPage(String title) throws Exception {
		if (getPageTitle().getControl().getText().toString().equalsIgnoreCase(title)) {
			Logz.step("Tables page is displayed");
		} else {
			Logz.step("Tables page is not displayed");
		}
	}

	public boolean isPriceLabelPresent(String itemName) throws Exception{
		commonMethodsPage = gotoCommonMethodsPage();
		return Common.isElementPresent(driver,By.xpath(sLblItem.replace("**",itemName)));
	}

	public ProductDescriptionPage verifyPDPNavigation() throws Exception {
		String itemName = (getProductListItems().get(1)).getText();
		clickOnItemName(itemName);
		String title = driver.getTitle();
		if (itemName.contains(title)) {
			Logz.step("PDP page is opened");
		} else {
			Logz.step("correct PDP page is not opened");
		}
		return new ProductDescriptionPage(driver);
	}

	public ShoppingCartPage addItemsToCart(int noOfItems) throws Exception {
		commonMethodsPage = gotoCommonMethodsPage();
		commonMethodsPage.addProductsToCart(noOfItems);
		return new ShoppingCartPage(driver);
	}

	public void validatePageLayoutIsTableView() throws Exception {
		if (getPageLayoutIsTableView().elementIsDisplayedAndEnabled()) {
			Logz.step(" Page layout is displayed Table view");
		}
	}

	public void validatePageLayoutIsListView() throws Exception {
		if (getPageLayoutIsListView().elementIsDisplayedAndEnabled()) {
			Logz.step(" Page layout is displayed List view");
		}
	}

	public void validatePageLayoutIsGridView() throws Exception {
		if (getPageLayoutIsGridView().elementIsDisplayedAndEnabled()) {
			Logz.step(" Page layout is displayed Grid view");
		}
	}

	public ProductListPage selectFiveItemsToCompareAndVerify() throws Exception {
		List<WebElement> lstToCmpEles = getListAddToCompare();
		Assert.assertTrue(lstToCmpEles.size() >= 5);
		Logz.step("Items to be compared are greater than 4");
		for (int i = 1; i <= 5; i++) {
			lstToCmpEles.get(i).click();
			if (i > 4) {
				String errorMssg = getAddToCompareErrorMsg().getControl().getText();
				Logz.step("Error Message is printed as: " + errorMssg);
				Logz.step("Expected Error Message is: " + BaseTest.getStringfromBundleFile("addToComapreErrorMsg"));
				Assert.assertEquals(errorMssg, BaseTest.getStringfromBundleFile("addToComapreErrorMsg"),
						"Error Message is not Displayed");
			}
			Logz.step("Selected Add To Compare checkbox for Item '" + i + "'");
		}
		return new ProductListPage(driver);
	}

	public void verifySortSelectorDropDown() throws Exception {
		Assert.assertTrue(getSortSelectorDropDown().elementIsDisplayedAndEnabled(),"Sort Dropdown is not available");
		Logz.step("Sort Dropdown is available");
	}

	public void verifyProductsAreSortedByPrice(String sort) throws Exception {
		boolean sorted = true;
		if (sort.equalsIgnoreCase("Sort by price, high to low")) {
			List<WebElement> productLst = getProductList();
			for (int i = 0; i < productLst.size() - 1; i++) {
				String price1 = productLst.get(i).getText();
				String price2 = productLst.get(i + 1).getText();
				price1 = price1.replaceAll("[$,]", "");
				price2 = price2.replaceAll("[$,]", "");

				if (Double.parseDouble(price1) >= Double.parseDouble(price2)) {
					sorted = true;
				} else {
					sorted = false;
					break;
				}
			}
			if (sorted) {
				Logz.step("Products are successfully sorted from High to Low");
			} else {
				Logz.step("Products failed to sort from High to Low");
			}
		}
		else if (sort.equalsIgnoreCase("Sort by price, low to high")) {
			List<WebElement> productLst = getProductList();
			for (int i = 0; i < productLst.size() - 1; i++) {
				String price1 = productLst.get(i).getText();
				String price2 = productLst.get(i + 1).getText();
				price1 = price1.replaceAll("[$,]", "");
				price2 = price2.replaceAll("[$,]", "");
				if (Double.parseDouble(price1) <= Double.parseDouble(price2)) {
					sorted = true;
				} else {
					sorted = false;
					break;
				}
			}
			
			if (sorted) {
				Logz.step("Products are successfully sorted from Low to High");
			} else {
				Logz.step("Products failed to sort from Low to High");
			}
		}
	}

	public void dragAndDropSliderRight() throws Exception {
		Generic btnSliderRightEle = getbuttonSliderRight();
		btnSliderRightEle.waitForClickableBy();
		WebElement sliderRight = btnSliderRightEle.getWebElement(btnSliderRight,"Slider Right");
		Actions a = new Actions(driver);
		a.dragAndDropBy(sliderRight, -50, 0).build().perform();
		Logz.step("performed drag and drop");
	}

	public ProductListPage dragAndDropSliderRightfacet(String option) throws Exception {
		WebElement headerExpandCollapseStatCtrl = getHeaderExpandCollapseStatus(option).getControl();
		if (headerExpandCollapseStatCtrl.getAttribute("aria-expanded")==null) {
			headerExpandCollapseStatCtrl.click();
			Logz.step("Clicked on expand icon");
		}
		Generic btnSliderRightEle = getbuttonSliderRight(option);
		btnSliderRightEle.waitForClickableBy();
		WebElement sliderRight = btnSliderRightEle.getWebElement(By.xpath("//div[@data-facet-id='"+option+"']//button[@class='facets-faceted-navigation-item-range-slider-bar-left']"),"Slider Right");
		Actions a = new Actions(driver);
		a.dragAndDropBy(sliderRight, -50, 0).build().perform();
		Logz.step("performed drag and drop");
		return new ProductListPage(driver);
	}

	public void verifyProductsSortedByPriceRangeSlider() throws Exception {
		Generic lnkProductPriceCtrl = getLinkProductPrice();
		lnkProductPriceCtrl.waitForClickableBy();
		WebElement sliderPriceCtrl = getSliderPrice().getControl();
		Double lowPrice = Double.parseDouble(sliderPriceCtrl.getAttribute("data-low").toString());
		Double highPrice = Double.parseDouble(sliderPriceCtrl.getAttribute("data-high").toString());
		List<WebElement> listProductPrice = lnkProductPriceCtrl.findElements(lnkProductPrice);
		for (int i = 0; i < listProductPrice.size(); i++) {
			String lprice = listProductPrice.get(i).getText();
			lprice = lprice.replace("$", "");
			if (lprice.contains(",")) {
				lprice = lprice.replace(",", "");
			}
			if (lowPrice <= Double.parseDouble(lprice) && highPrice >= Double.parseDouble(lprice)) {
				Logz.step("Product are sorted by price range slider");
			} else {
				Logz.step(listProductPrice.get(i) + "Product are not sorted by price range slider");
			}
		}
	}
	
	public void verifyFacetOptions(String name) throws Exception {
		Assert.assertTrue(getHeaderText(name).elementIsDisplayedAndEnabled(), "Header text is not equal ");
		Logz.step(name+ " Header text is displayed");
		if (getHeaderExpandCollapseStatus(name).getControl().getAttribute("aria-expanded")!=null) {
			getHeaderExpandCollapseIcon(name).getControl().click();
			Logz.step("Clicked on expand icon of" + name);

		}
		WebElement headerTxtCtrl = getHeaderText(name).getControl();
		WebElement filterOptCtrl = getSpecificFilterOptions(name).getControl();
		if (filterOptCtrl.getSize() != null && headerTxtCtrl.getText().equalsIgnoreCase(BaseTest.getStringfromBundleFile("depthIn"))) {
			List<WebElement> depthInDimension = driver.findElements(depthIn);
			depthInDimension.forEach(ele -> {
				Assert.assertTrue(ele.getText().contains("."));
				Logz.step(ele.getText() + " Value is shown in units of inches ");
			});

		} else if (filterOptCtrl.getSize() != null && headerTxtCtrl.getText().equalsIgnoreCase(BaseTest.getStringfromBundleFile("heightIn"))) {
			List<WebElement> heightInDimension = driver.findElements(heightIn);
			heightInDimension.forEach(ele -> {
				Assert.assertTrue(ele.getText().contains("."));
				Logz.step(ele.getText() + " Value is shown in units of inches ");
			});
		} else if (filterOptCtrl.getSize() != null) {
			Logz.step(name + " filter is having some values");
		} else Logz.step(name + "No options displayed");
	}

	//This method is used for filtering the results with dimensions
	public void verifyFilterResultsWithDimentions(String name,String value)throws Exception
	{
		String selValue="";
		Assert.assertTrue(getHeaderText(name).getControl().getText().equalsIgnoreCase(name),
				"Header text is not equal ");
		Logz.step(name + "Header text is displayed");
		if (getHeaderExpandCollapseStatus(name).getControl().getAttribute("aria-expanded")!=null) {
			getHeaderExpandCollapseIcon(name).getControl().click();
			Logz.step("Clicked on expand icon of" + name);
		}
		List<WebElement> options=getFilterOptions(name);
		if(options.size()>0)
		{
			for(int i=0;i<options.size();i++) {
				if(Double.parseDouble(options.get(i).getText().trim())==Double.parseDouble(value)) {
					selValue=options.get(i).getText().trim();
					options.get(i).click();
					break;
				} else
					Logz.step("Entered option value is not there in list"+value);
			}
		}
		else Logz.step("There are no option values for"+name);
		verifySearchTitle(selValue);
		List<WebElement>filResults=getFilteredProductTitles();
		if(filResults.size()>0)
		{
			for (int i = 0; i < filResults.size(); i++) {
				filResults.get(i).click();
				productDescriptionPage.verifyMeasurementDetails(selValue);
			}
		} else Logz.step("Filtered Results page is empty");

		WebElement clearAllIconCtrl = getclearAllIcon().getControl();
		if(clearAllIconCtrl.isDisplayed()) clearAllIconCtrl.click();
	}

	public void verifyFacetOptionShownInLeftSideForPrice() throws  Exception {
		Assert.assertTrue(getHeaderTextForPrice().elementIsDisplayedAndEnabled(),"option is not displayed in left side ");
		Logz.step("option is displayed");
	}

	public ProductListPage verifyFacetOptionShownInLeftSide(String name) throws  Exception {
		Assert.assertTrue(getHeaderText(name).elementIsDisplayedAndEnabled(), name+ "option is not displayed in left side ");
		Logz.step(name+" option is displayed");
		return new ProductListPage(driver);
	}

	public void verifyItemsDisplayingBasedOnSearch(String searchItemType) throws Exception {
		WebElement headerExpandCollapseStatFacetCtrl = getHeaderExpandCollapseStatusFacet().getControl();
		if (headerExpandCollapseStatFacetCtrl.getAttribute("aria-expanded")==null) {
			headerExpandCollapseStatFacetCtrl.click();
			Common.waitForLoadingCompletion(driver);
			Logz.step("Clicked on expand icon");
		}
		WebElement web = getFeatureList().getWebElement(featureList, "feature List");
		
		List<WebElement> listfeature = web.findElements(facetList);
		AtomicInteger count = new AtomicInteger(0);
		int iIndex = 0;
		listfeature.stream().filter(searchType -> searchType.getText().trim().equalsIgnoreCase(searchItemType.trim())).forEach(searchType -> {
			searchType.click();
			count.set(1);
		});

		Common.waitForLoadingCompletion(driver);
		Assert.assertEquals(count.intValue(), 1, "search Item type is not available");
		Logz.step("search Item Type is  available");
	}

	public void verifySearchTitle(String searchTitle) throws Exception {
		String Title = getSearchTitle().getControl().getText().trim();
		Assert.assertEquals(Title, searchTitle, "search Title is not equal ");
		Logz.step("search Title is  equal");
	}
	
	public ProductListPage verifyFacetsApplied() throws Exception {
		List<WebElement> list = getSearchTitle().getWebElements(searchTitle, "search Title ");
		int size = list.size();
		Logz.step("Size is "+size);
		Assert.assertEquals(size, 3, "search Title is not equal ");
		Logz.step("Verified that the facets are applied successfully");
		return new ProductListPage(driver);
	}

	public MyListPage goToMyWishList(String wishList ) throws Exception {
		getHeaderProfileMenu().getControl().click();
		Logz.step("Clicked on Header Profile Menu link");
		getSelectHeaderProfileLink(wishList).getControl().click();
		Logz.step("Header Profile Menu - Selected/clicked link name: " + wishList);

		return new MyListPage(driver);
	}
	public MyListPage navigateToMyListFromPLP(String linkName) throws Exception {
		getHeaderMenu().getControl().click();
		Logz.step("Clicked on Header Profile Menu link");
		getSelectHeaderLink(linkName).getControl().click();
		Logz.step("Header Profile Menu - Selected/clicked link name: " + linkName);
		return new MyListPage(driver);
	}

	public ProductDescriptionPage selectProductToAddToWishList() throws Exception
	{
		List<WebElement> prdLstItems = getProductListItems();
		itemName = prdLstItems.get(0).getText();
		clickOnItemName(itemName);
		return new ProductDescriptionPage(driver);
	}

}
