package util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import base.test.BaseTest;
import utils.Logz;

import javax.annotation.Nullable;

public interface Common {
	//To Handle Loader issue
	final By imgLoadingIndicator = By.id("loadingIndicator");

	public static void waitForOperations(RemoteWebDriver driver, By element) throws Exception {
		Logz.step("Waiting for page to load.");
		try {
			WebDriverWait wait = new WebDriverWait(driver, 120); //BaseTest.EXPLICIT_WAIT_TIME
			wait.until(ExpectedConditions.visibilityOfElementLocated(element));
		} catch (Throwable error) {
			Assert.fail("Timeout waiting for Page Load Request to complete.");
		}
	}
	
	public static void waitForStale(RemoteWebDriver driver, WebElement webEle, long... timeInSecs) {
		Long timer = timeInSecs!=null && timeInSecs.length > 0 ? timeInSecs[0] : BaseTest.EXPLICIT_WAIT_TIME;
		WebDriverWait wait = new WebDriverWait(driver,timer);
		wait.until(ExpectedConditions.stalenessOf(webEle));
	}

	public static ExpectedCondition<Boolean> textNotEqual(WebDriver driver, By by, final String text) {
		return new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver webDriver) {
				return !driver.findElement(by).getText().equals(text);
			}
		};
	}

    //This function is used to verify whether the element is present on the web page or not,
    // If element present it returns "true" else returns "false".
    //Since Framework does not have only displayed method solution is developed locally
    public static boolean isElementPresent(WebDriver driver, By by) throws Exception{
        boolean present;
        try {
            //Since Framework is taking ImplicitlyWait as 60 seconds and even element is not there on the page it is waiting for 60 seconds(driver timeout), to avoid this we have implemented this solution.
            driver.findElement(by);
            //getDriver().manage().timeouts().implicitlyWait(60L, TimeUnit.SECONDS);
            present = true;
        } catch (Exception  e) {
            //Logz.info("Element is not present" + e.getMessage());
            present = false;
        }
        return present;
    }

    public static boolean waitForElementToBeDisplayed(WebDriver driver, By element) throws Exception {
		return (new WebDriverWait(driver, (long) BaseTest.EXPLICIT_WAIT_TIME)).until(ExpectedConditions.visibilityOfElementLocated(element)).isDisplayed();

	}

    public static boolean waitForLoadingCompletion(WebDriver driver) throws Exception {
        return (new WebDriverWait(driver, (long) BaseTest.EXPLICIT_WAIT_TIME)).until(ExpectedConditions.invisibilityOfElementLocated(imgLoadingIndicator));

    }




}
