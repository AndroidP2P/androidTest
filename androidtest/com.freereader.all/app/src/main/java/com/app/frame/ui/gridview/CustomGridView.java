package com.app.frame.ui.gridview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by kanxue on 2017/1/13.
 */
public class CustomGridView extends GridView {
    public CustomGridView(Context context) {
        super(context);
    }
    private boolean isScrollViewState=true;
    public void setStateForScrollView(boolean state){
        this.isScrollViewState=state;
    }

    public CustomGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        if(isScrollViewState) {
            int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                    MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);
        }else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

}
