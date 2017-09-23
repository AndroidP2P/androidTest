package com.app.frame.ui.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
/**
 * Created by kanxue on 2016/1/14.
 */
public class AppBaseGeTuiReciever extends BroadcastReceiver
{
    public static StringBuilder payloadData = new StringBuilder();
    @Override
    public void onReceive(Context context, Intent intent)
    {
//        Bundle bundle = intent.getExtras();
//        switch (bundle.getInt(PushConsts.CMD_ACTION))
//        {
//            case PushConsts.GET_MSG_DATA:
//                // 获取透传数据
//                // String appid = bundle.getString("appid");
//                byte[] payload = bundle.getByteArray("payload");
//                String taskid = bundle.getString("taskid");
//                String messageid = bundle.getString("messageid");
//                break;
//            case PushConsts.GET_CLIENTID:
////              获取ClientID(CID)
////              第三方应用需要将CID上传到第三方服务器，并且将当前用户帐号和CID进行关联，以便日后通过用户帐号查找CID进行消息推送
//                String cid = bundle.getString("clientid");
//                break;
//            case PushConsts.THIRDPART_FEEDBACK:
//
//                break;
//            default:
//                break;
//        }
    }
}

