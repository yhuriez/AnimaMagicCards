package fr.enlight.anima.animamagiccards.viewmodels;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;

import com.android.databinding.library.baseAdapters.BR;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.utils.SpellGradeLevel;
import fr.enlight.anima.animamagiccards.utils.SpellbookType;
import fr.enlight.anima.animamagiccards.utils.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.cardmodel.model.Spell;
import fr.enlight.anima.cardmodel.model.SpellGrade;

/**
 *
 */
public class SpellViewModel implements BindableViewModel{

    public final Spell spell;
    public final SpellbookType spellbookType;

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

    public SpellGradeViewModel getSpellGradeModel(Context context, SpellGradeLevel level){
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
        return new SpellGradeViewModel(context, level, grade);
    }
}
