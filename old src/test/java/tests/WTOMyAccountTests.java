package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import pages.Cart.*;
import pages.MyAccountOverview.*;
import pojos.AddressBook;
import pojos.OrderType;
import utils.Logz;
import base.test.BaseTest;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import base.EOSWebContext;
import pages.HomePage;
import pages.LoginPage;
import pojos.Home;
import pojos.Login;

@ContextConfiguration({ "classpath:/EOSBeans.xml","classpath:/LoginBeans.xml","classpath:/AddressBookBeans.xml", "classpath:/HomePageBeans.xml","classpath:/OrderTypeBeans.xml"})
@TestExecutionListeners(inheritListeners = false, listeners = { DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class })

public class WTOMyAccountTests extends EOSWebContext {

	public LoginPage loginPage;
	public HomePage homePage;
	public ProductListPage productListPage;
	public CommonMethodsPage commonMethodsPage;
	public ShoppingCartPage shoppingCartPage;
	public ReorderItemsPage reorderItemsPage;
	public SwitchRestaurantPage switchRestaurantPage;
	public MyListPage myListPage;
	public CheckoutPage checkoutPage;
	public PaymentPage paymentPage;
	public ReviewYourOrderPage reviewOrderPage;
	public PurchaseHistoryPage purchaseHistorypage;
	public ShoppingCartPage shoppingCart;
	public TransactionHistoryPage transactionHistoryPage;
	public MyAccountPage myAccountPage;
	public DocumentsPage documentsPage;
	public ProductDescriptionPage productDescriptionPage;

	@Autowired
	public pojos.Login login;
	@Autowired
	public Login storeWithDocuments;
	@Autowired
	public Home home;
	@Autowired
	public AddressBook addressBook;
	@Autowired
	public OrderType orderTypePreAuth;

	// TFS ID: 144392
	// Test case Description: Verify Franchisee is able to view and edit Profile Information
	@Test
	public void verifyProfileInformation_144392() throws Exception {
		try {
			loginPage = goToHomePage(LoginPage.class, driverName);
			switchRestaurantPage = loginPage.SSOLogin(login);
			homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
			homePage.selectAccountType(BaseTest.getStringfromBundleFile("ProfileInformation"));
			homePage.verifyProfileInformation();
		} catch (Exception ex) {
			Logz.error(ex.getMessage());
			throw ex;
		}
	}

	//TFS ID: 144388
	//Test case Description: Verify Franchisee is able to reorder single/all items from a past purchase order
	@Test
	public void verifyReorderSingleItemsFromPurchaseHistory_144388() throws Exception {
		try {
			loginPage = goToHomePage(LoginPage.class, driverName);
			switchRestaurantPage = loginPage.SSOLogin(login);
			homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
			homePage.removeAllItemsFromCart();
			purchaseHistorypage=homePage.selectAccountTypeForPurchaseHistory(BaseTest.getStringfromBundleFile("PurchasesHistory"));
			purchaseHistorypage.reorderSingleItem();
			shoppingCart = purchaseHistorypage.navigateToShoppingCartPage();
			shoppingCart.verifyReorderItem();
		} catch (Exception ex) {
			Logz.error(ex.getMessage());
			throw ex;
		}
	}

	//TFS ID: 144388
	//Test case Description: Verify Franchisee is able to reorder single/all items from a past purchase order
	@Test
	public void verifyReorderAllItemsFromPurchaseHistory_228600() throws Exception {
		try {
			loginPage = goToHomePage(LoginPage.class, driverName);
			switchRestaurantPage = loginPage.SSOLogin(login);
			homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
			homePage.removeAllItemsFromCart();
			purchaseHistorypage = homePage.selectAccountTypeForPurchaseHistory(BaseTest.getStringfromBundleFile("PurchasesHistory"));
			purchaseHistorypage.reorderAllItems();
			shoppingCart = purchaseHistorypage.navigateToShoppingCartPage();
			shoppingCart.verifyReorderAllItems();
		} catch (Exception ex) {
			Logz.error(ex.getMessage());
			throw ex;
		}
	}

