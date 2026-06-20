package utils;

import java.io.IOException;
import java.util.Properties;
import java.io.InputStream;

/**
 * Utility class for reading test configuration from properties file.
 * Properties are loaded once at class initialization for performance.
 */
public class ConfigReader {

    private static final Properties properties = new Properties();

    // Static initialization ensures properties are loaded only once
    static {

        // Determine environment: use system property 'env' set via -Denv=qa (default: dev)
        String env = System.getProperty("env", "dev").trim();

        // First, try to load the base config.properties (if present). This preserves
        // existing keys like browser, headless, username, etc.
        String baseConfigPath = "config/config.properties";
        try (InputStream baseStream = ConfigReader.class.getClassLoader().getResourceAsStream(baseConfigPath)) {
            if (baseStream != null) {
                properties.load(baseStream);
            } // else: base config is optional when using environment-specific files
        } catch (IOException e) {
            throw new RuntimeException("Failed to load base configuration from '" + baseConfigPath + "'", e);
        }

        // Then load the environment-specific properties and overlay on top of base
        String envConfigPath = "config/" + env + ".properties";
        try (InputStream envStream = ConfigReader.class.getClassLoader().getResourceAsStream(envConfigPath)) {
            if (envStream == null) {
                throw new RuntimeException("Environment properties file not found: '" + envConfigPath + "'. "
                        + "Make sure the file exists under src/test/resources/config and that you set -Denv=" + env + " (or use default 'dev').");
            }
            Properties envProps = new Properties();
            envProps.load(envStream);
            // Overlay environment-specific properties (they take precedence)
            properties.putAll(envProps);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load environment configuration from '" + envConfigPath + "'", e);
        }

        System.out.println("Loaded configuration for environment='" + env + "' from '" + envConfigPath + "'");
    }

    /**
     * Returns configuration value for the given key.
     * @param key the property key
     * @return the property value or null if key doesn't exist
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Returns configuration value for the given key or defaultValue if missing.
     */
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}