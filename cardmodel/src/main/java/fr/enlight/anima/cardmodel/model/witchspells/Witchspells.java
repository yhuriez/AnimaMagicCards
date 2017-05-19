package fr.enlight.anima.cardmodel.model.witchspells;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Witchspells implements Parcelable {

    public int witchspellsId;
    public String witchName;
    public Date creationDate;

    public List<WitchspellsPath> witchPaths;


    public Witchspells() {
        witchspellsId = -1;
        witchName = null;
        creationDate = new Date();
        witchPaths = new ArrayList<>();
    }

    protected Witchspells(Parcel in) {
        witchspellsId = in.readInt();
        witchName = in.readString();
        creationDate = (Date) in.readSerializable();
        witchPaths = new ArrayList<>();
        in.readList(witchPaths, WitchspellsPath.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(witchspellsId);
        parcel.writeString(witchName);
        parcel.writeSerializable(creationDate);
        parcel.writeList(witchPaths);
    }

    public static final Creator<Witchspells> CREATOR = new Creator<Witchspells>() {
        @Override
        public Witchspells createFromParcel(Parcel in) {
            return new Witchspells(in);
        }

        @Override
        public Witchspells[] newArray(int size) {
            return new Witchspells[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }


}
