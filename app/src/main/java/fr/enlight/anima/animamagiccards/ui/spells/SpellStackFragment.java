package fr.enlight.anima.animamagiccards.ui.spells;


import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.async.SpellsLoader;
import fr.enlight.anima.animamagiccards.databinding.FragmentSpellsStackBinding;
import fr.enlight.anima.animamagiccards.ui.spells.viewmodels.DialogSpellEffectViewModel;
import fr.enlight.anima.animamagiccards.ui.spells.viewmodels.DialogSpellGradeViewModel;
import fr.enlight.anima.animamagiccards.ui.spells.viewmodels.SpellFilterViewModel;
import fr.enlight.anima.animamagiccards.ui.spells.viewmodels.SpellStackViewModel;
import fr.enlight.anima.animamagiccards.ui.spells.viewmodels.SpellViewModel;
import fr.enlight.anima.animamagiccards.views.BindingDialogFragment;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.cardmodel.business.SpellFilterFactory;
import fr.enlight.anima.cardmodel.model.spells.Spell;
import fr.enlight.anima.cardmodel.model.spells.SpellActionType;
import fr.enlight.anima.cardmodel.model.spells.SpellType;
import fr.enlight.anima.cardmodel.model.witchspells.Witchspells;


public class SpellStackFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Spell>>, SpellViewModel.Listener, SearchView.OnQueryTextListener {

    private static final String EFFECT_DIALOG = "EFFECT_DIALOG";
    private static final String GRADE_DIALOG = "GRADE_DIALOG";

    private static final String SPELLBOOK_ID = "SPELLBOOK_ID";
    private static final String WITCHSPELL_PARAM = "WITCHSPELL_PARAM";

    private static final String EMPTY_QUERY = "EMPTY_QUERY";

    private FragmentSpellsStackBinding binding;

    private SpellStackViewModel spellViewModels;
    private SpellFilterViewModel filterViewModel;

    private final List<SpellFilterFactory.SpellFilter> filters = new ArrayList<>();

    private View mLoadingOverlay;
    private SearchView mSearchView;
    private MenuItem searchMenuItem;

    public static SpellStackFragment newInstance(int spellbookId) {
        SpellStackFragment fragment = new SpellStackFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(SPELLBOOK_ID, spellbookId);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static SpellStackFragment newInstance(Witchspells witchspells) {
        SpellStackFragment fragment = new SpellStackFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(WITCHSPELL_PARAM, witchspells);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_spells_stack, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLoadingOverlay = view.findViewById(R.id.loading_overlay);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);

        spellViewModels = new SpellStackViewModel();
        binding.setModel(spellViewModels);

        filterViewModel = new SpellFilterViewModel();
        binding.setFilterModel(filterViewModel);

        getLoaderManager().initLoader(1, getArguments(), this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menu, menu);

        if(filterViewModel.filterPanelVisible.get()){
            inflater.inflate(R.menu.validate_menu, menu);
        }

        searchMenuItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(searchMenuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                filterViewModel.filterPanelVisible.set(true);
                getActivity().invalidateOptionsMenu();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                filterViewModel.filterPanelVisible.set(false);
                getActivity().invalidateOptionsMenu();
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_validate){
            searchMenuItem.collapseActionView();
            reloadSpellFilters(mSearchView.getQuery());
        }
        return super.onOptionsItemSelected(item);
    }

    // //////////////
    // region Filters
    // //////////////

    @Override
    public boolean onQueryTextSubmit(String query) {
        reloadSpellFilters(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }


    private void reloadSpellFilters(CharSequence textSearchQuery){
        filters.clear();
        SpellFilterFactory spellFilterFactory = new SpellFilterFactory();

        if(!TextUtils.isEmpty(textSearchQuery)){
            filters.add(spellFilterFactory.createSearchSpellFilter(textSearchQuery.toString(), filterViewModel.isSearchWitDesc()));
        }

        List<SpellType> spellType = filterViewModel.getSelectedSpellTypes();
        if(spellType != null){
            filters.add(spellFilterFactory.createTypeSpellFilter(spellType));
        }

        int intelligenceMaxValue = filterViewModel.getIntelligenceMaxValue();
        if(intelligenceMaxValue > 0){
            filters.add(spellFilterFactory.createIntelligenceSpellFilter(intelligenceMaxValue));
        }

        int zeonMaxValue = filterViewModel.getZeonMaxValue();
        int retentionMaxValue = filterViewModel.getRetentionMaxValue();
        if(zeonMaxValue > 0 || retentionMaxValue > 0){
            filters.add(spellFilterFactory.createZeonSpellFilter(zeonMaxValue, retentionMaxValue, filterViewModel.isDailyRetentionOnly()));
        }

        SpellActionType spellActionType = filterViewModel.getSpellActionType();
        if(spellActionType != null){
            filters.add(spellFilterFactory.createActionTypeSpellFilter(spellActionType));
        }

        getLoaderManager().restartLoader(1, getArguments(), this);
    }

    // endregion

    @Override
    public Loader<List<Spell>> onCreateLoader(int id, Bundle args) {
        mLoadingOverlay.setVisibility(View.VISIBLE);

        if (args.containsKey(SPELLBOOK_ID)) {
            return new SpellsLoader(getActivity(), args.getInt(SPELLBOOK_ID), filters);

        } else if(args.containsKey(WITCHSPELL_PARAM)){
            return new SpellsLoader(getActivity(), (Witchspells) args.getParcelable(WITCHSPELL_PARAM), filters);
        }
        throw new IllegalStateException("A param should be given to this fragment");
    }

    @Override
    public void onLoadFinished(Loader<List<Spell>> loader, List<Spell> data) {
        List<BindableViewModel> result = new ArrayList<>();
        for (Spell spell : data) {
            result.add(new SpellViewModel(spell, spell.spellbookType));
        }

        spellViewModels.setViewModels(result);

        mLoadingOverlay.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<List<Spell>> loader) {
    }


    @Override
    public void onEffectClicked(DialogSpellEffectViewModel dialogViewModel) {
        BindingDialogFragment.newInstance(dialogViewModel)
                .show(getFragmentManager(), EFFECT_DIALOG);
    }

    @Override
    public void onGradeClicked(DialogSpellGradeViewModel dialogViewModel) {
        BindingDialogFragment.newInstance(dialogViewModel)
                .show(getFragmentManager(), GRADE_DIALOG);
    }


}
