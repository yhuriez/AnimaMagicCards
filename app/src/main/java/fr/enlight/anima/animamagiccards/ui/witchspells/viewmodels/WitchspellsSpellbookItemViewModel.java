package fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels;

import android.content.Context;
import android.databinding.Bindable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;

import com.android.databinding.library.baseAdapters.BR;

import fr.enlight.anima.animamagiccards.MainApplication;
import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.ui.spellbooks.utils.SpellbookType;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.cardmodel.model.spells.Spellbook;

public class WitchspellsSpellbookItemViewModel implements BindableViewModel{

    private static final String MAJOR_PATH_TYPE = "Majeure";

    @NonNull
    private final Spellbook mSpellbook;

    @NonNull
    private final Listener mListener;
    private final Context mContext;

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

    public void onSpellbookClicked(){
        mListener.onSpellbookSelected(mSpellbook);
    }

    public interface Listener{
        void onSpellbookSelected(Spellbook spellbook);
    }
}