	//TFS ID: 144353
	//Test case Description: Verify Review Product List for Smallwares Package - Add to Cart (Single Item)
	//TFS ID: 147124
	//Test case Description: Adding Small-Wares Package
	@Test
	public void verifyAddToCartSingleItemFromSmallWarePackage_144353_147124() throws Exception {
		try {
			loginPage = goToHomePage(LoginPage.class, driverName);
			switchRestaurantPage = loginPage.SSOLogin(login);
			homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(orderTypePreAuth.getOrderType());
            productListPage = homePage.navigateProductListPage();
            myListPage =  productListPage.goToMyWishList(BaseTest.getStringfromBundleFile("wishList"));
            myListPage.makeMyListEmpty();
            productListPage = homePage.navigateProductListPage();
			productDescriptionPage = productListPage.selectProductToAddToWishList();
			productDescriptionPage.addProductToWishList(BaseTest.getStringfromBundleFile("wishList"));
			myListPage =  productListPage.goToMyWishList(BaseTest.getStringfromBundleFile("wishList"));
			myListPage.AddToCartSingleItemFromSmallWarePackage();
			shoppingCart = myListPage.navigateToShoppingCartPage();
			shoppingCart.verifyItemsInCart();
			checkoutPage = shoppingCart.clickProceedToCheckout();
			checkoutPage.selectDeliveryMethod(BaseTest.getStringfromBundleFile("deliveryMethod_2"));
			paymentPage = checkoutPage.clickOnContinueButton();
			reviewOrderPage = paymentPage.selectContinueButton();
			reviewOrderPage.verifyUserNavigateToReviewOrderPage();
		} catch (Exception ex) {
			Logz.error(ex.getMessage());
			throw ex;
		}
	}

	//TFS ID: 144356
	//Test case Description: Verify Review Product List for Smallwares Package - Add to Cart (All Items)
	@Test
	public void verifyAddToCartAllItemFromSmallwarePackage_144356() throws Exception {
		try {
			loginPage = goToHomePage(LoginPage.class, driverName);
			switchRestaurantPage = loginPage.SSOLogin(login);
			homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
			homePage.removeAllItemsFromCart();
			myListPage = homePage.selectAccountTypeWishList(BaseTest.getStringfromBundleFile("wishList"));
			myListPage.AddToCartAllItemFromSmallWarePackage();
			shoppingCart = myListPage.navigateToShoppingCartPage();
			shoppingCart.verifyItemsInCart();
			checkoutPage = shoppingCart.clickProceedToCheckout();
			checkoutPage.selectDeliveryMethod(BaseTest.getStringfromBundleFile("deliveryMethod_1"));
			paymentPage = checkoutPage.clickOnContinueButton();
			reviewOrderPage = paymentPage.selectContinueButton();
			reviewOrderPage.verifyUserNavigateToReviewOrderPage();
		} catch (Exception ex) {
			Logz.error(ex.getMessage());
			throw ex;
		}
	}

	//TFS ID: 144396
	//Test case Description: Verify Franchisee is able to view Address Book and Edit Address
	@Test
	public void verifyEditAddressBook_144396() throws Exception {
		try {
			loginPage = goToHomePage(LoginPage.class, driverName);
			switchRestaurantPage = loginPage.SSOLogin(login);
			homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
			homePage.selectAccountType(BaseTest.getStringfromBundleFile("addressBook"));
			homePage.addAddressBook(addressBook);
			homePage.verifyEditAndRemoveAddress(addressBook.getFullName());
		} catch (Exception ex) {
			Logz.error(ex.getMessage());
			throw ex;
		}
	}

