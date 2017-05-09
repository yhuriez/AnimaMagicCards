package fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels;


import com.android.databinding.library.baseAdapters.BR;

import java.util.ArrayList;
import java.util.List;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.ui.spellbooks.utils.SpellbookType;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.animamagiccards.views.viewmodels.ListBindableViewModel;
import fr.enlight.anima.cardmodel.model.witchspells.Witchspells;
import fr.enlight.anima.cardmodel.model.witchspells.WitchspellsPath;

public class WitchspellsBookViewModel extends ListBindableViewModel implements BindableViewModel{

    private final Witchspells witchspells;


    public WitchspellsBookViewModel(Witchspells witchspells) {
        this.witchspells = witchspells;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.view_witchspells_book_item;
    }

    @Override
    public int getVariableId() {
        return BR.model;
    }

    public String getWitchName(){
        return witchspells.witchName;
    }

    @Override
    public void setViewModels(List<BindableViewModel> viewModels) {
        // Do nothing
    }

    @Override
    public List<BindableViewModel> getViewModels() {
        List<BindableViewModel> result = new ArrayList<>();

        for (WitchspellsPath witchPath : witchspells.witchPaths) {
            // Main Path
            SpellbookType spellbookType = SpellbookType.getTypeFromBookId(witchPath.pathBookId);
            result.add(new WitchspellsPathItemViewModel(spellbookType, WitchspellsPathItemViewModel.MAIN_PATH_TYPE, witchPath));

            // Secondary Path
            if(witchPath.secondaryPathBookId >= 0){
                SpellbookType secondarySpellbookType = SpellbookType.getTypeFromBookId(witchPath.secondaryPathBookId);
                result.add(new WitchspellsPathItemViewModel(secondarySpellbookType, WitchspellsPathItemViewModel.SECONDARY_PATH_TYPE, witchPath));
            }

            // Free Access Spells
            if(witchPath.freeAccessSpellsIds != null && !witchPath.freeAccessSpellsIds.isEmpty()){
                result.add(new WitchspellsPathItemViewModel(null, WitchspellsPathItemViewModel.FREE_ACCESS_TYPE, witchPath));
            }
        }

        return result;
    }
}
