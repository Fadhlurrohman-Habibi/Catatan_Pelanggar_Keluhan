package com.example.catatan_pelanggar_keluhan.bottomsheet.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AbsListView;
import android.widget.ListView;

public class BottomSheetListView extends ListView {

    public BottomSheetListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (canScrollVertically(this)) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return super.onTouchEvent(ev);
    }

    private boolean canScrollVertically(AbsListView view) {
        boolean bisaScroll = false;

        if (view != null && view.getChildCount() > 0) {
            boolean isOnTop = view.getFirstVisiblePosition() != 0 || view.getChildAt(0).getTop() != 0;
            boolean isAllitemsVisible = isOnTop && view.getLastVisiblePosition() == getChildCount();

            if (isOnTop || isAllitemsVisible) {
                bisaScroll = true;
            }
        }
        return bisaScroll;
    }
}
