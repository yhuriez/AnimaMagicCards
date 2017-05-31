package fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels.freeaccess;

import android.content.Context;

import fr.enlight.anima.animamagiccards.BR;
import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.cardmodel.model.spells.Spell;


public abstract class WitchspellsFreeAccessViewModel implements BindableViewModel {

    @Override
    public int getLayoutRes() {
        return R.layout.view_witchspells_free_access;
    }

    @Override
    public int getVariableId() {
        return BR.model;
    }

    public abstract String getFreeAccessMessage(Context context);

    public abstract void onItemClicked();

}
