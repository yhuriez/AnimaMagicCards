package fr.enlight.anima.animamagiccards.ui.spellbooks;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.databinding.ActivitySpellbooksIndexBinding;
import fr.enlight.anima.animamagiccards.loaders.SpellbooksIndexLoader;
import fr.enlight.anima.animamagiccards.ui.spellbooks.utils.SpellbookType;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.animamagiccards.views.viewmodels.RecyclerViewModel;
import fr.enlight.anima.animamagiccards.ui.spellbooks.viewmodels.SpellbookViewModel;

public class SpellbooksFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<BindableViewModel>>,
        SpellbookViewModel.Listener {

    private RecyclerViewModel recyclerViewModel;
    private ActivitySpellbooksIndexBinding binding;

    private Callbacks mCallbacks;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_spellbooks_index, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerViewModel = new RecyclerViewModel();
        recyclerViewModel.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        binding.setModel(recyclerViewModel);

        getLoaderManager().initLoader(1, null, this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public Loader<List<BindableViewModel>> onCreateLoader(int i, Bundle bundle) {
        return new SpellbooksIndexLoader(getActivity(), this);
    }

    @Override
    public void onLoadFinished(Loader<List<BindableViewModel>> loader, List<BindableViewModel> spellbookViewModels) {
        recyclerViewModel.setViewModels(spellbookViewModels);
    }

    @Override
    public void onLoaderReset(Loader<List<BindableViewModel>> loader) {
        // Nothing to do
    }

    @Override
    public void onSpellbookClicked(int spellbookId, SpellbookType type) {
        mCallbacks.onSpellbookClicked(spellbookId, type);
    }


    public interface Callbacks{
        void onSpellbookClicked(int spellbookId, SpellbookType type);
    }
}
