package fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;

import com.android.databinding.library.baseAdapters.BR;

import fr.enlight.anima.animamagiccards.MainApplication;
import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.cardmodel.model.spells.SpellbookType;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.cardmodel.model.spells.Spellbook;

public class WitchspellsSecondarySpellbookViewModel extends BaseObservable implements BindableViewModel {

    @Nullable
    private final Spellbook mSelectedSecondaryPaths;
    @Nullable
    private final SpellbookType mSelectedSecondaryPathsType;

    private final Listener mListener;

    private final Context mContext;

    /**
     * Constructor for empty
     */
    public WitchspellsSecondarySpellbookViewModel(Listener mListener) {
        mSelectedSecondaryPaths = null;
        mSelectedSecondaryPathsType = null;
        this.mListener = mListener;
        mContext = MainApplication.getMainContext();
    }

    /**
     * Constructor made for secondary paths (with Spellbook's type only)
     */
    public WitchspellsSecondarySpellbookViewModel(@NonNull SpellbookType secondaryPathType, @NonNull Listener listener) {
        mSelectedSecondaryPaths = null;
        mSelectedSecondaryPathsType = secondaryPathType;
        mListener = listener;
        mContext = MainApplication.getMainContext();
    }

    /**
     * Constructor made for secondary paths (with Spellbook's full information)
     */
    public WitchspellsSecondarySpellbookViewModel(@NonNull Spellbook secondaryPathSpellbook, @NonNull Listener listener) {
        mSelectedSecondaryPaths = secondaryPathSpellbook;
        mSelectedSecondaryPathsType = secondaryPathSpellbook.spellbookType;
        mListener = listener;
        mContext = MainApplication.getMainContext();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.view_witchspells_spellbook_secondary;
    }

    @Override
    public int getVariableId() {
        return BR.model;
    }

    @Bindable
    public Drawable getSecondaryPathIcon() {
        if (mSelectedSecondaryPathsType == null) {
            return ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.ic_add_black, null);
        }

        return ResourcesCompat.getDrawable(mContext.getResources(), mSelectedSecondaryPathsType.iconRes, null);
    }

    @Bindable
    public String getSecondaryPathTitle() {
        if (mSelectedSecondaryPathsType == null) {
            return mContext.getString(R.string.Witchspells_Choose_Secondary_Path);
        }
        String pathName = mContext.getString(mSelectedSecondaryPathsType.titleRes);
        if(mSelectedSecondaryPaths == null){
            return mContext.getString(R.string.Witchspells_Secondary_Path_Format, pathName);
        }
        return pathName;
    }

    public void onItemClicked(){
        mListener.onSelectedSecondaryPath(mSelectedSecondaryPathsType);
    }

    public interface Listener {
        void onSelectedSecondaryPath(SpellbookType selectedSpellbookType);
    }
}
