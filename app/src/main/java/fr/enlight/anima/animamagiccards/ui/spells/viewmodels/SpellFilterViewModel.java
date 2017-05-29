package fr.enlight.anima.animamagiccards.ui.spells.viewmodels;


import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.cardmodel.model.spells.SpellActionType;
import fr.enlight.anima.cardmodel.model.spells.SpellType;

public class SpellFilterViewModel extends ViewModel {

    public final ObservableBoolean filterPanelVisible = new ObservableBoolean(false);

    private boolean searchWitDesc;
    private final Map<SpellType, Boolean> selectedSpellTypes = new HashMap<>();
    private String intelligenceMax;
    private String zeonMax;
    private String retentionMax;
    private boolean dailyRetentionOnly;
    private int spellActionTypeSelectedId;


    public boolean isSearchWitDesc() {
        return searchWitDesc;
    }

    public void setSearchWitDesc(boolean searchWitDesc) {
        this.searchWitDesc = searchWitDesc;
    }

    public String getIntelligenceMax() {
        return intelligenceMax;
    }

    public void setIntelligenceMax(String intelligenceMax) {
        this.intelligenceMax = intelligenceMax;
    }

    public boolean isSpellTypeChecked(SpellType spellType){
        Boolean selectedSpellType = selectedSpellTypes.get(spellType);
        return selectedSpellType != null && selectedSpellType;
    }

    public void onSpellTypeChecked(boolean checked, SpellType spellType){
        selectedSpellTypes.put(spellType, checked);
    }

    public String getZeonMax() {
        return zeonMax;
    }

    public void setZeonMax(String zeonMax) {
        this.zeonMax = zeonMax;
    }

    public String getRetentionMax() {
        return retentionMax;
    }

    public void setRetentionMax(String retentionMax) {
        this.retentionMax = retentionMax;
    }

    public boolean isDailyRetentionOnly() {
        return dailyRetentionOnly;
    }

    public void setDailyRetentionOnly(boolean dailyRetentionOnly) {
        this.dailyRetentionOnly = dailyRetentionOnly;
    }

    public int getSpellActionTypeSelectedId() {
        return spellActionTypeSelectedId;
    }

    public void setSpellActionTypeSelectedId(int spellActionTypeSelectedId) {
        this.spellActionTypeSelectedId = spellActionTypeSelectedId;
    }

    public List<SpellType> getSelectedSpellTypes(){
        return new ArrayList<>(selectedSpellTypes.keySet());
    }

    public SpellActionType getSpellActionType(){
        switch (spellActionTypeSelectedId){
            case R.id.spell_action_type_active:
                return SpellActionType.ACTIVE;
            case R.id.spell_action_type_passive:
                return SpellActionType.PASSIVE;
            default:
                return null;
        }
    }

    public int getIntelligenceMaxValue(){
        if(TextUtils.isEmpty(intelligenceMax)){
            return -1;
        }
        return Integer.parseInt(intelligenceMax);
    }

    public int getRetentionMaxValue(){
        if(TextUtils.isEmpty(retentionMax)){
            return -1;
        }
        return Integer.parseInt(retentionMax);
    }

    public int getZeonMaxValue(){
        if(TextUtils.isEmpty(zeonMax)){
            return -1;
        }
        return Integer.parseInt(zeonMax);
    }
}
