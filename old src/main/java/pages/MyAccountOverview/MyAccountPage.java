package pages.MyAccountOverview;




import base.gui.controls.browser.LinkText;
import base.pages.browser.BrowserBasePage;
import base.test.BaseTest;
import pages.LoginPage;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MyAccountPage<T extends RemoteWebDriver> extends BrowserBasePage{
	
	public DocumentsPage documentsPage;

    private By lnkDocument =By.xpath("//a[text()='"+BaseTest.getStringfromBundleFile("document")+"']");

    public MyAccountPage(RemoteWebDriver driver)throws Exception{
        super(driver);
    }

    private LinkText getDocumentsLink()throws Exception{
        return new LinkText(driver,lnkDocument, "Documents");
    }

    public DocumentsPage clickDocument()throws Exception{
        getDocumentsLink().click();
		return new DocumentsPage(driver);
    }

}


