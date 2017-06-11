package fr.enlight.anima.animamagiccards.async;

import android.os.AsyncTask;

import fr.enlight.anima.animamagiccards.MainApplication;
import fr.enlight.anima.cardmodel.business.WitchspellsBusinessService;
import fr.enlight.anima.cardmodel.model.witchspells.Witchspells;


public class SaveWitchspellsAsyncTask extends AsyncTask<Witchspells, Integer, Void> {

    private WitchspellsBusinessService mWitchspellsBusinessService;
    private final boolean mWithRefresh;

    public SaveWitchspellsAsyncTask() {
        mWitchspellsBusinessService = new WitchspellsBusinessService(MainApplication.getMainContext());
        mWithRefresh = true;
    }

    public SaveWitchspellsAsyncTask(boolean withRefresh) {
        mWitchspellsBusinessService = new WitchspellsBusinessService(MainApplication.getMainContext());
        mWithRefresh = withRefresh;
    }

    @Override
    protected Void doInBackground(Witchspells... witchspellsList) {
        for (Witchspells witchspells : witchspellsList) {
            mWitchspellsBusinessService.saveWitchspells(witchspells);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(mWithRefresh){
            mWitchspellsBusinessService.notifyWitchspellsUpdated();
        }
    }
}
