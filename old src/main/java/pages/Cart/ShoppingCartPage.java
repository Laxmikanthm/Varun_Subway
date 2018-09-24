package pages.Cart;

import base.gui.controls.browser.Generic;
import base.gui.controls.browser.LinkText;
import base.gui.controls.browser.TextBox;
import base.pages.browser.BrowserBasePage;
import base.test.BaseTest;
import org.openqa.selenium.By;
import base.gui.controls.browser.Button;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.CommonMethodsPage;
import pages.HomePage;
import util.Common;
import utils.Logz;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartPage<T extends RemoteWebDriver> extends BrowserBasePage {

	private By lnkEditItem = By.xpath("//a[@class='cart-item-actions-item-list-actionable-edit-button-edit']");
	//private By lnkSaveForLaterItem = By.xpath("//a[@class='cart-item-actions-item-list-actionable-edit-content-saveforlater']");
	private By lnkSaveForLaterItem = By.partialLinkText("Save for Later");
	private By lnkRemoveItem = By.xpath("//a[@class='cart-item-actions-item-list-actionable-edit-content-remove']");
	private By cartPopUpWindow = By.xpath("//div[@class='global-views-modal-content']/div[@id='modal-body']");
	private By txtFieldProdQuantity = By.xpath("//input[@class='product-details-quantity-value']");
	private By cartItemQuantity = By.xpath("//*[@class = 'header-menu-cart-dropdown']//i//following-sibling::span");
	private By btnUpdate = By.xpath("//button[@type='submit' and contains(text(),'"+BaseTest.getStringfromBundleFile("update")+"')]");
	private By cartItemNames = By.xpath("//div[@class='cart-lines-name']/a");
	private By cartTitle = By.xpath("//div[@class='cart-detailed-view-header']//h2");
	private By txtSaveItemsForLater = By.xpath("//div[@class ='global-views-message global-views-message-success alert']/div");
	private By btnMoveToCart = By.xpath("//button[contains(text(),' " + BaseTest.getStringfromBundleFile("buttonMoveToCart") + " ')]");
	private By txtOrderSummary = By.xpath("//h3[@class='cart-summary-title']");
	private static By txtSubTotalItems = By.xpath("//p[@class='cart-summary-grid-float']");
	private static By btnProceedToCheckout = By.id("btn-proceed-checkout");
	private By lnkPromoCode = By.xpath("//a[contains(text()," + BaseTest.getStringfromBundleFile("Have_a_Promo_Code") + ")]");
	private By lnkEstimateATaxAndShipping = By.xpath("//a[contains(text()," + BaseTest.getStringfromBundleFile("Have_a_Promo_Code") + ")]");
	private By txtFieldPromoCode = By.xpath("//input[@id='promocode']");
	private By btnApply = By.xpath("//button[contains(text(),'" + BaseTest.getStringfromBundleFile("apply") + "')]");
	private By lnkProceedToCheckout = By.xpath("//a[@id='btn-proceed-checkout']");
	private By inputQuantity = By.xpath("//input[@data-type='cart-item-quantity-input']");
	private By removeCartItemsLink = By.xpath("(//*[@class=' cart-lines-row']//div[7]//a[contains(text()," + BaseTest.getStringfromBundleFile("Remove") + ")])");
	private By lnkShoppingCart = By.xpath("//div[@class='header-menu-cart-dropdown']/div/a");
	private By lnkViewCart = By.xpath("//div[@class='header-mini-cart-buttons-left']/a");
	private By txtPageNotFound = By.xpath("//h1[contains(text(),'" + BaseTest.getStringfromBundleFile("shoppingCart") + "']");
	private By txtOutOfStock = By.xpath("//div[@class='product-line-stock']");
	private By lnkItemNames = By.xpath("//div[@class='cart-lines-name']/a");
	private By txtName = By.xpath("//a[contains(text(),' " + BaseTest.getStringfromBundleFile("itemName") + " ')]");
	private By txtvalidationMsg = By.xpath("//p[@data-validation-error='block']");
	private By spanSKUNumberProduct = By.xpath("//div[@data-view='Product.Sku']//span[@class='product-line-sku-value']");
	private By spanSKUNumberItem = By.xpath("//div[@data-view='Item.Sku']//span[@class='product-line-sku-value']");

	public HomePage homePage;
	public CommonMethodsPage commonMethodsPage;
	WebDriverWait wait = new WebDriverWait(driver, BaseTest.EXPLICIT_WAIT_TIME);

	// This method will return the commonMethodsPage driver object
	private CommonMethodsPage gotoCommonMethodsPage() throws Exception {
		return new CommonMethodsPage(driver);
	}

	public ShoppingCartPage(RemoteWebDriver driver) throws Exception {
		super(driver);
		Logz.step(getTextSubTotalItems().elementIsDisplayedAndEnabled()?"This is Shopping Cart page":"Shopping Cart Page is not displayed");
		Common.waitForLoadingCompletion(driver);
		Logz.step(getTextSubTotalItems().elementIsDisplayedAndEnabled()?"This is Shopping Cart page":"Shopping Cart Page is not displayed");
		Common.waitForLoadingCompletion(driver);
	}

	private Generic getTextValidationMsg() throws Exception {
		return new Generic(driver, txtvalidationMsg, "OrderLimit Validation Message");
	}
	private Generic getTextOrderSummary() throws Exception {
		return new Generic(driver, txtOrderSummary, "Order Summary");
	}
	private Generic getTextSubTotalItems() throws Exception {
		return new Generic(driver, txtSubTotalItems, "SubTotal Items");
	}
	private Generic getCartTitle() throws Exception {
		return new Generic(driver, cartTitle, "Cart Title");
	}
	private LinkText getSaveItemsForLaterTitle() throws Exception {
		return new LinkText(driver, txtSaveItemsForLater, "Save For Later Title");
	}
	private Button getButtonProceedToCheckout() throws Exception {
		return new Button(driver, btnProceedToCheckout, "Proceed to Checkout");
	}
	private Button getUpdateButton() throws Exception {
		return new Button(driver, btnUpdate, "Update");
	}
	private Button getMoveToCartButton() throws Exception {
		return new Button(driver, btnMoveToCart, "Move to Cart");
	}
	private Generic getLinkPromoCode() throws Exception {
		return new Generic(driver, lnkPromoCode, "Promo Code");
	}
	private Generic getLinkEstimaateTaxAndShipping() throws Exception {
		return new Generic(driver, lnkEstimateATaxAndShipping, "Estimate Tax and Shipping");
	}
	private TextBox getTextPromoCode() throws Exception {
		return new TextBox(driver, txtFieldPromoCode, "Promo code");
	}
	private Generic getCartPopUpWindow() throws Exception {
		return new Generic(driver, cartPopUpWindow, "Cart Popup Window");
	}
	private LinkText getEditItem() throws Exception {
		return new LinkText(driver, lnkEditItem, "Edit Item");
	}
	private LinkText getSaveForLaterItem() throws Exception {
		return new LinkText(driver, lnkSaveForLaterItem, "Save For Later Item");
	}
	private LinkText getRemoveItem() throws Exception {
		return new LinkText(driver, lnkRemoveItem, "Remove");
	}
	private Button getButtonApply() throws Exception {
		return new Button(driver, btnApply, "Apply Button");
	}

	// private Generic getLinkProceedToCheckout() throws Exception{
	// return new Generic(driver,linkProceedToCheckout,"Proceed to Check");
	// }

	private TextBox getInputQuantity() throws Exception {
		return new TextBox(driver, inputQuantity, "Quantity");
	}
	private TextBox getProductQuantity() throws Exception {
		return new TextBox(driver, txtFieldProdQuantity, "Product Quantity");
	}
	private TextBox getCartItemQty() throws Exception {
		return new TextBox(driver, cartItemQuantity, "Cart Item Quantity");
	}
	public LinkText getShoppingCart() throws Exception {
		return new LinkText(driver, lnkShoppingCart, "Cart");
	}
	public LinkText getViewCart() throws Exception {
		return new LinkText(driver, lnkViewCart, "View Cart");
	}
	public LinkText getPageNotFound() throws Exception {
		return new LinkText(driver, txtPageNotFound, "Search for Page Not Found");
	}
	private Generic getOutOfStockText() throws Exception {
		return new Generic(driver, txtOutOfStock, "OutOfStock");
	}
	private LinkText getNameItem() throws Exception {
		return new LinkText(driver, txtName, "Item Name");
	}
	private Generic getRemoveAllItems() throws Exception {
		return new Generic(driver, removeCartItemsLink, "Remove Items  in Cart");
	}
	// This will work though we have multiple products/Items in the Cart page, Since
	// we are passing SKU Number
	private Generic getCheckoutPageSKUNumber(String chkoutSKU) throws Exception {
		return new Generic(driver, By.xpath(".//*[@class='product-line-sku-container']//span[text()='" + chkoutSKU + "']"), "Product SKU on Checkout page");
	}
	private Generic cartItemNames() throws Exception {
		return new Generic(driver, cartItemNames, "Item Names which are present in the Cart");
	}
	private List<WebElement> getCartItems() throws Exception {
		return getTextOrderSummary().getWebElements(cartItemNames, "Cart Item Names");
	}
	public void verifyErrorMessage() throws Exception {
	Assert.assertEquals(getTextValidationMsg().getControl().getText(), BaseTest.getStringfromBundleFile("shoppingcartValidationexceedMessage"), "Expected error Message is not displayed" + getTextValidationMsg().getControl().getText());
	}
	public void verifyshoppingcartErrorMessage() throws Exception {
		Assert.assertEquals(getTextValidationMsg().getControl().getText(), BaseTest.getStringfromBundleFile("shoppingcartValidationMessage"), "Expected error Message is not displayed" + getTextValidationMsg().getControl().getText());
	}
	private Generic getProductSizeLabel(String size) throws Exception {
		return new Generic(driver, By.xpath(".//*[@class='custcol_fwh_size-controls-group']//label[contains(text(),'" + size + "')]"), "Product Size");
	}
	private Generic getProductSKUNumber() throws Exception {
		return new Generic(driver, spanSKUNumberProduct, "Product SKU number on Edit Screen");
	}
	private Generic getItemSKUNumber() throws Exception {
		return new Generic(driver, spanSKUNumberItem, "Item SKU number on Edit Screen");
	}
	private List<WebElement> getlinkItemNames() throws Exception {
		return getTextOrderSummary().getWebElements(lnkItemNames, "List of Producttitles Added to cart");
	}

	public void verifySKUNumberonCheckoutPage(String expectedSKU) throws Exception {

		wait.until(ExpectedConditions.invisibilityOfElementLocated(btnUpdate));
		Assert.assertEquals(getItemSKUNumber().getControl().getText(),expectedSKU,"SKU is not matching Expected "+expectedSKU+ "Actual "+getItemSKUNumber().getControl().getText());
		Logz.step("SKU is matching");
	}

	public void verifyCartBeforeAndAfterSwitching(ShoppingCartPage shoppingCartPage, HomePage homePage, String sStoreNM) throws Exception {
		commonMethodsPage = gotoCommonMethodsPage();
		commonMethodsPage.isCartHasSameItemsForDifferentStores(shoppingCartPage,homePage,sStoreNM);
	}

	public ShoppingCartPage getSubTotalItemsValue() throws Exception {
		return new ShoppingCartPage(driver);
	}

	public CheckoutPage clickProceedToCheckout() throws Exception {
        Common.waitForLoadingCompletion(driver);
		wait.until(ExpectedConditions.elementToBeClickable(btnProceedToCheckout));
		getButtonProceedToCheckout().click();
		Logz.step("Clicked on Proceed to Checkout Button");
		return new CheckoutPage(driver);
	}

	public ShoppingCartPage clickPromoCodeLink() throws Exception {
		getLinkPromoCode().getControl().click();
		return new ShoppingCartPage(driver);
	}

	public ShoppingCartPage clickEstimateTaxAndShipping() throws Exception {
		getLinkEstimaateTaxAndShipping().getControl().click();
		return new ShoppingCartPage(driver);
	}

	public ShoppingCartPage clickApplyButton() throws Exception {
		getButtonApply().click();
		return new ShoppingCartPage(driver);
	}

	public ShoppingCartPage enterValueForQuantity(String QuantityValue) throws Exception {
		getInputQuantity().setText(QuantityValue);
		return new ShoppingCartPage(driver);
	}

	public ShoppingCartPage getOrderSummaryText() throws Exception {
		return new ShoppingCartPage(driver);
	}

	public String[] getCartItemNames() throws Exception {
		String[] arrProductNames = new String[getCartItems().size()];
		int j = 0;
		for (int i = 0; i < getCartItems().size(); i++) {
			arrProductNames[j++] = getCartItems().get(i).getText().toString();
		}
		return arrProductNames;
	}

	public int getCartItemsCount() throws Exception {
		int itemQty = Integer.parseInt(getCartItemQty().getControl().getText());
		return itemQty;
	}

	public ShoppingCartPage clickEdit() throws Exception{
		getEditItem().click();
		Logz.step("Clicked on Edit Button");
		Common.waitForOperations(driver,cartPopUpWindow);
		return new ShoppingCartPage(driver);
	}

	public void navigateToAnItemToEdit() throws Exception {
		clickEdit();
		verifyItemPopupWindow();
	}

	public void verifyItemPopupWindow() throws Exception{
		Assert.assertTrue(getCartPopUpWindow().elementIsDisplayedAndEnabled(),"Item Pop is not displayed");
		Logz.step("Item Pop is displayed");
/*		if (getCartPopUpWindow().getControl().isDisplayed()) {
			Logz.step("Item Popup window is displayed");
		} else {
			Logz.step("Item Popup window is not displayed");
		}*/
	}

	public String editAndVerifySKUNumber(String productSize) throws Exception {
		String itemSKU = getProductSKUNumber().getControl().getText();
		String arrItemSKU[] = itemSKU.split("-");
		String ProdSize[] = productSize.split("'");
		getProductSizeLabel(ProdSize[0]).getControl().click();
		String arrSize[] = productSize.split("'");
		String updatedSKU = null;
		if (arrSize.length > 1) {
			updatedSKU = arrItemSKU[0] + "-" + arrSize[0] + "-" + arrSize[1];
			Assert.assertTrue(getProductSKUNumber().getControl().getText().toString().equalsIgnoreCase(updatedSKU), "SKU Number did not match after changing the product size");
		} else {
			updatedSKU = arrItemSKU[0] + "-" + arrSize[0];
			Assert.assertTrue(getProductSKUNumber().getControl().getText().toString().equalsIgnoreCase(updatedSKU), "SKU Number did not match after changing the product size");
		}
		getUpdateButton().click();
		return updatedSKU;
	}

	public void navigateToAnItemToSaveForLater() throws Exception {
		getSaveForLaterItem().click();
		Logz.step("Clicked on Save For Later Button");
	}

	public void removeItemFromCart() throws Exception {
		getRemoveItem().click();
		Logz.step("Clicked on Remove Button");
	}

	public ShoppingCartPage selectQuantityAndUpdateCart(String quantity) throws Exception {
		Assert.assertTrue(getProductQuantity().elementIsDisplayedAndEnabled(),"Product Quantity is not displayed");
		Logz.step("Product quantity is displayed");
		getProductQuantity().setText(quantity);
		Logz.step("Quantity is updated");
		Common.waitForOperations(driver,txtFieldProdQuantity);
		clickUpdate();
		return new ShoppingCartPage(driver);
	}

	public void clickUpdate() throws Exception{

		JavascriptExecutor javascriptExecutor = (JavascriptExecutor)driver;

		javascriptExecutor.executeScript("arguments[0].click();",getUpdateButton().getControl());
		javascriptExecutor.executeScript("arguments[0].click();",getUpdateButton().getControl());

		/*getUpdateButton().getControl().click();*/
		Logz.step("Clicked on Update Button");
	}

	public void verifyUpdatedItemQuantity(String quantity) throws Exception {
		if (getInputQuantity().getAttribute("value").toString().equalsIgnoreCase(quantity)) {
			Logz.step("Updated item Quantity is shown in the Shopping Cart");
		} else {
			Logz.step("Updated item Quantity is not shown in the Shopping Cart");
		}
	}

	public void verifyItemUnderCart() throws Exception {
		if (getCartTitle().getControl().getText().toString().equalsIgnoreCase(BaseTest.getStringfromBundleFile("cartEmpty"))) {
			Logz.step("Item is removed from cart");
		} else {
			Logz.step("Item is not removed from cart");
		}

	}

	public void verifyItemIsAddedToCart() throws Exception {
		commonMethodsPage = gotoCommonMethodsPage();
		if (Common.isElementPresent(driver,lnkProceedToCheckout)) {
			Logz.step("Item is added to cart");
		} else {
			Logz.step("Item is not added to cart");
		}
	}

	public void verifySaveItemsForLaterInCart() throws Exception {
		if (getMoveToCartButton().getText().toString().equalsIgnoreCase(BaseTest.getStringfromBundleFile("buttonMoveToCart"))) {
			Logz.step("Item is saved for later is displayed in the Saved for Later section in the Shopping Cart ");
		} else {
			Logz.step("Item is not saved for later is displayed in the Saved for Later section in the Shopping Cart ");
		}
	}

	private boolean isProceedToCheckoutButtonDisplayed() throws Exception {
		commonMethodsPage = gotoCommonMethodsPage();
		return Common.isElementPresent(driver,lnkProceedToCheckout);
	}

	public void verifyProductCheckoutButton() throws Exception {
		Assert.assertFalse(isProceedToCheckoutButtonDisplayed(),"Proceed To Check out Button is Visible and Enabled");
		Logz.step("Proceed To Check out Button is Visible and Enabled");
	}

	public void openShoppingCartPage() throws Exception {
		getShoppingCart().click();
		Logz.step("Shopping Cart Icon is clicked");
		getViewCart().click();
		Logz.step("View Button is clicked");
	}

	public void removeAllItemsFromCart() throws Exception {
		openShoppingCartPage();
		List<WebElement> list = getRemoveAllItems().getWebElements(removeCartItemsLink, "Remove Items  in Cart");
		list.forEach(WebElement::click);
		Logz.step("All items are removed");
	}

	public void verifyOutOfStockMsgInShoppingCart() throws Exception {
		Assert.assertTrue(getOutOfStockText().getControl().isDisplayed(),"Out of Stock message is not displayed");
		Logz.step("Out Of Stock message is displayed in the cart page");
	}

	public void navigateToShoppingCart() throws Exception {
		String text = getPageNotFound().getText();
		if (text.contains("Page Not Found")) {
			getShoppingCart().click();
			Logz.step("Shopping Cart Icon is  again clicked");
			getViewCart().click();
			Logz.step("View Button is again clicked");
		}
		Logz.step("Shopping Cart Page is opened");
	}

	public void clickOnMoveToCart() throws Exception {
		getMoveToCartButton().click();
		Logz.step("Clicked On MoveToCart to add Item");
		Thread.sleep(3000);
		Assert.assertTrue(getCartItemsCount()>0,"Item is not Moved back to cart");
		Logz.step("Item is moved back to cart");
	}

	public void verifyAddedProducts(List<String> names) throws Exception {

		List<String> productTitles = new ArrayList<String>();
		List<WebElement> lnkItemNames = getlinkItemNames();
		for (int i = 0; i < lnkItemNames.size(); i++) {
			productTitles.add(lnkItemNames.get(i).getText());
		}
		Assert.assertTrue(productTitles.containsAll(names), "Product items are added to cart");
	}

	public void verifyItemAddedToCartAfterClickingAccept(String itemName) throws Exception {
		String name = getNameItem().getText();
		Assert.assertEquals(name, itemName, "Item not added to cart");
		Logz.step("item added to cart" + name);
	}

	public void verifyReorderItem() throws Exception {
		int size = getCartItems().size();
		Logz.step("Number of items in cart: " + size);
		if (size > 0) {
			Logz.step("Items are added to cart");
		} else {
			Logz.step("Items are NOT added to cart");
		}
	}

	public void verifyReorderAllItems() throws Exception {
		int size3 = getCartItems().size();
		Logz.step("Number of items in cart: " + size3);
		if (size3 > 0) {
			Logz.step("Items are added to cart");
		} else {
			Logz.step("Items are NOT added to cart");
		}
	}

	public void verifyItemsInCart() throws Exception {
		int size = getCartItems().size();
		Logz.step("Number of items in cart: " + size);
		if (size > 0) {
			Logz.step("Items are added to cart");
		} else {
			Logz.step("Items are NOT added to cart");
		}
	}

	public void verifyItemInCart(int cartSize) throws Exception {
		int size = getCartItems().size();
		Logz.step("Number of items in cart: " + size);
		Assert.assertEquals(cartSize, size);
	}

}
