package fr.enlight.anima.cardmodel.model.witchspells;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

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

    public List<Integer> freeAccessSpellsIds;
    public List<Integer> choosenSpellsIds;


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
        dest.writeList(this.freeAccessSpellsIds);
        dest.writeList(this.choosenSpellsIds);
    }

    public WitchspellsPath() {
    }

    protected WitchspellsPath(Parcel in) {
        this.pathId = in.readInt();
        this.parentPathId = in.readInt();
        this.pathBookId = in.readInt();
        this.secondaryPathBookId = in.readInt();
        this.pathLevel = in.readInt();
        this.freeAccessSpellsIds = new ArrayList<Integer>();
        in.readList(this.freeAccessSpellsIds, Integer.class.getClassLoader());
        this.choosenSpellsIds = new ArrayList<Integer>();
        in.readList(this.choosenSpellsIds, Integer.class.getClassLoader());
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
