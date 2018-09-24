package pages;

import base.pages.browser.BrowserBasePage;
import base.test.BaseTest;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import pages.Cart.*;
import pages.MyAccountOverview.MyListPage;
import pojos.Home;
import util.Common;
import utils.Logz;
import java.text.SimpleDateFormat;
import java.util.*;
import org.openqa.selenium.By;

public class CommonMethodsPage<T extends RemoteWebDriver> extends BrowserBasePage
{
    public ProductDescriptionPage pdpPage ;
    public ProductListPage productListPage;
    public SwitchRestaurantPage switchRestaurantPage;
    public HomePage homePage;
    public ProductComparisonPage productComparisonPage;
    public ShoppingCartPage shoppingCartPage ;
    public CheckoutPage checkoutPage;
    public MyListPage myListpage;
    public RemoteWebDriver remoteWebDriver;

    @Autowired
    public Home home;

    public CommonMethodsPage(RemoteWebDriver driver) throws Exception {
        super(driver);
    }

    //We can remove this function once the Order type selection issue is fixed, we have this same function present under Home page
    public ProductDescriptionPage selectOrderType(String orderType) throws Exception {
        try {
            if (Common.isElementPresent(driver,homePage.drpSelectOrdertype)) {
                homePage.getSelectOrdertypeDropdown().getControl().click();
            } else if (Common.isElementPresent(driver,homePage.drpOrdertype)) {
                homePage.getOrdertypeDropdown().getControl().click();
            }
            homePage.getOrderTypeValue(orderType).getControl().click();
            Logz.step("Selected orderType : " + orderType);
        } catch (Exception ex) {
            Logz.error("Did not find the Order type drop down to select the order type value");
            Logz.error(ex.getMessage());
            throw new Exception(ex);
        }
        return new ProductDescriptionPage(driver);
    }

    public void addProductsToCart(int noOfProducts) throws Exception{
        productListPage = new ProductListPage(driver);
        List<WebElement> prdLstEles = productListPage.getProducts();
        String sItemName = "";
Logz.info("Product List Size:::" + prdLstEles.size());
        if (prdLstEles.size() > noOfProducts) {
                for (int i = 0; i < noOfProducts; i++) {
                    sItemName = prdLstEles.get(i).getText().replace("\\","");
                    prdLstEles.get(i).click();
                    //pdpPage = productListPage.clickOnItemName(sItemName);
                    //Commented Code to be deleted once multiple rounds of testing is complete
                    //pdpPage = productListPage.clickOnItemName(((WebElement)prdLstEles.get(i)).getText()).replace("X","x").replace("W","w"));
                if(pdpPage==null) {
                    pdpPage = new ProductDescriptionPage(driver);
                }

                //Validate If Matrix Products are clicked. Select Size from List
                if (sItemName.startsWith("LTR") || sItemName.startsWith("CHOICE") || sItemName.startsWith("FOOD"))
                {
                   if(Common.isElementPresent(driver,pdpPage.lblUnitOfMeasure))
                   {
                            pdpPage.getlblUnitSize().getControl().click();
                   }
                }

                pdpPage.clickAddToCartButton();
                Assert.assertTrue(pdpPage.isAddtoCartModelViewDisplayed());
                if (i < noOfProducts-1)
                {
                    pdpPage.clickOnContinueShoppingButton();
                    pdpPage.navigateProductListPage();
                    //Since User is navigated back to Product List Page, List would be stale.
                    //Get List again to Select Next Product From List of Products
                    prdLstEles = productListPage.getProducts();
                }
                else {
                    Logz.step("User is able to add multiple items to the cart");
                    pdpPage.clickOnViewCartCheckoutButton();
                    Common.waitForLoadingCompletion(driver);
                }
            }
        }else{
            Logz.error("No of Products: '"+noOfProducts+"' which you are trying to Add to Cart is grater than the products: '"+prdLstEles.size()+"' which are available in the Product listing Page");
        }
    }

    public String[] addProductsToQuoteAndClickOnRequestQuoteBtn(int noOfProducts) throws Exception{
        String arrProductsAddedToQuote[] = new String[noOfProducts];
        int j = 0;
        productListPage=new ProductListPage(driver);
        List<WebElement> prdLstEles = productListPage.getProducts();
        if (prdLstEles.size() > noOfProducts) {
            for (int i = 0; i < noOfProducts; i++) {
                String itemName = ((WebElement)prdLstEles.get(i)).getText();
                arrProductsAddedToQuote[j++] = itemName;
                pdpPage = productListPage.clickOnItemName(itemName);
                pdpPage.clickAddToQuoteButton();
                Logz.step("Actual" +pdpPage.getQuoteSuccessMsg().trim());
                Logz.step("Expected" +itemName+" "+ BaseTest.getStringfromBundleFile("quoteRequestSuccess"));
                Assert.assertTrue(pdpPage.getQuoteSuccessMsg().trim().equalsIgnoreCase(itemName+" "+BaseTest.getStringfromBundleFile("quoteRequestSuccess")));
                homePage = new HomePage(driver);
                if (i < noOfProducts) homePage.navigateProductListPage();
                else homePage.clickRequestAQuote();
            }
        }else{
            Logz.error("No of Products: '"+noOfProducts+"' which you are trying to Add to Quote is grater than the products: '"+prdLstEles.size()+"' which are available in the Product listing Page");
        }
        return arrProductsAddedToQuote;
    }

