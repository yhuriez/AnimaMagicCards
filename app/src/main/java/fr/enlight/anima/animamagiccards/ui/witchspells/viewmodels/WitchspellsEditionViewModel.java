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

    private Listener mListener;

    @NonNull
    private Witchspells mWitchspells;

    public WitchspellsEditionViewModel() {
        this.mWitchspells = new Witchspells();
    }

    public WitchspellsEditionViewModel(@NonNull Witchspells mWitchspells) {
        this.mWitchspells = mWitchspells;
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
            result.add(new WitchspellsPathGroupViewModel(witchPath));
        }

        // Used to create a field allowing to add a new witch path
        result.add(new WitchspellsPathGroupViewModel());

        return result;
    }

    public void setListener(Listener listener) {
        this.mListener = listener;
    }

    public interface Listener{

    }
}
