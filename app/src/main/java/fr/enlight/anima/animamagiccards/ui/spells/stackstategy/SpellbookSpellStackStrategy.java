package fr.enlight.anima.animamagiccards.ui.spells.stackstategy;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import java.util.List;

import fr.enlight.anima.animamagiccards.MainApplication;
import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.ui.spells.viewmodels.quickaccess.AbstractGroupQAViewModel;
import fr.enlight.anima.animamagiccards.ui.spells.viewmodels.quickaccess.GroupQAFreeSpellsViewModel;
import fr.enlight.anima.cardmodel.business.SpellBusinessService;
import fr.enlight.anima.cardmodel.business.SpellFilterManager;
import fr.enlight.anima.cardmodel.model.spells.Spell;
import fr.enlight.anima.cardmodel.model.spells.Spellbook;
import fr.enlight.anima.cardmodel.model.spells.SpellbookType;


public class SpellbookSpellStackStrategy implements SpellStackStrategy{

    @NonNull
    private final Spellbook spellbook;

    public SpellbookSpellStackStrategy(@NonNull Spellbook spellbook) {
        this.spellbook = spellbook;
    }

    @Override
    public String getStackTitle(Context context) {
        return spellbook.bookName;
    }

    @Override
    public AbstractGroupQAViewModel createQuickAccessViewModel(AbstractGroupQAViewModel.Listener listener) {
        if(spellbook.bookId == SpellbookType.FREE_ACCESS.bookId){
            return new GroupQAFreeSpellsViewModel(100, listener);
        }
        return null;
    }


    @Override
    public @DrawableRes
    int getCardBackground() {
        return R.drawable.card_verso;
    }

    @Override
    public int getBackgroundTextColor() {
        return android.R.color.black;
    }

    @Override
    public boolean isSelectionMode() {
        return false;
    }

    @Override
    public List<Spell> loadSpells(SpellBusinessService spellBusinessService, SpellFilterManager spellFilterManager) {
        // retrieve all spellbook spells
        List<Spell> spells = spellBusinessService.getBookFromIdWithType(spellbook.bookId, 100, MainApplication.mDefSystemLanguage);

        // Then filter and return them
        return spellFilterManager.filterSpells(spells);
    }

    @Override
    public void setSpellExpanded(boolean spellExpanded) {
        // Nothing to do here
    }
}
