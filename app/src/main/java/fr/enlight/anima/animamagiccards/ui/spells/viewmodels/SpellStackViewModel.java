package fr.enlight.anima.animamagiccards.ui.spells.viewmodels;


import com.loopeer.cardstack.CardStackView;

import fr.enlight.anima.animamagiccards.views.viewmodels.ListBindableViewModel;

public class SpellStackViewModel extends ListBindableViewModel implements CardStackView.ItemExpendListener {

    public CardStackView.ItemExpendListener getExpandListener(){
        return this;
    }

    @Override
    public void onItemExpend(boolean expend) {
        // Nothing to do for now
    }
}
