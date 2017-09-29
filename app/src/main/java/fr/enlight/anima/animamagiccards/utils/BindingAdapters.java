package fr.enlight.anima.animamagiccards.utils;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.transition.TransitionManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.loopeer.cardstack.CardStackView;

import java.util.List;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;


public class BindingAdapters {

    @BindingAdapter("setVisible")
    public static void setVisible(View view, boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("setVisibleWithAlpha")
    public static void setVisibleWithAlpha(View view, boolean visible) {
        if (view.getVisibility() == View.VISIBLE && !visible
                || view.getVisibility() == View.GONE && visible) {
            int measuredHeight = view.getMeasuredHeight();
            if (measuredHeight == 0 && !visible) {
                view.setVisibility(View.INVISIBLE);
                view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                    @Override
                    public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                        view.removeOnLayoutChangeListener(this);
                        view.setVisibility(View.GONE);
                    }
                });
            } else {
                view.animate().alpha(visible ? 1.0f : 0.0f)
                        .setListener(new VisibilityAnimatorListener(view, visible))
                        .start();
            }
        }
    }

    @BindingAdapter("setVisibleWithTranslation")
    public static void setVisibleWithTranslate(View view, boolean visible) {
        if (view.getVisibility() == View.VISIBLE && !visible
                || view.getVisibility() == View.GONE && visible) {
            int measuredHeight = view.getMeasuredHeight();
            if (measuredHeight == 0 && !visible) {
                view.setVisibility(View.INVISIBLE);
                view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                    @Override
                    public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                        view.removeOnLayoutChangeListener(this);
                        view.setVisibility(View.GONE);
                    }
                });
            } else {
                int fromY = visible ? -measuredHeight : 0;
                int toY = visible ? 0 : -measuredHeight;

                TranslateAnimation anim = new TranslateAnimation(0, 0, fromY, toY);
                anim.setDuration(500);
                anim.setAnimationListener(new VisibilityAnimationListener(view, visible));
                anim.setFillAfter(true);
                view.startAnimation(anim);
            }
        }
    }

    @BindingAdapter(value = {"setVisibleAnimation", "withCLayout"})
    public static void setVisibleAnimation(View view, boolean visible, ConstraintLayout constraintLayout) {
        ConstraintSet endingSet = new ConstraintSet();
        if(visible){
            endingSet.clone(view.getContext(), R.layout.view_spells_filters_open);
        } else {
            endingSet.clone(view.getContext(), R.layout.view_spells_filters);
        }

        TransitionManager.beginDelayedTransition(constraintLayout);
        endingSet.applyTo(constraintLayout);
    }

    @BindingAdapter("backgroundRes")
    public static void setBackgroundRes(View view, @DrawableRes int drawableRes) {
        if (drawableRes > 0) {
            view.setBackgroundResource(drawableRes);
        }
    }

    @BindingAdapter("customFont")
    public static void setFont(TextView view, String font) {
        if (font == null) {
            return;
        }

        Typeface type = TypefaceUtils.getTypeFace(view.getContext(), font);
        view.setTypeface(type);
    }

    @BindingAdapter("viewModels")
    public static void setViewModels(LinearLayout linearLayout, List<BindableViewModel> viewModels) {
        if (viewModels == null) {
            return;
        }
        linearLayout.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(linearLayout.getContext());
        for (BindableViewModel viewModel : viewModels) {
            ViewDataBinding binding = DataBindingUtil.inflate(inflater, viewModel.getLayoutRes(), linearLayout, false);
            binding.setVariable(viewModel.getVariableId(), viewModel);
            linearLayout.addView(binding.getRoot());
        }
    }

    @BindingAdapter(value = {"layout_marginLeft", "layout_marginTop", "layout_marginRight", "layout_marginBottom"}, requireAll = false)
    public static void setMargin(View view, float left, float top, float right, float bottom) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();

        int leftMargin = (left <= 0 && marginLayoutParams.leftMargin > 0) ? marginLayoutParams.leftMargin : Math.round(left);
        int topMargin = (top <= 0 && marginLayoutParams.topMargin > 0) ? marginLayoutParams.topMargin : Math.round(top);
        int rightMargin = (right <= 0 && marginLayoutParams.rightMargin > 0) ? marginLayoutParams.rightMargin : Math.round(right);
        int bottomMargin = (bottom <= 0 && marginLayoutParams.bottomMargin > 0) ? marginLayoutParams.bottomMargin : Math.round(bottom);

        marginLayoutParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);
        view.setLayoutParams(marginLayoutParams);
    }

    @BindingAdapter("adapter")
    public static void setAdapter(Spinner spinner, SpinnerAdapter adapter) {
        spinner.setAdapter(adapter);
    }

    @BindingAdapter("animate")
    public static void setTextSwitcherAnimation(TextSwitcher textSwitcher, boolean baseAnimation) {
        if (baseAnimation) {
            Animation in = AnimationUtils.loadAnimation(textSwitcher.getContext(), android.R.anim.slide_in_left);
            Animation out = AnimationUtils.loadAnimation(textSwitcher.getContext(), android.R.anim.slide_out_right);

            // set the animation type of textSwitcher
            textSwitcher.setInAnimation(in);
            textSwitcher.setOutAnimation(out);
        }
    }

    @BindingAdapter("factoryCompat")
    public static void setTextSwitcherFactoryCompat(TextSwitcher textSwitcher, ViewSwitcher.ViewFactory factory) {
        textSwitcher.removeAllViews();
        textSwitcher.setFactory(factory);
    }

    @BindingAdapter("scrollTo")
    public static void scrollTo(final RecyclerView recyclerView, final int scrollPosition){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.scrollToPosition(scrollPosition);
            }
        }, 100);
    }

    @BindingAdapter("toHtml")
    public static void setTextToHtml(TextView textView, @StringRes int value){
        String strValue = textView.getContext().getString(value);
        textView.setText(Html.fromHtml(strValue));
    }

    @BindingAdapter("stackHeaderHeightCustom")
    public static void setStackHeaderHeight(ViewGroup view, int dimenInPixel){
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if(layoutParams instanceof CardStackView.LayoutParams){
            ((CardStackView.LayoutParams) layoutParams).mHeaderHeight = dimenInPixel;
        }
    }

}
