package fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels.freeaccess;


import android.content.Context;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.cardmodel.model.spells.Spell;
import fr.enlight.anima.cardmodel.utils.SpellUtils;

public class WitchspellsSpellFreeAccessViewModel extends WitchspellsFreeAccessViewModel {

    private final int mSpellLevelPosition;
    private final Spell mSelectedSpell;

    private final Listener mListener;

    private final int mCeilingLevel;

    // Used for a Free Spell "Slot"
    public WitchspellsSpellFreeAccessViewModel(int spellLevelPosition, Listener mListener) {
        this.mSpellLevelPosition = spellLevelPosition;
        this.mCeilingLevel = SpellUtils.getCeilingLevelForSpellPosition(mSpellLevelPosition);

        this.mSelectedSpell = null;
        this.mListener = mListener;
    }

    // Used for a filled Free Spell
    public WitchspellsSpellFreeAccessViewModel(int spellLevelPosition, Spell selectedSpell, Listener mListener) {
        this.mSpellLevelPosition = spellLevelPosition;
        this.mCeilingLevel = SpellUtils.getCeilingLevelForSpellPosition(mSpellLevelPosition);

        this.mSelectedSpell = selectedSpell;
        this.mListener = mListener;
    }

    @Override
    public String getFreeAccessMessage(Context context) {
        if(mSelectedSpell == null){
            return context.getString(R.string.Witchspells_Free_Access_Slot, mCeilingLevel);
        }
        return mSelectedSpell.name;
    }

    @Override
    public void onItemClicked() {
        mListener.onSelectedFreeAccessAvailableSlot(mCeilingLevel);
    }

    public interface Listener{
        void onSelectedFreeAccessAvailableSlot(int levelAvailable);
    }
}
