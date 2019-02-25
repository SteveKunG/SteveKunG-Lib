package stevekung.mods.stevekungslib.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import stevekung.mods.stevekungslib.config.SteveKunGsLibConfig;

public class LoggerSL
{
    private static final Logger LOG = LogManager.getLogger("SteveKunG's Lib");
    private static final Logger LOG_DEBUG = LogManager.getLogger("SteveKunG's Lib Debug");
    private static final Marker INFO = MarkerManager.getMarker("INFO");
    private static final Marker ERROR = MarkerManager.getMarker("ERROR");
    private static final Marker WARNING = MarkerManager.getMarker("WARNING");
    private static final Marker DEBUG = MarkerManager.getMarker("DEBUG");

    public static void info(String message)
    {
        LoggerSL.LOG.info(INFO, message);
    }

    public static void error(String message)
    {
        LoggerSL.LOG.error(ERROR, message);
    }

    public static void warning(String message)
    {
        LoggerSL.LOG.warn(WARNING, message);
    }

    public static void debug(String message)
    {
        if (SteveKunGsLibConfig.GENERAL.enableDebugLog.get())
        {
            LoggerSL.LOG_DEBUG.info(DEBUG, message);
        }
    }

    public static void info(String message, Object... obj)
    {
        LoggerSL.LOG.info(INFO, message, obj);
    }

    public static void error(String message, Object... obj)
    {
        LoggerSL.LOG.error(ERROR, message, obj);
    }

    public static void warning(String message, Object... obj)
    {
        LoggerSL.LOG.warn(WARNING, message, obj);
    }

    public static void debug(String message, Object... obj)
    {
        if (SteveKunGsLibConfig.GENERAL.enableDebugLog.get())
        {
            LoggerSL.LOG_DEBUG.info(DEBUG, message, obj);
        }
    }
}