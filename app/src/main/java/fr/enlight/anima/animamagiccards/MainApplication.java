package fr.enlight.anima.animamagiccards;

import android.app.Application;
import android.content.Context;

import java.lang.ref.WeakReference;


public class MainApplication extends Application{

    private static WeakReference<MainApplication> instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = new WeakReference<>(this);
    }

    public static Context getMainContext(){
        return instance.get();
    }

}
