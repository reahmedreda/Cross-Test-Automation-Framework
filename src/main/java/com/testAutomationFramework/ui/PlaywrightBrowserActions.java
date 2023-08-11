package com.testAutomationFramework.ui;

import com.microsoft.playwright.*;

import java.nio.file.Paths;

public class PlaywrightBrowserActions implements BrowserActions {
    private static ThreadLocal<Browser> browserThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<Page> pageThreadLocal = new ThreadLocal<>();

    @Override
    public Object getBrowserSession() {
        System.out.println("Getting thread "+browserThreadLocal.get());
        return browserThreadLocal.get();
    }

    public Page getPageSession() {
        return pageThreadLocal.get();
    }

    @Override
    public void createBrowserSession(DriverType driverType, Boolean... useRemote) {
        Playwright playwright = Playwright.create();
        Browser browser = null;

        switch (driverType) {
            case CHROME:
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
            case CHROME_HEADLESS:
                browser =  playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
                break;
            case FIREFOX:
                browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
            case FIREFOX_HEADLESS:
                browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(true));
                break;
        }

        browserThreadLocal.set(browser);
        pageThreadLocal.set(browserThreadLocal.get().newPage());
        System.out.println("creating "+ Thread.currentThread().getId());

        System.out.println("creating "+ browserThreadLocal.get());
    }

    @Override
    public void closeBrowser() {
        if(browserThreadLocal.get() !=null && pageThreadLocal.get()!=null) {
            System.out.println("Closing " + Thread.currentThread().getId());
            System.out.println("Closing " + browserThreadLocal.get());
            pageThreadLocal.get().close();
            browserThreadLocal.get().close();
        }
    }

    @Override
    public void navigateTo(String url) {
        if(pageThreadLocal.get() == null){
            System.out.println(Thread.currentThread().getId());
        }
        else {
            pageThreadLocal.get().navigate(url);
        }
    }


    @Override
    public String getPageTitle() {
        return pageThreadLocal.get().title();
    }

    @Override
    public String getCurrentURL() {
        return pageThreadLocal.get().url();
    }

    @Override
    public void maximizeWindow() {
        //pageThreadLocal.get().m();
    }

    @Override
    public void navigateBack() {
        pageThreadLocal.get().goBack();
    }

    @Override
    public void navigateForward() {
        pageThreadLocal.get().goForward();
    }

    @Override
    public void refreshPage() {
        pageThreadLocal.get().reload();
    }

    @Override
    public void takeScreenshot(String filePath) {
        pageThreadLocal.get().screenshot(new Page.ScreenshotOptions().setPath(Paths.get(filePath)));
    }

    @Override
    public void waitForPageLoad() {
        pageThreadLocal.get().waitForLoadState();
    }

    @Override
    public void switchToFrame(String frameName) {
        pageThreadLocal.get().frame(frameName);
    }

    @Override
    public void switchToWindow(String windowHandle) {
       // pageThreadLocal.get().context().newPage(new BrowserContext.NewPageOptions().setTarget(windowHandle));
    }

    @Override
    public void executeJavaScript(String script) {
        pageThreadLocal.get().evaluate(script);
    }
}