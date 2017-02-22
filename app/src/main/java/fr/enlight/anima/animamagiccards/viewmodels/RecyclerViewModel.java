package fr.enlight.anima.animamagiccards.viewmodels;

import android.support.v7.widget.RecyclerView;

/**
 *
 */
public class RecyclerViewModel extends ListBindableViewModel {

    private RecyclerView.LayoutManager mLayoutManager;


    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return mLayoutManager;
    }
}
