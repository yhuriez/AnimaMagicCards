package fr.enlight.anima.animamagiccards.utils;


import android.content.Context;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.enlight.anima.cardmodel.model.spells.SpellbookType;
import fr.enlight.anima.cardmodel.model.spells.Spellbook;

public class SpellbookUtils {

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
}
