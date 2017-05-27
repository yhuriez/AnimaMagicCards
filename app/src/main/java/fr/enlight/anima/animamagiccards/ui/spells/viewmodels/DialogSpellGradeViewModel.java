package fr.enlight.anima.animamagiccards.ui.spells.viewmodels;

import com.android.databinding.library.baseAdapters.BR;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.ui.spells.bo.SpellGradeLevel;
import fr.enlight.anima.animamagiccards.views.viewmodels.DialogViewModel;
import fr.enlight.anima.animamagiccards.views.viewmodels.DismissDialogListener;
import fr.enlight.anima.cardmodel.model.spells.Spell;
import fr.enlight.anima.cardmodel.model.spells.SpellGrade;


public class DialogSpellGradeViewModel extends SpellGradeViewModel implements DialogViewModel{

    private DismissDialogListener mListener;

    public DialogSpellGradeViewModel(SpellGradeLevel gradeLevel, SpellGrade spellGrade, Spell spell) {
        super(gradeLevel, spellGrade, spell);
    }

    @Override
    public void setListener(DismissDialogListener listener) {
        mListener = listener;
    }

    public void onDismissClicked(){
        mListener.dismissDialog();
    }

    public String getFormattedEffect(){
        String effect = getEffect();
        String[] split = effect.split(" / ");
        if(split.length > 1){
            effect = effect.replace(" / ", "\n");
        }
        return effect;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.dialog_spell_grade;
    }

    @Override
    public int getVariableId() {
        return BR.model;
    }
}
