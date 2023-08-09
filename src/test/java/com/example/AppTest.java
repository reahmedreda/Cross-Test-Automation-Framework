package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.Assert;

public class AppTest {

    private class SeleniumActions {
        public enum BrowserType {
            CHROME,
            FIREFOX
        }

        private ThreadLocal<WebDriver> driver = new ThreadLocal<>();

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

        public WebElement getElement(By by) {
            return driver.get().findElement(by);
        }

        public void sendKeys(WebElement element, String keys) {
            element.sendKeys(keys);
        }

        public void submit(WebElement element) {
            element.submit();
        }
    }

    private SeleniumActions seleniumActions;

    @BeforeClass
    public void setup() {
        seleniumActions.createBrowserSession(SeleniumActions.BrowserType.FIREFOX);
    }

    @Test
    public void testMethod() {
        Assert.assertTrue(true);
    }

    @Test
    public void testGoogleSearch() {
        seleniumActions.driver.get().get("http://www.google.com");
        WebElement searchBox = seleniumActions.getElement(By.name("q"));
        seleniumActions.sendKeys(searchBox, "Sweep");
        seleniumActions.submit(searchBox);
        Assert.assertTrue(seleniumActions.driver.get().getTitle().startsWith("Sweep - Google Search"));
    }
}