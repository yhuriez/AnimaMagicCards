package fr.enlight.anima.cardmodel.dao;

import java.util.Arrays;
import java.util.List;


public class LocalizedFileDao {

    public static final List<String> localizationHandled = Arrays.asList("fr");

    private String mainDirectory;
    private String locale;

    public LocalizedFileDao(String mainDirectory, String locale) {
        this.mainDirectory = mainDirectory;
        this.locale = locale;
    }

    public String getLocalizedAssetFilename(String fileName){
        if(localizationHandled.contains(locale)){
            return mainDirectory + "-" + locale + "/" + fileName;
        }
        return mainDirectory + "/" + fileName;
    }
}
