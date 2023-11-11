package com.testAutomationFramework.utils;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesFileHandler {
    public static String propFileName = "application.properties";
    static Properties prop = new Properties();
    static boolean propInit = false;

    static {
        if(!propInit) init();
    }
    public static void  init (){
        InputStream inputStream;

        inputStream = PropertiesFileHandler.class.getClassLoader().getResourceAsStream(propFileName);
        try {
            if (inputStream != null) {
                prop.load(inputStream);
                propInit = true;
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
            propInit = false;
        } finally {
            try {
                inputStream.close();
            } catch (Exception e) {
            }
        }
    }

    public static String getPropertyValue(String key) {
        int x=0;
        while(propInit==false && x<2) {
            init();
            x++;
        }
        if(propInit){
            try {
                return prop.getProperty(key);
            } catch (Exception e) {
                throw e;
            }
        }
        return null;

    }
}
