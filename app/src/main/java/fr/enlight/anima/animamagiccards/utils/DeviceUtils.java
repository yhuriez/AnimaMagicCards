package fr.enlight.anima.animamagiccards.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


public class DeviceUtils {

    public static void hideSoftKeyboard(final View aView) {
        @SuppressLint("WrongConstant")
        InputMethodManager imm = (InputMethodManager) aView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(aView.getWindowToken(), 0);
    }
}
