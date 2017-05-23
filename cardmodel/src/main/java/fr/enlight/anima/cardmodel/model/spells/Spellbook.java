package fr.enlight.anima.cardmodel.model.spells;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 *
 */
public class Spellbook implements Parcelable{

    public int bookId;
    public String bookName;
    public String description;
    public String type;
    public String oppositeBook;
    public List<String> secondaryBookAccessibles;
    public List<String> primaryBookUnaccessibles;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.bookId);
        dest.writeString(this.bookName);
        dest.writeString(this.description);
        dest.writeString(this.type);
        dest.writeString(this.oppositeBook);
        dest.writeStringList(this.secondaryBookAccessibles);
        dest.writeStringList(this.primaryBookUnaccessibles);
    }

    public Spellbook() {
    }

    protected Spellbook(Parcel in) {
        this.bookId = in.readInt();
        this.bookName = in.readString();
        this.description = in.readString();
        this.type = in.readString();
        this.oppositeBook = in.readString();
        this.secondaryBookAccessibles = in.createStringArrayList();
        this.primaryBookUnaccessibles = in.createStringArrayList();
    }

    public static final Creator<Spellbook> CREATOR = new Creator<Spellbook>() {
        @Override
        public Spellbook createFromParcel(Parcel source) {
            return new Spellbook(source);
        }

        @Override
        public Spellbook[] newArray(int size) {
            return new Spellbook[size];
        }
    };
}
