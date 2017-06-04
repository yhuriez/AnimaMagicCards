package fr.enlight.anima.animamagiccards.ui.spells.viewmodels;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
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
            spellLevel = Math.max(1, (spell.level - 10)) + "-" + spell.level;
        } else {
            spellLevel = String.valueOf(spell.level);
        }

        return context.getString(R.string.spell_book_and_level_format, bookTitle, spellLevel);
    }

    public Drawable getBackground(Context context){
        return ResourcesCompat.getDrawable(context.getResources(), spellbookType.cardBackgroundRes, null);
    }

    public CharSequence getActionType(Context context){
        CharSequence actionTypeSpannable = new SpannableString(context.getText(R.string.spell_action_type_format) + " ");
        SpannableString actionValue = new SpannableString(spell.actionType);
        if(spell.highlightActionType){
            actionValue.setSpan(
                    new StyleSpan(Typeface.BOLD),
                    0,
                    actionValue.length(),
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE
            );
        }
        return TextUtils.concat(actionTypeSpannable, " " + actionValue);
    }

    public CharSequence getType(Context context){
        CharSequence actionTypeSpannable = new SpannableString(context.getText(R.string.spell_type_format) + " ");
        SpannableString typeValue = new SpannableString(spell.type);
        if(spell.highlightActionType){
            typeValue.setSpan(
                    new StyleSpan(Typeface.BOLD),
                    0,
                    typeValue.length(),
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE
            );
        }
        return TextUtils.concat(actionTypeSpannable, typeValue);
    }

    public CharSequence getEffect(Context context){
        CharSequence actionTypeSpannable = new SpannableString(context.getText(R.string.spell_effect_format) + " ");

        if(spell.effect.length() > 300){
            effectEllipsized.set(true);
        }

        return TextUtils.concat(actionTypeSpannable, " " + spell.effect);
    }

    public boolean isReduced() {
        return reduced;
    }

    public void setReduced(boolean reduced) {
        this.reduced = reduced;
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
            DialogSpellEffectViewModel dialogViewModel = new DialogSpellEffectViewModel(spell, spellbookType);
            mListener.onEffectClicked(dialogViewModel);
        }
    }

    @Override
    public void onGradeClicked(SpellGradeLevel level, SpellGrade spellGrade) {
        if(mListener != null){
            DialogSpellGradeViewModel dialogViewModel = new DialogSpellGradeViewModel(level, spellGrade, spell);
            mListener.onGradeClicked(dialogViewModel);
        }
    }

    public interface Listener{
        void onEffectClicked(DialogSpellEffectViewModel dialogViewModel);

        void onGradeClicked(DialogSpellGradeViewModel dialogViewModel);
    }
}
