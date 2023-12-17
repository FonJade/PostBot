package utilities;

import aquality.selenium.core.logging.Logger;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class FileUtilitiess {
    public static Properties loadProperties(String path) {
        Properties properties = null;
        try(FileInputStream fis = new FileInputStream(path)) {
            Logger.getInstance().debug("Loading configuration file: "+path);
            properties = new Properties();
            properties.load(fis);
        } catch (Exception e) {
            Logger.getInstance().error("Properties can't be loaded");
            e.printStackTrace();
        }
        return properties;
    }

    public static void saveBufferedImageToFile(BufferedImage image, String format){
        try {
            Logger.getInstance().debug("Saving buffered image to ."+format);
            ImageIO.write(image,format, new File("target/screeshots/checked_photo."+format));
        } catch (IOException e) {
            Logger.getInstance().error("Image cannot be saved");
            e.printStackTrace();
        }
    }
}