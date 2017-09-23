package com.app.frame.start;

import com.app.kernel.AppEpplication.AppApplication;
import com.app.libs.SPUtils;

/**
 * Created by kanxue on 2016/12/2.
 */
public class AppStartParamConstant {

    //set whether it need to start navigate,true is no navigation false navigation
    public static void setNavigationState(boolean flag){
        SPUtils.put(AppApplication.getInstance().getApplicationContext(),"appNavigateState",flag);
    }

    public static boolean getNavigationState(){
       return (boolean) SPUtils.get(AppApplication.getInstance()
               .getApplicationContext(),"appNavigateState",false);
    }

    //set whether it need to show advertisement,true show advertisement,flase don't show
    public static void setAdvertisementState(boolean flag){
        SPUtils.put(AppApplication.getInstance().getApplicationContext(),"appAdvertisement",flag);
    }

    public static boolean getAdvertisementState(){
       return (boolean) SPUtils.get(AppApplication.getInstance().
               getApplicationContext(),"appAdvertisement",false);
    }

}
