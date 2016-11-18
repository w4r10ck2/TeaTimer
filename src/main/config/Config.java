package main.config;

import java.io.*;
import java.util.Locale;
import java.util.Properties;

public class Config {

    private static final String FILENAME = "config.properties";

    public static void restoreDefaultProperties() {
        Properties defaultProp = new Properties();
        defaultProp.setProperty("greenTime", "02:00");
        defaultProp.setProperty("blackTime", "04:00");
        defaultProp.setProperty("oolongTime", "02:00");
        defaultProp.setProperty("fruitsTime", "10:00");
        defaultProp.setProperty("detoxTime", "06:00");
        defaultProp.setProperty("uspecTime", "03:00");
        defaultProp.setProperty("repetitionCount", "3");
        defaultProp.setProperty("musicFile", "/resources/musicfiles/Ticktac" + ".mp3");
        defaultProp.setProperty("language", Locale.getDefault().getDisplayLanguage());
        OutputStream output;
        if (!checkIfConfigFileIsThere()) {
            createConfigFile();
        }
        try {
            output = new FileOutputStream(FILENAME);
            defaultProp.store(output, FILENAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean checkIfConfigFileIsThere() {
        try {
            FileInputStream input = new FileInputStream(Config.class.getProtectionDomain().getCodeSource().getLocation().getPath() + File.separator + FILENAME);
        } catch (FileNotFoundException e) {
            return false;
        }
        return true;
    }

    private static void createConfigFile() {
        File jarPath = new File(Config.class.getProtectionDomain().getCodeSource().getLocation().getPath());
    }

    public static void changeTimeConfig() {
        //@TODO
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

}