    // Click on Switch link
    public SwitchRestaurantPage clickOnSwitchLink(HomePage homePage) throws Exception {
        //homePage = new HomePage(driver);
        homePage.getSwitchStore().getControl().click();
        return new SwitchRestaurantPage(driver);
    }

    public void addSpecificItemToQuoteAndClickOnRequestQuoteBtn(String itemName) throws Exception{
        homePage = new HomePage(driver);
        productListPage = homePage.searchForSpecificItem(itemName);
        if(((WebElement)productListPage.getProducts().get(0)).getText().toString().equalsIgnoreCase(itemName)){
          pdpPage = productListPage.clickOnItemName(itemName);
          pdpPage.enterSpecialInstructions(BaseTest.getStringfromBundleFile("specialInstruction"));
          pdpPage.clickAddToQuoteButton();
//          Assert.assertTrue(pdpPage.getQuoteSuccessMsg().trim().equalsIgnoreCase(BaseTest.getStringfromBundleFile("quoteRequestSuccess")));
            Assert.assertTrue(pdpPage.getQuoteSuccessMsg().trim().equalsIgnoreCase(itemName + BaseTest.getStringfromBundleFile("quoteRequestSuccess")));
        }else{
          Logz.error("Did not find the Special Order Item: '"+itemName+"' which we are searching for");
        }
    }

//  Verifying whether MyList is empty or not, if it is not empty we are making MyList to empty
    public void makeMyListItemsToEmpty() throws Exception{
        homePage = new HomePage(driver);
        myListpage = homePage.clickHeaderMenuMyAccountLinkAndMyList(BaseTest.getStringfromBundleFile("wishListMyList"));
        if (! myListpage.isMyListEmpty())
            myListpage.verifyRemoveItemsFromWishlist();
    }

    //  Verifying whether MyList is empty or not, if it is not empty we are making MyList to empty
    public void verifyWishListItemsPresentInTheList() throws Exception{
        //homePage = new HomePage(driver);
        myListpage = homePage.clickHeaderMenuMyAccountLinkAndMyList(BaseTest.getStringfromBundleFile("wishListMyList"));
        Assert.assertTrue(myListpage.isMyListEmpty(),"User is unable to view the list of items included in the Wishlist");
        Logz.step("User is able to view the list of items included in the Wishlist");

    }

    public void isCartHasSameItemsForDifferentStores(ShoppingCartPage shoppingCartPage, HomePage homePage, String sStoreNM) throws Exception{
        String[] cartItemNameBeforeSwitch = shoppingCartPage.getCartItemNames();
        switchRestaurantPage = clickOnSwitchLink(homePage);
        homePage = switchRestaurantPage.switchRestaurant(sStoreNM);//home.getStoreNumber()
        Common.waitForLoadingCompletion(driver);
        shoppingCartPage = homePage.navigateToShoppingCartPage();
/*        String text = shoppingCartPage.getPageNotFound().getText();
        if (text.contains("Shopping")) {
            shoppingCartPage.getShoppingCart().click();
            shoppingCartPage.getViewCart().click();
        }*/
        Common.waitForLoadingCompletion(driver);
        String[] cartItemNameAfterSwitch = shoppingCartPage.getCartItemNames();
        Assert.assertNotEquals(cartItemNameBeforeSwitch,cartItemNameAfterSwitch,"Both the Stores has Same Items in the Item Cart");
        Logz.step("Both the Stores does not have Same Items in the Item Cart");
    }

    //Scroll till element.
    public void scrollIntoElementView(WebElement element) throws Exception{
        JavascriptExecutor je = (JavascriptExecutor) driver;
        je.executeScript("arguments[0].scrollIntoView(true);",element);
    }

