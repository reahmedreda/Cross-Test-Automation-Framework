package utilities.ui;

public interface BrowserActions {

    Object getBrowserSession();

    void createBrowserSession(DriverType driverType, Boolean... useRemote);

    void closeBrowser();

    void navigateTo(String url);

    String getPageTitle();

    String getCurrentURL();

    void maximizeWindow();

    void navigateBack();

    void navigateForward();

    void refreshPage();

    void takeScreenshot(String filePath);

    void waitForPageLoad();

    void switchToFrame(String frameName);

    void switchToWindow(String windowHandle);

    void executeJavaScript(String script);

    enum DriverType {
        CHROME,
        CHROME_HEADLESS,
        FIREFOX,
        FIREFOX_HEADLESS
        // Add more driver types if needed
    }
}