package com.github.sufbo.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogHelper {
    public static void warning(Class<?> target, String message, Throwable cause){
        Logger logger = Logger.getLogger(target.getName());
        logger.log(Level.WARNING, message, cause);
    }
}
