package utilities.ui;

import jdk.jshell.spi.ExecutionControl;
import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static utilities.LoggingHandling.logger;


public class SeleniumUiActions implements UiActions {

    long twentySeconds = 20;

    WebDriver driver;

    public SeleniumUiActions() {
        SeleniumBrowserActions seleniumBrowserActions = new SeleniumBrowserActions();
        this.driver = (WebDriver) seleniumBrowserActions.getBrowserSession();
    }

    @Override
    public int getTheSizeOfListOfElements(ElementDto ele) {
        By b = returnElementLocatorBy(ele); // wait till all search block appears
        waitUntil(b, ExpectedConditionsEnum.visibilityOfElementLocated);
        List<WebElement> elements = driver.findElements(b);
        return elements.size();
    }

    @Override
    public void sendKeys(ElementDto ele, KeyboardKeys key) {
        throw new NotImplementedException();
    }

    @Override
    public void sendKeys(ElementDto ele, Keys key) {
        By b = returnElementLocatorBy(ele);
        WebElement element = waitUntil(b, ExpectedConditionsEnum.presenceOfElement);
        if (element != null)
            element.sendKeys(key);
        else {
            String message = String.format("Element with selector: %s is null", ele.selector);
            logger.severe(message);
            Assert.fail(message);
        }

    }

    @Override
    public void setText(ElementDto ele, String text, boolean clear, Boolean... assertOnActualValue) {
        By b = returnElementLocatorBy(ele);
        WebElement element = waitUntil(b, ExpectedConditionsEnum.visibilityOfElementLocated);
        if (element != null) {
            try {
                int count = 20;
                while (!element.isEnabled() && count > 0) {
                    Thread.sleep(500);
                    count--;
                }
                if (clear) {
                    element.clear();
                    count = 20;
                    while (!element.getText().isEmpty() && count > 0) {
                        Thread.sleep(500);
                        count--;
                    }
                    waitUntil(b, ExpectedConditionsEnum.visibilityOfElementLocated);
                }

                element.sendKeys(text);
                if (assertOnActualValue.length >= 1 && assertOnActualValue[0]) {
                    String actualValue = (
                            element.getAttribute("value") == null) ?
                            (element.getAttribute("innerHTML") == null ? element.getText() : element.getAttribute("innerHTML"))
                            : element.getAttribute("value");

                    assertEquals(actualValue, text);
                }
            } catch (Exception e) {
                String message = String.format("Couldn't set text to element with selector:" +
                        " %s because of the exception: %s", ele.selector, e.getMessage());
                logger.severe(message);
                Assert.fail(message);

            }
        } else {
            String message = String.format("Element with selector: %s is null", ele.selector);
            logger.severe(message);
            Assert.fail(message);
        }
    }

