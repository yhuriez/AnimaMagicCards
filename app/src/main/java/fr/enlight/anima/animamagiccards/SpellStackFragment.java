package fr.enlight.anima.animamagiccards;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopeer.cardstack.CardStackView;

import java.util.List;

import fr.enlight.anima.animamagiccards.databinding.ActivitySpellsStackBinding;
import fr.enlight.anima.animamagiccards.loaders.SpellsLoader;
import fr.enlight.anima.animamagiccards.utils.SpellbookType;
import fr.enlight.anima.animamagiccards.utils.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.animamagiccards.viewmodels.ListBindableViewModel;

/**
 *
 */
public class SpellStackFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<BindableViewModel>>, CardStackView.ItemExpendListener {

    private static final String SPELLBOOK_ID = "SPELLBOOK_ID";
    private static final String SPELLBOOK_TYPE = "SPELLBOOK_TYPE";

    private ActivitySpellsStackBinding binding;

    private ListBindableViewModel spellViewModels;
    private SpellbookType spellbookType;

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String bookType = getArguments().getString(SPELLBOOK_TYPE);
        if (bookType != null) {
            spellbookType = SpellbookType.valueOf(bookType);
        }

        spellViewModels = new ListBindableViewModel();
        binding.setModel(spellViewModels);
        binding.setListener(this);
        if (spellbookType != null) {
            binding.setSpellbookType(spellbookType);
        }

        getLoaderManager().initLoader(1, getArguments(), this);
    }

    @Override
    public Loader<List<BindableViewModel>> onCreateLoader(int id, Bundle args) {
        if (args.containsKey(SPELLBOOK_ID)) {
            return new SpellsLoader(getActivity(), args.getInt(SPELLBOOK_ID), spellbookType);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<BindableViewModel>> loader, List<BindableViewModel> data) {
        spellViewModels.setViewModels(data);
    }

    @Override
    public void onLoaderReset(Loader<List<BindableViewModel>> loader) {
    }

    @Override
    public void onItemExpend(boolean expend) {

    }
}
