package fr.enlight.spellgenerator.spells;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 */
public class SpellGrade implements Parcelable {

    public int zeon;
    public int requiredIntelligence;
    public String effect;
    public int retention;

    public boolean limitedIntelligence;
    public boolean limitedZeon;
    public boolean limitedRetention;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.zeon);
        dest.writeInt(this.requiredIntelligence);
        dest.writeString(this.effect);
        dest.writeInt(this.retention);
        dest.writeByte(this.limitedIntelligence ? (byte) 1 : (byte) 0);
        dest.writeByte(this.limitedZeon ? (byte) 1 : (byte) 0);
        dest.writeByte(this.limitedRetention ? (byte) 1 : (byte) 0);
    }

    public SpellGrade() {
    }

    protected SpellGrade(Parcel in) {
        this.zeon = in.readInt();
        this.requiredIntelligence = in.readInt();
        this.effect = in.readString();
        this.retention = in.readInt();
        this.limitedIntelligence = in.readByte() != 0;
        this.limitedZeon = in.readByte() != 0;
        this.limitedRetention = in.readByte() != 0;
    }

    public static final Creator<SpellGrade> CREATOR = new Creator<SpellGrade>() {
        @Override
        public SpellGrade createFromParcel(Parcel source) {
            return new SpellGrade(source);
        }

        @Override
        public SpellGrade[] newArray(int size) {
            return new SpellGrade[size];
        }
    };
}
