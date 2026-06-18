package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Utility class for reading test configuration from properties file.
 * Properties are loaded once at class initialization for performance.
 */
public class ConfigReader {

    private static Properties properties;

    // Static initialization ensures properties are loaded only once
    static {
        try {
            FileInputStream file = new FileInputStream("src/test/resources/config/config.properties");
            properties = new Properties();
            properties.load(file);
        } catch (IOException e) {
            throw new RuntimeException("Configuration file not found", e);
        }
    }

    /**
     * Returns configuration value for the given key.
     * @param key the property key
     * @return the property value or null if key doesn't exist
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}