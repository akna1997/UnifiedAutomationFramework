package com.qa.framework.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;

    static {
        try {
            InputStream input = ConfigReader.class
                    .getClassLoader()
                    .getResourceAsStream("config.properties");

            if (input == null) {
                throw new RuntimeException("config.properties tidak ditemukan di classpath!");
            }
            
            properties = new Properties();
            properties.load(input);
            input.close();
        } catch (IOException e) {
            System.out.println("Error : Gagal membaca config.properties");
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