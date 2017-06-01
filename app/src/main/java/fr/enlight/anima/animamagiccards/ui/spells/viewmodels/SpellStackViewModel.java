package fr.enlight.anima.animamagiccards.ui.spells.viewmodels;


import android.databinding.ObservableBoolean;

import com.loopeer.cardstack.CardStackView;

import java.util.List;

import fr.enlight.anima.animamagiccards.views.CustomCardStackView;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.animamagiccards.views.viewmodels.ListBindableViewModel;

public class SpellStackViewModel extends ListBindableViewModel {

    public final ObservableBoolean stackVisible = new ObservableBoolean(false);

    private final CardStackView.ItemExpendListener expandListener;
    private final CustomCardStackView.ItemSelectionListener selectionListener;

    public SpellStackViewModel(CardStackView.ItemExpendListener listener, CustomCardStackView.ItemSelectionListener selectionListener) {
        this.expandListener = listener;
        this.selectionListener = selectionListener;
    }

    public boolean isMessageMode(){
        List<BindableViewModel> viewModels = getViewModels();
        return stackVisible.get() && viewModels != null && viewModels.isEmpty();
    }

    public CardStackView.ItemExpendListener getExpandListener(){
        return expandListener;
    }

    public CustomCardStackView.ItemSelectionListener getSelectionListener(){
        return selectionListener;
    }
}
