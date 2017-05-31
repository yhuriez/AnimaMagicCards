package fr.enlight.anima.cardmodel.utils;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Pair;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import fr.enlight.anima.cardmodel.model.spells.Spell;
import fr.enlight.anima.cardmodel.model.spells.SpellGrade;
import fr.enlight.anima.cardmodel.model.spells.Spellbook;
import fr.enlight.anima.cardmodel.model.spells.SpellbookType;
import fr.enlight.anima.cardmodel.model.witchspells.WitchspellsPath;

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

    public static List<SpellGrade> extractGrades(Spell spell) {
        List<SpellGrade> result = new ArrayList<>();
        result.add(spell.initialGrade);
        result.add(spell.intermediateGrade);
        result.add(spell.advancedGrade);
        result.add(spell.arcaneGrade);
        return result;
    }

    public static int countSelectedFreeSpells(Map<Integer, Integer> freeAccessSpellsIds) {
        int count = 0;
        if(freeAccessSpellsIds == null){
            return count;
        }

        for (Integer spellValue : freeAccessSpellsIds.values()) {
            if (spellValue >= 0) {
                count++;
            }
        }
        return count;
    }

    @SuppressLint("UseSparseArrays")
    public static Map<Integer, Integer> generateDefaultFreeAccessMap(Spellbook spellbook, WitchspellsPath witchPath) {
        Map<Integer, Integer> result = new TreeMap<>();
        int level10Round = witchPath.pathLevel / 10;
        boolean hasSecondaryBook = witchPath.secondaryPathBookId > 0;

        for (int index = 0; index < level10Round; index++) {
            if (!hasSecondaryBook) {
                result.put(index + 4, -1);
            }
            if (!spellbook.isMajorPath()) {
                result.put(index + 8, -1);
            }
        }

        int levelFloor = level10Round * 10;
        int last10Levels = levelFloor % 10;
        if (last10Levels >= 4 && !hasSecondaryBook) {
            result.put(levelFloor + 4, -1);
        }
        if (last10Levels >= 8 && !spellbook.isMajorPath()) {
            result.put(levelFloor + 8, -1);
        }

        return result;
    }

    public static int getCeilingLevelForSpellPosition(int mSpellLevelPosition) {
        return ((mSpellLevelPosition / 10) + 1) * 10;
    }
}
