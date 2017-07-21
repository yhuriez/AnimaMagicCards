package fr.enlight.anima.animamagiccards.ui.spells.viewmodels.quickaccess;

import java.util.ArrayList;
import java.util.List;

import fr.enlight.anima.cardmodel.model.spells.SpellbookType;


public class GroupQASpellbookPathViewModel extends AbstractGroupQAViewModel {

    private final List<SpellbookType> mSpellbookTypes;
    private final boolean mWithAllTypesOption;

    public GroupQASpellbookPathViewModel(List<SpellbookType> spellbookTypes, Listener listener, boolean withAllTypesOption) {
        super(listener);
        mSpellbookTypes = spellbookTypes;
        mWithAllTypesOption = withAllTypesOption;
        initViewModels();
    }

    public GroupQASpellbookPathViewModel(List<SpellbookType> spellbookTypes, Listener listener) {
        this(spellbookTypes, listener, false);
    }

    protected List<QAViewModel> createViewModels() {
        List<QAViewModel> result = new ArrayList<>();

        if(mWithAllTypesOption){
            result.add(new QASpellbookPathViewModel(this));
        }

        for (SpellbookType type : mSpellbookTypes) {
            result.add(new QASpellbookPathViewModel(type, this));
        }

        return result;
    }
}
