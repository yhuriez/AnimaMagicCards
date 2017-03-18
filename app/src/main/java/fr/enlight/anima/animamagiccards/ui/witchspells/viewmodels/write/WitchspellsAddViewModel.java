package fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels.write;


import com.android.databinding.library.baseAdapters.BR;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;

public class WitchspellsAddViewModel implements BindableViewModel{

    @Override
    public int getLayoutRes() {
        return R.layout.view_witchspells_add_item;
    }

    @Override
    public int getVariableId() {
        return BR.model;
    }


}
