package com.testAutomationFramework;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utilities.ui.BrowserActions;
import utilities.ui.PlaywrightBrowserActions;

public class TestBase {
    BrowserActions browser;

    @BeforeMethod
    public void setup(){
        browser = new PlaywrightBrowserActions();
        browser.createBrowserSession(BrowserActions.DriverType.CHROME);
    }

    @AfterMethod
    public void tearDown(){
        browser.closeBrowser();
    }
}
