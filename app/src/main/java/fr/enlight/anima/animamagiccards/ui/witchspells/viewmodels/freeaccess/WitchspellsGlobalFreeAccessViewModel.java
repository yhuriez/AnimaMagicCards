package fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels.freeaccess;


import android.content.Context;

import fr.enlight.anima.animamagiccards.R;

public class WitchspellsGlobalFreeAccessViewModel extends WitchspellsFreeAccessViewModel {

    private final int totalAvailableSpells;
    private final int selectedSpellsCount;

    private final Listener mListener;

    public WitchspellsGlobalFreeAccessViewModel(int totalAvailableSpells, int selectedSpellsCount, Listener mListener) {
        this.totalAvailableSpells = totalAvailableSpells;
        this.selectedSpellsCount = selectedSpellsCount;
        this.mListener = mListener;
    }

    @Override
    public String getFreeAccessMessage(Context context) {
        if(totalAvailableSpells == 0){
            return context.getString(R.string.Witchspells_Free_Access_Choose);
        }
        return context.getResources().getQuantityString(R.plurals.Witchspells_Free_Access_Modify, selectedSpellsCount, selectedSpellsCount, totalAvailableSpells);
    }

    @Override
    public void onItemClicked() {
        mListener.onFreeAccessSelected();
    }

    public interface Listener{
        void onFreeAccessSelected();
    }
}
