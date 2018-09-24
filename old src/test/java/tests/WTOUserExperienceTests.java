package tests;

import base.EOSWebContext;
import base.test.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.testng.annotations.Test;
import pages.*;
import pages.Cart.*;
import pages.MyAccountOverview.ProfileInformationPage;
import pojos.*;
import utils.Logz;

@ContextConfiguration(locations = {"classpath:/EOSBeans.xml","classpath:/ItemBeans.xml","classpath:/AddressBookBeans.xml", "classpath:/LoginBeans.xml", "classpath:/HomePageBeans.xml", "classpath:/ProductListingBeans.xml"})
@TestExecutionListeners(inheritListeners = false, listeners =
        {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})

public class WTOUserExperienceTests extends EOSWebContext {
    public LoginPage loginPage;
    public ApplicationContext App_Context;
    public ProfileInformationPage profileInformationPage;
    public ProductListPage productListPage;
    public SwitchRestaurantPage switchRestaurantPage;
    public HomePage homePage;
    public ProductDescriptionPage pdpPage;
    public ShoppingCartPage shoppingCartPage;
    public CommonMethodsPage commonMethodsPage;
    public RequestQuotePage requestQuotePage;
    public CheckoutPage checkoutPage;
    public PaymentPage paymentPage;
    public ReviewYourOrderPage reviewYourOrderPage;
    public OrderConfirmationPage orderConfirmationPage;



    @Autowired
    public Login login;
    @Autowired
    public AddressBook addressBook;
    @Autowired
    public Home home;
    @Autowired
    public ProductListing productListing;
    @Autowired
    public Items item;
    @Autowired
    public Login singleRestaurantOwnerIsNotLitigation;
    @Autowired
    public Login SingleResOwnerRestIsLitigation;
    @Autowired
    public Login multiResOwnerRestIsNotLitigation;
    @Autowired
    public Login multiResOwnerAtLeastOneRestIsLitigation;


