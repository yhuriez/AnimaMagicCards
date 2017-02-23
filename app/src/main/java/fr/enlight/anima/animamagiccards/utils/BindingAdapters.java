package fr.enlight.anima.animamagiccards.utils;

import android.databinding.BindingAdapter;
import android.support.annotation.DrawableRes;
import android.view.View;


public class BindingAdapters {

    @BindingAdapter("setVisible")
    public static void setVisible(View view, boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("backgroundRes")
    public static void setBackgroundRes(View view, @DrawableRes int drawableRes){
        if(drawableRes > 0){
            view.setBackgroundResource(drawableRes);
        }
    }
}
