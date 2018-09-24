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
import pojos.AddressBook;
import pojos.Home;
import pojos.Login;
import utils.Logz;


@ContextConfiguration(locations = {"classpath:/EOSBeans.xml","classpath:/LoginBeans.xml", "classpath:/HomePageBeans.xml", "classpath:/ProductListingBeans.xml"})
@TestExecutionListeners(inheritListeners = false, listeners =
        {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})

public class WTOPaymentGatewaysTests extends EOSWebContext {
    public LoginPage loginPage;
    public HomePage homePage;
    public ProductListPage productListPage;
    public ShoppingCartPage shoppingCartPage;
    public CheckoutPage checkoutPage;
    public PaymentPage paymentPage;  
    public SwitchRestaurantPage switchRestaurantPage;
    
    @Autowired
    public Login login;
    @Autowired
    public Home home;

    //TFS ID: 144397
    //Test case Description: Navigate to Tax Inclusion to Orders
    @Test
    public void verifyNavigateToTaxInclusionToOrders_144397() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            homePage.removeAllItemsFromCart();
            productListPage = homePage.selectSubMenuItem(BaseTest.getStringfromBundleFile("shopByArea"), BaseTest.getStringfromBundleFile("tables"));
            shoppingCartPage = productListPage.addItemsToCart(1);
            checkoutPage = shoppingCartPage.clickProceedToCheckout();
            checkoutPage.verifyTaxIncludedInTotalPrice();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 144394
    //Test case Description: Verification of Payment option shown will be type Invoice
    @Test
    public void verifyPaymentOptionIsInvoiceInPaymentMethodDropdown_144394() throws Exception {
        try {
        	loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            homePage.removeAllItemsFromCart();
            productListPage = homePage.selectSubMenuItem(BaseTest.getStringfromBundleFile("shopByArea"), BaseTest.getStringfromBundleFile("tables"));
            shoppingCartPage = productListPage.addItemsToCart(1);
            checkoutPage = shoppingCartPage.clickProceedToCheckout();
            checkoutPage.selectDeliveryMethod(BaseTest.getStringfromBundleFile("deliveryMethod_1"));
            checkoutPage.verifyOrderType(BaseTest.getStringfromBundleFile("Pre-Auth"));
            paymentPage=checkoutPage.clickOnContinueButton();
            paymentPage.selectPaymentMethodDropdown(BaseTest.getStringfromBundleFile("invoice"));
            Logz.step("Invoice type is available in Payment Method");
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //144324: Replicate Current Uplift on Order for Freight
    //144399: Desire to have accurate freight added to the Order
    @Test
    public void verifyAccurateFreightAddedToOrder_144399_144324() throws Exception {
        try {
        	loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
            homePage.removeAllItemsFromCart();
            productListPage = homePage.selectSubMenuItem(BaseTest.getStringfromBundleFile("shopByArea"), BaseTest.getStringfromBundleFile("tables"));
            shoppingCartPage = productListPage.addItemsToCart(1);
            checkoutPage = shoppingCartPage.clickProceedToCheckout();

            checkoutPage.selectDeliveryMethod(BaseTest.getStringfromBundleFile("deliveryMethod_1"));
            checkoutPage.verifyShippingFromVendorTenPerOfOrderTotal();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 144414
    //Test case Description: User Views Landing page in English
    @Test
    public void verifyLandingPage_144414() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.verifyDomainInCurrentUrl();
            homePage.verifyLanguageInCurrentUrl();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }
}