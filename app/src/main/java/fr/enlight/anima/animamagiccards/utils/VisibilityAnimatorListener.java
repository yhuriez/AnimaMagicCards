package fr.enlight.anima.animamagiccards.utils;


import android.animation.Animator;
import android.view.View;

public class VisibilityAnimatorListener implements Animator.AnimatorListener {

    protected final View view;
    protected final boolean visibleOnEnd;

    public VisibilityAnimatorListener(View view, boolean visibleOnEnd) {
        this.view = view;
        this.visibleOnEnd = visibleOnEnd;
    }

    @Override
    public void onAnimationStart(Animator animator) {
        view.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAnimationEnd(Animator animator) {
        if(visibleOnEnd){
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAnimationCancel(Animator animator) {
        if(visibleOnEnd){
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAnimationRepeat(Animator animator) {
    }
}
