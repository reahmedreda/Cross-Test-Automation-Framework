package utilities.ui;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.ElementState;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.ArrayList;
import java.util.List;

import static com.microsoft.playwright.options.ElementState.*;

public class PlaywrightUiActions implements UiActions {

    private Page page ;

    public PlaywrightUiActions() {
        PlaywrightBrowserActions playwrightBrowserActions = new PlaywrightBrowserActions();
        page = playwrightBrowserActions.getPageSession();
    }

    @Override
    public int getTheSizeOfListOfElements(ElementDto ele) {
        ElementHandle elementHandle = getElementHandle(ele);
        return elementHandle.evaluateHandle("elements => elements.length");
    }

    @Override
    public void sendKeys(ElementDto ele, KeyboardKeys key) {
        ElementHandle elementHandle = getElementHandle(ele);
        elementHandle.press(key.toString());
    }

    @Override
    public void setText(ElementDto ele, String text, boolean clear, Boolean... assertOnActualValue) {
        ElementHandle elementHandle = getElementHandle(ele);
        if (clear) {
            elementHandle.clear();
        }
        elementHandle.type(text);
        if (assertOnActualValue.length > 0 && assertOnActualValue[0]) {
            String actualText = elementHandle.innerText();
            // Perform assertion on actualText
        }
    }

    @Override
    public void clickOn(ElementDto ele, boolean assertion, ElementDto expectedElementOb) throws Exception {
        ElementHandle elementHandle = getElementHandle(ele);
        elementHandle.click();

        if (assertion) {
            ElementHandle expectedElementHandle = getElementHandle(expectedElementOb);
            if (!expectedElementHandle.isVisible()) {
                throw new Exception("Expected element is not visible");
            }
        }
    }

    @Override
    public void clickOn(ElementDto ele, Boolean... waitUntilVisibleOnly) {
        ElementHandle elementHandle = getElementHandle(ele);
        if (waitUntilVisibleOnly.length > 0 && waitUntilVisibleOnly[0]) {
            elementHandle.waitForElementState(VISIBLE);
        }
        elementHandle.click();
    }

    @Override
    public Object waitUntil(ElementDto ele, ExpectedConditionsEnum condition, String... attribute) {
        ElementHandle elementHandle = getElementHandle(ele);
        switch (condition) {
            case presenceOfElement:
                elementHandle.waitForElementState(STABLE);
            case ElementToBeClickable:
                elementHandle.waitForElementState(ENABLED);
            case visibilityOfElementLocated:
                elementHandle.waitForElementState(VISIBLE);
            default:
                throw new IllegalArgumentException("Invalid expected condition: " + condition);
        }
    }

    @Override
    public void clickOnOneElementFromListOfElements(ElementDto ele, String value) {
        ElementHandle elementHandle = getElementHandle(ele);
        List<ElementHandle> elements = elementHandle.querySelectorAll(value);
        elements.get(0).click();
    }

    @Override
    public String getText(ElementDto ele) {
        ElementHandle elementHandle = getElementHandle(ele);
        return elementHandle.innerText();
    }

    @Override
    public String getValue(ElementDto ele) {
        ElementHandle elementHandle = getElementHandle(ele);
        return elementHandle.value();
    }

    @Override
    public List<String> ClickFirstThenGetText(ElementDto eleToClickOn, ElementDto eleToGetElements) {
        ElementHandle elementToClickOnHandle = getElementHandle(eleToClickOn);
        elementToClickOnHandle.click();

        ElementHandle elementToGetElementsHandle = getElementHandle(eleToGetElements);
        List<ElementHandle> elements = elementToGetElementsHandle.querySelectorAll("your-selector");

        List<String> textList = new ArrayList<>();
        for (ElementHandle element : elements) {
            textList.add(element.innerText());
        }
        return textList;
    }

    @Override
    public void navigateToPage(String url, ElementDto ele) {
        page.navigate(url);
        getElementHandle(ele);
    }

    @Override
    public void navigateToPage(String url) {
        page.navigate(url);
        page.waitForNavigation();
    }

    @Override
    public Object returnElementLocatorBy(ElementDto elementDto) {
        return getElementHandle(elementDto);
    }