	// TFS ID: 144395,144398
	// Test case Description: 144395 - Verify Franchisee is able to view Address Book and Add New Address
	// Test case Description: 144398 - Verify Franchisee is able to view Address Book and Remove Address
	@Test
	public void verifyUserAbleToRemoveAddress_144395_144398() throws Exception {
		try {
			loginPage = goToHomePage(LoginPage.class, driverName);
			switchRestaurantPage = loginPage.SSOLogin(login);
			homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
			homePage.selectAccountType(BaseTest.getStringfromBundleFile("addressBook"));
			homePage.addAddressBook(addressBook);
			homePage.removeAddedAddress(addressBook.getFullName());
		} catch (Exception ex) {
			Logz.error(ex.getMessage());
			throw ex;
		}
	}


	// TFS ID: 144335
	// Test case Description: Verify user is able to open the Documents page that lists all official store documents and forms
	@Test
	public void verifyUserIsAbleToOpenTheDocumentsPage_144335() throws Exception {

		try {
			loginPage = goToHomePage(LoginPage.class, driverName);
			switchRestaurantPage = loginPage.SSOLogin(storeWithDocuments);
			homePage = switchRestaurantPage.switchRestaurant(storeWithDocuments.getStoreWithDocuments());
			myAccountPage = homePage.clickAccountOverviewLink();
			documentsPage = myAccountPage.clickDocument();
			documentsPage.clickDownloadDocument();
			documentsPage.verifyStoreDocuments();
		} catch (Exception ex) {
			Logz.error(ex.getMessage());
			throw ex;
		}
	}

	// TFS ID: 144385
	// Test case Description: Verify user is able to open the Documents page that lists all official store documents and forms
	@Test
	public void verifyFranchiseeIsAbleToViewAllPreviousPurchaseHistory_144385() throws Exception {
		try {
			loginPage = goToHomePage(LoginPage.class, driverName);
			switchRestaurantPage = loginPage.SSOLogin(login);
			homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
			purchaseHistorypage = homePage.selectAccountTypeForPurchaseHistory(BaseTest.getStringfromBundleFile("PurchasesHistory"));
			purchaseHistorypage.clickOpenLink();
			purchaseHistorypage.clickAllLink();
			purchaseHistorypage.verifySortedValues(BaseTest.getStringfromBundleFile("sortByDate"));
			purchaseHistorypage.verifySortedValues(BaseTest.getStringfromBundleFile("sortByNumber"));
			purchaseHistorypage.verifySortedValues(BaseTest.getStringfromBundleFile("sortByAmount"));
		} catch (Exception ex) {
			Logz.error(ex.getMessage());
			throw ex;
		}
	}

	// TFS ID: 144384
	// Test case Description: Verify Review Product Lists for Smallwares Package - Filter
	@Test
	public void verifyReviewProductListsFoSmallwaresPackageFilter_144384() throws Exception {
		try {
			loginPage = goToHomePage(LoginPage.class, driverName);
			switchRestaurantPage = loginPage.SSOLogin(login);
			homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
			homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
			myListPage=homePage.clickHeaderMenuMyAccountLinkAndMyList(BaseTest.getStringfromBundleFile("wishListMyList"));
			myListPage.makeMyListEmpty();
			productListPage=myListPage.navigateToPLPFromMyList();
			commonMethodsPage = homePage.gotoCommonMethodsPage();
			//HashMap<String, Double> itemPriceHMap =commonMethodsPage.addProductsToWishList(2);
			myListPage=productListPage.navigateToMyListFromPLP(BaseTest.getStringfromBundleFile("wishListMyList"));
			//myListPage.verifySortByResults(BaseTest.getStringfromBundleFile("sortByName"), itemPriceHMap);
		} catch (Exception ex) {
			Logz.error(ex.getMessage());
			throw ex;
		}
	}

