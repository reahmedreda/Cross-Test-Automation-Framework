package com.testAutomationFramework.utils.testNgUtilities;

import com.testAutomationFramework.actionsImp.ui.*;
import com.testAutomationFramework.utils.PropertiesFileHandler;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class RunTestWithTransformer implements IAnnotationTransformer {
    public static Map<String, UiActionsDto> uiActionsTestMap = new HashMap<>();
    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        UiSupportedLibraries lib;
        if (testMethod.isAnnotationPresent(RunTestWith.class)) {
            RunTestWith runWith = testMethod.getAnnotation(RunTestWith.class);
            lib = runWith.value();
        }
        else if (PropertiesFileHandler.getPropertyValue("defaultUiLibrary")!=null) {
            try {
                lib = UiSupportedLibraries.valueOf(PropertiesFileHandler.getPropertyValue("defaultUiLibrary"));
            }
            catch (Exception e) {
                lib = UiSupportedLibraries.SELENIUM;
            }
        }
        else {
            lib = UiSupportedLibraries.SELENIUM;
        }
            BrowserActions browser=null;
            ElementActions uiActions=null;
            switch (lib){
                case PLAYWRIGHT:
                    browser = new PlaywrightBrowserActions();
                    uiActions= new PlaywrightUiActions(browser);
                    break;

                case SELENIUM:
                    browser=new SeleniumBrowserActions();
                    uiActions=new SeleniumUiActions(browser);
                    break;

                default:
                   // throw new Exception("the library "+ value+" you requested is invalid or not supported yet");

            }
            UiActionsDto uiActionsDto = new UiActionsDto(browser,uiActions,lib);
            uiActionsTestMap.put(testMethod.getName(),uiActionsDto);


    }

    public static void setRunWithUiLib(UiSupportedLibraries lib,Method testMethod) throws Exception {
        BrowserActions browser=null;
        ElementActions uiActions=null;
        switch (lib){
            case PLAYWRIGHT:
                browser = new PlaywrightBrowserActions();
                uiActions= new PlaywrightUiActions(browser);
                break;

            case SELENIUM:
                browser=new SeleniumBrowserActions();
                uiActions=new SeleniumUiActions(browser);
                break;

            default:
                 throw new Exception("the library "+ lib.toString()+" you requested is invalid or not supported yet");

        }
        UiActionsDto uiActionsDto = new UiActionsDto(browser,uiActions,lib);
        uiActionsTestMap.put(testMethod.getName(),uiActionsDto);
    }
}
