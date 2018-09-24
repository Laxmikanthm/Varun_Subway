package pages.MyAccountOverview;

import base.gui.controls.browser.*;
import base.pages.browser.BrowserBasePage;
import base.test.BaseTest;
import org.jsoup.Connection;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.Cart.CheckoutPage;
import pages.Cart.PaymentPage;
import pages.Cart.ShoppingCartPage;
import pages.Cart.ProductListPage;
import java.util.*;

import pages.CommonMethodsPage;
import pages.HomePage;
import util.Common;
import utils.Logz;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

public class MyListPage extends BrowserBasePage {

	String productName ;
	private By inputDesiredQuantity = By.xpath("//input[@id='in-modal-product-list-edit-item-quantity']");
	private By selectItemPriority = By.xpath("//select[@class='product-list-edit-item-select-priority product-list-edit-item-priority-input']");
	private By txtAreaNotes = By.xpath("//textarea[@class='product-list-edit-item-textarea']");
	private By btnSaveOnEditPopup = By.xpath("//div[@id='modal-body']/form//button[@class='product-list-edit-item-button-edit']");
	private By productsCount = By.xpath("//h2[contains(text(),'"+BaseTest.getStringfromBundleFile("wishList")+"')]/span");
	private By btnEdit = By.xpath("//button[@class='product-list-display-full-edit']");
	private By btnCancel = By.xpath("//button[contains(text(),'Cancel')]");
	private By Move = By.xpath("//button[@class='product-list-control-button-move']");
    private By txtItemFullName=By.xpath("//a[@class='product-list-display-full-name-anchor']");
	private static By myListHeader=By.xpath("//h2[@class='product-list-details-title']");
	// MyAccountOverview
	private By btnaddItemstoCart = By.xpath("//button[@class ='product-list-bulk-actions-button-addtocart']");
	private By lnkRemoveItem = By.xpath("//ul[@class='product-list-bulk-actions-dropdown']//a[contains(text(),'"+ BaseTest.getStringfromBundleFile("removeItem")+"')]");
	private By chkSelectAll = By.xpath("//input[@id='select-all']");
    private By chkListDisplay = By.xpath("//td[@class='product-list-display-full-select']/input");
	private By removeAllItemsMessage = By.xpath("//div[@class='product-list-details-no-items']");
	private By SingleCheckBox = By.xpath("//td[@class='product-list-display-full-select']/input");
	private By txtItemName = By.xpath("//div[@id='content']//a[@class='product-list-display-full-name-anchor']");
	private By btnAddToCartNext = By.xpath("//div[@class='product-list-bulk-actions-button-group']//button[@class='product-list-bulk-actions-button-addtocart']");
	private By lnkShoppingCart = By.xpath("//div[@class='header-menu-cart-dropdown']/div/a");
	private By lnkViewCart = By.xpath("//div[@class='header-mini-cart-buttons-left']/a");
	private By txtPageNotFound = By.xpath("//div/h1[contains(text()= ' " + BaseTest.getStringfromBundleFile("pageNotFound") + " ')]");
	//private By btnProceedToCheckout = By.xpath("//a[@id='btn-proceed-checkout']");
	private By pageHeader = By.xpath("//h2[text()='" + BaseTest.getStringfromBundleFile("reviewOrderPage") + "']");
	private By removeCardItemsLink = By.xpath("(//*[@class=' cart-lines-row']//div[7]//a[contains(text()," + BaseTest.getStringfromBundleFile("Remove") + ")])");
	private static By btnmove = By.xpath("//button[@class='product-list-control-button-move']");
 	private By wishListItemNames = By.xpath("//*[@class ='product-list-display-full-name']/a");
	private By drpSort = By.xpath("//*[@name='sort']");
	private By wishlistItemsAfterSort = By.xpath("//*[@class='product-list-display-full-name']/a");
	private By itemList = By.xpath("//button[@class='product-list-display-full-edit']");
	private By drpRemoveItems=By.xpath("//button[@class='product-list-bulk-actions-button-expander']");
	private By lnkCart = By.xpath("//div[@class='header-menu-cart-dropdown']/div/a");
	private By lnkCartView = By.xpath("//div[@class='header-mini-cart-buttons-left']/a");
	private By wishListItems = By.xpath("//*[@class = 'product-list-display-full-select']");
	private By bylnkSuccessMsg = By.xpath("//div[@class='global-views-message global-views-message-success alert']/div/a");
	public ShoppingCartPage shoppingCartPage;
	public RemoteWebDriver remoteWebDriver;
	public CommonMethodsPage commonMethodsPage;

