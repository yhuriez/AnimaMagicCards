package fr.enlight.anima.animamagiccards.async;

import android.os.AsyncTask;

import fr.enlight.anima.animamagiccards.MainApplication;
import fr.enlight.anima.cardmodel.business.WitchspellsBusinessService;
import fr.enlight.anima.cardmodel.model.witchspells.Witchspells;


public class SaveWitchspellsAsyncTask extends AsyncTask<Witchspells, Integer, Void> {

    private Listener mListener;

    public SaveWitchspellsAsyncTask(Listener mListener) {
        this.mListener = mListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mListener.onSaveStarted();
    }

    @Override
    protected Void doInBackground(Witchspells... witchspellsList) {
        WitchspellsBusinessService witchspellsBusinessService = new WitchspellsBusinessService(MainApplication.getMainContext());

        for (Witchspells witchspells : witchspellsList) {
            witchspellsBusinessService.saveWitchspells(witchspells);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mListener.onSaveFinished();
        mListener = null;
    }

    public interface Listener{
        void onSaveStarted();
        void onSaveFinished();
    }
}
