package fr.enlight.anima.animamagiccards.async;

import android.os.AsyncTask;

import fr.enlight.anima.animamagiccards.MainApplication;
import fr.enlight.anima.cardmodel.business.WitchspellsBusinessService;
import fr.enlight.anima.cardmodel.model.witchspells.Witchspells;


public class DeleteWitchspellsAsyncTask extends AsyncTask<Witchspells, Integer, Void> {

    private Listener mListener;

    public DeleteWitchspellsAsyncTask(Listener mListener) {
        this.mListener = mListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mListener.onDeleteStarted();
    }

    @Override
    protected Void doInBackground(Witchspells... witchspellsList) {
        WitchspellsBusinessService witchspellsBusinessService = new WitchspellsBusinessService(MainApplication.getMainContext());

        for (Witchspells witchspells : witchspellsList) {
            witchspellsBusinessService.deleteWitchspells(witchspells);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mListener.onDeleteFinished();
        mListener = null;
    }

    public interface Listener{
        void onDeleteStarted();
        void onDeleteFinished();
    }
}