	// strProductTitle = getSelectedItemToEdit().ghe;
	public CommonMethodsPage gotoCommonMethodsPage() throws Exception {
		return new CommonMethodsPage(driver);
	}

	public MyListPage(RemoteWebDriver driver) throws Exception {
		super(driver);
		Logz.step(getHeader().elementIsDisplayedAndEnabled()?"This is MyList Page":"MyList Page is not displayed");
	}

	public Generic getDesiredQntValue() throws Exception{
		return new Generic(driver, By.xpath("//div[@id='content']//a[contains(text(),'" +productName+ "')]/ancestor::td/following-sibling::td[@class='product-list-display-full-extras']//span[@class='product-list-display-full-quantity-value']"),"desired quantity value of selected item");
	}
	private Button getMoveButton() throws Exception{
		return new Button(driver, btnmove,"Move Button");
	}
	public Generic getPriorityValueOfSelectedItem()throws Exception {
		return new Generic(driver, By.xpath("//div[@id='content']//a[contains(text(),'" +productName+ "')]/ancestor::td/following-sibling::td[@class='product-list-display-full-extras']//span[@class='product-list-display-full-priority-value']"), "priority value  of selected item");
	}
	public Generic getNotesVlueofSelectedItem() throws Exception{
		return new Generic(driver,By.xpath("//div[@id='content']//a[contains(text(),'" +productName+ "')]/ancestor::td/following-sibling::td[@class='product-list-display-full-extras']//span[@class='product-list-display-full-notes-value']"),"note text of selected item");
	}
	public TextBox getNotesTextArea() throws Exception {
		return new TextBox(driver, txtAreaNotes, "Notes Text Area");
	}
	public SelectBox getPrioritySelectBox() throws Exception {
		return new SelectBox(driver, selectItemPriority, "Priority Select item");
	}
	public TextBox getDesiredQuantityInput() throws Exception {
		return new TextBox(driver, inputDesiredQuantity, "Desired Quantity Text Input");
	}
	public Button getSelectedItemEditButton() throws Exception {
		return new Button(driver, By.xpath("//div[@id='content']//a[contains(text(),'" +productName+ "')]/ancestor::td/following-sibling::td[@class='product-list-display-full-actions']//button[text()='Edit']"), "FirstProductName");
	}
	public Button getSaveButtonOnEditPopup() throws Exception {
		return new Button(driver, btnSaveOnEditPopup, "Save Buton on Edit Popup");
	}
	public Generic getTextItemName() throws Exception {
		return new Generic(driver, txtItemName, "Item Name");
	}
	private Generic getProductsCount() throws Exception {
		return new Generic(driver, productsCount, "Products Count");
	}
	private Button getEditButton() throws Exception {
		return new Button(driver, btnEdit, "Edit Button");
	}

	private Generic getMovebutton() throws Exception {
		return new Generic(driver, Move, "Move Button");
	}

	public List<WebElement> getSortedWishlistItems() throws  Exception{
		return getTextItemName().getWebElements(wishlistItemsAfterSort,"Sorted Item Names");
	}
	private SelectBox getDropDownSort() throws Exception{
		return new SelectBox(driver,drpSort,"Sort Drop Down");
	}

	// MyAccountOverview
	private Button getNextButton() throws Exception {
		return new Button(driver, btnAddToCartNext, "Next Button");
	}
	private LinkText getRemoveItem() throws Exception {
		return new LinkText(driver, lnkRemoveItem, "Remove Item");
	}
	private CheckBox getSingleCheckBox() throws Exception {
		return new CheckBox(driver, SingleCheckBox, "Select Single CheckBox");
	}
	public CheckBox getCheckBoxSelectAll() throws Exception {
		return new CheckBox(driver, chkSelectAll, "Search for the select all checkbox");
	}

