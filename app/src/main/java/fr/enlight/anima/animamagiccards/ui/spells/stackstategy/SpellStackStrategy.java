package fr.enlight.anima.animamagiccards.ui.spells.stackstategy;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;

import java.util.List;

import fr.enlight.anima.animamagiccards.ui.spells.viewmodels.quickaccess.AbstractGroupQAViewModel;
import fr.enlight.anima.cardmodel.business.SpellBusinessService;
import fr.enlight.anima.cardmodel.business.SpellFilterManager;
import fr.enlight.anima.cardmodel.model.spells.Spell;



public interface SpellStackStrategy {

    String getStackTitle(Context context);

    @Nullable AbstractGroupQAViewModel createQuickAccessViewModel(AbstractGroupQAViewModel.Listener listener);

    @DrawableRes int getCardBackground();

    @ColorRes int getBackgroundTextColor();

    boolean isSelectionMode();

    void setSpellExpanded(boolean spellExpanded);

    List<Spell> loadSpells(SpellBusinessService spellBusinessService, SpellFilterManager spellFilterManager);
}
