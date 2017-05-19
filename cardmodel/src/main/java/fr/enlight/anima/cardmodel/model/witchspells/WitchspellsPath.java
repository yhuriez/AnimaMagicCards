package fr.enlight.anima.cardmodel.model.witchspells;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;


public class WitchspellsPath implements Parcelable {

    public int pathBookId;
    public int secondaryPathBookId;
    public int pathLevel;
    public List<Integer> freeAccessSpellsIds;
    public List<Integer> choosenSpellsIds;

    public WitchspellsPath() {
        pathBookId = -1;
        secondaryPathBookId = -1;
        pathLevel = -1;
        freeAccessSpellsIds = new ArrayList<>();
        choosenSpellsIds = new ArrayList<>();
    }

    protected WitchspellsPath(Parcel in) {
        pathBookId = in.readInt();
        secondaryPathBookId = in.readInt();
        pathLevel = in.readInt();
        freeAccessSpellsIds = new ArrayList<>();
        in.readList(freeAccessSpellsIds, Integer.class.getClassLoader());
        choosenSpellsIds = new ArrayList<>();
        in.readList(choosenSpellsIds, Integer.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(pathBookId);
        parcel.writeInt(secondaryPathBookId);
        parcel.writeInt(pathLevel);
        parcel.writeList(freeAccessSpellsIds);
        parcel.writeList(choosenSpellsIds);
    }

    public static final Creator<WitchspellsPath> CREATOR = new Creator<WitchspellsPath>() {
        @Override
        public WitchspellsPath createFromParcel(Parcel in) {
            return new WitchspellsPath(in);
        }

        @Override
        public WitchspellsPath[] newArray(int size) {
            return new WitchspellsPath[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }


}
