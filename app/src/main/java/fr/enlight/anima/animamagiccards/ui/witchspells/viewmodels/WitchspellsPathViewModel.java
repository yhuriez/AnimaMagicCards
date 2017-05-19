package fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.res.ResourcesCompat;

import com.android.databinding.library.baseAdapters.BR;

import fr.enlight.anima.animamagiccards.MainApplication;
import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.ui.spellbooks.utils.SpellbookType;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.cardmodel.model.witchspells.WitchspellsPath;


public class WitchspellsPathViewModel implements BindableViewModel {

    private final WitchspellsPath mWitchspellsPath;
    private final Context mContext;

    private final SpellbookType mainPathType;
    private final SpellbookType secondaryPathType;

    private Listener mListener;

    public WitchspellsPathViewModel(WitchspellsPath mWitchspellsPath, Listener listener) {
        this.mWitchspellsPath = mWitchspellsPath;
        this.mContext = MainApplication.getMainContext();
        this.mListener = listener;

        if(mWitchspellsPath != null) {
            mainPathType = SpellbookType.getTypeFromBookId(mWitchspellsPath.pathBookId);
            secondaryPathType = SpellbookType.getTypeFromBookId(mWitchspellsPath.secondaryPathBookId);
        } else {
            mainPathType = null;
            secondaryPathType = null;
        }
    }

    @Override
    public int getLayoutRes() {
        return R.layout.view_witchspells_path_item;
    }

    @Override
    public int getVariableId() {
        return BR.model;
    }

    public Drawable getMainPathIcon(){
        @DrawableRes int iconRes;
        if(isAddPathModel()){
            iconRes = R.drawable.ic_add_white;
        } else {
            iconRes = mainPathType.iconRes;
        }

        return ResourcesCompat.getDrawable(mContext.getResources(), iconRes, null);
    }

    public String getMainPathTitle(){
        if(isAddPathModel()){
            return mContext.getString(R.string.Witchspells_Add_Main_Path);
        }
        return mContext.getString(mainPathType.titleRes);
    }

    public Drawable getSecondaryPathIcon(){
        @DrawableRes int iconRes;
        if(isAddPathModel()){
            iconRes = R.drawable.ic_add_white;
        } else if(secondaryPathType == null){
            iconRes = R.drawable.card_icon_book_free_access;
        } else {
            iconRes = mainPathType.iconRes;
        }

        return ResourcesCompat.getDrawable(mContext.getResources(), iconRes, null);
    }

    public String getSecondaryPathTitle(){
        if(isAddPathModel() && secondaryPathType == null){
            return null;
        }
        return mContext.getString(secondaryPathType.titleRes);
    }

    public int getFreeAccessSpellsCount(){
        return mWitchspellsPath.freeAccessSpellsIds.size();
    }

    public boolean isAddPathModel(){
        return mWitchspellsPath == null;
    }

    public void onPathClicked(){
        if(isAddPathModel()){
            mListener.onAddNewPath();
        } else {
            mListener.onEditPath(this);
        }
    }

    public interface Listener{
        void onAddNewPath();
        void onEditPath(WitchspellsPathViewModel pathViewModel);
    }
}
