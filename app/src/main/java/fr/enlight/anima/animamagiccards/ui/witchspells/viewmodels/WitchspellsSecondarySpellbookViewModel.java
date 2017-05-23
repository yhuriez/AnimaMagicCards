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
    private Spellbook mSelectedSecondaryPaths;

    private final Listener mListener;

    private final Context mContext;

    public WitchspellsSecondarySpellbookViewModel(Listener mListener) {
        mSelectedSecondaryPaths = null;
        this.mListener = mListener;
        mContext = MainApplication.getMainContext();
    }

    /**
     * Constructor made for secondary paths
     */
    public WitchspellsSecondarySpellbookViewModel(@NonNull Spellbook secondaryPathSpellbook, @NonNull Listener listener) {
        mSelectedSecondaryPaths = secondaryPathSpellbook;
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

    @Nullable
    public Spellbook getSecondarySpellbook() {
        return mSelectedSecondaryPaths;
    }

    @Bindable
    public Drawable getSecondaryPathIcon() {
        if (mSelectedSecondaryPaths == null) {
            return ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.ic_add_black, null);
        }

        SpellbookType typeFromBookId = SpellbookType.getTypeFromBookId(mSelectedSecondaryPaths.bookId);
        if (typeFromBookId == null) {
            return null;
        }
        return ResourcesCompat.getDrawable(mContext.getResources(), typeFromBookId.iconRes, null);
    }

    @Bindable
    public String getSecondaryPathTitle() {
        if (mSelectedSecondaryPaths == null) {
            return mContext.getString(R.string.Witchspells_Choose_Secondary_Path);
        }
        return mSelectedSecondaryPaths.bookName;
    }

    public void onItemClicked(){
        mListener.onSelectedSecondaryPath(mSelectedSecondaryPaths);
    }

    public interface Listener {
        void onSelectedSecondaryPath(Spellbook spellbook);
    }
}
