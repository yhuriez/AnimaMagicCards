package fr.enlight.anima.cardmodel.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import fr.enlight.anima.cardmodel.dao.WitchspellsDao;
import fr.enlight.anima.cardmodel.dao.WitchspellsPathDao;
import fr.enlight.anima.cardmodel.model.witchspells.Witchspells;
import fr.enlight.anima.cardmodel.model.witchspells.WitchspellsPath;

@Database(entities = {Witchspells.class, WitchspellsPath.class}, version = 2, exportSchema = false)
@TypeConverters({ListTypeConverters.class})
public abstract class AppDatabase  extends RoomDatabase{

    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context){
        if(sInstance == null){
            sInstance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "spell_database")
                    .addMigrations(
                            MIGRATION_1_2
                    ).build();
        }
        return sInstance;
    }


    public abstract WitchspellsDao getWitchspellsDao();

    public abstract WitchspellsPathDao getWitchspellsPathDao();


    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE witchspells ADD COLUMN chosenSpells TEXT");
        }
    };

}
