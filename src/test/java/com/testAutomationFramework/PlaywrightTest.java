package com.testAutomationFramework;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;
import utilities.ui.BrowserActions;
import utilities.ui.PlaywrightBrowserActions;
import utilities.ui.PlaywrightUiActions;

public class PlaywrightTest extends TestBase{

    PlaywrightUiActions playwrightUiActions;
        @BeforeMethod
        public void setup(){
             playwrightUiActions = new PlaywrightUiActions();
             browser.createBrowserSession(BrowserActions.DriverType.CHROME);
        }

    @AfterMethod
    public void tearDown(){
        browser.closeBrowser();
    }

    @Test
    public void testNavigateTo(){
        playwrightUiActions.navigateToPage("https://google.com");
        Assert.assertEquals(playwrightUiActions.getPageTitle(), "Google");
    }

    @Test
    public void testGetPageTitle(){
        playwrightUiActions.navigateToPage("https://google.com");
        Assert.assertEquals(playwrightUiActions.getPageTitle(), "Google");
    }
    
    @Test
    public void testGetCurrentURL(){
        playwrightUiActions.navigateToPage("https://google.com");
        Assert.assertEquals(playwrightUiActions.getCurrentURL(), "https://www.google.com/");
    }
    
    @Test
    public void testMaximizeWindow(){
        playwrightUiActions.navigateToPage("https://google.com");
        playwrightUiActions.maximizeWindow();
        // Add assertion to check if the window is maximized
    }
    
    @Test
    public void testNavigateBack(){
        playwrightUiActions.navigateToPage("https://google.com");
        playwrightUiActions.navigateToPage("https://www.example.com/");
        playwrightUiActions.navigateBack();
        Assert.assertEquals(playwrightUiActions.getCurrentURL(), "https://www.google.com/");
    }
    
    @Test
    public void testNavigateForward(){
        playwrightUiActions.navigateToPage("https://google.com");
        playwrightUiActions.navigateToPage("https://www.example.com/");
        playwrightUiActions.navigateBack();
        playwrightUiActions.navigateForward();
        Assert.assertEquals(playwrightUiActions.getCurrentURL(), "https://www.example.com/");
    }
    
    @Test
    public void testRefreshPage(){
        playwrightUiActions.navigateToPage("https://google.com");
        playwrightUiActions.refreshPage();
        Assert.assertEquals(playwrightUiActions.getCurrentURL(), "https://www.google.com/");
    }
    
    @Test
    public void testTakeScreenshot(){
        playwrightUiActions.navigateToPage("https://google.com");
        playwrightUiActions.takeScreenshot("screenshot.png");
        // Add assertion to check if the screenshot file exists
    }
    
    @Test
    public void testWaitForPageLoad(){
        playwrightUiActions.navigateToPage("https://google.com");
        playwrightUiActions.waitForPageLoad();
        Assert.assertEquals(playwrightUiActions.getPageTitle(), "Google");
    }
    
    @Test
    public void testSwitchToFrame(){
        playwrightUiActions.navigateToPage("https://www.example.com/"); // Replace with a URL of a page with a frame
        playwrightUiActions.switchToFrame("frameName"); // Replace with the name of the frame
        // Add assertion to check if the current frame is the frame
    }
    
    @Test
    public void testSwitchToWindow(){
        playwrightUiActions.navigateToPage("https://www.example.com/"); // Replace with a URL of a page with a new window
        playwrightUiActions.switchToWindow("windowHandle"); // Replace with the handle of the new window
        // Add assertion to check if the current window is the new window
    }
    
    @Test
    public void testExecuteJavaScript(){
        playwrightUiActions.navigateToPage("https://google.com");
        playwrightUiActions.executeJavaScript("console.log('Hello, World!');");
        // Add assertion to check if the JavaScript script was executed correctly
    }


}