	// TFS ID: 144368
	// Test case Description: Verify Review Product List for Smallwares Package - Edit Item
	@Test
	public void verifyReviewProductListForSmallWaresPackageEditItem_144368() throws Exception {
		try {
			loginPage = goToHomePage(LoginPage.class, driverName);
			switchRestaurantPage = loginPage.SSOLogin(login);
			homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
			homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
			myListPage =homePage.clickHeaderMenuMyAccountLinkAndMyList(BaseTest.getStringfromBundleFile("newListName"));
			myListPage.editAndVerfityItem();
		} catch (Exception ex) {
			Logz.error(ex.getMessage());
			throw ex;
		}
	}

	// TFS ID: 144389
	// Test case Description: Verify Franchisee is able to view transaction history through My Account
	@Test
	public void verifyFranchiseeIsAbleToViewTransactionHistoryThroughMyAccount_144389() throws Exception {
		try {
			loginPage = goToHomePage(LoginPage.class, driverName);
			switchRestaurantPage = loginPage.SSOLogin(login);
			homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
			homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
			transactionHistoryPage = homePage.clickHeaderMenuLinkAndTranHistory(BaseTest.getStringfromBundleFile("billingTransactionHistory"));
			 //OrderType drop down Transaction History results verification
            transactionHistoryPage.waitForTransactionPageLoad();
			transactionHistoryPage.verifyTransactionHistoryRecordTypeResults(BaseTest.getStringfromBundleFile("showCredit"));
			transactionHistoryPage.verifyTransactionHistoryRecordTypeResults(BaseTest.getStringfromBundleFile("showDeposit"));
			transactionHistoryPage.verifyTransactionHistoryRecordTypeResults(BaseTest.getStringfromBundleFile("showDepositApp"));
			transactionHistoryPage.verifyTransactionHistoryRecordTypeResults(BaseTest.getStringfromBundleFile("showInvoice"));
			transactionHistoryPage.verifyTransactionHistoryRecordTypeResults(BaseTest.getStringfromBundleFile("showCash"));
			transactionHistoryPage.verifyTransactionHistoryRecordTypeResults(BaseTest.getStringfromBundleFile("showPayment"));
			transactionHistoryPage.verifyTransactionHistorySortTypeResults(BaseTest.getStringfromBundleFile("byNumber"));
			transactionHistoryPage.verifyTransactionHistorySortTypeResults(BaseTest.getStringfromBundleFile("byAmount"));
			transactionHistoryPage.verifyTransactionHistorySortTypeResults(BaseTest.getStringfromBundleFile("byDate"));
		} catch (Exception ex) {
			Logz.error(ex.getMessage());
			throw ex;

		}
	}

	// TFS ID: 144359
	// Test case Description: Verify User is able change shipping address (Smallwares)
	@Test
	public void verifyUserIsAbleChangeShippingAddressSmallwares_144359() throws Exception {
		try {
			loginPage = goToHomePage(LoginPage.class, driverName);
			switchRestaurantPage = loginPage.SSOLogin(login);
			homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
			homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
			homePage.removeAllItemsFromCart();
			myListPage=homePage.clickHeaderMenuMyAccountLinkAndMyList(BaseTest.getStringfromBundleFile("wishListMyList"));
			myListPage.makeMyListEmpty();
            productListPage = homePage.navigateProductListPage();
            productDescriptionPage = productListPage.selectProductToAddToWishList();
            productDescriptionPage.addProductToWishList(BaseTest.getStringfromBundleFile("wishListMyList"));
            myListPage =  productListPage.goToMyWishList(BaseTest.getStringfromBundleFile("wishListMyList"));
			//Add Page Sync Call
            myListPage.AddToCartSingleItemFromSmallWarePackage();
			shoppingCart=myListPage.navigateToShoppingCartPage();
			checkoutPage = shoppingCart.clickProceedToCheckout();
            checkoutPage.clickOnChangeAddress();;
			checkoutPage.selectShippingAddress(addressBook);
			String arrCheckoutPageAddress[] = checkoutPage.getAddressLine();
			checkoutPage.selectDeliveryMethod(BaseTest.getStringfromBundleFile("pickUp"));
			paymentPage = checkoutPage.clickOnContinueButton();
			String arrPaymentpageAddress[] = paymentPage.getPaymentPageAddressLine();
			Assert.assertNotEquals(arrCheckoutPageAddress, arrPaymentpageAddress, "Address line present in Checkout Page '" + arrCheckoutPageAddress + "' And the Payment page '" + arrPaymentpageAddress + "' are not same");
			reviewOrderPage = paymentPage.selectContinueButton();
			String arrReviewOrderPageAddress[] = reviewOrderPage.getReviewOrderAddressLine();
			Assert.assertEquals(arrCheckoutPageAddress, arrReviewOrderPageAddress, "Address line present in Checkout Page '" + arrCheckoutPageAddress + "' And the ReviewOrder page '" + arrReviewOrderPageAddress + "' are not same");
		} catch (Exception ex) {
			Logz.error(ex.getMessage());
			throw ex;
		}
	}

