package fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.databinding.library.baseAdapters.BR;

import java.util.ArrayList;
import java.util.List;

import fr.enlight.anima.animamagiccards.MainApplication;
import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.animamagiccards.views.viewmodels.RecyclerViewModel;
import fr.enlight.anima.cardmodel.model.spells.Spellbook;
import fr.enlight.anima.cardmodel.model.spells.SpellbookType;
import fr.enlight.anima.cardmodel.model.witchspells.Witchspells;
import fr.enlight.anima.cardmodel.model.witchspells.WitchspellsPath;

public class WitchspellsBookViewModel extends RecyclerViewModel implements BindableViewModel {

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

    @Override
    public List<BindableViewModel> getViewModels() {
        List<BindableViewModel> result = new ArrayList<>();

        for (WitchspellsPath witchPath : witchspells.witchPaths) {
            SpellbookType mainBookId = SpellbookType.getTypeFromBookId(witchPath.pathBookId);
            SpellbookType secondaryBookId = SpellbookType.getTypeFromBookId(witchPath.secondaryPathBookId);

            result.add(new WitchspellsPathReducedViewModel(mainBookId, secondaryBookId));
        }

        return result;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(MainApplication.getMainContext(), 2, LinearLayoutManager.HORIZONTAL, false);
    }
}
