package com.terbah.sketch.app.core.logger;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * @author Dorian TERBAH
 * <p>
 * Class used to manage and create Logger.
 * @version 1.0
 */
public class SketchLoggerManager {

    private static final Map<Class<?>, Logger> LOGGERS = new HashMap<>();

    /**
     * @param clazz The clazz associated for the logger
     * @return The associated logger.
     */
    public static Logger getLogger(Class<?> clazz) {
        if (LOGGERS.containsKey(clazz)) {
            return LOGGERS.get(clazz);
        }

        Logger logger = LogManager.getLogManager().getLogger(clazz.getCanonicalName());
        if (logger == null) {
            logger = Logger.getLogger(clazz.getCanonicalName());
        }

        LOGGERS.put(clazz, logger);

        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        logger.addHandler(consoleHandler);
        return logger;
    }
}
