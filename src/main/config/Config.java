package main.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;
import java.util.Properties;

public class Config {

    private static final String FILENAME = "config.properties";

    private static void restoreDefaultProperties() {
        Properties defaultProp = new Properties();
        createConfigFile();
        defaultProp.setProperty("greenTime", "02:00");
        defaultProp.setProperty("blackTime", "04:00");
        defaultProp.setProperty("oolongTime", "02:00");
        defaultProp.setProperty("fruitsTime", "10:00");
        defaultProp.setProperty("detoxTime", "06:00");
        defaultProp.setProperty("uSpecTime", "03:00");
        defaultProp.setProperty("repetitionCount", "3");
        defaultProp.setProperty("musicFile", "/resources/musicfiles/Ticktac" + ".mp3");
        defaultProp.setProperty("language", Locale.getDefault().getDisplayLanguage());
        OutputStream output;
        try {
            output = getPropertiesOutputStream();
            defaultProp.store(output, FILENAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static InputStream getPropertiesInputStream() throws IOException {
        InputStream inputStream = null;
        boolean save;
        int counter = 0;
        save = false;
        while (!save) {
            if (counter > 10) {
                throw new IOException("couldn't load property file");
            }
            try {
                inputStream = new FileInputStream(FILENAME);
                save = true;
            } catch (FileNotFoundException e) {
                restoreDefaultProperties();
                counter++;
            }
        }
        return inputStream;
    }

    private static void createConfigFile() {
        new File(/*Config.class.getProtectionDomain()
                .getCodeSource().getLocation().getPath() + File.separator +*/
                FILENAME);
    }

    public static void changeTimeConfig(Properties timeProperties) throws IOException {
        OutputStream outputStream = null;
        boolean save = false;
        int counter = 0;
        InputStream inputStream = getPropertiesInputStream();
        Properties properties = new Properties();
        properties.load(inputStream);
        inputStream.close();
        while (!save) {
            if (counter > 10) {
                throw new IOException("couldn't load property file");
            }
            try {
                outputStream = getPropertiesOutputStream();
                save = true;
                counter = 0;
            } catch (FileNotFoundException e) {
                restoreDefaultProperties();
                counter++;
            }
        }
        for (String newPropString : timeProperties.stringPropertyNames()) {
            for (String oldPropString : properties.stringPropertyNames()) {
                if (newPropString.equals(oldPropString)) {
                    properties.setProperty(oldPropString, timeProperties.getProperty(oldPropString));
                }
            }
        }
        properties.store(outputStream, FILENAME);
    }

    public static void changeMusicFileConfig() {
        //@TODO
    }

    public static void changeRepetitionConfig() {
        //@TODO
    }

    public static void changeLanguageConfig() {
        //@TODO
    }

    private static OutputStream getPropertiesOutputStream() throws IOException {
        OutputStream outputStream = null;
        boolean save;
        int counter = 0;
        save = false;
        while (!save) {
            if (counter > 10) {
                throw new IOException("couldn't load property file");
            }
            try {
                outputStream = new FileOutputStream(FILENAME);
                save = true;
            } catch (FileNotFoundException e) {
                restoreDefaultProperties();
                counter++;
            }
        }
        return outputStream;
    }

    public static Properties getProperties() {
        InputStream in = null;
        try {
            in = getPropertiesInputStream();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        Properties properties = new Properties();
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }



}
