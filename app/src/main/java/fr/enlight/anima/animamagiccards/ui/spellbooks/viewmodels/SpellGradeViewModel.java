package fr.enlight.anima.animamagiccards.ui.spellbooks.viewmodels;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.v4.content.res.ResourcesCompat;

import fr.enlight.anima.animamagiccards.MainApplication;
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

    private Listener mListener;

    public SpellGradeViewModel(SpellGradeLevel gradeLevel, SpellGrade spellGrade) {
        this.context = MainApplication.getMainContext();
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
