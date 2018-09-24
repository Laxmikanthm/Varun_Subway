package tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.testng.annotations.Test;

/**
 * Created by E001891 on 12/19/2017.
 */
import base.EOSWebContext;
import pages.*;
import pages.Cart.ProductDescriptionPage;
import pages.Cart.ProductListPage;
import pages.Cart.ProductReviewPage;
import pojos.Home;
import pojos.Login;
import utils.Logz;
import base.EOSWebContext;
import base.test.BaseTest;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
import pages.ShopByArea.ShopByAreaPage;
import pojos.Home;
import pojos.Login;
import utils.Logz;
import pojos.Home;
import pojos.ProductListing;

import java.rmi.Remote;

@ContextConfiguration({"classpath:/EOSBeans.xml","classpath:/LoginBeans.xml", "classpath:/HomePageBeans.xml" })
@TestExecutionListeners(inheritListeners = false, listeners = { DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class })

public class WTOProductReviewsTests extends EOSWebContext {
	public LoginPage loginPage;
	public HomePage homePage;
	public ProductListPage productListPage;
	public ProductDescriptionPage productDescriptionPage;
	public SwitchRestaurantPage switchRestaurantPage;
	public ProductReviewPage productReviewPage;

	@Autowired
	public Login login;
	@Autowired
	public Home home;

	//TFS ID: 143888
	//TFS ID: 143892
	//Test case Description: Users can Submit Product Reviews via the Website
	//Test case Description: Product Review Record in NetSuite
	@Test
	public void verifyUsersSubmitProductReviewsViaWebsite_143888_143892() throws Exception {
		try{
			loginPage = goToHomePage(LoginPage.class, driverName);
			switchRestaurantPage = loginPage.SSOLogin(login);
			homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
			productListPage = homePage.selectSubMenuItem(BaseTest.getStringfromBundleFile("shopByArea"), BaseTest.getStringfromBundleFile("tables"));
            productDescriptionPage = productListPage.clickTableName(BaseTest.getStringfromBundleFile("productName"));
            productReviewPage=productDescriptionPage.clickReviewButton();
            productReviewPage.verifySubmitProductReview();
		}catch (Exception ex) {
			Logz.error(ex.getMessage());
			throw ex;
		}
	}
}
