package com.testAutomationFramework;

import com.testAutomationFramework.actionsImp.api.dtos.ApiActionsDto;
import com.testAutomationFramework.actionsImp.ui.dtos.UiActionsDto;
import com.testAutomationFramework.utils.testNgUtilities.listeners.RunTestWithTransformer;
import org.testng.ITest;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;


public class TestBaseForRunWith implements ITest{
    protected ThreadLocal<UiActionsDto> uiActionsDtos = new ThreadLocal<>();
    protected ThreadLocal<ApiActionsDto> apiActionsDtos = new ThreadLocal<>();
    private ThreadLocal<String> testName = new ThreadLocal<>();

    @BeforeMethod
    public void before(Method method, ITestResult result, ITestContext testContext) throws Exception {

        if (RunTestWithTransformer.uiActionsTestMap.isEmpty() && RunTestWithTransformer.apiActionsTestMap.isEmpty()) {
            System.out.println("Test is skipped because the Listener isn't called, please check the runner to include the listener");
            System.out.println("** If you are using Intellij as test runner make sure to add the Listener RunTestWithTransformer in the TestNg configuration **");
            throw new SkipException("Test is skipped because the Listener isn't called, please check the runner to include the listener");
        }
        if (RunTestWithTransformer.uiActionsTestMap.containsKey(method.getName())) {
            uiActionsDtos.set(RunTestWithTransformer.uiActionsTestMap.get(method.getName()));
            testName.set(method.getName() + "_" + uiActionsDtos.get().library);
        }
        if (RunTestWithTransformer.apiActionsTestMap.containsKey(method.getName())) {
            apiActionsDtos.set(RunTestWithTransformer.apiActionsTestMap.get(method.getName()));
            testName.set(testName.get() + "_" + apiActionsDtos.get().library);
        }

    }


    @Override
    public String getTestName() {
        return testName.get();
    }
}
