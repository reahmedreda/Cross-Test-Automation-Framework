package com.testAutomationFramework.dummy;

import com.testAutomationFramework.testNgUtilities.RunTestWithTransformer;
import com.testAutomationFramework.ui.*;
import com.testAutomationFramework.utils.RunTestWith;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

@Listeners(RunTestWithTransformer.class)
public class DummyTest {


    ThreadLocal<BrowserActions> browsers = new ThreadLocal<>();
    ThreadLocal<UiActions> uiActions=new ThreadLocal<>();
    ElementDto luckBtn = new ElementDto("/descendant::input[contains(@value,'Feeling Lucky')] [2]"),
            random =new ElementDto("//input"),
            searchBox = new ElementDto("//textarea[@name='q']");
    String url = "https://www.google.com/en";

    //@Test
    @RunTestWith(UiSupportedLibraries.SELENIUM)
    public void testFail(){
        Assert.fail("test fail");
    }

    //@Test
    @RunTestWith(UiSupportedLibraries.PLAYWRIGHT)
    public void testFail2(){
        Assert.fail("test fail");
    }

    @Test
    @RunTestWith(UiSupportedLibraries.PLAYWRIGHT)
    public void testGetText() {
        uiActions.get().navigateToPage(url);
        String text = uiActions.get().getText(luckBtn);
        Assert.assertTrue(text.contains("Lucky"));
    }

    @Test
    @RunTestWith(UiSupportedLibraries.SELENIUM)
    public void testGetText2() {
        uiActions.get().navigateToPage(url);
        String text = uiActions.get().getText(luckBtn);
        Assert.assertTrue(text.contains("Lucky"));
    }


    @BeforeMethod
    public void before(Method method, ITestResult result) throws Exception {

        String x=   RunTestWithTransformer.testVsLibraryMap.get(method.getName());

        if (x != null) {
            switch (x){
                case "playwright":
                    browsers.set(new PlaywrightBrowserActions());
                    uiActions.set(new PlaywrightUiActions(browsers.get()));
                    result.setAttribute("library", x);
                    break;

                case "selenium" :
                    browsers.set(new SeleniumBrowserActions());
                    uiActions.set(new SeleniumUiActions(browsers.get()));
                    result.setAttribute("library", x);
                    break;

                default:
                    throw new Exception("the library "+x+" you requested is invalid or not supported yet");

            }
            browsers.get().createBrowserSession(BrowserActions.DriverType.CHROME);

        }
        else {
            Assert.fail();
        }
         }

    @AfterMethod
    public void after(){
        browsers.get().closeBrowser();
        browsers.remove();
        uiActions.remove();
    }
}
