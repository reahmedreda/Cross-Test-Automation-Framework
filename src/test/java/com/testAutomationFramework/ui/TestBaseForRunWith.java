package com.testAutomationFramework.ui;

import com.testAutomationFramework.actionsImp.ui.BrowserActions;
import com.testAutomationFramework.actionsImp.ui.UiActionsDto;
import com.testAutomationFramework.actionsImp.ui.UiSupportedLibraries;
import com.testAutomationFramework.utils.testNgUtilities.RunTestWithTransformer;
import org.testng.ITest;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.lang.reflect.Method;


public class TestBaseForRunWith implements ITest{
    ThreadLocal<UiActionsDto> uiActionsDtos = new ThreadLocal<>();
    private ThreadLocal<String> testName = new ThreadLocal<>();

    @BeforeMethod
    @Parameters("library")
    public void before(Method method, ITestResult result, ITestContext testContext, @Optional UiSupportedLibraries lib) throws Exception {

        uiActionsDtos.set( RunTestWithTransformer.uiActionsTestMap.get(method.getName()));
        if(uiActionsDtos==null || RunTestWithTransformer.uiActionsTestMap.size()==0){
                throw new SkipException("Test is skipped because the Listener isn't called, please check the runner to include the listener");

        }
        testName.set(method.getName()+"_"+uiActionsDtos.get().library);
        uiActionsDtos.get().browserActions.createBrowserSession(BrowserActions.BrowserTypes.CHROME);
    }

    @AfterMethod
    public void after(){
        uiActionsDtos.get().browserActions.closeBrowser();

    }

    @Override
    public String getTestName() {
        return testName.get();
    }
}
