package tests;

import base.EOSWebContext;
import base.test.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.testng.annotations.Test;
import pages.Cart.ProductListPage;
import pages.HomePage;
import pages.LoginPage;
import pages.SwitchRestaurantPage;
import pojos.Home;
import pojos.Login;
import utils.Logz;

@ContextConfiguration(locations = {"classpath:/EOSBeans.xml","classpath:/LoginBeans.xml", "classpath:/HomePageBeans.xml"})
@TestExecutionListeners(inheritListeners = false, listeners =
        {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})

public class WTO313OrdersTests extends EOSWebContext {
    public LoginPage loginPage;
    public ProductListPage productListPage;
    public SwitchRestaurantPage switchRestaurantPage;
    public HomePage homePage;

    @Autowired
    public Login login;
    @Autowired
    public Home home;

    //TFS ID: 143640
    //Test case Description: Franchisees able to browse Product Catalog 313 replacement items
    @Test
    public void verifyFranchiseesBrowseProductCatalog_143640() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderType313"));
            productListPage = homePage.selectSubMenuItem(BaseTest.getStringfromBundleFile("shopByArea"), BaseTest.getStringfromBundleFile("tables"));
            productListPage.verifyProdutsDisplayedInPLP();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }
}
