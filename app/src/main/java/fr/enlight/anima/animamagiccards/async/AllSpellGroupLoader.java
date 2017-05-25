package fr.enlight.anima.animamagiccards.async;

import android.content.Context;

import java.util.List;

import fr.enlight.anima.cardmodel.business.SpellBusinessService;
import fr.enlight.anima.cardmodel.business.WitchspellsBusinessService;
import fr.enlight.anima.cardmodel.model.spells.Spellbook;
import fr.enlight.anima.cardmodel.model.witchspells.Witchspells;

/**
 *
 */
public class AllSpellGroupLoader extends BaseLoader<AllSpellGroupLoader.LoaderResult> {

    public AllSpellGroupLoader(Context context) {
        super(context);
    }

    @Override
    public LoaderResult loadInBackground() {
        WitchspellsBusinessService witchspellsBusinessService = new WitchspellsBusinessService(getContext());

        List<Witchspells> allWitchspells = witchspellsBusinessService.getAllWitchspells();

        SpellBusinessService spellBusinessService = new SpellBusinessService(getContext());
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
