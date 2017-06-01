package fr.enlight.anima.animamagiccards.ui.spells.viewmodels.quickaccess;


import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.cardmodel.business.SpellFilterFactory;

public interface QuickAccessViewModel extends BindableViewModel{
    void setSelected(boolean selected);

    SpellFilterFactory.SpellFilter getFilter(SpellFilterFactory factory);
}
