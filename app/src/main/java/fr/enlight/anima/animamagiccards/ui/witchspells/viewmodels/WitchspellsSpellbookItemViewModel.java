package fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import com.android.databinding.library.baseAdapters.BR;

import java.util.ArrayList;
import java.util.List;

import fr.enlight.anima.animamagiccards.MainApplication;
import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.ui.spellbooks.utils.SpellbookType;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.cardmodel.model.spells.Spellbook;

public class WitchspellsSpellbookItemViewModel implements BindableViewModel{

    private static final String MAJOR_PATH_TYPE = "Majeure";

    private final List<String> mAvailableLevels = new ArrayList<>();

    @NonNull
    private final Spellbook mSpellbook;

    @NonNull
    private final Listener mListener;
    private final Context mContext;

    private int mSelectedLevel;

    public WitchspellsSpellbookItemViewModel(@NonNull Spellbook spellbook, @NonNull Listener listener){
        mSpellbook = spellbook;
        mListener = listener;
        mContext = MainApplication.getMainContext();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.view_witchspells_spellbook_item;
    }

    @Override
    public int getVariableId() {
        return BR.model;
    }

    public boolean isMajorPath(){
        return mSpellbook.type.equals(MAJOR_PATH_TYPE);
    }

    public String getSpellbookTitle(){
        return mSpellbook.bookName;
    }

    public Drawable getSpellbookIcon(){
        SpellbookType typeFromBookId = SpellbookType.getTypeFromBookId(mSpellbook.bookId);
        if(typeFromBookId == null){
            return null;
        }
        return ResourcesCompat.getDrawable(mContext.getResources(), typeFromBookId.iconRes, null);
    }

    public String getOppositePath(){
        return mSpellbook.oppositeBook;
    }

    public SpinnerAdapter getLevelSpinnerAdapter(){
        if(mAvailableLevels.isEmpty()){
            mAvailableLevels.add("");
            for (int index = 2; index <= 100; index += 2) {
                mAvailableLevels.add(Integer.toString(index));
            }
        }

        return new ArrayAdapter<>(mContext, android.R.layout.simple_dropdown_item_1line, mAvailableLevels);
    }

    public void onSpellbookClicked(){
        mListener.onSpellbookSelected(mSpellbook);
    }

    public int getSelectedLevel(){
        int selectedLevelIndex = mAvailableLevels.indexOf(Integer.toString(mSelectedLevel));
        if(selectedLevelIndex < 0){
            return 0;
        }
        return selectedLevelIndex;
    }

    public void setSelectedLevel(int selectedLevelIndex){
        String selectedLevelStr = mAvailableLevels.get(selectedLevelIndex);
        if(selectedLevelStr == null || selectedLevelStr.isEmpty()){
            mSelectedLevel = 0;
        } else {
            mSelectedLevel = Integer.parseInt(selectedLevelStr);
        }
    }

    public interface Listener{
        void onSpellbookSelected(Spellbook spellbook);
        void onSpellbookLevelSelected(Spellbook spellbook, int levelSelected);
    }
}
