package com.app.libs;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

import java.lang.reflect.Field;

/**
 * Created by kanxue on 2016/12/8.
 */
public class LayoutRejustClass {
    static int layoutOffset_value=0;
    public static void rejustLayoutView(final View rootView, final View customView, final Context mContext){
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
            @Override
            public void onGlobalLayout() {
                // TODO Auto-generated method stub
                Rect r=new Rect();
                rootView.getWindowVisibleDisplayFrame(r);
                int screenHeight=rootView.getRootView().getHeight();
                int screen_right=rootView.getRight();
                int heightDiff=screenHeight-(r.bottom-r.top);
                if(heightDiff>200)
                {
                    int statusBarHeight = 0;
                    try {
                        Class<?> c = Class.forName("com.android.internal.R$dimen");
                        Object obj = c.newInstance();
                        Field field = c.getField("status_bar_height");
                        int x = Integer.parseInt(field.get(obj).toString());
                        statusBarHeight = mContext.getResources().getDimensionPixelSize(x);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    int realKeyboardHeight = heightDiff - statusBarHeight;
                    int keyboard_x=0;
                    int keyboard_y=screenHeight-realKeyboardHeight;
                    int[] login_position=new int[2];
                    customView.getLocationOnScreen(login_position);
                    int loginButton_y=login_position[1];
                    int loginButton_bottom=customView.getHeight()+loginButton_y;
                    layoutOffset_value=Math.abs(loginButton_bottom-keyboard_y);
                    rootView.layout(0, -layoutOffset_value,screen_right, screenHeight-layoutOffset_value);
                }else
                {
                    rootView.layout(0, 0, screen_right, screenHeight);
                }
            }
        });
    }
}
