package fr.enlight.anima.cardmodel.utils;


import android.text.TextUtils;

public class StringUtils {

    public static boolean containsIgnoreCase(String source, String toSearch) {
        return !TextUtils.isEmpty(source) && source.toLowerCase().contains(toSearch.toLowerCase());
    }

}
