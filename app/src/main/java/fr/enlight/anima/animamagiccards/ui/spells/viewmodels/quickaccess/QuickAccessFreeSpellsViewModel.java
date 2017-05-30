package fr.enlight.anima.animamagiccards.ui.spells.viewmodels.quickaccess;

import com.android.databinding.library.baseAdapters.BR;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;


public class QuickAccessFreeSpellsViewModel implements BindableViewModel {

    @Override
    public int getLayoutRes() {
        return R.layout.view_quick_access_free_spells;
    }

    @Override
    public int getVariableId() {
        return BR.model;
    }
}
