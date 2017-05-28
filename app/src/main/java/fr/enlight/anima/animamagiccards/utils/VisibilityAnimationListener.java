package fr.enlight.anima.animamagiccards.utils;


import android.view.View;
import android.view.animation.Animation;

public class VisibilityAnimationListener implements Animation.AnimationListener {

    protected final View view;
    protected final boolean visibleOnEnd;

    public VisibilityAnimationListener(View view, boolean visibleOnEnd) {
        this.view = view;
        this.visibleOnEnd = visibleOnEnd;
    }

    @Override
    public void onAnimationStart(Animation animation) {
        view.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if(visibleOnEnd){
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
