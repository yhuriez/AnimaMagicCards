package fr.enlight.anima.animamagiccards.ui.spells.viewmodels.quickaccess;

import java.util.ArrayList;
import java.util.List;


public class GroupQAFreeSpellsViewModel extends AbstractGroupQAViewModel {

    private final int mLevelLimit;

    public GroupQAFreeSpellsViewModel(int levelLimit, Listener listener) {
        super(listener);
        mLevelLimit = levelLimit;

        initViewModels();
    }

    protected List<QAViewModel> createViewModels() {
        List<QAViewModel> result = new ArrayList<>();

        if(mLevelLimit > 0){
            for (int index = 0; index < mLevelLimit / 10; index++) {
                int bottomLevel = (index == 0) ? 1 : index * 10;
                int topLevel = (index + 1) * 10;

                QAFreeSpellsViewModel viewModel = new QAFreeSpellsViewModel(bottomLevel, topLevel, this);
                result.add(viewModel);
            }
        }

        return result;
    }
}
