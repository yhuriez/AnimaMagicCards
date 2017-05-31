package fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import com.android.databinding.library.baseAdapters.BR;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.enlight.anima.animamagiccards.MainApplication;
import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels.freeaccess.WitchspellsFreeAccessViewModel;
import fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels.freeaccess.WitchspellsGlobalFreeAccessViewModel;
import fr.enlight.anima.cardmodel.model.spells.SpellbookType;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.cardmodel.model.spells.Spellbook;
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

    private final WitchspellsSecondarySpellbookViewModel mSecondarySpellbookViewModel;

    private final WitchspellsGlobalFreeAccessViewModel freeAccessViewModel;

    @NonNull
    private final Listener mListener;


    public WitchspellsMainSpellbookViewModel(@NonNull Spellbook spellbook, @Nullable WitchspellsPath witchspellsPath, @NonNull Listener listener) {
        mSpellbook = spellbook;
        mSpellbookType = spellbook.spellbookType;
        mListener = listener;
        mContext = MainApplication.getMainContext();

        if(witchspellsPath == null){
            mWitchspellsPath = new WitchspellsPath();
            mWitchspellsPath.pathBookId = spellbook.bookId;
        } else {
            mWitchspellsPath = witchspellsPath;
        }

        if(mWitchspellsPath.secondaryPathBookId > 0){
            SpellbookType secondaryBookType = SpellbookType.getTypeFromBookId(mWitchspellsPath.secondaryPathBookId);
            if(secondaryBookType == null){
                throw new IllegalStateException("If secondary id is registered in path, it should gives a SpellbookType");
            }
            mSecondarySpellbookViewModel = new WitchspellsSecondarySpellbookViewModel(secondaryBookType, this);

        } else {
            mSecondarySpellbookViewModel = new WitchspellsSecondarySpellbookViewModel(this);
        }

        Map<Integer, Integer> freeAccessSpellsIds = mWitchspellsPath.freeAccessSpellsIds;
        if(freeAccessSpellsIds == null || freeAccessSpellsIds.isEmpty()){
            freeAccessViewModel = new WitchspellsGlobalFreeAccessViewModel(0, 0, this);
        } else {
            int selectedSpellsCount = SpellUtils.countSelectedFreeSpells(freeAccessSpellsIds);
            freeAccessViewModel = new WitchspellsGlobalFreeAccessViewModel(freeAccessSpellsIds.size(), selectedSpellsCount, this);
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

    public SpinnerAdapter getLevelSpinnerAdapter() {
        if (mAvailableLevels.isEmpty()) {
            mAvailableLevels.add("");
            for (int index = 2; index <= 100; index += 2) {
                mAvailableLevels.add(Integer.toString(index));
            }
        }

        return new ArrayAdapter<>(mContext, android.R.layout.simple_dropdown_item_1line, mAvailableLevels);
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

        mListener.onWitchspellPathUpdated(mWitchspellsPath);
    }

    // region secondaryPaths

    public WitchspellsSecondarySpellbookViewModel getSecondarySpellbookViewModel(){
        return mSecondarySpellbookViewModel;
    }

    public WitchspellsGlobalFreeAccessViewModel getFreeAccessViewModel() {
        return freeAccessViewModel;
    }

    @Override
    public void onSelectedSecondaryPath(SpellbookType selectedSpellbookType) {
        mListener.onShowSecondarySpellbookForMainPath(mSpellbook);
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
    }
}
