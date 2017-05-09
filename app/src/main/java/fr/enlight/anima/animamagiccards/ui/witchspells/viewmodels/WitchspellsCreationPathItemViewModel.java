package fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels;


import android.content.Context;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels.WitchspellsPathItemViewModel;



public class WitchspellsCreationPathItemViewModel extends WitchspellsPathItemViewModel {


    public WitchspellsCreationPathItemViewModel(int pathType) {
        super(null, pathType, null);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.view_witchspells_path_item_edit;
    }

    public boolean isPathItemVisible(){
        return isMainPath() && mWitchspellsPath == null;
    }

    @Override
    public String getBookTitleAndLevel(Context context) {
        if(mWitchspellsPath == null){
            switch (mPathType){
                case MAIN_PATH_TYPE:
                    return context.getString(R.string.Witchspells_Add_Main_Path);
                case SECONDARY_PATH_TYPE:
                    return context.getString(R.string.Witchspells_Add_Secondary_Path);
                case FREE_ACCESS_TYPE:
                    return context.getString(R.string.Witchspells_Add_Free_Access);
            }
            throw new UnsupportedOperationException("Type cannot be different of the ones treated here");
        }

        return super.getBookTitleAndLevel(context);
    }
}
