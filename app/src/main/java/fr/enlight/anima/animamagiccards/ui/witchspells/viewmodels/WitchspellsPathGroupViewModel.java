package fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels;


import com.android.databinding.library.baseAdapters.BR;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.ui.spellbooks.utils.SpellbookType;
import fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels.WitchspellsPathItemViewModel;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.cardmodel.model.witchspells.WitchspellsPath;

public class WitchspellsPathGroupViewModel implements BindableViewModel{

    private final WitchspellsPath witchspellsPath;


    public WitchspellsPathGroupViewModel(WitchspellsPath witchspellsPath) {
        this.witchspellsPath = witchspellsPath;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.view_witchspells_path_group;
    }

    @Override
    public int getVariableId() {
        return BR.model;
    }


    public WitchspellsPathItemViewModel getMainPathModel(){
        if(witchspellsPath.pathBookId >= 0) {
            SpellbookType spellbookType = SpellbookType.getTypeFromBookId(witchspellsPath.pathBookId);
            return new WitchspellsPathItemViewModel(WitchspellsPathItemViewModel.MAIN_PATH_TYPE, spellbookType, witchspellsPath);
        }
        return null;
    }

    public WitchspellsPathItemViewModel getSecondaryPathModel(){
        if(witchspellsPath.pathBookId >= 0 && witchspellsPath.secondaryPathBookId >= 0){
            SpellbookType secondarySpellbookType = SpellbookType.getTypeFromBookId(witchspellsPath.secondaryPathBookId);
            return new WitchspellsPathItemViewModel(WitchspellsPathItemViewModel.SECONDARY_PATH_TYPE, secondarySpellbookType, witchspellsPath);
        }
        return null;
    }

    public WitchspellsPathItemViewModel getFreeAccessModel(){
        if(witchspellsPath.pathBookId >= 0 && witchspellsPath.freeAccessSpellsIds != null && !witchspellsPath.freeAccessSpellsIds.isEmpty()){
            return new WitchspellsPathItemViewModel(WitchspellsPathItemViewModel.FREE_ACCESS_TYPE, null, witchspellsPath);
        }
        return null;
    }
}
