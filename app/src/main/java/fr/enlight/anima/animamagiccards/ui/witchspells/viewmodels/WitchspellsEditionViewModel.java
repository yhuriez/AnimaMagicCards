package fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels;


import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.cardmodel.model.witchspells.Witchspells;
import fr.enlight.anima.cardmodel.model.witchspells.WitchspellsPath;

public class WitchspellsEditionViewModel extends BaseObservable {

    private final Listener mListener;

    @Nullable
    private Witchspells mWitchspells;

    public WitchspellsEditionViewModel(Listener listener) {
        this.mWitchspells = null;
        mListener = listener;
    }

    public WitchspellsEditionViewModel(@NonNull Witchspells mWitchspells, Listener listener) {
        this.mWitchspells = mWitchspells;
        mListener = listener;
    }

    public void setWitchName(String witchName){
        if(mWitchspells != null){
            mWitchspells.witchName = witchName;
        }
    }

    public String getWitchName(){
        if(mWitchspells == null){
            return null;
        }
        return mWitchspells.witchName;
    }

    public WitchspellsAddPathViewModel getAddPathViewModel(){
        return new WitchspellsAddPathViewModel(mListener);
    }

    @Bindable
    public List<BindableViewModel> getPathGroupViewModels(){
        List<BindableViewModel> result = new ArrayList<>();

        if(mWitchspells == null){
            return null;
        }

        for (WitchspellsPath witchPath : mWitchspells.witchPaths) {
            result.add(new WitchspellsPathViewModel(witchPath, mListener));
        }

        return result;
    }

    public interface Listener extends WitchspellsPathViewModel.Listener, WitchspellsAddPathViewModel.Listener {
    }
}
