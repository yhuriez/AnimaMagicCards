package fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableBoolean;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import com.android.databinding.library.baseAdapters.BR;

import java.util.ArrayList;
import java.util.List;

import fr.enlight.anima.animamagiccards.MainApplication;
import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.cardmodel.model.spells.SpellbookType;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.cardmodel.model.spells.Spellbook;

public class WitchspellsMainSpellbookViewModel extends BaseObservable implements BindableViewModel, WitchspellsSecondarySpellbookViewModel.Listener {

    private static final String MAJOR_PATH_TYPE = "Majeure";

    private final List<String> mAvailableLevels = new ArrayList<>();

    @NonNull
    private final Spellbook mSpellbook;
    private final SpellbookType mSpellbookType;

    private WitchspellsSecondarySpellbookViewModel mSecondarySpellbookViewModel;

    private final Listener mListener;

    public ObservableBoolean secondaryPathEditVisible = new ObservableBoolean(false);

    private final Context mContext;

    private int mSelectedLevel;


    public WitchspellsMainSpellbookViewModel(@NonNull Spellbook spellbook, @NonNull SpellbookType spellbookType, @NonNull Listener listener) {
        this(spellbook, spellbookType, listener, null);
    }

    public WitchspellsMainSpellbookViewModel(@NonNull Spellbook spellbook, @NonNull SpellbookType spellbookType, @NonNull Listener listener, @Nullable Spellbook selectedSecondaryPaths) {
        mSpellbook = spellbook;
        mSpellbookType = spellbookType;
        mListener = listener;
        mContext = MainApplication.getMainContext();
        if(selectedSecondaryPaths == null){
            mSecondarySpellbookViewModel = new WitchspellsSecondarySpellbookViewModel(this);
        } else {
            mSecondarySpellbookViewModel = new WitchspellsSecondarySpellbookViewModel(selectedSecondaryPaths, this);
        }
    }


    @Override
    public int getLayoutRes() {
        return R.layout.view_witchspells_spellbook_main;
    }

    @Override
    public int getVariableId() {
        return BR.model;
    }

    public boolean isMajorPath() {
        return mSpellbook.type.equals(MAJOR_PATH_TYPE);
    }

    public String getSpellbookTitle() {
        return mContext.getString(mSpellbookType.titleRes);
    }

    public Drawable getSpellbookIcon() {
        return ResourcesCompat.getDrawable(mContext.getResources(), mSpellbookType.iconRes, null);
    }

    public String getOppositePath() {
        return mSpellbook.oppositeBook;
    }

    public SpinnerAdapter getLevelSpinnerAdapter() {
        if (mAvailableLevels.isEmpty()) {
            mAvailableLevels.add("");
            for (int index = 2; index <= 100; index += 2) {
                mAvailableLevels.add(Integer.toString(index));
            }
        }

        return new ArrayAdapter<>(mContext, android.R.layout.simple_dropdown_item_1line, mAvailableLevels);
    }

    public void onSpellbookClicked() {
        mListener.onSpellbookSelected(mSpellbook);
    }

    public int getSelectedLevel() {
        int selectedLevelIndex = mAvailableLevels.indexOf(Integer.toString(mSelectedLevel));
        if (selectedLevelIndex < 0) {
            return 0;
        }
        return selectedLevelIndex;
    }

    public void setSelectedLevel(int selectedLevelIndex) {
        String selectedLevelStr = mAvailableLevels.get(selectedLevelIndex);
        if (selectedLevelStr == null || selectedLevelStr.isEmpty()) {
            mSelectedLevel = 0;
        } else {
            mSelectedLevel = Integer.parseInt(selectedLevelStr);
        }

        if(mSelectedLevel > 0){
            secondaryPathEditVisible.set(true);
        } else {
            secondaryPathEditVisible.set(false);
        }

        mListener.onSpellbookLevelSelected(mSpellbook, mSelectedLevel);
    }

    // region secondaryPaths

    @Bindable
    public WitchspellsSecondarySpellbookViewModel getSecondarySpellbookViewModel(){
        return mSecondarySpellbookViewModel;
    }

    @Override
    public void onSelectedSecondaryPath(Spellbook spellbook) {
        mListener.onShowSecondarySpellbookForMainPath(mSpellbook);
    }


    // endregion

    public interface Listener {
        void onSpellbookSelected(Spellbook spellbook);

        void onSpellbookLevelSelected(Spellbook spellbook, int levelSelected);

        void onShowSecondarySpellbookForMainPath(Spellbook spellbook);
    }
}