    //TFS ID: 144827
    //Test case Description: Choose the correct Restaurant for a Multi-Store Owner
    @Test
    public void verifyRestaurantForMultiStoreOwner_144827() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            profileInformationPage = homePage.clickHeaderMenuMyAccountLink(BaseTest.getStringfromBundleFile("linkProfileInformation"));
            profileInformationPage.verifyStoreNoInProfileInfoPage(home.getStoreNumber());
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 144829
    //Test case Description: Verification of Separate Shopping Cart for Multi-Store Owner
    @Test
    public void verifySeparateShoppingCartForMultiStoreOwner_144829() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            productListPage = homePage.navigateProductListPage();
            shoppingCartPage = productListPage.addItemsToCart(3);
            shoppingCartPage.verifyCartBeforeAndAfterSwitching(shoppingCartPage,homePage,home.getOtherstoreNumber());
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 144831
    //Test case Description: Verify Customer Eligible for Lease, Order Type Pre-Auth  and Item Eligible for Lease
    @Test
    public void verifyCustomerAndItemEligibleForLeaseOrderTypePreAuth_144831() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            productListPage = homePage.navigateProductListPage();
            pdpPage =  productListPage.clickTableName(productListing.getLeasableProduct());
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 144837
    //Test case Description: Verify Customer Eligible for Lease, Order Type Pre-Auth and Item Not Eligible for Lease
    @Test
    public void verifyCustomerEligibleForLeaseAndItemNotEligibleForLeaseOrderTypePreAuth_144837() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            productListPage = homePage.navigateProductListPage();
            pdpPage = productListPage.clickTableName(productListing.getNonLeasableProduct());
            pdpPage.isTextLeasableItemDisplayed();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 144839
    //Test case Description: Verify Customer Eligible for Lease, Order Type Undefined and Item Eligible for Lease
    @Test
    public void verifyCustomerAndItemEligibleForLeaseOrderTypeUndefined_144839() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            productListPage = homePage.navigateProductListPage();
            pdpPage = productListPage.clickTableName(productListing.getLeasableProduct());
            pdpPage.isTextLeasableItemDisplayed();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 144852
    //Test case Description: Verify Customer Eligible for Lease, Order Type Undefined and Item not Eligible for Lease
    @Test
    public void verifyCustomerEligibleForLeaseAndItemNotEligibleForLeaseOrderTypeUndefined_144852() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            productListPage = homePage.navigateProductListPage();
            pdpPage = productListPage.clickTableName(productListing.getNonLeasableProduct());
            pdpPage.verifyItemSelectedToBeLeasedCheckboxNotDisplayed();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 144853
    //Test case Description: Verify Customer Eligible for Lease, Order Type 313 and Item Eligible for Lease
    @Test
    public void verifyCustomerAndItemEligibleForLeaseOrderType313_144853() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderType313"));
            productListPage = homePage.navigateProductListPage();
            pdpPage = productListPage.clickTableName(productListing.getLeasableProduct());
            pdpPage.verifyItemSelectedToBeLeasedCheckboxDisplayed();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 144857
    //Test case Description: Verify Customer Eligible for Lease, Order Type 313 and Item not Eligible for Lease
    @Test
    public void verifyCustomerEligibleForLeaseAndItemNotEligibleForLeaseOrderType313_144857() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderType313"));
            productListPage = homePage.navigateProductListPage();
            pdpPage = productListPage.clickTableName(productListing.getNonLeasableProduct());
            pdpPage.verifyItemSelectedToBeLeasedCheckboxNotDisplayed();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 144876
    //Test case Description: Auto-Approval of Pre-Auth Order:  Single Restaurant Owner and Restaurant Not Under Litigation -  Order Total> > Pre-Auth Maximum Order Limit
    @Test
    public void verifySingleRestOwnerAndRestNotUnderLitigationOrderTotalGreaterThanPreAuthMaximumOrderLimit_144876() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            homePage = loginPage.SSOLoginForSingleRestaurant(singleRestaurantOwnerIsNotLitigation);
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            homePage.removeAllItemsFromCart();
            commonMethodsPage = homePage.gotoCommonMethodsPage();
            Double orderLimit = commonMethodsPage.restaurantsWithNoLitigation(BaseTest.getStringfromBundleFile("maxPreAuthOrderLimitType"),0);
            productListPage = homePage.selectSubMenuItem(BaseTest.getStringfromBundleFile("shopByArea"), BaseTest.getStringfromBundleFile("tables"));
            shoppingCartPage = commonMethodsPage.addToCartOverMaxPreAuthLimit(orderLimit,BaseTest.getStringfromBundleFile("shopByAreaMenu"));
            shoppingCartPage.verifyProductCheckoutButton();
            shoppingCartPage.verifyErrorMessage();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 145184
    //Test case Description: Auto-Approval of Pre-Auth Order:  Single Restaurant Owner and Restaurant is Under Litigation -  Order Total > Litigation Pre-Auth  Limit
    @Test
    public void verifySingleRestOwnerAndRestUnderLitigationOrderTotalGreaterThanLitigationPreAuthLimit_145184() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            homePage = loginPage.SSOLoginForSingleRestaurant(SingleResOwnerRestIsLitigation);
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            homePage.removeAllItemsFromCart();
            commonMethodsPage = homePage.gotoCommonMethodsPage();
            Double orderLimit = commonMethodsPage.restaurantsWithOneLitigation(BaseTest.getStringfromBundleFile("litigation"),null,0);
            productListPage = homePage.selectSubMenuItem(BaseTest.getStringfromBundleFile("shopByArea"), BaseTest.getStringfromBundleFile("tables"));
            shoppingCartPage = commonMethodsPage.addToCartOverMaxPreAuthLimit(orderLimit,BaseTest.getStringfromBundleFile("shopByAreaMenu"));
            shoppingCartPage.verifyProductCheckoutButton();
            shoppingCartPage.verifyErrorMessage();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 145187
    //Test case Description:  Multi-Restaurant Owner and No Restaurants are Under Litigation -  Order Total >  Pre-Auth Maximum Order Limit
    @Test
    public void verifyMultiRestOwnerAndRestNotUnderLitigationOrderTotalGreaterThanPreAuthMaximumOrderLimit_145187() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(multiResOwnerRestIsNotLitigation);
            int noOfStores = switchRestaurantPage.getRestaurantDropdown().getListOptions().size();
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            homePage.removeAllItemsFromCart();
            productListPage = homePage.selectSubMenuItem(BaseTest.getStringfromBundleFile("shopByArea"), BaseTest.getStringfromBundleFile("tables"));
            commonMethodsPage = productListPage.gotoCommonMethodsPage();
            Double orderLimit = commonMethodsPage.restaurantsWithNoLitigation(BaseTest.getStringfromBundleFile("maxPreAuthOrderLimitType"),noOfStores);
            shoppingCartPage = commonMethodsPage.addToCartOverMaxPreAuthLimit(orderLimit,BaseTest.getStringfromBundleFile("shopByAreaMenu"));
            shoppingCartPage.verifyProductCheckoutButton();
            shoppingCartPage.verifyErrorMessage();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 145190
    //Test case Description:  Auto-Approval of Pre-Auth Order:  Multi-Restaurant Owner and at least One Restaurant is Under Litigation (Logged in as a Restaurant under litigation)-  Order Total > Litigation Pre-Auth  Limit
    @Test
    public void verifyMultiRestOwnerAndRestUnderLitigationOrderTotalGreaterThanLitigationPreAuthLimit_145190() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(multiResOwnerAtLeastOneRestIsLitigation);
            int noOfStores = switchRestaurantPage.getRestaurantDropdown().getListOptions().size();
            homePage = switchRestaurantPage.switchRestaurant(multiResOwnerAtLeastOneRestIsLitigation.getLitigationStore());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            homePage.removeAllItemsFromCart();
            commonMethodsPage = homePage.gotoCommonMethodsPage();
            Double orderLimit=commonMethodsPage.restaurantsWithOneLitigation("litigationStore",null, noOfStores);
            productListPage = homePage.selectSubMenuItem(BaseTest.getStringfromBundleFile("shopByArea"), BaseTest.getStringfromBundleFile("tables"));
            shoppingCartPage = commonMethodsPage.addToCartOverMaxPreAuthLimit(orderLimit,BaseTest.getStringfromBundleFile("shopByAreaMenu"));
            shoppingCartPage.verifyProductCheckoutButton();
            shoppingCartPage.verifyErrorMessage();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 145199
    //Test case Description:  Multi-Restaurant Owner and at least One Restaurant is Under Litigation (Logged in as a Restaurant not under litigation)-   Order Total > Pre-Auth Maximum Order Limit
    @Test
    public void verifyMultiRestOwnerAndRestUnderLitigationOrderTotalGreaterThanPreAuthMaximumOrderLimit_145199() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(multiResOwnerAtLeastOneRestIsLitigation);
            int noOfStores = switchRestaurantPage.getRestaurantDropdown().getListOptions().size();
            homePage = switchRestaurantPage.switchRestaurant(multiResOwnerAtLeastOneRestIsLitigation.getNonLitigationStore());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            homePage.removeAllItemsFromCart();
            commonMethodsPage = homePage.gotoCommonMethodsPage();
            Double orderLimit = commonMethodsPage.restaurantsWithOneLitigation(BaseTest.getStringfromBundleFile("nonLitigation"),BaseTest.getStringfromBundleFile("maxPreAuthOrderLimitType"),noOfStores);
            productListPage = homePage.selectSubMenuItem(BaseTest.getStringfromBundleFile("shopByArea"), BaseTest.getStringfromBundleFile("tables"));
            shoppingCartPage = commonMethodsPage.addToCartOverMaxPreAuthLimit(orderLimit,BaseTest.getStringfromBundleFile("shopByAreaMenu"));
            shoppingCartPage.verifyProductCheckoutButton();
            shoppingCartPage.verifyErrorMessage();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 145201
    //Test case Description:  Choose Necessary Parts and Accessories for items that are being added to the cart
    //TFS ID: 148605
    // Test case Description: Verify that 'Parts and Accessories' is value in 'Website Related Item' field
    @Test
    public void verifyNecessaryPartsAndAccessoriesForItems_145201_148605() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            productListPage = homePage.navigateProductListPage();
            pdpPage = productListPage.clickTableName(productListing.getProductName1());
            pdpPage.verifyPartNamesUnderPartsAndAccessoriesSection();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 145202
    //Test case Description:  Franchisee to be able to compare more than 4 items
    @Test
    public void verifyUserIsAbleToCompareMoreThan4Items_145202() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            productListPage = homePage.navigateProductListPage();
            productListPage.selectFiveItemsToCompareAndVerify();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 145203
    //Test case Description:  Franchisee should be able to initiate a quote from the website
    @Test
    public void verifyUserIsAbleToInitiateQuoteFromTheWebsite_145203() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            productListPage = homePage.navigateProductListPage();
            commonMethodsPage = productListPage.gotoCommonMethodsPage();
            String arrItemsAddedToQuote[] = commonMethodsPage.addProductsToQuoteAndClickOnRequestQuoteBtn(4);
            requestQuotePage = homePage.clickRequestAQuote();
            String arrItemsPresentInQuote[] = requestQuotePage.getItemNamesRequestedForQuote();
            requestQuotePage.verifyItemsAddedToRequestQuote(arrItemsAddedToQuote,arrItemsPresentInQuote);
            requestQuotePage.clickOnSubmitButtonAtTop();
            requestQuotePage.verifyQuoteNumber(requestQuotePage.getQuoteRequestNumber());
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 145205
    //Test case Description:  Add to Quote for Special Order Item
    @Test
    public void verifyUserIsAbleToAddQuoteForSpecialOrderItem_145205() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            commonMethodsPage = homePage.gotoCommonMethodsPage();
            commonMethodsPage.addSpecificItemToQuoteAndClickOnRequestQuoteBtn(BaseTest.getStringfromBundleFile("specialOrderItem"));
            requestQuotePage = homePage.clickRequestAQuote();
            requestQuotePage.clickOnSubmitButtonAtTop();
            requestQuotePage.verifyQuoteNumber(requestQuotePage.getQuoteRequestNumber());
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 145207
    //Test case Description:  Request a Quote for Regular Item
    @Test
    public void verifyRequestQuoteForRegularItem_145207() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            requestQuotePage=homePage.clickRequestAQuote();
            String quotenumber = requestQuotePage.verifyAddQuoteWithMultipleItems(item, BaseTest.getStringfromBundleFile("itemQty"));
            Logz.step("Quote Number: "+quotenumber);
            requestQuotePage.verifyQuoteNumber(quotenumber);
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 145209
    //Test case Description:  Request a Quote for Special Order Item
    @Test
    public void verifyRequestQuoteForSpecialOrderItem_145209() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
//          homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            requestQuotePage = homePage.clickRequestAQuote();
            requestQuotePage.selectItemForQuoteandSubmit(BaseTest.getStringfromBundleFile("specialOrderItemSKU"),BaseTest.getStringfromBundleFile("itemQty"));
            requestQuotePage.clickOnSubmitButtonAtTop();
            requestQuotePage.verifyQuoteNumber(requestQuotePage.getQuoteRequestNumber());
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 145212
    //Test case Description:  Undefined Order Type
    @Test
    public void verifyUndefinedOrderType_145212() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            //homePage = switchRestaurantPage.verifyUndefinedOrderType();
            productListPage = homePage.selectSubMenuItem(BaseTest.getStringfromBundleFile("shopByArea"), BaseTest.getStringfromBundleFile("tables"));
            pdpPage = productListPage.clickOnItemName(productListing.getSpecialOrderItem());
            pdpPage.verifyAddToCartBtnDisabled();
            pdpPage.verifyOrderTypeSelectionPromptMsg();
            pdpPage.selectOrderTypeValue(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            pdpPage.verifyAddToCartBtnEnabled();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 151967
    //Test case Description:  Multi-Language Website - English Language
    @Test
    public void verifyEnglishLanguageWebsite_151967() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.clickHomeHeaderMenu();
            homePage.verifyLanguageInCurrentUrl();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 151969
    //Test case Description:  Product Comparison - 4 or less items
    @Test
    public void verifyProductComparison4OrIessItems_151969() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
//          homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            productListPage = homePage.navigateProductListPage();
            commonMethodsPage = productListPage.gotoCommonMethodsPage();
            commonMethodsPage.verifySelectedItemsInGoToCompare();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 145211
    //Test case Description: Franchisee can create wish lists from the shopping application on the website
    @Test
    public void verifyUserCanCreateWishListsFromTheShoppingApplicationOnTheWebsite_145211() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            commonMethodsPage = homePage.gotoCommonMethodsPage();
            commonMethodsPage.makeMyListItemsToEmpty();
            productListPage = homePage.selectSubMenuItem(BaseTest.getStringfromBundleFile("shopByArea"), BaseTest.getStringfromBundleFile("tables"));
            commonMethodsPage.verifyWishListItemsPresentInTheList();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 151980
    //Test case Description: Single Restaurant Owners not Prompted to Choose Restaurant
    @Test
    public void verifyLoginWithSingleRestOwner_151980_151975() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
        	homePage = loginPage.SSOLoginForSingleRestaurant(SingleResOwnerRestIsLitigation);
            homePage.verifyLoggedWithSingleRestOwner();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 145195
    //Test case Description: Multi-Restaurant Owner and at least One Restaurant is Under Litigation (Logged in as a Restaurant not under litigation)-  Order Total <  Pre-Auth Auto-Approve  Limit
    @Test
    public void verifyMultiRestOneRestUnderLitigation_145195() throws Exception
    {
        try{
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(multiResOwnerAtLeastOneRestIsLitigation);
            int noOfStores = switchRestaurantPage.getRestaurantDropdown().getListOptions().size();
            homePage = switchRestaurantPage.switchRestaurant(multiResOwnerAtLeastOneRestIsLitigation.getNonLitigationStore());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            homePage.removeAllItemsFromCart();
            commonMethodsPage = homePage.gotoCommonMethodsPage();
            Double orderLimit = commonMethodsPage.restaurantsWithOneLitigation(BaseTest.getStringfromBundleFile("nonLitigation"),BaseTest.getStringfromBundleFile("maxPreAuthOrderLimitType"),noOfStores);
//            Double orderLimit = 3500.00;
            productListPage = homePage.selectSubMenuItem(BaseTest.getStringfromBundleFile("shopByArea"), BaseTest.getStringfromBundleFile("tables"));
            shoppingCartPage = commonMethodsPage.addToCartBasedOnOrderLimit(orderLimit,BaseTest.getStringfromBundleFile("shopByAreaMenu"));
            checkoutPage = shoppingCartPage.clickProceedToCheckout();
//            checkoutPage.enterPromoCode(BaseTest.getStringfromBundleFile("testCust"));
            checkoutPage.enterPromoCode(BaseTest.getStringfromBundleFile("testPlyItems"));
            checkoutPage.verifyDiscountIsAppliedToTotal();
            checkoutPage.selectShippingAddress(addressBook);
            checkoutPage.selectDeliveryMethod(BaseTest.getStringfromBundleFile("shipFromVendor"));
            checkoutPage.verifyOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            float reviewTotal=checkoutPage.verifySubTotal();
            paymentPage = checkoutPage.clickOnContinueButton();
            paymentPage.selectPaymentMethodDropdown(BaseTest.getStringfromBundleFile("invoice"));
            //paymentPage.selectChangeAddressLink();
            reviewYourOrderPage = paymentPage.selectContinueButton();
            reviewYourOrderPage.verifyUserNavigateToReviewOrderPage();
            reviewYourOrderPage.verifyOrderSummary(reviewTotal);
            reviewYourOrderPage.clickOnPlaceOrder();
            orderConfirmationPage = reviewYourOrderPage.clickOnAccept();
            orderConfirmationPage.verifySalesOrderNumber();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 145188
    //Test case Description:  Multi-Restaurant Owner and at least One Restaurant is Under Litigation (Logged in as a Restaurant under litigation)-  Order Total < Litigation Pre-Auth  Limit
    @Test
    public void verifyMultiRestOneRestUnderLitigationOrderLessThanLimit_145188() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(multiResOwnerAtLeastOneRestIsLitigation);
            homePage = switchRestaurantPage.switchRestaurant(multiResOwnerAtLeastOneRestIsLitigation.getLitigationStore());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            homePage.removeAllItemsFromCart();
            commonMethodsPage = homePage.gotoCommonMethodsPage();
            Double orderLimit = commonMethodsPage.restaurantsWithOneLitigation(BaseTest.getStringfromBundleFile("litigation"), null,0);
            productListPage = homePage.selectSubMenuItem(BaseTest.getStringfromBundleFile("shopByArea"), BaseTest.getStringfromBundleFile("tables"));
            shoppingCartPage = commonMethodsPage.addToCartBasedOnOrderLimit(orderLimit, BaseTest.getStringfromBundleFile("shopByAreaMenu"));
            checkoutPage = shoppingCartPage.clickProceedToCheckout();
            checkoutPage.selectShippingAddress(addressBook);
            checkoutPage.selectDeliveryMethod(BaseTest.getStringfromBundleFile("shipFromVendor"));
            checkoutPage.verifyOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            float reviewTotal=checkoutPage.verifySubTotal();
            paymentPage = checkoutPage.clickOnContinueButton();
            paymentPage.selectPaymentMethodDropdown(BaseTest.getStringfromBundleFile("invoice"));
            reviewYourOrderPage = paymentPage.selectContinueButton();
            reviewYourOrderPage.verifyUserNavigateToReviewOrderPage();
            reviewYourOrderPage.verifyOrderSummary(reviewTotal);
            reviewYourOrderPage.clickOnPlaceOrder();
            orderConfirmationPage = reviewYourOrderPage.clickOnAccept();
            orderConfirmationPage.verifyOrderConfirmation();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }
    //TFS ID: 145185
    //Test case Description: Multi-Restaurant Owner and No Restaurants are Under Litigation -  Order Total <  Pre-Auth Auto Approve Limit
    @Test
    public void verifyMultiRestWithNoLitigationOrderLessThanLimit_145185() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(multiResOwnerRestIsNotLitigation);
            int noOfStores = switchRestaurantPage.getRestaurantDropdown().getListOptions().size();
            homePage = switchRestaurantPage.switchRestaurant(multiResOwnerRestIsNotLitigation.getStoreToSelect());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            homePage.removeAllItemsFromCart();
            commonMethodsPage = homePage.gotoCommonMethodsPage();
            Double orderLimit = commonMethodsPage.restaurantsWithNoLitigation(BaseTest.getStringfromBundleFile("preAuthOrderLimitType"),noOfStores);
            productListPage = homePage.selectSubMenuItem(BaseTest.getStringfromBundleFile("shopByArea"), BaseTest.getStringfromBundleFile("tables"));
            shoppingCartPage = commonMethodsPage.addToCartBasedOnOrderLimit(orderLimit, BaseTest.getStringfromBundleFile("shopByAreaMenu"));
            checkoutPage = shoppingCartPage.clickProceedToCheckout();
            checkoutPage.selectShippingAddress(addressBook);
            checkoutPage.selectDeliveryMethod(BaseTest.getStringfromBundleFile("shipFromVendor"));
            checkoutPage.verifyOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            float reviewTotal=checkoutPage.verifySubTotal();
            paymentPage = checkoutPage.clickOnContinueButton();
            paymentPage.selectPaymentMethodDropdown(BaseTest.getStringfromBundleFile("invoice"));
            //paymentPage.selectShippingAddressCheckbox();
            reviewYourOrderPage = paymentPage.selectContinueButton();
            reviewYourOrderPage.verifyUserNavigateToReviewOrderPage();
            reviewYourOrderPage.verifyOrderSummary(reviewTotal);
            reviewYourOrderPage.clickOnPlaceOrder();
            orderConfirmationPage = reviewYourOrderPage.clickOnAccept();
            orderConfirmationPage.verifyOrderConfirmation();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 145186
    //Test case Description:  Multi-Restaurant Owner and No Restaurants are Under Litigation - Pre-Auth Auto Approve Limit < Order Total < Pre-Auth Maximum Order Limit
    @Test
    public void verifyMultiRestWithNoLitigationOrderGreatThanLimit_145186() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(multiResOwnerRestIsNotLitigation);
            int noOfStores = switchRestaurantPage.getRestaurantDropdown().getListOptions().size();
            homePage = switchRestaurantPage.switchRestaurant(multiResOwnerRestIsNotLitigation.getStoreToSelect());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            homePage.removeAllItemsFromCart();
            commonMethodsPage = homePage.gotoCommonMethodsPage();
            Double orderLimit = commonMethodsPage.restaurantsWithNoLitigation(BaseTest.getStringfromBundleFile("preAuthOrderLimitType"),noOfStores);
            Double maxOrderLimit = commonMethodsPage.restaurantsWithNoLitigation(BaseTest.getStringfromBundleFile("maxPreAuthOrderLimitType"),noOfStores);
            productListPage = homePage.selectSubMenuItem(BaseTest.getStringfromBundleFile("shopByArea"), BaseTest.getStringfromBundleFile("tables"));
            shoppingCartPage = commonMethodsPage.addToCartBasedOnPreAuthAndMaxLimit(orderLimit,maxOrderLimit ,BaseTest.getStringfromBundleFile("shopByAreaMenu"));
            checkoutPage = shoppingCartPage.clickProceedToCheckout();
            checkoutPage.selectShippingAddress(addressBook);
            checkoutPage.selectDeliveryMethod(BaseTest.getStringfromBundleFile("shipFromVendor"));
            checkoutPage.verifyOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            float reviewTotal=checkoutPage.verifySubTotal();
            paymentPage = checkoutPage.clickOnContinueButton();
            paymentPage.selectPaymentMethodDropdown(BaseTest.getStringfromBundleFile("invoice"));
            //Step 15 and Step 16 are no more valid in application
            //paymentPage.selectShippingAddressCheckbox();
            reviewYourOrderPage = paymentPage.selectContinueButton();
            reviewYourOrderPage.verifyUserNavigateToReviewOrderPage();
            reviewYourOrderPage.verifyOrderSummary(reviewTotal);
            reviewYourOrderPage.clickOnPlaceOrder();
            orderConfirmationPage = reviewYourOrderPage.clickOnAccept();
            orderConfirmationPage.verifyOrderConfirmation();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 145197
    //Test case Description: Multi-Restaurant Owner and at least One Restaurant is Under Litigation (Logged in as a Restaurant not under litigation)- Pre-Auth Auto-Approve  Limit < Order Total < Pre-Auth Maximum Order Limit
    @Test
    public void verifyMultiRestOneRestUnderLitigationMaxLimit_145197() throws Exception
    {
        try{
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(multiResOwnerAtLeastOneRestIsLitigation);
            int noOfStores = switchRestaurantPage.getRestaurantDropdown().getListOptions().size();
            homePage = switchRestaurantPage.switchRestaurant(multiResOwnerAtLeastOneRestIsLitigation.getNonLitigationStore());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            homePage.removeAllItemsFromCart();
            commonMethodsPage = homePage.gotoCommonMethodsPage();
            Double preAuthLimit = commonMethodsPage.restaurantsWithOneLitigation(BaseTest.getStringfromBundleFile("nonLitigation"),BaseTest.getStringfromBundleFile("preAuthOrderLimitType"),noOfStores);
            Double maxPreAuthLimit = commonMethodsPage.restaurantsWithOneLitigation(BaseTest.getStringfromBundleFile("nonLitigation"),BaseTest.getStringfromBundleFile("maxPreAuthOrderLimitType"),noOfStores);
            productListPage = homePage.selectSubMenuItem(BaseTest.getStringfromBundleFile("shopByArea"), BaseTest.getStringfromBundleFile("tables"));
            shoppingCartPage = commonMethodsPage.addToCartBasedOnPreAuthAndMaxLimit(preAuthLimit,maxPreAuthLimit,BaseTest.getStringfromBundleFile("shopByAreaMenu"));
            checkoutPage = shoppingCartPage.clickProceedToCheckout();
//            checkoutPage.enterPromoCode(BaseTest.getStringfromBundleFile("testCust"));
            checkoutPage.enterPromoCode(BaseTest.getStringfromBundleFile("testPlyItems"));
            checkoutPage.verifyDiscountIsAppliedToTotal();
            checkoutPage.selectShippingAddress(addressBook);
            checkoutPage.selectDeliveryMethod(BaseTest.getStringfromBundleFile("shipFromVendor"));
            checkoutPage.verifyOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            float reviewTotal=checkoutPage.verifySubTotal();
            paymentPage = checkoutPage.clickOnContinueButton();
            paymentPage.selectPaymentMethodDropdown(BaseTest.getStringfromBundleFile("invoice"));
            paymentPage.selectChangeAddressLink();
            reviewYourOrderPage = paymentPage.selectContinueButton();
            reviewYourOrderPage.verifyUserNavigateToReviewOrderPage();
            reviewYourOrderPage.verifyOrderSummary(reviewTotal);
            reviewYourOrderPage.clickOnPlaceOrder();
            orderConfirmationPage = reviewYourOrderPage.clickOnAccept();
            orderConfirmationPage.verifySalesOrderNumber();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 144878
    //Test case Description: Single Restaurant Owner and Restaurant is Under Litigation -  Order Total < Litigation Pre-Auth  Limit
    @Test
    public void verifySingleRestOwnerAndRestIsUnderLitigation_144878() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            homePage = loginPage.SSOLoginForSingleRestaurant(SingleResOwnerRestIsLitigation);
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            homePage.removeAllItemsFromCart();
            commonMethodsPage = homePage.gotoCommonMethodsPage();
            Double orderLimit = commonMethodsPage.restaurantsWithOneLitigation(BaseTest.getStringfromBundleFile("litigation"),null,0);
            productListPage = homePage.selectSubMenuItem(BaseTest.getStringfromBundleFile("shopByArea"), BaseTest.getStringfromBundleFile("tables"));
            shoppingCartPage = commonMethodsPage.addToCartBasedOnOrderLimit(orderLimit,BaseTest.getStringfromBundleFile("shopByAreaMenu"));
//            shoppingCartPage.verifyProductCheckoutButton();
            checkoutPage = shoppingCartPage.clickProceedToCheckout();
            checkoutPage.selectShippingAddress(addressBook);
            checkoutPage.selectDeliveryMethod(BaseTest.getStringfromBundleFile("shipFromVendor"));
            checkoutPage.verifyOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            float reviewTotal=checkoutPage.verifySubTotal();
            paymentPage = checkoutPage.clickOnContinueButton();
            paymentPage.selectPaymentMethodDropdown(BaseTest.getStringfromBundleFile("invoice"));
            //Step 14 & 15 are Currently descoped
            //paymentPage.selectShippingAddressCheckbox();
            reviewYourOrderPage = paymentPage.selectContinueButton();
            reviewYourOrderPage.verifyUserNavigateToReviewOrderPage();
            reviewYourOrderPage.verifyOrderSummary(reviewTotal);
            reviewYourOrderPage.clickOnPlaceOrder();
            orderConfirmationPage = reviewYourOrderPage.clickOnAccept();
            orderConfirmationPage.verifyOrderConfirmation();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }
}