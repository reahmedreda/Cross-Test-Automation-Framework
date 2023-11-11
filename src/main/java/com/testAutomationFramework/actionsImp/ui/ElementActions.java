package com.testAutomationFramework.actionsImp.ui;


import java.util.List;

public interface ElementActions<T,B> {
    int getTheSizeOfListOfElements(ElementDto ele);
    void setText(ElementDto ele, String text, boolean clear, Boolean... assertOnActualValue);
    void clickOn(ElementDto ele, boolean assertion, ElementDto expectedElementOb) throws Exception;
    void clickOn(ElementDto ele, Boolean... waitUntilVisibleOnly);
    T waitUntil(ElementDto ele, ExpectedConditionsEnum condition, String... attribute);
    void clickOnOneElementFromListOfElements(ElementDto ele, String value);
    String getText(ElementDto ele);
    String getValue(ElementDto ele);
    List<String> ClickFirstThenGetText(ElementDto eleToClickOn, ElementDto eleToGetElements);
    void navigateToPage(String url, ElementDto ele);
    void navigateToPage(String url);
    B returnElementLocatorBy(ElementDto elementDto);
    int getMatchingElementsCount(ElementDto ele);
    boolean isElementDisplayed(ElementDto ele);

    List<String> getElementsText(ElementDto ele);

    List<String> getUniqueElementsText(ElementDto ele);

    boolean isElementExist(ElementDto ele);

    boolean isElementEnabled(ElementDto ele) throws Exception;
    boolean isElementSelected(ElementDto ele);

    boolean isElementClickable(ElementDto ele);

    void clearField(ElementDto ele);

    boolean clickOnListOfButtons(ElementDto ele);

    // to select one element from list of elements
    void clickOnOneElementFromListOfElements(ElementDto ele, int index);

    String getAttribute(ElementDto ele, String attribute);

    void clickOnKeyBoard(ElementDto ele, KeyboardKeys key);

    void implicitWait(int seconds);

    public enum KeyboardKeys {
        UP,
        DOWN,
        ENTER,
        TAB,
        ESC,
        SPACE
    }


    public enum ExpectedConditionsEnum {
        presenceOfElement,
        ElementToBeClickable,
        visibilityOfElementLocated,
        attributeToBeNotEmpty,
        invisibilityOfElementLocated,
        pageIsLoaded,
        invisibilityOfElements
    }

}