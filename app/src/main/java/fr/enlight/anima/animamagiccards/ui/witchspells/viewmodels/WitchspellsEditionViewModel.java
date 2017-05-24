package fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels;


import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import fr.enlight.anima.animamagiccards.BR;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.cardmodel.model.witchspells.Witchspells;
import fr.enlight.anima.cardmodel.model.witchspells.WitchspellsPath;

public class WitchspellsEditionViewModel extends BaseObservable {

    private final Listener mListener;

    @NonNull
    private Witchspells mWitchspells;


    public WitchspellsEditionViewModel(@NonNull Witchspells mWitchspells, Listener listener) {
        this.mWitchspells = mWitchspells;
        mListener = listener;
    }

    public void setWitchspells(@NonNull Witchspells mWitchspells) {
        this.mWitchspells = mWitchspells;
    }

    public void setWitchName(String witchName){
        mWitchspells.witchName = witchName;
    }

    public String getWitchName(){
        return mWitchspells.witchName;
    }

    public WitchspellsAddPathViewModel getAddPathViewModel(){
        return new WitchspellsAddPathViewModel(mListener);
    }

    @Bindable
    public List<BindableViewModel> getPathGroupViewModels(){
        List<BindableViewModel> result = new ArrayList<>();

        for (WitchspellsPath witchPath : mWitchspells.witchPaths) {
            result.add(new WitchspellsPathViewModel(witchPath, mListener));
        }

        return result;
    }

    public void onModifyClicked(){
        mListener.onModifyWitchPath();
    }

    public void onValidateClicked(){
        mListener.onValidateWitchspells();
    }

    public void refreshWitchPath() {
        notifyPropertyChanged(BR.pathGroupViewModels);
    }

    public interface Listener extends WitchspellsPathViewModel.Listener, WitchspellsAddPathViewModel.Listener {
        void onValidateWitchspells();

        void onModifyWitchPath();
    }
}
