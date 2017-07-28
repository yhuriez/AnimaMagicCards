package fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels.homepage;


import android.view.View;

import com.android.databinding.library.baseAdapters.BR;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;

public class WitchspellsAddViewModel implements BindableViewModel{

    private Listener mListener;

    public WitchspellsAddViewModel(Listener listener) {
        this.mListener = listener;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.view_witchspells_add_item;
    }

    @Override
    public int getVariableId() {
        return BR.model;
    }

    public void onWitchspellsClicked(View originator){
        if(mListener != null){
            mListener.onAddWitchspells();
        }
    }

    public interface Listener{
        void onAddWitchspells();
    }
}
