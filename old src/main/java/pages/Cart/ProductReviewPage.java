package pages.Cart;

import base.gui.controls.browser.Button;
import base.gui.controls.browser.LinkText;
import base.gui.controls.browser.TextBox;
import base.pages.browser.BrowserBasePage;
import base.test.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.Logz;

public class ProductReviewPage<T extends RemoteWebDriver> extends BrowserBasePage {

    private By txtReviewerName= By.id("writerName");
    private static By txtReview= By.id("text");
    private By txtHeadline= By.id("title");
    private By btnSubmit= By.xpath("//button[contains(text(),'"+ BaseTest.getStringfromBundleFile("submit")+"')]");
    private By btnPreview= By.xpath("//button[contains(text(),'"+ BaseTest.getStringfromBundleFile("preview")+"')]");
    private By msgSubmitted= By.xpath("//div[@class='product-reviews-form-confirmation-message']//p[contains(text() ,'"+ BaseTest.getStringfromBundleFile("yourReview")+"')]");

    public ProductReviewPage(RemoteWebDriver driver) throws Exception {
        super(driver);
        Logz.step(getReviewText().elementIsDisplayedAndEnabled()?"This is Product Review page":"Product Review Page is not displayed");
    }

    private LinkText getRating(String number)throws Exception {
        return new LinkText(driver,By.xpath("//div [@class=\"global-views-star-rating-area-writable\"]/button[@value='"+number+"']"),"Rating");
    }
    private TextBox getReviewerName() throws Exception {
        return new TextBox(driver,txtReviewerName,"Reviewer Name");
    }
    private TextBox getReviewText() throws Exception {
        return new TextBox(driver,txtReview,"Text Review");
    }
    private TextBox getHeadlineText() throws Exception {
        return new TextBox(driver,txtHeadline,"Text Headline");
    }
    private Button getSubmitButton() throws Exception {
        return new Button(driver,btnSubmit,"Submit Button");
    }
    private Button getPreviewButton() throws Exception {
        return new Button(driver,btnPreview,"Preview Button");
    }
    private Button getMsgSubmitted() throws Exception {
        return new Button(driver,msgSubmitted," your review is submitted");
    }

    public ProductReviewPage clickRating(String number) throws Exception {
        getRating(number).click();
        Logz.step("Clicked on Rating");
        return new ProductReviewPage(driver);
    }

    public ProductReviewPage setReviewerName(String name) throws Exception {
        getReviewerName().setText(name);
        Logz.step("Entered Reviewer Name");
        return new ProductReviewPage(driver);
    }

    public ProductReviewPage setReviewText(String text) throws Exception {
        getReviewText().setText(text);
        Logz.step("Ëntered Review Text");
        return new ProductReviewPage(driver);
    }

    public ProductReviewPage setReviewHeadline(String headline) throws Exception {
        getHeadlineText().setText(headline);
        Logz.step("Entered Review Headline");
        return new ProductReviewPage(driver);
    }

    public ProductReviewPage clickSubmit() throws Exception {
        getSubmitButton().click();
        Logz.step("Clicked on Submit Button");
        return new ProductReviewPage(driver);
    }

    public ProductReviewPage clickPreview() throws Exception {
        getPreviewButton().click();
        Logz.step("Clicked on Preview Button");
        return new ProductReviewPage(driver);
    }

    public void verifySubmitProductReview() throws Exception {
        clickRating(BaseTest.getStringfromBundleFile("number"));
        Logz.step("Rating is clicked");
        setReviewerName(BaseTest.getStringfromBundleFile("name"));
        Logz.step("Reviewer Name is set");
        setReviewText(BaseTest.getStringfromBundleFile("text"));
        Logz.step("Review Text is set");
        setReviewHeadline(BaseTest.getStringfromBundleFile("headline"));
        Logz.step("Review Headline is set");
        clickSubmit();
        if(getMsgSubmitted().getText().toString().equalsIgnoreCase(BaseTest.getStringfromBundleFile("yourReview"))) {
            Logz.step("User Submitted the product review");
        } else {
            Logz.step("User not Submitted the product review");
        }
    }
}
