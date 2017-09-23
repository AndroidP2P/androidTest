package com.app.frame.login;

import android.content.ContentValues;
import android.text.TextUtils;

import com.app.frame.http.AppProtocalParamConstant;
import com.app.frame.http.NormalRequest.HttpUtils;
import com.app.frame.http.NormalRequest.AppNetRequestFactory;
import com.app.frame.ui.toast.T;
import com.app.kernel.Exception.Log.AppExceptionLogUtils;
import com.app.libs.JsonDealwithTool;
import com.app.libs.StringUtils;

import org.json.JSONObject;

/**
 * Created by kanxue on 2016/12/8.
 */
public class ManbuLoginModel {

    private static String MANBU_LOGIN_API="/doctor/api/login";
    public static void dealWithoginResultFromJson(String usrName,String result, HttpUtils.doPostCallBack callBack){

        try {
            JSONObject loginResult=new JSONObject(result);
            String resultLoginForJson= JsonDealwithTool.getJsonDataValueByKey("result",loginResult);
            String loginResultCode=JsonDealwithTool.getJsonDataValueByKey("result",resultLoginForJson);
            int RESULT_INDICATE=loginFeedBackTag(loginResultCode);
            if(RESULT_INDICATE==USR_LOGIN_SUCCESS){
                String appHost=JsonDealwithTool.getJsonDataValueByKey("host",resultLoginForJson,"");
                if(!TextUtils.isEmpty(appHost)){
                    AppProtocalParamConstant.setBalanceUrlToLocal(appHost);
                    AppProtocalParamConstant.changeBaseServerAPI(appHost);
                }
                //存储信息
                String id=JsonDealwithTool.getJsonDataValueByKey("doctorID",resultLoginForJson,"");
                //医生交流群ID
                String chattingId=JsonDealwithTool.getJsonDataValueByKey("PDGroupID",resultLoginForJson,"");
                //设置已经登录
                ManBuLoginParamConstant.setWhetherAppNeedToLoginState(true);
                callBack.onRequestComplete(RESULT_SUCCESS);
                return;
            }else if(RESULT_INDICATE==USR_NOT_EXIST){
                callBack.onRequestComplete(RESULT_FALSE_USR_NOT_EXIST);
            }else if(RESULT_INDICATE==USR_PWD_FAILURE){
                callBack.onRequestComplete(RESULT_FALSE_USR_PWD_WRONG);
            }else {
                callBack.onRequestComplete(RESULT_FALSE);
            }
        }catch (Exception e){
            AppExceptionLogUtils.LOG_FOR_STL(e);
            callBack.onRequestComplete(RESULT_FALSE);
        }
    }

    public static int USR_NOT_EXIST= 0x002;
    public static int USR_PWD_FAILURE=0x003;
    public static int USR_LOGIN_SUCCESS=0x004;
    public static String RESULT_FALSE_NO_SERVER="fail_no_server";
    public static String RESULT_FALSE="fail";
    public static String RESULT_SUCCESS="success";
    public static String RESULT_FALSE_USR_NOT_EXIST="fail_usr_not_exist";
    public static String RESULT_FALSE_USR_PWD_WRONG="fail_usr_pwd_wrong";
    public static void doLogin(final String usrName, String publicToken, String cid, final HttpUtils.doPostCallBack callBack){
        // 当CID 不存在时置空
        if(TextUtils.isEmpty(cid)){
            cid="";
        }
        ContentValues mLoginValues=new ContentValues();
        mLoginValues.put("userName",usrName);
        mLoginValues.put("userToken",publicToken);
        mLoginValues.put("clientID",cid);
        String loginParams= AppNetRequestFactory.paramsCombind(mLoginValues);
        try {
            HttpUtils.doPostAsyn(AppProtocalParamConstant.BASE_SERVER_API + MANBU_LOGIN_API, loginParams,
                    new HttpUtils.doPostCallBack() {
                @Override
                public void onRequestComplete(String result) {
                    if(result==null){
                        callBack.onRequestComplete(RESULT_FALSE);
                        return;
                    }
                    if(result.equals(RESULT_FALSE_NO_SERVER)){
                        callBack.onRequestComplete(RESULT_FALSE);
                        return;
                    }
                    dealWithoginResultFromJson(usrName,result,callBack);
                }
            });
        } catch (Exception e) {
            AppExceptionLogUtils.LOG_FOR_STL(e);
        }
        return;
    }

    public static boolean checkUsrLoginParam(String usrName,String usrPwd,String cid){

        if(TextUtils.isEmpty(usrName)){
            T.showShort("用户姓名不能为空");
            return false;
        }

        if(TextUtils.isEmpty(usrPwd)){
            T.showShort("用户密码不能为空");
            return false;
        }

        if(TextUtils.isEmpty(cid)){
            T.showShort("登录参数CID异常");
            AppExceptionLogUtils.LOG_FOR_STL("登录参数CID异常");
//            //再次初始化CID
//            GeTuiUtils.getInstance().initPushManager();
            return true;
        }

        if (!StringUtils.matchForUserPhoneNumber(usrName)){
            T.showShort("不是合法的电话号码！");
            return false;
        }

        return true;
    }

    public static int loginFeedBackTag(String tag){
        if(tag.equals("-1")){
            return USR_NOT_EXIST;
        }else if(tag.equals("0")){
            return USR_PWD_FAILURE;
        }
        return USR_LOGIN_SUCCESS;
    }

}
