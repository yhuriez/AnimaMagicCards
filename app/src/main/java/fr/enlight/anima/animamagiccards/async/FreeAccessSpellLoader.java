package fr.enlight.anima.animamagiccards.async;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import fr.enlight.anima.animamagiccards.MainApplication;
import fr.enlight.anima.cardmodel.business.SpellBusinessService;
import fr.enlight.anima.cardmodel.model.spells.Spell;
import fr.enlight.anima.cardmodel.model.spells.SpellbookType;

import static fr.enlight.anima.animamagiccards.MainApplication.mDefSystemLanguage;

/**
 *
 */
public class FreeAccessSpellLoader extends BaseLoader<Map<Integer, Spell>> {

    private SpellBusinessService spellBusinessService;

    private final Map<Integer, Integer> freeAccessSpellsIds;

    public FreeAccessSpellLoader(Context context, Map<Integer, Integer> freeAccessSpellsIds) {
        super(context);
        spellBusinessService = new SpellBusinessService(context);
        this.freeAccessSpellsIds = freeAccessSpellsIds;
    }

    @Override
    public Map<Integer, Spell> loadInBackground() {
        Map<Integer, Spell> result = new TreeMap<>();

        List<Spell> pathSpells = getBookFromIdWithType(SpellbookType.FREE_ACCESS.bookId, 100);

        for (Integer spellPosition : freeAccessSpellsIds.keySet()) {
            int spellId = freeAccessSpellsIds.get(spellPosition);
            if(spellId > 0){
                for (Spell pathSpell : pathSpells) {
                    if (pathSpell.spellId == spellId){
                        result.put(spellPosition, pathSpell);
                        break;
                    }
                }
            }

            if(!result.containsKey(spellPosition)){
                result.put(spellPosition, null);
            }
        }

        return result;
    }

    private List<Spell> getBookFromIdWithType(int bookId, int levelMax) {
        List<Spell> result = new ArrayList<>();
        SpellbookType typeFromBookId = SpellbookType.getTypeFromBookId(bookId);
        List<Spell> spellsForBook = spellBusinessService.getSpellsForBook(bookId, mDefSystemLanguage);
        for (Spell spell : spellsForBook) {
            if (spell.level > levelMax) {
                break;
            }
            spell.spellbookType = typeFromBookId;
            result.add(spell);
        }
        return result;
    }
}
