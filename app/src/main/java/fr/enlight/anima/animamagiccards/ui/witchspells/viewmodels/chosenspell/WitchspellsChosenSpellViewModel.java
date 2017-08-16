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

    private final Spell mSpell;
    private final Listener mListener;
    private final boolean mReduced;


    public static WitchspellsChosenSpellViewModel newEditionInstance(@NonNull Spell spell, Listener listener) {
        return new WitchspellsChosenSpellViewModel(spell, listener, false);
    }

    public static WitchspellsChosenSpellViewModel newReadOnlyInstance(@NonNull Spell spell) {
        return new WitchspellsChosenSpellViewModel(spell, null, true);
    }

    private WitchspellsChosenSpellViewModel(@NonNull Spell spell, Listener listener, boolean reduced) {
        this.mSpell = spell;
        this.mListener = listener;
        this.mReduced = reduced;
    }

    @Override
    public int getLayoutRes() {
        if(mReduced){
            return R.layout.view_witchspells_chosen_spell_reduced;
        }
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
        if(mListener != null){
            mListener.onModifyChosenSpellClicked(mSpell);
        }
    }

    public void onDeleteSpell(){
        if(mListener != null){
            mListener.onDeleteChosenSpell(mSpell);
        }
    }

    public interface Listener{
        void onModifyChosenSpellClicked(Spell spell);

        void onDeleteChosenSpell(Spell spell);
    }
}
