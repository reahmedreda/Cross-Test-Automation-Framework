package com.testAutomationFramework;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;


public class LoggingHandling {

    public static Logger logger = Logger.getLogger(LoggingHandling.class.getName());
    static FileHandler f1 = null;
    public static String logFileName;
    private static FileHandler fh=null,fh2 = null;
    static Logger globalLogger = Logger.getLogger("global");
    static String  logsFolder = "Logs";


    static {
        createNewLog();
    }

    public static void createNewLog() {
        Handler[] handlers = globalLogger.getHandlers();
        for (Handler handler : handlers) {
            globalLogger.removeHandler(handler);
        }
        handlers = logger.getHandlers();
        for (Handler handler : handlers) {
            logger.removeHandler(handler);
        }
        SimpleDateFormat format = new SimpleDateFormat("d-M-y  HH-mm-ss");
        try {
            File theDir = new File(logsFolder);
            if (!theDir.exists()){
                theDir.mkdirs();
            }

            logFileName = logsFolder+"/" + format.format(Calendar.getInstance().getTime()) + ".log";
            fh = new FileHandler(logFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        LoggingHandling.logger.addHandler(fh);
        LoggingHandling.logger.setUseParentHandlers(false);
        BriefLogFormatter.init();
        logger.info("");
        logger.warning("");
        logger.severe("");
    }

    public static void createNewLog(String fileName) {
        Handler[] handlers = globalLogger.getHandlers();
        for (Handler handler : handlers) {
            globalLogger.removeHandler(handler);
        }
        handlers = logger.getHandlers();
        for (Handler handler : handlers) {
            logger.removeHandler(handler);
        }
        try {
            logFileName = "Logs/" + fileName+ ".log";
            fh = new FileHandler(logFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        LoggingHandling.logger.addHandler(fh);
        LoggingHandling.logger.setUseParentHandlers(false);
        BriefLogFormatter.init();
        logger.info("");
        logger.warning("");
        logger.severe("");
    }

    public static void logError(Exception e) {
        // logger.severe(e.getMessage());
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        logger.severe("Error: " + sw.toString());

    }



    public static void reportMailingError(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        logger.severe("Error: " + sw.toString());
    }

    public static void logError(FileNotFoundException e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        logger.severe("Error: " + sw.toString());
    }

}



