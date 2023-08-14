package com.testAutomationFramework.testNgUtilities;


import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

public class CustomTestListener implements ITestListener {

    private List<TestResult> testResults = new ArrayList<>();
    int skippedCount=0,successCount=0,failedCount=0;
    double totalTime = 0;
    private boolean beforeTestFailed = false;
    private StringBuilder systemOutContent = new StringBuilder();

    public Map<String, String> getTestParams() {
        return testParams;
    }


    public void setTestParams(Map<String, String> testParams) {
        this.testParams = testParams;
    }

    Map<String,String> testParams = new HashMap<>();

    @Override
    public void onStart(ITestContext context) {
        for (Map.Entry<String, String> parameterEntry : getTestParams().entrySet()) {
            String paramName = parameterEntry.getKey();
            String paramValue = parameterEntry.getValue();
            context.getCurrentXmlTest().addParameter(paramName,paramValue);
        }
    }


    @Override
    public void onTestStart(ITestResult result) {
        // Perform actions when a test starts
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // Perform actions when a test passes
        successCount++;
        captureTestResult(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // Perform actions when a test fails
        if (result.getMethod().isBeforeTestConfiguration()) {
            beforeTestFailed = true;
        }
        failedCount++;
        captureTestResult(result);
        result.setStatus(ITestResult.FAILURE);
        result.setThrowable(null);
    }

    @Override
    public void onFinish(ITestContext context) {
        if (beforeTestFailed) {
            // Append system out content to the JUnit XML report
            for (ITestResult result : context.getFailedTests().getAllResults()) {
                if (result.getMethod().isBeforeTestConfiguration()) {
                    systemOutContent.append(getSystemOutContent());
                    break; // Append once for the first failed @BeforeTest
                }
            }
        }
    }
    public String getSystemOutContent() {
        return systemOutContent.toString();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // Perform actions when a test is skipped
        skippedCount++;
        captureTestResult(result);
    }

    // ... Other listener methods ...

    public String generateJUnitXmlReport() {
        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
        xmlBuilder.append("<testsuites>\n");
        xmlBuilder.append("  <testsuite name=\"Test Suite\" timestamp=\"")
                .append(new Date().getTime()).append("\" tests=\"")
                .append(testResults.size())
                .append("\" success=\"")
                .append(successCount)
                .append("\" failures=\"")
                .append(failedCount)
                .append("\" errors=\"0\" skipped=\"")
                .append(skippedCount)
                .append("\" time=\"")
                .append(totalTime).append("\">\n");

        if (beforeTestFailed) {
            xmlBuilder.append("    <system-out><![CDATA[").append(systemOutContent).append("]]></system-out>\n");
        }
        for (TestResult result : testResults) {
            xmlBuilder.append(result.toJUnitXml()).append("\n");
        }

        xmlBuilder.append("  </testsuite>\n");
        xmlBuilder.append("</testsuites>");
        return xmlBuilder.toString();
    }

    private void getTotalExecutionTime(ITestResult tr) {

            totalTime += tr.getEndMillis() - tr.getStartMillis();
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
