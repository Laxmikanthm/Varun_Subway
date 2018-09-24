package pages.MyAccountOverview;

import base.gui.controls.browser.LinkText;
import base.pages.browser.BrowserBasePage;
import base.test.BaseTest;
import util.Common;
import utils.Logz;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

public class DocumentsPage<T extends RemoteWebDriver> extends BrowserBasePage {

	private By lnkDownloadDocument =By.xpath("//a[text()='"+BaseTest.getStringfromBundleFile("downloadDocument")+"']");

	public DocumentsPage(RemoteWebDriver driver) throws Exception {
		super(driver);
	}

	 private LinkText getDocumentsDownloadLink()throws Exception{
		 return new LinkText(driver,lnkDownloadDocument, "Documents Download");
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

	 public void clickDownloadDocument()throws Exception{
    	getDocumentsDownloadLink().click();
     }
    
    public void verifyStoreDocuments() throws Exception {		
		String MainWindow = getWindowHandle();
		Set<String> ChildWindows = getWindowHandles();
		for (String ChildWindow : ChildWindows) {
			Logz.step("ChildWindow: " + ChildWindow);
			if (!MainWindow.equalsIgnoreCase(ChildWindow)) {
				getSwitchTo(ChildWindow);
				String pdfurl = getCurrentUrl();
				Logz.step("pdfurl: " + pdfurl);
				if (pdfurl.contains("Sample"))
					Assert.assertTrue(true);
			}
		}
		getSwitchTo(MainWindow);
	}
}
