package com.testAutomationFramework;

import com.testAutomationFramework.ui.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;


public  class TestBase {
    BrowserActions browser;
    protected UiActions uiActions;

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
