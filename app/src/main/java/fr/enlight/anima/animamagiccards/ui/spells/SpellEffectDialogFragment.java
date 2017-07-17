package fr.enlight.anima.animamagiccards.ui.spells;

import android.os.Bundle;

import fr.enlight.anima.animamagiccards.ui.spells.viewmodels.DialogSpellEffectViewModel;
import fr.enlight.anima.animamagiccards.views.BindingDialogFragment;
import fr.enlight.anima.animamagiccards.views.viewmodels.DialogViewModel;
import fr.enlight.anima.cardmodel.model.spells.Spell;
import fr.enlight.anima.cardmodel.model.spells.SpellbookType;

/**
 * Created by enlight on 16/07/2017.
 */
public class SpellEffectDialogFragment extends BindingDialogFragment {

    public static final String SPELL_PARAM =  "SPELL_PARAM";
    public static final String SPELLBOOK_TYPE_PARAM =  "SPELL_GRADE_LEVEL_PARAM";

    public static SpellEffectDialogFragment newInstance(Spell spell, SpellbookType spellbookType) {
        Bundle args = new Bundle();
        args.putParcelable(SPELL_PARAM, spell);
        args.putString(SPELLBOOK_TYPE_PARAM, spellbookType.name());
        SpellEffectDialogFragment fragment = new SpellEffectDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public DialogViewModel createViewModel() {
        Bundle arguments = getArguments();
        Spell spell = arguments.getParcelable(SPELL_PARAM);
        SpellbookType spellbookType = SpellbookType.valueOf(arguments.getString(SPELLBOOK_TYPE_PARAM));
        return new DialogSpellEffectViewModel(spell, spellbookType);
    }
}
