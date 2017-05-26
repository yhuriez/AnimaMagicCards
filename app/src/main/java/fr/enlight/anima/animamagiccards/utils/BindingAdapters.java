package fr.enlight.anima.animamagiccards.utils;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Typeface;
import android.support.annotation.DrawableRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.List;

import fr.enlight.anima.animamagiccards.views.bindingrecyclerview.BindableViewModel;


public class BindingAdapters {

    @BindingAdapter("setVisible")
    public static void setVisible(View view, boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("backgroundRes")
    public static void setBackgroundRes(View view, @DrawableRes int drawableRes){
        if(drawableRes > 0){
            view.setBackgroundResource(drawableRes);
        }
    }

    @BindingAdapter("customFont")
    public static void setFont(TextView view, String font){
        if(font == null){
            return;
        }

        Typeface type = TypefaceUtils.getTypeFace(view.getContext(), font);
        view.setTypeface(type);
    }

    @BindingAdapter("viewModels")
    public static void setViewModels(LinearLayout linearLayout, List<BindableViewModel> viewModels){
        if(viewModels == null){
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
    public static void setMargin(View view, float left, float top, float right, float bottom){
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();

        int leftMargin = (left <= 0 && marginLayoutParams.leftMargin > 0) ? marginLayoutParams.leftMargin : Math.round(left);
        int topMargin = (top <= 0 && marginLayoutParams.topMargin > 0) ? marginLayoutParams.topMargin : Math.round(top);
        int rightMargin = (right <= 0 && marginLayoutParams.rightMargin > 0) ? marginLayoutParams.rightMargin : Math.round(right);
        int bottomMargin = (bottom <= 0 && marginLayoutParams.bottomMargin > 0) ? marginLayoutParams.bottomMargin : Math.round(bottom);

        marginLayoutParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);
        view.setLayoutParams(marginLayoutParams);
    }

    @BindingAdapter("adapter")
    public static void setAdapter(Spinner spinner, SpinnerAdapter adapter){
        spinner.setAdapter(adapter);
    }

    @BindingAdapter("animate")
    public static void setTextSwitcherAnimation(TextSwitcher textSwitcher, boolean baseAnimation){
        if(baseAnimation){
            Animation in = AnimationUtils.loadAnimation(textSwitcher.getContext(), android.R.anim.slide_in_left);
            Animation out = AnimationUtils.loadAnimation(textSwitcher.getContext(), android.R.anim.slide_out_right);

            // set the animation type of textSwitcher
            textSwitcher.setInAnimation(in);
            textSwitcher.setOutAnimation(out);
        }
    }

    @BindingAdapter("factoryCompat")
    public static void setTextSwitcherFactoryCompat(TextSwitcher textSwitcher, ViewSwitcher.ViewFactory factory){
        textSwitcher.removeAllViews();
        textSwitcher.setFactory(factory);
    }
}
