package com.qa.framework.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigReader {
    private static Properties properties;
    private static final Logger log = LoggerFactory.getLogger(DriverManager.class);

    static {
        try {
            String env = System.getProperty("env", "");                                                                                                                                                                                                                                 
            String configFile = env.isEmpty() ? "config.properties" : "config." + env.toLowerCase() + ".properties";

            InputStream input = ConfigReader.class
                    .getClassLoader()
                    .getResourceAsStream(configFile);

            if (input == null) {
                throw new RuntimeException("config.properties not found in classpath!");
            }

            properties = new Properties();
            properties.load(input);
            input.close();
        } catch (IOException e) {
            log.error("Error : Failed to read config file");
            e.printStackTrace();
            throw new RuntimeException("Failed to read config file");
        }
    }

    public static String getProperty(String key) {
        String sysProp = System.getProperty(key);
        if (sysProp != null && !sysProp.isEmpty()) {
            return sysProp;
        }
        return properties.getProperty(key);
    }
}