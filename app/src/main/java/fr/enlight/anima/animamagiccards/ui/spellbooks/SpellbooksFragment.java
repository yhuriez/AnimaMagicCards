package fr.enlight.anima.animamagiccards.ui.spellbooks;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;

import java.util.ArrayList;
import java.util.List;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.databinding.ActivitySpellbooksIndexBinding;
import fr.enlight.anima.animamagiccards.loaders.SpellbooksWitchspellsLoader;
import fr.enlight.anima.cardmodel.model.spells.SpellbookType;
import fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels.WitchspellsAddViewModel;
import fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels.WitchspellsBookViewModel;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.animamagiccards.views.viewmodels.RecyclerViewModel;
import fr.enlight.anima.animamagiccards.ui.spellbooks.viewmodels.SpellbookViewModel;
import fr.enlight.anima.cardmodel.model.spells.Spellbook;
import fr.enlight.anima.cardmodel.model.witchspells.Witchspells;

public class SpellbooksFragment extends Fragment implements LoaderManager.LoaderCallbacks<SpellbooksWitchspellsLoader.LoaderResult>,
        SpellbookViewModel.Listener, WitchspellsAddViewModel.Listener {

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
        CarouselLayoutManager carouselLayoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, false);
        carouselLayoutManager.setMaxVisibleItems(3);
        carouselLayoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        recyclerViewModel.setLayoutManager(carouselLayoutManager);
        recyclerViewModel.setOnScrollListener(new CenterScrollListener());

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
    public Loader<SpellbooksWitchspellsLoader.LoaderResult> onCreateLoader(int i, Bundle bundle) {
        return new SpellbooksWitchspellsLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<SpellbooksWitchspellsLoader.LoaderResult> loader, SpellbooksWitchspellsLoader.LoaderResult loaderResult) {
        List<BindableViewModel> viewModels = new ArrayList<>();

        for (Witchspells witchspells : loaderResult.witchspells) {
            WitchspellsBookViewModel viewModel = new WitchspellsBookViewModel(witchspells);
            viewModels.add(viewModel);
        }
        viewModels.add(new WitchspellsAddViewModel(this));

        for (Spellbook spellbook : loaderResult.spellbooks) {
            SpellbookViewModel viewModel = new SpellbookViewModel(spellbook);
            viewModel.setListener(this);
            viewModels.add(viewModel);
        }

        recyclerViewModel.setViewModels(viewModels);
    }

    @Override
    public void onLoaderReset(Loader<SpellbooksWitchspellsLoader.LoaderResult> loader) {
        // Nothing to do
    }

    @Override
    public void onSpellbookClicked(int spellbookId, SpellbookType type) {
        mCallbacks.onSpellbookClicked(spellbookId, type);
    }

    @Override
    public void onAddWitchspells() {
        mCallbacks.onAddWitchspells();
    }

    public interface Callbacks{
        void onSpellbookClicked(int spellbookId, SpellbookType type);

        void onAddWitchspells();
    }
}
