package com.loopeer.cardstack;

import android.content.Context;
import android.util.AttributeSet;

public class CustomCardStackView extends CardStackView {

    private ItemSelectionListener mItemSelectionListener;
    private int mCurrentSelectedPosition;

    public CustomCardStackView(Context context) {
        super(context);
    }

    public CustomCardStackView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomCardStackView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setItemSelectionListener(ItemSelectionListener itemSelectionListener) {
        this.mItemSelectionListener = itemSelectionListener;
    }

    @Override
    public void setSelectPosition(int selectPosition) {
        if(mItemSelectionListener != null){
            mCurrentSelectedPosition = selectPosition;
            mItemSelectionListener.onItemSelected(selectPosition);
        }
        super.setSelectPosition(selectPosition);
    }

    public void unselectCard(){
        if(mCurrentSelectedPosition >= 0){
            performItemClick(getViewHolder(mCurrentSelectedPosition));
        }
    }

    public interface ItemSelectionListener{
        void onItemSelected(int position);
    }
}
