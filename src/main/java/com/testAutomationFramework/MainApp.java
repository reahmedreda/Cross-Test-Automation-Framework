package com.testAutomationFramework;

import com.testAutomationFramework.testNgUtilities.CustomTestListener;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;

public class MainApp {

    public static void main(String[] args) throws IOException {
        String srcFolder = "src/main/java/",
                testFolder = "src/test/java/",
                destMainPackage = "com/testAutomationFramework";

        String testSrcPackage = "com/testAutomationFramework";


        try {
            Class<?> testClass = null;
            //copyTestClassesToMainPackage(testFolder + testSrcPackage, srcFolder + destMainPackage);
            String currentTestFolder = testFolder + destMainPackage;
            // Create a TestNG suite and add the requested test class
            XmlSuite suite = new XmlSuite();
            XmlTest test = new XmlTest(suite);
            Path currentFolder = Paths.get(currentTestFolder);
            executeMavenCommand("test-compile");
            copyTestClassesToTarget();
            Files.walk(currentFolder)
                    .filter(Files::isRegularFile)
                    .forEach(sourceFile -> {
                        Path relativePath = currentFolder.relativize(sourceFile);
                        //compileModifiedClasses(currentTestFolder + "/" + relativePath);
                        //executeMavenCommand("test-compile");
                        test.getXmlClasses().add(new XmlClass( testSrcPackage.replace("/",".")+"."+"CrossLibrariesUiE2eTest"));

                    });


            //test.getXmlClasses().add(new XmlClass(destMainPackage.replace("/", ".") + ".test"));
            TestNG testNG = new TestNG();
            testNG.setXmlSuites(Collections.singletonList(suite));
            CustomTestListener listener = new CustomTestListener();
            testNG.addListener(listener);
            testNG.run();

            // Generate XML results
            try {
                String xmlReport = listener.generateJUnitXmlReport();
                System.out.println("JUnit XML Report:\n" + xmlReport);

                String outputPath = "test-report.xml"; // Specify the desired output path
                listener.saveJUnitXmlReport(outputPath);
                System.out.println("JUnit XML report generated at: " + outputPath);
            } catch (Exception e) {
                System.err.println("Error generating JUnit XML report: " + e.getMessage());
            }
        } catch (Exception e) {
            throw  e;

        } finally {
            //deleteCopiedTestClasses(srcFolder + destMainPackage);
        }


    }
    public static void copyTestClassesToTarget() {
        try {
            Path sourceDir = Paths.get("target","test-classes");
            Path targetDir = Paths.get("target", "classes");

            Files.walk(sourceDir)
                    .filter(Files::isRegularFile)
                    .forEach(sourceFile -> {
                        Path relativePath = sourceDir.relativize(sourceFile);
                        Path targetFile = targetDir.resolve(relativePath);

                        try {
                            Files.createDirectories(targetFile.getParent());
                            Files.copy(sourceFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copyTestClassesToMainPackage(String sourcePackage, String destinationPackage) {
        Path sourceDir = Paths.get(sourcePackage.replace(".", File.separator));
        Path destinationDir = Paths.get(destinationPackage.replace(".", File.separator));

        try {
            Files.walk(sourceDir)
                    .filter(Files::isRegularFile)
                    .forEach(sourceFile -> {
                        Path relativePath = sourceDir.relativize(sourceFile);
                        Path destinationFile = destinationDir.resolve(relativePath);
                        try {
                            Files.createDirectories(destinationFile.getParent());
                            Files.copy(sourceFile, destinationFile, StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
            System.out.println("Classes copied from " + sourcePackage + " to " + destinationPackage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void deleteCopiedTestClasses(String destinationPackage) {
        Path destinationDir = Paths.get(destinationPackage);

        try {
            Files.walk(destinationDir)
                    .filter(Files::isRegularFile)
                    .forEach(file -> {
                        try {
                            Files.delete(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
            System.out.println("Copied test classes deleted from main package.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void compileModifiedClasses(String mainPackagePath) {
        // Compile the modified classes in the main package
        // Example command for compiling Java classes using the command-line:
        // javac -cp . <path_to_classes>/*.java
        String command = "mvn exec:exec@compile-single-file -DclassName=" + mainPackagePath;
        try {
            Process process = Runtime.getRuntime().exec(command);
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Modified classes in the main package compiled successfully.");
            } else {
                System.out.println(process.getErrorStream().toString());
                System.err.println("Error compiling modified classes in the main package.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void executeMavenCommand(String commands) {
        try {
            // Create a ProcessBuilder for the Maven command
            ProcessBuilder processBuilder = new ProcessBuilder("mvn", commands);

            // Redirect the process output to display it
            processBuilder.redirectErrorStream(true);

            // Start the process
            Process process = processBuilder.start();

            // Read and display the process output
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Wait for the process to finish
            int exitCode = process.waitFor();
            System.out.println("Process exited with code: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
