package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.Assert;

public class AppTest {
    private WebDriver driver;
    private SeleniumActions seleniumActions;

    @BeforeClass
    public void setup() {
        driver = new FirefoxDriver();
        seleniumActions = new SeleniumActions(driver);
    }

    @Test
    public void testMethod() {
        Assert.assertTrue(true);
    }

    @Test
    public void testGoogleSearch() {
        driver.get("http://www.google.com");
        WebElement searchBox = seleniumActions.getElement(By.name("q"));
        seleniumActions.sendKeys(searchBox, "Sweep");
        seleniumActions.submit(searchBox);
        Assert.assertTrue(driver.getTitle().startsWith("Sweep - Google Search"));
    }
}