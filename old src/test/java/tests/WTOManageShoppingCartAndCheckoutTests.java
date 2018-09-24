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
import pojos.AddressBook;
import pojos.Home;
import pojos.Login;
import utils.Logz;

@ContextConfiguration(locations = {"classpath:/EOSBeans.xml","classpath:/LoginBeans.xml","classpath:/AddressBookBeans.xml", "classpath:/HomePageBeans.xml", "classpath:/ProductListingBeans.xml","classpath:/ItemBeans.xml"})
@TestExecutionListeners(inheritListeners = false, listeners =
        {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})

public class WTOManageShoppingCartAndCheckoutTests extends EOSWebContext {
    public LoginPage loginPage;

    public HomePage homePage;
    public SwitchRestaurantPage switchRestaurantPage;
    public QuotePage quotePage;
    public QuoteDescriptionPage quoteDescriptionPage;
    public ProductListPage productListPage;
    public ShoppingCartPage shoppingCartPage;
    public CheckoutPage checkoutPage;
    public PaymentPage paymentPage;
    public ReviewYourOrderPage reviewYourOrderPage;
    public OrderConfirmationPage orderConfirmationPage;
    public SearchResultsPage searchResultsPage;
    public ProductDescriptionPage productDescriptionPage;
    public RequestQuotePage requestQuotePage;
    public ApplicationContext App_Context;

    @Autowired
    public Home home;
    @Autowired
    public Login login;
    @Autowired
    public AddressBook addressBook;
    @Autowired
    public Login SingleResOwnerRestIsLitigation;
    @Autowired
    public Login singleRestaurantOwnerIsNotLitigation;
    @Autowired
    public Login multiResOwnerRestIsNotLitigation;
    @Autowired
    public Login userNotApprovedForPreAuth;
    @Autowired
    public Login multiResOwnerAtLeastOneRestIsLitigation;

