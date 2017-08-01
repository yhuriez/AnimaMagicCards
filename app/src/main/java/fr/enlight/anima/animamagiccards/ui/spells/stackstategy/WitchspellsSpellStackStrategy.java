package fr.enlight.anima.animamagiccards.ui.spells.stackstategy;

import android.content.Context;
import android.support.annotation.DrawableRes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import fr.enlight.anima.animamagiccards.MainApplication;
import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.ui.spells.viewmodels.quickaccess.AbstractGroupQAViewModel;
import fr.enlight.anima.animamagiccards.ui.spells.viewmodels.quickaccess.GroupQASpellbookPathViewModel;
import fr.enlight.anima.cardmodel.business.SpellBusinessService;
import fr.enlight.anima.cardmodel.business.SpellFilterManager;
import fr.enlight.anima.cardmodel.model.spells.Spell;
import fr.enlight.anima.cardmodel.model.spells.SpellbookType;
import fr.enlight.anima.cardmodel.model.witchspells.Witchspells;
import fr.enlight.anima.cardmodel.model.witchspells.WitchspellsPath;


public class WitchspellsSpellStackStrategy implements SpellStackStrategy{

    private final Witchspells witchspells;

    public WitchspellsSpellStackStrategy(Witchspells witchspells) {
        this.witchspells = witchspells;
    }

    @Override
    public String getStackTitle(Context context) {
        return context.getString(R.string.Witchspells_Name_Format, witchspells.witchName);
    }

    @Override
    public AbstractGroupQAViewModel createQuickAccessViewModel(AbstractGroupQAViewModel.Listener listener) {
        List<SpellbookType> spellbookTypes = new ArrayList<>();
        for (WitchspellsPath path : witchspells.witchPaths) {
            spellbookTypes.add(SpellbookType.getTypeFromBookId(path.pathBookId));
        }
        return new GroupQASpellbookPathViewModel(spellbookTypes, listener, true);
    }

    @Override
    public @DrawableRes int getCardBackground() {
        return R.drawable.witchspells_book_background;
    }

    @Override
    public int getBackgroundTextColor() {
        return android.R.color.white;
    }

    @Override
    public boolean isSelectionMode() {
        return false;
    }

    @Override
    public List<Spell> loadSpells(SpellBusinessService spellBusinessService, SpellFilterManager spellFilterManager) {
        List<Spell> result = new ArrayList<>();
        String mDefSystemLanguage = MainApplication.mDefSystemLanguage;

        for (WitchspellsPath witchPath : witchspells.witchPaths) {
            List<Spell> pathSpells = spellBusinessService.getBookFromIdWithType(witchPath.pathBookId, witchPath.pathLevel, mDefSystemLanguage);

            if (witchPath.secondaryPathBookId > 0) {
                pathSpells.addAll(spellBusinessService.getBookFromIdWithType(witchPath.secondaryPathBookId, witchPath.pathLevel, mDefSystemLanguage));
            }

            Map<Integer, Integer> freeAccessSpellsIds = witchPath.freeAccessSpellsIds;
            if (freeAccessSpellsIds != null && !freeAccessSpellsIds.isEmpty()) {
                List<Spell> freeAccessSpells = spellBusinessService.getBookFromIdWithType(SpellbookType.FREE_ACCESS.bookId, 100, mDefSystemLanguage);
                for (Integer spellPosition : freeAccessSpellsIds.keySet()) {
                    int spellId = freeAccessSpellsIds.get(spellPosition);
                    for (Spell freeAccessSpell : freeAccessSpells) {
                        if (freeAccessSpell.spellId == spellId) {
                            freeAccessSpell.level = spellPosition;
                            freeAccessSpell.freeAccessAssociatedType = pathSpells.get(0).spellbookType;
                            pathSpells.add(freeAccessSpell);
                            break;
                        }
                    }
                }
            }

            Collections.sort(pathSpells);
            result.addAll(pathSpells);
        }

        return spellFilterManager.filterSpells(result);
    }

    @Override
    public void setSpellExpanded(boolean spellExpanded) {
        // Nothing to do here
    }
}