    public CheckBox getAllCheckBoxSelect() throws Exception {
	    return new CheckBox(driver, chkSelectAll, "Search for the select all checkbox");
    }

    public List<WebElement> getChkListDisplay() throws Exception {
        return driver.findElements(chkListDisplay);
    }

	public List<WebElement> getProductListItems() throws  Exception{
		return driver.findElements(wishListItemNames);
	}
	public List<WebElement> getProductList() throws  Exception{
		return driver.findElements(wishListItems);
	}
	private LinkText getLnkSuccessMsg() throws Exception{
		return new LinkText(driver,bylnkSuccessMsg,"Success Message");
	}
	private TextBox getRemoveAllItemsMessage() throws Exception {
		return new TextBox(driver, removeAllItemsMessage, "Search for the select all checkbox");
	}
	private Generic getPageHeader() throws Exception {
		return new Generic(driver, pageHeader, "review your order header");
	}
	private LinkText getPageNotFound() throws Exception {
		return new LinkText(driver, txtPageNotFound, "Search for Page Not Found");
	}
	private Generic getHeader() throws Exception {
		return new Generic(driver, myListHeader, "my list order header");
	}
	private Button getAddItemsToCart() throws Exception {
		return new Button(driver, btnaddItemstoCart, "Next Button");
	}
	private LinkText getShoppingCart() throws Exception {
		return new LinkText(driver, lnkShoppingCart, "Cart");
	}
	private LinkText getViewCart() throws Exception {
		return new LinkText(driver, lnkViewCart, "View Cart");
	}
	
	private Generic getListItems() throws Exception {
		return new Generic(driver, itemList, "Size of Items");
	}
	private List<WebElement> getListOfItems()throws Exception {
		return getListItems().getWebElements(itemList,"Size of Items");
	}

	private Button getRemoveDropDown() throws Exception {
		return new Button(driver, drpRemoveItems, "Next Button");
	}
	private LinkText getShpCart() throws Exception {
		return new LinkText(driver, lnkCart, "Cart");
	}
	private LinkText getCartView() throws Exception {
		return new LinkText(driver, lnkCartView, "View Cart");
	}

	private List<WebElement> getProducts() throws Exception{
		return driver.findElements(txtItemFullName);
	}

	public void selectSortBy(String sortBy) throws Exception{
		getDropDownSort().selectFromDropDown(sortBy);
	}

	public MyListPage clickEditButton() throws Exception {
		getEditButton().click();
		return new MyListPage(driver);
	}
	private void enterQuantity(String quantity) throws Exception {
		Actions a = new Actions(driver);
		Common.waitForOperations(driver, inputDesiredQuantity);
		TextBox txtDesiredQtyInput = getDesiredQuantityInput();
		a.sendKeys(txtDesiredQtyInput.getControl(), Keys.BACK_SPACE).perform();
		Logz.step("clear quantity");
		a.sendKeys(txtDesiredQtyInput.getControl(), quantity).perform();
		txtDesiredQtyInput.getControl().sendKeys(Keys.TAB);
		Logz.step("entered quantity");
	}

	public void editAndVerfityItem() throws Exception{
		productName= getProducts().get(0).getText();
		getSelectedItemEditButton().click();
		enterQuantityPriorityNote(BaseTest.getStringfromBundleFile("desiredQuantity"),BaseTest.getStringfromBundleFile("priority"),BaseTest.getStringfromBundleFile("notes"));
		getSaveButtonOnEditPopup().click();
		Common.waitForOperations(driver,btnaddItemstoCart);
		Assert.assertEquals(BaseTest.getStringfromBundleFile("desiredQuantity"),getDesiredQntValue().getControl().getText().toString());
		Logz.step("quantity updated for edited item");
		Assert.assertEquals(BaseTest.getStringfromBundleFile("priority").toLowerCase(),getPriorityValueOfSelectedItem().getControl().getText().toString().toLowerCase());
		Logz.step("priority updated for edited item");
		Assert.assertEquals(BaseTest.getStringfromBundleFile("notes"),getNotesVlueofSelectedItem().getControl().getText().toString());
		Logz.step("notes updated for edited item");
	}

