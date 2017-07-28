package fr.enlight.anima.animamagiccards.async;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.enlight.anima.cardmodel.business.SpellBusinessService;
import fr.enlight.anima.cardmodel.model.spells.Spell;
import fr.enlight.anima.cardmodel.model.spells.Spellbook;
import fr.enlight.anima.cardmodel.model.spells.SpellbookType;

import static fr.enlight.anima.animamagiccards.MainApplication.mDefSystemLanguage;

/**
 *
 */
public class SpellbooksIndexLoader extends BaseLoader<SpellbooksIndexLoader.Result> {

    private SpellBusinessService spellBusinessService;

    private final Map<Integer, List<Integer>> chosenSpells;

    public SpellbooksIndexLoader(Context context, Map<Integer, List<Integer>> chosenSpells) {
        super(context);
        spellBusinessService = new SpellBusinessService(context);
        this.chosenSpells = chosenSpells;
    }

    @Override
    public Result loadInBackground() {
        List<Spellbook> spellbooks = spellBusinessService.getSpellbooksIndex(mDefSystemLanguage);

        List<Spell> spells = new ArrayList<>();
        for (Integer spellbookId : chosenSpells.keySet()) {
            List<Spell> spellsForBook = spellBusinessService.getSpellsForBook(spellbookId, mDefSystemLanguage);
            SpellbookType type = SpellbookType.getTypeFromBookId(spellbookId);
            for (Integer spellId : chosenSpells.get(spellbookId)) {
                for (Spell spell : spellsForBook) {
                    if(spell.spellId == spellId){
                        spell.spellbookType = type;
                        spells.add(spell);
                    }
                }
            }
        }

        return new Result(spellbooks, spells);
    }


    public class Result {
        private List<Spellbook> spellbooks;
        private List<Spell> spells;

        public Result(List<Spellbook> spellbooks, List<Spell> spells) {
            this.spellbooks = spellbooks;
            this.spells = spells;
        }

        public List<Spellbook> getSpellbooks() {
            return spellbooks;
        }

        public List<Spell> getSpells() {
            return spells;
        }
    }
}
