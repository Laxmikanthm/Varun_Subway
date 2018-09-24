package pages;

/**
 * Created by E001891 on 12/4/2017.
 */

import base.gui.controls.browser.*;
import base.pages.browser.BrowserBasePage;
import base.test.BaseTest;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.Cart.QuoteConfirmationPage;
import pojos.Items;
import util.Common;
import utils.Logz;
import java.util.List;
import java.util.stream.IntStream;

public class RequestQuotePage<T extends RemoteWebDriver> extends BrowserBasePage {

    private By txtFieldSearch = By.xpath("//span[@class='twitter-typeahead']//input[@placeholder='Enter SKU or Item Name']");
    //private By searchItemName = By.xpath("//input[@id='quickaddSearch']//span[@class='tt-dropdown-menu']/div");
    private By searchItemName = By.xpath("//div[@class='tt-dataset-1'");
    private By txtFieldSpecialInstruction = By.xpath("//input[@id='specialinstructions']");
    private static By txtBoxQuantity = By.id("quantity");
    private By btnAddItem = By.xpath("//button[text()='"+BaseTest.getStringfromBundleFile("additem")+"']");
    private By btnTopSubmit = By.xpath("//a[text()=' "+BaseTest.getStringfromBundleFile("submitQuoteRequest")+" ']");
    private By btnBottomSubmit = By.xpath("//div[@class='requestquote-wizard-step-button-container']/button[@class='requestquote-wizard-step-button-continue']");
    private By btnEdit = By.xpath("//a[text()='"+BaseTest.getStringfromBundleFile("edit")+"']");
    private By btnChangeAddress = By.xpath("//a[text()='"+BaseTest.getStringfromBundleFile("changeAddress")+"']");
    private By drpSelectConsolidator = By.xpath("//div[@class='requestquote-wizard-module-comments']");
    private By quoteNumber= By.xpath("//p[@class='requestquote-wizard-module-confirmation-body']//a[contains(text(),'#')]");
    private By confirmationMessage = By.xpath("//div[@class='requestquote-wizard-module-confirmation']//h2[@class='requestquote-wizard-module-confirmation-title']");
    private By txtComment=By.xpath("//div[@class='requestquote-wizard-module-comments-box']/textarea");
    private By txtQuoteItems = By.xpath("//a[@class='transaction-line-views-cell-actionable-expanded-name-link']");
    private By btnRemovQuoteItem = By.xpath("//a[@class='requestquote-wizard-module-items-line-actions-button-remove']");
    private By txtSubmitQuoteSuccess = By.xpath("//h2[@class='requestquote-wizard-module-confirmation-title']");
    private By txtQuoteRequestNo = By.xpath("//p[contains(text(),'"+BaseTest.getStringfromBundleFile("yourQuoteRequest")+"')]");
    private By lnkSKUItem = By.xpath("//div[contains(@class,'tt-dataset')]//div[@class='quick-add-item-results-title']");


    public RequestQuotePage(RemoteWebDriver driver) throws Exception {
        super(driver);
        Logz.step(getQuantityTextBox().elementIsDisplayedAndEnabled()?"This is Request Quote Page":"Request Quote Page is not displayed");
    }

    public CommonMethodsPage commonMethodsPage;

    //This method will return the commonMethodsPage driver object
    public CommonMethodsPage gotoCommonMethodsPage() throws Exception {
        return new CommonMethodsPage(driver);
    }

    private Generic getConfirmationMessage() throws Exception {
        return new Generic(driver,confirmationMessage,"Confirmation Message");
    }
    private TextBox getComment() throws Exception {
        return new TextBox(driver,txtComment,"Comment");
    }
    private Generic getQuoteNumber() throws Exception {
        return new Generic(driver,quoteNumber,"Quote Number");
    }
    private List<WebElement> getRequestQuoteItems() throws Exception{
        return getDropdownSelectConsolidator().getWebElements(txtQuoteItems,"Quote Requested Items");
    }
    private List<WebElement> getQuoteItemRemoveBtns() throws Exception{
        return getDropdownSelectConsolidator().getWebElements(btnRemovQuoteItem,"Quote Item Remove Buttons");
    }
    private TextBox getTxtFieldSearch() throws Exception {
        return new TextBox(driver, txtFieldSearch, "Search text field");
    }
    private TextBox getTxtFieldSpecialInstruction() throws Exception {
        return new TextBox(driver, txtFieldSpecialInstruction, "Special Instruction text field");
    }
 
