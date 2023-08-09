package com.example;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;
import com.example.SeleniumActions;

public class AppTest {
    SeleniumActions seleniumActions = new SeleniumActions();

    @BeforeMethod
    public void setup() {
        seleniumActions.createBrowserSession(SeleniumActions.BrowserType.CHROME);
    }

    @Test
    public void testGoogleSearch() {
        seleniumActions.navigateToUrl("http://www.google.com");
        WebElement searchBox = seleniumActions.getElement(By.name("q"));
        seleniumActions.sendKeys(searchBox, "Sweep");
        seleniumActions.submit(searchBox);
        Assert.assertFalse(seleniumActions.getTitle().startsWith("Sweep - Google Search"));
    }
    
    @Test
    public void testGoogleSearchPlaywright() throws Exception {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch();
            Page page = browser.newPage();
            page.navigate("http://www.google.com");
            page.fill("[name='q']", "Sweep");
            page.press("[name='q']", "Enter");
            //page.waitForURL("**/search**");
    public String getTitle() {
        return driver.get().getTitle();
    }

    public void closeBrowserSession() {
        driver.get().quit();
        driver.remove();
    }

    public enum BrowserType {
        CHROME,
        FIREFOX
    }
}