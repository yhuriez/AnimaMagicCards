package fr.enlight.anima.cardmodel.business;


import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fr.enlight.anima.cardmodel.dao.WitchspellsDao;
import fr.enlight.anima.cardmodel.model.spells.Spell;
import fr.enlight.anima.cardmodel.model.witchspells.Witchspells;
import fr.enlight.anima.cardmodel.model.witchspells.WitchspellsPath;

public class WitchspellsBusinessService {

    private SpellBusinessService mSpellBusinessService;
    private WitchspellsDao mWitchspellsDao;
    private Context context;

    public WitchspellsBusinessService(Context context) {
        this.context = context;
    }

    public void saveWitchspells(Witchspells witchspells){
        getWitchspellsDao().addWitchspells(witchspells);
    }

    public void deleteWitchspells(Witchspells witchspells){
        getWitchspellsDao().removeWitchspells(witchspells.witchspellsId);
    }

    public List<Witchspells> getAllWitchspells(){
        List<Witchspells> allWitchspells = getWitchspellsDao().getAllWitchspells();
        Collections.sort(allWitchspells, new Comparator<Witchspells>() {
            @Override
            public int compare(Witchspells first, Witchspells second) {
                return first.creationDate.compareTo(second.creationDate);
            }
        });

        return allWitchspells;
    }

    /**
     * Build a list of spells using the Witchspells data. Witchspells object contains indications of
     * which spellbooks are used by the witch and which secondary book or free access spells are choosen
     * for each of them.
     */
    public List<Spell> buildSpellsFromWitchspells(Witchspells witchspells){
        List<Spell> result = new ArrayList<>();

        SpellBusinessService spellBusinessService = getSpellBusinessService();

        for (WitchspellsPath witchPath : witchspells.witchPaths) {
            List<Spell> spellsForBook = spellBusinessService.getSpellsForBook(witchPath.pathBookId);
            for (int index = 0; index < witchPath.pathLevel / 2; index++) {
                // TODO
            }
        }

        return result;
    }

    private WitchspellsDao getWitchspellsDao(){
        if(mWitchspellsDao == null){
            mWitchspellsDao = new WitchspellsDao(context);
        }
        return mWitchspellsDao;
    }

    private SpellBusinessService getSpellBusinessService(){
        if(mSpellBusinessService == null){
            mSpellBusinessService = new SpellBusinessService(context);
        }
        return mSpellBusinessService;
    }
}
