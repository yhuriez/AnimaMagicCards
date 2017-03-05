package fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels;


import java.util.List;

import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.animamagiccards.views.viewmodels.ListBindableViewModel;
import fr.enlight.anima.cardmodel.model.witchspells.Witchspells;

public class WitchspellsViewModel extends ListBindableViewModel{

    private final Witchspells witchspells;


    public WitchspellsViewModel(Witchspells witchspells) {
        this.witchspells = witchspells;
    }

    public String getWitchName(){
        return witchspells.witchName;
    }

    @Override
    public List<BindableViewModel> getViewModels() {

        return super.getViewModels();
    }
}
