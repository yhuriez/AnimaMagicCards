package fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;

import com.android.databinding.library.baseAdapters.BR;

import fr.enlight.anima.animamagiccards.MainApplication;
import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.cardmodel.model.spells.SpellbookType;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.cardmodel.model.witchspells.WitchspellsPath;


public class WitchspellsPathViewModel implements BindableViewModel {

    @NonNull
    private final WitchspellsPath mWitchspellsPath;
    private final Context mContext;

    private final SpellbookType mainPathType;
    private final SpellbookType secondaryPathType;

    private Listener mListener;

    public WitchspellsPathViewModel(@NonNull WitchspellsPath mWitchspellsPath, Listener listener) {
        this.mWitchspellsPath = mWitchspellsPath;
        this.mContext = MainApplication.getMainContext();
        this.mListener = listener;
        mainPathType = SpellbookType.getTypeFromBookId(mWitchspellsPath.pathBookId);
        secondaryPathType = SpellbookType.getTypeFromBookId(mWitchspellsPath.secondaryPathBookId);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.view_witchspells_path_item;
    }

    @Override
    public int getVariableId() {
        return BR.model;
    }

    public int getPathLevel(){
        return mWitchspellsPath.pathLevel;
    }

    public Drawable getMainPathIcon(){
        return ResourcesCompat.getDrawable(mContext.getResources(), mainPathType.iconRes, null);
    }

    public String getMainPathTitle(){
        return mContext.getString(mainPathType.titleRes);
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

    public String getSecondaryPathTitle(){
        if(secondaryPathType == null){
            return mContext.getString(R.string.Witchspells_Free_Access_Value);
        }
        String secondaryPath = mContext.getString(secondaryPathType.titleRes);
        return mContext.getString(R.string.Witchspells_Secondary_Path_Format, secondaryPath);
    }

    public int getFreeAccessSpellsCount(){
        if(mWitchspellsPath.freeAccessSpellsIds == null){
            return 0;
        }
        return mWitchspellsPath.freeAccessSpellsIds.size();
    }

    public void onPathClicked(){

    }

    public interface Listener{

    }
}
