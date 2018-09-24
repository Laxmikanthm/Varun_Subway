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
import pages.Cart.ProductListPage;
import pages.Cart.ShoppingCartPage;
import pages.ShopByArea.ShopByAreaPage;
import pojos.Home;
import pojos.Login;
import pojos.OrderType;
import util.Common;
import utils.Logz;

@ContextConfiguration({"classpath:/EOSBeans.xml","classpath:/LoginBeans.xml","classpath:/HomePageBeans.xml","classpath:/OrderTypeBeans.xml"})
@TestExecutionListeners(inheritListeners = false, listeners =
        {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})

public class WTOPreAuthOrdersTests extends EOSWebContext {
    public LoginPage loginPage;
    public ProductListPage productListPage;
    public SwitchRestaurantPage switchRestaurantPage;
    public HomePage homePage;
    public ShoppingCartPage shoppingCartpage;
    public ShopByAreaPage shopByAreaPage;

    @Autowired
    public Login login;
    @Autowired
    public Home home;
    @Autowired
    public OrderType orderTypePreAuth;

    //TFS ID: 143696
    //Test case Description: Filter Products by Price, High to Low
    @Test
    public void verifyFilterProductsByPriceHighToLow_143696() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            productListPage = homePage.selectSubMenuItem(BaseTest.getStringfromBundleFile("shopByArea"), BaseTest.getStringfromBundleFile("tables"));
            productListPage.verifySortSelectorDropDown();
            productListPage.selectPriceSorting(BaseTest.getStringfromBundleFile("sortPrice_1"));
            productListPage.verifyProductsAreSortedByPrice(BaseTest.getStringfromBundleFile("sortPrice_1"));
        } catch (Exception ex) {
           ex.printStackTrace();
        }
    }

    //TFS ID: 143695
    //Test case Description: Filter Products by Price, Low To High
    @Test
    public void verifyFilterProductsByPriceLowToHigh_143695() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            productListPage = homePage.selectSubMenuItem(BaseTest.getStringfromBundleFile("shopByArea"), BaseTest.getStringfromBundleFile("tables"));
            productListPage.verifySortSelectorDropDown();
            productListPage.selectPriceSorting(BaseTest.getStringfromBundleFile("sortPrice_2"));
            productListPage.verifyProductsAreSortedByPrice(BaseTest.getStringfromBundleFile("sortPrice_2"));
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 143639
    //Test case Description: Franchisees able to browse Product Catalog for Pre-Auth
    @Test
    public void verifyBrowseProductCatalogForPreAuth_143639() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(orderTypePreAuth.getOrderType());
            productListPage = homePage.selectSubMenuItem(BaseTest.getStringfromBundleFile("shopByArea"), BaseTest.getStringfromBundleFile("tables"));
            productListPage.validateTablesPage(BaseTest.getStringfromBundleFile("tablesPageHeader"));
           } catch (Exception ex) {
           Logz.error(ex.getMessage());
           throw ex;
        }
    }

    //TFS ID: 143641
    //Test case Description: Customer can add item to Shopping Cart for Pre-Auth
    @Test
    public void verifyAddingItemToCartForPreAuth_143641() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(orderTypePreAuth.getOrderType());
            homePage.removeAllItemsFromCart();
            productListPage = homePage.selectSubMenuItem(BaseTest.getStringfromBundleFile("shopByArea"), BaseTest.getStringfromBundleFile("tables"));
            shoppingCartpage = productListPage.addItemsToCart(1);
            shoppingCartpage.verifyItemIsAddedToCart();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 143642
    //Test case Description: Customer can edit item in Cart for Pre-Auth
    @Test
    public void verifyUserIsAbleToEditItemInCartForPreAuth_143642() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(orderTypePreAuth.getOrderType());
            productListPage = homePage.navigateProductListPage();
            shoppingCartpage = productListPage.addItemsToCart(1);
            shoppingCartpage.navigateToAnItemToEdit();
            shoppingCartpage.selectQuantityAndUpdateCart(BaseTest.getStringfromBundleFile("quantity"));
            shoppingCartpage.verifyUpdatedItemQuantity(BaseTest.getStringfromBundleFile("quantity"));
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 143643
    //Test case Description: Customer can save Items for Later in Shopping Cart for Pre-Auth
    @Test
    public void verifySaveItemForLaterInCartForPreAuth_143643() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(orderTypePreAuth.getOrderType());
            productListPage = homePage.navigateProductListPage();
            shoppingCartpage = productListPage.addItemsToCart(1);
            shoppingCartpage.navigateToAnItemToSaveForLater();
            shoppingCartpage.verifySaveItemsForLaterInCart();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 143644
    //Test case Description: Customer can remove Items from Cart for Pre-Auth
    @Test
    public void verifyRemoveItemFromCart_143644() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(orderTypePreAuth.getOrderType());
            homePage.removeAllItemsFromCart();
            productListPage = homePage.navigateProductListPage();
            shoppingCartpage = productListPage.addItemsToCart(1);
            shoppingCartpage.removeItemFromCart();
            shoppingCartpage.verifyItemUnderCart();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 143645
    //Test case Description: Upload category thumbnails using Website
    @Test
    public void verifyCategoryThumbnailsInWebsite_143645() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(orderTypePreAuth.getOrderType());
            shopByAreaPage = homePage.clickShopByArea();
            shopByAreaPage.verifyBackRoomLink();
            shopByAreaPage.verifyExteriorLink();
            shopByAreaPage.verifyCustomerAreaLink();
            shopByAreaPage.verifyServiceAreaLink();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 143646
    //Test case Description: Navigate to Facet and Faceted Search functionality
    @Test
        public void verifyNavigateToSearchFunctionality_143646() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            productListPage=homePage.navigateProductListPage();
            productListPage.verifyFacetOptionShownInLeftSideForPrice();
            productListPage.verifyFacetOptionShownInLeftSide(BaseTest.getStringfromBundleFile("vendorName"));
            productListPage.verifyFacetOptionShownInLeftSide(BaseTest.getStringfromBundleFile("restaurantArea"));
            productListPage.verifyFacetOptionShownInLeftSide(BaseTest.getStringfromBundleFile("depthIn"));
            productListPage.verifyFacetOptionShownInLeftSide(BaseTest.getStringfromBundleFile("heightIn"));
            productListPage.verifyFacetOptionShownInLeftSide(BaseTest.getStringfromBundleFile("decorType"));

        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 143657
    //Test case Description: Validate paging for display of 12 Products
    @Test
    public void verifyProductsPerPage_143657() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            productListPage = homePage.navigateProductListPage();
            productListPage.selectProductsPerPage(BaseTest.getStringfromBundleFile("show12productsperpage"));
            productListPage.validateProductsPerPage(BaseTest.getStringfromBundleFile("12productsperpage"));
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 143658
    //Test case Description: Validate paging for display of 24 Products
    @Test
    public void verifyProductsPerPage_143658() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            productListPage = homePage.navigateProductListPage();
            productListPage.selectProductsPerPage(BaseTest.getStringfromBundleFile("show24productsperpage"));
            productListPage.validateProductsPerPage(BaseTest.getStringfromBundleFile("24productsperpage"));
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 143659
    //Test case Description: Validate paging for display of 48 Products
    @Test
    public void verifyProductsPerPage_143659() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            productListPage = homePage.navigateProductListPage();
            productListPage.selectProductsPerPage(BaseTest.getStringfromBundleFile("show48productsperpage"));
            productListPage.validateProductsPerPage(BaseTest.getStringfromBundleFile("48productsperpage"));
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 143697
    //Test case Description: Verify Page Layout is displayed List view
    @Test
    public void verifyPageLayoutIsListView_143697() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(orderTypePreAuth.getOrderType());
            productListPage = homePage.selectSubMenuItem(BaseTest.getStringfromBundleFile("shopByArea"), BaseTest.getStringfromBundleFile("tables"));
            productListPage.clickOnListView();
            productListPage.validatePageLayoutIsListView();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 143698
    //Test case Description: Verify Page Layout is displayed Table view
    @Test
    public void verifyPageLayoutIsTableView_143698() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(orderTypePreAuth.getOrderType());
            productListPage = homePage.selectSubMenuItem(BaseTest.getStringfromBundleFile("shopByArea"), BaseTest.getStringfromBundleFile("tables"));
            productListPage.clickOnTableView();
            productListPage.validatePageLayoutIsTableView();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 143699
    //Test case Description: Verify Page Layout is displayed Grid view
    @Test
    public void verifyPageLayoutIsGridView_143699() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(orderTypePreAuth.getOrderType());
            productListPage = homePage.selectSubMenuItem(BaseTest.getStringfromBundleFile("shopByArea"), BaseTest.getStringfromBundleFile("tables"));
            productListPage.clickOnGridView();
            productListPage.validatePageLayoutIsGridView();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 143647
    //Test case Description: Filter Products by Price Range
    @Test
    public void verifyFilterProductsByPriceRange_143647() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            homePage.selectOrderType(orderTypePreAuth.getOrderType());
            productListPage = homePage.navigateProductListPage();
            productListPage.dragAndDropSliderRight();
            productListPage.verifyProductsSortedByPriceRangeSlider();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }

    //TFS ID: 143649
    //Test case Description:Verify Facet Options
    // TFS ID: 143649
    // Test case Description: Range Slider Facets for filtering by dimension
    @Test
    public void verifyFacetOptions_143648_143649() throws Exception {
        try {
            loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            productListPage = homePage.navigateProductListPage();
            productListPage.verifyFacetOptions(BaseTest.getStringfromBundleFile("heightIn"));
            productListPage.verifyFacetOptions(BaseTest.getStringfromBundleFile("widthIn"));
            productListPage.verifyFacetOptions(BaseTest.getStringfromBundleFile("depthIn"));
            productListPage.verifyFacetOptions(BaseTest.getStringfromBundleFile("vendorName"));
            productListPage.verifyFacetOptions(BaseTest.getStringfromBundleFile("decorType"));

        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }
    //TFS ID: 143650
    //Test case Description: Navigate to list of Products by using Faceted Search
    @Test
    public void verifyNavigateToListOfProductsByUsingFacetedSearch_143650() throws Exception {
        try {
        	loginPage = goToHomePage(LoginPage.class, driverName);
            switchRestaurantPage = loginPage.SSOLogin(login);
            homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
            productListPage = homePage.navigateProductListPage();
            productListPage.verifyItemsDisplayingBasedOnSearch(BaseTest.getStringfromBundleFile("customerArea"));
            productListPage.verifySearchTitle(BaseTest.getStringfromBundleFile("customerArea"));
            productListPage.verifyPDPNavigation();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }
    //TFS ID: 143651
    //Test case Description: Filter Products by dimension
    @Test
    public void verifyFilterProductsByDimension_143651() throws Exception {
        try {
        	 loginPage = goToHomePage(LoginPage.class, driverName);
             switchRestaurantPage = loginPage.SSOLogin(login);
             homePage = switchRestaurantPage.switchRestaurant(home.getStoreNumber());
             productListPage = homePage.navigateProductListPage();
             productListPage.dragAndDropSliderRightfacet(BaseTest.getStringfromBundleFile("heightIn"));
             productListPage.dragAndDropSliderRightfacet(BaseTest.getStringfromBundleFile("depthIn"));
             productListPage.dragAndDropSliderRightfacet(BaseTest.getStringfromBundleFile("widthIn"));
             productListPage.verifyFacetsApplied();
        } catch (Exception ex) {
            Logz.error(ex.getMessage());
            throw ex;
        }
    }
}