package com.naukri.apptracer.logger;

import android.os.Environment;

import com.naukri.apptracer.utils.Constants;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class AppTracerTimingLogger {
    private static final Logger LOGGER;
    private static long startMilli = 0;
    private static final String NEW_LINE = "\n";
    private static String VIEW_CATEGORY = "View Category:";
    private static String LOAD_TIME = "Load Time:";
    private static String NETWORK = "Network:";
    private static HashMap<String, Long> startTimeMap = new HashMap<String, Long>();
    public static final int WIFI = 0;
    public static final int MOBILE = 1;

    static {
        LOGGER = Logger.getLogger(Constants.LOGGER);
        FileHandler handler;
        try {
            File file = new File(Environment.getExternalStorageDirectory(),
                    Constants.LOGGER);
            handler = new FileHandler(file.getAbsolutePath(), true);
            LOGGER.addHandler(handler);
            handler.setFormatter(new SimpleFormatter());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * prints log at Log Level.INFO
     */
    public static void info(String msg) {
        LOGGER.log(Level.INFO, VIEW_CATEGORY + msg);
    }


    public static void error(String msg, boolean showTime) {
        long endTime = System.currentTimeMillis();
        if (startMilli != 0 && showTime) {
            LOGGER.log(Level.SEVERE, msg + Constants.BLANK_SPACE
                    + (endTime - startMilli) + "ms");
        } else {
            LOGGER.log(Level.SEVERE, msg);
        }
        startMilli = endTime;
    }

    /**
     * reset timer
     */
    public static void reset() {
        startMilli = 0;
    }

    public static void setStartTimeToCurrent() {
        startMilli = System.currentTimeMillis();
    }

}
