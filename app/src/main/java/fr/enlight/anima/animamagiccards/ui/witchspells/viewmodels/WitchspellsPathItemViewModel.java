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

    protected SpellbookType mSpellbookType;
    protected int mPathType;
    protected WitchspellsPath mWitchspellsPath;

    public WitchspellsPathItemViewModel(SpellbookType mSpellbookType, int mPathType, WitchspellsPath mWitchspellsPath) {
        this.mSpellbookType = mSpellbookType;
        this.mPathType = mPathType;
        this.mWitchspellsPath = mWitchspellsPath;
    }

    public String getBookTitleAndLevel(Context context){
        String bookTitle = null;
        if(mSpellbookType != null){
            bookTitle = context.getString(mSpellbookType.titleRes);
        }

        switch (mPathType){
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

    public Drawable getBookDrawable(Context context){
        if(mSpellbookType != null){
            return ResourcesCompat.getDrawable(context.getResources(), mSpellbookType.iconRes, null);
        }
        return null;
    }

    public boolean isMainPath(){
        return mPathType == MAIN_PATH_TYPE;
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
