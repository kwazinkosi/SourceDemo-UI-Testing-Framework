package utils;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

/**
 * LoggingManager is a utility class for managing logging operations.
 * It provides methods to log messages at different levels (info, error, warn, debug).
 */
public class LoggingManager {

    private static final Logger logger = LogManager.getLogger(LoggingManager.class);

    /**
     * Configures the logging system using the specified Log4j2 configuration file.
     * Sets the path for the log file and initializes the logger configuration.
     * @param suiteName 
     */
    public static void configureLogging(String suiteName) {
        
    	String fileSeparator = File.separator;
        String logDirPath = System.getProperty("user.dir") + fileSeparator + "logs";
        String logFilePath = logDirPath + fileSeparator + suiteName + "_logs.log"; // Dynamic log file name
        String pathToXml = System.getProperty("user.dir") + fileSeparator + "log4j2.xml";

        // Set system property for log file path
        System.setProperty("logFilePath", logFilePath);

        // Ensure the log directory exists
        File logDir = new File(System.getProperty("user.dir") + fileSeparator + "logs");
        if (!logDir.exists()) {
            logDir.mkdirs();
        }

        // Initialize the logger configuration
        Configurator.initialize(null, pathToXml);

        // Log an info message to verify configuration
        logger.info("Logging configuration initialized. Log file path: " + logFilePath);
    }

    /**
     * Logs an informational message.
     * 
     * @param message The message to log.
     */
    public static void info(String message) {
        logger.info(message);
    }

    /**
     * Logs an error message along with an exception.
     * 
     * @param message   The error message to log.
     * @param throwable The exception to log.
     */
    public static void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

    /**
     * Logs a warning message.
     * 
     * @param message The warning message to log.
     */
    public static void warn(String message) {
        logger.warn(message);
    }

    /**
     * Logs a debug message.
     * 
     * @param message The debug message to log.
     */
    public static void debug(String message) {
        logger.debug(message);
    }

	public static void error(String message) {
		logger.error(message);
	}

}
