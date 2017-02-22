package fr.enlight.anima.animamagiccards.utils.bindingrecyclerview;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 *
 */
public class RecyclerViewBindingAdapter {

    @BindingAdapter("viewModels")
    public static void setViewModels(RecyclerView recyclerView, List<? extends BindableViewModel> viewModels){
        if(recyclerView.getAdapter() == null){
            BindingRecyclerAdapter bindingRecyclerAdapter = new BindingRecyclerAdapter();
            bindingRecyclerAdapter.setViewModels(viewModels);
            recyclerView.setAdapter(bindingRecyclerAdapter);

        } else if (recyclerView.getAdapter() instanceof BindingRecyclerAdapter){
            BindingRecyclerAdapter bindingRecyclerAdapter = (BindingRecyclerAdapter) recyclerView.getAdapter();
            bindingRecyclerAdapter.setViewModels(viewModels);
        }
    }

}
