package fr.enlight.anima.animamagiccards.async;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import fr.enlight.anima.animamagiccards.ui.spells.stackstategy.SpellStackStrategy;
import fr.enlight.anima.cardmodel.business.SpellFilterFactory;
import fr.enlight.anima.cardmodel.business.SpellFilterManager;
import fr.enlight.anima.cardmodel.model.spells.SpellbookType;
import fr.enlight.anima.cardmodel.business.SpellBusinessService;
import fr.enlight.anima.cardmodel.model.spells.Spell;
import fr.enlight.anima.cardmodel.model.witchspells.Witchspells;
import fr.enlight.anima.cardmodel.model.witchspells.WitchspellsPath;

import static fr.enlight.anima.animamagiccards.MainApplication.mDefSystemLanguage;

/**
 *
 */
public class SpellsLoader extends BaseLoader<List<Spell>> {

    private SpellBusinessService spellBusinessService;
    private final SpellFilterManager spellFilterManager;

    private final SpellStackStrategy spellStackStrategy;

    public SpellsLoader(Context context, SpellStackStrategy spellStackStrategy, SpellFilterManager spellFilterManager) {
        super(context);
        spellBusinessService = new SpellBusinessService(context);
        this.spellStackStrategy = spellStackStrategy;
        this.spellFilterManager = spellFilterManager;
    }

    @Override
    public List<Spell> loadInBackground() {
        return spellStackStrategy.loadSpells(spellBusinessService, spellFilterManager);
    }
}
