package fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels.homepage;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.databinding.library.baseAdapters.BR;

import java.util.ArrayList;
import java.util.List;

import fr.enlight.anima.animamagiccards.MainApplication;
import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels.WitchspellsPathViewModel;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.animamagiccards.views.viewmodels.RecyclerViewModel;
import fr.enlight.anima.cardmodel.model.witchspells.Witchspells;
import fr.enlight.anima.cardmodel.model.witchspells.WitchspellsPath;

public class WitchspellsBookViewModel extends RecyclerViewModel implements BindableViewModel, WitchspellsPathViewModel.Listener {

    private final Witchspells witchspells;
    private final Listener mListener;

    public WitchspellsBookViewModel(Witchspells witchspells, Listener listener) {
        this.witchspells = witchspells;
        mListener = listener;
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
    public List<? extends BindableViewModel> getViewModels() {
        List<BindableViewModel> result = new ArrayList<>();

        for (WitchspellsPath witchPath : witchspells.witchPaths) {
            result.add(new WitchspellsPathViewModel(witchPath, this, true));
        }

        return result;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(MainApplication.getMainContext(), LinearLayoutManager.VERTICAL, false);
    }

    public boolean isWithClickableFrame(){
        return witchspells.witchPaths.size() < 3;
    }

    public void onBookClicked(){
        mListener.onWitchspellsClicked(witchspells);
    }

    @Override
    public void onPathSelected(WitchspellsPath witchspellsPath) {
        mListener.onWitchspellsClicked(witchspells);
    }

    public interface Listener {
        void onWitchspellsClicked(Witchspells witchspells);
    }
}
