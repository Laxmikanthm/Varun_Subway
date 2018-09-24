package controls;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;

import base.gui.controls.browser.TextBox;

public class TextBoxJS extends TextBox {
    
    private JavascriptExecutor jsexe;

    public TextBoxJS(RemoteWebDriver driver, By by, String controlName) throws Exception {
        super(driver, by, controlName);
        jsexe = (JavascriptExecutor) driver;
    }

    @Override
    public void setText(String value) throws Exception {
    	jsexe.executeScript("arguments[0].value=arguments[1]", getControl(), value);
            }
    
    @Override
    public void setTextWithoutClearTextBox(String value) throws Exception {
        setText(value);
    }
}