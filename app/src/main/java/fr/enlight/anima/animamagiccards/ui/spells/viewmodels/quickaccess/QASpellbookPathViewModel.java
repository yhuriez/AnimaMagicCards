package fr.enlight.anima.animamagiccards.ui.spells.viewmodels.quickaccess;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;

import com.android.databinding.library.baseAdapters.BR;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.cardmodel.business.SpellFilterFactory;
import fr.enlight.anima.cardmodel.model.spells.SpellbookType;


public class QASpellbookPathViewModel implements QAViewModel {

    private final SpellbookType mSpellbookType;

    public final ObservableBoolean selected = new ObservableBoolean(false);

    private final QAClickListener mListener;


    /**
     * Used for "All types" version
     */
    public QASpellbookPathViewModel(QAClickListener listener) {
        this(null, listener);
    }

    public QASpellbookPathViewModel(SpellbookType spellbookType, QAClickListener listener) {
        mSpellbookType = spellbookType;
        mListener = listener;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.view_quick_access_spellbook_path;
    }

    @Override
    public int getVariableId() {
        return BR.model;
    }

    public Drawable getSpellbookDrawable(Context context){
        if(mSpellbookType == null){
            return ResourcesCompat.getDrawable(context.getResources(), R.drawable.card_icon_book_free_access, null);
        }
        return ResourcesCompat.getDrawable(context.getResources(), mSpellbookType.iconRes, null);
    }

    public void onItemClicked(){
        if(!selected.get()) {
            selected.set(true);
            mListener.onQuickAccessClicked(this);
        }
    }

    @Override
    public void setSelected(boolean value) {
        selected.set(value);
    }

    @Override
    public SpellFilterFactory.SpellFilter getFilter(SpellFilterFactory factory) {
        if(mSpellbookType == null){
            return factory.createNonFilteringFilter();
        }
        return factory.createSpellbookPathFilter(mSpellbookType);
    }
}
