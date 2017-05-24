package fr.enlight.anima.cardmodel.model.witchspells;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

@Entity(tableName = "witchspells")
public class Witchspells implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int witchspellsId;

    public String witchName;

    public long creationDate;

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
        dest.writeTypedList(this.witchPaths);
    }

    public Witchspells() {
    }

    protected Witchspells(Parcel in) {
        this.witchspellsId = in.readInt();
        this.witchName = in.readString();
        this.creationDate = in.readLong();
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
