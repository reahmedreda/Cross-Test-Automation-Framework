package com.testAutomationFramework;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.ui.BrowserActions;
import utilities.ui.PlaywrightBrowserActions;

public class PlaywrightTest extends TestBase{

    //PlaywrightBrowserActions playwrightBrowserActions;
//    @BeforeMethod
//    public void setup(){
//         playwrightBrowserActions = new PlaywrightBrowserActions();
//        playwrightBrowserActions.createBrowserSession(BrowserActions.DriverType.CHROME);
//    }

//    @AfterMethod
//    public void tearDown(){
//        browser.closeBrowser();
//    }

    @Test
    public void test1(){
        browser.navigateTo("https://google.com");
    }

    @Test
    public void test2(){
        browser.navigateTo("https://facebook.com");
    }


}
