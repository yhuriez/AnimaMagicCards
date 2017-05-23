package fr.enlight.anima.animamagiccards.ui.spellbooks.viewmodels;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.text.SpannableString;
import android.text.TextUtils;

import com.android.databinding.library.baseAdapters.BR;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.ui.spellbooks.bo.SpellGradeLevel;
import fr.enlight.anima.cardmodel.model.spells.SpellbookType;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.cardmodel.model.spells.Spell;
import fr.enlight.anima.cardmodel.model.spells.SpellGrade;

/**
 *
 */
public class SpellViewModel implements BindableViewModel, SpellGradeViewModel.Listener {

    public final Spell spell;
    public final SpellbookType spellbookType;

    public ObservableBoolean effectEllipsized = new ObservableBoolean(false);

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

    public CharSequence getSubtitle(Context context){
        String bookTitle = context.getString(spellbookType.titleRes);
        return context.getString(R.string.spell_book_and_level_format, bookTitle, spell.level);
    }

    public Drawable getBackground(Context context){
        return ResourcesCompat.getDrawable(context.getResources(), spellbookType.cardBackgroundRes, null);
    }

    public CharSequence getActionType(Context context){
        CharSequence actionTypeSpannable = new SpannableString(context.getText(R.string.spell_action_type_format));
        return TextUtils.concat(actionTypeSpannable, spell.actionType);
    }

    public CharSequence getType(Context context){
        CharSequence actionTypeSpannable = new SpannableString(context.getText(R.string.spell_type_format));
        return TextUtils.concat(actionTypeSpannable, spell.type);
    }

    public CharSequence getEffect(Context context){
        CharSequence actionTypeSpannable = new SpannableString(context.getText(R.string.spell_effect_format));

        if(spell.effect.length() > 300){
            effectEllipsized.set(true);
        }

        return TextUtils.concat(actionTypeSpannable, spell.effect);
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
