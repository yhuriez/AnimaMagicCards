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


    public SpellbooksIndexLoader(Context context) {
        super(context);
        spellBusinessService = new SpellBusinessService(context);
    }

    @Override
    public Result loadInBackground() {
        List<Spellbook> spellbooks = spellBusinessService.getSpellbooksIndex(mDefSystemLanguage);



        return new Result(spellbooks);
    }


    public class Result {
        private List<Spellbook> spellbooks;

        public Result(List<Spellbook> spellbooks) {
            this.spellbooks = spellbooks;
        }

        public List<Spellbook> getSpellbooks() {
            return spellbooks;
        }
    }
}
