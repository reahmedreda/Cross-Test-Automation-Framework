package utilities.ui;


import java.util.List;

public interface UiActions {
    int getTheSizeOfListOfElements(ElementDto ele);
    void sendKeys(ElementDto ele, KeyboardKeys key);

    void setText(ElementDto ele, String text, boolean clear, Boolean... assertOnActualValue);
    void clickOn(ElementDto ele, boolean assertion, ElementDto expectedElementOb) throws Exception;
    void clickOn(ElementDto ele, Boolean... waitUntilVisibleOnly);
    Object waitUntil(ElementDto ele, ExpectedConditionsEnum condition, String... attribute);
    void clickOnOneElementFromListOfElements(ElementDto ele, String value);
    String getText(ElementDto ele);
    String getValue(ElementDto ele);
    List<String> ClickFirstThenGetText(ElementDto eleToClickOn, ElementDto eleToGetElements);
    void navigateToPage(String url, ElementDto ele);
    void navigateToPage(String url);
    Object returnElementLocatorBy(ElementDto elementDto);
    int getMatchingElementsCount(ElementDto ele);
    boolean isElementDisplayed(ElementDto ele);

    List<String> getElementsText(ElementDto ele);

    List<String> getUniqueElementsText(ElementDto ele);

    boolean isElementExist(ElementDto ele);

    boolean isElementEnabled(ElementDto ele) throws Exception;
    boolean isElementSelected(ElementDto ele);
    void assertElementText(ElementDto ele, String expectedText);

    boolean isElementClickable(ElementDto ele);

    void clearField(ElementDto ele);

    boolean clickOnListOfButtons(ElementDto ele);

    // to select one element from list of elements
    void clickOnOneElementFromListOfElements(ElementDto ele, int index);

    String getAttribute(ElementDto ele, String attribute);

    void clickOnKeyBoard(ElementDto ele, KeyboardKeys key);

    void implicitWait(int seconds);

    public enum KeyboardKeys {
        up,
        down,
        enter,
        tab,
        esc
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