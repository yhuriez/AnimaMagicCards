package fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels;

public class WitchspellsAddPathViewModel {

    private final Listener mListener;

    public WitchspellsAddPathViewModel(Listener listener) {
        mListener = listener;
    }

    public void onPathClicked(){
        mListener.onAddPath();
    }

    public interface Listener{
        void onAddPath();
    }
}
