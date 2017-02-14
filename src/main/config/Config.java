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
            output.close();
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
        new File(FILENAME);
    }

    public static void updateProperties(Properties newProperties) throws IOException {
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
        for (String newPropString : newProperties.stringPropertyNames()) {
            for (String oldPropString : properties.stringPropertyNames()) {
                if (newPropString.equals(oldPropString)) {
                    properties.setProperty(oldPropString, newProperties.getProperty(oldPropString));
                }
            }
        }
        properties.store(outputStream, FILENAME);
        outputStream.close();
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
            e.printStackTrace();
        }
        Properties properties = new Properties();
        try {
            properties.load(in);
            if (in != null) {
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }



}
