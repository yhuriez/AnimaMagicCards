package fr.enlight.anima.animamagiccards.ui.spells.viewmodels.quickaccess;

import java.util.ArrayList;
import java.util.List;

import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.animamagiccards.views.viewmodels.RecyclerViewModel;
import fr.enlight.anima.cardmodel.business.SpellFilterFactory;


public class SpellQuickAccessViewModel extends RecyclerViewModel implements QuickAccessFreeSpellsViewModel.Listener {

    private final int mLevelLimit;

    private final List<QuickAccessViewModel> quickAccessViewModels = new ArrayList<>();
    private QuickAccessViewModel selectedViewModel;

    private final SpellFilterFactory mSpellFilterFactory;

    private Listener mListener;


    public SpellQuickAccessViewModel(int levelLimit, Listener listener) {
        mLevelLimit = levelLimit;
        mSpellFilterFactory = new SpellFilterFactory();
        mListener = listener;

        initViewModels();
    }

    private void initViewModels() {
        List<BindableViewModel> result = new ArrayList<>();

        quickAccessViewModels.clear();

        if(mLevelLimit > 0){
            for (int index = 0; index < mLevelLimit / 10; index++) {
                int bottomLevel = (index == 0) ? 1 : index * 10;
                int topLevel = (index + 1) * 10;

                QuickAccessFreeSpellsViewModel viewModel = new QuickAccessFreeSpellsViewModel(bottomLevel, topLevel, this);
                result.add(viewModel);
                quickAccessViewModels.add(viewModel);

                if(index == 0){
                    viewModel.setSelected(true);
                    selectedViewModel = viewModel;
                }
            }
        }

        setViewModels(result);
    }

    @Override
    public void onQuickAccessFreeSpell(QuickAccessFreeSpellsViewModel viewModel) {
        selectedViewModel = viewModel;

        for (QuickAccessViewModel quickAccessViewModel : quickAccessViewModels) {
            if(quickAccessViewModel != viewModel){
                quickAccessViewModel.setSelected(false);
            }
        }

        SpellFilterFactory.SpellFilter levelWindowFilter = viewModel.getFilter(mSpellFilterFactory);
        mListener.onQuickAccessSelected(levelWindowFilter);
    }

    public SpellFilterFactory.SpellFilter getCurrentQuickAccessFilter() {
        return selectedViewModel.getFilter(mSpellFilterFactory);
    }

    public interface Listener {
        void onQuickAccessSelected(SpellFilterFactory.SpellFilter quickAccessFilter);
    }
}
