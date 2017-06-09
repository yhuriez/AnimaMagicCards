package fr.enlight.anima.animamagiccards.async;

import android.os.AsyncTask;

import fr.enlight.anima.animamagiccards.MainApplication;
import fr.enlight.anima.cardmodel.business.WitchspellsBusinessService;
import fr.enlight.anima.cardmodel.model.witchspells.Witchspells;


public class DeleteWitchspellsAsyncTask extends AsyncTask<Witchspells, Integer, Void> {

    private WitchspellsBusinessService mWitchspellsBusinessService;

    public DeleteWitchspellsAsyncTask() {
        this.mWitchspellsBusinessService = new WitchspellsBusinessService(MainApplication.getMainContext());
    }

    @Override
    protected Void doInBackground(Witchspells... witchspellsList) {
        for (Witchspells witchspells : witchspellsList) {
            mWitchspellsBusinessService.deleteWitchspells(witchspells);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        mWitchspellsBusinessService.notifyWitchspellsUpdated();
    }
}
