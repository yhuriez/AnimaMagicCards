package fr.enlight.anima.animamagiccards.ui.witchspells;

import android.annotation.SuppressLint;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.SparseArray;

import com.android.databinding.library.baseAdapters.BR;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.async.SpellbooksLoader;
import fr.enlight.anima.animamagiccards.databinding.ActivityWitchspellsPathChooserBinding;
import fr.enlight.anima.animamagiccards.ui.AnimaBaseActivity;
import fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels.WitchspellsMainPathChooserListener;
import fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels.WitchspellsMainSpellbookViewModel;
import fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels.freeaccess.WitchspellsFreeAccessChooserFragment;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.animamagiccards.views.viewmodels.RecyclerViewModel;
import fr.enlight.anima.cardmodel.model.spells.Spellbook;
import fr.enlight.anima.cardmodel.model.spells.SpellbookType;
import fr.enlight.anima.cardmodel.model.witchspells.WitchspellsPath;
import fr.enlight.anima.cardmodel.utils.SpellUtils;

public class WitchspellsMainPathChooserActivity extends AnimaBaseActivity implements
        WitchspellsMainSpellbookViewModel.Listener,
        WitchspellsSecondaryPathChooserFragment.Listener,
        LoaderManager.LoaderCallbacks<List<Spellbook>>,
        WitchspellsMainPathChooserListener,
        WitchspellsFreeAccessChooserFragment.Listener {

    public static final String WITCHSPELLS_PATHS_RESULT = "WITCHSPELLS_PATHS_PARAM";

    private static final String WITCHSPELLS_PATHS_PARAM = "WITCHSPELLS_PATHS_PARAM";
    private static final String SECONDARY_DIALOG_FRAGMENT_TAG = "SECONDARY_DIALOG_FRAGMENT_TAG";
    private static final String FREE_ACCESS_DIALOG_FRAGMENT_TAG = "FREE_ACCESS_DIALOG_FRAGMENT_TAG";

    private ActivityWitchspellsPathChooserBinding mBinding;

    private RecyclerViewModel mRecyclerViewModel;

    private List<Spellbook> mSpellbooks;
    private SparseArray<Spellbook> mSpellbookMapping;

    @SuppressLint("UseSparseArrays")
    // Key is the spellbook related id
    private final Map<Integer, WitchspellsPath> mWitchspellsPathMap = new HashMap<>();


    public static Intent navigateForEdition(Context context, ArrayList<WitchspellsPath> witchspells) {
        Intent intent = new Intent(context, WitchspellsMainPathChooserActivity.class);
        intent.putParcelableArrayListExtra(WITCHSPELLS_PATHS_PARAM, witchspells);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_witchspells_path_chooser);

        ArrayList<WitchspellsPath> witchspellsPaths = getIntent().getParcelableArrayListExtra(WITCHSPELLS_PATHS_PARAM);
        if(witchspellsPaths == null) {
            throw new IllegalStateException("Witchspell should not be null at this point");
        }
        for (WitchspellsPath path : witchspellsPaths) {
            mWitchspellsPathMap.put(path.pathBookId, path);
        }

        mRecyclerViewModel = new RecyclerViewModel();
        mRecyclerViewModel.setLayoutManager(new LinearLayoutManager(this));

        mBinding.setListener(this);

        getLoaderManager().initLoader(1, null, this);
    }

    @Override
    public Loader<List<Spellbook>> onCreateLoader(int id, Bundle args) {
        return new SpellbooksLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Spellbook>> loader, List<Spellbook> spellbooks) {
        mSpellbooks = spellbooks;
        mSpellbookMapping = new SparseArray<>();
        for (Spellbook spellbook : spellbooks) {
            mSpellbookMapping.append(spellbook.bookId, spellbook);
        }

        refreshViewModels();
    }

    @Override
    public void onLoaderReset(Loader<List<Spellbook>> loader) {
        // Nothing to do
    }

    public void refreshViewModels(){
        List<BindableViewModel> result = new ArrayList<>();

        for (Spellbook spellbook : mSpellbooks) {
            SpellbookType bookType = spellbook.spellbookType;

            if(bookType != null && SpellbookType.MAIN_SPELLBOOKS.contains(bookType)){
                WitchspellsPath pathForMainBook = mWitchspellsPathMap.get(spellbook.bookId);

                WitchspellsMainSpellbookViewModel viewModel = new WitchspellsMainSpellbookViewModel(spellbook, pathForMainBook,this);

                result.add(viewModel);
            }
        }

        mRecyclerViewModel.setViewModels(result);
        mBinding.setVariable(BR.model, mRecyclerViewModel);
    }

    // region Callbacks

    @Override
    public void onWitchspellPathUpdated(@NonNull WitchspellsPath witchspellsPath) {
        int pathBookId = witchspellsPath.pathBookId;

        if(witchspellsPath.pathLevel > 0) {
            mWitchspellsPathMap.put(pathBookId, witchspellsPath);

        } else if (mWitchspellsPathMap.containsKey(pathBookId)){
            mWitchspellsPathMap.remove(pathBookId);
        }
    }

    @Override
    public void onShowSecondarySpellbookForMainPath(Spellbook mainSpellbook) {
        ArrayList<Spellbook> spellbooks = SpellUtils.extractSecondarySpellbooks(this, mainSpellbook, mSpellbookMapping);

        WitchspellsSecondaryPathChooserFragment.newInstance(spellbooks, mainSpellbook.bookId)
                .show(getFragmentManager(), SECONDARY_DIALOG_FRAGMENT_TAG);
    }

    @Override
    public void onShowFreeAccessSpells(Spellbook mainSpellbook) {
        WitchspellsPath witchspellsPath = mWitchspellsPathMap.get(mainSpellbook.bookId);
        if(witchspellsPath.freeAccessSpellsIds == null){
            witchspellsPath.freeAccessSpellsIds = SpellUtils.generateDefaultFreeAccessMap(mainSpellbook, witchspellsPath);
        }

        WitchspellsFreeAccessChooserFragment.newInstance(witchspellsPath)
                .show(getFragmentManager(), FREE_ACCESS_DIALOG_FRAGMENT_TAG);
    }

    @Override
    public void onSecondaryPathChosen(int mainPathId, SpellbookType secondaryBookSelectedType) {
        WitchspellsPath witchspellsPath = mWitchspellsPathMap.get(mainPathId);
        if(witchspellsPath == null){
            throw new IllegalStateException("Witchspells path should not be nut at this point");
        }

        witchspellsPath.secondaryPathBookId = secondaryBookSelectedType.bookId;
        refreshViewModels();
    }

    @Override
    public void onValidationClicked() {
        ArrayList<WitchspellsPath> witchspellsPaths = new ArrayList<>(mWitchspellsPathMap.values());
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(WITCHSPELLS_PATHS_RESULT, witchspellsPaths);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onFreeAccessSpellsValidated(int mainPathId, Map<Integer, Integer> freeAccessSpellsIds) {
        WitchspellsPath witchspellsPath = mWitchspellsPathMap.get(mainPathId);
        witchspellsPath.freeAccessSpellsIds = freeAccessSpellsIds;
        refreshViewModels();
    }

    // endregion
}
