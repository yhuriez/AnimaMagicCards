package fr.enlight.anima.animamagiccards.ui.witchspells;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.databinding.FragmentWitchspellsListBinding;
import fr.enlight.anima.animamagiccards.loaders.WitchspellsLoader;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.animamagiccards.views.viewmodels.ListBindableViewModel;

public class WitchspellsListFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<BindableViewModel>> {

    private static final int LOADER_ID = 10;

    private FragmentWitchspellsListBinding mBinding;
    private ListBindableViewModel mWitchspellList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_witchspells_list, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mWitchspellList = new ListBindableViewModel();

        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<List<BindableViewModel>> onCreateLoader(int id, Bundle args) {
        return new WitchspellsLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<BindableViewModel>> loader, List<BindableViewModel> data) {
        mWitchspellList.setViewModels(data);
    }

    @Override
    public void onLoaderReset(Loader<List<BindableViewModel>> loader) {
    }
}
