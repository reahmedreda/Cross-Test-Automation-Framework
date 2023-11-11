package com.testAutomationFramework.ui;

import com.testAutomationFramework.Modules.pom.GoogleHomePage;
import com.testAutomationFramework.TestBaseForRunWith;
import com.testAutomationFramework.actionsImp.ui.dtos.UiActionsDto;
import com.testAutomationFramework.actionsImp.ui.dtos.UiSupportedLibraries;
import com.testAutomationFramework.actionsImp.ui.interfaces.BrowserActions;
import com.testAutomationFramework.utils.testNgUtilities.annotations.RunUiTestWith;
import com.testAutomationFramework.utils.testNgUtilities.listeners.RunTestWithTransformer;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


@Listeners(RunTestWithTransformer.class)
public class GoogleTest extends TestBaseForRunWith {

    @BeforeMethod
    public void setUp(){
        uiActionsDtos.get().browserActions.createBrowserSession(BrowserActions.BrowserTypes.CHROME);
    }

    @AfterMethod
    public void after(){
        uiActionsDtos.get().browserActions.closeBrowser();

    }
    @Test
    @RunUiTestWith(UiSupportedLibraries.PLAYWRIGHT)
    public void testClickLuckyBtn() {
        UiActionsDto uiActionsDto = uiActionsDtos.get();
        GoogleHomePage googleHomePage = new GoogleHomePage(uiActionsDto);
        googleHomePage.navigate();
        googleHomePage.clickLuckButton();

    }


    @Test
    @RunUiTestWith(UiSupportedLibraries.SELENIUM)
    public void testClickLuckyBtn2() {
        UiActionsDto uiActionsDto = uiActionsDtos.get();
        GoogleHomePage googleHomePage = new GoogleHomePage(uiActionsDto);
        googleHomePage.navigate();
        googleHomePage.clickLuckButton();
    }

    @Test
    public void search() {
        UiActionsDto uiActionsDto = uiActionsDtos.get();
        GoogleHomePage googleHomePage = new GoogleHomePage(uiActionsDto);
        googleHomePage.navigate();
        googleHomePage.setSearch("reda");
        googleHomePage.clickSearch();
        String textFromFirstResult = googleHomePage.getTextFromFirstResult();
        Assert.assertTrue(textFromFirstResult.contains("رضا"));
    }


}
