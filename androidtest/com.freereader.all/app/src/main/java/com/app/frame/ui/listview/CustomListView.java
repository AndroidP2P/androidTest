package com.app.frame.ui.listview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ListView;
import com.freereader.all.R;
public class CustomListView extends ListView {
    public CustomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void dividerConfig() {
        setDefaultDividerConfig();
    }

    public void setDefaultDividerConfig() {
        setDefaultDivider(getResources().getDrawable(R.drawable.app_ui_divider));
    }

    private boolean isScrollViewState=true;
    public void setStateForScrollView(boolean state){
        this.isScrollViewState=state;
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

    public void setDefaultDivider(Drawable dividerDrawable){
        setDivider(dividerDrawable);
    }

    public void setDefaultDividerHeight(int height){
        setDividerHeight(height);
    }

}
