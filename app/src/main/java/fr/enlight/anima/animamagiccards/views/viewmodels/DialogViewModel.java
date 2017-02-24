package fr.enlight.anima.animamagiccards.views.viewmodels;


import java.io.Serializable;

import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;

public interface DialogViewModel extends BindableViewModel, Serializable {

    void setListener(DismissDialogListener listener);
}
