package stevekung.mods.stevekunglib.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerSL
{
    private static final Logger LOG = LogManager.getLogger("SteveKunG's Lib");
    private static final Logger LOG_DEBUG = LogManager.getLogger("SteveKunG's Lib Debug");

    public static void info(String message)
    {
        LoggerSL.LOG.info(message);
    }

    public static void error(String message)
    {
        LoggerSL.LOG.error(message);
    }

    public static void warning(String message)
    {
        LoggerSL.LOG.warn(message);
    }

    public static void debug(String message)
    {
        /*if (ConfigManagerLib.stevekung_lib_general.enableDebugLog)TODO
        {
            LoggerSL.LOG_DEBUG.info(message);
        }*/
    }

    public static void info(String message, Object... obj)
    {
        LoggerSL.LOG.info(message, obj);
    }

    public static void error(String message, Object... obj)
    {
        LoggerSL.LOG.error(message, obj);
    }

    public static void warning(String message, Object... obj)
    {
        LoggerSL.LOG.warn(message, obj);
    }

    public static void debug(String message, Object... obj)
    {
        /*if (ConfigManagerLib.stevekung_lib_general.enableDebugLog)TODO
        {
            LoggerSL.LOG_DEBUG.info(message, obj);
        }*/
    }
}