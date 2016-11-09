package com.reenexample.datepicker;

/**
 * Created by reen on 8/11/16.
 */
public interface ItemTouchHelperAdapter {

    void onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
