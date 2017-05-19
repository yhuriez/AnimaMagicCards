package fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels;


import com.android.databinding.library.baseAdapters.BR;

import java.util.ArrayList;
import java.util.List;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.cardmodel.model.witchspells.Witchspells;
import fr.enlight.anima.cardmodel.model.witchspells.WitchspellsPath;

public class WitchspellsBookViewModel implements BindableViewModel{

    private final Witchspells witchspells;


    public WitchspellsBookViewModel(Witchspells witchspells) {
        this.witchspells = witchspells;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.view_witchspells_book_item;
    }

    @Override
    public int getVariableId() {
        return BR.model;
    }

    public String getWitchName(){
        return witchspells.witchName;
    }

    public List<BindableViewModel> getViewModels() {
        List<BindableViewModel> result = new ArrayList<>();

        for (WitchspellsPath witchPath : witchspells.witchPaths) {
//            result.add(new WitchspellsPathViewModel(witchPath, mListener));
        }

        return result;
    }
}
