package com.agoda.api.helper;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Properties;

/**
 * @author <a href="mailto:nazar.bukhari12@gmail.com">Nazar-E-Bukhari</a>
 * @since 8/5/18.
 */
public class CommonHelper {

    private static Properties prop;

    public static String generateFileCheckSum(String fileName) {

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(Files.readAllBytes(Paths.get(fileName)));
            return DatatypeConverter.printHexBinary(messageDigest.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void loadPropertyFile() {

        prop = new Properties();
        InputStream inputStream = null;
        try {

            Resource resource = new ClassPathResource("application.properties");
            File resourceFile = resource.getFile();
            inputStream = new FileInputStream(resourceFile);
            prop.load(inputStream);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Properties getProperties() {
        return prop;
    }
}