	// TFS ID: 144361
	// Test case Description: Verify User is able Add New Address shipping address(Smallwares)
	@Test
	public void verifyUserIsAbleAddNewAddressShippingAddressSmallWares_144361() throws Exception {

		try {
			loginPage = goToHomePage(LoginPage.class, driverName);
			switchRestaurantPage = loginPage.SSOLogin(login);
			homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
			homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
			homePage.removeAllItemsFromCart();
			myListPage = homePage.clickHeaderMenuMyAccountLinkAndMyList(BaseTest.getStringfromBundleFile("wishListMyList"));
			myListPage.makeMyListEmpty();
            productListPage = homePage.navigateProductListPage();
            productDescriptionPage = productListPage.selectProductToAddToWishList();
            productDescriptionPage.addProductToWishList(BaseTest.getStringfromBundleFile("wishListMyList"));
            myListPage =  productListPage.goToMyWishList(BaseTest.getStringfromBundleFile("wishListMyList"));
			myListPage.AddToCartSingleItemFromSmallWarePackage();
			shoppingCart=homePage.navigateToShoppingCartPage();
			checkoutPage = shoppingCart.clickProceedToCheckout();
			//Missing Change Address Button. Discuss with Team and Comment Code if this is no more Valid
			checkoutPage.clickOnChangeAddress();
			homePage.addAddressBook(addressBook);
			checkoutPage.selectNewlyCreatedAddressAsShipToThisAddress(BaseTest.getStringfromBundleFile("addressName"));
			String arrCheckoutPageAddress[] = checkoutPage.getAddressLine();
			checkoutPage.selectDeliveryMethod(BaseTest.getStringfromBundleFile("shipFromVendor"));
			paymentPage = checkoutPage.clickOnContinueButton();
			reviewOrderPage = paymentPage.selectContinueButton();
			String arrReviewOrderPageAddress[] = reviewOrderPage.getReviewOrderAddressLine();
			Assert.assertEquals(arrCheckoutPageAddress, arrReviewOrderPageAddress, "Address line present in Checkout Page '" + arrCheckoutPageAddress + "' And the ReviewOrder page '" + arrReviewOrderPageAddress + "' are not same");
		} catch (Exception ex) {
			Logz.error(ex.getMessage());
			throw ex;
		}
	}

