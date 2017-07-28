package fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels.chosenspell;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;

import com.android.databinding.library.baseAdapters.BR;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.cardmodel.model.spells.Spell;


public class WitchspellsChosenSpellViewModel implements BindableViewModel {

    private final int mIndex;
    private final Spell mSpell;
    private final Listener mListener;

    public WitchspellsChosenSpellViewModel(int index, @NonNull Spell spell, Listener listener) {
        this.mIndex = index;
        this.mSpell = spell;
        this.mListener = listener;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.view_witchspells_chosen_spell;
    }

    @Override
    public int getVariableId() {
        return BR.model;
    }

    public Drawable getPathIcon(Context context){
        return ResourcesCompat.getDrawable(context.getResources(), mSpell.spellbookType.iconRes, null);
    }

    public String getSpellTitle(){
        return mSpell.name;
    }

    public String getSpellDescription(Context context){
        String pathName = context.getString(mSpell.spellbookType.titleRes);
        return context.getString(R.string.chosen_spell_description_format, pathName, mSpell.level);
    }

    public void onModifySpellClicked(){
        mListener.onModifyChosenSpellClicked(mIndex, mSpell);
    }

    public interface Listener{
        void onModifyChosenSpellClicked(int position, Spell spell);
    }
}
