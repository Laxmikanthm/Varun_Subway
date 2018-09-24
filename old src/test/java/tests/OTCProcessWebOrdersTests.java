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
import pages.Cart.CheckoutPage;
import pages.Cart.ProductDescriptionPage;
import pages.Cart.ProductListPage;
import pages.Cart.ShoppingCartPage;
import pages.MyAccountOverview.MyListPage;
import pojos.Home;
import pojos.Login;
import pojos.OrderType;
import utils.Logz;

@ContextConfiguration({"classpath:/EOSBeans.xml","classpath:/LoginBeans.xml","classpath:/HomePageBeans.xml","classpath:/OrderTypeBeans.xml"})
@TestExecutionListeners(inheritListeners = false, listeners =
        {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})

public class OTCProcessWebOrdersTests extends EOSWebContext {

    public LoginPage loginPage;
    public ProductListPage productListPage;
    public SwitchRestaurantPage switchRestaurantPage;
    public HomePage homePage;
    public MyListPage myListPage;
    public  ShoppingCartPage shoppingCartpage;
    public  CheckoutPage checkoutPage;
    public ProductDescriptionPage productDescriptionPage;

    @Autowired
    public Login login;
    @Autowired
    public Home home;
    @Autowired
    public OrderType orderTypePreAuth;

    //TFS ID: 147125
    //Test case Description: Editing Small-Wares Package
    @Test
    public void verifyEditingSmallWaresPackageItem_147125() throws Exception{
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(orderTypePreAuth.getOrderType());
            homePage.removeAllItemsFromCart();
            myListPage = homePage.selectAccountTypeWishList(BaseTest.getStringfromBundleFile("wishList"));
            myListPage.makeMyListEmpty();
            productListPage = homePage.navigateProductListPage();
            productDescriptionPage = productListPage.selectProductToAddToWishList();
            productDescriptionPage.addProductToWishList(BaseTest.getStringfromBundleFile("wishList"));
            myListPage =  productListPage.goToMyWishList(BaseTest.getStringfromBundleFile("wishList"));
            myListPage.editQuantityAndVerifyItem();
            shoppingCartpage=myListPage.addItemFromSmallWarePackage();
            checkoutPage = shoppingCartpage.clickProceedToCheckout();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 147126
    //Test case Description: Save For Later Small-Wares Package
    @Test
    public void verifySaveForLaterSmallWaresPackage_147126() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(orderTypePreAuth.getOrderType());
            homePage.removeAllItemsFromCart();
            myListPage = homePage.selectAccountTypeWishList(BaseTest.getStringfromBundleFile("wishList"));
            myListPage.makeMyListEmpty();
            productListPage = homePage.navigateProductListPage();
            productDescriptionPage = productListPage.selectProductToAddToWishList();
            productDescriptionPage.addProductToWishList(BaseTest.getStringfromBundleFile("wishList"));
            myListPage =  productListPage.goToMyWishList(BaseTest.getStringfromBundleFile("wishList"));
            shoppingCartpage = myListPage.addItemFromSmallWarePackage();
            shoppingCartpage.navigateToAnItemToSaveForLater();
            shoppingCartpage.verifySaveItemsForLaterInCart();
            shoppingCartpage.clickOnMoveToCart();
            checkoutPage = shoppingCartpage.clickProceedToCheckout();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 147127
    //Test case Description: Remove Small-Wares Package items
    @Test
    public void verifyRemoveSmallWaresPackageItem_147127() throws Exception{
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(orderTypePreAuth.getOrderType());
            homePage.removeAllItemsFromCart();
            myListPage = homePage.selectAccountTypeWishList(BaseTest.getStringfromBundleFile("wishList"));
            myListPage.makeMyListEmpty();
            productListPage = homePage.navigateProductListPage();
            productDescriptionPage = productListPage.selectProductToAddToWishList();
            productDescriptionPage.addProductToWishList(BaseTest.getStringfromBundleFile("wishList"));
            myListPage =  productListPage.goToMyWishList(BaseTest.getStringfromBundleFile("wishList"));
            shoppingCartpage = myListPage.addItemFromSmallWarePackage();
            shoppingCartpage.removeItemFromCart();
            shoppingCartpage.verifyItemUnderCart();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }
}