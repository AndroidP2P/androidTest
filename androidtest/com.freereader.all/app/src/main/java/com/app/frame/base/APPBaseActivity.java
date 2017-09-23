package com.app.frame.base;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;

import com.app.frame.ui.topbar.AppTopBarView;
import com.app.kernel.AppEpplication.AppApplication;
import com.app.kernel.Exception.AppEventBaseParam;
import com.app.libs.KeyBoardUtils;
import com.freereader.all.R;


public abstract class APPBaseActivity extends FragmentActivity implements OnClickListener,AppEventBaseParam
{
    public AppTopBarView topBarView;
    public static APPBaseActivity instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppApplication.getInstance().addActivityToDataList(this);
    }

    // get the instance of APPBaseActivity
    public void initTopBarView(AppTopBarView mainBarView) {
        this.topBarView=mainBarView;
        topBarView.setTopBarBackClickEvent(this);
        topBarView.setTopBarTitle(getResources().getResourceName(R.string.manbu_actionbar_title));
        topBarView.setTopBarRightOperateEvent(this);
    }

    public AppTopBarView getTopBarView(){
        return topBarView;
    }

    //点击空白区关闭键盘
    public void closeInputMethodWhenClickOutSide(View touchView, final Context mContext){
        touchView.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                // TODO Auto-generated method stub
                InputMethodManager imm= KeyBoardUtils.getInputMethodManager(mContext);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                }
                return true;
            }
        });
    }

    private String broadCastRecieverAction = null;
    private BroadcastReceiver broadCastReciever = null;
    private IntentFilter broadCastIntentFilter = null;

    // broadcast
    public void initManBuDataChangeReciever(Context context, String actionAddress) {
        if (context != null && actionAddress != null) {
            broadCastRecieverAction = actionAddress;
            broadCastIntentFilter = new IntentFilter(actionAddress);
            broadCastReciever = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    displayBroadcastMessage(context,intent);
                }
            };
            context.registerReceiver(broadCastReciever, broadCastIntentFilter);
        }
    }


    public void displayBroadcastMessage(Context context, Intent intent){}
    //发送广播
    public void sendAppBroadCastMessage(Context context, Intent intentAction) {
        if (context != null && intentAction != null)
            context.sendBroadcast(intentAction);
    }

    //默认发送的广播
    public void sendDefaultManBuRSBroadCastMessage(Context context) {
        if (broadCastRecieverAction != null)
            context.sendBroadcast(new Intent(broadCastRecieverAction));
    }

    //注销广播
    public void unRegisterManBuRSBroadCastReciever(Context context) {
        if (context != null && broadCastReciever != null)
            context.unregisterReceiver(broadCastReciever);
    }


    // base abstract method
    public abstract void initListeners();
    public abstract void initData();
    public abstract void initViews();

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.manbu_Back:
                finish();
        }
    }
}