	private void enterQuantityPriorityNote(String quantity, String priority, String notes) throws Exception {
		Common.waitForOperations(driver, inputDesiredQuantity);
		TextBox txtDesiredQtyInput = getDesiredQuantityInput();
		txtDesiredQtyInput.getControl().sendKeys(Keys.chord(Keys.CONTROL,"a",quantity));
		Logz.step("entered quantity");
		getPrioritySelectBox().selectOptionFromDropDown(priority);
		Logz.step("selected priority");
		getNotesTextArea().setText(notes);
		Logz.step("entered note");
		getSaveButtonOnEditPopup();
	}

	public void verifySingleItemRemovalFromWishlist() throws Exception {
		int size1=getListOfItems().size();
		Logz.step("Items list before Removal: "+size1);
		Common.waitForOperations(driver, SingleCheckBox);
		getSingleCheckBox().check();
		clickRemove();
		int size2 = getListOfItems().size();
		Logz.step("Items list after Removal: "+size2);
		if(size2 < size1) {
			Logz.step("Selected single item is Successfully Removed");
		}else {
			Logz.step("Selected single item is not Removed");
		}
	}

	private MyListPage clickRemove() throws Exception{
		getRemoveDropDown().click();
		Logz.step("Clicked on Remove Dropdown");
		getRemoveItem().click();
		Logz.step("Clicked on Remove Item");
		return new MyListPage(driver);
	}
	public void verifyRemoveItemsFromWishlist() throws Exception {
        int iIndex = getChkListDisplay().size();

        if(iIndex > 1)
        {
            getAllCheckBoxSelect().check();
            Common.waitForLoadingCompletion(driver);
        }
        else
        {
            getSingleCheckBox().check();
            Common.waitForLoadingCompletion(driver);
        }
        clickRemove();
        Common.waitForLoadingCompletion(driver);
		Assert.assertTrue(isMyListEmpty(), "All Items are not Removed");
		Logz.step("All Items are Removed Successfully");
	}

	public boolean isMyListEmpty() throws Exception {
		boolean flag = false;
		commonMethodsPage = gotoCommonMethodsPage();
		if (Common.isElementPresent(driver,removeAllItemsMessage)) {
			String verifyAllItemsRemoved = getRemoveAllItemsMessage().getText();
			if (verifyAllItemsRemoved.equalsIgnoreCase(BaseTest.getStringfromBundleFile("afterMakingMyListEmpty"))) {
				flag = true;
			}
		}
		return flag;
	}

	public void verifyItemNameOnMyList(String item) throws Exception {
		Assert.assertTrue(item.equals(getTextItemName().getControl().getText()));
	}

	public void verifyReviewPage() throws Exception {
		String value = getPageHeader().getControl().getText();
		Assert.assertEquals(value, "Review Your Order", "Review Your Order Page is not Displayed");
	}

	public void AddToCartSingleItemFromSmallWarePackage() throws Exception {
		int size1=getListOfItems().size();
		Logz.step("Items list before Removal: "+size1);
		Common.waitForOperations(driver, SingleCheckBox);
		getSingleCheckBox().check();
		Common.waitForLoadingCompletion(driver);
		getAddItemsToCart().click();
		Logz.step("Add to cart button is clicked");


	}

	public ShoppingCartPage AddToCartAllItemsFromSmallWarePackage() throws Exception {
		getCheckBoxSelectAll().check();		
		List<WebElement> list = getMovebutton().getWebElements(Move, "Move Button");
		int beforeAddingToCart = list.size();
		getAddItemsToCart().click();
		Logz.step("Add to cart button is clicked");
		return new ShoppingCartPage(driver);
	}

	public int AddToCartAllItemFromSmallWarePackage() throws Exception {
		getCheckBoxSelectAll().check();		
		List<WebElement> list = getMovebutton().getWebElements(Move, "Move Button");
		int beforeAddingToCart = list.size();
		getAddItemsToCart().click();
		Logz.step("Add to cart button is clicked");
		return beforeAddingToCart;
	}

