package fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels;


import com.android.databinding.library.baseAdapters.BR;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;


public class WitchspellsPathItemAddViewModel implements BindableViewModel {

    public WitchspellsPathItemAddViewModel() {

    }

    @Override
    public int getLayoutRes() {
        return R.layout.view_witchspells_path_item_add;
    }

    @Override
    public int getVariableId() {
        return BR.model;
    }
}
