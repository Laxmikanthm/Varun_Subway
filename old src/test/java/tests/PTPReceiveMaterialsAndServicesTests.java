package tests;

import base.EOSWebContext;
import base.test.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.MyAccountOverview.PurchaseHistoryPage;
import pages.SwitchRestaurantPage;
import pojos.Home;
import pojos.Login;
import utils.Logz;

@ContextConfiguration(locations = {"classpath:/EOSBeans.xml","classpath:/LoginBeans.xml", "classpath:/HomePageBeans.xml"})
@TestExecutionListeners(inheritListeners = false, listeners =
        {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})

public class PTPReceiveMaterialsAndServicesTests extends EOSWebContext {

    public LoginPage loginPage;
    public HomePage homePage;
    public SwitchRestaurantPage switchRestaurantPage;
    public PurchaseHistoryPage purchaseHistoryPage;

    @Autowired
    public Home home;
    @Autowired
    public Login login;

    //TFS ID: 146620
    //Test case Description: Franchisee can track Order Status through the website
    @Test
    public void verifyOrderStatus_146620() throws Exception{
        try{
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            purchaseHistoryPage = homePage.selectAccountTypeForPurchaseHistory(BaseTest.getStringfromBundleFile("PurchasesHistory"));
            purchaseHistoryPage.verifyPurchaseList();
        }
        catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }
}
