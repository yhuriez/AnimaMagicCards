package fr.enlight.anima.animamagiccards.ui.witchspells;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.databinding.FragmentWitchspellsCreationBinding;
import fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels.WitchspellsEditionViewModel;
import fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels.WitchspellsPathViewModel;
import fr.enlight.anima.cardmodel.model.witchspells.Witchspells;

public class WitchspellsEditionFragment extends Fragment implements WitchspellsEditionViewModel.Listener {

    private static final String WITCHSPELLS_PARAM = "WITCHSPELLS_PARAM";

    private FragmentWitchspellsCreationBinding mBinding;
    private WitchspellsEditionViewModel mEditionViewModel;

    public static WitchspellsEditionFragment newCreationInstance() {
        return new WitchspellsEditionFragment();
    }

    public static WitchspellsEditionFragment newEditionInstance(Witchspells witchspells) {
        Bundle args = new Bundle();
        args.putParcelable(WITCHSPELLS_PARAM, witchspells);

        WitchspellsEditionFragment fragment = new WitchspellsEditionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_witchspells_creation, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mEditionViewModel = new WitchspellsEditionViewModel(this);

        mBinding.setModel(mEditionViewModel);
    }

    @Override
    public void onAddNewPath() {

    }

    @Override
    public void onEditPath(WitchspellsPathViewModel pathViewModel) {

    }
}
