package fr.enlight.anima.animamagiccards.utils;


import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;
import java.util.Map;

public class TypefaceUtils {

    private static final String TYPEFACE_FOLDER = "fonts";
    private static final String TYPEFACE_EXTENSION = ".ttf";

    private static Map<String, Typeface> sTypeFaces = new HashMap<>();


    public static Typeface getTypeFace(Context context, String fileName) {
        Typeface tempTypeface = sTypeFaces.get(fileName);

        if (tempTypeface == null) {
            String fontPath = TYPEFACE_FOLDER + '/' + fileName + TYPEFACE_EXTENSION;
            tempTypeface = Typeface.createFromAsset(context.getAssets(), fontPath);
            sTypeFaces.put(fileName, tempTypeface);
        }

        return tempTypeface;
    }
}
