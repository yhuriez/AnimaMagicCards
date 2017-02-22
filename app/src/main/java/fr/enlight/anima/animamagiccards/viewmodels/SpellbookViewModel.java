package fr.enlight.anima.animamagiccards.viewmodels;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;

import com.android.databinding.library.baseAdapters.BR;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.utils.SpellbookType;
import fr.enlight.anima.animamagiccards.utils.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.cardmodel.model.Spellbook;

/**
 *
 */
public class SpellbookViewModel implements BindableViewModel {

    private Spellbook mSpellbook;
    private SpellbookType mType;

    private Listener mListener;

    public SpellbookViewModel(Spellbook spellbook, SpellbookType type) {
        this.mSpellbook = spellbook;
        this.mType = type;
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
        return context.getString(mType.getTitleRes());
    }

    public Drawable getSpellbookIcon(Context context){
        return ResourcesCompat.getDrawable(context.getResources(), mType.getIconRes(), null);
    }

    public void onSpellbookClicked(){
        if(mListener != null){
            mListener.onSpellbookClicked(mSpellbook.bookId, mType);
        }
    }

    public interface Listener {
        void onSpellbookClicked(int spellbookId, SpellbookType mType);
    }
}
