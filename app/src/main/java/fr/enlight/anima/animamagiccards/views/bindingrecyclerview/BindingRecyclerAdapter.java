package fr.enlight.anima.animamagiccards.views.bindingrecyclerview;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class BindingRecyclerAdapter extends RecyclerView.Adapter<BindingRecyclerViewHolder> {

    private final List<BindableViewModel> viewModels = new ArrayList<>();

    private LayoutInflater mInflater;

    @Override
    public BindingRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType_layoutRes) {
        if(mInflater == null){
            mInflater = LayoutInflater.from(parent.getContext());
        }

        ViewDataBinding binding = DataBindingUtil.inflate(mInflater, viewType_layoutRes, parent, false);
        return new BindingRecyclerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(BindingRecyclerViewHolder holder, int position) {
        holder.setViewModel(viewModels.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return viewModels.get(position).getLayoutRes();
    }

    @Override
    public int getItemCount() {
        return viewModels.size();
    }


    public void setViewModels(List<? extends BindableViewModel> models) {
        if(models != null && !models.isEmpty()) {
            viewModels.clear();
            viewModels.addAll(models);

            notifyDataSetChanged();
        }
    }
}
