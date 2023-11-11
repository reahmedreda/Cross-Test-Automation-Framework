package com.testAutomationFramework.actionsImp.testingAsService;

import com.testAutomationFramework.utils.testNgUtilities.CustomTestListener;
import com.testAutomationFramework.utils.testNgUtilities.TestNgDynamicRunner;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public @RestController
class TestExecutionController {


    String outputPath = "test-report-";

    @GetMapping("/run-test-package")
    public ResponseEntity<String> runTestPackage(@RequestParam(required = false) String packageName,
                                                 @RequestParam(required = false) Map<String, String> allParams) {

        try {
            CustomTestListener listener = new CustomTestListener();
            HashMap<String,String> testParams = new HashMap<>();
            for (Map.Entry<String, String> parameterEntry : allParams.entrySet()) {
                String paramName = parameterEntry.getKey();
                String paramValue = parameterEntry.getValue();
                testParams.put(paramName,paramValue);
            }

            listener = TestNgDynamicRunner.runTestPackage(packageName, listener,testParams);


            // Generate XML results
            String o= outputPath+generateRandomAlphaNumeric(5)+".xml";
            try {
                return handleReporting(o,listener);
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e) {
           return handleExceptions(e);
        }
    }

    @GetMapping("/run-test-xml")
    public ResponseEntity<String> runTestXml(@RequestParam(required = true) String xmlName) {

        try {
            File xmlFile = new File(xmlName);
            if (!xmlFile.exists()) {
                throw new FileNotFoundException(xmlName + " is not found");
            }

            CustomTestListener listener = new CustomTestListener();
            listener= TestNgDynamicRunner.runXmlTestFile(xmlName,listener);


            // Generate XML results

            String o= outputPath+generateRandomAlphaNumeric(5)+".xml";
            try {
             return handleReporting(o,listener);
            } catch (Exception e) {
                throw e;
            }

        } catch (Exception e) {
            return handleExceptions(e);
        }
    }

    ResponseEntity<String> handleReporting(String outputPath,CustomTestListener listener) throws IOException {

        File output = null;
        try {
            output= new File(outputPath);
            String xmlReport = listener.generateJUnitXmlReport();

            System.out.println("JUnit XML Report:\n" + xmlReport);

            // Specify the desired output path
            listener.saveJUnitXmlReport(outputPath);
            System.out.println("JUnit XML report generated at: " + outputPath);
            // Read the generated XML report
            String reportContent = new String(Files.readAllBytes(Paths.get(outputPath)));

            // Return the XML report as a response
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_XML);
            return new ResponseEntity<>(reportContent, headers, HttpStatus.OK);
        }
        catch (Exception e){
            throw e;
        }
        finally {
            if (output!=null) {
                output.delete();
            }
        }

    }

    ResponseEntity<String> handleExceptions(Exception e){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String stackTrace = sw.toString();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Test execution failed:\n" + stackTrace);
    }

    public String generateRandomAlphaNumeric(int size){
        return RandomStringUtils.randomAlphanumeric(size);
    }


}
