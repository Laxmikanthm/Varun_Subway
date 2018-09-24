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
import pages.SwitchRestaurantPage;
import pojos.Home;
import pojos.Login;
import pojos.OrderType;
import utils.Logz;

@ContextConfiguration(locations = {"classpath:/EOSBeans.xml","classpath:/LoginBeans.xml", "classpath:/HomePageBeans.xml","classpath:/OrderTypeBeans.xml"})
@TestExecutionListeners(inheritListeners = false, listeners =
        {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})

public class OTCCaptureAndProcessSalesOrdersTests extends EOSWebContext {

    public LoginPage loginPage;
    public HomePage homePage;
    public SwitchRestaurantPage switchRestaurantPage;

    @Autowired
    public Home home;
    @Autowired
    public Login login;
    @Autowired
    public OrderType orderType313;

    //TFS ID: 148442
    //Test case Description: 313 - Order Type Availability
    @Test
    public void verifyOrderTypeDropdown_148442() throws Exception {
        try{
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(orderType313.getOrderType());
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }
}