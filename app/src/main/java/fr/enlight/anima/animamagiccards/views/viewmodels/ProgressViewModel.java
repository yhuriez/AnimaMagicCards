package fr.enlight.anima.animamagiccards.views.viewmodels;

import fr.enlight.anima.animamagiccards.BR;
import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;


public class ProgressViewModel implements DialogViewModel {

    private String progressMessage;

    public ProgressViewModel(String progressMessage) {
        this.progressMessage = progressMessage;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.view_progress;
    }

    @Override
    public int getVariableId() {
        return BR.model;
    }

    public String getProgressMessage() {
        return progressMessage;
    }

    @Override
    public void setListener(DismissDialogListener listener) {
        // Nothing to add here
    }
}
