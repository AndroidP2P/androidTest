package com.app.frame.ui.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.app.frame.http.NormalRequest.HttpUtils;

public class NetStateChangeDataReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // 判断当网络状态改变啊的时候去更新数据
        if (HttpUtils.isNetworkConnected(context)) {
            //发送全局广播，来水了。
            Intent netStateIntent = new Intent("netWorkIsAvaliable");
            context.sendBroadcast(netStateIntent);
        }
    }
}
