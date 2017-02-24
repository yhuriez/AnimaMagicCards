package fr.enlight.anima.animamagiccards.views.bindingrecyclerview;

import android.support.annotation.LayoutRes;

/**
 *
 */

public interface BindableViewModel{

    @LayoutRes int getLayoutRes();

    int getVariableId();
}
