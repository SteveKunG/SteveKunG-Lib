package stevekung.mods.stevekungslib.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class LoggerBase
{
    private final Logger log;
    private final Logger logDebug;
    private static final Marker INFO = MarkerManager.getMarker("INFO");
    private static final Marker ERROR = MarkerManager.getMarker("ERROR");
    private static final Marker WARNING = MarkerManager.getMarker("WARNING");
    private static final Marker DEBUG = MarkerManager.getMarker("DEBUG");
    private final String modName;
    private final boolean debug;

    public LoggerBase(String modName, boolean debug)
    {
        this.modName = modName;
        this.debug = debug;
        this.log = LogManager.getLogger(this.modName);
        this.logDebug = LogManager.getLogger(this.modName + " Debug");
    }

    public void info(String message)
    {
        this.log.info(INFO, message);
    }

    public void error(String message)
    {
        this.log.error(ERROR, message);
    }

    public void warning(String message)
    {
        this.log.warn(WARNING, message);
    }

    public void debug(String message)
    {
        if (this.debug)
        {
            this.logDebug.info(DEBUG, message);
        }
    }

    public void info(String message, Object... obj)
    {
        this.log.info(INFO, message, obj);
    }

    public void error(String message, Object... obj)
    {
        this.log.error(ERROR, message, obj);
    }

    public void warning(String message, Object... obj)
    {
        this.log.warn(WARNING, message, obj);
    }

    public void debug(String message, Object... obj)
    {
        if (this.debug)
        {
            this.logDebug.info(DEBUG, message, obj);
        }
    }
}