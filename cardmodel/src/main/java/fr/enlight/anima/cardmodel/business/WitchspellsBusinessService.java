package fr.enlight.anima.cardmodel.business;


import android.content.Context;
import android.support.annotation.WorkerThread;

import java.util.ArrayList;
import java.util.List;

import fr.enlight.anima.cardmodel.dao.WitchspellsDao;
import fr.enlight.anima.cardmodel.dao.WitchspellsPathDao;
import fr.enlight.anima.cardmodel.database.AppDatabase;
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
    public List<Witchspells> getAllWitchspells(){
        List<Witchspells> witchspellsList = getWitchspellsDao().getWitchspells();

        WitchspellsPathDao witchspellsPathDao = getWitchspellsPathDao();
        for (Witchspells witchspells : witchspellsList) {
            witchspells.witchPaths = witchspellsPathDao.getWitchspellsPath(witchspells.witchspellsId);
        }

        return witchspellsList;
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
