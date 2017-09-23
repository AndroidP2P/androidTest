package com.app.frame.ui.broadcast;

import android.content.Context;

import com.app.kernel.AppEpplication.AppApplication;
import com.app.libs.SPUtils;

/**
 * Created by kanxue on 2016/12/9.
 */
public class GeTuiUtils {
    private static Context mContext= AppApplication.getInstance().
            getApplicationContext();
//    private static GeTuiUtils instance;
//    private static PushManager geTuiManager;
//    static final String tag_cid="app_getui_cid";
//    public static GeTuiUtils getInstance(){
//        if(instance==null){
//            instance=new GeTuiUtils();
//        }
//        if(geTuiManager==null){
//            geTuiManager=PushManager.getInstance();
//        }
//        return instance;
    }

//    public void initPushManager(){
//        geTuiManager.initialize(mContext);
//        saveCIDToLocal();
//    }
//
//    public void stopPushManager(){
//       geTuiManager.stopService(mContext);
//    }
//
//    public  String getClientCID(){
//        return geTuiManager.getClientid(mContext);
//    }
//
//    private static void saveCIDToLocal(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String cid_temp=instance.getClientCID();
//                SPUtils.put(mContext,tag_cid,cid_temp);
//            }
//        }).start();
//    }
//
//    public String getCIDFromLocal(){
//        return (String) SPUtils.get(mContext,tag_cid,"");
//    }
//}
