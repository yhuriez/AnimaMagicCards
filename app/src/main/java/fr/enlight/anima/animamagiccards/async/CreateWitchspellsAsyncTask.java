package fr.enlight.anima.animamagiccards.async;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.HashMap;

import fr.enlight.anima.animamagiccards.MainApplication;
import fr.enlight.anima.cardmodel.business.WitchspellsBusinessService;
import fr.enlight.anima.cardmodel.model.witchspells.Witchspells;


public class CreateWitchspellsAsyncTask extends AsyncTask<String, Integer, Witchspells> {

    private Listener mListener;
    private WitchspellsBusinessService mWitchspellsBusinessService;

    public CreateWitchspellsAsyncTask(Listener listener) {
        mListener = listener;
        this.mWitchspellsBusinessService = new WitchspellsBusinessService(MainApplication.getMainContext());
    }

    @SuppressLint("UseSparseArrays")
    @Override
    protected Witchspells doInBackground(String... witchspellsNameArray) {
        Witchspells witchspellsResult = new Witchspells();
        witchspellsResult.witchName = witchspellsNameArray[0];
        witchspellsResult.creationDate = System.currentTimeMillis();
        witchspellsResult.witchPaths = new ArrayList<>();
        witchspellsResult.chosenSpells = new HashMap<>();

        witchspellsResult = mWitchspellsBusinessService.saveWitchspells(witchspellsResult);
        return witchspellsResult;
    }

    @Override
    protected void onPostExecute(Witchspells witchspells) {
        mListener.onCreatedWitchspells(witchspells);
        mListener = null;
    }

    public interface Listener{
        void onCreatedWitchspells(Witchspells witchspells);
    }
}
