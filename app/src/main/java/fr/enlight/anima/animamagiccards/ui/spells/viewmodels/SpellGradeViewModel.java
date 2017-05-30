package fr.enlight.anima.animamagiccards.ui.spells.viewmodels;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.v4.content.res.ResourcesCompat;

import fr.enlight.anima.animamagiccards.MainApplication;
import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.ui.spells.bo.SpellGradeLevel;
import fr.enlight.anima.cardmodel.model.spells.Spell;
import fr.enlight.anima.cardmodel.model.spells.SpellGrade;

/**
 *
 */
public class SpellGradeViewModel {

    private final Context context;

    private final SpellGradeLevel gradeLevel;
    private final SpellGrade spellGrade;
    private final Spell spell;

    private Listener mListener;

    public SpellGradeViewModel(SpellGradeLevel gradeLevel, SpellGrade spellGrade, Spell spell) {
        this.context = MainApplication.getMainContext();
        this.gradeLevel = gradeLevel;
        this.spellGrade = spellGrade;
        this.spell = spell;
    }

    public String getGradeTitle(){
        return context.getString(gradeLevel.gradeTitleRes);
    }

    public @ColorInt int getGradeColor(){
        return ResourcesCompat.getColor(context.getResources(), gradeLevel.gradeColorRes, null);
    }

    public String getGradeValues(){

        String result = context.getString(R.string.spell_grade_int_zeon_format, spellGrade.requiredIntelligence, spellGrade.zeon);

        if(spell.withRetention){
            result += "\n";
            if(spell.dailyRetention){
                result += context.getString(R.string.spell_grade_retention_daily_format, spellGrade.retention);
            } else {
                result += context.getString(R.string.spell_grade_retention_format, spellGrade.retention);
            }
        }

        return result;
    }

    public String getEffect(){
        return spellGrade.effect;
    }

    public void setListener(Listener listener) {
        this.mListener = listener;
    }

    public void expandGrade(){
        if(mListener != null){
            mListener.onGradeClicked(gradeLevel, spellGrade);
        }
    }

    public interface Listener{
        void onGradeClicked(SpellGradeLevel level, SpellGrade spellGrade);
    }
}
