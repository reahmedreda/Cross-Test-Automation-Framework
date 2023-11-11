package com.testAutomationFramework.utils.testNgUtilities;

import com.testAutomationFramework.utils.testNgUtilities.listeners.CustomTestListener;
import com.testAutomationFramework.utils.testNgUtilities.listeners.RunTestWithTransformer;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class TestNgDynamicRunner {
    static String srcFolder = "src/main/java/",
            testFolder = "src/test/java/",
            destMainPackage = "com/testAutomationFramework",testSrcPackage = "com/testAutomationFramework";

    public static CustomTestListener runXmlTestFile(String xmlName, CustomTestListener listener){
        TestNG testNG = new TestNG();
        XmlSuite suite = new XmlSuite();
        List<XmlSuite> suites = new ArrayList<>();
        suite.setSuiteFiles(Arrays.asList(xmlName)); // Provide the path to your XML suite file

        suites.add(suite);
        testNG.setXmlSuites(suites);
        testNG.addListener(listener);
        testNG.run();
        return listener;
    }

    public static CustomTestListener runTestPackage(String packageName, CustomTestListener listener, Map<String, String>... testParameters) throws IOException {

            String currentTestFolder = testFolder + destMainPackage;
            if (packageName != null && !packageName.isEmpty()) {
                currentTestFolder += "/" + packageName;
            }

            XmlSuite suite = new XmlSuite();
            XmlTest test = new XmlTest(suite);
            Path currentFolder = Paths.get(currentTestFolder);
        if (testParameters != null && testParameters.length>0) {
            listener.setTestParams(testParameters[0]);
        }
            Files.walk(currentFolder)
                    .filter(Files::isRegularFile)
                    .forEach(sourceFile -> {
                        Path relativePath = currentFolder.relativize(sourceFile);

                        String trimmedRelativePath = "";
                        if (packageName != null && !packageName.isEmpty()) {
                            trimmedRelativePath +=
                                    "." + packageName;
                        }
                        trimmedRelativePath += "." + relativePath.toString().replace(".java", "");
                        XmlClass xmlClass = new XmlClass((testSrcPackage + trimmedRelativePath).replace("/", "."));

                        test.getXmlClasses().add(xmlClass);

                    });


        TestNG testNG = new TestNG();
        testNG.setXmlSuites(Collections.singletonList(suite));
        testNG.addListener(new RunTestWithTransformer());
        testNG.addListener(listener);
        testNG.run();
        return listener;
    }
}
