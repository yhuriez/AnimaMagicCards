package fr.enlight.anima.animamagiccards.async;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import fr.enlight.anima.cardmodel.business.SpellBusinessService;
import fr.enlight.anima.cardmodel.business.SpellFilterFactory;
import fr.enlight.anima.cardmodel.model.spells.Spell;
import fr.enlight.anima.cardmodel.model.spells.SpellbookType;
import fr.enlight.anima.cardmodel.model.witchspells.Witchspells;
import fr.enlight.anima.cardmodel.model.witchspells.WitchspellsPath;

/**
 *
 */
public class FreeAccessSpellLoader extends BaseLoader<Map<Integer, Spell>> {

    private SpellBusinessService spellBusinessService;

    private final WitchspellsPath witchPath;

    public FreeAccessSpellLoader(Context context, WitchspellsPath witchspellsPath) {
        super(context);
        spellBusinessService = new SpellBusinessService(context);
        this.witchPath = witchspellsPath;
    }

    @Override
    public Map<Integer, Spell> loadInBackground() {
        Map<Integer, Spell> result = new TreeMap<>();

        List<Spell> pathSpells = getBookFromIdWithType(SpellbookType.FREE_ACCESS.bookId, witchPath.pathLevel);

        for (Integer spellPosition : witchPath.freeAccessSpellsIds.keySet()) {
            int spellId = witchPath.freeAccessSpellsIds.get(spellPosition);
            if(spellId > 0){
                for (Spell pathSpell : pathSpells) {
                    if (pathSpell.spellId == spellId){
                        result.put(spellId, pathSpell);
                    }
                }
            }
        }

        return result;
    }

    private List<Spell> getBookFromIdWithType(int bookId, int levelMax) {
        List<Spell> result = new ArrayList<>();
        SpellbookType typeFromBookId = SpellbookType.getTypeFromBookId(bookId);
        for (Spell spell : spellBusinessService.getSpellsForBook(bookId)) {
            spell.spellbookType = typeFromBookId;
            result.add(spell);
            if (spell.level >= levelMax) {
                break;
            }
        }
        return result;
    }

}
