package fr.enlight.anima.animamagiccards.ui.other;


import com.android.databinding.library.baseAdapters.BR;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.views.viewmodels.DialogViewModel;
import fr.enlight.anima.animamagiccards.views.viewmodels.DismissDialogListener;

public class AboutUsViewModel implements DialogViewModel {

    private DismissDialogListener mListener;

    @Override
    public int getLayoutRes() {
        return R.layout.view_about_us;
    }

    @Override
    public int getVariableId() {
        return BR.model;
    }

    @Override
    public void setListener(DismissDialogListener listener) {
        mListener = listener;
    }

    public void onOkClicked(){
        mListener.dismissDialog();
    }

}
