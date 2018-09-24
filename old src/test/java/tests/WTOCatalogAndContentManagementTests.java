package tests;

import base.EOSWebContext;
import base.test.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.testng.annotations.Test;
import pages.Cart.*;
import pages.CommonMethodsPage;
import pages.HomePage;
import pages.LoginPage;
import pages.MyAccountOverview.MyListPage;
import pages.ShopByArea.ShopByAreaPage;
import pages.SwitchRestaurantPage;
import pojos.OrderType;
import utils.Logz;
import pojos.Home;
import pojos.Login;

@ContextConfiguration({"classpath:/EOSBeans.xml","classpath:/LoginBeans.xml","classpath:/HomePageBeans.xml","classpath:/OrderTypeBeans.xml"})
@TestExecutionListeners(inheritListeners = false, listeners =
        {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})

public class WTOCatalogAndContentManagementTests extends EOSWebContext {
    public LoginPage loginPage;
    public HomePage homePage;
    public ShopByAreaPage shopByAreaPage;
    public ShoppingCartPage shoppingCartPage;
    public ProductListPage productListPage;
    public CheckoutPage checkoutPage;
    public PaymentPage paymentPage;
    public ReviewYourOrderPage reviewYourOrderPage;
    public SearchResultsPage searchResultsPage;
    public SwitchRestaurantPage switchRestaurantPage;
    public CommonMethodsPage commonMethodsPage;
    public ProductDescriptionPage productDescriptionPage;
    public MyListPage myListPage;

    @Autowired
    public Login login;
    @Autowired
    public Home home;
    @Autowired
    public OrderType orderTypePreAuth;

