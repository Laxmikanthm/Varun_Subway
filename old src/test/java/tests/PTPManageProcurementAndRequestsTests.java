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
import pojos.OrderType;
import utils.Logz;

@ContextConfiguration({"classpath:/EOSBeans.xml","classpath:/LoginBeans.xml","classpath:/HomePageBeans.xml","classpath:/OrderTypeBeans.xml"})
@TestExecutionListeners(inheritListeners = false, listeners =
        {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})

public class PTPManageProcurementAndRequestsTests extends EOSWebContext {

    public LoginPage  loginPage;
    public ProductListPage productListPage;
    public SwitchRestaurantPage switchRestaurantPage;
    public HomePage homePage;
    public SearchResultsPage searchResultsPage;
    public ProductDescriptionPage pdpPage;
    public RequestQuotePage requestQuotePage;
    public QuoteConfirmationPage quoteConfirmationPage;

    @Autowired
    public Login login;
    @Autowired
    public Home home;
    @Autowired
    public OrderType orderTypePreAuth;

    //TFS ID: 146625
    //Test case Description: Special Order item by search an item for Quote button
    @Test
        public void verifySpecialOrderBySearchForQuote_146625() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(orderTypePreAuth.getOrderType());
            searchResultsPage = homePage.searchItemNameInSearchIcon(BaseTest.getStringfromBundleFile("itemNumber"));
            pdpPage = searchResultsPage.navigateToPDPwithItemNumber();
            pdpPage.enterSpecialInstructions(BaseTest.getStringfromBundleFile("instruction"));
            pdpPage.clickAddToQuoteButton();
            requestQuotePage=pdpPage.clickOnQuoteRequest();
            //requestQuotePage.enterComment(BaseTest.getStringfromBundleFile("comment"));
            quoteConfirmationPage = requestQuotePage.clickOnSubmitButtonAtBottom();
            quoteConfirmationPage.verifyQuoteOrderConfirmation();
        }
        catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 146626
    //Test case Description: Special Order item by clicking the Request for Quote button
    @Test
    public void verifySpecialOrderByClickingRequestQuote_146626() throws Exception {

        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(orderTypePreAuth.getOrderType());
            requestQuotePage = homePage.clickRequestAQuote();
            requestQuotePage.selectItemForQuoteandSubmit(BaseTest.getStringfromBundleFile("itemNumber"),BaseTest.getStringfromBundleFile("quantity"));
            requestQuotePage.enterComment(BaseTest.getStringfromBundleFile("comment"));
            quoteConfirmationPage = requestQuotePage.clickOnSubmitButtonAtTop();
            quoteConfirmationPage.verifyQuoteOrderConfirmation();
        }
        catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }
}
