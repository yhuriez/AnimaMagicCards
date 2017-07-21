package fr.enlight.anima.animamagiccards.ui.other;


import android.content.Context;

import com.android.databinding.library.baseAdapters.BR;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.views.viewmodels.DialogViewModel;
import fr.enlight.anima.animamagiccards.views.viewmodels.DismissDialogListener;
import fr.enlight.anima.cardmodel.dao.LocalizedFileDao;

public class AboutUsViewModel implements DialogViewModel {

    private DismissDialogListener mListener;

    private String locale;

    public AboutUsViewModel(String locale) {
        this.locale = locale;
    }

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

    public String getFilepath(){
        LocalizedFileDao localizedFileDao = new LocalizedFileDao("file:///android_asset/html", locale);
        return localizedFileDao.getLocalizedAssetFilename("about_us.html");
    }

}
