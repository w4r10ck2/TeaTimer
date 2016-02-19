package main.language;

import java.util.Locale;
import java.util.ResourceBundle;

public class Language {
    private Locale language;
    private ResourceBundle rb;

    public Language() {
        language = Locale.getDefault();
        rb = ResourceBundle.getBundle("MyResources", language);
    }

    public void setLanguage(Locale language) {
        Locale.setDefault(language);
        this.language = Locale.getDefault();
        rb = ResourceBundle.getBundle("MyResources", language);
    }

    public Locale getLanguage() {
        return language;
    }

    public ResourceBundle getResourceBundle() {
        return rb;
    }
}
