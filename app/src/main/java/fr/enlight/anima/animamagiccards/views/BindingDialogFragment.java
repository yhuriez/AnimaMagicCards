package fr.enlight.anima.animamagiccards.views;

import android.app.DialogFragment;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
public abstract class BindingDialogFragment extends DialogFragment implements DismissDialogListener {

    private DialogViewModel viewModel;
    private ViewDataBinding binding;

    public abstract DialogViewModel createViewModel();

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        viewModel = createViewModel();
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
