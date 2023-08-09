package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.Assert;

public class AppTest {
public class SeleniumActions {

    private ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();

    public void createBrowserSession(BrowserType browserType) {
        switch (browserType) {
            case CHROME:
                driver.set(new ChromeDriver());
                break;
            case FIREFOX:
                driver.set(new FirefoxDriver());
                break;
        }
    }

    public void navigateToUrl(String url) {
        driver.get().get(url);
    }

    public String getPageTitle() {
        return driver.get().getTitle();
    }
}
    @Test
    public void testGoogleSearch() {
        seleniumActions.navigateToUrl("http://www.google.com");
        WebElement searchBox = seleniumActions.getElement(By.name("q"));
        seleniumActions.sendKeys(searchBox, "Sweep");
        seleniumActions.submit(searchBox);
        Assert.assertTrue(seleniumActions.getPageTitle().startsWith("Sweep - Google Search"));
    }
}