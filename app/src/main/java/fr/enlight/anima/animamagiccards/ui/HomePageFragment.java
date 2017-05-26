package fr.enlight.anima.animamagiccards.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;

import java.util.ArrayList;
import java.util.List;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.async.AllSpellGroupLoader;
import fr.enlight.anima.animamagiccards.databinding.FragmentHomePageBinding;
import fr.enlight.anima.animamagiccards.ui.spellbooks.viewmodels.BookSubviewViewModel;
import fr.enlight.anima.animamagiccards.ui.spellbooks.viewmodels.SpellbookViewModel;
import fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels.WitchspellsAddViewModel;
import fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels.WitchspellsBookViewModel;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.animamagiccards.views.viewmodels.EmptyItemViewModel;
import fr.enlight.anima.cardmodel.model.spells.Spellbook;
import fr.enlight.anima.cardmodel.model.spells.SpellbookType;
import fr.enlight.anima.cardmodel.model.witchspells.Witchspells;

public class HomePageFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<AllSpellGroupLoader.LoaderResult>,
        CarouselLayoutManager.OnCenterItemSelectionListener {

    private static final int LOADER_ID = 1;

    private HomePageViewModel homePageViewModel;
    private FragmentHomePageBinding binding;

    private Callbacks mListener;

    private final SparseArray<String> switchingTitlesIndex = new SparseArray<>();
    private final SparseArray<BookSubviewViewModel> switchingSubviewModelIndex = new SparseArray<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_page, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        homePageViewModel = new HomePageViewModel();
        CarouselLayoutManager carouselLayoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, false);
        carouselLayoutManager.setMaxVisibleItems(1);
        carouselLayoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        carouselLayoutManager.addOnItemSelectionListener(this);
        homePageViewModel.setLayoutManager(carouselLayoutManager);
        homePageViewModel.setOnScrollListener(new CenterScrollListener());

        binding.setModel(homePageViewModel);

        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public void onCenterItemChanged(int index) {
        updateHomePageElements(index);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (Callbacks) context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public Loader<AllSpellGroupLoader.LoaderResult> onCreateLoader(int i, Bundle bundle) {
        return new AllSpellGroupLoader(getActivity());
    }

    @Override
    public void onLoaderReset(Loader<AllSpellGroupLoader.LoaderResult> loader) {
        // Nothing to do
    }

    @Override
    public void onLoadFinished(Loader<AllSpellGroupLoader.LoaderResult> loader, AllSpellGroupLoader.LoaderResult loaderResult) {
        List<BindableViewModel> viewModels = new ArrayList<>();

        for (Witchspells witchspells : loaderResult.witchspells) {
            addWitchspellsToIndex(viewModels.size(), witchspells);
            WitchspellsBookViewModel viewModel = new WitchspellsBookViewModel(witchspells, mListener);
            viewModels.add(viewModel);
        }
        addNewWitchspellsToIndex(viewModels.size());
        viewModels.add(new WitchspellsAddViewModel(mListener));

        // Separator
        viewModels.add(new EmptyItemViewModel());

        for (Spellbook spellbook : loaderResult.spellbooks) {
            // Separator
            if (spellbook.spellbookType == SpellbookType.FREE_ACCESS) {
                viewModels.add(new EmptyItemViewModel());
            }

            addSpellbookToIndex(viewModels.size(), spellbook);

            SpellbookViewModel viewModel = new SpellbookViewModel(spellbook);
            viewModel.setListener(mListener);
            viewModels.add(viewModel);

            // Separator
            if (spellbook.spellbookType == SpellbookType.FREE_ACCESS) {
                viewModels.add(new EmptyItemViewModel());
            }
        }

        homePageViewModel.setViewModels(viewModels);
        updateHomePageElements(0);
    }

    private void addNewWitchspellsToIndex(int position) {
        String title = getString(R.string.Witchspells_Add_Label);
        String description = getString(R.string.Witchspells_Add_Description_Label);

        switchingSubviewModelIndex.put(position, new BookSubviewViewModel(title, description));
        switchingTitlesIndex.put(position, getString(R.string.Witchspells_Title));
    }

    public void addSpellbookToIndex(int position, Spellbook spellbook) {
        switchingSubviewModelIndex.put(position, new BookSubviewViewModel(spellbook));

        if (SpellbookType.MAIN_SPELLBOOKS.contains(spellbook.spellbookType)) {
            switchingTitlesIndex.put(position, getString(R.string.Main_Path_Title));

        } else if (SpellbookType.SECONDARY_SPELLBOOKS.contains(spellbook.spellbookType)) {
            switchingTitlesIndex.put(position, getString(R.string.Secondary_Path_Title));

        } else if (SpellbookType.FREE_ACCESS == spellbook.spellbookType) {
            switchingTitlesIndex.put(position, getString(R.string.Free_Access_Title));
        }
    }

    public void addWitchspellsToIndex(int position, Witchspells witchspells) {
        switchingSubviewModelIndex.put(position, new BookSubviewViewModel(witchspells, mListener));
        switchingTitlesIndex.put(position, getString(R.string.Witchspells_Title));
    }

    private void updateHomePageElements(int index) {
        BookSubviewViewModel bookModel = switchingSubviewModelIndex.get(index);
        if (bookModel != null) {
            binding.setBookModel(bookModel);
        }

        String title = switchingTitlesIndex.get(index);
        if (title != null) {
            homePageViewModel.setCurrentTitle(title);
        }
    }

    public void resetSpellbooks() {
        getLoaderManager().restartLoader(LOADER_ID, null, this);
    }


    public interface Callbacks extends
            BookSubviewViewModel.Listener,
            WitchspellsBookViewModel.Listener,
            WitchspellsAddViewModel.Listener,
            SpellbookViewModel.Listener {
    }
}
