package tests;
import base.EOSWebContext;
import base.test.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.testng.annotations.Test;
import pages.*;
import pages.Cart.*;
import pojos.*;
import utils.Logz;

@ContextConfiguration(locations = {"classpath:/EOSBeans.xml","classpath:/LoginBeans.xml","classpath:/AddressBookBeans.xml", "classpath:/HomePageBeans.xml", "classpath:/ProductListingBeans.xml","classpath:/ItemBeans.xml"})
@TestExecutionListeners(inheritListeners = false, listeners =
        {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})

public class EndToEndTests extends EOSWebContext {
    public LoginPage loginPage;
    public HomePage homePage;
    public SwitchRestaurantPage switchRestaurantPage;
    public ProductListPage productListPage;
    public ShoppingCartPage shoppingCartPage;
    public CheckoutPage checkoutPage;
    public PaymentPage paymentPage;
    public ReviewYourOrderPage reviewYourOrderPage;
    public OrderConfirmationPage orderConfirmationPage;
    public CommonMethodsPage commonMethodsPage;

    @Autowired
    public Login login;
    @Autowired
    public AddressBook addressBook;
    @Autowired
    public Home home;
    @Autowired
    public Login singleRestaurantOwnerIsNotLitigation;
    @Autowired
    public Login SingleResOwnerRestIsLitigation;
    @Autowired
    public Login multiResOwnerRestIsNotLitigation;
    @Autowired
    public Login multiResOwnerAtLeastOneRestIsLitigation;


    //TFS ID: 158814
    //Test case Description: Single Restaurant Owner and Restaurant Not Under Litigation - Order Total < Pre-Auth Auto Approve Limit
    @Test
    public void verifyOrderTotalLessThanPreAuthLimit_158814() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            homePage = loginPage.SSOLoginForSingleRestaurant(singleRestaurantOwnerIsNotLitigation);
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            homePage.removeAllItemsFromCart();
            homePage.navigateProductListPage();
            commonMethodsPage = homePage.gotoCommonMethodsPage();
            Double orderLimit = commonMethodsPage.restaurantsWithNoLitigation(BaseTest.getStringfromBundleFile("preAuthOrderLimitType"),0);
            shoppingCartPage =commonMethodsPage.addToCartBasedOnOrderLimit(orderLimit,BaseTest.getStringfromBundleFile("headerMenuType"));
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

    //TFS ID: 158817
    //Test case Description: Single Restaurant Owner and Restaurant is Under Litigation -  Order Total < Litigation Pre-Auth  Limit
    @Test
    public void verifyOrderTotalLessThanPreAuthLimit_158817() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            homePage = loginPage.SSOLoginForSingleRestaurant(SingleResOwnerRestIsLitigation);
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            homePage.removeAllItemsFromCart();
            homePage.navigateProductListPage();
            commonMethodsPage = homePage.gotoCommonMethodsPage();
            Double orderLimit = commonMethodsPage.restaurantsWithOneLitigation(BaseTest.getStringfromBundleFile("litigation"),null,0);
            shoppingCartPage =commonMethodsPage.addToCartBasedOnOrderLimit(orderLimit,BaseTest.getStringfromBundleFile("headerMenuType"));
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

    //TFS ID: 158815
    //Test case Description: Single Restaurant Owner and Restaurant Not Under Litigation - Pre-Auth Auto Approve Limit < Order Total < Pre-Auth Maximum Order Limit
    @Test
    public void verifyOrderTotalLessThanMaxPreAuthLimit_158815() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            homePage = loginPage.SSOLoginForSingleRestaurant(singleRestaurantOwnerIsNotLitigation);
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            homePage.removeAllItemsFromCart();
            homePage.navigateProductListPage();
            commonMethodsPage = homePage.gotoCommonMethodsPage();
            Double preAuthLimit = commonMethodsPage.restaurantsWithNoLitigation(BaseTest.getStringfromBundleFile("preAuthOrderLimitType"),0);
            Double maxPreAuthLimit = commonMethodsPage.restaurantsWithNoLitigation(BaseTest.getStringfromBundleFile("maxPreAuthOrderLimitType"),0);
            shoppingCartPage = commonMethodsPage.addToCartBasedOnPreAuthAndMaxLimit(preAuthLimit,maxPreAuthLimit,BaseTest.getStringfromBundleFile("headerMenuType"));
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

