package fr.enlight.anima.cardmodel.utils;


import android.content.Context;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

import fr.enlight.anima.cardmodel.model.spells.Spell;
import fr.enlight.anima.cardmodel.model.spells.SpellGrade;
import fr.enlight.anima.cardmodel.model.spells.Spellbook;
import fr.enlight.anima.cardmodel.model.spells.SpellbookType;

public class SpellUtils {

    public static ArrayList<Spellbook> extractSecondarySpellbooks(Context context, Spellbook spellbook, SparseArray<Spellbook> spellbooksMapping) {
        ArrayList<Spellbook> result = new ArrayList<>();

        for (SpellbookType secondarySpellbook : SpellbookType.SECONDARY_SPELLBOOKS) {
            for (String secondaryBookName : spellbook.secondaryBookAccessibles) {
                String bookName = context.getString(secondarySpellbook.titleRes);
                if (secondaryBookName.equalsIgnoreCase(bookName)) {
                    result.add(spellbooksMapping.get(secondarySpellbook.bookId));
                }
            }
        }

        return result;
    }

    public static List<SpellGrade> extractGrades(Spell spell){
        List<SpellGrade> result = new ArrayList<>();
        result.add(spell.initialGrade);
        result.add(spell.intermediateGrade);
        result.add(spell.advancedGrade);
        result.add(spell.arcaneGrade);
        return result;
    }
}
