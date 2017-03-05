package fr.enlight.anima.animamagiccards.utils;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Typeface;
import android.support.annotation.DrawableRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        linearLayout.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(linearLayout.getContext());
        for (BindableViewModel viewModel : viewModels) {
            ViewDataBinding binding = DataBindingUtil.inflate(inflater, viewModel.getLayoutRes(), linearLayout, false);
            binding.setVariable(viewModel.getVariableId(), viewModel);
            linearLayout.addView(binding.getRoot());
        }
    }
}
