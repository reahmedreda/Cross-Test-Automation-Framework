package com.testAutomationFramework.utils.testNgUtilities.listeners;

import com.testAutomationFramework.actionsImp.api.dtos.ApiActionsDto;
import com.testAutomationFramework.actionsImp.api.dtos.ApiSupportedLibraries;
import com.testAutomationFramework.actionsImp.api.imp.RestAssuredApiActions;
import com.testAutomationFramework.actionsImp.api.imp.RestTemplateApiActions;
import com.testAutomationFramework.actionsImp.api.interfaces.ApiActions;
import com.testAutomationFramework.actionsImp.ui.dtos.UiActionsDto;
import com.testAutomationFramework.actionsImp.ui.dtos.UiSupportedLibraries;
import com.testAutomationFramework.actionsImp.ui.imp.PlaywrightBrowserActions;
import com.testAutomationFramework.actionsImp.ui.imp.PlaywrightUiActions;
import com.testAutomationFramework.actionsImp.ui.imp.SeleniumBrowserActions;
import com.testAutomationFramework.actionsImp.ui.imp.SeleniumUiActions;
import com.testAutomationFramework.actionsImp.ui.interfaces.BrowserActions;
import com.testAutomationFramework.actionsImp.ui.interfaces.ElementActions;
import com.testAutomationFramework.utils.fileHandlers.PropertiesFileHandler;
import com.testAutomationFramework.utils.testNgUtilities.annotations.RunApiTestWith;
import com.testAutomationFramework.utils.testNgUtilities.annotations.RunUiTestWith;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class RunTestWithTransformer implements IAnnotationTransformer {
    public static Map<String, UiActionsDto> uiActionsTestMap = new HashMap<>();
    public static Map<String, ApiActionsDto> apiActionsTestMap = new HashMap<>();
    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod){
        UiSupportedLibraries uiLib;
        ApiSupportedLibraries apiLib;
        if (testMethod.isAnnotationPresent(RunUiTestWith.class)) {
            RunUiTestWith runWith = testMethod.getAnnotation(RunUiTestWith.class);
            uiLib = runWith.value();
        }
        else if (PropertiesFileHandler.getPropertyValue("defaultUiLibrary")!=null) {
            try {
                uiLib = UiSupportedLibraries.valueOf(PropertiesFileHandler.getPropertyValue("defaultUiLibrary"));
            }
            catch (Exception e) {
                uiLib = UiSupportedLibraries.SELENIUM;
            }
        }
        else {
            uiLib = UiSupportedLibraries.SELENIUM;
        }
        if (testMethod.isAnnotationPresent(RunApiTestWith.class)) {
            RunApiTestWith runWith = testMethod.getAnnotation(RunApiTestWith.class);
            apiLib = runWith.value();
        }
        else if (PropertiesFileHandler.getPropertyValue("defaultApiLibrary")!=null) {
            try {
                apiLib = ApiSupportedLibraries.valueOf(PropertiesFileHandler.getPropertyValue("defaultApiLibrary"));
            }
            catch (Exception e) {
                apiLib = ApiSupportedLibraries.RESTASSURED;
            }
        }
        else {
            apiLib = ApiSupportedLibraries.RESTASSURED;
        }

            BrowserActions browser=null;
            ElementActions uiActions=null;
            switch (uiLib){
                case PLAYWRIGHT:
                    browser = new PlaywrightBrowserActions();
                    uiActions= new PlaywrightUiActions(browser);
                    break;

                case SELENIUM:
                    browser=new SeleniumBrowserActions();
                    uiActions=new SeleniumUiActions(browser);
                    break;

                default:
                //    throw new Exception("the library "+ uiLib.toString()+" you requested is invalid or not supported yet");

            }

            UiActionsDto uiActionsDto = new UiActionsDto(browser,uiActions,uiLib);
            uiActionsTestMap.put(testMethod.getName(),uiActionsDto);

            ApiActions apiActions=null;
            switch (apiLib){
                case RESTASSURED:
                    apiActions = new RestAssuredApiActions();
                    break;
                case RESTTEMPLATE:
                    apiActions = new RestTemplateApiActions();
            }
            ApiActionsDto apiActionsDto = new ApiActionsDto(apiActions,apiLib);
            apiActionsTestMap.put(testMethod.getName(),apiActionsDto);

    }

}