    private TextBox getQuantityTextBox() throws Exception {
        return new TextBox(driver, txtBoxQuantity, "Quantity text field");
    }
    private TextBox getAddItemButton() throws Exception {
        return new TextBox(driver, btnAddItem, "Add Item");
    }
    private Button getTopSubmitButton() throws Exception {
        return new Button(driver, btnTopSubmit, " Submit Quote Request ");
    }
    private Button getBottomSubmitButton() throws Exception {
        return new Button(driver, btnBottomSubmit, " Submit Quote Request ");
    }
    private TextBox getEditButton() throws Exception {
        return new TextBox(driver, btnEdit, "edit");
    }
    private Button getChangeAddressButton() throws Exception {
        return new Button(driver, btnChangeAddress, "ChangeAddress");
    }
    private Generic getDropdownSelectConsolidator() throws Exception {
        return new Generic(driver, drpSelectConsolidator, "selectConsolidator drop down");
    }
    private SelectBox getConsolidatorTypeValue(String option) throws Exception {
        return new SelectBox(driver, By.xpath("//div[@class='requestquote-wizard-module-comments']//select/option[text()='" + option + "']"), "ConsolidatorTypeValue type ");
    }
   
    private Generic gettxtQuoteRequestNo() throws Exception{
        return new Generic(driver,txtQuoteRequestNo,"Quote Request Number");
    }

    private LinkText getSKUItem() throws Exception{
        return new LinkText(driver,lnkSKUItem,"SKU Items");
    }

    public String getQuoteRequestNumber() throws Exception{
        String requestQuoteNo = gettxtQuoteRequestNo().getControl().getText().toString();
        Logz.step("Request Quote No" +requestQuoteNo);
        return  requestQuoteNo;
    }

    public void selectConsolidatorValue(String value) throws Exception {
        getDropdownSelectConsolidator().getControl().click();
        Logz.step("Clicked on Consolidator drop down link");
        getConsolidatorTypeValue(value).getControl().click();
        Logz.step("Selected value : " + value);
    }

    public void clickOnEdit() throws Exception {
        getEditButton().click();
        Logz.step("Clicked on Edit Button");
    }

    public void clickOnChangeAddress() throws Exception {
        getChangeAddressButton().getControl().click();
        Logz.step("Clicked on Change Address Button");
    }

    public QuoteConfirmationPage clickOnSubmitButtonAtTop() throws Exception {
        getTopSubmitButton().click();
        Logz.step("Clicked on Submit Button");
        return  new QuoteConfirmationPage(driver);
    }

    public QuoteConfirmationPage clickOnSubmitButtonAtBottom() throws Exception {
        Common.waitForLoadingCompletion(driver);
        WebDriverWait wait = new WebDriverWait(driver, BaseTest.EXPLICIT_WAIT_TIME);
        wait.until(ExpectedConditions.visibilityOfElementLocated(btnBottomSubmit));
        wait.until(ExpectedConditions.elementToBeClickable(btnBottomSubmit));
        getBottomSubmitButton().click();
        Common.waitForLoadingCompletion(driver);
        Logz.step("Clicked on Submit Button");
        return  new QuoteConfirmationPage(driver);
    }

    public HomePage clearRequestedQuoteItems() throws Exception{
        commonMethodsPage = gotoCommonMethodsPage();
        if (Common.isElementPresent(driver,btnRemovQuoteItem)){
        	List<WebElement> quoteItemRemoveBtnEles = getQuoteItemRemoveBtns();
            int noOfQuoteItems = quoteItemRemoveBtnEles.size();
            System.out.println("noOfQuoteItems"+noOfQuoteItems);
            IntStream.range(0,noOfQuoteItems).forEach(i -> quoteItemRemoveBtnEles.get(0).click());
        }else Logz.step("No Quotes are present to remove");
        return new HomePage(driver);
    }

