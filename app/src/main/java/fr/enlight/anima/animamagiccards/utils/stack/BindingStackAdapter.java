package fr.enlight.anima.animamagiccards.utils.stack;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.ViewGroup;

import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.StackAdapter;

import java.util.List;

import fr.enlight.anima.animamagiccards.utils.bindingrecyclerview.BindableViewModel;


public class BindingStackAdapter extends StackAdapter<BindableViewModel> {

    public BindingStackAdapter(Context context) {
        super(context);
    }

    @Override
    protected BindingStackViewHolder onCreateView(ViewGroup parent, int viewType_layoutRes) {
        ViewDataBinding binding = DataBindingUtil.inflate(getLayoutInflater(), viewType_layoutRes, parent, false);
        return new BindingStackViewHolder(binding);
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getLayoutRes();
    }

    @Override
    public void bindView(BindableViewModel data, int position, CardStackView.ViewHolder holder) {
        ((BindingStackViewHolder)holder).setData(data);
    }

    @BindingAdapter("viewModels")
    public static void setViewModels(CardStackView stackView, List<BindableViewModel> viewModels){
        BindingStackAdapter bindingStackAdapter = new BindingStackAdapter(stackView.getContext());
        stackView.setAdapter(bindingStackAdapter);
        bindingStackAdapter.updateData(viewModels);
    }

    @BindingAdapter("expandListener")
    public static void setExpandListener(CardStackView stackView, CardStackView.ItemExpendListener expandListener){
        stackView.setItemExpendListener(expandListener);
    }
}
