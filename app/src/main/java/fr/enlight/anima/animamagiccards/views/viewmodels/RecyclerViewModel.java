package fr.enlight.anima.animamagiccards.views.viewmodels;

import android.support.v7.widget.RecyclerView;

import com.azoft.carousellayoutmanager.CenterScrollListener;

/**
 *
 */
public class RecyclerViewModel extends ListBindableViewModel {

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.OnScrollListener onScrollListener;


    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return mLayoutManager;
    }

    public void setOnScrollListener(RecyclerView.OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    public RecyclerView.OnScrollListener getOnScrollListener() {
        return onScrollListener;
    }
}
