package fr.enlight.anima.animamagiccards.ui;


import android.content.Context;
import android.databinding.Bindable;
import android.support.v4.content.res.ResourcesCompat;
import android.view.Gravity;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import fr.enlight.anima.animamagiccards.BR;
import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.utils.BindingAdapters;
import fr.enlight.anima.animamagiccards.views.viewmodels.RecyclerViewModel;

public class HomePageViewModel extends RecyclerViewModel {

    private String currentTitle;

    private ViewSwitcher.ViewFactory switcherFactory;

    public ViewSwitcher.ViewFactory getSwitcherFactory(final Context context){
        if(switcherFactory == null) {
            switcherFactory = new ViewSwitcher.ViewFactory() {

                @Override
                public TextView makeView() {
                    TextView textView = new TextView(context);
                    textView.setGravity(Gravity.CENTER);
                    textView.setTextSize(context.getResources().getDimension(R.dimen.textSize_medium));
                    textView.setTextColor(ResourcesCompat.getColor(context.getResources(), android.R.color.white, null));
                    BindingAdapters.setFont(textView, context.getString(R.string.spell_title_font));
                    return textView;
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
