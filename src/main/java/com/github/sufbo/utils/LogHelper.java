package com.github.sufbo.utils;

import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogHelper {
    private LogHelper(){
        // do nothing.
    }
    
    public static void warning(Class<?> target, String message, Throwable cause){
        Logger logger = logger(target);
        logger.log(Level.WARNING, message, cause);
    }

    public static void info(Class<?> target, String message){
        Logger logger = logger(target);
        logger.info(message);
    }

    public static void info(Class<?> target, Supplier<String> messageBuilder){
        Logger logger = logger(target);
        logger.info(messageBuilder);
    }

    private static Logger logger(Class<?> target){
        String loggerName = target.getName();
        return Logger.getLogger(loggerName);        
    }
}
