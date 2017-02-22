package fr.enlight.anima.animamagiccards.utils.bindingrecyclerview;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 *
 */

public class BindingRecyclerViewHolder extends RecyclerView.ViewHolder {

    private final ViewDataBinding binding;

    public BindingRecyclerViewHolder(ViewDataBinding viewBinding) {
        super(viewBinding.getRoot());
        binding = viewBinding;
    }

    public void setViewModel(BindableViewModel viewModel) {
        binding.setVariable(viewModel.getVariableId(), viewModel);
        binding.executePendingBindings();
    }
}
