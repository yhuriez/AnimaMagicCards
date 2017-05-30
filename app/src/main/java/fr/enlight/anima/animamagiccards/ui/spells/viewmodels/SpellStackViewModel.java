package fr.enlight.anima.animamagiccards.ui.spells.viewmodels;


import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.loopeer.cardstack.CardStackView;

import java.util.List;

import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.animamagiccards.views.viewmodels.ListBindableViewModel;
import fr.enlight.anima.animamagiccards.views.viewmodels.RecyclerViewModel;

public class SpellStackViewModel extends RecyclerViewModel {

    public final ObservableBoolean stackVisible = new ObservableBoolean(false);

    public boolean isMessageMode(){
        List<BindableViewModel> viewModels = getViewModels();
        return stackVisible.get() && viewModels != null && viewModels.isEmpty();
    }
}
