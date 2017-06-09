package fr.enlight.anima.animamagiccards.ui.spells.viewmodels;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;

import com.android.databinding.library.baseAdapters.BR;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.cardmodel.model.spells.SpellbookType;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.cardmodel.model.spells.Spellbook;

/**
 *
 */
public class SpellbookViewModel implements BindableViewModel {

    private Spellbook mSpellbook;
    private SpellbookType mType;

    private Listener mListener;

    public SpellbookViewModel(Spellbook spellbook) {
        this.mSpellbook = spellbook;
        this.mType = mSpellbook.spellbookType;
    }

    public SpellbookViewModel(SpellbookType spellbookType) {
        this.mSpellbook = null;
        this.mType = spellbookType;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.view_spellbook_item;
    }

    @Override
    public int getVariableId() {
        return BR.model;
    }

    public void setListener(Listener listener) {
        this.mListener = listener;
    }

    public String getTitle(Context context){
        return context.getString(mType.titleRes);
    }

    public Drawable getSpellbookIcon(Context context){
        return ResourcesCompat.getDrawable(context.getResources(), mType.iconRes, null);
    }

    public void onSpellbookClicked(){
        if(mListener != null){
            mListener.onSpellbookClicked(mSpellbook);
        }
    }

    public interface Listener {
        void onSpellbookClicked(Spellbook spellbook);
    }
}
