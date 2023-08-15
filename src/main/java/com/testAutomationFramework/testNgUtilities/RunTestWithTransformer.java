package com.testAutomationFramework.testNgUtilities;

import com.testAutomationFramework.ui.UiSupportedLibraries;
import com.testAutomationFramework.utils.RunTestWith;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RunTestWithTransformer implements IAnnotationTransformer {
    public static Map<String,String> testVsLibraryMap = new HashMap<>();
    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        if (testMethod.isAnnotationPresent(RunTestWith.class)) {
            RunTestWith runWith = testMethod.getAnnotation(RunTestWith.class);
            UiSupportedLibraries value = runWith.value();
            testVsLibraryMap.put(testMethod.getName(),value.toString().toLowerCase(Locale.ROOT) );

        }
    }

}
