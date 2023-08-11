package com.testAutomationFramework;

import com.testAutomationFramework.ui.*;
import org.testng.annotations.*;


public  class TestBase {
    BrowserActions browser;
    UiActions uiActions;

    @BeforeTest
    public void beforeSuite(@Optional("selenium") String library){
        //System.getProperty("testLibrary");
        if (library != null) {
            switch (library){
                case "playwright":
                    browser = new PlaywrightBrowserActions();
                    uiActions = new PlaywrightUiActions(browser);

                    break;

                default:
                    browser = new SeleniumBrowserActions();
                    uiActions = new SeleniumUiActions(browser);

                    break;

            }
        } else {
            browser = new SeleniumBrowserActions();
            uiActions = new SeleniumUiActions(browser);

        }
    }

//    abstract UiActions getUiActions();


//
//    abstract BrowserActions getBrowserActions();

    @BeforeMethod
    public void setup(){
        browser.createBrowserSession(BrowserActions.DriverType.CHROME_HEADLESS);
    }

    @AfterMethod
    public void tearDown(){
        browser.closeBrowser();
    }
}
