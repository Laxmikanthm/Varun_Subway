package tests;

import base.EOSWebContext;
import base.test.BaseTest;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
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
import pojos.OrderType;
import util.Common;
import utils.Logz;

@ContextConfiguration(locations = {"classpath:/EOSBeans.xml","classpath:/LoginBeans.xml","classpath:/AddressBookBeans.xml", "classpath:/HomePageBeans.xml", "classpath:/ProductListingBeans.xml","classpath:/OrderTypeBeans.xml"})
@TestExecutionListeners(inheritListeners = false, listeners =
        {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})


public class WTOPromotionsTests extends EOSWebContext{
    public LoginPage<RemoteWebDriver> loginPage;
    @Autowired
    public Home home;
    @Autowired
    public Login login;
    @Autowired
    public AddressBook addressBook;
    @Autowired
    public OrderType orderTypePreAuth;

    public HomePage homePage;
    public SwitchRestaurantPage switchRestaurantPage;
    public ProductListPage productListPage;
    public ShoppingCartPage shoppingCartPage;
    public CheckoutPage checkoutPage;
    public PaymentPage paymentPage;
    public ReviewYourOrderPage reviewYourOrderPage;
    public OrderConfirmationPage orderConfirmationPage;
    public ProductDescriptionPage productDescriptionPage;

    //TFS ID: 143883
    //Test case Description: Verify Vendor Promotions
    @Test
    public void verifyVendorPromotions_143883() throws Exception {
        try{
        	loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            homePage.removeAllItemsFromCart();
            productListPage = homePage.selectSubMenuItem(BaseTest.getStringfromBundleFile("shopByArea"), BaseTest.getStringfromBundleFile("tables"));
            productDescriptionPage = productListPage.clickTableName(BaseTest.getStringfromBundleFile("productName"));
            shoppingCartPage = productDescriptionPage.clickAddToCartButton().clickViewCartAndCheckoutButton();
            shoppingCartPage.verifyItemsInCart();
            checkoutPage = shoppingCartPage.clickProceedToCheckout();
            checkoutPage.enterPromoCode(BaseTest.getStringfromBundleFile("testCust"));
            checkoutPage.verifyDiscountIsAppliedToTotal();
            checkoutPage.selectShippingAddress(addressBook);
            checkoutPage.selectDeliveryMethod(BaseTest.getStringfromBundleFile("pickUp"));
            checkoutPage.verifyOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            float reviewTotal=checkoutPage.verifySubTotal();
            //Change Address Link in Shipping Address Page
            checkoutPage.selectShippingAddress(addressBook);
            paymentPage = checkoutPage.clickOnContinueButton();
            paymentPage.selectPaymentMethodDropdown(BaseTest.getStringfromBundleFile("invoice"));
            reviewYourOrderPage = paymentPage.selectContinueButton();
            reviewYourOrderPage.verifyUserNavigateToReviewOrderPage();
            reviewYourOrderPage.EditAddress();
            reviewYourOrderPage.verifyOrderSummary(reviewTotal);
            reviewYourOrderPage.clickOnPlaceOrder();
            orderConfirmationPage = reviewYourOrderPage.clickOnAccept();
            orderConfirmationPage.verifySalesOrderNumber();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 143904
    //Test case Description: Validate Item-based promotions Limited to certain Vendors
    @Test
    public void verifyItemBasedPromotionsEligibleVendor_143904() throws Exception {
        try{
        	loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            homePage.removeAllItemsFromCart();
            productListPage = homePage.selectSubMenuItem(BaseTest.getStringfromBundleFile("shopByArea"), BaseTest.getStringfromBundleFile("tables"));
            productListPage.clickVendorOptions(BaseTest.getStringfromBundleFile("Plymold"));
            productDescriptionPage=productListPage.clickTableName(BaseTest.getStringfromBundleFile("productName"));
            shoppingCartPage = productDescriptionPage.clickAddToCartButton().clickViewCartAndCheckoutButton();
            checkoutPage = shoppingCartPage.clickProceedToCheckout();
            checkoutPage.enterPromoCode(BaseTest.getStringfromBundleFile("testPlyItems"));
            checkoutPage.verifyDiscountIsAppliedToTotal();
            checkoutPage.selectShippingAddress(addressBook);
            //checkoutPage.createConsolidatedShipment();
            checkoutPage.selectDeliveryMethod(BaseTest.getStringfromBundleFile("pickUp"));
            //checkoutPage.selectDeliveryMethodFromDropdown(BaseTest.getStringfromBundleFile("pickUpFromVendor"));
            paymentPage = checkoutPage.clickOnContinueButton();
            float reviewTotal=paymentPage.verifySubTotal();
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

    //TFS ID: 143906
    //Test case Description: Validate Item-based Promotion to not eligible Vendor
    @Test
    public void verifyItemBasedPromotionsNotEligibleVendor_143906() throws Exception {
        try{
        	loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            productListPage = homePage.navigateProductListPage();
            productListPage.clickVendorOptions(BaseTest.getStringfromBundleFile("dukeManufacturing"));
            productDescriptionPage=productListPage.clickingOnItem();
            shoppingCartPage = productDescriptionPage.clickAddToCartButton().clickViewCartAndCheckoutButton();
            checkoutPage = shoppingCartPage.clickProceedToCheckout();
            checkoutPage.enterPromoCode(BaseTest.getStringfromBundleFile("plytbl10"));
            checkoutPage.verifyNoPriceReductionAfterPromo();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 143913
    //Test case Description: Validate Item-based promotion to eligible Vendor and not eligible Item
    @Test
    public void verifyItemBasedPromotionsEligibleVendorAndNotEligibleItem_143913() throws Exception {
        try{
        	loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(orderTypePreAuth.getOrderType());
            productListPage = homePage.selectSubMenuItem(BaseTest.getStringfromBundleFile("shopByArea"), BaseTest.getStringfromBundleFile("tables"));
            productListPage.clickVendorOptions(BaseTest.getStringfromBundleFile("Plymold"));
            productDescriptionPage= productListPage.clickingOnItem();
            shoppingCartPage = productDescriptionPage.clickAddToCartButton().clickViewCartAndCheckoutButton();
            checkoutPage = shoppingCartPage.clickProceedToCheckout();
            checkoutPage.enterPromoCode(BaseTest.getStringfromBundleFile("plytbl10"));
            checkoutPage.verifyNoPriceReductionAfterPromo();
        } catch(Exception ex){
            Logz.error(ex.getMessage());
            throw ex;
        }
    }
}