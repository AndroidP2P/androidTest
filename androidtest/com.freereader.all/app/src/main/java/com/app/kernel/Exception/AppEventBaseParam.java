package com.app.kernel.Exception;

import android.os.Handler;
import android.os.Message;

/**
 * Created by kanxue on 2016/12/1.
 */
public interface AppEventBaseParam {
     int EVENT_SHOWDATA=0x003;
     int EVENT_TRANSLATE_DATA=0x002;
     int EVENT_SAVE_DATA=0x001;
     String NET_STATE_IS_CONNECTED="netWorkIsAvaliable";
     String EVENT_FAILURE_FOR_NET_WORKE="false";
     String EVENT_SUCCESS="success";
     String EVENT_FALSE="false";
     String EVENT_SUCCESS_NUMBER="1";
     String EVENT_FALSE_NUMBER="0";
     //define a global invoke interface
     interface LoadCallBack{
          void load(Object object);
     }
}
