package fr.enlight.anima.animamagiccards.views.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.util.List;

import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;

/**
 *
 */
public class ListBindableViewModel extends BaseObservable {

    private List<BindableViewModel> viewModels;

    @Bindable
    public List<BindableViewModel> getViewModels() {
        return viewModels;
    }

    public void setViewModels(List<BindableViewModel> viewModels) {
        this.viewModels = viewModels;
        notifyChange();
    }
}
