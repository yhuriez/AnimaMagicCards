package fr.enlight.anima.animamagiccards.ui.spellbooks.viewmodels;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.v4.content.res.ResourcesCompat;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.ui.spellbooks.utils.SpellGradeLevel;
import fr.enlight.anima.cardmodel.model.SpellGrade;

/**
 *
 */
public class SpellGradeViewModel {

    private final Context context;

    private final SpellGradeLevel gradeLevel;
    private final SpellGrade spellGrade;

    public SpellGradeViewModel(Context context, SpellGradeLevel gradeLevel, SpellGrade spellGrade) {
        this.context = context;
        this.gradeLevel = gradeLevel;
        this.spellGrade = spellGrade;
    }

    public String getGradeTitle(){
        return context.getString(gradeLevel.gradeTitleRes);
    }

    public @ColorInt int getGradeColor(){
        return ResourcesCompat.getColor(context.getResources(), gradeLevel.gradeColorRes, null);
    }

    public String getIntAndZeon(){
        return context.getString(R.string.spell_grade_int_zeon_format, spellGrade.requiredIntelligence, spellGrade.zeon);
    }

    public String getEffect(){
        return spellGrade.effect;
    }

    public String getRetention(){
        if(spellGrade.retention > 0){
            return context.getString(R.string.spell_grade_retention_format, spellGrade.retention);
        }
        return null;
    }
}
