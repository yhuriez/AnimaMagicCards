package fr.enlight.anima.animamagiccards.ui.spells.viewmodels;


import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.databinding.ObservableBoolean;
import android.text.TextUtils;
import android.widget.Toast;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.cardmodel.model.spells.SpellActionType;
import fr.enlight.anima.cardmodel.model.spells.SpellType;

public class SpellFilterViewModel extends ViewModel {

    public final ObservableBoolean filterPanelVisible = new ObservableBoolean(false);

    private boolean searchWitDesc;
    private int spellTypeSelectedId;
    private String intelligenceMax;
    private String zeonMax;
    private String retentionMax;
    private boolean dailyRetentionOnly;
    private int spellActionTypeSelectedId;

    public void onViewClicked(Context context){
        Toast.makeText(context, "Fuck it", Toast.LENGTH_SHORT).show();
    }

    public boolean isSearchWitDesc() {
        return searchWitDesc;
    }

    public void setSearchWitDesc(boolean searchWitDesc) {
        this.searchWitDesc = searchWitDesc;
    }

    public int getSpellTypeSelectedId() {
        return spellTypeSelectedId;
    }

    public void setSpellTypeSelectedId(int spellTypeSelectedId) {
        this.spellTypeSelectedId = spellTypeSelectedId;
    }

    public String getIntelligenceMax() {
        return intelligenceMax;
    }

    public void setIntelligenceMax(String intelligenceMax) {
        this.intelligenceMax = intelligenceMax;
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


    public SpellType getSpellType(){
        switch (spellTypeSelectedId){
            case R.id.spell_type_attack:
                return SpellType.ATTACK;
            case R.id.spell_type_defense:
                return SpellType.DEFENSE;
            case R.id.spell_type_effect:
                return SpellType.EFFECT;
            case R.id.spell_type_animismic:
                return SpellType.ANIMISTIC;
            case R.id.spell_type_automatic:
                return SpellType.AUTOMATIC;
            case R.id.spell_type_detection:
                return SpellType.DETECTION;
            default:
                return null;
        }
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
