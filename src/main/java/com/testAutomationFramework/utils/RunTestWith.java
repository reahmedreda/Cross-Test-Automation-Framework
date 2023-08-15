package com.testAutomationFramework.utils;
import com.testAutomationFramework.ui.UiSupportedLibraries;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RunTestWith {
    UiSupportedLibraries value() default UiSupportedLibraries.SELENIUM;
}