	/*public ShoppingCartPage AddToCartAllItemFromSmallWarePackage() throws Exception
	{
		getCheckBoxSelectAll().getControl().click();
		Logz.step("Select All CheckBox is Clicked");
//		List<WebElement> list = remoteWebDriver.findElements(moveButton);
//		int beforeAddingToCart = list.size();
		getAddItemsToCart().click();
		Logz.step("Add to cart button is clicked");
		shoppingCartPage = new ShoppingCartPage(driver);
	    shoppingCartPage.openShoppingCartPage();
//		List<WebElement> list1 = remoteWebDriver.findElements(removeCardIteamsLink);
//		int afterAddingToCart = list1.size();
//		Assert.assertEquals(beforeAddingToCart, afterAddingToCart);
		return new ShoppingCartPage(driver);
	}
*/
	public void verifySortByResults(String sortByName, HashMap<String, Double> HMap) throws Exception{
		Map<String,Double> map=new TreeMap<String,Double>(HMap);
		selectSortBy(sortByName);
		Set itemNames = map.keySet();
		ArrayList<String> al = new ArrayList<String>();
		for (int i=0;i<getSortedWishlistItems().size();i++){
			al.add(getSortedWishlistItems().get(i).getText());
		}
		Assert.assertEquals(itemNames.toArray(),al.toArray());
	}

	public ShoppingCartPage addItemFromSmallWarePackage() throws Exception {
		Common.waitForOperations(driver, SingleCheckBox);
		getSingleCheckBox().check();
		Logz.step(" CheckBox is Clicked");
		getAddItemsToCart().click();
		Logz.step("Add to cart button is clicked");
        //Wait Till Element is Added to Wish List
		Common.waitForOperations(driver, bylnkSuccessMsg);
		getShoppingCart().click();
		Logz.step("Shopping Cart Icon is clicked");
		getViewCart().click();
		Logz.step("View Button is clicked");

		return new ShoppingCartPage(driver);
	}

	public ShoppingCartPage navigateToShoppingCartPage() throws Exception {
		getShpCart().click();
        WebDriverWait wait = new WebDriverWait(driver,120);
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(lnkCartView)));
		getCartView().click();
		return new ShoppingCartPage(driver);
	}

	public void makeMyListEmpty() throws Exception{

	if (!isMyListEmpty()){
			removeAllItemsFromWishList();
			Logz.step("MyList Is Empty");
		}
	}
	public ProductListPage navigateToPLPFromMyList() throws Exception{
		driver.navigate().to(BaseTest.getStringfromBundleFile("shopMenu"));
		return new ProductListPage(driver);
	}


	public void editQuantityAndVerifyItem() throws Exception {
		productName = getProducts().get(0).getText();
		getSelectedItemEditButton().click();
		enterQuantity(BaseTest.getStringfromBundleFile("desiredQuantity"));
		getSaveButtonOnEditPopup().click();
		WebDriverWait wait = new WebDriverWait(driver,20);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(btnCancel));
		Common.waitForOperations(driver, btnaddItemstoCart);
		Assert.assertEquals(BaseTest.getStringfromBundleFile("desiredQuantity"), getDesiredQntValue().getControl().getText().toString());
		Logz.step("quantity updated for edited item");
	}

    private void removeAllItemsFromWishList() throws Exception {
        try {
            List<WebElement> itemChkBoxes = getProductList();
            if (itemChkBoxes.size() > 0) {
                if (itemChkBoxes.size() > 1) {
                    getCheckBoxSelectAll().check();
                } else {
                    itemChkBoxes.get(0).click();
                }
                getRemoveDropDown().click();
                getRemoveItem().click();
            }
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

	public void verifyProductExistInWishList() throws Exception
	{
		try
		{
			List<WebElement> itemList = getProductListItems();
			for (WebElement element :itemList
				 ) {
				if(element.getText().contains(ProductListPage.itemName));
				{
					Logz.info("item found in wish list");
					break;
				}
			}

		} catch (Exception ex) {
			Logz.error(ex.getMessage());
			throw ex;
		}
		finally {
			removeAllItemsFromWishList();
		}

	}
}
