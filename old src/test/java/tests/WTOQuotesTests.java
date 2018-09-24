package tests;

import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.QuotePage;
import utils.Logz;
import base.test.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import base.EOSWebContext;
import pages.SwitchRestaurantPage;
import pojos.Home;

@ContextConfiguration({"classpath:/EOSBeans.xml","classpath:/LoginBeans.xml","classpath:/HomePageBeans.xml"})
@TestExecutionListeners(inheritListeners = false, listeners = { DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class })

public class WTOQuotesTests extends EOSWebContext {

	public LoginPage loginPage;
	public HomePage homePage;
	public SwitchRestaurantPage switchRestaurantPage;
	public QuotePage quotePage;

	@Autowired
	public pojos.Login login;
	@Autowired
	public Home home;

	//TFS ID: 144344
	//Test case Description: Verify Review Product List for Equipment (Quote) - Review Quote
	@Test
	public void verifyReviewQuote_144344() throws Exception {
		try {
			loginPage = goToHomePage(LoginPage.class, driverName);
			switchRestaurantPage = loginPage.SSOLogin(login);
			homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
		    quotePage = homePage.selectAccountQuoteType(BaseTest.getStringfromBundleFile("quotes"));
			quotePage.reviewQuote();
		} catch (Exception ex) {
			Logz.error(ex.getMessage());
			throw ex;
		}
	}

	//TFS ID: 144352
	//Test case Description: Verify Review Product List for Equipment (Quote) - Filter Quote list
	@Test
	public void verifyFilterQuoteList_144352() throws Exception {
		try {
			loginPage = goToHomePage(LoginPage.class, driverName);
			switchRestaurantPage = loginPage.SSOLogin(login);
			homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
			quotePage = homePage.selectAccountQuoteType(BaseTest.getStringfromBundleFile("quotes"));
			quotePage.filterQuotes_Status(BaseTest.getStringfromBundleFile("StatusType"));
			quotePage.filterQuotes_Box2(BaseTest.getStringfromBundleFile("SortingType"));
		} catch (Exception ex) {
			Logz.error(ex.getMessage());
			throw ex;
		}
	}

	//TFS ID: 144347
	//Test case Description: Verify Review Product List for Equipment (Quote) - Download PDF
	@Test
	public void verifyDownloadPDF_144347() throws Exception {
		try {
			loginPage = goToHomePage(LoginPage.class, driverName);
			switchRestaurantPage = loginPage.SSOLogin(login);
			homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
			quotePage = homePage.selectAccountQuoteType(BaseTest.getStringfromBundleFile("quotes"));
			quotePage.pdfDownload();
		} catch (Exception ex) {
			Logz.error(ex.getMessage());
			throw ex;
		}
	}
}
