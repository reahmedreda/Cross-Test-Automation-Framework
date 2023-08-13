package com.testAutomationFramework.testNgUtilities;


import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomTestListener implements ITestListener {

    private List<TestResult> testResults = new ArrayList<>();

    @Override
    public void onTestStart(ITestResult result) {
        // Perform actions when a test starts
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // Perform actions when a test passes
        captureTestResult(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // Perform actions when a test fails
        captureTestResult(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // Perform actions when a test is skipped
        captureTestResult(result);
    }

    // ... Other listener methods ...

    public String generateJUnitXmlReport() {
        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
        xmlBuilder.append("<testsuite name=\"Test Suite\" timestamp=\"").append(new Date().getTime()).append("\">\n");

        for (TestResult result : testResults) {
            xmlBuilder.append(result.toJUnitXml()).append("\n");
        }

        xmlBuilder.append("</testsuite>");
        return xmlBuilder.toString();
    }
    public void saveJUnitXmlReport(String outputPath) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputPath))) {
            writer.println(generateJUnitXmlReport());
        }
    }

    private void captureTestResult(ITestResult result) {
        TestResult testResult = new TestResult(result);
        testResults.add(testResult);
    }

    private static class TestResult {
        private String className;
        private String methodName;
        private String status;
        private String stackTrace;

        public TestResult(ITestResult result) {
            className = result.getTestClass().getName();
            methodName = result.getMethod().getMethodName();
            status = result.getStatus() == ITestResult.SUCCESS ? "pass" : "fail";
            stackTrace = getStackTrace(result.getThrowable());
        }

        public String toJUnitXml() {
            return String.format(
                    "<testcase classname=\"%s\" name=\"%s\">\n" +
                            "%s" +
                            "</testcase>",
                    className, methodName, status.equals("fail") ? stackTrace : ""
            );
        }

        private String getStackTrace(Throwable throwable) {
            if (throwable != null) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                throwable.printStackTrace(pw);
                return "<failure message=\"" + throwable.getMessage() + "\">\n" + sw.toString() + "</failure>";
            }
            return "";
        }
    }
}