    public String[] getItemNamesRequestedForQuote() throws Exception{
        String arrItemsRequestedForQuote[] = new String[getRequestQuoteItems().size()];
        int j = 0;
        for(int i=0;i<=getRequestQuoteItems().size()-1;i++){
            arrItemsRequestedForQuote[j++] = getRequestQuoteItems().get(i).getText().toString();
        }
        return arrItemsRequestedForQuote;
    }

    public void verifyItemsAddedToRequestQuote(String arr1[],String arr2[]) throws Exception{
        Assert.assertEqualsNoOrder(arr1,arr2,"Items Added to Request ToQuote: '"+arr1+"' and the items present in the Request A Quote: '"+arr2+"' are not matching");
    }

    public void selectItemForQuoteandSubmit(String ItemName, String Quantity) throws Exception {
        TextBox textFieldSearch = getTxtFieldSearch();
        textFieldSearch.setText(ItemName);

        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(lnkSKUItem));
        getSKUItem().click();
/*        WebElement element = driver.findElement(By.xpath("//span[@class='tt-dropdown-menu']"));
        WebElement ele = By.xpath("//div[contains(@class,'tt-dataset')]//div[@class='quick-add-item-results-title']").findElement(driver);
        Actions set = new Actions(driver);
        set.moveToElement(element).build().perform();
Logz.step("Eleemnt Moved");
        element.click();

       WebElement txtFldSearchCtrl = textFieldSearch.getControl();
    	txtFldSearchCtrl.sendKeys(Keys.ARROW_DOWN);
        textFieldSearch.click();
    	txtFldSearchCtrl.click();*/

        getQuantityTextBox().setText(Quantity);

        TextBox txtFldSpecialInstEle = getTxtFieldSpecialInstruction();
        if (txtFldSpecialInstEle.elementIsDisplayedAndEnabled()) {
            String strSpecialInst = BaseTest.getStringfromBundleFile("specialInstruction");
            txtFldSpecialInstEle.setText(strSpecialInst);
            Logz.step("Entered Special Instruction: '" + strSpecialInst + "' in the Special instruction field");
            if (txtFldSpecialInstEle.elementIsDisplayedAndEnabled()) {
                strSpecialInst = BaseTest.getStringfromBundleFile("specialInstruction");
                txtFldSpecialInstEle.setText(strSpecialInst);
                Logz.step("Entered Special Instruction: '" + strSpecialInst + "' in the Special instruction field");

                getQuantityTextBox().setText(Quantity);
            }
            getAddItemButton().getControl().click();
        }
    }
    public String verifyAddQuoteConfirmationandGetQuoteNumber(String ItemName, String Quantity) throws Exception {
        selectItemForQuoteandSubmit(ItemName,Quantity);
        clickOnSubmitButtonAtBottom();
        Assert.assertTrue(getConfirmationMessage().elementIsDisplayedAndEnabled(),"Quote is not Added");
        return getQuoteNumber().getControl().getText();
    }

    public String verifyAddQuoteWithMultipleItems(Items items, String quantity)throws Exception {
        List<String> itemNames=items.getItemName();
        for(String itemName:itemNames) selectItemForQuoteandSubmit(itemName,quantity);
        clickOnSubmitButtonAtBottom();
        Logz.step("Clicked on Submit quote request button ");
        Assert.assertTrue(getConfirmationMessage().elementIsDisplayedAndEnabled(),"Quote is not Added");
        Logz.step("Quote is Added");
        return getQuoteNumber().getControl().getText();
    }

    public void verifyQuoteNumber(String quoteNumber) throws Exception{
        Assert.assertNotNull(quoteNumber,"Did not get the quote number");
    }

    public void enterComment(String comment) throws Exception {
        getComment().setText(comment);
        Logz.step("entered Comments");
    }
}