package main.language;

import main.config.Config;

import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class Language {
    private Locale language;
    private ResourceBundle rb;

    public Language() {
        Properties properties = Config.getProperties();
        switch (properties.getProperty("language")) {
            case "English":
                language = Locale.ENGLISH;
                break;
            case "Deutsch":
                language = Locale.GERMAN;
                break;
            default:
                throw new IllegalArgumentException("Language not available: " + properties.getProperty("language"));
        }
        rb = ResourceBundle.getBundle("resources/MyResources", language);
    }

    public void setLanguage(Locale language) throws IOException {
        Locale.setDefault(language);
        this.language = Locale.getDefault();
        Properties newProperty = new Properties();
        newProperty.setProperty("language", language.getDisplayLanguage());
        Config.updateProperties(newProperty);
        rb = ResourceBundle.getBundle("resources/MyResources", language);
    }

    public Locale getLanguage() {
        return language;
    }

    public ResourceBundle getResourceBundle() {
        return rb;
    }
}
