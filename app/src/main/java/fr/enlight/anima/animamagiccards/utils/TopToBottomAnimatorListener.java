package fr.enlight.anima.animamagiccards.utils;


import android.animation.Animator;
import android.view.View;

public class TopToBottomAnimatorListener extends VisibilityAnimatorListener {

    public TopToBottomAnimatorListener(View view, boolean visibleOnEnd) {
        super(view, visibleOnEnd);
    }

    @Override
    public void onAnimationStart(Animator animator) {
        BindingAdapters.setMargin(view, 0,0,0, view.getMeasuredHeight());
        super.onAnimationStart(animator);
    }

    @Override
    public void onAnimationEnd(Animator animator) {
        super.onAnimationEnd(animator);
        BindingAdapters.setMargin(view, 0,0,0, 0);
    }
}
