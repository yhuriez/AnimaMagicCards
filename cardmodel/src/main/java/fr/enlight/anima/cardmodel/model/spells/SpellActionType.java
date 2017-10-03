package fr.enlight.anima.cardmodel.model.spells;


import android.support.annotation.StringRes;

import fr.enlight.anima.cardmodel.R;

public enum SpellActionType {
    ACTIVE(R.string.enum_active_type),
    PASSIVE(R.string.enum_passive_type);

    public final @StringRes int name;

    SpellActionType(@StringRes int name) {
        this.name = name;
    }
}
