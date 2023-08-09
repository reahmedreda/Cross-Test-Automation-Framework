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

    @BeforeClass
    public void setup() {
        driver = new FirefoxDriver();
    }

    @Test
    public void testMethod() {
        Assert.assertTrue(true);
    }

    @Test
    public void testGoogleSearch() {
        driver.get("http://www.google.com");
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("Sweep");
        searchBox.submit();
        Assert.assertTrue(driver.getTitle().startsWith("Sweep - Google Search"));
    }
}