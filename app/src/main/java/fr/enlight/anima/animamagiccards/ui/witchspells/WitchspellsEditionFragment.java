package fr.enlight.anima.animamagiccards.ui.witchspells;


import android.app.Fragment;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.databinding.FragmentWitchspellsCreationBinding;
import fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels.WitchspellsEditionViewModel;
import fr.enlight.anima.cardmodel.model.witchspells.Witchspells;

public class WitchspellsEditionFragment extends Fragment {

    private static final String WITCHSPELLS_PARAM = "WITCHSPELLS_PARAM";

    private FragmentWitchspellsCreationBinding mBinding;

    private Listener mListener;

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
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (Listener) context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mBinding.setModel(new WitchspellsEditionViewModel(mListener));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface Listener extends WitchspellsEditionViewModel.Listener{
    }
}
