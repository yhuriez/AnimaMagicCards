package fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;

import com.android.databinding.library.baseAdapters.BR;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.ui.spellbooks.utils.SpellbookType;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.cardmodel.model.witchspells.WitchspellsPath;


public class WitchspellsPathItemViewModel implements BindableViewModel {

    public static final int MAIN_PATH_TYPE = 0;
    public static final int SECONDARY_PATH_TYPE = 1;
    public static final int FREE_ACCESS_TYPE = 2;

    private final SpellbookType mSpellbookType;
    private final int mPathType;
    private final WitchspellsPath mWitchspellsPath;

    public WitchspellsPathItemViewModel(int mPathType) {
        this.mPathType = mPathType;
        this.mSpellbookType = null;
        this.mWitchspellsPath = null;
    }

    public WitchspellsPathItemViewModel(int mPathType, SpellbookType mSpellbookType, WitchspellsPath mWitchspellsPath) {
        this.mPathType = mPathType;
        this.mSpellbookType = mSpellbookType;
        this.mWitchspellsPath = mWitchspellsPath;
    }

    public String getLabel(Context context) {
        if (mWitchspellsPath == null) {
            switch (mPathType) {
                case MAIN_PATH_TYPE:
                    return context.getString(R.string.Witchspells_Add_Main_Path);
                case SECONDARY_PATH_TYPE:
                    return context.getString(R.string.Witchspells_Add_Secondary_Path);
                case FREE_ACCESS_TYPE:
                    return context.getString(R.string.Witchspells_Add_Free_Access);
            }
            throw new UnsupportedOperationException("Type cannot be different of the ones treated here");
        }

        if (mSpellbookType != null) {
            String bookTitle = context.getString(mSpellbookType.titleRes);

            switch (mPathType) {
                case MAIN_PATH_TYPE:
                    return context.getString(R.string.Witchspells_Path_Title_And_Level_Format, bookTitle, Integer.toString(mWitchspellsPath.pathLevel));
                case SECONDARY_PATH_TYPE:
                    return context.getString(R.string.Witchspells_Path_Title_And_Level_Format, bookTitle, Integer.toString(mWitchspellsPath.pathLevel));
                case FREE_ACCESS_TYPE:
                    int freeAccessCount = mWitchspellsPath.freeAccessSpellsIds.size();
                    return context.getResources().getQuantityString(R.plurals.Witchspells_Free_Access_Title, freeAccessCount, freeAccessCount);
            }
            throw new UnsupportedOperationException("Type cannot be different of the ones treated here");
        }

        throw new UnsupportedOperationException("Cannot have a witchspells path without spellbook type");
    }

    public Drawable getBookDrawable(Context context) {
        if (mSpellbookType != null) {
            return ResourcesCompat.getDrawable(context.getResources(), mSpellbookType.iconRes, null);
        }
        return null;
    }

    public boolean isMainPath() {
        return mPathType == MAIN_PATH_TYPE;
    }

    public boolean isPathItemVisible(){
        return isMainPath() && mWitchspellsPath == null;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.view_witchspells_path_item;
    }

    @Override
    public int getVariableId() {
        return BR.model;
    }
}
