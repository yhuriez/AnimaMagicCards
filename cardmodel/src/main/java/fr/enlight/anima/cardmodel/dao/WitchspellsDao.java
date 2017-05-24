package fr.enlight.anima.cardmodel.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.enlight.anima.cardmodel.model.witchspells.Witchspells;

@Dao
public interface WitchspellsDao {

    @Query("SELECT * FROM witchspells ORDER BY creationDate DESC")
    List<Witchspells> getWitchspells();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertWitchspells(Witchspells witchspells);

    @Update
    void updateWitchspells(Witchspells witchspells);

    @Delete
    void deleteWitchspells(Witchspells witchspells);
}
