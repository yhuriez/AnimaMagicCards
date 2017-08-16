package fr.enlight.anima.animamagiccards.ui.spells.viewmodels;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.DimenRes;
import android.support.v4.content.res.ResourcesCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StyleSpan;

import com.android.databinding.library.baseAdapters.BR;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.ui.spells.bo.SpellGradeLevel;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.cardmodel.model.spells.Spell;
import fr.enlight.anima.cardmodel.model.spells.SpellGrade;
import fr.enlight.anima.cardmodel.model.spells.SpellbookType;

/**
 *
 */
public class SpellViewModel implements BindableViewModel, SpellGradeViewModel.Listener {

    public final Spell spell;
    public final SpellbookType spellbookType;

    public ObservableBoolean effectEllipsized = new ObservableBoolean(false);

    private boolean reduced;

    private Listener mListener;

    public SpellViewModel(Spell spell, SpellbookType spellbookType) {
        this.spell = spell;
        this.spellbookType = spellbookType;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.view_spell_item;
    }

    @Override
    public int getVariableId() {
        return BR.model;
    }

    public Spell getSpell() {
        return spell;
    }

    public CharSequence getSubtitle(Context context){
        String bookTitle = context.getString(spellbookType.titleRes);

        String spellLevel;
        if(spell.bookId == SpellbookType.FREE_ACCESS.bookId){
            int ceilingLevel;
            if(spell.level % 10 == 0){
                // Free access book case
                ceilingLevel = spell.level;
            } else {
                // Free access in witchspells case
                ceilingLevel = ((spell.level / 10) + 1) * 10;
            }
            int bottomLevel = Math.max(1, ceilingLevel - 10);

            spellLevel = bottomLevel + " - " + ceilingLevel;

        } else {
            spellLevel = String.valueOf(spell.level);
        }

        return context.getString(R.string.spell_book_and_level_format, bookTitle, spellLevel);
    }

    public Drawable getBackground(Context context){
        return ResourcesCompat.getDrawable(context.getResources(), spellbookType.cardBackgroundRes, null);
    }

    public CharSequence getActionType(Context context){
        SpannableString actionTypeSpannable = new SpannableString(context.getText(R.string.spell_action_type_format) + " ");
        actionTypeSpannable.setSpan(
                new StyleSpan(Typeface.BOLD),
                0,
                actionTypeSpannable.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        SpannableString actionValue = new SpannableString(spell.actionType);
        if(spell.highlightActionType){
            actionValue.setSpan(
                    new StyleSpan(Typeface.BOLD),
                    0,
                    actionValue.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }
        return TextUtils.concat(actionTypeSpannable, actionValue);
    }

    public CharSequence getType(Context context){
        SpannableString actionTypeSpannable = new SpannableString(context.getText(R.string.spell_type_format) + " ");
        actionTypeSpannable.setSpan(
                new StyleSpan(Typeface.BOLD),
                0,
                actionTypeSpannable.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        SpannableString typeValue = new SpannableString(spell.type);
        if(spell.highlightType){
            typeValue.setSpan(
                    new StyleSpan(Typeface.BOLD),
                    0,
                    typeValue.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }
        return TextUtils.concat(actionTypeSpannable, typeValue);
    }

    public CharSequence getEffect(Context context){
        SpannableString actionTypeSpannable = new SpannableString(context.getText(R.string.spell_effect_format) + " ");
        actionTypeSpannable.setSpan(
                new StyleSpan(Typeface.BOLD),
                0,
                actionTypeSpannable.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        if(spell.effect.length() > 300){
            effectEllipsized.set(true);
        }

        return TextUtils.concat(actionTypeSpannable, spell.effect);
    }

    public boolean isReduced() {
        return reduced;
    }

    public void setReduced(boolean reduced) {
        this.reduced = reduced;
    }

    public int getStackHeaderHeight(Context context){
        @DimenRes int stackRes = reduced ? R.dimen.spell_stack_header_height_reduced : R.dimen.spell_stack_header_height_normal;
        return context.getResources().getDimensionPixelSize(stackRes);
    }

    public SpellGradeViewModel getSpellGradeModel(SpellGradeLevel level){
        SpellGrade grade = null;
        switch (level){
            case INITIAL_LEVEL:
                grade = spell.initialGrade;
                break;
            case INTERMEDIATE_LEVEL:
                grade = spell.intermediateGrade;
                break;
            case ADVANCED_LEVEL:
                grade = spell.advancedGrade;
                break;
            case ARCANE_LEVEL:
                grade = spell.arcaneGrade;
                break;
        }

        SpellGradeViewModel spellGradeViewModel = new SpellGradeViewModel(level, grade, spell);
        spellGradeViewModel.setListener(this);
        return spellGradeViewModel;
    }

    public void setListener(Listener listener) {
        this.mListener = listener;
    }

    public void expandEffect(){
        if(mListener != null){
            mListener.onEffectClicked(spell, spellbookType);
        }
    }

    @Override
    public void onGradeClicked(SpellGradeLevel level, SpellGrade spellGrade) {
        if(mListener != null){
            mListener.onGradeClicked(level, spellGrade, spell);
        }
    }

    public interface Listener{
        void onEffectClicked(Spell spell, SpellbookType spellbookType);

        void onGradeClicked(SpellGradeLevel level, SpellGrade spellGrade, Spell spell);
    }
}
