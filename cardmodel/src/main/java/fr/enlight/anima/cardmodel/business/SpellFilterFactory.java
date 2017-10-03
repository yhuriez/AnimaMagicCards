package fr.enlight.anima.cardmodel.business;


import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import fr.enlight.anima.cardmodel.model.spells.Spell;
import fr.enlight.anima.cardmodel.model.spells.SpellActionType;
import fr.enlight.anima.cardmodel.model.spells.SpellGrade;
import fr.enlight.anima.cardmodel.model.spells.SpellType;
import fr.enlight.anima.cardmodel.model.spells.SpellbookType;
import fr.enlight.anima.cardmodel.utils.SpellUtils;

import static fr.enlight.anima.cardmodel.utils.StringUtils.containsIgnoreCase;

public class SpellFilterFactory {

    public SpellFilter createSearchSpellFilter(String searchText, boolean withDescriptionSearch){
        return new SearchSpellFilter(searchText, withDescriptionSearch);
    }

    public SpellFilter createTypeSpellFilter(List<SpellType> spellType){
        return new TypeSpellFilter(spellType);
    }

    public SpellFilter createIntelligenceSpellFilter(int intelligence){
        return new IntelligenceSpellFilter(intelligence);
    }

    public SpellFilter createZeonSpellFilter(int zeon, int retention, boolean dailyOnly){
        return new ZeonSpellFilter(zeon, retention, dailyOnly);
    }

    public SpellFilter createActionTypeSpellFilter(Context context, SpellActionType spellActionType){
        return new ActionTypeSpellFilter(context.getString(spellActionType.name));
    }

    public SpellFilter createLevelWindowFilter(int bottomLevel, int topLevel) {
        return new LevelWindowSpellFilter(bottomLevel, topLevel);
    }

    public SpellFilter createSpellbookPathFilter(SpellbookType spellbookType) {
        return new SpellbookPathFilter(spellbookType);
    }

    public SpellFilter createNonFilteringFilter() {
        return new NonFilteringFilter();
    }

    // ////////////////////////////
    // Filter interface and classes
    // ////////////////////////////

    public interface SpellFilter{
        boolean matchFilter(Spell spell);

        void updateSpellWithFilter(Spell spell);
    }

    public class SearchSpellFilter implements SpellFilter{

        @NonNull
        private String textToSearch;

        private boolean searchInDescription;

        public SearchSpellFilter(@NonNull String textToSearch, boolean searchInDescription) {
            this.textToSearch = textToSearch;
            this.searchInDescription = searchInDescription;
        }

        @Override
        public boolean matchFilter(Spell spell) {
            return containsIgnoreCase(spell.name, textToSearch) || (searchInDescription && containsIgnoreCase(spell.effect, textToSearch));
        }

        @Override
        public void updateSpellWithFilter(Spell spell) {
            spell.searchWord = textToSearch;
            spell.searchInDescription = searchInDescription;
        }
    }

    public class TypeSpellFilter implements SpellFilter{

        private List<SpellType> spellTypeList;

        public TypeSpellFilter(List<SpellType> spellType) {
            this.spellTypeList = spellType;
        }

        @Override
        public boolean matchFilter(Spell spell) {
            for (SpellType type : spellTypeList) {
                if(containsIgnoreCase(spell.type, type.name)){
                    return true;
                }
            }
            return false;
        }

        @Override
        public void updateSpellWithFilter(Spell spell) {
            spell.highlightType = true;
        }
    }

    public class IntelligenceSpellFilter implements SpellFilter{

        private int intelligenceMax;

        public IntelligenceSpellFilter(int intelligenceMax) {
            this.intelligenceMax = intelligenceMax;
        }

        @Override
        public boolean matchFilter(Spell spell) {
            return spell.initialGrade.requiredIntelligence <= intelligenceMax;
        }

        @Override
        public void updateSpellWithFilter(Spell spell) {
            for (SpellGrade spellGrade : SpellUtils.extractGrades(spell)) {
                if(spellGrade.requiredIntelligence > intelligenceMax){
                    spellGrade.limitedIntelligence = true;
                }
            }
        }
    }

    public class ZeonSpellFilter implements SpellFilter{

        private int zeonMax;
        private int retentionMax;
        private boolean dailyMaintainOnly;

        public ZeonSpellFilter(int zeonMax, int retentionMax, boolean dailyMaintainOnly) {
            this.zeonMax = zeonMax;
            this.retentionMax = retentionMax;
            this.dailyMaintainOnly = dailyMaintainOnly;
        }

        @Override
        public boolean matchFilter(Spell spell) {
            boolean result = spell.initialGrade.zeon <= zeonMax;

            if(spell.withRetention && retentionMax > 0){
                result &= spell.initialGrade.retention <= retentionMax;
            }

            if(result && dailyMaintainOnly){
                return spell.dailyRetention;
            }

            return result;
        }

        @Override
        public void updateSpellWithFilter(Spell spell) {
            for (SpellGrade spellGrade : SpellUtils.extractGrades(spell)) {
                if(spellGrade.zeon > zeonMax){
                    spellGrade.limitedZeon = true;
                }
                if(spell.withRetention && retentionMax > 0 && spellGrade.retention > retentionMax){
                    spellGrade.limitedRetention = true;
                }
            }
        }
    }

    public class ActionTypeSpellFilter implements SpellFilter{

        private String actionTypeSpell;

        public ActionTypeSpellFilter(String actionTypeSpell) {
            this.actionTypeSpell = actionTypeSpell;
        }

        @Override
        public boolean matchFilter(Spell spell) {
            return containsIgnoreCase(spell.actionType, actionTypeSpell);
        }

        @Override
        public void updateSpellWithFilter(Spell spell) {
            spell.highlightActionType = true;
        }
    }

    public class LevelWindowSpellFilter implements SpellFilter{

        private int bottomLevel;
        private int topLevel;

        public LevelWindowSpellFilter(int bottomLevel, int topLevel) {
            this.bottomLevel = bottomLevel;
            this.topLevel = topLevel;
        }

        @Override
        public boolean matchFilter(Spell spell) {
            return spell.level >= bottomLevel && spell.level <= topLevel;
        }

        @Override
        public void updateSpellWithFilter(Spell spell) {
            // Nothing to do here
        }
    }

    private class SpellbookPathFilter implements SpellFilter {

        private final SpellbookType mSpellbookType;

        public SpellbookPathFilter(SpellbookType spellbookType) {
            mSpellbookType = spellbookType;
        }

        @Override
        public boolean matchFilter(Spell spell) {
            if(spell.freeAccessAssociatedType!= null){
                return spell.freeAccessAssociatedType.bookId == mSpellbookType.bookId;
            }
            return spell.spellbookType != null && spell.spellbookType.bookId == mSpellbookType.bookId;
        }

        @Override
        public void updateSpellWithFilter(Spell spell) {
            // Nothing to update here
        }
    }

    private class NonFilteringFilter implements SpellFilter {

        @Override
        public boolean matchFilter(Spell spell) {
            return true;
        }

        @Override
        public void updateSpellWithFilter(Spell spell) {
            // Nothing to update here
        }
    }
}
