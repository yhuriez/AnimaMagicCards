package fr.enlight.anima.animamagiccards.utils.bindingrecyclerview;

import android.support.annotation.LayoutRes;

/**
 *
 */

public interface BindableViewModel{

    @LayoutRes int getLayoutRes();

    int getVariableId();
}