    public <E extends Comparable<E>> E[] sortedResults(E a[],String sortType)
    {
        int len=a.length;
        E temp;
        if(sortType.equalsIgnoreCase("Asc"))
        {
            for (int i = 0; i < len; i++) {
                for (int j = i + 1; j < len; j++) {
                    if (a[i].compareTo(a[j]) > 0) {
                        temp = a[i];
                        a[i] = a[j];
                        a[j] = temp;

                    }
                }
            }
        }
        else if(sortType.equalsIgnoreCase("Desc"))
        {
            for (int i = 0; i < len; i++) {
                for (int j = i + 1; j < len; j++) {
                    if (a[i].compareTo(a[j]) < 0) {
                        temp = a[i];
                        a[i] = a[j];
                        a[j] = temp;

                    }
                }
            }
        }
        return a;
    }

    public List<String> sortDates(String sortType,String[] dates)throws Exception
    {
        Date[] sortedDates = new Date[dates.length];
        String[] finalDates = new String[dates.length];
        SimpleDateFormat sdf = new SimpleDateFormat("M-d-yyyy");
        for(int i=0;i<dates.length;i++)
        {
            dates[i]= dates[i].replace("/","-");
            sortedDates[i] = sdf.parse(dates[i]);
        }
        switch (sortType)
        {
            case "ASC":
                Arrays.sort(sortedDates);
                break;

            case "DESC":

                Arrays.sort(sortedDates);
                List<Date> li = Arrays.asList(sortedDates);
                Collections.reverse(li);
                sortedDates = li.toArray(new Date[li.size()]);
                break;
        }

        for(int i=0;i<sortedDates.length;i++)
        {
            finalDates[i] = sdf.format(sortedDates[i]);
            finalDates[i]= finalDates[i].replace("-","/");
        }
        return Arrays.asList(finalDates);
    }

    public boolean verifySortByDate(String sortType,By xpath)throws Exception{
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       List<WebElement> listProductDate = driver.findElements(xpath);
        Boolean flag= false;
        if(sortType.equalsIgnoreCase("Sort By Date")) {
            for (int i = 0; i < listProductDate.size(); i++) {
                for (int j = i+1; j > i; j--) {
                    if ((sdf.parse(listProductDate.get(i).getText()).compareTo(sdf.parse(listProductDate.get(i).getText())))>= 0)
                    {
                        Logz.step(listProductDate.get(i).getText() +"is latest date");
                        Logz.step("Dates sorted latest to old");
                        flag = true;
                    }
                    else {
                        Logz.step(listProductDate.get(i).getText() +"is Old  date");
                        Logz.step("Dates are not sorted latest to old");
                        flag=false;
                    }
                }
            }
        }
        return flag;
    }

    public List readValuesToArray(By xpath,String splitValue) {
        List<String> list = new ArrayList<String>();
        List<WebElement> elements = remoteWebDriver.findElements(xpath);
        for (WebElement el : elements) {
            if (splitValue == "$") {
                String[] s = el.getText().split("\\$");
                list.add(s[1]);
            } else list.add(el.getText().toString());
        }
        return list;
    }

    //This method is used for calculating Order Limit(PreAuth/MaxPreAuth) of Single or Multi restaurant user with No Litigation Store
    public Double restaurantsWithNoLitigation(String OrderLmtType, int noOfItems) throws Exception {
        Double orderAmount=0.0, noOfRestaurants;
        //To convert the Integer into Double(Since Direct Up casting is not possible in Java)
        Double test =1.0;
        switchRestaurantPage = new SwitchRestaurantPage(driver);
        noOfRestaurants = noOfItems*test;
        if (OrderLmtType.equalsIgnoreCase("PreAuthAutoApprove")){
            Double preAuthOrderAmount ;

            if (Common.isElementPresent(driver,switchRestaurantPage.lnkSelectRestaurant)) preAuthOrderAmount = (noOfRestaurants * 1000);
            else preAuthOrderAmount = 1000.00;
            switchRestaurantPage = new SwitchRestaurantPage(driver);
            if (Common.isElementPresent(driver,switchRestaurantPage.lnkSelectRestaurant))
                preAuthOrderAmount = (noOfRestaurants * 10);
            else
                preAuthOrderAmount = 10.00;
            orderAmount = preAuthOrderAmount;
        }else if(OrderLmtType.equalsIgnoreCase("MaxPreAuth")){
            Double maxPreAuthOrderAmount ;
            if (Common.isElementPresent(driver,switchRestaurantPage.lnkSelectRestaurant)) {
                maxPreAuthOrderAmount = (noOfRestaurants * 12);
            }
             else {
                maxPreAuthOrderAmount = 12.00;
            }
            orderAmount = maxPreAuthOrderAmount;
        }
        return orderAmount;
    }

