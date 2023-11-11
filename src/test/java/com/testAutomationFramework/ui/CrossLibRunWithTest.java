package com.testAutomationFramework.ui;

import com.testAutomationFramework.Modules.pom.GoogleHomePage;
import com.testAutomationFramework.actionsImp.ui.UiActionsDto;
import com.testAutomationFramework.actionsImp.ui.UiSupportedLibraries;
import com.testAutomationFramework.utils.testNgUtilities.RunTestWith;
import com.testAutomationFramework.utils.testNgUtilities.RunTestWithTransformer;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


@Listeners(RunTestWithTransformer.class)
public class CrossLibRunWithTest extends TestBaseForRunWith{

    @Test
    @RunTestWith(UiSupportedLibraries.PLAYWRIGHT)
    public void testClickLuckyBtn() {
        UiActionsDto uiActionsDto = uiActionsDtos.get();
        GoogleHomePage googleHomePage = new GoogleHomePage(uiActionsDto);
        googleHomePage.navigate();
        googleHomePage.clickLuckButton();

    }


    @Test
    @RunTestWith(UiSupportedLibraries.SELENIUM)
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
