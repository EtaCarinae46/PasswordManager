package controller;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class Props {
    private static Properties prop = new Properties();

    private static final String PROP_FILE = "config.properties";

    private static final String DEFAULT_DB_PATH = "./data";

    public static boolean exists() {
        return Files.exists(Paths.get(PROP_FILE));
    }

    public static boolean createProp() {
        try {
            prop.setProperty("db-url", DEFAULT_DB_PATH);
            prop.setProperty("user", "");
            prop.store(new FileWriter(PROP_FILE), null);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static String getProp(String key) {
        try {
            prop.load(new FileInputStream(PROP_FILE));
        } catch (IOException e) {
            System.out.println(String.format("Error during loading file: %s", PROP_FILE));
        }
        return prop.getProperty(key);
    }

    public static void setProp(String key, String val) {
        prop.setProperty(key, val);
        try {
            prop.store(new FileWriter(PROP_FILE), null);
        } catch (IOException e) {
            System.out.println(String.format("Error during storing file: %s, key: %s", PROP_FILE, key));
        }
    }

    public static void removeProp(String key) {
        prop.remove(key);
        try {
            prop.store(new FileWriter(PROP_FILE), null);
        } catch (IOException e) {
            System.out.println(String.format("Error during removing from file: %s, key: %s", PROP_FILE, key));
        }
    }
}