    //TFS ID: 148461
    //Verify information in Item Descriptive Attributes against the information in tables on EOS website
    @Test
    public void verifyItemDescriptiveAttributesAgainstTheInformationInTables_148461() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(orderTypePreAuth.getOrderType());
            productListPage = homePage.navigateProductListPage();
            productDescriptionPage = productListPage.clickOnItemName(BaseTest.getStringfromBundleFile("itemWithDescriptiveAttributes"));
            productDescriptionPage.verifyItemTitle(BaseTest.getStringfromBundleFile("itemWithDescriptiveAttributes"));
            productDescriptionPage.verifyItemDescriptiveAttributes();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 148485
    //Verify user is able to view areas and corresponding thumbnails
    @Test
    public void verifyUserIsAbleToViewAreasAndCorrespondingThumbnails_148485() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            shopByAreaPage = homePage.clickShopByArea();
            shopByAreaPage.verifyThumbnailsForAreas();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 148498
    //Verify that sku and image change is maintained in all the pages from add to cart page to Checkout Review page
    @Test
        public void verifyThatSkuAndImageChangeIsMaintained_148498() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(orderTypePreAuth.getOrderType());
            homePage.removeAllItemsFromCart();
            productListPage = homePage.navigateProductListPage();
            productDescriptionPage=productListPage.clickOnItemName(BaseTest.getStringfromBundleFile("matrixItem"));
            productDescriptionPage.verifyItemTitle(BaseTest.getStringfromBundleFile("matrixItem"));
            productDescriptionPage.verifyProductSizeAndSKUNumber(BaseTest.getStringfromBundleFile("matrixSize1"));
            String updatedSKU = productDescriptionPage.verifyProductSizeAndSKUNumber(BaseTest.getStringfromBundleFile("matrixSize2"));
            //productDescriptionPage.verifyProductSizeAndSKUNumber(BaseTest.getStringfromBundleFile("matrixSize3"));
            //String updatedSKU = productDescriptionPage.verifyProductSizeAndSKUNumber(BaseTest.getStringfromBundleFile("matrixSize4"));
            productDescriptionPage.clickAddToCartButton();
            shoppingCartPage = productDescriptionPage.clickViewCartAndCheckoutButton();
            shoppingCartPage.verifySKUNumberonCheckoutPage(updatedSKU);
            checkoutPage = shoppingCartPage.clickProceedToCheckout();
            checkoutPage.verifyProductSKUInCheckoutPage(updatedSKU);
            checkoutPage.selectDeliveryMethod(BaseTest.getStringfromBundleFile("deliveryMethod_1"));
            paymentPage = checkoutPage.clickOnContinueButton();
            reviewYourOrderPage = paymentPage.selectContinueButton();
            reviewYourOrderPage.verifyProdSKU(updatedSKU);
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 148595
    //Verify that user is able to add/increase the item count in shopping cart using arrows
    @Test
    public void verifyThatUserAbleToIncreaseTheItemCountUsingArrows_148595() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(orderTypePreAuth.getOrderType());
            homePage.removeAllItemsFromCart();
            productListPage = homePage.navigateProductListPage();
            productDescriptionPage = productListPage.clickingOnItem();
            String Qnty = productDescriptionPage.increaseQuantityUsingArrow();
            productDescriptionPage.clickAddToCartButton();
            productDescriptionPage.verifyItemQuantityOnAddedToCartWindow(Qnty);
            shoppingCartPage = productDescriptionPage.clickViewCartAndCheckoutButton();
            shoppingCartPage.verifyUpdatedItemQuantity(Qnty);
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 148599
    //Verify that item quantity adjusted in Add to Cart page are reflected in final 'Review Your Order' section
    @Test
    public void verifyThatItemQuantityAdjustedInAddToCartPage_148599() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(orderTypePreAuth.getOrderType());
            homePage.removeAllItemsFromCart();
            productListPage = homePage.navigateProductListPage();
            productDescriptionPage = productListPage.clickingOnItem();
            String Qnty = productDescriptionPage.enterValueForItemQuantity(BaseTest.getStringfromBundleFile("quantity"));
            productDescriptionPage.clickAddToCartButton();
            productDescriptionPage.verifyItemQuantityOnAddedToCartWindow(Qnty);
            shoppingCartPage = productDescriptionPage.clickViewCartAndCheckoutButton();
            shoppingCartPage.verifyUpdatedItemQuantity(Qnty);
            checkoutPage = shoppingCartPage.clickProceedToCheckout();
            checkoutPage.verifyItemQuantity(Qnty);
            checkoutPage.selectDeliveryMethod(BaseTest.getStringfromBundleFile("deliveryMethod_1"));
            paymentPage = checkoutPage.clickOnContinueButton();
            reviewYourOrderPage = paymentPage.selectContinueButton();
            reviewYourOrderPage.verifyItemQuantity(Qnty);
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 148600
    //Description: Verify the 'Invalid quantity value' message notification, when user sets item quantity as 0(zero)
    @Test
    public void verifyTheInvalidQuantityValueMessageNotification_148600() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(orderTypePreAuth.getOrderType());
            homePage.removeAllItemsFromCart();
            productListPage = homePage.navigateProductListPage();
            productDescriptionPage = productListPage.clickingOnItem();
            productDescriptionPage.enterValueForItemQuantity(BaseTest.getStringfromBundleFile("invalidQuantity"));
            productDescriptionPage.verifyInvalidQuantityMsg();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 148473
    //Test case Description: Verify user is able to navigate to item's PDP and view item information
    @Test
    public void verifyUserIsAbleToNavigateToItemPDPAndViewItemInformation_148473() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(orderTypePreAuth.getOrderType());
            homePage.removeAllItemsFromCart();
            productListPage = homePage.navigateProductListPage();
            productDescriptionPage = productListPage.verifyPDPNavigation();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 148437
    //Test case Description: Verify 'Out of Stock' message is present next to item
    @Test
    public void verifyOutOfStockMessageIsPresentNextToItem_148437() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(orderTypePreAuth.getOrderType());
            homePage.removeAllItemsFromCart();
            searchResultsPage = homePage.searchItemNameInSearchIcon(BaseTest.getStringfromBundleFile("itemOutOfStock"));
            productDescriptionPage = searchResultsPage.clickItemName();
            productDescriptionPage.verifyOutOfStockMsgInPDP();
            shoppingCartPage = productDescriptionPage.clickAddToCartButton().clickOnViewCartCheckoutButton();
            shoppingCartPage.verifyOutOfStockMsgInShoppingCart();
            checkoutPage = shoppingCartPage.clickProceedToCheckout();
            checkoutPage.verifyOutOfStockMsgInCheckOutPage();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 148569
    //Test case Description: Verify that the Online Price listed in NetSuite matches price listed on PDP(Website)
    @Test
    public void verifyListedPriceOnPDP_148569() throws Exception {
        try{
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(orderTypePreAuth.getOrderType());
            homePage.removeAllItemsFromCart();
            productListPage = homePage.navigateProductListPage();
            productDescriptionPage = productListPage.clickingOnItem();
            productDescriptionPage.verifyItemPrice();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 148563
    //Test case Description: Verify that content and format of Detailed Description in NetSuite matches Details section in PDP(Website)
    @Test
    public void verifyDetailsOnPDP_148563() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(orderTypePreAuth.getOrderType());
            homePage.removeAllItemsFromCart();
            productListPage = homePage.navigateProductListPage();
            productDescriptionPage = productListPage.clickOnItemName(BaseTest.getStringfromBundleFile("itemWithDetails"));
            productDescriptionPage.verifyItemTitle(BaseTest.getStringfromBundleFile("itemWithDetails"));
            productDescriptionPage.verifyItemDetails();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

        //TFS ID: 148480
       //Test case Description: Verify that the sequence of images displayed on website correspond to naming convention in NetSuite
        @Test
        public void verifySequenceOfImagesDisplay_148480() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.removeAllItemsFromCart();
            productListPage=homePage.navigateProductListPage();
            productDescriptionPage = productListPage.clickOnItemName(BaseTest.getStringfromBundleFile("itemWithMultipleImages"));
            productDescriptionPage.verifyItemTitle(BaseTest.getStringfromBundleFile("itemWithMultipleImages"));
            productDescriptionPage.verifyItemImagesInPDP();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 148573
    // Verify that the items are getting added under wishlist by clicking on “Add to Wish list” button on PDP
    @Test
    public void verifyThatTheItemsAreGettingAddedUnderWishList_148573() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(orderTypePreAuth.getOrderType());
            productListPage = homePage.navigateProductListPage();
            productDescriptionPage = productListPage.selectProductToAddToWishList();
            productDescriptionPage.addProductToWishList(BaseTest.getStringfromBundleFile("wishList"));
            myListPage =  productListPage.goToMyWishList(BaseTest.getStringfromBundleFile("wishList"));
            myListPage.verifyProductExistInWishList();
            //commonMethodsPage = productListPage.gotoCommonMethodsPage();
            //commonMethodsPage.verifyWishListItemsPresentInTheList();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 148592
    //Test case Description: Verify new wish list creation and adding an items to newly created wishlist functionality
    @Test
    public void verifyNewWishListCreation_148592() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(orderTypePreAuth.getOrderType());
            productListPage = homePage.navigateProductListPage();
            productDescriptionPage = productListPage.selectProductToAddToWishList();
            productDescriptionPage.clickAddToWishlistButtonAndCreateList(BaseTest.getStringfromBundleFile("newListName"));
            myListPage =  productListPage.goToMyWishList(BaseTest.getStringfromBundleFile("newListName"));
            myListPage.verifyProductExistInWishList();
            //commonMethodsPage = productListPage.gotoCommonMethodsPage();
            //commonMethodsPage.verifyNewWishListAndItemsPresentInTheList();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
        finally {
            myListPage.isMyListEmpty();
        }
    }

    //TFS ID: 148556
    //Test case Description: Verify that after edit and update the item in shopping cart the updated sku and image change is maintained in all the pages
    @Test
    public void verifyUpdatedSkuAndImageChangeAfterEditAndUpdateTheItem_148556() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(orderTypePreAuth.getOrderType());
            homePage.removeAllItemsFromCart();
            productListPage = homePage.navigateProductListPage();
            productDescriptionPage=productListPage.clickOnItemName(BaseTest.getStringfromBundleFile("matrixItem"));
            productDescriptionPage.verifyItemTitle(BaseTest.getStringfromBundleFile("matrixItem"));
            productDescriptionPage.verifyProductSizeAndSKUNumber(BaseTest.getStringfromBundleFile("matrixSize1"));
            productDescriptionPage.verifyProductSizeAndSKUNumber(BaseTest.getStringfromBundleFile("matrixSize2"));
            productDescriptionPage.verifyProductSizeAndSKUNumber(BaseTest.getStringfromBundleFile("matrixSize3"));
            String updatedSKU = productDescriptionPage.verifyProductSizeAndSKUNumber(BaseTest.getStringfromBundleFile("matrixSize4"));
            productDescriptionPage.clickAddToCartButton();
            productDescriptionPage.verifySKUNumberOnAddToCartPopUp(updatedSKU);
            shoppingCartPage = productDescriptionPage.clickViewCartAndCheckoutButton();
            shoppingCartPage.verifySKUNumberonCheckoutPage(updatedSKU);
            shoppingCartPage.navigateToAnItemToEdit();
            updatedSKU = shoppingCartPage.editAndVerifySKUNumber(BaseTest.getStringfromBundleFile("matrixSize3"));
            shoppingCartPage.verifySKUNumberonCheckoutPage(updatedSKU);
            checkoutPage = shoppingCartPage.clickProceedToCheckout();
            checkoutPage.verifyProductSKUInCheckoutPage(updatedSKU);
            checkoutPage.selectDeliveryMethod(BaseTest.getStringfromBundleFile("deliveryMethod_1"));
            paymentPage = checkoutPage.clickOnContinueButton();
            reviewYourOrderPage = paymentPage.selectContinueButton();
            reviewYourOrderPage.verifyProdSKU(updatedSKU);
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }
}