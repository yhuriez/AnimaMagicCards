package fr.enlight.anima.animamagiccards.ui.spellbooks.viewmodels;


import fr.enlight.anima.cardmodel.model.spells.Spellbook;
import fr.enlight.anima.cardmodel.model.witchspells.Witchspells;

public class BookSubviewViewModel {

    public final Spellbook mSpellbook;

    public final Witchspells mWitchspells;
    public final Listener mListener;

    public BookSubviewViewModel(Spellbook spellbook) {
        mSpellbook = spellbook;
        mWitchspells = null;
        mListener = null;
    }

    public BookSubviewViewModel(Witchspells witchspells, Listener listener) {
        mWitchspells = witchspells;
        mSpellbook = null;
        mListener = listener;
    }

    public interface Listener{
        void onModifyWitchspells();
        void onViewWitchspells();
        void onDeleteWitchspells();
    }
}
