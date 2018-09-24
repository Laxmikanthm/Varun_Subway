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
import pages.Cart.ProductDescriptionPage;
import pages.Cart.SearchResultsPage;
import pojos.Home;
import pojos.Login;

@ContextConfiguration({"classpath:/EOSBeans.xml","classpath:/LoginBeans.xml","classpath:/HomePageBeans.xml"})
@TestExecutionListeners(inheritListeners = false, listeners =
        {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})


public class WTOManageSearchTests extends EOSWebContext {
    public LoginPage loginPage;
    
    @Autowired
    public Home home;
    @Autowired
    public Login login;
    
    public HomePage homePage;
    public SwitchRestaurantPage switchRestaurantPage;
    public ProductDescriptionPage productDescriptionPage;
    public SearchResultsPage searchResultsPage;


    //TFS ID: 151929, 151947, 151956
    //Test case Description: Search Bar Display and Item Search
    @Test
    public void verifySearchItem_151929_151947_151956() throws Exception {
        loginPage = goToHomePage(LoginPage.class, driverName);
        switchRestaurantPage = loginPage.SSOLogin(login);
        homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
        homePage.verifySearchDropdownAfterEnterText(BaseTest.getStringfromBundleFile("searchBoxText"));
    }

    //TFS ID: 151959
    //Test case Description: Search Item and Navigation to PDP
    @Test
    public void verifySearchItemandNavigateToPDP_151959() throws Exception{
        loginPage = goToHomePage(LoginPage.class, driverName);
        switchRestaurantPage = loginPage.SSOLogin(login);
        homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
        homePage.verifySearchDropdownAfterEnterText(BaseTest.getStringfromBundleFile("searchBoxText"));
        homePage.verifySearchItem();
    }

    //TFS ID: 151960
    //Test case Description: Search Criteria - Name
    @Test
    public void verifySearchItemWithName_151960() throws Exception
    {
        loginPage = goToHomePage(LoginPage.class, driverName);
        switchRestaurantPage = loginPage.SSOLogin(login);
        homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
        searchResultsPage=homePage.searchItemNameInSearchIcon(BaseTest.getStringfromBundleFile("counter"));
        productDescriptionPage=searchResultsPage.navigateToPDPwithOutItemName(BaseTest.getStringfromBundleFile("counter"));
        productDescriptionPage.verifyItemName();
    }

    //TFS ID: 151963
    //Test case Description: Search Criteria - Item Number
    @Test
    public void verifySearchItemNumber_151963() throws Exception
    {
        loginPage = goToHomePage(LoginPage.class, driverName);
        switchRestaurantPage = loginPage.SSOLogin(login);
        homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
        searchResultsPage = homePage.searchItemNumber();
        productDescriptionPage = searchResultsPage.navigateToPDPwithItemNumber();
        productDescriptionPage.verifyItemNumber();
    }
}
