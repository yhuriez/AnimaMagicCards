package fr.enlight.anima.animamagiccards.async;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fr.enlight.anima.cardmodel.business.WitchspellsBusinessService;
import fr.enlight.anima.cardmodel.model.spells.SpellbookType;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.animamagiccards.ui.spellbooks.viewmodels.SpellViewModel;
import fr.enlight.anima.cardmodel.business.SpellBusinessService;
import fr.enlight.anima.cardmodel.model.spells.Spell;
import fr.enlight.anima.cardmodel.model.witchspells.Witchspells;
import fr.enlight.anima.cardmodel.model.witchspells.WitchspellsPath;

/**
 *
 */
public class SpellsLoader extends BaseLoader<List<Spell>> {

    private SpellBusinessService spellBusinessService;

    private final Witchspells witchspells;
    private final int bookId;

    public SpellsLoader(Context context, int bookId) {
        super(context);
        spellBusinessService = new SpellBusinessService(context);
        this.bookId = bookId;
        this.witchspells = null;

    }

    public SpellsLoader(Context context, Witchspells witchspells) {
        super(context);
        spellBusinessService = new SpellBusinessService(context);
        this.witchspells = witchspells;
        this.bookId = -1;
    }

    @Override
    public List<Spell> loadInBackground() {
        if(bookId >= 0){
            return getBookFromIdWithType(bookId, 100);
        }

        if(witchspells != null){
            List<Spell> result = new ArrayList<>();
            for (WitchspellsPath witchPath : witchspells.witchPaths) {
                List<Spell> pathSpells = getBookFromIdWithType(witchPath.pathBookId, witchPath.pathLevel);

                if(witchPath.secondaryPathBookId > 0){
                    pathSpells.addAll(getBookFromIdWithType(witchPath.secondaryPathBookId, witchPath.pathLevel));
                }

                Collections.sort(pathSpells);
                result.addAll(pathSpells);
            }
            return result;
        }

        return Collections.emptyList();
    }

    private List<Spell> getBookFromIdWithType(int bookId, int levelMax){
        List<Spell> result = new ArrayList<>();
        SpellbookType typeFromBookId = SpellbookType.getTypeFromBookId(bookId);
        for (Spell spell : spellBusinessService.getSpellsForBook(bookId)) {
            spell.spellbookType = typeFromBookId;
            result.add(spell);
            if(spell.level >= levelMax){
                break;
            }
        }
        return result;
    }

}
