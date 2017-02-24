package fr.enlight.anima.animamagiccards.views.viewmodels;


public class DialogViewModel {

    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void dismissClicked(){
        listener.onDismiss();
    }

    public interface Listener{
        void onDismiss();
    }
}
