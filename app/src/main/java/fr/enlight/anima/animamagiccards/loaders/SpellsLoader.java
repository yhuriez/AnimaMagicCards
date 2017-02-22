package fr.enlight.anima.animamagiccards.loaders;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import fr.enlight.anima.animamagiccards.utils.SpellbookType;
import fr.enlight.anima.animamagiccards.utils.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.animamagiccards.viewmodels.SpellViewModel;
import fr.enlight.anima.cardmodel.business.SpellBusinessService;
import fr.enlight.anima.cardmodel.model.Spell;

/**
 *
 */
public class SpellsLoader extends BaseLoader<List<BindableViewModel>> {

    private SpellBusinessService spellBusinessService;
    private final int bookId;
    private final SpellbookType bookType;

    public SpellsLoader(Context context, int bookId, SpellbookType spellbookType) {
        super(context);
        spellBusinessService = new SpellBusinessService(context);
        this.bookId = bookId;
        this.bookType = spellbookType;
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
}
