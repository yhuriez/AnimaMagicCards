package fr.enlight.anima.cardmodel.business;


import android.support.annotation.NonNull;

import java.util.List;

import fr.enlight.anima.cardmodel.model.spells.Spell;
import fr.enlight.anima.cardmodel.model.spells.SpellActionType;
import fr.enlight.anima.cardmodel.model.spells.SpellType;

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

    public SpellFilter createActionTypeSpellFilter(SpellActionType spellActionType){
        return new ActionTypeSpellFilter(spellActionType.name);
    }

    // ////////////////////////////
    // Filter interface and classes
    // ////////////////////////////

    public interface SpellFilter{
        boolean matchFilter(Spell spell);
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
    }
}
