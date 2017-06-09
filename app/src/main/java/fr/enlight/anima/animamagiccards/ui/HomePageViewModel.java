package fr.enlight.anima.animamagiccards.ui;


import android.content.Context;
import android.databinding.Bindable;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import fr.enlight.anima.animamagiccards.BR;
import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.views.viewmodels.RecyclerViewModel;

public class HomePageViewModel extends RecyclerViewModel {

    private String currentTitle;

    private ViewSwitcher.ViewFactory switcherFactory;

    public ViewSwitcher.ViewFactory getSwitcherFactory(final Context context){
        if(switcherFactory == null) {
            switcherFactory = new ViewSwitcher.ViewFactory() {

                @Override
                public TextView makeView() {
                    return (TextView) DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.view_text_book_title, null, false).getRoot();
                }
            };
        }
        return switcherFactory;
    }

    @Bindable
    public String getCurrentTitle() {
        return currentTitle;
    }

    public void setCurrentTitle(String title) {
        String oldTitle = currentTitle;
        this.currentTitle = title;
        if(oldTitle == null || !oldTitle.equalsIgnoreCase(title)){
            notifyPropertyChanged(BR.currentTitle);
        }
    }
}
