package fr.enlight.anima.cardmodel.model.spells;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 *
 */
public class Spell implements Comparable<Spell>, Parcelable {

    public int spellId;
    public int bookId;
    public String name;
    public int level;
    public String actionType;
    public String type;
    public String effect;
    public SpellGrade initialGrade;
    public SpellGrade intermediateGrade;
    public SpellGrade advancedGrade;
    public SpellGrade arcaneGrade;
    public boolean withRetention;
    public boolean dailyRetention;

    public SpellbookType spellbookType;
    public SpellbookType freeAccessAssociatedType;
    public String searchWord;
    public boolean searchInDescription;
    public boolean highlightActionType;
    public boolean highlightType;

    @Override
    public int compareTo(@NonNull Spell other) {
        if(level == other.level){
            return bookId - other.bookId;
        }
        return level - other.level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Spell spell = (Spell) o;

        return spellId == spell.spellId;
    }

    @Override
    public int hashCode() {
        return spellId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.spellId);
        dest.writeInt(this.bookId);
        dest.writeString(this.name);
        dest.writeInt(this.level);
        dest.writeString(this.actionType);
        dest.writeString(this.type);
        dest.writeString(this.effect);
        dest.writeParcelable(this.initialGrade, flags);
        dest.writeParcelable(this.intermediateGrade, flags);
        dest.writeParcelable(this.advancedGrade, flags);
        dest.writeParcelable(this.arcaneGrade, flags);
        dest.writeByte(this.withRetention ? (byte) 1 : (byte) 0);
        dest.writeByte(this.dailyRetention ? (byte) 1 : (byte) 0);
        dest.writeInt(this.spellbookType == null ? -1 : this.spellbookType.ordinal());
        dest.writeString(this.searchWord);
        dest.writeByte(this.searchInDescription ? (byte) 1 : (byte) 0);
        dest.writeByte(this.highlightActionType ? (byte) 1 : (byte) 0);
        dest.writeByte(this.highlightType ? (byte) 1 : (byte) 0);
    }

    public Spell() {
    }

    protected Spell(Parcel in) {
        this.spellId = in.readInt();
        this.bookId = in.readInt();
        this.name = in.readString();
        this.level = in.readInt();
        this.actionType = in.readString();
        this.type = in.readString();
        this.effect = in.readString();
        this.initialGrade = in.readParcelable(SpellGrade.class.getClassLoader());
        this.intermediateGrade = in.readParcelable(SpellGrade.class.getClassLoader());
        this.advancedGrade = in.readParcelable(SpellGrade.class.getClassLoader());
        this.arcaneGrade = in.readParcelable(SpellGrade.class.getClassLoader());
        this.withRetention = in.readByte() != 0;
        this.dailyRetention = in.readByte() != 0;
        int tmpSpellbookType = in.readInt();
        this.spellbookType = tmpSpellbookType == -1 ? null : SpellbookType.values()[tmpSpellbookType];
        this.searchWord = in.readString();
        this.searchInDescription = in.readByte() != 0;
        this.highlightActionType = in.readByte() != 0;
        this.highlightType = in.readByte() != 0;
    }

    public static final Creator<Spell> CREATOR = new Creator<Spell>() {
        @Override
        public Spell createFromParcel(Parcel source) {
            return new Spell(source);
        }

        @Override
        public Spell[] newArray(int size) {
            return new Spell[size];
        }
    };
}
