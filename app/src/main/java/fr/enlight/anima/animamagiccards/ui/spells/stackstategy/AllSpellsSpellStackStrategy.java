package fr.enlight.anima.animamagiccards.ui.spells.stackstategy;

import android.content.Context;
import android.support.annotation.DrawableRes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.enlight.anima.animamagiccards.MainApplication;
import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.ui.spells.viewmodels.quickaccess.AbstractGroupQAViewModel;
import fr.enlight.anima.animamagiccards.ui.spells.viewmodels.quickaccess.GroupQAFreeSpellsViewModel;
import fr.enlight.anima.animamagiccards.ui.spells.viewmodels.quickaccess.GroupQASpellbookPathViewModel;
import fr.enlight.anima.cardmodel.business.SpellBusinessService;
import fr.enlight.anima.cardmodel.business.SpellFilterManager;
import fr.enlight.anima.cardmodel.model.spells.Spell;
import fr.enlight.anima.cardmodel.model.spells.SpellbookType;


public class AllSpellsSpellStackStrategy implements SpellStackStrategy {

    private boolean spellExpanded;

    @Override
    public String getStackTitle(Context context) {
        if (spellExpanded) {
            return context.getString(R.string.Witchspells_Choose_Spell_Confirm);
        } else {
            return context.getString(R.string.Witch_Creation_Title);
        }
    }

    @Override
    public AbstractGroupQAViewModel createQuickAccessViewModel(AbstractGroupQAViewModel.Listener listener) {
        List<SpellbookType> spellbookTypes = Arrays.asList(SpellbookType.values());
        return new GroupQASpellbookPathViewModel(spellbookTypes, listener);
    }

    @Override
    public @DrawableRes int getCardBackground() {
        return R.drawable.card_verso;
    }

    @Override
    public int getBackgroundTextColor() {
        return android.R.color.black;
    }

    @Override
    public boolean isSelectionMode() {
        return true;
    }

    @Override
    public void setSpellExpanded(boolean spellExpanded) {
        this.spellExpanded = spellExpanded;
    }

    @Override
    public List<Spell> loadSpells(SpellBusinessService spellBusinessService, SpellFilterManager spellFilterManager) {
        // retrieve all spellbook spells
        List<Spell> spells = new ArrayList<>();
        for (SpellbookType spellbookType : SpellbookType.values()) {
            spells.addAll(spellBusinessService.getBookFromIdWithType(spellbookType.bookId, 100, MainApplication.mDefSystemLanguage));
        }

        // Then filter and return them
        return spellFilterManager.filterSpells(spells);
    }
}
