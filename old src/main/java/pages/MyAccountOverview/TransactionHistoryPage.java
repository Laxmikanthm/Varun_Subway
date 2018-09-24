package pages.MyAccountOverview;

import base.gui.controls.browser.*;
import base.pages.browser.BrowserBasePage;
import base.test.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.CommonMethodsPage;
import utils.Logz;

import java.util.List;

public class TransactionHistoryPage<T extends RemoteWebDriver> extends BrowserBasePage {

    private By txtFieldFromDate = By.xpath("//input[@id='from']");
    private static By transactionHistoryHeader = By.xpath("//header[@class='transaction-history-list-header']");
    private By txtFieldToDate = By.xpath("//input[@id='to']");
    private By drpRecordType = By.xpath("//*[@class = 'list-header-view-sorts']//following-sibling::label/select");
    private By drpSortType = By.xpath("//*[@class = 'list-header-view-sorts']//select");
    private By transactionHistoryList = By.xpath("//*[@class = 'transaction-history-list']");
    private By recordViewtitles = By.xpath("//*[@class = 'recordviews-title-anchor']");
    private By transactioHistoryAmount = By.xpath("//*[@class = 'recordviews-currency']//span[@class='recordviews-value']");
    private By transactioHistory = By.xpath("//table[@class = 'transaction-history-list-results-table']");
    private By transactioHistoryDate = By.xpath("//*[@class = 'recordviews-date']//span[@class='recordviews-value']");
    private By txtNoTransactions = By.xpath("//div[@class='transaction-history-list-empty-section']");
    public CommonMethodsPage commonMethodsPage;

    public TransactionHistoryPage(RemoteWebDriver driver) throws Exception {
        super(driver);
        commonMethodsPage = gotoCommonMethodsPage();
        Logz.step(getTransactionHistHeader().elementIsDisplayedAndEnabled()?"This is Transaction History page":"Transaction History page is not displayed");
    }

    //This method will return the commonMethodsPage driver object
    public CommonMethodsPage gotoCommonMethodsPage() throws Exception {
        return new CommonMethodsPage(driver);
    }

    private TextBox getInputFromDate() throws Exception {
        return new TextBox(driver, txtFieldFromDate, "From Date");
    }
    private TextBox getInputToDate() throws Exception {
        return new TextBox(driver, txtFieldToDate, "To Date");
    }
    public SelectBox getRecordType() throws Exception{
        return new SelectBox(driver, drpRecordType,"Record Type Filter");
    }
    private SelectBox getSortType() throws Exception{
        return new SelectBox(driver, drpSortType,"Sort Type Filter");
    }
    private Generic transactionHistList() throws  Exception{
        return new Generic(driver,transactionHistoryList,"Transaction History List");
    }
    private Generic getNoTransactions() throws  Exception{
        return new Generic(driver,txtNoTransactions,"Transaction History List");
    }
    private Generic getTransactionHistHeader() throws  Exception{
        return new Generic(driver,transactionHistoryHeader,"Transaction History List");
    }
    public List<WebElement> getRecordViewTitles()throws Exception {
        return transactionHistList().getWebElements(recordViewtitles,"Record View Titles in the results");
    }
    public List<WebElement> getAmountResults()throws Exception {
        return transactionHistList().getWebElements(transactioHistoryAmount,"Amount column data in the results");
    }
    public List<WebElement> getDatetResults()throws Exception {
        return transactionHistList().getWebElements(transactioHistoryDate,"Date column data in the results");
    }

    //EOS Website Transaction History page
    public TransactionHistoryPage setFromInputDate(String fromDate) throws Exception {
        getInputFromDate().setText(fromDate);
        Logz.step("Entered from Date value");
        return new TransactionHistoryPage(driver);
    }

    public TransactionHistoryPage setToInputDate(String toDate) throws Exception {
        getInputToDate().setText(toDate);
        Logz.step("Entered To Date value");
        return new TransactionHistoryPage(driver);
    }

    public void waitForTransactionPageLoad() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, 120);
        wait.until(ExpectedConditions.visibilityOfElementLocated(transactioHistory));
    }

    public TransactionHistoryPage verifyTransactionHistoryRecordTypeResults(String recordType) throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, 120);

        SelectBox selRecordType = getRecordType();
        WebElement elmRecordType = selRecordType.getControl();

        selRecordType.selectFromDropDown(recordType);
        wait.until(ExpectedConditions.stalenessOf(elmRecordType));
        wait.until(ExpectedConditions.visibilityOfElementLocated(transactionHistoryList));
        String recordNumberTitle[] = recordType.split(" ");
        List<WebElement> recordViewTitlesEles = getRecordViewTitles();
        if(recordViewTitlesEles.size()>0){
            for(int i=0;i<=recordViewTitlesEles.size()-1;i++)
            {
                Assert.assertTrue(recordType.contains(recordViewTitlesEles.get(i).getText().split("#")[0].trim()),"Record Type Selected - "+recordType+" and Records History Shows - "+recordViewTitlesEles.get(i).getText().split("#")[0].trim());
            }
        }else if(recordViewTitlesEles.size() == 0) {
            Assert.assertTrue(getNoTransactions().getControl().getText().contains(BaseTest.getStringfromBundleFile("NoTransactions")));
        }

        return new TransactionHistoryPage(driver);
    }

    public TransactionHistoryPage verifyTransactionHistorySortTypeResults(String sortType) throws Exception {
        SelectBox selSortType = getSortType();
        WebElement elmSortType = selSortType.getControl();
        selSortType.selectFromDropDown(sortType);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.stalenessOf(elmSortType));
        String number[] ;
        int k=0;
        List<WebElement> recordViewTitlesEles = getRecordViewTitles();
        String numberIds[]=new String[recordViewTitlesEles.size()];
        String arrAmount[]=new String[getAmountResults().size()];
        String arrDate[]  =new String[getDatetResults().size()];

        if (sortType.toString().trim().equalsIgnoreCase(BaseTest.getStringfromBundleFile("byNumber").toString().trim())){
            for(int i=0;i<=recordViewTitlesEles.size()-1;i++) {
                number = recordViewTitlesEles.get(i).getText().toString().split("#");
                numberIds[k++] = number[1];
            }

            Assert.assertEquals(numberIds,commonMethodsPage.sortedResults(numberIds,"Desc"));
        }else if(sortType.toString().trim().equalsIgnoreCase(BaseTest.getStringfromBundleFile("byAmount").toString().trim())){
            for(int i=0;i<=getAmountResults().size()-1;i++){
                arrAmount[k++] = getAmountResults().get(i).getText().toString().substring(1);
            }
            Assert.assertEquals(arrAmount,commonMethodsPage.sortedResults(arrAmount,"Asc"));
        }else if(sortType.toString().trim().equalsIgnoreCase(BaseTest.getStringfromBundleFile("byDate").toString().trim())){
            for(int i=0;i<=getDatetResults().size()-1;i++){
                arrDate[k++] = getDatetResults().get(i).getText();
            }
//            *************** Date sorting verification is pending, since date sort reusable function in not yet ready**************
//            Assert.assertArrayEquals(arrDate,);
        }
        return new TransactionHistoryPage(driver);
    }
}




