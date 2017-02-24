package fr.enlight.anima.animamagiccards.loaders;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import fr.enlight.anima.animamagiccards.ui.spellbooks.utils.SpellbookType;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.animamagiccards.ui.spellbooks.viewmodels.SpellbookViewModel;
import fr.enlight.anima.cardmodel.business.SpellBusinessService;
import fr.enlight.anima.cardmodel.model.Spellbook;

/**
 *
 */
public class SpellbooksIndexLoader extends BaseLoader<List<BindableViewModel>> {

    private final SpellbookViewModel.Listener listener;
    private SpellBusinessService spellBusinessService;

    public SpellbooksIndexLoader(Context context, SpellbookViewModel.Listener listener) {
        super(context);
        spellBusinessService = new SpellBusinessService(context);
        this.listener = listener;
    }

    @Override
    public List<BindableViewModel> loadInBackground() {
        List<Spellbook> spellbooksIndex = spellBusinessService.getSpellbooksIndex();

        List<BindableViewModel> result = new ArrayList<>();

        for (Spellbook spellbook : spellbooksIndex) {
            SpellbookType type = SpellbookType.getTypeFromBookId(spellbook.bookId);
            SpellbookViewModel viewModel = new SpellbookViewModel(spellbook, type);
            viewModel.setListener(listener);
            result.add(viewModel);
        }

        return result;
    }

    @Override
    public void deliverResult(List<BindableViewModel> data) {
        if (data != null && !data.isEmpty()){
            for (BindableViewModel spellbookViewModel : data) {
                ((SpellbookViewModel) spellbookViewModel).setListener(listener);
            }
        }
        super.deliverResult(data);
    }
}
