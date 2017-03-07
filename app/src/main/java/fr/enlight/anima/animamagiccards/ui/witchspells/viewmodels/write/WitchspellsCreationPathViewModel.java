package fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels.write;


import com.android.databinding.library.baseAdapters.BR;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.ui.spellbooks.utils.SpellbookType;
import fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels.read.WitchspellsPathItemViewModel;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.cardmodel.model.witchspells.WitchspellsPath;

public class WitchspellsCreationPathViewModel implements BindableViewModel{

    private final WitchspellsPath witchspellsPath;


    public WitchspellsCreationPathViewModel(WitchspellsPath witchspellsPath) {
        this.witchspellsPath = witchspellsPath;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.view_witchspells_creation_path;
    }

    @Override
    public int getVariableId() {
        return BR.model;
    }


    public WitchspellsPathItemViewModel getMainPathModel(){
        if(witchspellsPath.pathBookId >= 0) {
            SpellbookType spellbookType = SpellbookType.getTypeFromBookId(witchspellsPath.pathBookId);
            return new WitchspellsPathItemViewModel(spellbookType, WitchspellsPathItemViewModel.MAIN_PATH_TYPE, witchspellsPath);
        }
        return null;
    }

    public WitchspellsPathItemViewModel getSecondaryPathModel(){
        if(witchspellsPath.pathBookId >= 0 && witchspellsPath.secondaryPathBookId >= 0){
            SpellbookType secondarySpellbookType = SpellbookType.getTypeFromBookId(witchspellsPath.secondaryPathBookId);
            return new WitchspellsPathItemViewModel(secondarySpellbookType, WitchspellsPathItemViewModel.SECONDARY_PATH_TYPE, witchspellsPath);
        }
        return null;
    }

    public WitchspellsPathItemViewModel getFreeAccessModel(){
        if(witchspellsPath.pathBookId >= 0 && witchspellsPath.freeAccessSpellsIds != null && !witchspellsPath.freeAccessSpellsIds.isEmpty()){
            return new WitchspellsPathItemViewModel(null, WitchspellsPathItemViewModel.FREE_ACCESS_TYPE, witchspellsPath);
        }
        return null;
    }
}
