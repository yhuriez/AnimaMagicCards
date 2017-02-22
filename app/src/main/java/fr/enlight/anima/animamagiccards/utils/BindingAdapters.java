package fr.enlight.anima.animamagiccards.utils;

import android.databinding.BindingAdapter;
import android.view.View;

/**
 * Created by yann_huriez on 22/02/17.
 */

public class BindingAdapters {

    @BindingAdapter("setVisible")
    public static void setVisible(View view, boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }
}
