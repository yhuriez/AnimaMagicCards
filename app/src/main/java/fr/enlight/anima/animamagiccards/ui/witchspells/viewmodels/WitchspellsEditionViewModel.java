package fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels;


import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.cardmodel.model.witchspells.Witchspells;
import fr.enlight.anima.cardmodel.model.witchspells.WitchspellsPath;

public class WitchspellsEditionViewModel extends BaseObservable {

    private final Listener mListener;

    private Witchspells mWitchspells;

    public WitchspellsEditionViewModel(Listener listener) {
        this(null, listener);
    }

    public WitchspellsEditionViewModel(Witchspells mWitchspells, Listener listener) {
        this.mWitchspells = mWitchspells;
        mListener = listener;
    }

    public void setWitchName(String witchName){
        mWitchspells.witchName = witchName;
    }

    public String getWitchName(){
        return mWitchspells.witchName;
    }

    @Bindable
    public List<BindableViewModel> getPathGroupViewModels(){
        List<BindableViewModel> result = new ArrayList<>();

        for (WitchspellsPath witchPath : mWitchspells.witchPaths) {
            result.add(new WitchspellsPathViewModel(witchPath, mListener));
        }

        // Always add an empty witchPath to allow addition of a new path
        result.add(new WitchspellsPathViewModel(null, mListener));

        return result;
    }

    public interface Listener extends WitchspellsPathViewModel.Listener {
    }
}
