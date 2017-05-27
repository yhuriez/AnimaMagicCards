package fr.enlight.anima.animamagiccards.ui.spells.viewmodels;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.cardmodel.model.spells.SpellbookType;
import fr.enlight.anima.animamagiccards.views.viewmodels.DialogViewModel;
import fr.enlight.anima.animamagiccards.views.viewmodels.DismissDialogListener;
import fr.enlight.anima.cardmodel.model.spells.Spell;


public class DialogSpellEffectViewModel extends SpellViewModel implements DialogViewModel {

    private DismissDialogListener mListener;

    public DialogSpellEffectViewModel(Spell spell, SpellbookType spellbookType) {
        super(spell, spellbookType);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.dialog_spell_effect;
    }

    @Override
    public void setListener(DismissDialogListener listener) {
        mListener = listener;
    }

    public void onDismissClicked(){
        mListener.dismissDialog();
    }
}
