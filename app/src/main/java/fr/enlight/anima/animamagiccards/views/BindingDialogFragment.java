package fr.enlight.anima.animamagiccards.views;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.enlight.anima.animamagiccards.views.viewmodels.DialogViewModel;
import fr.enlight.anima.animamagiccards.views.viewmodels.DismissDialogListener;

/**
 * Dialog fragment that looks like a popup.
 *
 * @author Alexandre Gianquinto
 */
public class BindingDialogFragment extends DialogFragment implements DismissDialogListener {

    public static final String ARG_VIEW_MODEL = "ARG_VIEW_MODEL";

    private DialogViewModel viewModel;
    private ViewDataBinding binding;

    public static BindingDialogFragment newInstance(DialogViewModel dialogViewModel) {
        final BindingDialogFragment fragment = new BindingDialogFragment();
        final Bundle args = new Bundle();
        args.putSerializable(ARG_VIEW_MODEL, dialogViewModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        viewModel = (DialogViewModel) getArguments().getSerializable(ARG_VIEW_MODEL);
        if(viewModel != null) {
            binding = DataBindingUtil.inflate(inflater, viewModel.getLayoutRes(), container, false);
            return binding.getRoot();
        }
        throw new UnsupportedOperationException("This dialog fragment should have a ViewModel to show");
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        getDialog().getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );

        viewModel.setListener(this);
        binding.setVariable(viewModel.getVariableId(), viewModel);
    }

    @Override
    public void dismissDialog() {
        dismiss();
    }
}
