package fr.enlight.anima.animamagiccards.ui.spellbooks.viewmodels;


import com.loopeer.cardstack.CardStackView;

import fr.enlight.anima.cardmodel.model.spells.SpellbookType;
import fr.enlight.anima.animamagiccards.views.viewmodels.ListBindableViewModel;

public class SpellStackViewModel extends ListBindableViewModel implements CardStackView.ItemExpendListener {

    public final SpellbookViewModel spellbookModel;

    public SpellStackViewModel(SpellbookType spellbookType) {
        this.spellbookModel = new SpellbookViewModel(spellbookType);
    }

    public CardStackView.ItemExpendListener getExpandListener(){
        return this;
    }

    @Override
    public void onItemExpend(boolean expend) {
        // Nothing to do for now
    }
}
