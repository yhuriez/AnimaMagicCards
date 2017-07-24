package fr.enlight.anima.animamagiccards.ui.other;

import fr.enlight.anima.animamagiccards.MainApplication;
import fr.enlight.anima.animamagiccards.views.BindingDialogFragment;
import fr.enlight.anima.animamagiccards.views.viewmodels.DialogViewModel;

/**
 * Created by enlight on 16/07/2017.
 */

public class AboutUsDialogFragment extends BindingDialogFragment {

    @Override
    public DialogViewModel createViewModel() {
        String locale = MainApplication.getDefSystemLanguage();
        return new AboutUsViewModel(locale);
    }
}
