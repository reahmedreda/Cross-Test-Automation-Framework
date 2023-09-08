package com.testAutomationFramework.ui;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.apache.commons.io.FileUtils;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class SeleniumBrowserActions implements BrowserActions{

    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    @Override
    public WebDriver getBrowserSession() {
        return driverThreadLocal.get();
    }

    @Override
    public void createBrowserSession(DriverType driverType, Boolean... useRemote) {
        WebDriver driver;
        boolean remote = useRemote.length > 0 ? useRemote[0]:false;
        if (remote) {
            try {
                driver = createRemoteBrowserSession(driverType);
            } catch (MalformedURLException e) {
                System.err.println("Selenium Grid URL is invalid. Falling back to local browser session.");
                driver = createLocalBrowserSession(driverType);
            }
        } else {
            driver = createLocalBrowserSession(driverType);
        }
        driverThreadLocal.set(driver);
    }

    @Override
    public void closeBrowser() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
        }
    }

    private WebDriver createRemoteBrowserSession(DriverType driverType) throws MalformedURLException {
        return new RemoteWebDriver(getGridURL(), getOptions(driverType));
    }

    private WebDriver createLocalBrowserSession(DriverType driverType) {
        switch (driverType) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();
            case CHROME_HEADLESS:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--headless");
                return  new ChromeDriver(chromeOptions);
            case FIREFOX:
                return new FirefoxDriver();
            case FIREFOX_HEADLESS:
                FirefoxOptions headlessFirefoxOptions = new FirefoxOptions();
                headlessFirefoxOptions.setHeadless(true);
                return new FirefoxDriver(headlessFirefoxOptions);
            // Add more cases for other driver types if needed
            default:
                throw new IllegalArgumentException("Invalid driver type: " + driverType);
        }
    }

    private URL getGridURL() throws MalformedURLException {
        return new URL("http://localhost:4444/wd/hub");
    }

    private DesiredCapabilities getOptions(DriverType driverType) {
        switch (driverType) {
            case CHROME:
                return DesiredCapabilities.chrome();
            case CHROME_HEADLESS:
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--headless");
                return DesiredCapabilities.chrome().merge(chromeOptions);
            case FIREFOX:
                return DesiredCapabilities.firefox();
            case FIREFOX_HEADLESS:
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setHeadless(true);
                return DesiredCapabilities.firefox().merge(firefoxOptions);
            // Add more cases for other driver types if needed
            default:
                throw new IllegalArgumentException("Invalid driver type: " + driverType);
        }
    }

    @Override
    public void navigateTo(String url) {
        WebDriver driver = driverThreadLocal.get();
        driver.get(url);
    }

    @Override
    public String getPageTitle() {
        WebDriver driver = driverThreadLocal.get();
        return driver.getTitle();
    }

    @Override
    public String getCurrentURL() {
        WebDriver driver = driverThreadLocal.get();
        return driver.getCurrentUrl();
    }

    @Override
    public void maximizeWindow() {
        WebDriver driver = driverThreadLocal.get();
        driver.manage().window().maximize();
    }

    @Override
    public void navigateBack() {
        WebDriver driver = driverThreadLocal.get();
        driver.navigate().back();
    }

    @Override
    public void navigateForward() {
        WebDriver driver = driverThreadLocal.get();
        driver.navigate().forward();
    }

    @Override
    public void refreshPage() {
        WebDriver driver = driverThreadLocal.get();
        driver.navigate().refresh();
    }

    @Override
    public void takeScreenshot(String filePath) {
        WebDriver driver = driverThreadLocal.get();
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshotFile, new File(filePath));
        } catch (IOException e) {
            System.err.println("Failed to save screenshot: " + e.getMessage());
        }
    }

    @Override
    public void waitForPageLoad() {
        WebDriver driver = driverThreadLocal.get();
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        String pageLoadStatus;
        do {
            pageLoadStatus = (String) jsExecutor.executeScript("return document.readyState");
        } while (!pageLoadStatus.equals("complete"));
    }

    @Override
    public void switchToFrame(String frameName) {
        WebDriver driver = driverThreadLocal.get();
        driver.switchTo().frame(frameName);
    }

    @Override
    public void switchToWindow(String windowHandle) {
        WebDriver driver = driverThreadLocal.get();
        driver.switchTo().window(windowHandle);
    }

    @Override
    public void executeJavaScript(String script) {
        WebDriver driver = driverThreadLocal.get();
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript(script);
    }
}