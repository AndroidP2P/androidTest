package com.app.frame.login.token;

import android.content.Context;
import android.text.TextUtils;

import com.app.frame.http.NormalRequest.AppNetRequestFactory;
import com.app.frame.http.AppProtocalParamConstant;
import com.app.frame.http.NormalRequest.HttpUtils;
import com.app.frame.ui.toast.T;
import com.app.kernel.AppEpplication.AppApplication;
import com.app.libs.JsonDealwithTool;
import com.app.libs.MessageDigestUtils;
import com.app.libs.SPUtils;

/**
 * Created by kanxue on 2016/12/8.
 */
public class ManbuTokenClass {

    //manbuLoginToken API
    private static String MANBU_LOGIN_TOKEN_API="/doctor/api/doctorGetToken";
    public static final String APP_KEY="b5117505b3a6562d6126e5d8134bd348";
    private static String APP_MANBU_TOKEN=null;
    private static Context mContext= AppApplication.getInstance().getApplicationContext();

    // getLoginToken
    public static void getLoginToken(final HttpUtils.doPostCallBack callBack,String params){
        AppNetRequestFactory.getDataNormalByMethod(AppProtocalParamConstant.BASE_SERVER_API
                        + MANBU_LOGIN_TOKEN_API + params,
                new HttpUtils.doPostCallBack() {
                    @Override
                    public void onRequestComplete(String result) {
                        String app_token = getAppTokenFromJson(result);
                        callBack.onRequestComplete(app_token);
                    }
                });
    }

    public static void saveAppPublicTokenToLocal(String appToken){
        SPUtils.put(mContext,"app_manbu_token",appToken);
    }

    public static String getAppPublicTokenFromLocal(){
       return (String) SPUtils.get(mContext,"app_manbu_token","");
    }

    public static String generateLoginPostToken(String pwd,String token){
        return MessageDigestUtils.getMd5(APP_KEY+token+pwd);
    }

    private static String getAppTokenFromJson(String json_result){
        String app_manbu_token= JsonDealwithTool.getJsonDataValueByKey("loginToken",json_result," ");
        if(TextUtils.isEmpty(app_manbu_token)){
            String resultInfo=JsonDealwithTool.getJsonDataValueByKey("result",json_result,"");
            //fail to get token
            if(resultInfo.equals("0")){
                T.showShort("该用户不存在");
            }
        }
        return app_manbu_token;
    }

    public static boolean checkTokenValid(String token){
        if(TextUtils.isEmpty(token)){
            T.showShort("该用户不存在,请注册");
            return false;
        }
        return true;
    }

}