    //    This method is used for calculating Order Limit(PreAuth/MaxPreAuth) of Single or Multi restaurant user with One Litigation Store
//   Please pass OrderLmtType = null if the login store is under litigation
    public Double restaurantsWithOneLitigation(String storeType,String OrderLmtType,int noOfItems) throws Exception {
        Double preAuthOrderAmountLimit, maxPreAuthOrderAmountLimit, litigationOrderAmountLimit, orderAmount=0.00, noOfRestaurants;
        //To convert the Integer into Double(Since Direct Up casting is not possible in Java)
        Double test =1.0;
        switchRestaurantPage = new SwitchRestaurantPage(driver);

        if (storeType.equalsIgnoreCase("nonLitigationStore") && Common.isElementPresent(driver,switchRestaurantPage.lnkSelectRestaurant))
        {
            noOfRestaurants = noOfItems*test;
            if (OrderLmtType.equalsIgnoreCase("PreAuthAutoApprove")){
                preAuthOrderAmountLimit = (noOfRestaurants * 1000);
                orderAmount = preAuthOrderAmountLimit;
            }else if(OrderLmtType.equalsIgnoreCase("MaxPreAuth"))
            {
                maxPreAuthOrderAmountLimit = ((noOfRestaurants) * 1250);
                orderAmount = maxPreAuthOrderAmountLimit;
            }
        }
        else if(storeType.equalsIgnoreCase("litigationStore"))
        {
            litigationOrderAmountLimit = 500.00;
            orderAmount = litigationOrderAmountLimit;
        }
        return orderAmount;
    }

    //This method is used for adding products to cart based on given order limit
    public ShoppingCartPage addToCartBasedOnOrderLimit(Double orderLimit, String headerMenuType)throws Exception
    {
        Double totalPrice=0.0;
        int itemQty=0;
        productListPage = new ProductListPage(driver);
        if(orderLimit >= 3000.00){
            productListPage.selectPriceSorting(BaseTest.getStringfromBundleFile("sortByPriceHighToLow"));
        }
        int size=productListPage.getProductListItems().size();
        for (int i = 0; i < size ; i++)
        {
            String itemName = ((WebElement) productListPage.getProductListItems().get(i)).getText().toString().replace("X","x").replace("W","w");
            if (productListPage.isPriceLabelPresent(itemName))
            {
                Double itemPrice = Double.parseDouble(productListPage.getSpecificItemPrice(itemName).getControl().getText().substring(1).replaceAll(",",""));
                pdpPage = productListPage.clickOnItemName(itemName);
                itemQty = (int) Math.floor(orderLimit);
                pdpPage.setQunatityBox(Integer.toString(itemQty));
                // This code is to select the first unit of measure of an Item
                if ((itemName.startsWith("LTR")) || (itemName.startsWith("CHOICE")) || (itemName.startsWith("FOOD")))
                {
                    if(Common.isElementPresent(driver,pdpPage.lblUnitOfMeasure))
                    {
                        pdpPage.getlblUnitSize().getControl().click();
                    }
                }
                pdpPage.clickAddToCartButton();
                Assert.assertTrue(pdpPage.isAddtoCartModelViewDisplayed());
                pdpPage.clickOnViewCartCheckoutButton();
                break;

            }
        }
        return new ShoppingCartPage(driver);
    }

    public ShoppingCartPage addToCartBasedOnPreAuthAndMaxLimit(Double preAuthLimit,Double maxPreAuthLimit, String headerMenuType )throws Exception {
        Double value=(preAuthLimit+maxPreAuthLimit)/2;
        return addToCartBasedOnOrderLimit(value,headerMenuType);
    }

    public ShoppingCartPage addToCartOverMaxPreAuthLimit(Double maxPreAuthLimit, String headerMenuType)throws Exception {
        Double value=maxPreAuthLimit;
        addToCartBasedOnOrderLimit(value,headerMenuType);
        return new ShoppingCartPage(driver);
    }
    
    public void verifySelectedItemsInGoToCompare() throws Exception {
    	final int MAX_SIZE = 4;
        productListPage = new ProductListPage(driver);
        List<WebElement> prdLstPageEles = productListPage.getListAddToCompare();
        Assert.assertTrue(prdLstPageEles.size() >= MAX_SIZE);
        String [] itemsToCompare = new String[MAX_SIZE];
        int j=0;
        for (int i = 0; i < MAX_SIZE; i++) {
            ((WebElement)prdLstPageEles.get(i)).click();
            itemsToCompare[j++] = ((WebElement)productListPage.getProductListItems().get(i)).getText();
            if (i > 2) {
                productListPage.getGoToCompareLink().elementIsDisplayedAndEnabled();
                productListPage.getGoToCompareLink().click();
            }
            Logz.step("Selected Add To Compare checkbox for Item '" + i + "'");
        }
        productComparisonPage = new ProductComparisonPage(driver);
        productComparisonPage.verifyItemOptionsOnComparisonPage(itemsToCompare);
    }

    public HomePage gotoHomePage() throws Exception {
        return new HomePage(driver);
    }
}
