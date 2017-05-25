package fr.enlight.anima.animamagiccards.views.viewmodels;

import com.android.databinding.library.baseAdapters.BR;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;


public class EmptyItemViewModel implements BindableViewModel {

    @Override
    public int getLayoutRes() {
        return R.layout.view_book_empty_item;
    }

    @Override
    public int getVariableId() {
        return BR.model;
    }
}
