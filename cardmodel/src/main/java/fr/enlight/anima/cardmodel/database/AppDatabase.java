package fr.enlight.anima.cardmodel.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import fr.enlight.anima.cardmodel.dao.WitchspellsDao;
import fr.enlight.anima.cardmodel.dao.WitchspellsPathDao;
import fr.enlight.anima.cardmodel.model.witchspells.Witchspells;
import fr.enlight.anima.cardmodel.model.witchspells.WitchspellsPath;

@Database(entities = {Witchspells.class, WitchspellsPath.class}, version = 1)
@TypeConverters({ListTypeConverters.class})
public abstract class AppDatabase  extends RoomDatabase{

    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context){
        if(sInstance == null){
            sInstance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "spell_database").build();
        }
        return sInstance;
    }


    public abstract WitchspellsDao getWitchspellsDao();

    public abstract WitchspellsPathDao getWitchspellsPathDao();
}
