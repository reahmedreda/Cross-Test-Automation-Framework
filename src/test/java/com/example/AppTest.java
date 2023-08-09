package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.Assert;


import com.example.SeleniumActions;

public class AppTest {
    SeleniumActions seleniumActions = new SeleniumActions();

    @Test
    public void testGoogleSearch() {
        seleniumActions.navigateToUrl("http://www.google.com");
        WebElement searchBox = seleniumActions.getElement(By.name("q"));
        seleniumActions.sendKeys(searchBox, "Sweep");
        seleniumActions.submit(searchBox);
        Assert.assertTrue(seleniumActions.getTitle().startsWith("Sweep - Google Search"));
    }
}
