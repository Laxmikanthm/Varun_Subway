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
import pojos.Home;
import pojos.Login;
import utils.Logz;

@ContextConfiguration({"classpath:/EOSBeans.xml","classpath:/LoginBeans.xml","classpath:/HomePageBeans.xml"})
@TestExecutionListeners(inheritListeners = false, listeners =
        {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})

public class WTOManageSiteTests extends EOSWebContext {
    public LoginPage loginPage;
    public ProductListPage productListPage;
    public SwitchRestaurantPage switchRestaurantPage;
    public HomePage homePage;
    public CheckoutPage checkoutPage;
    public ShoppingCartPage shoppingCartPage;
    
    @Autowired
    public Home home;
    @Autowired
    public Login login;

    //TFS ID: 143930
    //Test case Description: Verify secure domain for checkout
    @Test
    public void verifySecureDomainForCheckout_143930() throws Exception {
        try{
        	loginPage = goToHomePage(LoginPage.class, driverName);
			switchRestaurantPage = loginPage.SSOLogin(login);
			homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
			homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
			homePage.removeAllItemsFromCart();
            productListPage = homePage.selectSubMenuItem(BaseTest.getStringfromBundleFile("shopByArea"), BaseTest.getStringfromBundleFile("tables"));
            shoppingCartPage = productListPage.addItemsToCart(1);
            checkoutPage = shoppingCartPage.clickProceedToCheckout();
			checkoutPage.verifyURLbeginsWithhttps();
        }
        catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }
}



