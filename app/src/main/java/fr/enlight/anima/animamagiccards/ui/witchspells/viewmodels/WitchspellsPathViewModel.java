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

    private boolean reducedVersion;


    public WitchspellsPathViewModel(@NonNull WitchspellsPath mWitchspellsPath, Listener listener) {
        this.mWitchspellsPath = mWitchspellsPath;
        this.mContext = MainApplication.getMainContext();
        this.mListener = listener;
        mainPathType = SpellbookType.getTypeFromBookId(mWitchspellsPath.pathBookId);
        secondaryPathType = SpellbookType.getTypeFromBookId(mWitchspellsPath.secondaryPathBookId);
    }

    public WitchspellsPathViewModel(@NonNull WitchspellsPath mWitchspellsPath, Listener listener, boolean reducedVersion) {
        this(mWitchspellsPath, listener);
        this.reducedVersion = reducedVersion;
    }

    @Override
    public int getLayoutRes() {
        if(reducedVersion){
            return R.layout.view_witchspells_path_item_reduced;
        } else {
            return R.layout.view_witchspells_path_item;
        }
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
            int freeAccessSpellsCount = getFreeAccessSpellsCount();
            if(freeAccessSpellsCount > 0){
                return mContext.getResources().getQuantityString(R.plurals.Witchspells_Free_Access_Format, freeAccessSpellsCount, freeAccessSpellsCount);
            } else {
                return mContext.getString(R.string.Witchspells_No_Secondary_Spells);
            }
        }
        String secondaryPath = mContext.getString(secondaryPathType.titleRes);
        return mContext.getString(R.string.Witchspells_Secondary_Path_Format, secondaryPath);
    }

    public boolean isSecondLineVisible(){
        return getFreeAccessSpellsCount() > 0 && secondaryPathType != null;
    }

    public int getFreeAccessSpellsCount(){
        if(mWitchspellsPath.freeAccessSpellsIds == null){
            return 0;
        }
        return mWitchspellsPath.freeAccessSpellsIds.size();
    }

    public void onPathClicked(){
        if(mListener != null){
            mListener.onPathSelected(mWitchspellsPath);
        }
    }

    public interface Listener{
        void onPathSelected(WitchspellsPath witchspellsPath);
    }
}
