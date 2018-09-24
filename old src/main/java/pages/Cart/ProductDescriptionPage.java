package pages.Cart;

import base.gui.controls.browser.*;
import base.pages.browser.BrowserBasePage;
import base.test.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import pages.CommonMethodsPage;
import pages.HomePage;
import pages.RequestQuotePage;
import util.Common;
import utils.Logz;
import java.util.List;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductDescriptionPage<T extends RemoteWebDriver> extends BrowserBasePage {

    public By drpSelectOrdertype = By.xpath("//ul/li//button[contains(text(),' "+ BaseTest.getStringfromBundleFile("dropdownSelectOrderType") + " ')]");
    private By btnAddToCart = By.xpath("//div[@class = 'cart-add-to-cart-button']//button[contains(text(),'"+BaseTest.getStringfromBundleFile("linkAddToCart")+"')]");
    private By btnAddToQuote = By.xpath("//button[@class='product-detail-to-quote-add-to-quote-button']");
    private static By quantity = By.id("quantity");
    private By btnReview = By.xpath("//a[contains(text(),'" + BaseTest.getStringfromBundleFile("review") + "')]");
    private By btnAddToWishlist = By.xpath("//button[@class='product-list-control-button-wishlist']");
    private By lnkCreateNewList = By.xpath("//div/a[text() = ' "+BaseTest.getStringfromBundleFile("createNewList")+" ']");
    private By inputNewListName = By.xpath("//div[@class='product-list-control-new-item-add-new-list-input-container']/input");
    private By btnCreateAndAddItem = By.xpath("//button[text()='  "+BaseTest.getStringfromBundleFile("buttonCreateAndAddItem")+"  ']");
    private By addToCartModelView = By.xpath("//div[@class = 'global-views-modal-content']");
    //private By lnkViewCartCheckout = By.xpath("//div[@class = 'global-views-modal-content']//div/a[text()='" + BaseTest.getStringfromBundleFile("btnViewCartCheckout") + "']");
    private By lnkViewCartCheckout = By.xpath("//a[text()='" + BaseTest.getStringfromBundleFile("btnViewCartCheckout") + "']");
    private By btnContinueShopping = By.xpath("//div[@class = 'global-views-modal-content']//div/button[text()='" + BaseTest.getStringfromBundleFile("btnContinueShopping") + "']");
    private By txtLeasableItem = By.xpath("//header[@class = 'product-details-full-header']//div/b");
    private By txtItemLeasable = By.xpath("//div[@class='custcol_fwh_isleased-controls-group']//label[contains(text(),'Leased')]");
    private By chkLeasible = By.xpath("//div[@id ='custcol_fwh_isleased-container']//label/following-sibling::div/input");
    private By btnViewCartAndCheckout = By.xpath("//a[contains(text(),'" + BaseTest.getStringfromBundleFile("viewCartCheckout")+"')]");
    private By txtItemAddedToProduct = By.xpath("//div[contains(text(),'   Good! You added this item to your product list   ')]");
    private By txtHeaderPartsAndAccessories = By.xpath("//h3[text() = 'Parts and Accessories']");
    private By orderTypeSelectionPrompt = By.xpath("//div[@class='global-views-message global-views-message-error alert']");
    private By orderTypeDropdown = By.xpath("//div[@data-view='OrderTypeView']//button");
    private By txtInvalidMessage = By.xpath("//form[@id='product-details-full-form']//p");
    //private By spanSKUNumber = By.xpath("//form[@id='product-details-full-form']//span[@class='product-line-sku-value']");
    private By spanSKUNumber = By.xpath("//span[@itemprop='sku']");
    private By addToCartModelSKUNumber = By.xpath("//div[@class='cart-confirmation-modal']//span[@class='product-line-sku-value']");
    private By headerAddedToCartWindow = By.xpath("//div[@id='modal-header']/h2");
    private By txtItemQuantity = By.xpath("//span[text()='Quantity: ']/following::span");
    private By txtFieldItemQuantity = By.id("quantity");
    private By btnModelpoupClose = By.xpath("//*[@id='modal-header']/button");
    private By txtSuccessAlert = By.xpath("//*[@class = 'global-views-message global-views-message-success alert']/div");
    private By txtSplInstructions =By.xpath("//input[@id='custcol_fwh_special_instructions']");
    private By txtItemDescriptiveAttributes = By.xpath("//section[@data-view='ProductDescritiveAttributes']/table//th");
    private By txtOutOfStock=By.xpath("//div[@class='product-line-stock']");
    private By txtMeasurements=By.xpath("//div[@class='product-details-information-tab-content-container']");
    private By btnClose= By.xpath("//button[@class='global-views-modal-content-header-close']");
    private By txtItemPrice= By.xpath("//span[@class='product-views-price-lead']");
    private By txtDetails= By.id("product-details-information-tab-content-container-0");
    private By btnAccept = By.xpath("//button[contains(text(),'"+BaseTest.getStringfromBundleFile("accept")+"')]");
    private By imgNextIcon=By.xpath("//a[@data-action='next-image']");
    private By imgPrevIcon=By.xpath("//a[@data-action='prev-image']");
    private By imgPath=By.xpath("//div[@class='bx-pager bx-custom-pager']/div/a/img");
    private By detailsList = By.xpath("//div[@id='product-details-information-tab-content-container-0']//ul//li");
    public By lblUnitOfMeasure = By.xpath("//label[@class='product-views-option-tile-label']");
    public By lblUnitSize = By.xpath("//label[@class='product-views-option-tile-label']//following-sibling::div/*[1]");
    private By orderVerificationModelTitle=By.xpath("//h1[@class = 'modal-title']");
    private By btnAcceptOrderVerification=By.xpath("//div[@class='global-views-modal-content']//button[@class='btn btn-primary orderVerification']");
    private By lnkQuoteRequest = By.xpath("//a[contains(text(),' "+BaseTest.getStringfromBundleFile("quoteRequest")+" ')]");
    private By imgItem = By.xpath("//div[@class='product-details-image-gallery-detailed-image']");
    private By lnkShopBYArea = By.xpath("//a[contains(text(),'"+BaseTest.getStringfromBundleFile("shopByArea")+"')]");

    public CommonMethodsPage<T> commonMethodsPage;
    private String itemName;
    public ProductDescriptionPage(RemoteWebDriver driver) throws Exception {
        super(driver);
        Logz.step(getQuantityBox().elementIsDisplayedAndEnabled()?"This is Product Description page":"Product Description Page is not displayed");
    }

    public ProductDescriptionPage(RemoteWebDriver driver,String itemName) throws Exception {
        this(driver);
        this.itemName = itemName;
    }

    //This method will return the commonMethodsPage driver object
    public CommonMethodsPage gotoCommonMethodsPage() throws Exception {
        return new CommonMethodsPage(driver);
    }

    public Generic getTextItemPrice() throws Exception {
        return new Generic(driver,txtItemPrice,"Item Price");
    }
    public Generic getItemsDetails() throws Exception {
        return new Generic(driver,txtDetails,"Details Text");
    }
    public Generic getlblUnitSize() throws Exception {
        return new Generic(driver,lblUnitSize,"First size label under Units Of Measure");
    }
    public List<WebElement> itemDetailsList()throws Exception {
        return getItemsDetails().getWebElements(detailsList,"Item details list");
    }
    public List<WebElement> getItemDescriptiveAttributes()throws Exception {
        return getItemsDetails().getWebElements(txtItemDescriptiveAttributes,"Item Descriptive Attributes");
    }
    private Generic getOrderVerificationModelView()throws Exception {
        return new Generic(driver,orderVerificationModelTitle,"Order VerificationModel View Popup");
    }
    private Button getBtnAcceptOrderVerification()throws Exception {
        return new Button(driver,btnAcceptOrderVerification,"Accept button on Order VerificationModel View Popup");
    }
    private Generic getItemImage() throws Exception{
        return new Generic(driver,imgItem,"Item Image");
    }
    private LinkText getQuoteRequestLink() throws Exception{
        return new LinkText(driver,lnkQuoteRequest,"Quote Request Link");
    }

    public Generic getShopByAreaLink()throws Exception {
        return new Generic(driver,lnkShopBYArea,"");
    }

    public List<WebElement> getLeasableItemList()throws Exception {
        return getShopByAreaLink().getWebElements(txtItemLeasable,"Leasable Item");
    }

    public Generic getTextInvalidMessage() throws Exception {
        return new Generic(driver, txtInvalidMessage, "Invalid Quantity value");
    }
    private Generic getProductSKUNumber() throws Exception {
        return new Generic(driver, spanSKUNumber, "Product SKU number");
    }
    private Generic getItemHeaderTitle() throws Exception {
        return new Generic(driver, By.xpath("//h1[@class='product-details-full-content-header-title']"), "Item Header Title");
    }
    private Generic getTextMeasurements()throws Exception {
        return new Generic(driver,txtMeasurements,"Measurement Details");
    }
    private Generic getAddToCartModelSKUNumber() throws Exception {
        return new Generic(driver, addToCartModelSKUNumber, "Product SKU number on Add To Cart");
    }
    private Generic getProductSizeLabel(String size) throws Exception {
        //return new Generic(driver, By.xpath("//div[@id='custcol_fwh_size-container']//label[contains(text(),\"" + size + "\")]"), "Product Size");
    	return new Generic(driver, By.xpath("//label[contains(text(),\"" + size + "\")]"), "Product Size");
    }
    public Generic getItemQuantity() throws Exception {
        return new Generic(driver, txtItemQuantity, "Item Quantity");
    }
    private Generic getOutOfStockText() throws Exception {
        return new Generic(driver, txtOutOfStock, "OutOfStock");
    }
    private List<WebElement> getImagesPath()throws Exception {
        return getItemHeaderTitle().getWebElements(imgPath,"Specified PDP Page Images");
    }
    private Generic getImageNextIcon()throws Exception {
        return new Generic(driver,imgNextIcon,"Next icon in image");
    }
    private Generic getImagePrevIcon()throws Exception {
        return new Generic(driver,imgPrevIcon,"Previous icon in image");
    }
    private TextBox getSplInstructions() throws Exception {
        return new TextBox(driver, txtSplInstructions, "Special Instructions");
    }
    private Button getAcceptButton() throws Exception {
        return new Button(driver, btnAccept, "Accept");
    }
    private Button getCloseBtn() throws Exception {
        return new Button(driver, btnClose, "Close button");
    }
    public void verifySKUNumberOnAddToCartPopUp(String itemSKU) throws Exception {
        Assert.assertTrue(getAddToCartModelSKUNumber().getControl().getText().toString().equalsIgnoreCase(itemSKU), "SKU Number in the Add To Cart popup did not match with the actual");
    }
    public Button getModelpoupCloseButton() throws Exception{
        return new Button(driver,btnModelpoupClose,"Model Pop Up Close Button");
    }
    public Generic getSuccessAlertText() throws Exception{
        return new Generic(driver,txtSuccessAlert,"Success Quote Request Alert Text");
    }
    private Generic getItemHeaderTitle(String item) throws Exception {
        return new Generic(driver, By.xpath("//h1[@class='product-details-full-content-header-title' and contains(text(),'" + item + "')]"), "Item Header Title");
    }
    private Button getAddToCartLink() throws Exception {
        return new Button(driver, btnAddToCart, "Add to cart Button");
    }
    private Button getAddToQuoteLink() throws Exception {
        return new Button(driver, btnAddToQuote, "Add to quote Button");
    }
    private TextBox getQuantityBox() throws Exception {
        return new TextBox(driver, quantity, "Quantity");
    }
    private Button getReviewLink() throws Exception {
        return new Button(driver, btnReview, "Write a Review Button");
    }
    private Button getAddToWishlistLink() throws Exception {
        return new Button(driver, btnAddToWishlist, "Add to Wishlist Button");
    }
    private LinkText getCreateNewListLink() throws Exception {
        return new LinkText(driver, lnkCreateNewList, "Create a New List Link");
    }
    private Generic getInputNewListName() throws Exception {
        return new Generic(driver, inputNewListName, "New List Name Input Field");
    }
    private Button getCreateAndAddItemButton() throws Exception {
        return new Button(driver, btnCreateAndAddItem, "Create And Add Item Button");
    }
    private Generic getAddToCartModelView() throws Exception {
        return new Generic(driver, addToCartModelView, "Add to cart model view pop up");
    }
    private LinkText getlinkViewCartCheckout() throws Exception {
        return new LinkText(driver, lnkViewCartCheckout, "View Cart and Checkout Button");
    }
    private Button getContinueShoppingBtn() throws Exception {
        return new Button(driver, btnContinueShopping, "Continue Shopping Button");
    }
    private Generic getTextLeasableItem() throws Exception {
        return new Generic(driver, txtLeasableItem, "Leasable Item text in the product description page");
    }
    private Generic getTextItemAddedToProductList() throws Exception {
        return new Generic(driver, txtItemAddedToProduct, "item added to product list");
    }
    private Button getViewCartAndCheckoutBtn() throws Exception {
        return new Button(driver, btnViewCartAndCheckout, "View Cart And Checkout");
    }
    private Generic getChechBoxLeasible() throws Exception {
        return new Generic(driver, chkLeasible, "Item selected to be Leased Checkbox");
    }
    private Generic getPartsAndAccessoriesText() throws Exception {
        return new Generic(driver, txtHeaderPartsAndAccessories, "Parts & Accessories section in PDP");
    }
    private Generic getTextPartsAndAccessories(String partAndAccessoriesName) throws Exception {
        return new Generic(driver, By.xpath("//h3[text() = 'Parts and Accessories']//following-sibling::div//li[@class = 'item-relations-cell']//a/span[text() = '" + partAndAccessoriesName + "']"), "Parts, Accessories names under Parts & Accessories Section");
    }
    private Generic getAddedToCartWindowText() throws Exception {
        return new Generic(driver, headerAddedToCartWindow, "Added To Cart Window");
    }
    private TextBox getInputFieldItemQuantity() throws Exception {
        return new TextBox(driver, txtFieldItemQuantity, "Texbox Item Quantity");
    }
    private Generic getCheckBoxWishListName(String listName) throws Exception {
        return new Generic(driver, By.xpath("//label[contains(text(),'  "+listName+"  ')]/input"), "Wish List Name: '" + listName + "'");
    }
    private SelectBox getOrderTypeValueOption(String option) throws Exception {
		return new SelectBox(driver, By.xpath("//div[@class = 'btn-group open']//a[text() = '" + option + "']"),"SelectOrderType option PreAuth");
	}
    private Generic getOrderTypeSelectionPromptMsg() throws Exception {
        return new Generic(driver, orderTypeSelectionPrompt, "Order Type Selection Prompt Error Message");
    }
    private SelectBox getOrderTypeValue() throws Exception {
        return new SelectBox(driver, orderTypeDropdown, "OrderType Drop Down");
    }

    public String verifyProductSizeAndSKUNumber(String productSize) throws Exception {
    	WebElement prdSkuNumCtrl = getProductSKUNumber().getControl();
        String itemSKU = prdSkuNumCtrl.getText().split("-")[0];
        String arrSize[] = productSize.split("'");
        //getProductSizeLabel(arrSize[0]).getControl().click();
        WebElement prdSize = getProductSizeLabel(productSize).getControl();
        prdSize.click();
        for(int i=0;i<arrSize.length;i++)
        {
            itemSKU = itemSKU+"-"+arrSize[i];
        }
        Common.waitForOperations(driver,spanSKUNumber);
        String actualSKU = getProductSKUNumber().getControl().getText();
        Logz.info(actualSKU);
        Assert.assertEquals(itemSKU,actualSKU,"SKU number didn't matched the product size expected = "+itemSKU+"actual = "+getProductSKUNumber().getControl().getText());
        //if (arrSize.length > 1) {
            //String oldSKU[] = itemSKU.split("-");
            //updatedSKU = oldSKU[0]+"-"+arrSize[0]+"-"+arrSize[1];
           // Assert.assertTrue(prdSkuNumCtrl.getText().toString().equalsIgnoreCase(updatedSKU), "SKU Number did not match after changing the product size");
       // }else{
          //  updatedSKU = itemSKU+"-"+arrSize[0];
           // Assert.assertTrue(prdSkuNumCtrl.getText().toString().equalsIgnoreCase(updatedSKU), "SKU Number did not match after changing the product size");
       // }
        return itemSKU;
    }

    public String getQuoteSuccessMsg() throws Exception{
        String quoteSuccessText = getSuccessAlertText().getControl().getText();
        return quoteSuccessText;
    }

    public void verifyProductSKUNumber(String expectedSKU) throws Exception {
        Assert.assertTrue(getProductSKUNumber().getControl().getText().toString().equalsIgnoreCase(expectedSKU), "SKU Number did not match after click on Add to cart button");
    }

    public void verifyItemImagesInPDP()throws Exception {
    	List<WebElement> imagePathEles = getImagesPath(); 
    	WebElement imgNextIconCtrl = getImageNextIcon().getControl();
        Assert.assertTrue(imagePathEles.size()>0,"No images found in specified PDP");
        Logz.step("Images are found in PDP");
        if(imagePathEles.size()>0) {
            Logz.step("No of images found in PDP "+imagePathEles.size());
            if(imagePathEles.size() > 1) {
                Assert.assertTrue(imgNextIconCtrl.isDisplayed(), "Next icon is not displayed");
                Assert.assertTrue(getImagePrevIcon().getControl().isDisplayed(), "Previous icon is not displayed");
            }
        }
        imgNextIconCtrl.click();
        Logz.step("Clicked on Next Icon");
    }

    // This method is used to enter value for Item Quantity
    public String enterValueForItemQuantity(String ItemQuantity) throws Exception {
    	TextBox txtInputFldQty = getInputFieldItemQuantity();
    	txtInputFldQty.setText(ItemQuantity);
        getProductSKUNumber().getControl().click();
        Logz.step("Quantity is set");
        String Qnty = txtInputFldQty.getControl().getAttribute("value");
        Logz.step("Quantity is "+quantity);
        return Qnty;
    }

    public ProductDescriptionPage clickAddToCartButton() throws Exception {
    	getAddToCartLink().click();
        if (getItemHeaderTitle().getControl().getText().startsWith("Refrigerated")){
            commonMethodsPage = gotoCommonMethodsPage();
            if (Common.isElementPresent(driver,orderVerificationModelTitle)){
                getBtnAcceptOrderVerification().click();
            }
        }
        if (isAddtoCartModelViewDisplayed()) {
            Logz.step("Clicked on Add to Cart");
        }else{
            Logz.step(" Add to Cart button is not clicked ");
             }
        return new ProductDescriptionPage(driver);
    }

    private boolean isAddToCartBtnEnabled() throws Exception {
        return getAddToCartLink().elementIsDisplayedAndEnabled();
    }

    public ProductDescriptionPage verifyOrderTypeSelectionPromptMsg() throws Exception {
    	String text = getOrderTypeSelectionPromptMsg().getControl().getText();
		Logz.step("text printed as: " + text);
		Assert.assertEquals(text, BaseTest.getStringfromBundleFile("orderTypeSelectionPrompt"),"Order type prompt message is not displayed");
		Logz.step("Order type prompt message is displayed");
        return new ProductDescriptionPage(driver);
    }

    public ProductDescriptionPage verifyAddToCartBtnEnabled() throws Exception {
        Assert.assertTrue(isAddToCartBtnEnabled());
		Logz.step("Add to cart button is now Displayed");
        return new ProductDescriptionPage(driver);
    }

    public ProductDescriptionPage verifyAddToCartBtnDisabled() throws Exception {
        Assert.assertFalse(isAddToCartBtnEnabled());
        Logz.step("Add to cart button is disabled");
        return new ProductDescriptionPage(driver);
    }

    public ProductDescriptionPage selectOrderTypeValue(String orderType) throws Exception {
    	try {
    		Common.waitForOperations(driver, orderTypeDropdown);
    		SelectBox orderTypeVal = getOrderTypeValue();
    		WebElement orderTypeCtrl = orderTypeVal.getControl();
			Assert.assertTrue(orderTypeCtrl.isDisplayed()," Order Type not shown");
			orderTypeCtrl.click();
			Logz.step("Clicked on SelectOrderType drop down link");
			getOrderTypeValueOption(orderType).getControl().click();
			Logz.step("Selected orderType : " + orderType);
		} catch (Exception e) {
			Logz.error("Did not find the Order type drop down to select the order type value");
		}
        return new ProductDescriptionPage(driver);
    }

    public boolean isAddtoCartModelViewDisplayed() throws Exception {
    	try {
			new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(getlinkViewCartCheckout().getControl()));
			Logz.info("Button is available");
			return true;
			
		} catch (Exception e) {
			return false;
		}
        /*boolean flag = false;
        try {
            if (getAddToCartModelView().getControl().isDisplayed()){
                Logz.step("Add to Cart Model View Pop up Displayed");
                flag = true;
            }
        } catch (Exception e) {
            Logz.error("Add to Cart Model View Pop up did't get Displayed");
            flag = false;
        }
        return flag;*/
    }

    public void verifyItemQuantityOnAddedToCartWindow(String qnty) throws Exception {
        Assert.assertTrue(getAddedToCartWindowText().getControl().isDisplayed(), "Added to Cart window did not displayed after click on Add to Cart");
        Logz.step("Added to Cart window is displayed");
        Assert.assertTrue(qnty.equals(getItemQuantity().getControl().getText().toString()),"Quantity is not equal");
        Logz.step("Quantity is equal");
    }

    public boolean isTextLeasableItemDisplayed() throws Exception {
        List<WebElement> li = getLeasableItemList();
        boolean flag = false;
        if(li.size()>0)
        {
            flag = true;
            Logz.step("Leasable Item");
        }else if(li.size()==0)
        {
            flag = false;
            Logz.step("Non Leasable Item");
        }
        return flag;
    }

    public void verifyItemSelectedToBeLeasedCheckboxDisplayed() throws Exception {
        Assert.assertTrue(isItemSelectedToBeLeasedCheckboxDisplayed(),"ITEM Selected to be Leased Checkbox is not displayed");
        Logz.step("ITEM SELECTED TO BE LEASED checkbox is displayed" );
    }

    public void verifyItemSelectedToBeLeasedCheckboxNotDisplayed() throws Exception {
        Assert.assertFalse(isItemSelectedToBeLeasedCheckboxDisplayed(),"ITEM Selected to be Leased Checkbox is displayed");
        Logz.step("ITEM SELECTED TO BE LEASED checkbox is not displayed" );
    }

    public boolean isTextYouAddedThisItemToYourProductListDisplayed() throws Exception {
        boolean flag = false;
        try {
            if (getTextItemAddedToProductList().getControl().isDisplayed()) {
                Logz.step("Good! You added this item to your product list");
                flag = true;
            }
        } catch (Exception e) {
            Logz.error("Leasable Item text is not Displayed in the product description page");
            flag = false;
        }
        return flag;
    }

    public ProductDescriptionPage clickAddToQuoteButton() throws Exception {

        Common.waitForElementToBeDisplayed(driver,btnAddToQuote);
        getAddToQuoteLink().click();
        Common.waitForLoadingCompletion(driver);
        getAddToQuoteLink().click();
        Logz.step("Clicked on Add to Quote");
        return new ProductDescriptionPage(driver);
    }



    public void setQunatityBox(String qty) throws Exception {
        getQuantityBox().setText(qty);
        getInputFieldItemQuantity().getControl().sendKeys(Keys.TAB);
        Logz.step("Quantity has been set");
    }

    public ProductReviewPage clickReviewButton() throws Exception {
        getReviewLink().click();
        Logz.step("Clicked on Write a Review");
        return new ProductReviewPage(driver);
    }

    //Function to click on Add to WishList button and Select the Name of the List(List name is Parameter)
    public void addProductToWishList(String listName) throws Exception {
        getAddToWishlistLink().click();
        Logz.step("Clicked on add to Wishlist Button");
        WebElement chkWishList = getCheckBoxWishListName(listName).getControl(); //has to be replaced by MAF methods
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOf(chkWishList));
        chkWishList.click();
        // will uncomment later as I may use this
       // if(!getCheckBoxWishListName(listName).getControl().isSelected())
       // {
       //     chkWishList.click();
      //  }
        Logz.step("Clicked on Selected Wishlist" + listName);
        Assert.assertEquals((getSuccessAlertText().getControl().getText()).trim(), (BaseTest.getStringfromBundleFile("wishListSuccessAlert")).trim());
        getSuccessAlertText().getControl().click();
    }

    //Function to click on Add to WishList button and Create new List(List name is Parameter)
    public void clickAddToWishlistButtonAndCreateList(String listName) throws Exception {
        getAddToWishlistLink().click();
        Logz.step("Clicked on Wishlist Link");
        getCreateNewListLink().click();
        //String randomNumber=RandomStringUtils.randomNumeric(2);
        //String newListName =
        getInputNewListName().getControl().sendKeys(listName);
        getCreateAndAddItemButton().click();
        Assert.assertEquals((getSuccessAlertText().getControl().getText()).trim(), (BaseTest.getStringfromBundleFile("wishListSuccessAlert")).trim());
        //return newListName;
    }

    public ShoppingCartPage clickOnViewCartCheckoutButton() throws Exception {
        getlinkViewCartCheckout().getControl().click();
        Logz.step("Clicked on View Cart and Checkout Button");
        Common.waitForLoadingCompletion(driver);
        return new ShoppingCartPage(driver);
    }

    public void clickOnContinueShoppingButton() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.elementToBeClickable(btnContinueShopping));
        getContinueShoppingBtn().getControl().click();
        Logz.step("Clicked on Continue Shopping Button");
    }

    public boolean isItemSelectedToBeLeasedCheckboxDisplayed() throws Exception {
        boolean flag = false;
        try {
            if (getChechBoxLeasible().getControl().isDisplayed()) {
                //Logz.step("check box is displayed");
                flag = true;
            }
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public ShoppingCartPage clickViewCartAndCheckoutButton() throws Exception {
        getViewCartAndCheckoutBtn().click();
        Logz.step("Clicked on ViewCart And Checkout Button");
        return new ShoppingCartPage(driver);
    }

    public ShoppingCartPage clickViewCartAndAccept() throws Exception {
        clickOnAcceptBtn();
        getViewCartAndCheckoutBtn().click();
        Logz.step("Clicked on ViewCart And Checkout Button");
        return new ShoppingCartPage(driver);
    }

    public void verifySearchItem(String searchItem) throws Exception {
        Assert.assertTrue(getItemHeaderTitle(searchItem).elementIsDisplayedAndEnabled(), "ITEM Title Mismatch");
        Logz.step("Item is matching");
    }

    public ProductDescriptionPage scrollToPartsAndAccessoriesView() throws Exception {
        commonMethodsPage=gotoCommonMethodsPage();
        commonMethodsPage.scrollIntoElementView(getPartsAndAccessoriesText().getControl());
        return new ProductDescriptionPage(driver);
    }

    private boolean isPartAndAccessoriesPresent(String partName) throws Exception {
        boolean flag = false;
        if (getTextPartsAndAccessories(partName).getControl().isDisplayed()) {
            flag = true;
            Logz.step("'" + partName + "' : Is Displayed in the Parts and Accessories section");
        } else {
            Logz.error("'" + partName + "' : Is not displayed in the Parts and Accessories section");
        }
        return flag;
    }

    public void verifyPartNamesUnderPartsAndAccessoriesSection() throws Exception {
        Assert.assertTrue(isPartAndAccessoriesPresent("'" + BaseTest.getStringfromBundleFile("partsAndAccessoriesName") + "'"));
    }

    public void verifyInvalidQuantityMsg() throws Exception {
        Assert.assertTrue(getTextInvalidMessage().getControl().isDisplayed());
        Logz.step("Invalid Quantity Value message is displayed");
    }

   public ProductDescriptionPage verifyItemDescriptiveAttributes()throws Exception{
       List<WebElement> elements = getItemDescriptiveAttributes();
       Assert.assertEquals(elements.get(0).getText(),BaseTest.getStringfromBundleFile("electrical"));
       Assert.assertEquals(elements.get(1).getText(),BaseTest.getStringfromBundleFile("measurements"));
       Assert.assertEquals(elements.get(2).getText(),BaseTest.getStringfromBundleFile("documents"));
       Logz.step("Item Descriptive Attributes are verified");
       return new ProductDescriptionPage(driver);
   }

    public void verifyOutOfStockMsgInPDP() throws Exception {
        Assert.assertTrue(getOutOfStockText().getControl().isDisplayed(),"Out of Stock message is not displayed");
        Logz.step("Out Of Stock message is displayed in PDP");
    }

    public ProductDescriptionPage enterSpecialInstructions(String instruction) throws Exception {
        getSplInstructions().setText(instruction);
        Logz.step("entered Special Instructions");
        return new ProductDescriptionPage(driver);
    }

    public void verifyMeasurementDetails(String num)throws Exception {
        String text=getTextMeasurements().getControl().getText();
        Assert.assertTrue(text.contains(num),"Selected Measurement is not there");
    }

    public ProductDescriptionPage verifyItemPrice() throws Exception{
       Assert.assertTrue(getTextItemPrice().elementIsDisplayedAndEnabled(),"Item Price is not Displayed");
       Logz.step("Item Price is Displayed");
       return new ProductDescriptionPage(driver);
    }

    public void verifyItemDetails() throws Exception{
        Assert.assertTrue(getItemsDetails().elementIsDisplayedAndEnabled(),"Item Details is not Displayed");
        Logz.step("Item Details are Displayed in PDP");
    }

    public ProductDescriptionPage validateItemName(String itemName)throws Exception {
        String text = getItemHeaderTitle().getControl().getText();
        Assert.assertEquals(text,itemName,"Item Name is not displayed in the header");
        Logz.step("Item Name is displayed in the Header");
        return new ProductDescriptionPage(driver);
    }

    public void verifyItemName() throws Exception{
        String text = getItemHeaderTitle().getControl().getText();
        Assert.assertFalse(text.contains(BaseTest.getStringfromBundleFile("counter")),"Counter is displayed in the header");
        Logz.step("Counter is not displayed in the Header");
        List<WebElement> details = itemDetailsList();
        int count = 0;
        for(WebElement itemDetail : details)
        {
            if((itemDetail.getText()).contains(BaseTest.getStringfromBundleFile("counter")))
            {
                Logz.step("Counter is present in Item Details");
                count = 1;
                break;
            }
        }
        Assert.assertEquals(count,1,"Counter is not present in Item Details");
    }

    public void verifyItemNumber() throws Exception {
        String itemNumber = BaseTest.getStringfromBundleFile("item_number");
        Assert.assertEquals(getProductSKUNumber().getControl().getText(),itemNumber,"Item Number is not matching");
        Logz.step("Item Number is matching");
    }
    public void clickOnAddToCart() throws Exception {
        getAddToCartLink().click();
        Logz.step("Clicked On AddOnCart button");
    }
    public void clickOnCloseInAddedToCartPopUp() throws Exception {
        getCloseBtn().click();
        Logz.step("Clicked Close button in addedToCart popup");
    }

    public HomePage gotoHomePagePage() throws Exception {
        return new HomePage(driver);
    }

    public void clickOnAcceptBtn() throws Exception{
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.visibilityOfElementLocated(btnAccept));

        getAcceptButton().click();
        Logz.step("Clicked On accept button");
    }

    public String increaseQuantityUsingArrow() throws Exception {
    	WebElement txtCtrl = getInputFieldItemQuantity().getControl();
    	txtCtrl.sendKeys(Keys.ARROW_UP);
        Logz.step("Clicked On Arrow for increasing product quantity");
        String quantity = txtCtrl.getAttribute("value");
        Logz.step("Quantity is "+quantity);
        return quantity;
    }

    public ProductListPage navigateProductListPage() throws Exception{
        driver.navigate().to(BaseTest.getStringfromBundleFile("shopMenu"));

        return new ProductListPage(driver);
    }

    public RequestQuotePage clickOnQuoteRequest() throws Exception{
        WebDriverWait wait = new WebDriverWait(driver, BaseTest.EXPLICIT_WAIT_TIME);
        wait.until(ExpectedConditions.elementToBeClickable(lnkQuoteRequest));
        getQuoteRequestLink().click();
        Logz.step("Clicked on Quote Request Link");
        return new RequestQuotePage(driver);
    }

    public void verifyItemTitle(String Item) throws Exception{
        String title = getItemHeaderTitle().getControl().getText();
        Assert.assertEquals(title,Item,"Did not click on selected item");
        Logz.step("Successfully verified the item title.");
        //return this;
    }
}
