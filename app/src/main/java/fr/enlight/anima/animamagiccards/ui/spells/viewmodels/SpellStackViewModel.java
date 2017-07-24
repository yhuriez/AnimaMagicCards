package fr.enlight.anima.animamagiccards.ui.spells.viewmodels;


import android.content.Context;
import android.databinding.ObservableBoolean;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.res.ResourcesCompat;

import com.loopeer.cardstack.CardStackView;

import java.util.List;

import com.loopeer.cardstack.CustomCardStackView;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.animamagiccards.views.viewmodels.ListBindableViewModel;

public class SpellStackViewModel extends ListBindableViewModel {

    public final ObservableBoolean stackVisible = new ObservableBoolean(false);

    private final @DrawableRes int cardBackground;
    private final @ColorRes int backgroundTextColor;

    private final CardStackView.ItemExpendListener expandListener;
    private final CustomCardStackView.ItemSelectionListener selectionListener;

    public SpellStackViewModel(CardStackView.ItemExpendListener listener,
                               CustomCardStackView.ItemSelectionListener selectionListener,
                               @DrawableRes int cardBackground,
                               @ColorRes int backgroundTextColor) {
        this.expandListener = listener;
        this.selectionListener = selectionListener;
        this.cardBackground = cardBackground;
        this.backgroundTextColor = backgroundTextColor;
    }

    public boolean isMessageMode(){
        List<? extends BindableViewModel> viewModels = getViewModels();
        return stackVisible.get() && viewModels != null && viewModels.isEmpty();
    }

    public Drawable getCardBackground(Context context){
        return ResourcesCompat.getDrawable(context.getResources(), cardBackground,null);
    }

    public @ColorInt int getBackgroundTextColor(Context context){
        return ResourcesCompat.getColor(context.getResources(), backgroundTextColor,null);
    }

    public CardStackView.ItemExpendListener getExpandListener(){
        return expandListener;
    }

    public CustomCardStackView.ItemSelectionListener getSelectionListener(){
        return selectionListener;
    }
}
