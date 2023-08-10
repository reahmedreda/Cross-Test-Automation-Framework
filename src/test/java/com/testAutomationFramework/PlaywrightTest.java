package com.testAutomationFramework;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;
import utilities.ui.BrowserActions;
import utilities.ui.PlaywrightBrowserActions;
import utilities.ui.PlaywrightUiActions;

public class PlaywrightTest extends TestBase{

    PlaywrightUiActions playwrightUiActions;
        @BeforeMethod
        public void setup(){
             playwrightUiActions = new PlaywrightUiActions();
             browser.createBrowserSession(BrowserActions.DriverType.CHROME);
        }

    @AfterMethod
    public void tearDown(){
        browser.closeBrowser();
    }

    @Test
    public void testNavigateTo(){
        playwrightUiActions.navigateToPage("https://google.com");
        Assert.assertEquals(playwrightUiActions.getPageTitle(), "Google");
    }

    // Add more test methods for other public functions in the PlaywrightUiActions class


}