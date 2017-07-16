package fr.enlight.anima.animamagiccards.ui.spells;

import android.os.Bundle;

import fr.enlight.anima.animamagiccards.ui.spells.bo.SpellGradeLevel;
import fr.enlight.anima.animamagiccards.ui.spells.viewmodels.DialogSpellGradeViewModel;
import fr.enlight.anima.animamagiccards.views.BindingDialogFragment;
import fr.enlight.anima.animamagiccards.views.viewmodels.DialogViewModel;
import fr.enlight.anima.cardmodel.model.spells.Spell;
import fr.enlight.anima.cardmodel.model.spells.SpellGrade;

/**
 * Created by enlight on 16/07/2017.
 */
public class SpellGradeDialogFragment extends BindingDialogFragment {

    public static final String SPELL_GRADE_LEVEL_PARAM =  "SPELL_GRADE_LEVEL_PARAM";
    public static final String SPELL_GRADE_PARAM =  "SPELL_GRADE_PARAM";
    public static final String SPELL_PARAM =  "SPELL_PARAM";

    public static SpellGradeDialogFragment newInstance(SpellGradeLevel spellGradeLevel, SpellGrade spellGrade, Spell spell) {
        Bundle args = new Bundle();
        args.putParcelable(SPELL_PARAM, spell);
        args.putString(SPELL_GRADE_LEVEL_PARAM, spellGradeLevel.name());
        args.putParcelable(SPELL_GRADE_PARAM, spellGrade);
        SpellGradeDialogFragment fragment = new SpellGradeDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public DialogViewModel createViewModel() {
        Bundle arguments = getArguments();
        Spell spell = arguments.getParcelable(SPELL_PARAM);
        SpellGradeLevel spellGradeLevel = SpellGradeLevel.valueOf(arguments.getString(SPELL_GRADE_LEVEL_PARAM));
        SpellGrade spellGrade = arguments.getParcelable(SPELL_GRADE_PARAM);
        return new DialogSpellGradeViewModel(spellGradeLevel, spellGrade, spell);
    }
}
