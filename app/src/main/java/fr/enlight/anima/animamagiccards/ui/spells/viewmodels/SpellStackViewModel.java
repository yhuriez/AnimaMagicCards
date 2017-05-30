package fr.enlight.anima.animamagiccards.ui.spells.viewmodels;


import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.loopeer.cardstack.CardStackView;

import java.util.List;

import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.animamagiccards.views.viewmodels.ListBindableViewModel;

public class SpellStackViewModel extends ListBindableViewModel implements CardStackView.ItemExpendListener {

    public final ObservableBoolean stackVisible = new ObservableBoolean(false);

    public boolean isMessageMode(){
        List<BindableViewModel> viewModels = getViewModels();
        return stackVisible.get() && viewModels != null && viewModels.isEmpty();
    }

    public CardStackView.ItemExpendListener getExpandListener(){
        return this;
    }

    @Override
    public void onItemExpend(boolean expend) {
        // Nothing to do for now
    }
}
