package fr.enlight.anima.animamagiccards.ui.spellbooks;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tuyenmonkey.mkloader.MKLoader;

import java.util.List;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.databinding.ActivitySpellsStackBinding;
import fr.enlight.anima.animamagiccards.loaders.SpellsLoader;
import fr.enlight.anima.animamagiccards.ui.spellbooks.utils.SpellbookType;
import fr.enlight.anima.animamagiccards.ui.spellbooks.viewmodels.DialogSpellEffectViewModel;
import fr.enlight.anima.animamagiccards.ui.spellbooks.viewmodels.DialogSpellGradeViewModel;
import fr.enlight.anima.animamagiccards.ui.spellbooks.viewmodels.SpellStackViewModel;
import fr.enlight.anima.animamagiccards.ui.spellbooks.viewmodels.SpellViewModel;
import fr.enlight.anima.animamagiccards.views.BindingDialogFragment;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;


public class SpellStackFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<BindableViewModel>>, SpellViewModel.Listener {

    private static final String EFFECT_DIALOG = "EFFECT_DIALOG";
    private static final String GRADE_DIALOG = "GRADE_DIALOG";


    private static final String SPELLBOOK_ID = "SPELLBOOK_ID";
    private static final String SPELLBOOK_TYPE = "SPELLBOOK_TYPE";

    private ActivitySpellsStackBinding binding;

    private SpellStackViewModel spellViewModels;
    private SpellbookType spellbookType;

    private View mLoadingOverlay;
    private MKLoader mProgress;

    public static SpellStackFragment newInstance(int spellbookId, SpellbookType type) {
        SpellStackFragment fragment = new SpellStackFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(SPELLBOOK_ID, spellbookId);
        bundle.putString(SPELLBOOK_TYPE, type.name());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_spells_stack, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLoadingOverlay = view.findViewById(R.id.loading_overlay);
        mProgress = (MKLoader) view.findViewById(R.id.progress);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String bookType = getArguments().getString(SPELLBOOK_TYPE);
        if (bookType != null) {
            spellbookType = SpellbookType.valueOf(bookType);
        }

        spellViewModels = new SpellStackViewModel(spellbookType);
        binding.setModel(spellViewModels);

        getLoaderManager().initLoader(1, getArguments(), this);
    }

    @Override
    public Loader<List<BindableViewModel>> onCreateLoader(int id, Bundle args) {
        if (args.containsKey(SPELLBOOK_ID)) {
            return new SpellsLoader(getActivity(), args.getInt(SPELLBOOK_ID), spellbookType, this);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<BindableViewModel>> loader, List<BindableViewModel> data) {
        spellViewModels.setViewModels(data);

        mLoadingOverlay.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<List<BindableViewModel>> loader) {
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
