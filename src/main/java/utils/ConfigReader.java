

package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;


/**
 * ConfigReader is a utility class for loading and accessing configuration properties from a properties file.
 */
public class ConfigReader {
    private static Properties properties = new Properties();

    static {
        FileInputStream input = null;
        String prop_path = File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"config"+File.separator+"config.properties";
        try {
            LoggingManager.info("Loading the properties file");
            // Define the path to the config.properties file relative to the current working directory
            String configFilePath = Paths.get(System.getProperty("user.dir"), prop_path).toString();
            input = new FileInputStream(configFilePath);
            properties.load(input);
        } catch (IOException e) {
            LoggingManager.error("Failed to load properties file: " + e.getMessage(), e);
        } finally {
            // Ensure the FileInputStream is closed to avoid resource leaks
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    LoggingManager.error("Failed to close properties file stream: " + e.getMessage(), e);
                }
            }
        }
    }

    
    /**
     * Retrieves the value for the specified property from the configuration file.
     * 
     * @param prop The name of the property to retrieve.
     * @return The value of the property as a string, or null if the property is not found.
     */
    public static String getProperty(String prop) {
        return properties.getProperty(prop);
    }

}
