package fr.enlight.anima.animamagiccards.ui.witchspells;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;

import java.util.ArrayList;
import java.util.List;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.loaders.SpellbooksLoader;
import fr.enlight.anima.animamagiccards.utils.SpellbookUtils;
import fr.enlight.anima.cardmodel.model.spells.SpellbookType;
import fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels.WitchspellsMainSpellbookViewModel;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.animamagiccards.views.viewmodels.RecyclerViewModel;
import fr.enlight.anima.cardmodel.model.spells.Spellbook;
import fr.enlight.anima.cardmodel.model.witchspells.Witchspells;
import fr.enlight.anima.cardmodel.model.witchspells.WitchspellsPath;

public class WitchspellsMainPathChooserFragment extends Fragment implements WitchspellsMainSpellbookViewModel.Listener, LoaderManager.LoaderCallbacks<List<Spellbook>> {

    private static final String WITCHSPELL_PARAM = "WITCHSPELL_PARAM";

    private static final String SECONDARY_DIALOG_FRAGMENT_TAG = "SECONDARY_DIALOG_FRAGMENT_TAG";

    private ViewDataBinding mBinding;
    private Listener mListener;

    private Witchspells mWitchspells;

    private RecyclerViewModel mRecyclerViewModel;

    private SparseArray<Spellbook> mSpellbookMapping;


    public static WitchspellsMainPathChooserFragment newMainSpellbookInstance(Witchspells witchspells) {
        Bundle args = new Bundle();
        args.putParcelable(WITCHSPELL_PARAM, witchspells);

        WitchspellsMainPathChooserFragment fragment = new WitchspellsMainPathChooserFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_witchspells_path_chooser, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (Listener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(getArguments() != null){
            mWitchspells = getArguments().getParcelable(WITCHSPELL_PARAM);
        } else {
            mWitchspells = new Witchspells();
        }

        mRecyclerViewModel = new RecyclerViewModel();
        mRecyclerViewModel.setLayoutManager(new LinearLayoutManager(getActivity()));

        getLoaderManager().initLoader(1, null, this);
    }

    @Override
    public Loader<List<Spellbook>> onCreateLoader(int id, Bundle args) {
        return new SpellbooksLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<Spellbook>> loader, List<Spellbook> spellbooks) {
        initViewModels(spellbooks);
    }

    @Override
    public void onLoaderReset(Loader<List<Spellbook>> loader) {
        // Nothing to do
    }

    private void initViewModels(List<Spellbook> spellbooksIndex) {
        List<BindableViewModel> result = new ArrayList<>();

        mSpellbookMapping = new SparseArray<>();
        for (Spellbook spellbook : spellbooksIndex) {
            mSpellbookMapping.append(spellbook.bookId, spellbook);
        }

        for (Spellbook spellbook : spellbooksIndex) {
            SpellbookType bookType = spellbook.spellbookType;
            if(bookType != null && SpellbookType.MAIN_SPELLBOOKS.contains(bookType)){
                WitchspellsPath pathForMainBook = mWitchspells.getPathForMainBook(spellbook.bookId);
                WitchspellsMainSpellbookViewModel viewModel;
                if(pathForMainBook != null && pathForMainBook.secondaryPathBookId > 0){
                    Spellbook secondarySpellbook = mSpellbookMapping.get(pathForMainBook.secondaryPathBookId);
                    viewModel = new WitchspellsMainSpellbookViewModel(spellbook, bookType, this, secondarySpellbook);
                } else {
                    viewModel = new WitchspellsMainSpellbookViewModel(spellbook, bookType, this);
                }
                result.add(viewModel);
            }
        }

        mRecyclerViewModel.setViewModels(result);
        mBinding.setVariable(BR.model, mRecyclerViewModel);
    }

    // region Main spellbook mode

    @Override
    public void onSpellbookSelected(Spellbook spellbook) {

    }

    @Override
    public void onSpellbookLevelSelected(Spellbook spellbook, int levelSelected) {

    }

    @Override
    public void onShowSecondarySpellbookForMainPath(Spellbook spellbook) {
        ArrayList<Spellbook> spellbooks = SpellbookUtils.extractSecondarySpellbooks(getActivity(), spellbook, mSpellbookMapping);

        WitchspellsSecondaryPathChooserFragment.newInstance(spellbooks)
                .show(getFragmentManager(), SECONDARY_DIALOG_FRAGMENT_TAG);

    }

    // endregion


    public interface Listener extends WitchspellsMainSpellbookViewModel.Listener {
        void onMainPathsChosen(Witchspells witchspells);
    }
}
