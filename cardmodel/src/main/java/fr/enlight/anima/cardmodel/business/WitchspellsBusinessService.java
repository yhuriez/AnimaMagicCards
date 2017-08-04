package fr.enlight.anima.cardmodel.business;


import android.content.Context;
import android.support.annotation.WorkerThread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import fr.enlight.anima.cardmodel.dao.WitchspellsDao;
import fr.enlight.anima.cardmodel.dao.WitchspellsPathDao;
import fr.enlight.anima.cardmodel.database.AppDatabase;
import fr.enlight.anima.cardmodel.model.spells.Spell;
import fr.enlight.anima.cardmodel.model.witchspells.Witchspells;
import fr.enlight.anima.cardmodel.model.witchspells.WitchspellsPath;

public class WitchspellsBusinessService {

    private Context context;

    private WitchspellsDao witchspellsDao;
    private WitchspellsPathDao witchspellsPathDao;

    private static final List<WitchspellsUpdateListener> sWitchspellsListeners = new ArrayList<>();


    public WitchspellsBusinessService(Context context) {
        this.context = context;
    }

    @WorkerThread
    public Witchspells saveWitchspells(Witchspells witchspells){
        int witchspellsId = witchspells.witchspellsId;
        if(witchspellsId > 0){
            getWitchspellsDao().updateWitchspells(witchspells);
        } else {
            witchspellsId = (int) getWitchspellsDao().insertWitchspells(witchspells);
            witchspells.witchspellsId = witchspellsId;
        }

        WitchspellsPathDao witchspellsPathDao = getWitchspellsPathDao();

        witchspellsPathDao.deleteWitchspellsPaths(witchspellsId);

        for (WitchspellsPath witchPath : witchspells.witchPaths) {
            witchPath.parentPathId = witchspellsId;
            witchspellsPathDao.insertWitchspellsPath(witchPath);
        }
        return witchspells;
    }

    @WorkerThread
    public List<Witchspells> getAllWitchspells(String locale){
        List<Witchspells> witchspellsList = getWitchspellsDao().getWitchspells();

        WitchspellsPathDao witchspellsPathDao = getWitchspellsPathDao();
        SpellBusinessService spellBusinessService = new SpellBusinessService(context);

        for (Witchspells witchspells : witchspellsList) {
            witchspells.witchPaths = witchspellsPathDao.getWitchspellsPath(witchspells.witchspellsId);
            completeWitchspellsWithChosenSpells(witchspells, spellBusinessService, locale);
        }

        return witchspellsList;
    }

    public void completeWitchspellsWithChosenSpells(Witchspells witchspells, SpellBusinessService spellBusinessService, String locale){
        if(witchspells.chosenSpells != null && !witchspells.chosenSpells.isEmpty()){
            TreeMap<Integer, List<Spell>> result = new TreeMap<>();
            for (Integer spellbookId : witchspells.chosenSpells.keySet()) {
                // Init spell list
                List<Spell> spells = new ArrayList<>();
                result.put(spellbookId, spells);

                // Init related spellbook
                List<Spell> spellsForBook = spellBusinessService.getSpellsForBook(spellbookId, locale);

                List<Integer> spellIdList = witchspells.chosenSpells.get(spellbookId);
                Collections.sort(spellIdList);

                for (Spell spell : spellsForBook) {
                    for (Integer spellId : spellIdList) {
                        if(spellId == spell.spellId){
                            spells.add(spell);
                            break;
                        }
                    }
                }
            }

            witchspells.chosenSpellsInstantiated = result;
        }

    }

    @WorkerThread
    public void deleteWitchspells(Witchspells witchspells) {
        getWitchspellsPathDao().deleteWitchspellsPaths(witchspells.witchspellsId);
        getWitchspellsDao().deleteWitchspells(witchspells);
    }

    public WitchspellsDao getWitchspellsDao(){
        if(witchspellsDao == null){
            witchspellsDao = AppDatabase.getInstance(context).getWitchspellsDao();
        }
        return witchspellsDao;
    }

    public WitchspellsPathDao getWitchspellsPathDao(){
        if(witchspellsPathDao == null){
            witchspellsPathDao = AppDatabase.getInstance(context).getWitchspellsPathDao();
        }
        return witchspellsPathDao;
    }

    public void notifyWitchspellsUpdated(){
        for (WitchspellsUpdateListener listener : sWitchspellsListeners) {
            listener.onWitchspellsUpdated();
        }
    }

    public static void addWitchspellsListener(WitchspellsUpdateListener witchspellsUpdateListener){
        sWitchspellsListeners.add(witchspellsUpdateListener);
    }

    public static void removeWitchspellsListener(WitchspellsUpdateListener witchspellsUpdateListener){
        sWitchspellsListeners.remove(witchspellsUpdateListener);
    }
}