    //TFS ID: 158828
    //Test case Description: Multi-Restaurant Owner and at least One Restaurant is Under Litigation (Logged in as a Restaurant under litigation) - Order Total > Litigation Pre-Auth Limit
    @Test
    public void verifyOrderTotalGreaterThanPreAuthLimit_158828() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(multiResOwnerAtLeastOneRestIsLitigation);
            homePage = switchRestaurantPage.switchRestaurant(multiResOwnerAtLeastOneRestIsLitigation.getLitigationStore());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            homePage.removeAllItemsFromCart();
            commonMethodsPage = homePage.gotoCommonMethodsPage();
            Double orderLimit = commonMethodsPage.restaurantsWithOneLitigation(BaseTest.getStringfromBundleFile("litigation"),null ,0);
            productListPage = homePage.navigateProductListPage();
            shoppingCartPage = commonMethodsPage.addToCartBasedOnOrderLimit(orderLimit,BaseTest.getStringfromBundleFile("headerMenuType"));
            shoppingCartPage.verifyProductCheckoutButton();
            shoppingCartPage.verifyErrorMessage();
        }
        catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 158825
    //Test case Description: Auto-Approval of Pre-Auth Order: Multi-Restaurant Owner and at least One Restaurant is Under Litigation (Logged in as a Restaurant under litigation) - Order Total < Litigation Pre-Auth Limit
    @Test
    public void verifyOrderTotalLessThanPreAuthLimit_158825() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(multiResOwnerAtLeastOneRestIsLitigation);
            int noOfStores = switchRestaurantPage.getRestaurantDropdown().getListOptions().size();
            homePage = switchRestaurantPage.switchRestaurant(multiResOwnerAtLeastOneRestIsLitigation.getLitigationStore());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            homePage.removeAllItemsFromCart();
            homePage.navigateProductListPage();
            commonMethodsPage = homePage.gotoCommonMethodsPage();
            Double orderLimit = commonMethodsPage.restaurantsWithOneLitigation(BaseTest.getStringfromBundleFile("litigation"),null,noOfStores );
            shoppingCartPage = commonMethodsPage.addToCartBasedOnOrderLimit(orderLimit,BaseTest.getStringfromBundleFile("headerMenuType"));
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
        }
        catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 158816
    //Test case Description: Single Restaurant Owner and Restaurant Not Under Litigation - Order Total > Pre-Auth Maximum Order Limit
    @Test
    public void verifySingleRestaurantOwnerandRestaurantNotUnderLitigation_158816() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            homePage = loginPage.SSOLoginForSingleRestaurant(singleRestaurantOwnerIsNotLitigation);
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            homePage.removeAllItemsFromCart();
            homePage.navigateProductListPage();
            commonMethodsPage = homePage.gotoCommonMethodsPage();
            Double orderLimit = commonMethodsPage.restaurantsWithNoLitigation(BaseTest.getStringfromBundleFile("maxPreAuthOrderLimitType"),0);
            shoppingCartPage = commonMethodsPage.addToCartOverMaxPreAuthLimit(orderLimit,BaseTest.getStringfromBundleFile("headerMenuType"));
            shoppingCartPage.verifyProductCheckoutButton();
            shoppingCartPage.verifyErrorMessage();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 158818
    //Test case Description: Single Restaurant Owner and Restaurant is Under Litigation - Order Total > Litigation Pre-Auth Limit
    @Test

    public void verifySingleRestaurantOwnerandRestaurantisUnderLitigation_158818() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            homePage = loginPage.SSOLoginForSingleRestaurant(SingleResOwnerRestIsLitigation);
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            homePage.removeAllItemsFromCart();
            homePage.navigateProductListPage();
            commonMethodsPage = homePage.gotoCommonMethodsPage();
            Double orderLimit = commonMethodsPage.restaurantsWithOneLitigation(BaseTest.getStringfromBundleFile("litigation"),null,0);
            shoppingCartPage = commonMethodsPage.addToCartOverMaxPreAuthLimit(orderLimit,BaseTest.getStringfromBundleFile("headerMenuType"));
            //shoppingCartPage.verifyProductCheckoutButton();
            shoppingCartPage.verifyErrorMessage();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 158824
    //Test case Description: Multi-Restaurant Owner and No Restaurants are Under Litigation - Order Total > Pre-Auth Maximum Order Limit
    @Test
    public void verifyMultiRestaurantOwnerandNoRestaurantsareUnderLitigation_158824() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(multiResOwnerRestIsNotLitigation);
            int noOfStores = switchRestaurantPage.getRestaurantDropdown().getListOptions().size();
            homePage = switchRestaurantPage.switchRestaurant(multiResOwnerRestIsNotLitigation.getStoreToSelect());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            homePage.removeAllItemsFromCart();
            homePage.navigateProductListPage();
            commonMethodsPage = homePage.gotoCommonMethodsPage();
            Double orderLimit = commonMethodsPage.restaurantsWithNoLitigation(BaseTest.getStringfromBundleFile("maxPreAuthOrderLimitType"),noOfStores);
            shoppingCartPage = commonMethodsPage.addToCartOverMaxPreAuthLimit(orderLimit,BaseTest.getStringfromBundleFile("headerMenuType"));
            //shoppingCartPage.verifyProductCheckoutButton();
            shoppingCartPage.verifyErrorMessage();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 158889
    //Test case Description:  Multi-Restaurant Owner and at least One Restaurant is Under Litigation (Logged in as a Restaurant not under litigation)-   Order Total > Pre-Auth Maximum Order Limit
    @Test
    public void verifyMultiRestaurantOwnerandatleastOneRestaurantisUnderLitigation_158889() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(multiResOwnerAtLeastOneRestIsLitigation);
            int noOfStores = switchRestaurantPage.getRestaurantDropdown().getListOptions().size();
            homePage = switchRestaurantPage.switchRestaurant(multiResOwnerAtLeastOneRestIsLitigation.getNonLitigationStore());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            homePage.removeAllItemsFromCart();
            homePage.navigateProductListPage();
            commonMethodsPage = homePage.gotoCommonMethodsPage();
            Double orderLimit = commonMethodsPage.restaurantsWithOneLitigation(BaseTest.getStringfromBundleFile("nonLitigation"),BaseTest.getStringfromBundleFile("maxPreAuthOrderLimitType"),noOfStores);
            shoppingCartPage = commonMethodsPage.addToCartOverMaxPreAuthLimit(orderLimit,BaseTest.getStringfromBundleFile("headerMenuType"));
            shoppingCartPage.verifyProductCheckoutButton();
            shoppingCartPage.verifyErrorMessage();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 158885
    //Test case Description:ETE 14 - Auto-Approval of Pre-Auth Order:  Multi-Restaurant Owner and at least One Restaurant is Under Litigation (Logged in as a Restaurant not under litigation)-  Order Total <  Pre-Auth Auto-Approve  Limit
    @Test
    public void verifyMultiRestaurantOwnerandatleastOneRestaurantisUnderLitigation_158885() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(multiResOwnerAtLeastOneRestIsLitigation);
            int noOfStores = switchRestaurantPage.getRestaurantDropdown().getListOptions().size();
            homePage = switchRestaurantPage.switchRestaurant(login.getNonLitigationStore());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            homePage.removeAllItemsFromCart();
            homePage.navigateProductListPage();
            commonMethodsPage = homePage.gotoCommonMethodsPage();
            Double orderLimit=commonMethodsPage.restaurantsWithOneLitigation("nonLitigationStore","PreAuthAutoApprove",2);
            shoppingCartPage = commonMethodsPage.addToCartBasedOnOrderLimit(orderLimit,BaseTest.getStringfromBundleFile("headerMenuType"));
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
        }
        catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 158888
    //Test case Description:ETE 15 - Auto-Approval of Pre-Auth Order:  Multi-Restaurant Owner and at least One Restaurant is Under Litigation (Logged in as a Restaurant not under litigation)-  Pre-Auth Auto-Approve  Limit < Order Total < Pre-Auth Maximum Order Limit
    @Test
    public void verifyMultiRestaurantOwnerandatleastOneRestaurantisUnderLitigation_158888() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(multiResOwnerAtLeastOneRestIsLitigation);
            int noOfStores = switchRestaurantPage.getRestaurantDropdown().getListOptions().size();
            homePage = switchRestaurantPage.switchRestaurant(multiResOwnerAtLeastOneRestIsLitigation.getNonLitigationStore());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            homePage.removeAllItemsFromCart();
            homePage.navigateProductListPage();
            commonMethodsPage = homePage.gotoCommonMethodsPage();
            Double preAuthOrderLimit=commonMethodsPage.restaurantsWithOneLitigation("nonLitigationStore","PreAuthAutoApprove",2);
            Double maxPreAuthOrderLimit=commonMethodsPage.restaurantsWithOneLitigation("nonLitigationStore","MaxPreAuth",2);
            shoppingCartPage = commonMethodsPage.addToCartBasedOnPreAuthAndMaxLimit(preAuthOrderLimit,maxPreAuthOrderLimit,BaseTest.getStringfromBundleFile("headerMenuType"));
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
        }
        catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 158820,158822
    //Test case Description:Multi-Restaurant Owner and No Restaurants are Under Litigation -  Order Total <  Pre-Auth Auto Approve Limit
    @Test
    public void verifyOrderTotalLessThanPreAuthLimit_158820_158822() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(multiResOwnerRestIsNotLitigation);
            int noOfStores = switchRestaurantPage.getRestaurantDropdown().getListOptions().size();
            homePage = switchRestaurantPage.switchRestaurant(multiResOwnerRestIsNotLitigation.getStoreToSelect());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            homePage.removeAllItemsFromCart();
            homePage.navigateProductListPage();
            commonMethodsPage = homePage.gotoCommonMethodsPage();
            Double orderLimit = commonMethodsPage.restaurantsWithNoLitigation(BaseTest.getStringfromBundleFile("preAuthOrderLimitType"),4);
            shoppingCartPage =commonMethodsPage.addToCartBasedOnOrderLimit(orderLimit,BaseTest.getStringfromBundleFile("headerMenuType"));
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
        }
        catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }
}