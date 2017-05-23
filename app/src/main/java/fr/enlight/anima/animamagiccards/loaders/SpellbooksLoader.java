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
public class SpellbooksLoader extends BaseLoader<List<Spellbook>> {

    private SpellBusinessService spellBusinessService;

    public SpellbooksLoader(Context context) {
        super(context);
        spellBusinessService = new SpellBusinessService(context);
    }

    @Override
    public List<Spellbook> loadInBackground() {
        return spellBusinessService.getSpellbooksIndex();
    }
}
