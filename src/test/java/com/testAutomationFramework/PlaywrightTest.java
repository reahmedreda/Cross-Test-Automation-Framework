package com.testAutomationFramework;

import com.sun.source.tree.AssertTree;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;
import utilities.ui.*;

import java.util.List;


public class PlaywrightTest extends TestBase{

    PlaywrightUiActions playwrightUiActions;
        @BeforeMethod
        public void setup2(){
             playwrightUiActions = new PlaywrightUiActions();
         }

    @Test
    public void testGetTheSizeOfListOfElements() {
        playwrightUiActions.navigateToPage("https://www.google.com");
        ElementDto element = new ElementDto("//input");
        int size = playwrightUiActions.getTheSizeOfListOfElements(element);
        Assert.assertEquals(size,7);
    }

    @Test
    public void testSetTextAndPressEnter() {
        playwrightUiActions.navigateToPage("https://www.google.com");
        ElementDto element = new ElementDto("//textarea[@name='q']");
        playwrightUiActions.setText(element, "Hello World", true);
        playwrightUiActions.clickOnKeyBoard(element, UiActions.KeyboardKeys.ENTER);
        Assert.assertFalse(
                playwrightUiActions.isElementExist(element)
        );
    }
    @Test
    public void testClickOn() throws Exception {
        playwrightUiActions.navigateToPage("https://www.google.com");
        ElementDto element = new ElementDto("//textarea[@name='q']");
        playwrightUiActions.clickOn(element, true, element);
        // Perform assertion or verification as needed
    }

    @Test
    public void testWaitUntil() {
        playwrightUiActions.navigateToPage("https://www.google.com");
        ElementDto element = new ElementDto("//textarea[@name='q']");
        playwrightUiActions.waitUntil(element, UiActions.ExpectedConditionsEnum.visibilityOfElementLocated);
        // Perform assertion or verification as needed
    }

    @Test
    public void testClickOnOneElementFromListOfElements() {
        playwrightUiActions.navigateToPage("https://www.google.com");
        ElementDto element = new ElementDto("a");
        playwrightUiActions.clickOnOneElementFromListOfElements(element, "value");
        // Perform assertion or verification as needed
    }

    @Test
    public void testGetText() {
        playwrightUiActions.navigateToPage("https://www.google.com");
        ElementDto element = new ElementDto("//textarea[@name='q']");
        String text = playwrightUiActions.getText(element);
        // Perform assertion on the text
    }

    @Test
    public void testGetValue() {
        playwrightUiActions.navigateToPage("https://www.google.com");
        ElementDto element = new ElementDto("//textarea[@name='q']");
        String value = playwrightUiActions.getValue(element);
        // Perform assertion on the value
    }

    @Test
    public void testClickFirstThenGetText() {
        playwrightUiActions.navigateToPage("https://www.google.com");
        ElementDto elementToClickOn = new ElementDto("button");
        ElementDto elementToGetElements = new ElementDto("div");
        List<String> textList = playwrightUiActions.ClickFirstThenGetText(elementToClickOn, elementToGetElements);
        // Perform assertion on the textList
    }

    @Test
    public void testNavigateToPage() {
        ElementDto element = new ElementDto("//textarea[@name='q']");
        playwrightUiActions.navigateToPage("https://www.google.com", element);
        // Perform assertion or verification as needed
    }

    @Test
    public void testReturnElementLocatorBy() {
        playwrightUiActions.navigateToPage("https://www.google.com");
        ElementDto element = new ElementDto("//textarea[@name='q']");
        Object elementLocator = playwrightUiActions.returnElementLocatorBy(element);
        // Perform assertion or verification as needed
    }

    @Test
    public void testGetMatchingElementsCount() {
        playwrightUiActions.navigateToPage("https://www.google.com");
        ElementDto element = new ElementDto("//textarea[@name='q']");
        int count = playwrightUiActions.getMatchingElementsCount(element);
        // Perform assertion on the count
    }

    @Test
    public void testIsElementDisplayed() {
        playwrightUiActions.navigateToPage("https://www.google.com");
        ElementDto element = new ElementDto("//textarea[@name='q']");
        boolean isDisplayed = playwrightUiActions.isElementDisplayed(element);
        // Perform assertion on the isDisplayed
    }

    @Test
    public void testGetElementsText() {
        playwrightUiActions.navigateToPage("https://www.google.com");
        ElementDto element = new ElementDto("//textarea[@name='q']");
        List<String> textList = playwrightUiActions.getElementsText(element);
        // Perform assertion on the textList
    }

    @Test
    public void testGetUniqueElementsText() {
        playwrightUiActions.navigateToPage("https://www.google.com");
        ElementDto element = new ElementDto("//textarea[@name='q']");
        List<String> uniqueTextList = playwrightUiActions.getUniqueElementsText(element);
        // Perform assertion on the uniqueTextList
    }

    @Test
    public void testIsElementExist() {
        playwrightUiActions.navigateToPage("https://www.google.com");
        ElementDto element = new ElementDto("//textarea[@name='q']");
        boolean isExist = playwrightUiActions.isElementExist(element);
        // Perform assertion on the isExist
    }

    @Test
    public void testIsElementEnabled() throws Exception {
        playwrightUiActions.navigateToPage("https://www.google.com");
        ElementDto element = new ElementDto("//textarea[@name='q']");
        boolean isEnabled = playwrightUiActions.isElementEnabled(element);
        // Perform assertion on the isEnabled
    }

    @Test
    public void testIsElementSelected() {
        playwrightUiActions.navigateToPage("https://www.google.com");
        ElementDto element = new ElementDto("//textarea[@name='q']");
        boolean isSelected = playwrightUiActions.isElementSelected(element);
        Assert.assertTrue(isSelected);
    }


    @Test
    public void testIsElementClickable() {
        playwrightUiActions.navigateToPage("https://www.google.com");
        //ElementDto element = new ElementDto("inputCertainly! Here is the rest of the test class:
    }
}