package fr.enlight.anima.animamagiccards.views.stack;

import android.databinding.ViewDataBinding;

import com.loopeer.cardstack.CardStackView;

import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;


public class BindingStackViewHolder extends CardStackView.ViewHolder {

    private ViewDataBinding binding;

    public BindingStackViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void setData(BindableViewModel viewModel){
        binding.setVariable(viewModel.getVariableId(), viewModel);
        binding.executePendingBindings();
    }

    @Override
    public void onItemExpand(boolean expand) {

    }
}
