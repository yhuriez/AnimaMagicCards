package fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels.freeaccess;

import android.app.DialogFragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.async.FreeAccessSpellLoader;
import fr.enlight.anima.animamagiccards.ui.spells.SpellSelectionActivity;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.animamagiccards.views.viewmodels.RecyclerViewModel;
import fr.enlight.anima.cardmodel.model.spells.Spell;
import fr.enlight.anima.cardmodel.model.witchspells.WitchspellsPath;
import fr.enlight.anima.cardmodel.utils.SpellUtils;

public class WitchspellsFreeAccessChooserFragment extends DialogFragment implements
        WitchspellsSpellFreeAccessViewModel.Listener,
        LoaderManager.LoaderCallbacks<Map<Integer, Spell>>{

    private static final int SPELL_STACK_REQUEST_CODE = 285;

    private static final String WITCHSPELLS_PATH_PARAM = "WITCHSPELLS_PATH_PARAM";

    private ViewDataBinding mBinding;
    private Listener mListener;

    private WitchspellsPath mWitchspellsPath;
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle arguments = getArguments();
        mWitchspellsPath = arguments.getParcelable(WITCHSPELLS_PATH_PARAM);

        mRecyclerViewModel = new RecyclerViewModel();
        mRecyclerViewModel.setLayoutManager(new LinearLayoutManager(getActivity()));

        getLoaderManager().initLoader(1, null, this);
    }

    @Override
    public Loader<Map<Integer, Spell>> onCreateLoader(int id, Bundle args) {
        return new FreeAccessSpellLoader(getActivity(), mWitchspellsPath);
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

        Map<Integer, Integer> freeAccessSpellsIds = mWitchspellsPath.freeAccessSpellsIds;
        for (Integer spellPosition : freeAccessSpellsIds.keySet()) {
            int freeSpellId = freeAccessSpellsIds.get(spellPosition);
            if(freeSpellId > 0){
                result.add(new WitchspellsSpellFreeAccessViewModel(spellPosition, this));
            } else {
                Spell spell = mLoadedSpell.get(spellPosition);
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

    public interface Listener {
        void onFreeAccessSpellsSelected(int mainPathId, Map<Integer, String> freeAccessSpellsIds);

        void onFreeAccessSpellsValidated(int mainPathId, Map<Integer, String> freeAccessSpellsIds);
    }
}
