package fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels;

import android.content.Context;

import fr.enlight.anima.animamagiccards.BR;
import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;


public class WitchspellsFreeAccessViewModel implements BindableViewModel {

    private final boolean emptyFreeAccess;
    private int selectedSpellsCount;
    private int totalAvailableSpells;

    private final Listener mListener;

    public WitchspellsFreeAccessViewModel(Listener listener) {
        emptyFreeAccess = true;
        mListener = listener;
    }

    public WitchspellsFreeAccessViewModel(int selectedSpellsCount, int totalAvailableSpells, Listener listener) {
        this.selectedSpellsCount = selectedSpellsCount;
        this.totalAvailableSpells = totalAvailableSpells;
        emptyFreeAccess = false;
        mListener = listener;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.view_quick_access_free_spells;
    }

    @Override
    public int getVariableId() {
        return BR.model;
    }

    public String getFreeAccessMessage(Context context) {
        if(emptyFreeAccess){
            return context.getString(R.string.Witchspells_Free_Access_Choose);
        }
        return context.getString(R.string.Witchspells_Free_Access_Modify, selectedSpellsCount, totalAvailableSpells);
    }

    public void onItemClicked(){
        mListener.onFreeAccessClicked();
    }

    public interface Listener{
        void onFreeAccessClicked();
    }
}