    //TFS ID: 143899
    //Test case Description: Order Type drop down is available for Approved User
    @Test
    public void verifyOrderTypeDropdown_143899() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 143932
    //Test case Description: User Navigate to list of WTOQuotesTests
    @Test
    public void verifyNavigateToQuotesList_143932() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            quotePage = homePage.clickQuotesHeaderMenu(BaseTest.getStringfromBundleFile("linkQuotes"));
            quotePage.verifyQuoteList();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 152332
    //Test case Description: Separation of Direct Ship and Consolidated Item Ship on Quote
    @Test
    public void verifyQuoteDetails_152332() throws Exception {
        try {
        	loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            quotePage = homePage.clickQuotesHeaderMenu(BaseTest.getStringfromBundleFile("linkQuotes"));
            quotePage.verifyQuoteList();
            quoteDescriptionPage = quotePage.verifyQuoteWithStatus(BaseTest.getStringfromBundleFile("statusProposal"));
            quoteDescriptionPage.verifyQuoteConsolidate();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 144358
    //Test case Description: Post Order Confirmation
    @Test
    public void verifyPostOrderConfirmation_144358() throws Exception {
        try {
        	loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            homePage.removeAllItemsFromCart();
            productListPage = homePage.navigateProductListPage();           
            shoppingCartPage = productListPage.addItemsToCart(2);
            checkoutPage = shoppingCartPage.clickProceedToCheckout();
            checkoutPage.selectShippingAddress(addressBook);
            checkoutPage.selectDeliveryMethod(BaseTest.getStringfromBundleFile("shipFromVendor"));
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

    //TFS ID: 144365
    //Test case Description: Placing an Order from a Quote (Status : Proposal)
    @Test
    public void verifyProposalQuoteOrder_144365() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            quotePage = homePage.clickQuotesHeaderMenu(BaseTest.getStringfromBundleFile("linkQuotes"));
            quotePage.verifyQuoteList();
            quoteDescriptionPage = quotePage.verifyQuoteWithStatus(BaseTest.getStringfromBundleFile("statusProposal"));
            quoteDescriptionPage.verifyReviewOrderButtondisabled();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 144367
    //Test case Description: Placing an Order from a Quote (Status : Closed-Won)
    @Test
    public void verifyClosedWonQuoteOrder_144367() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            quotePage = homePage.clickQuotesHeaderMenu(BaseTest.getStringfromBundleFile("linkQuotes"));
            quotePage.verifyQuoteList();
            quotePage.verifyQuoteWithStatus(BaseTest.getStringfromBundleFile("statusClosedWon"));
            quoteDescriptionPage.verifyReviewOrderButtonNotPresent();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 143926
    //Test case Description: Franchisees should have the option to Ship or Pick-up Orders from a Consolidator
    @Test
    public void verifyDeliveryMethod_143926() throws Exception {
        try {
        	 loginPage = goToHomePage(LoginPage.class, driverName);
             switchRestaurantPage = loginPage.SSOLogin(login);
             homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
             homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
             productListPage = homePage.selectSubMenuItem(BaseTest.getStringfromBundleFile("shopByArea"), BaseTest.getStringfromBundleFile("tables"));
             productDescriptionPage = productListPage.clickTableName(BaseTest.getStringfromBundleFile("productName"));
             shoppingCartPage = productDescriptionPage.clickAddToCartButton().clickViewCartAndCheckoutButton();
             checkoutPage = shoppingCartPage.clickProceedToCheckout();
             checkoutPage.selectShippingAddress(addressBook);
             checkoutPage.verifyDeliveryOptions();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 143916
    //Test case Description: Capability to Split Payment Across Restaurants
    @Test
    public void verifySplitPaymentPopup_143916() throws Exception {
        try {
        	loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(multiResOwnerRestIsNotLitigation);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            productListPage = homePage.selectSubMenuItem(BaseTest.getStringfromBundleFile("shopByArea"), BaseTest.getStringfromBundleFile("tables"));
            productDescriptionPage = productListPage.clickTableName(BaseTest.getStringfromBundleFile("productName"));
            shoppingCartPage = productDescriptionPage.clickAddToCartButton().clickViewCartAndCheckoutButton();
            checkoutPage = shoppingCartPage.clickProceedToCheckout();
            checkoutPage.selectShippingAddress(addressBook);
            checkoutPage.selectDeliveryMethod(BaseTest.getStringfromBundleFile("pickUp"));
            checkoutPage.verifyOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            paymentPage = checkoutPage.clickOnContinueButton();
            paymentPage.selectPaymentMethodDropdown(BaseTest.getStringfromBundleFile("invoice"));
            paymentPage.verifySplitPaymentPopup();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 148443
    //Test case Description: Split Payment - Split among all Restaurants
    @Test
    public void verifySplitPaymentAmongAllRestaurants_148443() throws Exception {
        try {
        	loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            productListPage = homePage.selectSubMenuItem(BaseTest.getStringfromBundleFile("shopByArea"), BaseTest.getStringfromBundleFile("tables"));
            productDescriptionPage = productListPage.clickTableName(BaseTest.getStringfromBundleFile("productName"));
            shoppingCartPage = productDescriptionPage.clickAddToCartButton().clickViewCartAndCheckoutButton();
            checkoutPage = shoppingCartPage.clickProceedToCheckout();
            checkoutPage.selectShippingAddress(addressBook);
            checkoutPage.selectDeliveryMethod(BaseTest.getStringfromBundleFile("pickUp"));
            paymentPage = checkoutPage.clickOnContinueButton();
            paymentPage.selectPaymentMethodDropdown(BaseTest.getStringfromBundleFile("invoice"));
            paymentPage.verifySplitPaymentAmongRestaurants();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 148451
    //Test case Description: Split Payment - Single Restaurant Owner not under litigation - Pre-Auth Order Type
    @Test
    public void verifySplitPaymentForSingleRestaurantPreAuth_148451() throws Exception {
        try {
        	loginPage = goToHomePage(LoginPage.class, driverName);
            homePage = loginPage.SSOLoginForSingleRestaurant(singleRestaurantOwnerIsNotLitigation);
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            productListPage = homePage.selectSubMenuItem(BaseTest.getStringfromBundleFile("shopByArea"), BaseTest.getStringfromBundleFile("tables"));
            productDescriptionPage = productListPage.clickTableName(BaseTest.getStringfromBundleFile("productName"));
            shoppingCartPage = productDescriptionPage.clickAddToCartButton().clickViewCartAndCheckoutButton();
            checkoutPage = shoppingCartPage.clickProceedToCheckout();
            checkoutPage.selectShippingAddress(addressBook);
            checkoutPage.selectDeliveryMethod(BaseTest.getStringfromBundleFile("pickUp"));
            checkoutPage.verifyOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            paymentPage = checkoutPage.clickOnContinueButton();
            paymentPage.verifySplitPaymentOptionNotAvailable();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 148452
    // Test case Description: Split Payment - Single Restaurant Owner not under litigation - 313 Order Type
    @Test
    public void verifySplitPaymentForSingleRestaurant313_148452() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            homePage = loginPage.SSOLoginForSingleRestaurant(singleRestaurantOwnerIsNotLitigation);
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderType313"));
            productListPage = homePage.selectSubMenuItem(BaseTest.getStringfromBundleFile("shopByArea"), BaseTest.getStringfromBundleFile("tables"));
            productDescriptionPage = productListPage.clickTableName(BaseTest.getStringfromBundleFile("productName"));
            shoppingCartPage = productDescriptionPage.clickAddToCartButton().clickViewCartAndCheckoutButton();
            checkoutPage = shoppingCartPage.clickProceedToCheckout();
            checkoutPage.selectShippingAddress(addressBook);
            checkoutPage.selectDeliveryMethod(BaseTest.getStringfromBundleFile("pickUp"));
            checkoutPage.verifyOrderType(BaseTest.getStringfromBundleFile("orderType313"));
            paymentPage = checkoutPage.clickOnContinueButton();
            paymentPage.verifySplitPaymentOptionNotAvailable();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 148454
    // Test case Description: Split Payment - Single Restaurant Owner under litigation - Pre-Auth Order Type
    @Test
    public void verifySplitPaymentForSingleRestaurantLitigationPreAuth_148454() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            homePage = loginPage.SSOLoginForSingleRestaurant(SingleResOwnerRestIsLitigation);
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            homePage.removeAllItemsFromCart();
            productListPage = homePage.selectSubMenuItem(BaseTest.getStringfromBundleFile("shopByArea"), BaseTest.getStringfromBundleFile("tables"));
            productDescriptionPage = productListPage.clickTableName(BaseTest.getStringfromBundleFile("productName"));
            shoppingCartPage = productDescriptionPage.clickAddToCartButton().clickViewCartAndCheckoutButton();
            checkoutPage = shoppingCartPage.clickProceedToCheckout();
            checkoutPage.selectShippingAddress(addressBook);
            checkoutPage.selectDeliveryMethod(BaseTest.getStringfromBundleFile("pickUp"));
            checkoutPage.verifyOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            paymentPage = checkoutPage.clickOnContinueButton();
            paymentPage.verifySplitPaymentOptionNotAvailable();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 148472
    // Test case Description: Split Payment - Single Restaurant Owner under litigation - 313 Order Type
    @Test
    public void verifySplitPaymentForSingleRestaurantLitigation313_148472() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            homePage = loginPage.SSOLoginForSingleRestaurant(SingleResOwnerRestIsLitigation);
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderType313"));
            productListPage = homePage.selectSubMenuItem(BaseTest.getStringfromBundleFile("shopByArea"), BaseTest.getStringfromBundleFile("tables"));
            productDescriptionPage = productListPage.clickTableName(BaseTest.getStringfromBundleFile("productName"));
            shoppingCartPage = productDescriptionPage.clickAddToCartButton().clickViewCartAndCheckoutButton();
            checkoutPage = shoppingCartPage.clickProceedToCheckout();
            checkoutPage.selectShippingAddress(addressBook);
            checkoutPage.selectDeliveryMethod(BaseTest.getStringfromBundleFile("pickUp"));
            checkoutPage.verifyOrderType(BaseTest.getStringfromBundleFile("orderType313"));
            paymentPage = checkoutPage.clickOnContinueButton();
            paymentPage.verifySplitPaymentOptionNotAvailable();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 148234
    // Test case Description: Capability to Split Payment Across Restaurants- Multi Restaurant Owner and No Restaurants Under litigation
    @Test
    public void verifySplitForMultiRestaurantNoLitigation_148234() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(multiResOwnerRestIsNotLitigation);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            productListPage = homePage.selectSubMenuItem(BaseTest.getStringfromBundleFile("shopByArea"), BaseTest.getStringfromBundleFile("tables"));
            productDescriptionPage = productListPage.clickTableName(BaseTest.getStringfromBundleFile("productName"));
            shoppingCartPage = productDescriptionPage.clickAddToCartButton().clickViewCartAndCheckoutButton();
            checkoutPage = shoppingCartPage.clickProceedToCheckout();
            checkoutPage.selectShippingAddress(addressBook);
            checkoutPage.selectDeliveryMethod(BaseTest.getStringfromBundleFile("pickUp"));
            checkoutPage.verifyOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            paymentPage = checkoutPage.clickOnContinueButton();
            paymentPage.selectPaymentMethodDropdown(BaseTest.getStringfromBundleFile("invoice"));
            paymentPage.verifySplitPaymentPopup();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 144328
    // Test case Description:  verify User is navigated to the Review & Submit Order Page Correct
    @Test
    public void verifyUserNavigatingToReviewOrderPage_144328() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            productListPage = homePage.selectSubMenuItem(BaseTest.getStringfromBundleFile("shopByArea"), BaseTest.getStringfromBundleFile("tables"));
            productDescriptionPage = productListPage.clickTableName(BaseTest.getStringfromBundleFile("productName"));
            shoppingCartPage = productDescriptionPage.clickAddToCartButton().clickViewCartAndCheckoutButton();
            checkoutPage = shoppingCartPage.clickProceedToCheckout();
            checkoutPage.selectDeliveryMethod(BaseTest.getStringfromBundleFile("shipFromVendor"));
            paymentPage = checkoutPage.clickOnContinueButton();
            paymentPage.selectPaymentMethodDropdown(BaseTest.getStringfromBundleFile("invoice"));
            reviewYourOrderPage = paymentPage.selectContinueButton();
            reviewYourOrderPage.verifyUserNavigateToReviewOrderPage();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 143936
    // Test case Description:Confirm 'Invoice' Payment for 313 or Pre-Auth
    @Test
    public void verifyPaymentMethodAvailableIsInvoice_143936() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            productListPage =homePage.navigateProductListPage();
            shoppingCartPage = productListPage.addItemsToCart(2);
            checkoutPage = shoppingCartPage.clickProceedToCheckout();
            checkoutPage.selectDeliveryMethod(BaseTest.getStringfromBundleFile("shipFromVendor"));
            paymentPage = checkoutPage.clickOnContinueButton();
            paymentPage.verifyPaymentMethodHasOnlyInvoice();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 144317
    // Test case Description: Terms and Conditions Not Accepted before the sales Order is created
    @Test
    public void verifyTermsAndConditionsNotAcceptedBeforeSalesOrderIsCreated_144317() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            productListPage = homePage.navigateProductListPage();
            shoppingCartPage = productListPage.addItemsToCart(2);
            checkoutPage = shoppingCartPage.clickProceedToCheckout();
            checkoutPage.selectDeliveryMethod(BaseTest.getStringfromBundleFile("shipFromVendor"));
            paymentPage = checkoutPage.clickOnContinueButton();
            reviewYourOrderPage.clickOnPlaceOrder();
            reviewYourOrderPage.clickOnCancel();
            reviewYourOrderPage.verifyUserNavigateToReviewOrderPage();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 143952
    // Test case Description: Terms and Conditions Acceptance before the sales Order is created
    @Test
    public void verifyTermsAndConditionsAcceptedBeforeSalesOrderIsCreated_143952() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            productListPage = homePage.navigateProductListPage();
            shoppingCartPage = productListPage.addItemsToCart(2);
            checkoutPage = shoppingCartPage.clickProceedToCheckout();
            checkoutPage.selectDeliveryMethod(BaseTest.getStringfromBundleFile("shipFromVendor"));
            paymentPage = checkoutPage.clickOnContinueButton();
            reviewYourOrderPage.clickOnPlaceOrder();
            orderConfirmationPage = reviewYourOrderPage.clickOnAccept();
            orderConfirmationPage.verifySalesOrderNumber();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }


    //TFS ID: 143938
    // Test case Description: Customers to confirm certain item Options before adding an Item to the Cart
    @Test
    public void verifyItemOptionsBeforeAddingItemToCart_143938() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            homePage.removeAllItemsFromCart();
            searchResultsPage = homePage.searchItemNameInSearchIcon(BaseTest.getStringfromBundleFile("itemName"));
            productDescriptionPage = searchResultsPage.navigateToPDPwithItemName(BaseTest.getStringfromBundleFile("itemName"));
            productDescriptionPage.clickOnAddToCart();
            //productDescriptionPage.clickOnAcceptBtn();
            shoppingCartPage= productDescriptionPage.clickViewCartAndAccept();
            shoppingCartPage.verifyItemAddedToCartAfterClickingAccept(BaseTest.getStringfromBundleFile("itemName"));
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 148448
    // Test case Description : Split Payment - Not Available with 313
    @Test
    public void verifySplitPaymentNotAvailableWith313PaymentType_148448() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderType313"));
            homePage.removeAllItemsFromCart();
            productListPage = homePage.navigateProductListPage();
            shoppingCartPage = productListPage.addItemsToCart(2);
            checkoutPage = shoppingCartPage.clickProceedToCheckout();
            checkoutPage.selectDeliveryMethod(BaseTest.getStringfromBundleFile("shipFromVendor"));
            checkoutPage.verifyOrderType(BaseTest.getStringfromBundleFile("orderType313"));
            paymentPage = checkoutPage.clickOnContinueButton();
            paymentPage.verifySplitPaymentOptionNotAvailable();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 148436
    // Test case Description : Capability to Split Payment Across Restaurants- Multi Restaurant Owner and at least one Restaurant Under litigation (logged in as a restaurant under litigation)
    @Test
    public void verifySplitPaymentCheckBoxForRestaurantUnderLitigation_148436() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(multiResOwnerAtLeastOneRestIsLitigation);
            homePage = switchRestaurantPage.switchRestaurant(multiResOwnerAtLeastOneRestIsLitigation.getLitigationStore());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            productListPage =homePage.navigateProductListPage();
            shoppingCartPage = productListPage.addItemsToCart(2);
            checkoutPage = shoppingCartPage.clickProceedToCheckout();
            checkoutPage.selectShippingAddress(addressBook);
            checkoutPage.selectDeliveryMethod(BaseTest.getStringfromBundleFile("shipFromVendor"));
            checkoutPage.verifyOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            paymentPage = checkoutPage.clickOnContinueButton();
            paymentPage.selectPaymentMethodDropdown(BaseTest.getStringfromBundleFile("invoice"));
            paymentPage.verifySplitPaymentOptionNotAvailable();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 148438
    // Test case Description : Capability to Split Payment Across Restaurants- Multi Restaurant Owner and at least one Restaurant Under litigation (logged in as a restaurant not under litigation)
    @Test
    public void verifySplitPaymentCheckBoxForRestaurantNotUnderLitigation_148438() throws Exception {
        try {
        	loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(multiResOwnerAtLeastOneRestIsLitigation);
            int restaurantListSize = switchRestaurantPage.restaurantListSize();
            homePage = switchRestaurantPage.switchRestaurant(multiResOwnerAtLeastOneRestIsLitigation.getNonLitigationStore());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            homePage.removeAllItemsFromCart();
            productListPage =homePage.navigateProductListPage();
            shoppingCartPage = productListPage.addItemsToCart(2);
            checkoutPage = shoppingCartPage.clickProceedToCheckout();
            checkoutPage.selectShippingAddress(addressBook);
            checkoutPage.selectDeliveryMethod(BaseTest.getStringfromBundleFile("shipFromVendor"));
            checkoutPage.verifyOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            paymentPage = checkoutPage.clickOnContinueButton();
            paymentPage.selectPaymentMethodDropdown(BaseTest.getStringfromBundleFile("invoice"));
            paymentPage.verifySplitPaymentPopup();
            paymentPage.verifySplitPaymentAmongRestaurantsNotUnderLitigation(restaurantListSize);
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    // 144360 - Placing an Order from a Quote (Status : Purchasing)
    // 148590 - Passing Information to Sales Order - Vendor
    @Test
    public void verifyPurchasingQuoteOrder_144360_148590() throws Exception {
        try {
        	loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            quotePage = homePage.clickQuotesHeaderMenu(BaseTest.getStringfromBundleFile("linkQuotes"));
            quotePage.verifyQuoteList();
            quoteDescriptionPage = quotePage.verifyQuoteWithStatus(BaseTest.getStringfromBundleFile("purchasing"));
            float reviewTotal = quoteDescriptionPage.verifySubTotal();
            reviewYourOrderPage = quoteDescriptionPage.clickReviewOrder();
            reviewYourOrderPage.verifyPaymentMethodHasOnlyInvoice();
            reviewYourOrderPage.verifyOrderSummary(reviewTotal);
            orderConfirmationPage = reviewYourOrderPage.clickSubmitButton();
            orderConfirmationPage.verifyQuoteOrderConfirmation();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }


    //TFS ID: 143933
    // Test case Description: Identification of Leased Items on Quote
    @Test
    public void verifyLeasedItemsOnQuote_143933() throws Exception {
        try {
			loginPage = goToHomePage(LoginPage.class, driverName);
			switchRestaurantPage = loginPage.SSOLogin(login);
			homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            requestQuotePage = homePage.clickRequestAQuote();
            requestQuotePage.selectItemForQuoteandSubmit("Refrigerated",BaseTest.getStringfromBundleFile("itemQty"));
            // Self-Serve High Profile Specialty Merchandiser
			//requestQuotePage.selectItemForQuoteandSubmit(BaseTest.getStringfromBundleFile("leasedItem"),BaseTest.getStringfromBundleFile("itemQty"));
			quotePage = homePage.clickQuotesHeaderMenu(BaseTest.getStringfromBundleFile("linkQuotes"));
			quotePage.verifyQuoteList();
			quotePage.verifyQuoteItemLeasability();
		} catch (Exception ex) {
			Logz.error(ex.getMessage());
			throw ex;
		}
    }

    //TFS ID: 143900
    // Test case Description: Order Type drop down is not available for not Approved User
    //All the users are pre-auth approved users therefore no data for this test case
    @Test
    public void verifyOrderTypeDropDown_143900() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            //userNotApprovedForPreAuth = (Login) this.App_Context.getBean("userNotApprovedForPreAuth");
            switchRestaurantPage = loginPage.SSOLogin(userNotApprovedForPreAuth);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.verifyOrderTypeDropDown();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 143919
    // Test case Description: Successful Split of Payment Across Restaurants
    @Test
    public void verifySuccessfulSplitPayment_143919() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            productListPage = homePage.navigateProductListPage();
            shoppingCartPage = productListPage.addItemsToCart(1);
            checkoutPage = shoppingCartPage.clickProceedToCheckout();
            checkoutPage.selectShippingAddress(addressBook);
            checkoutPage.selectDeliveryMethod(BaseTest.getStringfromBundleFile("pickUp"));
            checkoutPage.verifyOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            paymentPage = checkoutPage.clickOnContinueButton();
            paymentPage.selectPaymentMethodDropdown(BaseTest.getStringfromBundleFile("invoice"));
            paymentPage.verifySplitPaymentPopup();
            paymentPage.verifySplitRestaurantsAddAndDelete();
            paymentPage.verifySplitPaymentAmongRestaurants();
            reviewYourOrderPage = paymentPage.selectContinueButton();
            reviewYourOrderPage.clickOnPlaceOrder();
            orderConfirmationPage = reviewYourOrderPage.clickOnAccept();
            orderConfirmationPage.verifySalesOrderNumber();
        } catch (Exception ex) {
          Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 148440
    // Test case Description: Successful Split of Payment Across Restaurants - Choose Specific Restaurants
    @Test
    public void verifySuccessfulSplitForSpecificRestaurants_148440() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            productListPage = homePage.navigateProductListPage();
            shoppingCartPage = productListPage.addItemsToCart(1);
            checkoutPage = shoppingCartPage.clickProceedToCheckout();
            checkoutPage.selectShippingAddress(addressBook);
            checkoutPage.selectDeliveryMethod(BaseTest.getStringfromBundleFile("pickUp"));
            paymentPage = checkoutPage.clickOnContinueButton();
            paymentPage.selectPaymentMethodDropdown(BaseTest.getStringfromBundleFile("invoice"));
            paymentPage.verifySplitPaymentPopup();
            paymentPage.verifyAddSplitRestaurantAndAccept();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 144354
    // Test case Description: Customer Submits an Order on the Website, a Sales Order will be created in NetSuite
    @Test
    public void verifyOrderSubmitRequest_144354() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            productListPage = homePage.navigateProductListPage();
            shoppingCartPage = productListPage.addItemsToCart(2);
            checkoutPage = shoppingCartPage.clickProceedToCheckout();
            checkoutPage.selectShippingAddress(addressBook);
            checkoutPage.selectDeliveryMethod(BaseTest.getStringfromBundleFile("pickUp"));
            checkoutPage.verifyOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            paymentPage = checkoutPage.clickOnContinueButton();
            paymentPage.selectPaymentMethodDropdown(BaseTest.getStringfromBundleFile("invoice"));
            paymentPage.selectChangeAddressLink();
            reviewYourOrderPage = paymentPage.selectContinueButton();
            reviewYourOrderPage.verifyUserNavigateToReviewOrderPage();
            reviewYourOrderPage.clickOnPlaceOrder();
            orderConfirmationPage = reviewYourOrderPage.clickOnAccept();
            orderConfirmationPage.verifySalesOrderNumber();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }
    
    //TFS ID: 144337
    // Test case Description: Customer Submits an Order on the Website by entering Special Instructions and then a Sales Order is created and verify those instructions in NetSuite
    @Test
    public void verifySpecialInstructionsToTheOrders_144337() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            productListPage = homePage.navigateProductListPage();
            shoppingCartPage = productListPage.addItemsToCart(1);
            checkoutPage = shoppingCartPage.clickProceedToCheckout();
            checkoutPage.selectShippingAddress(addressBook);
            checkoutPage.selectDeliveryMethod(BaseTest.getStringfromBundleFile("pickUp"));
            checkoutPage.enterSpecialInstructions(BaseTest.getStringfromBundleFile("specialInstruction"));
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
    
 // TFS ID: 143909
 // Test case Description: Consolidation of Products with Single Consolidator
  	@Test
  	public void verifyConsolidationOfProducts_143909() throws Exception {
  		try {
  			loginPage = goToHomePage(LoginPage.class, driverName);
  			switchRestaurantPage = loginPage.SSOLogin(login);
  			homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
  			homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
  			productListPage = homePage.navigateProductListPage();
  			shoppingCartPage = productListPage.addItemsToCart(1);
  			checkoutPage = shoppingCartPage.clickProceedToCheckout();
  			checkoutPage.clickOnCreateConsolidatedShipment();
  			checkoutPage.clickOnSelectConsolidator();
  		} catch (Exception ex) {
  			Logz.error(ex.getMessage());
  			throw ex;
  		}
  	}

    // TFS ID: 143903
    //Test case Description: User can able to Create Order with Multiple Items
    @Test
    public void verifyUsercanabletoCreateOrderwithMultipleItems_143903() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderType313"));
            homePage.removeAllItemsFromCart();
            productListPage = homePage.navigateProductListPage();
            shoppingCartPage = productListPage.addItemsToCart(2);
            checkoutPage = shoppingCartPage.clickProceedToCheckout();
            checkoutPage.clickOnCreateConsolidatedShipment();
            checkoutPage.selectConsolidatorAndAccept(BaseTest.getStringfromBundleFile("consolidator"));
            //checkoutPage.clickOnSelectConsolidator();
            //checkoutPage.verifyPopupOrderVerification();
            String itemsAddedToShipping[] = checkoutPage.selectItemsToCreateShipment(2);
            //checkoutPage.clickOnCreateShipmentBtn();
            checkoutPage.clickContinueOnCheckOut();

            //checkoutPage.verifyItemsUnderShippmentSection(itemsAddedToShipping);
            //checkoutPage.selectShippingAddress(1);
            //checkoutPage.verifyItemsUnderShippmentSection(checkoutPage.selectItemsToCreateShipment(2));
            checkoutPage.selectDeliveryMethodFromDropdown(BaseTest.getStringfromBundleFile("pickUpFromVendor"));
            paymentPage = checkoutPage.clickOnContinueButton();
            float reviewTotal=checkoutPage.verifySubTotal();
            //paymentPage = checkoutPage.clickOnContinueButton();
            //paymentPage.selectPaymentMethodDropdown(BaseTest.getStringfromBundleFile("invoice"));
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

    // TFS ID: 143907
    //Test case Description: Consolidation of Products unable to be Consolidated
    @Test
    public void verifyConsolidationofProductsunabletobeConsolidated_143907() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderType313"));
            homePage.removeAllItemsFromCart();
            productListPage = homePage.navigateProductListPage();
            shoppingCartPage = productListPage.addItemsToCart(1);
            searchResultsPage = homePage.searchItemNameInSearchIcon(BaseTest.getStringfromBundleFile("searchItemName"));
            productDescriptionPage = searchResultsPage.navigateToPDPwithItemNumber();
            productDescriptionPage.clickAddToCartButton();
            shoppingCartPage = productDescriptionPage.clickViewCartAndCheckoutButton();
            checkoutPage = shoppingCartPage.clickProceedToCheckout();
            checkoutPage.clickOnCreateConsolidatedShipment();
            checkoutPage.clickOnSelectConsolidator();
            //checkoutPage.verifyPopupOrderVerification();
            //checkoutPage.selectConsolidatorAndAccept(BaseTest.getStringfromBundleFile("consolidator"));
            checkoutPage.selectItemsToCreateShipment(1);
            //checkoutPage.selectAllItemsCheckBox();
            //checkoutPage.verifyModalViewPopupMessage();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    // TFS ID: 143910
    //Test case Description: Restaurant Owner can create order with Multiple Address shipments
    @Test
    public void verifyRestaurantOwnercancreateorderwithMultipleAddressshipments_143910() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            homePage.removeAllItemsFromCart();
            productListPage = homePage.navigateProductListPage();
            shoppingCartPage = productListPage.addItemsToCart(3);
            checkoutPage = shoppingCartPage.clickProceedToCheckout();
            checkoutPage.clickOnShipToMultipleAddresses();
            checkoutPage.selectShippingAddress(0);
            //Due to Impact on Other Tests, selectItemsToCreateShipment return type is not set to void for now.
            checkoutPage.selectItemsToCreateShipment(3);
            checkoutPage.clickContinueOnCheckOut();
            checkoutPage.selectDeliveryMethodFromDropdown(BaseTest.getStringfromBundleFile("pickUpFromVendor"));
            float reviewTotal=checkoutPage.verifySubTotal();
            paymentPage = checkoutPage.clickOnContinueButton();

            reviewYourOrderPage = paymentPage.selectContinueButton();
            reviewYourOrderPage.verifyOrderSummary(reviewTotal);
            reviewYourOrderPage.clickOnPlaceOrder();
            orderConfirmationPage = reviewYourOrderPage.clickOnAccept();
            orderConfirmationPage.verifySalesOrderNumber();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    // TFS ID: 143915
    //Test case Description: Restaurant Owner having only one address in the Customer Record
    @Test
    public void verifyRestaurantOwnerHavingOnlyOneAddressInTheCustomerRecord_143915() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            homePage.removeAllItemsFromCart();
            productListPage = homePage.navigateProductListPage();
            shoppingCartPage = productListPage.addItemsToCart(4);
            checkoutPage = shoppingCartPage.clickProceedToCheckout();
            checkoutPage.clickOnShipToMultipleAddresses();
            //To Check Implementation with Functional Team
            //int noOfAddressesBefore = 4; //checkoutPage.getNoOfAddresses();
            checkoutPage.clickOnAddNewAddressButton();
            homePage.addAddressBook(addressBook);
            //To Check Implementation with Functional Team
            //int noOfAddressesAfter = checkoutPage.getNoOfAddresses();
            //checkoutPage.verifyNewlyAddressGetAdded(noOfAddressesBefore,noOfAddressesAfter);
            checkoutPage.clickContinueInShipment();
            //checkoutPage.selectShippingAddress(0);
            checkoutPage.verifyItemsUnderShippmentSection(checkoutPage.selectItemsToCreateShipment(4));
            //checkoutPage.clickOnCreateShipmentBtn();
            //checkoutPage.verifyItemsUnderShippmentSection(checkoutPage.selectItemsToCreateShipment(3));
            //checkoutPage.clickOnCreateShipmentBtn();
            //checkoutPage.createConsolidatedShipment();
            //checkoutPage.selectShippingAddress(1);
            //checkoutPage.verifyItemsUnderShippmentSection(checkoutPage.selectItemsToCreateShipment(2));
            paymentPage = checkoutPage.clickOnContinueButton();
            checkoutPage.selectDeliveryMethodFromDropdown(BaseTest.getStringfromBundleFile("pickUpFromVendor"));
            //checkoutPage.selectDeliveryMethodFromDropdown(BaseTest.getStringfromBundleFile("pickUpFromVendor"));
            //checkoutPage.selectDeliveryMethodFromDropdown(BaseTest.getStringfromBundleFile("pickUpFromVendor"));
            float reviewTotal=checkoutPage.verifySubTotal();
            paymentPage = checkoutPage.clickOnContinueButton();
            paymentPage.selectPaymentMethodDropdown(BaseTest.getStringfromBundleFile("invoice"));
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
}
