package fr.enlight.anima.animamagiccards;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import java.lang.ref.WeakReference;
import java.util.Locale;


public class MainApplication extends Application{

    private static WeakReference<MainApplication> instance;
    public static String mDefSystemLanguage;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = new WeakReference<>(this);
        mDefSystemLanguage = Locale.getDefault().getLanguage();
    }

    public static Context getMainContext(){
        return instance.get();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        mDefSystemLanguage = newConfig.locale.getLanguage();
    }

    public static String getDefSystemLanguage() {
        return mDefSystemLanguage;
    }

}
