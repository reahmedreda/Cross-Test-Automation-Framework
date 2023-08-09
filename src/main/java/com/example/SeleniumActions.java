package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumActions {

    private ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();

    public void createBrowserSession(BrowserType browserType) {
        switch (browserType) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
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

    public void navigateToUrl(String url) {
        driver.get().get(url);
    }

    public String getTitle() {
        return driver.get().getTitle();
    }

    public enum BrowserType {
        CHROME,
        FIREFOX
    }

}