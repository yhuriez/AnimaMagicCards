package fr.enlight.spellgenerator.spells;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 *
 */
public class Spell implements Comparable<Spell> {

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

        return spellId == spell.spellId && bookId == spell.bookId;
    }

    @Override
    public int hashCode() {
        int result = spellId;
        result = 51 * result + bookId;
        return result;
    }
}
