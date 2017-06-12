package fr.enlight.anima.animamagiccards.ui;


import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import fr.enlight.anima.animamagiccards.ui.other.AboutUsViewModel;
import fr.enlight.anima.animamagiccards.ui.spells.viewmodels.BookSubviewViewModel;
import fr.enlight.anima.animamagiccards.ui.spells.viewmodels.SpellbookViewModel;
import fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels.WitchspellsAddViewModel;
import fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels.WitchspellsBookViewModel;
import fr.enlight.anima.animamagiccards.utils.IntentsUtils;
import fr.enlight.anima.animamagiccards.views.BindingDialogFragment;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.animamagiccards.views.viewmodels.EmptyItemViewModel;
import fr.enlight.anima.cardmodel.business.WitchspellsBusinessService;
import fr.enlight.anima.cardmodel.business.WitchspellsUpdateListener;
import fr.enlight.anima.cardmodel.model.spells.Spellbook;
import fr.enlight.anima.cardmodel.model.spells.SpellbookType;
import fr.enlight.anima.cardmodel.model.witchspells.Witchspells;

public class HomePageFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<AllSpellGroupLoader.LoaderResult>,
        CarouselLayoutManager.OnCenterItemSelectionListener, WitchspellsUpdateListener {

    private static final int LOADER_ID = 1;

    private HomePageViewModel homePageViewModel;
    private FragmentHomePageBinding binding;

    private Callbacks mListener;

    private final SparseArray<String> switchingTitlesIndex = new SparseArray<>();
    private final SparseArray<BookSubviewViewModel> switchingSubviewModelIndex = new SparseArray<>();
    private boolean mFirstLoad;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_page, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setTitle(getString(R.string.HomePage_Title));
        }
        setHasOptionsMenu(true);

        mFirstLoad = true;

        homePageViewModel = new HomePageViewModel();
        CarouselLayoutManager carouselLayoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, false);
        carouselLayoutManager.setMaxVisibleItems(1);
        carouselLayoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        carouselLayoutManager.addOnItemSelectionListener(this);
        homePageViewModel.setLayoutManager(carouselLayoutManager);
        homePageViewModel.setOnScrollListener(new CenterScrollListener());

        binding.setModel(homePageViewModel);
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
    public void onStart() {
        super.onStart();
        getLoaderManager().restartLoader(LOADER_ID, null, this);
        WitchspellsBusinessService.addWitchspellsListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        WitchspellsBusinessService.removeWitchspellsListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.homepage_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_notify_bug:
                startActivity(IntentsUtils.navigateToSendBugMail(getActivity()));
                break;

            case R.id.action_about_us:
                BindingDialogFragment.newInstance(new AboutUsViewModel()).show(getFragmentManager(), "");
                break;
        }
        return super.onOptionsItemSelected(item);
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

        if(mFirstLoad){
            updateHomePageElements(0);
            mFirstLoad = false;
        }
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

    @Override
    public void onWitchspellsUpdated() {
        getLoaderManager().restartLoader(LOADER_ID, null, this);
    }

    public interface Callbacks extends
            BookSubviewViewModel.Listener,
            WitchspellsBookViewModel.Listener,
            WitchspellsAddViewModel.Listener,
            SpellbookViewModel.Listener {
    }
}
