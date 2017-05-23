package fr.enlight.anima.animamagiccards.loaders;

import android.content.Context;

import java.util.List;

import fr.enlight.anima.cardmodel.business.SpellBusinessService;
import fr.enlight.anima.cardmodel.business.WitchspellsBusinessService;
import fr.enlight.anima.cardmodel.model.spells.Spellbook;
import fr.enlight.anima.cardmodel.model.witchspells.Witchspells;

/**
 *
 */
public class SpellbooksWitchspellsLoader extends BaseLoader<SpellbooksWitchspellsLoader.LoaderResult> {

    private SpellBusinessService spellBusinessService;
    private WitchspellsBusinessService witchspellsBusinessService;

    public SpellbooksWitchspellsLoader(Context context) {
        super(context);
        spellBusinessService = new SpellBusinessService(context);
        witchspellsBusinessService = new WitchspellsBusinessService(context);
    }

    @Override
    public LoaderResult loadInBackground() {
        List<Witchspells> allWitchspells = witchspellsBusinessService.getAllWitchspells();
        List<Spellbook> spellbooksIndex = spellBusinessService.getSpellbooksIndex();
        return new LoaderResult(allWitchspells, spellbooksIndex);
    }


    public static class LoaderResult{
        public final List<Witchspells> witchspells;
        public final List<Spellbook> spellbooks;

        public LoaderResult(List<Witchspells> witchspells, List<Spellbook> spellbooks) {
            this.witchspells = witchspells;
            this.spellbooks = spellbooks;
        }
    }
}
