package fr.enlight.anima.cardmodel.utils;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import fr.enlight.anima.cardmodel.model.spells.PathType;
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

    public static Map<Integer, Integer> reevaluateFreeAccessMap(WitchspellsPath witchPath, @Nullable SpellbookType spellbookType) {
        Map<Integer, Integer> result = new TreeMap<>();

        if(spellbookType == null){
            spellbookType = SpellbookType.getTypeFromBookId(witchPath.pathBookId);
        }

        int level10Round = witchPath.pathLevel / 10;
        boolean hasSecondaryBook = witchPath.secondaryPathBookId > 0;

        for (int index = 0; index < level10Round; index++) {
            if (!hasSecondaryBook) {
                int spellPosition = (index * 10) + 4;
                result.put(spellPosition, getSpellId(witchPath, spellPosition));
            }
            if (spellbookType.pathType != PathType.MAJOR_PATH) {
                int spellPosition = (index * 10) + 8;
                result.put(spellPosition, getSpellId(witchPath, spellPosition));
            }
        }

        int levelFloor = level10Round * 10;
        int last10Levels = witchPath.pathLevel % 10;
        if (last10Levels >= 4 && !hasSecondaryBook) {
            int spellPosition = levelFloor + 4;
            result.put(spellPosition, getSpellId(witchPath, spellPosition));
        }
        if (last10Levels >= 8 && spellbookType.pathType != PathType.MAJOR_PATH) {
            int spellPosition = levelFloor + 8;
            result.put(spellPosition, getSpellId(witchPath, spellPosition));
        }

        return result;
    }

    public static int getSpellId(WitchspellsPath witchPath, int spellPosition){
        Integer existingSpellId = witchPath.freeAccessSpellsIds.get(spellPosition);
        return (existingSpellId == null) ? -1 : existingSpellId.intValue();
    }

    public static boolean isFreeAccessAvailable(Spellbook spellbook, WitchspellsPath witchspellsPath) {
        boolean hasSecondaryBook = witchspellsPath.secondaryPathBookId > 0;
        boolean isMajorPath = spellbook.isMajorPath();

        return !((hasSecondaryBook || witchspellsPath.pathLevel < 4) &&
                (isMajorPath || witchspellsPath.pathLevel < 8));
    }

    public static int getCeilingLevelForSpellPosition(int mSpellLevelPosition) {
        return ((mSpellLevelPosition / 10) + 1) * 10;
    }
}
