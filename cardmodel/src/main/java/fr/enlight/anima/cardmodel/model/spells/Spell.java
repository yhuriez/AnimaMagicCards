package fr.enlight.anima.cardmodel.model.spells;

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

    public SpellbookType spellbookType;
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
}
