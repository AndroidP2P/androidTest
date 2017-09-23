package com.app.frame.register;

import android.content.ContentValues;
import android.text.TextUtils;

import com.app.frame.http.NormalRequest.AppNetRequestFactory;
import com.app.frame.http.AppProtocalParamConstant;
import com.app.frame.http.NormalRequest.HttpUtils;
import com.app.frame.ui.toast.T;
import com.app.frame.ui.verifycode.AppVerifyCodeClass;
import com.app.kernel.Exception.Log.AppExceptionLogUtils;
import com.app.libs.JsonDealwithTool;
import com.app.libs.StringUtils;

import org.json.JSONObject;

/**
 * Created by kanxue on 2016/12/13.
 */
public class ManbuRegisterModelClass {

    private static final String USER_REGISTER_URI = "/doctor/api/register";
    private static final String USER_CATCH_VERIFY_CODE = "/doctor/api/getIdentifyingCode/phoneNumber=";
    protected static final String REGISTER_SUCCES="success";
    protected static final String REGISTER_FAIL="fail";
    public static String getVerifyCodeUrl(){
        return AppProtocalParamConstant.BASE_SERVER_API+USER_CATCH_VERIFY_CODE;
    }
    //获取
    private static ManbuRegisterModelClass instance;

    public static ManbuRegisterModelClass getInstance() {
        if (instance == null) {
            instance = new ManbuRegisterModelClass();
        }
        return instance;
    }

    public static int checkPostParams(String phone,String firstPwd,String secondPwd,String verifycode){
        if(checkPostParams(phone,firstPwd,secondPwd)==-1){
            return -1;
        }
        AppVerifyCodeClass verifyCodeClass=AppVerifyCodeClass.getInstance();
        if(!verifyCodeClass.checkVerifyCodeNumber(verifycode)){
            return -1;
        }
        return 1;
    }

    public static int checkPostParams(String phone,String firstPwd,String secondPwd){
        if(TextUtils.isEmpty(phone)){
            T.showShort("电话号码不能为空");
            return-1;
        }else if(!StringUtils.matchForUserPhoneNumber(phone)){
            T.showShort("电话号码不正确");
            return -1;
        }else if(TextUtils.isEmpty(firstPwd)){
            T.showShort("密码不能为空");
            return -1;
        }else if(TextUtils.isEmpty(secondPwd)){
            T.showShort("密码不能为空");
            return -1;
        }else if(firstPwd.length()<6){
            T.showShort("密码长度过短，至少6位");
            return -1;
        }else if(secondPwd.length()<6){
            T.showShort("密码长度过短，至少6位");
            return -1;
        }else if(!TextUtils.equals(firstPwd,secondPwd)){
            T.showShort("密码不相等,请检查");
            return -1;
        }
        return 1;
    }

    public static String getVerifyCode(String result){
        try {
            JSONObject verifyCodeJson=new JSONObject(result).getJSONObject("result");
            String code=JsonDealwithTool.getJsonDataValueByKey("code",verifyCodeJson,"");
            String isExist=JsonDealwithTool.getJsonDataValueByKey("isExist",verifyCodeJson,"");
            String random=JsonDealwithTool.getJsonDataValueByKey("random",verifyCodeJson,"");
            if(code.equals("0")){
                T.showShort("验证码发送失败");
                return null;
            }else if(code.equals("-1")){
                T.showShort("发送短信达到上限");
                return null;
            }else if(isExist.equals("1")){
                T.showShort("该电话号码已经注册");
                return null;
            }else if(TextUtils.isEmpty(random)){
                T.showShort("获取了为空的验证码");
                return null;
            }
            T.showShort("获取验证码成功");
            return random;
        }catch (Exception e){
            AppExceptionLogUtils.LOG_FOR_STL(e);
            return null;
        }

    }

    private HttpUtils.doPostCallBack callBack=null;
    //获取信息
    public void doRegister(ContentValues param, final HttpUtils.doPostCallBack callBack) {
        this.callBack=callBack;
        AppNetRequestFactory.postDataNomalMethod(
                AppProtocalParamConstant.BASE_SERVER_API + USER_REGISTER_URI, param, new HttpUtils.doPostCallBack() {
                    @Override
                    public void onRequestComplete(String result) {
                        dealWithRegisterFeedBack(result);
                    }
                });
    }

    //处理信息
    private void dealWithRegisterFeedBack(String result){
        try {
            JSONObject registerResult=new JSONObject(result).getJSONObject("result");
            //feed info
            String isExist= JsonDealwithTool.getJsonDataValueByKey("isExist",registerResult,"");
            String isFinish=JsonDealwithTool.getJsonDataValueByKey("isFinish",registerResult,"");
            String doctorID=JsonDealwithTool.getJsonDataValueByKey("doctorID",registerResult,"");
            if(isExist.equals("1")){
                T.showShort("该用户已注册，请登录");
                callBack.onRequestComplete(REGISTER_FAIL);
                return;
            }
            if(isFinish.equals("0")){
                T.showShort("注册失败");
                callBack.onRequestComplete(REGISTER_FAIL);
                return;
            }else if(isFinish.equals("-1")){
                T.showShort("验证码超时");
                callBack.onRequestComplete(REGISTER_FAIL);
                return;
            }else if(isFinish.equals("-2")){
                T.showShort("您输入的验证码有误");
                callBack.onRequestComplete(REGISTER_FAIL);
                return;
            }else if(isFinish.equals("1")){
                callBack.onRequestComplete(REGISTER_SUCCES);
                T.showShort("注册成功");
                return;
            }
        }catch (Exception e){
            callBack.onRequestComplete(REGISTER_FAIL);
            AppExceptionLogUtils.LOG_FOR_STL(e);
        }
    }
    //注册成功执行登录
    private void doLogin(){

    }

}
