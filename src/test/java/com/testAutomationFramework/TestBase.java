package com.testAutomationFramework;

import com.testAutomationFramework.ui.*;
import org.testng.ITest;
import org.testng.annotations.*;

import java.lang.reflect.Method;


public  class TestBase implements ITest {
    BrowserActions browser;
    protected UiActions uiActions;
    private ThreadLocal<String> testName = new ThreadLocal<>();

    @BeforeClass
    @Parameters("library")
    public void beforeClass(@Optional("selenium") String library) throws Exception {
        //System.getProperty("testLibrary");
        if (library != null) {
            switch (library){
                case "playwright":
                    browser = new PlaywrightBrowserActions();
                    uiActions = new PlaywrightUiActions(browser);

                    break;

                case "selenium" :
                    browser = new SeleniumBrowserActions();
                    uiActions = new SeleniumUiActions(browser);
                    break;

                default:
                    throw new Exception("the library "+library+" you requested is invalid or not supported yet");

            }
        } else {
            browser = new SeleniumBrowserActions();
            uiActions = new SeleniumUiActions(browser);

        }
    }


    @BeforeMethod
    @Parameters("library")
    public void beforeMethod(@Optional("selenium") String library, Method method){
        browser.createBrowserSession(BrowserActions.DriverType.CHROME_HEADLESS);
        testName.set(method.getName() + "_" + library);
    }

    @AfterMethod
    public void tearDown(){
        browser.closeBrowser();
    }

    @Override
    public String getTestName() {
        return testName.get();
    }
}
