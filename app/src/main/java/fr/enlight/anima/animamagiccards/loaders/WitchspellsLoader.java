package fr.enlight.anima.animamagiccards.loaders;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels.WitchspellsBookViewModel;
import fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels.WitchspellsAddViewModel;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.cardmodel.business.WitchspellsBusinessService;
import fr.enlight.anima.cardmodel.model.witchspells.Witchspells;

/**
 *
 */
public class WitchspellsLoader extends BaseLoader<List<BindableViewModel>> {

    private WitchspellsBusinessService witchspellsBusinessService;
    private final WitchspellsAddViewModel.Listener mListener;

    public WitchspellsLoader(Context context, WitchspellsAddViewModel.Listener listener) {
        super(context);
        this.witchspellsBusinessService = new WitchspellsBusinessService(context);
        this.mListener = listener;
    }

    @Override
    public List<BindableViewModel> loadInBackground() {
        List<Witchspells> allWitchspells = witchspellsBusinessService.getAllWitchspells();

        List<BindableViewModel> result = new ArrayList<>();

        for (Witchspells witchspells : allWitchspells) {
            WitchspellsBookViewModel viewModel = new WitchspellsBookViewModel(witchspells);
            result.add(viewModel);
        }

        result.add(new WitchspellsAddViewModel(mListener));

        return result;
    }
}
