package fr.enlight.anima.animamagiccards.ui.witchspells;

import android.app.DialogFragment;
import android.content.Context;
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

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels.WitchspellsMainSpellbookViewModel;
import fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels.WitchspellsSecondarySpellbookViewModel;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.animamagiccards.views.viewmodels.RecyclerViewModel;
import fr.enlight.anima.cardmodel.model.spells.Spellbook;
import fr.enlight.anima.cardmodel.model.witchspells.Witchspells;

public class WitchspellsSecondaryPathChooserFragment extends DialogFragment implements WitchspellsSecondarySpellbookViewModel.Listener {

    private static final String SPELLBOOK_LIST_PARAM = "SPELLBOOK_LIST_PARAM";

    private ViewDataBinding mBinding;
    private Listener mListener;

    private List<Spellbook> mSecondarySpellbooks;

    private RecyclerViewModel mRecyclerViewModel;


    public static WitchspellsSecondaryPathChooserFragment newInstance(ArrayList<Spellbook> secondarySpellbooks) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(SPELLBOOK_LIST_PARAM, secondarySpellbooks);

        WitchspellsSecondaryPathChooserFragment fragment = new WitchspellsSecondaryPathChooserFragment();
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

        mSecondarySpellbooks = getArguments().getParcelable(SPELLBOOK_LIST_PARAM);

        mRecyclerViewModel = new RecyclerViewModel();
        mRecyclerViewModel.setLayoutManager(new LinearLayoutManager(getActivity()));

        initViewModels();
    }

    private void initViewModels() {
        List<BindableViewModel> result = new ArrayList<>();

        for (Spellbook spellbook : mSecondarySpellbooks) {
            result.add(new WitchspellsSecondarySpellbookViewModel(spellbook, this));
        }

        mRecyclerViewModel.setViewModels(result);
        mBinding.setVariable(BR.model, mRecyclerViewModel);
    }

    @Override
    public void onSelectedSecondaryPath(Spellbook spellbook) {

    }


    public interface Listener extends WitchspellsMainSpellbookViewModel.Listener {
        void onSecondaryPathChosen(Witchspells witchspells);
    }
}
