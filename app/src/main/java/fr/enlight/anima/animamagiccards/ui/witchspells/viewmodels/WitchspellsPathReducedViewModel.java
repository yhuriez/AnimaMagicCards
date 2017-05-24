package fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.res.ResourcesCompat;

import com.android.databinding.library.baseAdapters.BR;

import fr.enlight.anima.animamagiccards.MainApplication;
import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.cardmodel.model.spells.SpellbookType;


public class WitchspellsPathReducedViewModel implements BindableViewModel {

    private final Context mContext;

    private SpellbookType mainPathType;
    private SpellbookType secondaryPathType;

    public WitchspellsPathReducedViewModel(SpellbookType mainPathType, SpellbookType secondaryPathType) {
        this.mainPathType = mainPathType;
        this.secondaryPathType = secondaryPathType;
        this.mContext = MainApplication.getMainContext();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.view_witchspells_path_item_reduced;
    }

    @Override
    public int getVariableId() {
        return BR.model;
    }

    public Drawable getMainPathIcon(){
        return ResourcesCompat.getDrawable(mContext.getResources(), mainPathType.iconRes, null);
    }

    public Drawable getSecondaryPathIcon(){
        @DrawableRes int iconRes;
        if(secondaryPathType == null){
            iconRes = R.drawable.card_icon_book_free_access;
        } else {
            iconRes = secondaryPathType.iconRes;
        }

        return ResourcesCompat.getDrawable(mContext.getResources(), iconRes, null);
    }
}
