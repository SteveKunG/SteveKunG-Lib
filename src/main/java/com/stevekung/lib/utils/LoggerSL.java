package com.stevekung.lib.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.stevekung.lib.core.SteveKunGLib;

public class LoggerSL
{
    private static final Logger LOGGER = LogManager.getLogger("SteveKunG's Lib");

    public static void info(String message)
    {
        LoggerSL.LOGGER.info(message);
    }

    public static void error(String message)
    {
        LoggerSL.LOGGER.error(message);
    }

    public static void warning(String message)
    {
        LoggerSL.LOGGER.warn(message);
    }

    public static void debug(String message)
    {
        if (SteveKunGLib.isDevelopment)
        {
            LoggerSL.LOGGER.info(message);
        }
    }

    public static void info(String message, Object... obj)
    {
        LoggerSL.LOGGER.info(message, obj);
    }

    public static void error(String message, Object... obj)
    {
        LoggerSL.LOGGER.error(message, obj);
    }

    public static void warning(String message, Object... obj)
    {
        LoggerSL.LOGGER.warn(message, obj);
    }

    public static void debug(String message, Object... obj)
    {
        if (SteveKunGLib.isDevelopment)
        {
            LoggerSL.LOGGER.info(message, obj);
        }
    }
}