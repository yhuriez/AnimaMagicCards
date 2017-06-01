package fr.enlight.anima.cardmodel.model.witchspells;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

import fr.enlight.anima.cardmodel.database.ListTypeConverters;


@Entity(tableName = "witchpath")
@TypeConverters({ListTypeConverters.class})
public class WitchspellsPath implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int pathId;

    public int parentPathId;

    public int pathBookId;
    public int secondaryPathBookId;
    public int pathLevel;

    public Map<Integer, Integer> freeAccessSpellsIds;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.pathId);
        dest.writeInt(this.parentPathId);
        dest.writeInt(this.pathBookId);
        dest.writeInt(this.secondaryPathBookId);
        dest.writeInt(this.pathLevel);
        dest.writeInt(this.freeAccessSpellsIds.size());
        for (Map.Entry<Integer, Integer> entry : this.freeAccessSpellsIds.entrySet()) {
            dest.writeValue(entry.getKey());
            dest.writeValue(entry.getValue());
        }
    }

    public WitchspellsPath() {
    }

    @SuppressLint("UseSparseArrays")
    protected WitchspellsPath(Parcel in) {
        this.pathId = in.readInt();
        this.parentPathId = in.readInt();
        this.pathBookId = in.readInt();
        this.secondaryPathBookId = in.readInt();
        this.pathLevel = in.readInt();
        int freeAccessSpellsIdsSize = in.readInt();
        this.freeAccessSpellsIds = new HashMap<>(freeAccessSpellsIdsSize);
        for (int i = 0; i < freeAccessSpellsIdsSize; i++) {
            Integer key = (Integer) in.readValue(Integer.class.getClassLoader());
            Integer value = (Integer) in.readValue(Integer.class.getClassLoader());
            this.freeAccessSpellsIds.put(key, value);
        }
    }

    public static final Creator<WitchspellsPath> CREATOR = new Creator<WitchspellsPath>() {
        @Override
        public WitchspellsPath createFromParcel(Parcel source) {
            return new WitchspellsPath(source);
        }

        @Override
        public WitchspellsPath[] newArray(int size) {
            return new WitchspellsPath[size];
        }
    };
}
