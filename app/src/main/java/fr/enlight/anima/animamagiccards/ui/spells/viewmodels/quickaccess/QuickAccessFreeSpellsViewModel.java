package fr.enlight.anima.animamagiccards.ui.spells.viewmodels.quickaccess;

import android.databinding.ObservableBoolean;

import com.android.databinding.library.baseAdapters.BR;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.cardmodel.business.SpellFilterFactory;


public class QuickAccessFreeSpellsViewModel implements QuickAccessViewModel {

    private final int mBottomLevel;
    private final int mTopLevel;

    public final ObservableBoolean selected = new ObservableBoolean(false);

    private final Listener mListener;

    public QuickAccessFreeSpellsViewModel(int bottomLevel, int topLevel, Listener listener) {
        mBottomLevel = bottomLevel;
        mTopLevel = topLevel;
        mListener = listener;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.view_quick_access_free_spells;
    }

    @Override
    public int getVariableId() {
        return BR.model;
    }

    public String getLevelText(){
        return Integer.toString(mBottomLevel) + "-" + Integer.toString(mTopLevel);
    }

    public void onItemClicked(){
        if(!selected.get()) {
            selected.set(true);
            mListener.onQuickAccessFreeSpell(this);
        }
    }

    @Override
    public void setSelected(boolean value) {
        selected.set(value);
    }

    @Override
    public SpellFilterFactory.SpellFilter getFilter(SpellFilterFactory factory) {
        return factory.createLevelWindowFilter(mBottomLevel + 1, mTopLevel);
    }

    public interface Listener{
        void onQuickAccessFreeSpell(QuickAccessFreeSpellsViewModel viewModel);
    }
}
