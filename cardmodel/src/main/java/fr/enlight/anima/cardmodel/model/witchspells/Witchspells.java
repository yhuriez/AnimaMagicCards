package fr.enlight.anima.cardmodel.model.witchspells;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.enlight.anima.cardmodel.database.ListTypeConverters;

@Entity(tableName = "witchspells")
@TypeConverters({ListTypeConverters.class})
public class Witchspells implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int witchspellsId;

    public String witchName;

    public long creationDate;

    public Map<Integer, List<Integer>> chosenSpells;

    @Ignore
    public List<WitchspellsPath> witchPaths;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.witchspellsId);
        dest.writeString(this.witchName);
        dest.writeLong(this.creationDate);
        dest.writeInt(this.chosenSpells.size());
        for (Map.Entry<Integer, List<Integer>> entry : this.chosenSpells.entrySet()) {
            dest.writeValue(entry.getKey());
            dest.writeList(entry.getValue());
        }
        dest.writeTypedList(this.witchPaths);
    }

    public Witchspells() {
    }

    @SuppressLint("UseSparseArrays")
    protected Witchspells(Parcel in) {
        this.witchspellsId = in.readInt();
        this.witchName = in.readString();
        this.creationDate = in.readLong();
        int chosenSpellsSize = in.readInt();
        this.chosenSpells = new HashMap<>(chosenSpellsSize);
        for (int i = 0; i < chosenSpellsSize; i++) {
            Integer key = (Integer) in.readValue(Integer.class.getClassLoader());
            List<Integer> value = new ArrayList<>();
            in.readList(value, Integer.class.getClassLoader());
            this.chosenSpells.put(key, value);
        }
        this.witchPaths = in.createTypedArrayList(WitchspellsPath.CREATOR);
    }

    public static final Creator<Witchspells> CREATOR = new Creator<Witchspells>() {
        @Override
        public Witchspells createFromParcel(Parcel source) {
            return new Witchspells(source);
        }

        @Override
        public Witchspells[] newArray(int size) {
            return new Witchspells[size];
        }
    };
}
