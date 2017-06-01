package fr.enlight.anima.animamagiccards.views;

import android.content.Context;
import android.util.AttributeSet;

import com.loopeer.cardstack.CardStackView;

public class CustomCardStackView extends CardStackView {

    private ItemSelectionListener mItemSelectionListener;

    public CustomCardStackView(Context context) {
        super(context);
    }

    public CustomCardStackView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomCardStackView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomCardStackView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setItemSelectionListener(ItemSelectionListener itemSelectionListener) {
        this.mItemSelectionListener = itemSelectionListener;
    }

    @Override
    public void setSelectPosition(int selectPosition) {
        if(mItemSelectionListener != null){
            mItemSelectionListener.onItemSelected(selectPosition);
        }
        super.setSelectPosition(selectPosition);
    }

    public interface ItemSelectionListener{
        void onItemSelected(int position);
    }
}
