package com.app.frame.base;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.app.frame.ui.toast.T;
import com.app.kernel.Exception.AppEventBaseParam;


/**
 * Created by kanxue on 2016/1/13.
 */
abstract public class APPBaseFragment extends Fragment implements View.OnClickListener,AppEventBaseParam ,AdapterView.OnItemClickListener{
    private String broadCastRecieverAction = null;
    private BroadcastReceiver broadCastReciever = null;
    private IntentFilter broadCastIntentFilter = null;
    private boolean isFragmentVisualble=false;
    public void setFragmentVisualble(boolean flag){
        isFragmentVisualble=flag;
    }
    public boolean getFragmentVisualble(){
        return isFragmentVisualble;
    }
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

    @Override
    public void onResume() {
        super.onResume();
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
    public abstract void initViews();
    public abstract void initListeners();
    public abstract void initData();
    @Override
    public void onClick(View v) {
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }
}
