package com.testAutomationFramework;

import com.testAutomationFramework.ui.ElementDto;
import com.testAutomationFramework.ui.UiActions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CrossLibrariesUiE2eTest extends TestBase {
    ElementDto luckBtn = new ElementDto("/descendant::input[contains(@value,'Feeling Lucky')] [2]"),
    random =new ElementDto("//input"),
    searchBox = new ElementDto("//textarea[@name='q']");
    String url = "https://www.google.com/en";

    @Test
    public void testGetTheSizeOfListOfElements() {
        uiActions.navigateToPage(url);
        int size = uiActions.getTheSizeOfListOfElements(random);
        Assert.assertEquals(size,9);
    }

    @Test
    public void testSetTextAndPressEnter() {
        uiActions.navigateToPage(url);

        uiActions.setText(searchBox, "Hello World", true);
        uiActions.clickOnKeyBoard(searchBox, UiActions.KeyboardKeys.ENTER);
        uiActions.implicitWait(2);
        Assert.assertFalse(
                uiActions.isElementExist(luckBtn
                )
        );
    }
    @Test
    public void testClickOn() {
        uiActions.navigateToPage(url);
        uiActions.clickOn(luckBtn);
        uiActions.implicitWait(2);
        Assert.assertFalse(
                uiActions.isElementExist(luckBtn)
        );
    }

    @Test
    public void testGetText() {
        uiActions.navigateToPage(url);
        String text = uiActions.getText(luckBtn);
        Assert.assertTrue(text.contains("Lucky"));
    }

    @Test
    public void testGetValue() {
        uiActions.navigateToPage(url);
         String value = uiActions.getValue(luckBtn);
        Assert.assertTrue(value.contains("Lucky"));
        // Perform assertion on the value
    }





}