    @Override
    public void clickOn(ElementDto ele, boolean assertion, ElementDto expectedElementOb) throws Exception {
        By b = returnElementLocatorBy(ele);
        WebElement element = waitUntil(b, ExpectedConditionsEnum.presenceOfElement);
        if (element != null) {
            try {
                waitUntil(b, ExpectedConditionsEnum.ElementToBeClickable);
                element.click();
            } catch (Exception e) {
                try {
                    JavascriptExecutor executor = (JavascriptExecutor) driver;
                    executor.executeScript("arguments[0].click();", element);
                } catch (Exception c) {
                    String message = String.format("Couldn't click on button with selector:" +
                            " %s because of the exception: %s", ele.selector, c.getMessage());
                    logger.severe(message);
                    throw new Exception(message);
                }
            }
            new WebDriverWait(driver, twentySeconds).until(
                    webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
            if (assertion) {
                By expectedElement = returnElementLocatorBy(expectedElementOb);
                if (waitUntil(expectedElement, ExpectedConditionsEnum.ElementToBeClickable) == null) {
                    logger.severe(String.format("Expected element %s to appear after click is null", expectedElementOb.selector));
                    throw new Exception("Expected element to appear after click is null");
                }
                ;
            }
        } else {
            String message = String.format("Element with selector: %s is null", ele.selector);
            logger.severe(message);
            throw new Exception(message);
        }
    }

    @Override
    public void clickOn(ElementDto ele, Boolean... waitUntilVisibleOnly) {
        By b = returnElementLocatorBy(ele);
        WebElement element;
        if (waitUntilVisibleOnly.length > 0 && waitUntilVisibleOnly[0] == true) {
            element = waitUntil(b, ExpectedConditionsEnum.visibilityOfElementLocated);
        } else {
            element = waitUntil(b, ExpectedConditionsEnum.ElementToBeClickable);
        }

        if (element != null) {
            try {
                element.click();
            } catch (Exception e) {
                try {
                    JavascriptExecutor executor = (JavascriptExecutor) driver;
                    executor.executeScript("arguments[0].click();", element);
                } catch (Exception c) {
                    String message = String.format("Couldn't click on button with selector:" +
                            " %s because of the exception: %s", ele.selector, c.getMessage());
                    logger.severe(message);
                    Assert.fail(message);
                }
            }
            new WebDriverWait(driver,twentySeconds ).until(
                    webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        } else {
            String message = String.format("Element with selector: %s is null", ele.selector);
            logger.severe(message);
            Assert.fail(message);
        }
    }


    public WebElement waitUntil(By b, ExpectedConditionsEnum condition, String... attribute) {
        try {
            WebElement element = null;
            switch (condition) {
                case invisibilityOfElements:
                    boolean f2 = (new WebDriverWait(driver, twentySeconds)).until(ExpectedConditions.invisibilityOfElementLocated(b));
                    if (f2) element = driver.findElement(b);
                    else element = null;
                    return element;
                case presenceOfElement:
                    element = (new WebDriverWait(driver, twentySeconds)).until(ExpectedConditions.presenceOfElementLocated(b));
                    return element;
                case ElementToBeClickable:
                    element = (new WebDriverWait(driver, twentySeconds)).until(ExpectedConditions.elementToBeClickable(b));
                    return element;
                case visibilityOfElementLocated:
                    element = (new WebDriverWait(driver, twentySeconds)).until(ExpectedConditions.visibilityOfElementLocated(b));
                    return element;
                case attributeToBeNotEmpty:
                    boolean flag = (new WebDriverWait(driver, twentySeconds)).until(ExpectedConditions.attributeToBeNotEmpty(driver.findElement(b), attribute[0]));
                    if (flag) element = driver.findElement(b);
                    else element = null;
                    return element;
                case invisibilityOfElementLocated:
                    boolean f = (new WebDriverWait(driver, twentySeconds)).until(ExpectedConditions.invisibilityOfElementLocated(b));
                    if (f) element = driver.findElement(b);
                    else element = null;
                    return element;
                case pageIsLoaded:
                    driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
                    return element;
                default:
                    element = null;
                    Assert.fail("Wrong condition");
            }
            return element;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public WebElement waitUntil(ElementDto ele, ExpectedConditionsEnum condition, String... attribute) {
        By b = returnElementLocatorBy(ele);
        try {
            WebElement element = null;
            switch (condition) {
                case invisibilityOfElements:
                    boolean f2 = (new WebDriverWait(driver, twentySeconds)).until(ExpectedConditions.invisibilityOfElementLocated(b));
                    if (f2) element = driver.findElement(b);
                    else element = null;
                    return element;
                case presenceOfElement:
                    element = (new WebDriverWait(driver, twentySeconds)).until(ExpectedConditions.presenceOfElementLocated(b));
                    return element;
                case ElementToBeClickable:
                    element = (new WebDriverWait(driver, twentySeconds)).until(ExpectedConditions.elementToBeClickable(b));
                    return element;
                case visibilityOfElementLocated:
                    element = (new WebDriverWait(driver, twentySeconds)).until(ExpectedConditions.visibilityOfElementLocated(b));
                    return element;
                case attributeToBeNotEmpty:
                    boolean flag = (new WebDriverWait(driver, twentySeconds)).until(ExpectedConditions.attributeToBeNotEmpty(driver.findElement(b), attribute[0]));
                    if (flag) element = driver.findElement(b);
                    else element = null;
                    return element;
                case invisibilityOfElementLocated:
                    boolean f = (new WebDriverWait(driver, twentySeconds)).until(ExpectedConditions.invisibilityOfElementLocated(b));
                    if (f) element = driver.findElement(b);
                    else element = null;
                    return element;
                case pageIsLoaded:
                    driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
                    return element;
                default:
                    element = null;
                    Assert.fail("Wrong condition");
            }
            return element;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void clickOnOneElementFromListOfElements(ElementDto ele, String value) {
        By b = returnElementLocatorBy(ele); // wait till all search block appears
//        waitUntil(b, ExpectedConditionsEnum.visibilityOfElementLocated);
        List<WebElement> elements = driver.findElements(b);
        for (WebElement element : elements) {
            if (element == null) {
                Assert.fail("Can't click on element with selector : " + ele.selector);
            }
            if (element.getAttribute("label").equals(value)) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                element.click();
            }

        }
        sendKeys(ele, Keys.TAB);

    }

    @Override
    public String getText(ElementDto ele) {
        try {
            By b = returnElementLocatorBy(ele);
            WebElement element = waitUntil(b, ExpectedConditionsEnum.presenceOfElement);
            if (element != null) {
                return element.getText();
            } else {
                return null;
            }

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getValue(ElementDto ele) {
        try {
            By b = returnElementLocatorBy(ele);
            WebElement element = waitUntil(b, ExpectedConditionsEnum.visibilityOfElementLocated);
            if (element != null) {
                return element.getAttribute("value");
            } else {
                return null;
            }

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<String> ClickFirstThenGetText(ElementDto eleToClickOn, ElementDto eleToGetElements) {
        clickOn(eleToClickOn);
        List<String> elements = new ArrayList();
        elements = getElementsText(eleToGetElements);
        return elements;
    }

    @Override
    public void navigateToPage(String url, ElementDto ele) {
        driver.get(url);
        By b = returnElementLocatorBy(ele);
        new WebDriverWait(driver, twentySeconds).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));

        WebElement element = waitUntil(b, ExpectedConditionsEnum.presenceOfElement);
        assertNotNull(element, "Navigation Failed to this Website the expected element is null " + url);
    }

    @Override
    public void navigateToPage(String url) {
        driver.get(url);
        new WebDriverWait(driver, twentySeconds).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    @Override
    public By returnElementLocatorBy(ElementDto elementDto) {
        switch (elementDto.locator) {
            case XPath:
                return new By.ByXPath(elementDto.selector);

            case Id:
                return new By.ById(elementDto.selector);

            case ClassName:
                return new By.ByClassName(elementDto.selector);


            case CSS:
                return new By.ByCssSelector(elementDto.selector);


            default:
                return null;
        }
    }

    @Override
    public int getMatchingElementsCount(ElementDto ele) {
        By b = returnElementLocatorBy(ele);
        WebElement element = waitUntil(b, ExpectedConditionsEnum.visibilityOfElementLocated);
        if (element != null) {
            try {
                List<WebElement> elements = driver.findElements(returnElementLocatorBy(ele));
                if (elements != null) {
                    return elements.size();
                }
            } catch (Exception e) {
            }
        } else {
            String message = String.format("Element with selector: %s is null", ele.selector);
            logger.severe(message);
        }
        return 0;

    }


    @Override
    public boolean isElementDisplayed(ElementDto ele) {
        return false;
    }

    @Override
    public List<String> getElementsText(ElementDto ele) {
        By b = returnElementLocatorBy(ele);
        waitUntil(b, ExpectedConditionsEnum.presenceOfElement);
        List<WebElement> elements = driver.findElements(b);
        List<String> textList = new ArrayList<>();
        if (elements != null) {
            for (WebElement element : elements) {
                textList.add(element.getText());
            }
        }

        return textList;
    }

    @Override
    public List<String> getUniqueElementsText(ElementDto ele) {
        By b = returnElementLocatorBy(ele);
        waitUntil(b, ExpectedConditionsEnum.presenceOfElement);
        List<WebElement> elements = driver.findElements(b);
        HashMap<String, String> unique = new HashMap<>();
        List<String> textList = new ArrayList<>();
        if (elements != null) {
            for (WebElement element : elements) {
                String val = element.getText();
                if (!unique.containsKey(val)) {
                    unique.put(val, val);
                    textList.add(element.getText());
                }
            }
        }
        return textList;
    }

    @Override
    public boolean isElementExist(ElementDto ele) {
        return (!driver.findElements(
                returnElementLocatorBy(ele)
        ).isEmpty());
    }

    @Override
    public boolean isElementEnabled(ElementDto ele) throws Exception {
        By b = returnElementLocatorBy(ele);
        WebElement element = waitUntil(b, ExpectedConditionsEnum.presenceOfElement);
        if (element != null) {
            return (driver.findElement(
                    returnElementLocatorBy(ele)
            ).isEnabled());
        } else {
            String message = String.format("Element with selector: %s is null", ele.selector);
            logger.severe(message);
            throw new Exception(message);
        }


    }

    @Override
    public boolean isElementSelected(ElementDto ele) {
        return false;
    }

    @Override
    public void assertElementText(ElementDto ele, String expectedText) {

    }

    @Override
    public boolean isElementClickable(ElementDto ele) {
        By b = returnElementLocatorBy(ele);
        WebElement element = waitUntil(b, ExpectedConditionsEnum.presenceOfElement);
        if (element != null) {
            try {
                driver.findElement(
                        returnElementLocatorBy(ele)
                ).click();
                return true;
            } catch (Exception e) {
                return false;
            }

        }
        return true;
    }

    @Override
    public void clearField(ElementDto ele) {

        By b = returnElementLocatorBy(ele);
        waitUntil(b, ExpectedConditionsEnum.visibilityOfElementLocated);

        driver.findElement(b).clear();
    }

    @Override
    public boolean clickOnListOfButtons(ElementDto ele) {
        try {

            By b = returnElementLocatorBy(ele);
            List<WebElement> elements = driver.findElements(b);
            if (elements != null && elements.size() > 0) {
                for (int i = 0; i < elements.size(); i++) {
                    elements.get(i).click();
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // to select one element from list of elements
    @Override
    public void clickOnOneElementFromListOfElements(ElementDto ele, int index) {
        By b = returnElementLocatorBy(ele); // wait till all search block appears
        // waitUntil(b,ExpectedConditionsEnum.ElementToBeClickable);

        List<WebElement> elements = driver.findElements(b);

        for (WebElement element : elements) {

            if (element == null) {
                Assert.fail("Can't click on element with selector : " + ele.selector);
            }

        }
        elements.get(index).click();
        //   WebElement element = waitUntil(b, ExpectedConditionsEnum.presenceOfElement);

    }

    @Override
    public String getAttribute(ElementDto ele, String attribute) {
        By b = returnElementLocatorBy(ele);
        WebElement element = waitUntil(b, ExpectedConditionsEnum.presenceOfElement);
        if (element != null) {
            if (isElementExist(ele)) {

                return
                        driver.findElement(returnElementLocatorBy(ele))
                                .getAttribute(attribute);
            }
        } else {
            String message = String.format("Element with selector: %s is null", ele.selector);
            logger.severe(message);
        }
        return null;

    }

    @Override
    public void clickOnKeyBoard(ElementDto ele, KeyboardKeys key) {
        By b = returnElementLocatorBy(ele);
        WebElement webElement = driver.findElement(b);
        Keys k;
        switch (key) {
            case down:
                k = Keys.DOWN;
                break;

            case up:
                k = Keys.UP;
                break;

            case enter:
                k = Keys.ENTER;
                break;

            case tab:
                k = Keys.TAB;
                break;

            case esc:
                k = Keys.ESCAPE;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + key);
        }

        webElement.sendKeys(k);
    }

    @Override
    public void implicitWait(int seconds) {
        driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
    }


}
