package fr.enlight.anima.animamagiccards.ui.spells.viewmodels.quickaccess;

import java.util.List;

import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.animamagiccards.views.viewmodels.RecyclerViewModel;
import fr.enlight.anima.cardmodel.business.SpellFilterFactory;


public abstract class AbstractGroupQAViewModel extends RecyclerViewModel implements QAClickListener {

    private List<QAViewModel> quickAccessViewModels;
    protected QAViewModel selectedViewModel;
    private int mSelectedPosition;
    private boolean mFirstSelected = true;

    private final SpellFilterFactory mSpellFilterFactory;

    private Listener mListener;


    public AbstractGroupQAViewModel(Listener listener) {
        mSpellFilterFactory = new SpellFilterFactory();
        mListener = listener;
    }

    protected abstract List<QAViewModel> createViewModels();

    protected void initViewModels(){
        quickAccessViewModels = createViewModels();

        if(!quickAccessViewModels.isEmpty()) {
            if (mFirstSelected) {
                mSelectedPosition = 0;
            } else {
                mSelectedPosition = quickAccessViewModels.size() - 1;
            }
            selectedViewModel = quickAccessViewModels.get(mSelectedPosition);
            selectedViewModel.setSelected(true);
        }
    }

    public void setFirstSelected(boolean firstSelected) {
        this.mFirstSelected = firstSelected;
    }

    @Override
    public List<? extends BindableViewModel> getViewModels() {
        if(quickAccessViewModels == null){
            initViewModels();
        }
        return quickAccessViewModels;
    }

    @Override
    public void onQuickAccessClicked(QAViewModel viewModel) {
        selectedViewModel = viewModel;

        for (QAViewModel quickAccessViewModel : quickAccessViewModels) {
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

    public int getSelectedPosition() {
        return mSelectedPosition;
    }

    public interface Listener {
        void onQuickAccessSelected(SpellFilterFactory.SpellFilter quickAccessFilter);
    }
}
