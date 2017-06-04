package fr.enlight.anima.animamagiccards.ui.spells;


import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Loader;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.loopeer.cardstack.CardStackView;

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
import fr.enlight.anima.animamagiccards.ui.spells.viewmodels.quickaccess.SpellQuickAccessViewModel;
import fr.enlight.anima.animamagiccards.utils.DeviceUtils;
import fr.enlight.anima.animamagiccards.views.BindingDialogFragment;
import fr.enlight.anima.animamagiccards.views.CustomCardStackView;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.cardmodel.business.SpellFilterFactory;
import fr.enlight.anima.cardmodel.model.spells.Spell;
import fr.enlight.anima.cardmodel.model.spells.SpellActionType;
import fr.enlight.anima.cardmodel.model.spells.SpellType;
import fr.enlight.anima.cardmodel.model.spells.SpellbookType;
import fr.enlight.anima.cardmodel.model.witchspells.Witchspells;


public class SpellStackFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Spell>>,
        SpellViewModel.Listener, SearchView.OnQueryTextListener, SpellQuickAccessViewModel.Listener, CardStackView.ItemExpendListener, CustomCardStackView.ItemSelectionListener {

    private static final String EFFECT_DIALOG = "EFFECT_DIALOG";
    private static final String GRADE_DIALOG = "GRADE_DIALOG";

    private static final String SPELLBOOK_ID = "SPELLBOOK_ID";
    private static final String WITCHSPELLS_PARAM = "WITCHSPELLS_PARAM";

    private static final String FREE_ACCESS_LIMIT_PARAM = "FREE_ACCESS_LIMIT_PARAM";
    private static final String SELECTION_MODE_PARAM = "SELECTION_MODE_PARAM";

    private FragmentSpellsStackBinding binding;

    private SpellStackViewModel spellViewModels;
    private SpellFilterViewModel filterViewModel;
    private SpellQuickAccessViewModel quickAccessViewModel;

    private final List<SpellFilterFactory.SpellFilter> filters = new ArrayList<>();
    private SpellFilterFactory.SpellFilter mQuickAccessFilter;

    private boolean selectionMode;
    private boolean spellExpanded;
    private Spell mLastSelectedSpell;

    private SearchView mSearchView;
    private MenuItem searchMenuItem;

    private Handler handler = new Handler();

    private Listener mListener;


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
        bundle.putParcelable(WITCHSPELLS_PARAM, witchspells);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static SpellStackFragment newInstanceForSelection(int freeAccessLimit) {
        SpellStackFragment fragment = new SpellStackFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(FREE_ACCESS_LIMIT_PARAM, freeAccessLimit);
        bundle.putBoolean(SELECTION_MODE_PARAM, true);
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Toolbar
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            modifyTitle(actionBar, getArguments());
        }
        setHasOptionsMenu(true);

        spellViewModels = new SpellStackViewModel(this, this, getArguments().containsKey(WITCHSPELLS_PARAM));
        binding.setModel(spellViewModels);

        filterViewModel = ViewModelProviders.of((FragmentActivity) getActivity()).get(SpellFilterViewModel.class);
        binding.setFilterModel(filterViewModel);

        selectionMode = getArguments().getBoolean(SELECTION_MODE_PARAM);

        if (getArguments().containsKey(FREE_ACCESS_LIMIT_PARAM)) {
            quickAccessViewModel = new SpellQuickAccessViewModel(getArguments().getInt(FREE_ACCESS_LIMIT_PARAM), this);

        } else if (getArguments().getInt(SPELLBOOK_ID) == SpellbookType.FREE_ACCESS.bookId) {
            quickAccessViewModel = new SpellQuickAccessViewModel(100, this);
        }

        if (quickAccessViewModel != null) {
            quickAccessViewModel.setLayoutManager(new LinearLayoutManager(getActivity()));
            mQuickAccessFilter = quickAccessViewModel.getCurrentQuickAccessFilter();
            binding.setQuickAccessModel(quickAccessViewModel);
        }

        reloadSpellFilters();
    }

    private void modifyTitle(ActionBar actionBar, Bundle arguments) {
        String title = null;
        if (arguments.containsKey(WITCHSPELLS_PARAM)) {
            Witchspells witchspells = arguments.getParcelable(WITCHSPELLS_PARAM);
            title = getString(R.string.Witchspells_Name_Format, witchspells.witchName);

        } else if (arguments.containsKey(SPELLBOOK_ID)) {
            SpellbookType spellbookType = SpellbookType.getTypeFromBookId(arguments.getInt(SPELLBOOK_ID));
            title = getString(spellbookType.titleRes);

        } else if (arguments.containsKey(FREE_ACCESS_LIMIT_PARAM)) {
            if (selectionMode && spellExpanded) {
                title = getString(R.string.Witchspells_Choose_Spell_Confirm);
            } else {
                int levelLimit = arguments.getInt(FREE_ACCESS_LIMIT_PARAM);
                title = getString(R.string.Witchspells_Choose_Spell_Title, levelLimit);
            }
        }

        actionBar.setTitle(title);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Listener) {
            mListener = (Listener) context;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof Listener) {
            mListener = (Listener) activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        if (selectionMode && spellExpanded) {
            inflater.inflate(R.menu.validate_selection_menu, menu);

        } else {
            inflater.inflate(R.menu.search_menu, menu);

            searchMenuItem = menu.findItem(R.id.action_search);
            mSearchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
            mSearchView.setSubmitButtonEnabled(true);
            mSearchView.setOnQueryTextListener(this);

            MenuItemCompat.setOnActionExpandListener(searchMenuItem, new MenuItemCompat.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionExpand(MenuItem item) {
                    filterViewModel.filterPanelVisible.set(true);
                    getActivity().invalidateOptionsMenu();

                    // Trick to make the keyboard not showing on search view expand
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            DeviceUtils.hideSoftKeyboard(getActivity().getCurrentFocus());
                            CharSequence searchQuery = filterViewModel.getSearchQuery();
                            if (searchQuery != null) {
                                mSearchView.setQuery(searchQuery, false);
                            }
                        }
                    }, 10);

                    return true;
                }

                @Override
                public boolean onMenuItemActionCollapse(MenuItem item) {
                    filterViewModel.filterPanelVisible.set(false);
                    getActivity().invalidateOptionsMenu();
                    return true;
                }
            });

            if (filterViewModel.filterPanelVisible.get()) {
                inflater.inflate(R.menu.validate_filter_menu, menu);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_validate_filter) {
            filterViewModel.setSearchQuery(mSearchView.getQuery());
            reloadSpellFilters();
            searchMenuItem.collapseActionView();

        } else if (item.getItemId() == R.id.action_validate_spell) {
            if (mLastSelectedSpell == null) {
                throw new IllegalStateException("Last selected spell should not be null at this point");
            }
            mListener.onSpellSelected(mLastSelectedSpell);
        }
        return super.onOptionsItemSelected(item);
    }

    // //////////////
    // region Filters
    // //////////////

    @Override
    public boolean onQueryTextSubmit(String query) {
        filterViewModel.setSearchQuery(query);
        reloadSpellFilters();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }


    private void reloadSpellFilters() {
        filters.clear();

        SpellFilterFactory spellFilterFactory = new SpellFilterFactory();

        CharSequence searchQuery = filterViewModel.getSearchQuery();
        if (!TextUtils.isEmpty(searchQuery)) {
            filters.add(spellFilterFactory.createSearchSpellFilter(searchQuery.toString(), filterViewModel.isSearchWitDesc()));
        }

        List<SpellType> spellTypeList = filterViewModel.getSelectedSpellTypes();
        if (spellTypeList != null && !spellTypeList.isEmpty()) {
            filters.add(spellFilterFactory.createTypeSpellFilter(spellTypeList));
        }

        int intelligenceMaxValue = filterViewModel.getIntelligenceMaxValue();
        if (intelligenceMaxValue > 0) {
            filters.add(spellFilterFactory.createIntelligenceSpellFilter(intelligenceMaxValue));
        }

        int zeonMaxValue = filterViewModel.getZeonMaxValue();
        int retentionMaxValue = filterViewModel.getRetentionMaxValue();
        if (zeonMaxValue > 0 || retentionMaxValue > 0) {
            filters.add(spellFilterFactory.createZeonSpellFilter(zeonMaxValue, retentionMaxValue, filterViewModel.isDailyRetentionOnly()));
        }

        SpellActionType spellActionType = filterViewModel.getSpellActionType();
        if (spellActionType != null) {
            filters.add(spellFilterFactory.createActionTypeSpellFilter(spellActionType));
        }

        getLoaderManager().restartLoader(1, getArguments(), this);
    }

    // endregion

    @Override
    public Loader<List<Spell>> onCreateLoader(int id, Bundle args) {
        spellViewModels.stackVisible.set(false);

        if (args.containsKey(SPELLBOOK_ID)) {
            return new SpellsLoader(getActivity(), args.getInt(SPELLBOOK_ID), filters, mQuickAccessFilter);

        } else if (args.containsKey(WITCHSPELLS_PARAM)) {
            return new SpellsLoader(getActivity(), (Witchspells) args.getParcelable(WITCHSPELLS_PARAM), filters, mQuickAccessFilter);

        } else if (args.containsKey(FREE_ACCESS_LIMIT_PARAM)) {
            return new SpellsLoader(getActivity(), SpellbookType.FREE_ACCESS.bookId, filters, mQuickAccessFilter);
        }
        throw new IllegalStateException("A param should be given to this fragment");
    }

    @Override
    public void onLoadFinished(Loader<List<Spell>> loader, List<Spell> data) {
        List<BindableViewModel> result = new ArrayList<>();
        for (Spell spell : data) {
            SpellViewModel spellViewModel = new SpellViewModel(spell, spell.spellbookType);
            spellViewModel.setListener(this);
            spellViewModel.setReduced(quickAccessViewModel != null);
            result.add(spellViewModel);
        }

        spellViewModels.setViewModels(result);
        spellViewModels.stackVisible.set(true);
    }

    @Override
    public void onLoaderReset(Loader<List<Spell>> loader) {
        spellViewModels.setViewModels(null);
    }

    @Override
    public void onItemExpend(boolean expend) {
        if (selectionMode) {
            spellExpanded = expend;

            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            modifyTitle(actionBar, getArguments());

            getActivity().invalidateOptionsMenu();
        }
    }

    @Override
    public void onQuickAccessSelected(SpellFilterFactory.SpellFilter quickAccessFilter) {
        this.mQuickAccessFilter = quickAccessFilter;
        getLoaderManager().restartLoader(1, getArguments(), this);
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

    @Override
    public void onItemSelected(int position) {
        if (position >= 0) {
            List<BindableViewModel> viewModels = spellViewModels.getViewModels();
            if (viewModels != null && !viewModels.isEmpty()) {
                SpellViewModel spellViewModel = (SpellViewModel) viewModels.get(position);
                mLastSelectedSpell = spellViewModel.getSpell();
            }

        } else {
            mLastSelectedSpell = null;
        }
    }

    public interface Listener {
        void onSpellSelected(Spell mLastSelectedSpell);
    }
}