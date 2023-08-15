package com.testAutomationFramework;

import com.testAutomationFramework.utils.FileManager;
import com.testAutomationFramework.utils.MavenUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class MainApp {

    public static void main(String[] args) throws IOException {
        MavenUtils.executeMavenCommand("test-compile");
        FileManager.copyTestClassesToTarget();
        SpringApplication.run(MainApp.class, args);
    }


}
