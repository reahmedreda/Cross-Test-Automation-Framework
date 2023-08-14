package com.testAutomationFramework.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MavenUtils {


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
