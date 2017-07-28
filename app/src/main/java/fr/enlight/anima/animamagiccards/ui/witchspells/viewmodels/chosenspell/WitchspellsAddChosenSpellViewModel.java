package fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels.chosenspell;

import com.android.databinding.library.baseAdapters.BR;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;



public class WitchspellsAddChosenSpellViewModel implements BindableViewModel {

    private Listener mListener;

    public WitchspellsAddChosenSpellViewModel(Listener listener) {
        this.mListener = listener;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.view_witchspells_add_chosen_spell_item;
    }

    @Override
    public int getVariableId() {
        return BR.model;
    }

    public void onAddClicked(){
        mListener.onAddChosenSpellClicked();
    }

    public interface Listener{
        void onAddChosenSpellClicked();
    }
}
