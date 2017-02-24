package fr.enlight.anima.animamagiccards.ui.spellbooks.viewmodels;

import com.android.databinding.library.baseAdapters.BR;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.ui.spellbooks.utils.SpellGradeLevel;
import fr.enlight.anima.animamagiccards.views.viewmodels.DialogViewModel;
import fr.enlight.anima.animamagiccards.views.viewmodels.DismissDialogListener;
import fr.enlight.anima.cardmodel.model.SpellGrade;


public class DialogSpellGradeViewModel extends SpellGradeViewModel implements DialogViewModel{

    private DismissDialogListener mListener;

    public DialogSpellGradeViewModel(SpellGradeLevel gradeLevel, SpellGrade spellGrade) {
        super(gradeLevel, spellGrade);
    }

    @Override
    public void setListener(DismissDialogListener listener) {
        mListener = listener;
    }

    public void onDismissClicked(){
        mListener.dismissDialog();
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
