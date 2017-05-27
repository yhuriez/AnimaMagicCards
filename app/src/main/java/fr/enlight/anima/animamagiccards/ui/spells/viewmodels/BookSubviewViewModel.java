package fr.enlight.anima.animamagiccards.ui.spells.viewmodels;


import android.content.Context;

import fr.enlight.anima.animamagiccards.MainApplication;
import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.cardmodel.model.spells.Spellbook;
import fr.enlight.anima.cardmodel.model.witchspells.Witchspells;

public class BookSubviewViewModel {

    public final Spellbook spellbook;
    public final Witchspells witchspells;

    private final String mTitle;
    private final String mDescription;

    private final Listener mListener;

    public BookSubviewViewModel(Spellbook spellbook) {
        this.spellbook = spellbook;
        witchspells = null;
        mListener = null;
        mTitle = spellbook.bookName;
        mDescription = spellbook.description;
    }

    public BookSubviewViewModel(Witchspells witchspells, Listener listener) {
        this.witchspells = witchspells;
        spellbook = null;
        this.mListener = listener;
        Context mainContext = MainApplication.getMainContext();
        mTitle = mainContext.getString(R.string.Witchspells_Name_Format, witchspells.witchName);
        mDescription = null;
    }

    public BookSubviewViewModel(String title, String description) {
        mTitle = title;
        mDescription = description;
        witchspells = null;
        mListener = null;
        spellbook = null;
    }

    public String getTitle(){
        return mTitle;
    }

    public String getDescription(){
        return mDescription;
    }

    public void onModifyClicked(){
        if(mListener != null){
            mListener.onModifyWitchspells(witchspells);
        }
    }

    public void onDeleteClicked(){
        if(mListener != null){
            mListener.onDeleteWitchspells(witchspells);
        }
    }

    public void onSeeSpellsClicked(){
        if(mListener != null){
            mListener.onViewWitchspells(witchspells);
        }
    }

    public interface Listener{
        void onModifyWitchspells(Witchspells witchspells);
        void onViewWitchspells(Witchspells witchspells);
        void onDeleteWitchspells(Witchspells witchspells);
    }
}
