package com.qa.framework.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;

    static {
        try {
            String configFilePath = "src/test/resources/config.properties";
            FileInputStream fileInputStream = new FileInputStream(configFilePath);

            properties = new Properties();
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File config.properties tidak ditemukan! Pastikan letaknya di src/test/resources/");
            e.printStackTrace();
            throw new RuntimeException("Config file not found");
        } catch (IOException e) {
            System.out.println("ERROR: Gagal membaca file config.properties!");
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