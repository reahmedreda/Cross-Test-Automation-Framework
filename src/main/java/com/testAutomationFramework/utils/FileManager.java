package com.testAutomationFramework.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileManager {
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

}
