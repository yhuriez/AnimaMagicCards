package fr.enlight.anima.animamagiccards.async;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import fr.enlight.anima.cardmodel.model.spells.SpellbookType;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.animamagiccards.ui.spellbooks.viewmodels.SpellViewModel;
import fr.enlight.anima.cardmodel.business.SpellBusinessService;
import fr.enlight.anima.cardmodel.model.spells.Spell;

/**
 *
 */
public class SpellsLoader extends BaseLoader<List<BindableViewModel>> {

    private SpellBusinessService spellBusinessService;
    private final int bookId;
    private final SpellbookType bookType;
    private final SpellViewModel.Listener listener;

    public SpellsLoader(Context context, int bookId, SpellbookType spellbookType, SpellViewModel.Listener listener) {
        super(context);
        spellBusinessService = new SpellBusinessService(context);
        this.bookId = bookId;
        this.bookType = spellbookType;
        this.listener = listener;
    }

    @Override
    public List<BindableViewModel> loadInBackground() {
        List<Spell> spells = spellBusinessService.getSpellsForBook(bookId);

        List<BindableViewModel> result = new ArrayList<>();

        for (Spell spell : spells) {
            SpellViewModel spellViewModel = new SpellViewModel(spell, bookType);
            result.add(spellViewModel);
        }

        return result;
    }

    @Override
    public void deliverResult(List<BindableViewModel> data) {
        if(data != null && !data.isEmpty()){
            for (BindableViewModel bindableViewModel : data) {
                if (bindableViewModel instanceof SpellViewModel){
                    ((SpellViewModel) bindableViewModel).setListener(listener);
                }
            }
        }

        super.deliverResult(data);
    }
}
