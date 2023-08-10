package com.testAutomationFramework;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;
import utilities.ui.BrowserActions;
import utilities.ui.SeleniumBrowserActions;
import utilities.ui.SeleniumUiActions;

public class SeleniumTest extends TestBase{
    //SeleniumBrowserActions seleniumBrowserActions;

//    @BeforeMethod
//    public void setup() {
//        seleniumBrowserActions = new SeleniumBrowserActions();
//        seleniumBrowserActions.createBrowserSession(BrowserActions.DriverType.CHROME,false);
//    }

//    @AfterMethod
//    public void tearDown() {
//        browser.closeBrowser();
//    }

//    @Test
//    public void test1(){
//        SeleniumUiActions s = new SeleniumUiActions();
//        s.navigateToPage("http://google.com");
//    }


//    @Test
//    public void test2(){
//        SeleniumUiActions s = new SeleniumUiActions();
//        s.navigateToPage("http://facebook.com");
//    }

    @Test
    public void test1(){
        browser.navigateTo("https://google.com");
    }

    @Test
    public void test2(){
        browser.navigateTo("https://facebook.com");
    }

    
    //@Test
    public void testGoogleSearchPlaywright() throws Exception {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch();
            Page page = browser.newPage();
            page.navigate("http://www.google.com");
            page.fill("[name='q']", "Sweep");
            page.press("[name='q']", "Enter");
            //page.waitForURL("**/search**");
        }
    }
}