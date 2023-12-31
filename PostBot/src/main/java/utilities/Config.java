package utilities;

import java.util.Properties;

import static utilities.FileUtilitiess.loadProperties;

public class Config {
    private static Properties properties;

    public static String get(String key) {
        if (properties == null) {
            String currentDir = System.getProperty("user.dir");
            String filePath = currentDir + "\\..\\vk.key";
            properties = loadProperties(filePath);
            return properties.getProperty(key);
        }
        return properties.getProperty(key);
    }
}