    @Override
    public int getMatchingElementsCount(ElementDto ele) {
        ElementHandle elementHandle = getElementHandle(ele);
        List<ElementHandle> elements = elementHandle.querySelectorAll("*");
        return elements.size();
    }

    @Override
    public boolean isElementDisplayed(ElementDto ele) {
        ElementHandle elementHandle = getElementHandle(ele);
        return elementHandle.isVisible();
    }

    @Override
    public List<String> getElementsText(ElementDto ele) {
        ElementHandle elementHandle = getElementHandle(ele);
        List<ElementHandle> elements = elementHandle.querySelectorAll("*");

        List<String> textList = new ArrayList<>();
        for (ElementHandle element : elements) {
            textList.add(element.innerText());
        }
        return textList;
    }

    @Override
    public List<String> getUniqueElementsText(ElementDto ele) {
        ElementHandle elementHandle = getElementHandle(ele);
        List<ElementHandle> elements = elementHandle.querySelectorAll("*");

        List<String> uniqueTextList = new ArrayList<>();
        for (ElementHandle element : elements) {
            String text = element.innerText();
            if (!uniqueTextList.contains(text)) {
                uniqueTextList.add(text);
            }
        }
        return uniqueTextList;
    }

    @Override
    public boolean isElementExist(ElementDto ele) {
        ElementHandle elementHandle = page.querySelector(ele.selector);
        return elementHandle != null;
    }

    @Override
    public boolean isElementEnabled(ElementDto ele) throws Exception {
        ElementHandle elementHandle = getElementHandle(ele);
        if (elementHandle.isEnabled()) {
            return true;
        } else {
            throw new Exception("Element is not enabled");
        }
    }

    @Override
    public boolean isElementSelected(ElementDto ele) {
        ElementHandle elementHandle = getElementHandle(ele);
        return elementHandle.isChecked();
    }

    @Override
    public void assertElementText(ElementDto ele, String expectedText) {
        ElementHandle elementHandle = getElementHandle(ele);
        String actualText = elementHandle.innerText();
        // Perform assertion on actualText and expectedText
    }

    @Override
    public boolean isElementClickable(ElementDto ele) {
        ElementHandle elementHandle = getElementHandle(ele);
        return elementHandle.isEnabled() && elementHandle.isVisible();
    }

    @Override
    public void clearField(ElementDto ele) {
        ElementHandle elementHandle = getElementHandle(ele);
        elementHandle.click(); // Click on the field to focus it
        elementHandle.press("Control+A"); // Select all text in the field
        elementHandle.press("Backspace"); // Delete the selected text
    }

    @Override
    public boolean clickOnListOfButtons(ElementDto ele) {
        ElementHandle elementHandle = getElementHandle(ele);
        List<ElementHandle> buttons = elementHandle.querySelectorAll("button");

        if (buttons.size() > 0) {
            buttons.get(0).click();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void clickOnOneElementFromListOfElements(ElementDto ele, int index) {
        ElementHandle elementHandle = getElementHandle(ele);
        List<ElementHandle> elements = elementHandle.querySelectorAll("*");
        elements.get(index).click();
    }

    @Override
    public String getAttribute(ElementDto ele, String attribute) {
        ElementHandle elementHandle = getElementHandle(ele);
        return elementHandle.getAttribute(attribute);
    }

    @Override
    public void clickOnKeyBoard(ElementDto ele, KeyboardKeys key) {
        ElementHandle elementHandle = getElementHandle(ele);
        elementHandle.press(key.toString());
    }

    @Override
    public void implicitWait(int seconds) {
        page.waitForTimeout(seconds * 1000);
    }

    private ElementHandle getElementHandle(ElementDto ele) {
        ElementUtilities.Locator locator = ele.locator;
        String selector = ele.selector;

        switch (locator) {
            case XPath:
                page.getBy
                return page.waitForSelector(selector);
            case CSS:
                return page.querySelector(selector);
            case Id:
                return page.querySelector("#" + selector);
            case ClassName:
                return page.querySelector("." + selector);
            default:
                throw new IllegalArgumentException("Invalid locator: " + locator);
        }
    }
}