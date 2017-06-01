package fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels.freeaccess;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.android.databinding.library.baseAdapters.BR;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.async.FreeAccessSpellLoader;
import fr.enlight.anima.animamagiccards.databinding.FragmentWitchspellsFreeAccessChooserBinding;
import fr.enlight.anima.animamagiccards.ui.spells.SpellSelectionActivity;
import fr.enlight.anima.animamagiccards.utils.ValidationListener;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.animamagiccards.views.viewmodels.RecyclerViewModel;
import fr.enlight.anima.cardmodel.model.spells.Spell;
import fr.enlight.anima.cardmodel.model.witchspells.WitchspellsPath;

public class WitchspellsFreeAccessChooserFragment extends DialogFragment implements
        WitchspellsSpellFreeAccessViewModel.Listener,
        ValidationListener,
        LoaderManager.LoaderCallbacks<Map<Integer, Spell>>{

    private static final int SPELL_STACK_REQUEST_CODE = 285;

    private static final String WITCHSPELLS_PATH_PARAM = "WITCHSPELLS_PATH_PARAM";

    private FragmentWitchspellsFreeAccessChooserBinding mBinding;
    private Listener mListener;

    private int mainPathId;
    private Map<Integer, Integer> mFreeAccessMap;
    private Map<Integer, Spell> mLoadedSpell;

    private RecyclerViewModel mRecyclerViewModel;



    public static WitchspellsFreeAccessChooserFragment newInstance(WitchspellsPath witchspellsPath) {
        Bundle args = new Bundle();
        args.putParcelable(WITCHSPELLS_PATH_PARAM, witchspellsPath);

        WitchspellsFreeAccessChooserFragment fragment = new WitchspellsFreeAccessChooserFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_witchspells_free_access_chooser, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (Listener) context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (Listener) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @SuppressLint("UseSparseArrays")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Window window = getDialog().getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);

        Bundle arguments = getArguments();
        WitchspellsPath witchspellsPath = arguments.getParcelable(WITCHSPELLS_PATH_PARAM);

        mFreeAccessMap = new HashMap<>(witchspellsPath.freeAccessSpellsIds);
        mainPathId = witchspellsPath.pathBookId;


        mRecyclerViewModel = new RecyclerViewModel();
        mRecyclerViewModel.setLayoutManager(new LinearLayoutManager(getActivity()));

        mBinding.setListener(this);

        getLoaderManager().initLoader(1, null, this);
    }

    @Override
    public Loader<Map<Integer, Spell>> onCreateLoader(int id, Bundle args) {
        return new FreeAccessSpellLoader(getActivity(), mFreeAccessMap);
    }

    @Override
    public void onLoadFinished(Loader<Map<Integer, Spell>> loader, Map<Integer, Spell> data) {
        mLoadedSpell = data;
        initViewModels();
    }

    @Override
    public void onLoaderReset(Loader<Map<Integer, Spell>> loader) {
    }

    private void initViewModels() {
        List<BindableViewModel> result = new ArrayList<>();

        for (Integer spellPosition : mLoadedSpell.keySet()) {
            Spell spell = mLoadedSpell.get(spellPosition);
            if(spell == null){
                result.add(new WitchspellsSpellFreeAccessViewModel(spellPosition, this));
            } else {
                result.add(new WitchspellsSpellFreeAccessViewModel(spellPosition, spell, this));
            }
        }

        mRecyclerViewModel.setViewModels(result);
        mBinding.setVariable(BR.model, mRecyclerViewModel);
    }

    @Override
    public void onSelectedFreeAccessAvailableSlot(int ceilingLevel) {
        startActivityForResult(SpellSelectionActivity.navigate(getActivity(), ceilingLevel), SPELL_STACK_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SPELL_STACK_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            int spellId = data.getIntExtra(SpellSelectionActivity.SELECTED_SPELL_RESULT, -1);
            int spellPosition = data.getIntExtra(SpellSelectionActivity.FREE_ACCESS_POSITION_PARAM, -1);
            if(spellPosition < 0){
                throw new IllegalStateException("Spell position should not be null at this point");
            }

            mFreeAccessMap.put(spellPosition, spellId);
            getLoaderManager().restartLoader(1, getArguments(), this);
        }
    }

    @Override
    public void onValidateClicked() {
        mListener.onFreeAccessSpellsValidated(mainPathId, mFreeAccessMap);
        dismissAllowingStateLoss();
    }

    public interface Listener {
        void onFreeAccessSpellsValidated(int mainPathId, Map<Integer, Integer> freeAccessSpellsIds);
    }
}
