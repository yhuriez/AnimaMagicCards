package fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableBoolean;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import com.android.databinding.library.baseAdapters.BR;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.enlight.anima.animamagiccards.MainApplication;
import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels.freeaccess.WitchspellsGlobalFreeAccessViewModel;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.cardmodel.model.spells.Spellbook;
import fr.enlight.anima.cardmodel.model.spells.SpellbookType;
import fr.enlight.anima.cardmodel.model.witchspells.WitchspellsPath;
import fr.enlight.anima.cardmodel.utils.SpellUtils;

public class WitchspellsMainSpellbookViewModel extends BaseObservable implements BindableViewModel,
        WitchspellsSecondarySpellbookViewModel.Listener, WitchspellsGlobalFreeAccessViewModel.Listener{

    private final List<String> mAvailableLevels = new ArrayList<>();

    private final Context mContext;

    @NonNull
    private final Spellbook mSpellbook;
    @NonNull
    private final SpellbookType mSpellbookType;
    @NonNull
    private final WitchspellsPath mWitchspellsPath;

    private WitchspellsSecondarySpellbookViewModel mSecondarySpellbookViewModel;

    public final ObservableBoolean secondaryPathDefined = new ObservableBoolean(false);
    public final ObservableBoolean freeAccessDefined = new ObservableBoolean(false);

    @NonNull
    private final Listener mListener;


    @SuppressLint("UseSparseArrays")
    public WitchspellsMainSpellbookViewModel(@NonNull Spellbook spellbook, @Nullable WitchspellsPath witchspellsPath, @NonNull Listener listener) {
        mSpellbook = spellbook;
        mSpellbookType = spellbook.spellbookType;
        mListener = listener;
        mContext = MainApplication.getMainContext();

        if(witchspellsPath == null){
            mWitchspellsPath = new WitchspellsPath();
            mWitchspellsPath.pathBookId = spellbook.bookId;
            mWitchspellsPath.freeAccessSpellsIds = new HashMap<>();
        } else {
            mWitchspellsPath = witchspellsPath;
        }

        if(mWitchspellsPath.secondaryPathBookId > 0){
            SpellbookType secondaryBookType = SpellbookType.getTypeFromBookId(mWitchspellsPath.secondaryPathBookId);
            if(secondaryBookType == null){
                throw new IllegalStateException("If secondary id is registered in path, it should gives a SpellbookType");
            }
            mSecondarySpellbookViewModel = new WitchspellsSecondarySpellbookViewModel(secondaryBookType, this);
            secondaryPathDefined.set(true);

        } else {
            mSecondarySpellbookViewModel = new WitchspellsSecondarySpellbookViewModel(this);
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
        return mSpellbook.isMajorPath();
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

    public void onMainPathClicked(View spinner){
        spinner.performClick();
    }

    public SpinnerAdapter getLevelSpinnerAdapter() {
        if (mAvailableLevels.isEmpty()) {
            mAvailableLevels.add("");
            for (int index = 2; index <= 100; index += 2) {
                mAvailableLevels.add(Integer.toString(index));
            }
        }

        return new ArrayAdapter<>(mContext, R.layout.simple_spinner_layout, mAvailableLevels);
    }

    @Bindable
    public int getSelectedLevel() {
        int selectedLevelIndex = mAvailableLevels.indexOf(Integer.toString(mWitchspellsPath.pathLevel));
        if (selectedLevelIndex < 0) {
            return 0;
        }
        return selectedLevelIndex;
    }

    public void setSelectedLevel(int selectedLevelIndex) {
        String selectedLevelStr = mAvailableLevels.get(selectedLevelIndex);
        int selectedLevel;
        if (selectedLevelStr == null || selectedLevelStr.isEmpty()) {
            selectedLevel = 0;
        } else {
            selectedLevel = Integer.parseInt(selectedLevelStr);
        }

        mWitchspellsPath.pathLevel = selectedLevel;
        notifyPropertyChanged(BR.selectedLevel);
        notifyPropertyChanged(BR.freeAccessViewModel);

        mListener.onWitchspellPathUpdated(mWitchspellsPath);
    }

    // region secondaryPaths

    @Bindable
    public WitchspellsSecondarySpellbookViewModel getSecondarySpellbookViewModel(){
        return mSecondarySpellbookViewModel;
    }

    public void onDeleteSecondaryPath(){
        mListener.onDeleteSecondaryPath(mWitchspellsPath.pathBookId);
    }

    @Override
    public void onSelectedSecondaryPath(SpellbookType selectedSpellbookType) {
        mListener.onShowSecondarySpellbookForMainPath(mSpellbook);
    }

    // endregion

    // region free access

    @Bindable
    public WitchspellsGlobalFreeAccessViewModel getFreeAccessViewModel() {
        if(SpellUtils.isFreeAccessAvailable(mSpellbook, mWitchspellsPath)) {
            Map<Integer, Integer> freeAccessSpellsIds = mWitchspellsPath.freeAccessSpellsIds;

            int selectedSpellsCount = SpellUtils.countSelectedFreeSpells(freeAccessSpellsIds);
            if (freeAccessSpellsIds == null || freeAccessSpellsIds.isEmpty() || selectedSpellsCount == 0) {
                freeAccessDefined.set(false);
                return new WitchspellsGlobalFreeAccessViewModel(0, 0, this);
            } else {
                freeAccessDefined.set(true);
                return new WitchspellsGlobalFreeAccessViewModel(freeAccessSpellsIds.size(), selectedSpellsCount, this);
            }
        }
        return null;
    }

    public void onDeleteFreeAccess(){
        mListener.onDeleteFreeAccess(mWitchspellsPath.pathBookId);
    }

    @Override
    public void onFreeAccessSelected() {
        mListener.onShowFreeAccessSpells(mSpellbook);
    }

    // endregion

    public interface Listener {
        void onWitchspellPathUpdated(@NonNull WitchspellsPath mWitchspellsPath);

        void onShowSecondarySpellbookForMainPath(Spellbook mainSpellbook);

        void onShowFreeAccessSpells(Spellbook mainSpellbook);

        void onDeleteSecondaryPath(int mainPathId);

        void onDeleteFreeAccess(int mainPathId);
    }
}
