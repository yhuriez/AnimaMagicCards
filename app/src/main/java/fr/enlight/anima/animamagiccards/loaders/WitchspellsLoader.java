package fr.enlight.anima.animamagiccards.loaders;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels.read.WitchspellsViewModel;
import fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels.write.WitchspellsAddViewModel;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.cardmodel.business.WitchspellsBusinessService;
import fr.enlight.anima.cardmodel.model.witchspells.Witchspells;

/**
 *
 */
public class WitchspellsLoader extends BaseLoader<List<BindableViewModel>> {

    private WitchspellsBusinessService witchspellsBusinessService;

    public WitchspellsLoader(Context context) {
        super(context);
        this.witchspellsBusinessService = new WitchspellsBusinessService(context);
    }

    @Override
    public List<BindableViewModel> loadInBackground() {
        List<Witchspells> allWitchspells = witchspellsBusinessService.getAllWitchspells();

        List<BindableViewModel> result = new ArrayList<>();

        for (Witchspells witchspells : allWitchspells) {
            WitchspellsViewModel viewModel = new WitchspellsViewModel(witchspells);
            result.add(viewModel);
        }

        result.add(new WitchspellsAddViewModel());

        return result;
    }
}
