package com.app.frame.login;

import android.content.Context;

import com.app.kernel.AppEpplication.AppApplication;
import com.app.libs.SPUtils;

/**
 * Created by kanxue on 2016/12/8.
 */
public class ManBuLoginParamConstant {
    private static Context mContext= AppApplication.getInstance().getApplicationContext();
    //public whether it need to login
    public static void setWhetherAppNeedToLoginState(boolean flag){
        SPUtils.put(mContext,"appLoginState",flag);
    }
    public static boolean getWhetherAppNeedToLoginState(){
        return (boolean) SPUtils.get(mContext,"appLoginState",false);
    }
}
