package fr.enlight.anima.animamagiccards.ui.spells.viewmodels;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v4.content.res.ResourcesCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

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

    public CharSequence getGradeValues(){
        SpannableString intelligenceString = new SpannableString(context.getString(R.string.spell_grade_int_format, spellGrade.requiredIntelligence));
        SpannableString zeonString = new SpannableString(context.getString(R.string.spell_grade_zeon_format, spellGrade.zeon));

        if(spellGrade.limitedIntelligence){
            ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
            intelligenceString.setSpan(redSpan, 0, intelligenceString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }

        if(spellGrade.limitedZeon){
            ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
            zeonString.setSpan(redSpan, 0, zeonString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }

        CharSequence result = TextUtils.concat(intelligenceString, " / ", zeonString);

        if(spell.withRetention){
            SpannableString retention;
            if(spell.dailyRetention){
                retention = new SpannableString(context.getString(R.string.spell_grade_retention_daily_format, spellGrade.retention));

            } else {
                retention = new SpannableString(context.getString(R.string.spell_grade_retention_format, spellGrade.retention));
            }

            if(spellGrade.limitedRetention){
                ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
                retention.setSpan(redSpan, 0, retention.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            }

            result = TextUtils.concat(result, "\n", retention);
        }

        return result;
    }

    public boolean isLimitedGrade(){
        return spellGrade.limitedIntelligence || spellGrade.limitedZeon || spellGrade.limitedRetention;
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
