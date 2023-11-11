package com.testAutomationFramework.actionsImp.ui;

/**
 * Represents a Data Transfer Object (DTO) for storing information about a UI element.
 */

public class ElementDto {

    public String selector;
    public ElementUtilities.Locator locator;


    public ElementDto(String selector, ElementUtilities.Locator... selectors) {
        this.selector = selector;
        if (selectors.length > 0) {
            this.locator = selectors[0];
        } else {
            this.locator = ElementUtilities.Locator.XPath;
        }
    }

}

