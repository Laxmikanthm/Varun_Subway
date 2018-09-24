package pages;

import base.gui.controls.browser.Button;
import base.gui.controls.browser.Generic;
import base.gui.controls.browser.LinkText;
import base.gui.controls.browser.SelectBox;
import base.pages.browser.BrowserBasePage;
import base.test.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.Common;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import utils.Logz;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class QuotePage<T extends RemoteWebDriver> extends BrowserBasePage {

	public SelectBox selectbox;
	private By pageHeaderTitle = By.xpath("//header[@class='quote-list-header']/h2");
	private static By quoteListTableHeader = By.xpath("//table[@class='quote-list-quotes-table']");
	private By quoteListRow = By.xpath("//table[@class='quote-list-quotes-table']/tbody[@data-view='Quote.List.Items']/tr[@class='recordviews-row']");
    private By tblQuoteList = By.xpath("//table[@class='quote-list-quotes-table']/tbody[@data-view='Quote.List.Items']/tr[@class='recordviews-row']/td");
	private By tblQuotes = By.xpath("//table[@class='quote-list-quotes-table']");
	private By statusSelect = By.xpath("//select[@name='filter']");
	private By qutoesSelect = By.xpath("//select[@name='sort']");
	private By btnToggle = By.xpath("//button[@class='list-header-view-accordion-body-button-sort']");
	private By quote = By.xpath("(//a[@class='recordviews-title-anchor'])[1]");
	private By downloadPage = By.xpath("//a[contains(text(),'" + BaseTest.getStringfromBundleFile("downloadPage") + "')]");
	private By status = By.xpath("(//span[@class='recordviews-value'])[4]");
	private By reviewAndPlaceOrder = By.xpath("//a[@class='quote-details-button-review-and-order']");
	private By leasibleItemValue = By.xpath("//div[@class='transaction-line-views-selected-option' and @name='Item Selected to be Leased']//span[@class='transaction-line-views-selected-option-value']");
	private By consolidateItemValue = By.xpath("//div[@class='transaction-line-views-selected-option' and @name='Consolidate']//span[@class='transaction-line-views-selected-option-value']");
	private By statuslink = By.tagName("tr");
	private By btnDownloadAsPDF = By.xpath("//a[@class='quote-details-button-download-pdf']");

	public QuotePage(RemoteWebDriver driver) throws Exception {
		super(driver);
		Logz.step(getQuoteTableHeader().elementIsDisplayedAndEnabled()?"Quotes page is loaded":"Quotes page is not loaded");
	}

	private LinkText getPageHeaderTitle() throws Exception {
		return new LinkText(driver, pageHeaderTitle, "Page Header - Quotes");
	}
	private Generic getQuoteTableHeader() throws Exception {
		return new Generic(driver, quoteListTableHeader, "Table Header - Quotes");
	}
	private List<WebElement> getQuotesRowList() throws Exception {
		return getQuoteTableHeader().getWebElements(quoteListRow, "Table Rows");
	}

    private List<WebElement> getTblQuotesCells(int Index) throws Exception {
        return getQuotesRowList().get(Index).findElements(tblQuoteList);
    }

	private Generic getTblQuotes() throws Exception {
		return new Generic(driver, tblQuotes, "Search Web Table");
	}

	private SelectBox getStatusSelect() throws Exception {
		return new SelectBox(driver, statusSelect, "Search select box");
	}
	private SelectBox getQuotesSelect() throws Exception {
		return new SelectBox(driver, qutoesSelect, "Search select box");
	}
	private Button getButtontoggle() throws Exception {
		return new Button(driver, btnToggle, "Search for toggle button");
	}
	private LinkText getQuote() throws Exception {
		return new LinkText(driver, quote, "Page Header - Quotes");
	}
	private SelectBox getOrderTypeValue(String option) throws Exception {
		return new SelectBox(driver, By.xpath("//div[@class = 'btn-group open']//a[text() = '" + option + "']"),"SelectOrderType option PreAuth");
	}
	private LinkText getQuoteLink(String number) throws Exception {
		return new LinkText(driver, By.xpath("//a[@class='recordviews-title-anchor' and contains(text(),'" + number + "')]"), "Quote Link");
	}
	private Generic getProposalList() throws Exception {
		return new Generic(driver, status, "Proposal List Items");
	}
	private List<WebElement> statusList() throws Exception {
		return getProposalList().getWebElements(status, "Table Rows");
	}
	private LinkText getPDF() throws Exception {
		return new LinkText(driver, downloadPage, "Download PDF");
	}
	private LinkText getReviewAndPlaceOrder() throws Exception {
		return new LinkText(driver, reviewAndPlaceOrder, "Search Review and Place order button");
	}
	private Generic getLeasibleItemText() throws Exception {
		return new Generic(driver, leasibleItemValue, "Leasable item value");
	}
	private Generic getConsolidateItemText() throws Exception {
		return new Generic(driver, consolidateItemValue, "Consolidate Item Value");
	}

    //Method Will be redesigned Once Code Fixes are Completed
	private List<WebElement> getQuoteStatusList(String status) throws Exception {
		return getQuoteTableHeader().getWebElements(By.xpath(
				"//tbody[@data-view='Quote.List.Items']//tr[@class='recordviews-row']//td[@class='recordviews-status']//span[@class='recordviews-value' and contains(text(), '"
						+ status
						+ "')]/../preceding-sibling::td[@class='recordviews-exp-date']//span[@class='quote-list-expiration-date' and not(child::span[@class='quote-list-expiration-date-overdue'])]/../..//preceding-sibling::td[@class='recordviews-title']//a"),
				"Quote List Status");
	}

	private String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	private String getWindowHandle() {
		return driver.getWindowHandle();
	}

	private Set<String> getWindowHandles() {
		return driver.getWindowHandles();
	}

	private WebDriver getSwitchTo(String Window) {
		return driver.switchTo().window(Window);
	}

	public void verifyQuoteList() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver,BaseTest.EXPLICIT_WAIT_TIME);
        wait.until(ExpectedConditions.visibilityOfElementLocated(quoteListRow));
		List<WebElement> quotesRowListEles = getQuotesRowList();
		int value = quotesRowListEles.size();
		Logz.step("Number of Quotes available: " + value);
		Logz.step(value > 0?"Quote List is available":"Quote List is not available");
	}

	public QuoteDescriptionPage verifyQuoteWithStatus(String status) throws Exception {
		Common.waitForOperations(driver,tblQuotes);
        Assert.assertTrue(getQuotesRowList().size() > 0,"No Purchase Quotes are available");
        Logz.step("Purchase Quotes are available");
        Random r = new Random();
        int iRand = r.nextInt((getQuotesRowList().size() - 5) );
        getTblQuotesCells(iRand).get(0).click();
		Logz.step("Clicked on Quote");
		return new QuoteDescriptionPage(driver);
	}

	public void reviewQuote() throws Exception {
		verifyQuoteList();
		getStatusSelect().selectFromDropDown(BaseTest.getStringfromBundleFile("proposal"));
		getQuote().click();
		Common.waitForOperations(driver,btnDownloadAsPDF);
		String Value = getReviewAndPlaceOrder().getControl().getAttribute("disabled");
		Logz.step("boolean value is " + Value);
		Assert.assertEquals("true", Value);
	}

	public void pdfDownload() throws Exception {
		verifyQuoteList();
		getQuote().click();
		getPDF().click();
		String MainWindow = getWindowHandle();
		Set<String> ChildWindows = getWindowHandles();
		for (String ChildWindow : ChildWindows) {
			Logz.step("ChildWindow: " + ChildWindow);
			if (!MainWindow.equalsIgnoreCase(ChildWindow)) {
				getSwitchTo(ChildWindow);
				String pdfurl = getCurrentUrl();
				Logz.step("pdfurl: " + pdfurl);
				Assert.assertTrue(pdfurl.contains("hotprint.nl"));
			}
		}
		getSwitchTo(MainWindow);
	}

	public ArrayList<String> getQuotestStatus() {
		ArrayList<String> statusList = new ArrayList<>();
		statusList.add("Proposal");
		statusList.add("Purchasing");
		statusList.add("Closed Lost");
		statusList.add("In Discussion");
		statusList.add("Identified Decision Makers");
		statusList.add("In Negotiation");
		statusList.add("");
		return statusList;
	}

	// Method Will be redesigned Once Code Fixes are Done
	public void filterQuotes_Status(String dropdowntype) throws Exception {
		verifyQuoteList();
		ArrayList<String> statusList = getQuotestStatus();
		getStatusSelect().selectFromDropDown(dropdowntype);
		
		WebElement byTbl = driver.findElement(By.xpath("//table[@class='quote-list-quotes-table']"));
		
		Common.waitForStale(driver, byTbl);
		
		List<WebElement> webEles = getTblQuotes().getWebElements(statuslink, "Web Table");
		for (int i = 1; i < webEles.size(); i++) {
			String status = driver.findElement(By.xpath("//table[@class='quote-list-quotes-table']//tr[" + i + "]//td[5]")).getText();
			Logz.step("status is: " + status);
			if (!dropdowntype.equalsIgnoreCase("Show all statuses")) Assert.assertEquals(status, dropdowntype);
			else Assert.assertTrue(statusList.contains(status));
		}
	}

	public HashMap<String, String> getQuotesSelectIndex() {
		HashMap<String, String> map = new HashMap<>();
		map.put("by Number", "1");
		map.put("by Request date", "2");
		map.put("by Expiration date", "3");
		map.put("by Amount", "4");
		return map;
	}

	// Dropbox2
	public void filterQuotes_Box2(String dropdowntype) throws Exception {
		verifyQuoteList();
		HashMap<String, String> map = getQuotesSelectIndex();
		String tdcoloumn = map.get(dropdowntype);
		getQuotesSelect().selectFromDropDown(dropdowntype);
		int compareNum1 = 0;
		String num1 = null;
		LocalDateTime date2 = null;
		List<WebElement> webEles = getTblQuotes().getWebElements(statuslink, "Web Table");
		for (int i = 1; i < webEles.size(); i++) {
			int compareNum2 = 0;
			String num2;
			String value = driver.findElement(By.xpath("//table[@class='quote-list-quotes-table']//tr[" + i + "]//td[" + tdcoloumn + "]")).getText();
			if (dropdowntype.equals("by Number")) {
				String number[] = value.split("#");
				if (number.length > 0) compareNum2 = Integer.parseInt(number[1]);
				if (i != 1 && compareNum2 <= compareNum1) Assert.assertTrue(true);
				else if (i != 1) Assert.assertTrue(false);
				Logz.step("Quote Number: " + compareNum1);
				compareNum1 = compareNum2;
			} else if (dropdowntype.equals("by Request date") || dropdowntype.equals("by Expiration date")) {
				final DateTimeFormatter sdf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
				LocalDateTime date1 = (LocalDateTime) sdf.parse(value);
				if (i != 1 && date1.compareTo(date2) <= 0) Assert.assertTrue(true);
				else if (i != 1) Assert.assertTrue(false);
				Logz.step("date1: " + date1);
				date2 = date1;
			} else if (dropdowntype.equals("by Amount")) {
				num2 = value.replaceAll("[$,]", "");

				if (i != 1 && Double.parseDouble(num2) <= Double.parseDouble(num1)) Assert.assertTrue(true);
				else if (i != 1) Assert.assertTrue(false);
				Logz.step("Amount: " + num1);
				num1 = num2;
			}
		}
	}

	public void verifyQuoteItemLeasability() throws Exception {
		getQuote().click();
		Logz.step("Quote is clicked");
		Assert.assertEquals(getLeasibleItemText().getControl().getText(), "Yes", "Item is not Leased");
		Logz.step("Item is leased");
	}


}
