package pages;

import base.gui.controls.browser.*;
import base.pages.browser.BrowserBasePage;
import base.test.BaseTest;
import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import util.Common;
import utils.Logz;

public class SwitchRestaurantPage<T extends RemoteWebDriver> extends BrowserBasePage {

   private static By drpRestaurant = By.id("selectRestaurant");

   private By btnContinue = By.xpath("//button[contains(text(), '"+ BaseTest.getStringfromBundleFile("continue")+"')]");
   private By drpOrdertype = By.xpath("//button[contains(text(),'"+BaseTest.getStringfromBundleFile("orderTypePreAuth")+"') or contains(text(),'"+BaseTest.getStringfromBundleFile("orderType313")+"')]");
   private By drpSelectOrdertype = By.xpath("//ul/li//button[contains(text(),'"+ BaseTest.getStringfromBundleFile("dropdownSelectOrderType")+"')]");

   private By lnkHeader = By.xpath("//div[@class='header-logo-wrapper' ]//a");
   private static By txtDefaultStore = By.xpath("//a[@class='header-profile-welcome-link']/strong");
   public  By lnkSelectRestaurant = By.xpath("//span[text() = '"+ BaseTest.getStringfromBundleFile("linkSwitch") +"']");
   private By lnkHomePage =By.xpath("//div[@class='home']//a[@name='orderhistory']");
   private By lnkCancel = By.xpath("//a[contains(text(),'"+BaseTest.getStringfromBundleFile("cancel")+"')]");

   public SwitchRestaurantPage(RemoteWebDriver driver) throws Exception {
        super(driver);
        this.driver = driver;
        Logz.step(getDefaultStore().elementIsDisplayedAndEnabled()?"This is Restaurant Selection page":"Restaurant Selection page is not disaplayed");
    }

    private LinkText getHeader() throws Exception{
        return new LinkText(driver, lnkHeader,"Header logo");
    }
    public SelectBox getRestaurantDropdown() throws Exception{
        return new SelectBox(driver, drpRestaurant,"Restaurant Drop down");
    }

//    private Generic getRestaurantOptions () throws Exception {
//        return new Generic(driver, drpSelectRestaurant, "Restaurant Drop down options");
//    }

    private Generic getDefaultStore () throws Exception {
        return new Generic(driver, txtDefaultStore, "Default Store");
    }
    public Generic getSelectRestaurantLink () throws Exception {
        return new Generic(driver, lnkSelectRestaurant, "Switch Link");
    }
    private Button getContinueButton() throws Exception{
        return new Button(driver, btnContinue,"Switch Button");
    }
    private Button getDropdownOrdertype() throws Exception{
        return new Button(driver, drpOrdertype,"OrderType drop down with value Pre-Auth/313 selected");
    }
    private Button getSelectOrdertype() throws Exception{
        return new Button(driver, drpSelectOrdertype,"SelectOrderType drop down when no value is selected");
    }
    private LinkText getLinkHeaderHome() throws Exception{
        return new LinkText(driver, By.xpath("//nav[@class='header-menu-secondary-nav']//a[text()=' "+BaseTest.getStringfromBundleFile("linkHeaderHome")+" ']"),"Home Header link");
    }
    public SelectBox getOrderTypeValue(String option) throws Exception {
        return new SelectBox(driver, By.xpath("//div[@class = 'btn-group open']//a[text() = '" + option + "']"), "SelectOrderType option PreAuth");
    }
    private LinkText getCancelLink() throws Exception{
        return new LinkText(driver, lnkCancel,"Cancel Link");
    }

    public int getNoOfRestaurants() throws Exception{
        int noOfRestaurants = getRestaurantDropdown().getListOptions().size();
        return noOfRestaurants;
    }

    //Select Restaurant from the drop down
    public void selectRestaurant(String restaurantNumber) throws Exception {
        getRestaurantDropdown().selectFromDropDown(restaurantNumber);
        Logz.step("Selected Restaurant Number:" + restaurantNumber);
    }
	
	public int restaurantListSize() throws Exception {
    	int restaurantListSize = getRestaurantDropdown().getListOptions().size();
    	Logz.step("Total Number of Restauranrs :"+restaurantListSize);
		return restaurantListSize;
	}

    //Select the Restaurant which you want to make it default
    public HomePage switchRestaurant(String storeNumber) throws Exception
    {
    	String defaultStore = getDefaultStore().getControl().getText();
        if (!storeNumber.contains(defaultStore)){
            Logz.step("Clicked on Switch Button to Navigate to Select Restaurant page");
            selectRestaurant(storeNumber);
            getContinueButton().click();
            Logz.step("Clicked on Continue Button on Select Restaurant page");
        }else{
            Logz.step("Store Required to switch :'"+storeNumber+"' and Default store : '"+Integer.parseInt(defaultStore)+"' both are same, So no need to perform Switch operation");
            getCancelLink().click();
        }
        Common.waitForOperations(driver,lnkHomePage );
        return new HomePage(driver);
    }

    //Verify Order type drop down displayed without any value
    private boolean isOrderTypeDropdownSelected() throws  Exception{
        boolean flag = false;
        try{
            if(getDropdownOrdertype().getControl().isDisplayed()){
                flag = true;
            }else if (getSelectOrdertype().getControl().isDisplayed()){
                flag = false;
            }
        }catch (Exception e){
            Logz.error(" Did not find the Order type dropdown");
        }
        return flag;
    }

    public HomePage verifyUndefinedOrderType() throws Exception{
        if (!getSelectOrdertype().getControl().isDisplayed()){
        	SelectBox restaurantDrpDwn = getRestaurantDropdown();
            int noOfRestaurants = restaurantDrpDwn.getListOptions().size();
            int i=0;
            while (isOrderTypeDropdownSelected()){
                // To Switch Restaurant
                if (i < noOfRestaurants){
                    switchRestaurant(restaurantDrpDwn.getValue(i));
                }
            }
        }
        return new HomePage(driver);
    }

    public HomePage selectOrderType(String orderType) throws Exception {
        try {
        	Button btnSelectOrderType = getSelectOrdertype();
            Assert.assertTrue(btnSelectOrderType.elementIsDisplayedAndEnabled(),"Select Order Type is not Displayed");;
            Logz.step("Select Order type is displayed");
            btnSelectOrderType.getControl().click();
            Logz.step("Clicked on Select Order Type");
            getOrderTypeValue(orderType).getControl().click();
            Logz.step("Selected orderType : " + orderType);
        } catch (Exception ex) {
            Logz.error("Did not find the Order type drop down to select the order type value");
            Logz.error(ex.getMessage());
            throw new Exception(ex);
        }
        return new HomePage(driver);
    }

    public HomePage clickHomeHeaderMenu() throws Exception{
        getLinkHeaderHome().getControl().click();
        Logz.step("Clicked on Header Link Home");
        return new HomePage(driver);
    }

    public HomePage navigateToHomePage() throws Exception {
        getHeader().getControl().click();
        return new HomePage(driver);
    }
}