	// TFS Id:144387
	// Test Case Description: Verify Franchisee is able to view past orders and easily reorder items through WTOMyAccountTests
	@Test
	public void verifyPastOrdersAndReorderItems_144387() throws Exception {
		try {
			loginPage = goToHomePage(LoginPage.class, driverName);
			switchRestaurantPage = loginPage.SSOLogin(login);
			homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
			homePage.selectOrderType(BaseTest.getStringfromBundleFile("orderTypePreAuth"));
			homePage.removeAllItemsFromCart();
			reorderItemsPage = homePage.clickReorderItems(BaseTest.getStringfromBundleFile("linkReorderItems"));
			reorderItemsPage.verifyFilterByDays(BaseTest.getStringfromBundleFile("showLast15Days"));
			reorderItemsPage.verifyFilterByDays(BaseTest.getStringfromBundleFile("showLast30Days"));
			reorderItemsPage.verifyFilterByDays(BaseTest.getStringfromBundleFile("showLast60Days"));
			reorderItemsPage.verifyFilterByDays(BaseTest.getStringfromBundleFile("showLast90Days"));
			reorderItemsPage.verifyFilterByDays(BaseTest.getStringfromBundleFile("showLast180Days"));
			//reorderItemsPage.verifyDropDownSort(BaseTest.getStringfromBundleFile("byName"));
			reorderItemsPage.verifyDropDownSort(BaseTest.getStringfromBundleFile("byPrice"));
			reorderItemsPage.verifyDropDownSort(BaseTest.getStringfromBundleFile("byMostRecentlyPurchased"));
			List<String> names = reorderItemsPage.addToCartFromReorderItemsPage(2);
			shoppingCartPage = reorderItemsPage.enterShoppingPage();
			shoppingCartPage.openShoppingCartPage();
		    shoppingCartPage.verifyAddedProducts(names);
		} catch (Exception ex) { Logz.error(ex.getMessage());
			throw ex;
		}
	}
	//TFS ID: 144369
	//Test case Description:Verify Review Product List for Smallwares Package - Remove Item (Single Item)
	//@Test
	/*public void verifyRemoveSingleItemFromSmallWarePackages_144369() throws Exception {
		try {
			loginPage = goToHomePage(LoginPage.class, driverName);
			switchRestaurantPage = loginPage.SSOLogin(login);
			homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
			productListPage = homePage.navigateProductListPage();
			productDescriptionPage = productListPage.selectProductToAddToWishList();
			productDescriptionPage.addProductToWishList(BaseTest.getStringfromBundleFile("wishList"));
			myListPage = homePage.selectAccountTypeWishList(BaseTest.getStringfromBundleFile("wishList"));
			myListPage.verifySingleItemRemovalFromWishlist();
		} catch (Exception ex) {
			Logz.error(ex.getMessage());
			throw ex;
		}
	}*/

	//TFS ID: 144370
	//Test case Description: Verify Review Product List for Smallwares Package - Remove Item (All Items)
	/*@Test
	public void verifyRemoveAllItemFromSmallwarePackages_144370() throws Exception {
		try {
			loginPage = goToHomePage(LoginPage.class, driverName);
			switchRestaurantPage = loginPage.SSOLogin(login);
			homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
			myListPage = homePage.selectAccountTypeWishList(BaseTest.getStringfromBundleFile("wishList"));
			myListPage.verifyRemoveItemsFromWishlist();
		} catch (Exception ex) {
			Logz.error(ex.getMessage());
			throw ex;
		}
	}*/

	//Tests are closed
	/*//TFS ID: 144325
	//Test case Description: Verify Franchisee’s 313 Account Balance to be viewed in My Account
	@Test
	public void verify313AccountBalance_144325() throws Exception {
		try {
			loginPage = goToHomePage(LoginPage.class, driverName);
			switchRestaurantPage = loginPage.SSOLogin(login);
			homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
			homePage.selectAccountTypeHome(BaseTest.getStringfromBundleFile("AccountBalance"));
			homePage.verifyAccountBalance();
		} catch (Exception ex) {
			Logz.error(ex.getMessage());
			throw ex;
		}
	}

	//TFS ID: 144393
	//Test case Description: Verify Franchisee is able to view and edit Email Preferences
	@Test
	public void verifyEmailPreferences_144393() throws Exception {
		try {
			loginPage = goToHomePage(LoginPage.class, driverName);
			switchRestaurantPage = loginPage.SSOLogin(login);
			homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
			homePage.selectAccountTypeHome(BaseTest.getStringfromBundleFile("EmailPreferences"));
			homePage.emailPreference();
		} catch (Exception ex) {
			Logz.error(ex.getMessage());
			throw ex;
		}
	}*/
}
