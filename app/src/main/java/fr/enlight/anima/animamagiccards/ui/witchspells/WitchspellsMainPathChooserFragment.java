package fr.enlight.anima.animamagiccards.ui.witchspells;

import android.app.Fragment;
import android.content.Context;
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
import fr.enlight.anima.animamagiccards.ui.spellbooks.utils.SpellbookType;
import fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels.WitchspellsMainSpellbookViewModel;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.animamagiccards.views.viewmodels.RecyclerViewModel;
import fr.enlight.anima.cardmodel.business.SpellBusinessService;
import fr.enlight.anima.cardmodel.model.spells.Spellbook;
import fr.enlight.anima.cardmodel.model.witchspells.Witchspells;
import fr.enlight.anima.cardmodel.model.witchspells.WitchspellsPath;

public class WitchspellsMainPathChooserFragment extends Fragment implements WitchspellsMainSpellbookViewModel.Listener {

    private static final String WITCHSPELL_PARAM = "WITCHSPELL_PARAM";

    private ViewDataBinding mBinding;
    private Listener mListener;

    private Witchspells mWitchspells;

    private RecyclerViewModel mRecyclerViewModel;

    private SpellBusinessService mSpellBusinessService;


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

        mWitchspells = getArguments().getParcelable(WITCHSPELL_PARAM);

        mSpellBusinessService = new SpellBusinessService(getActivity());

        mRecyclerViewModel = new RecyclerViewModel();
        mRecyclerViewModel.setLayoutManager(new LinearLayoutManager(getActivity()));

        initViewModels();
    }

    private void initViewModels() {
        List<BindableViewModel> result = new ArrayList<>();
        List<Spellbook> spellbooksIndex = mSpellBusinessService.getSpellbooksIndex();

        SparseArray<Spellbook> spellbookMapped = new SparseArray<>();
        for (Spellbook spellbook : spellbooksIndex) {
            spellbookMapped.append(spellbook.bookId, spellbook);
        }

        for (Spellbook spellbook : spellbooksIndex) {
            SpellbookType bookType = SpellbookType.getTypeFromBookId(spellbook.bookId);
            if(bookType != null && SpellbookType.MAIN_SPELLBOOKS.contains(bookType)){
                WitchspellsPath pathForMainBook = mWitchspells.getPathForMainBook(spellbook.bookId);
                WitchspellsMainSpellbookViewModel viewModel;
                if(pathForMainBook.secondaryPathBookId > 0){
                    Spellbook secondarySpellbook = spellbookMapped.get(pathForMainBook.secondaryPathBookId);
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
    public void onSecondarySpellbookSelected(Spellbook mainSpellbook, Spellbook secondarySpellbook) {

    }

    @Override
    public void onSpellbookLevelSelected(Spellbook spellbook, int levelSelected) {

    }

    // endregion


    public interface Listener {
        void onMainPathsChoosen(Witchspells witchspells);
    }
}
