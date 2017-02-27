package fr.enlight.anima.animamagiccards.utils;

import android.databinding.BindingAdapter;
import android.graphics.Typeface;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.TextView;


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

    @BindingAdapter("customFont")
    public static void setFont(TextView view, String lastFont, String newFont){
        if(lastFont == null && newFont == null){
            return;
        }

        if(lastFont == null || !lastFont.equals(newFont)) {
            lastFont = newFont;
            Typeface type = Typeface.createFromAsset(view.getContext().getAssets(), "fonts/" + lastFont + ".ttf");
            view.setTypeface(type);
        }
    }
}
