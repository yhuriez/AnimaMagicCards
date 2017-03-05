package fr.enlight.anima.cardmodel.dao;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.enlight.anima.cardmodel.model.witchspells.Witchspells;

public class WitchspellsDao {

    private static final String WITCHSPELLS_SHARED_PREFS = "WITCHSPELLS_SHARED_PREFS";

    private static final String WITCHSPELLS_PARAM_PREFIX = "WITCHSPELLS_PARAM_";

    private final SharedPreferences mSharedPreferences;
    private Gson gson;

    public WitchspellsDao(Context context) {
        mSharedPreferences = context.getSharedPreferences(WITCHSPELLS_SHARED_PREFS, Context.MODE_PRIVATE);
    }

    public void addWitchspells(Witchspells witchspells){
        String key = WITCHSPELLS_PARAM_PREFIX + witchspells.witchspellsId;
        String jsonWitchspells = getGson().toJson(witchspells);
        mSharedPreferences.edit().putString(key, jsonWitchspells).apply();
    }

    public List<Witchspells> getAllWitchspells(){
        Map<String, ?> all = mSharedPreferences.getAll();
        List<Witchspells> result = new ArrayList<>();
        for (Object object : all.values()) {
            String jsonWitchspells = (String) object;
            Witchspells witchspells = getGson().fromJson(jsonWitchspells, Witchspells.class);
            result.add(witchspells);
        }
        return result;
    }

    public void removeWitchspells(int witchspellsId){
        String key = WITCHSPELLS_PARAM_PREFIX + witchspellsId;
        mSharedPreferences.edit().remove(key).apply();
    }

    private Gson getGson(){
        if(gson == null){
            gson = new Gson();
        }
        return gson;
    }
}
