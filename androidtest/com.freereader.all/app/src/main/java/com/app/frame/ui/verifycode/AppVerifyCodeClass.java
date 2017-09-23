package com.app.frame.ui.verifycode;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.app.frame.http.NormalRequest.AppNetRequestFactory;
import com.app.frame.http.NormalRequest.HttpUtils;
import com.app.frame.ui.toast.T;
import com.app.kernel.AppEpplication.AppApplication;

/**
 * Created by kanxue on 2016/12/13.
 */
public class AppVerifyCodeClass {

    private static AppVerifyCodeClass instance;
    public static Context mContext= AppApplication.getInstance().getApplicationContext();
    public static AppVerifyCodeClass getInstance() {
        if(instance==null) {
            instance = new AppVerifyCodeClass();
        }
        return instance;
    }

    private VerifyCodeCallBack codeCallBack;
    private int timeDelay;
    private Button verfiyButton;
    private View.OnClickListener listener;
    public void startVerifyCode(Button button, int timeDelay, View.OnClickListener listener, VerifyCodeCallBack callBack) {
        if(isRunning){
            T.showShort("验证码正在运行中，请等待验证码结束");
            return;
        }
        resetVerifyCode();
        this.timeDelay = timeDelay;
        this.codeCallBack = callBack;
        this.verfiyButton=button;
        this.listener=listener;
        init();
    }

    public static void getVerifyCodeFromNet(String urlPath, String param, final HttpUtils.doPostCallBack callBack){
        if(!HttpUtils.isNetworkConnected(mContext)){
            T.showShort("网络状态不好，请稍后重试");
            return;
        }
        AppNetRequestFactory.getManbuDataFromCallBack(urlPath + param, new HttpUtils.doPostCallBack() {
            @Override
            public void onRequestComplete(String result) {
                callBack.onRequestComplete(result);
            }
        });
    }

    public void startVerifyCode(Button button, int timeDelay, View.OnClickListener listener) {
        this.timeDelay = timeDelay;
        this.codeCallBack = defaultVerifyCodeCallBack;
        this.verfiyButton=button;
        this.listener=listener;
        init();
    }

    VerifyCodeCallBack defaultVerifyCodeCallBack=new VerifyCodeCallBack() {
        @Override
        public void onRunning(int i) {
            verfiyButton.setText(i+"(s)");
        }

        @Override
        public void onEnd() {
            verfiyButton.setText("获取验证码");
        }
    };

    private int maxTimeDelay;
    private long currentSettingTime=0L;
    private String verifyNumber=null;
    private final int EVENT_VERIFY_CODE_END=0x0001;
    private final int EVENT_VERIFY_CODE_RUNNING=0x002;
    public void setMaxDelayTime(int maxTime,String verifyCodeNumber){
        this.currentSettingTime=System.currentTimeMillis();
        this.verifyNumber=verifyCodeNumber;
        this.maxTimeDelay=maxTime;
    }

    private String getVerifyNumber(){
        if(System.currentTimeMillis()-currentSettingTime>maxTimeDelay*1000){
            verifyNumber=null;
            T.showShort("验证码已过期");
            instance=null;
            return null;
        }
        return verifyNumber;
    }

    private void resetVerifyCode(){
        currentSettingTime=0L;
        verifyNumber=null;
        maxTimeDelay=60;
        isRunning=false;
    }

    public boolean checkVerifyCodeNumber(String verifyCodeNumber){
        if(TextUtils.isEmpty(verifyCodeNumber)){
            T.showShort("验证码不能为空");
            return false;
        }
        if(getVerifyNumber()==null){
            return false;
        }
        if(TextUtils.equals(verifyNumber,verifyCodeNumber)){
           return true;
        }else{
            T.showShort("验证码不一致");
            return false;
        }
    }

    public void setOrder(int sequnece){
        this.sequnece=sequnece;
    }

    public boolean getIsVerifyCodeRunning(){
        return isRunning;
    }

    private int sequnece;
    public static class VerifyCodeOrder{
        public static int POSITIVE_SEQUENCE=0;
        public static int REVERSE_SEQUENCE=1;
    }

    private boolean isRunning=true;
    private void init(){
        verfiyButton.setOnClickListener(null);
        isRunning=true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning) {
                    running();
                }

            }
        }).start();
    }

    void running(){
        try {
            if(timeDelay==0){
                isRunning=false;
                mHandler.sendEmptyMessage(EVENT_VERIFY_CODE_END);
                return;
            }else{
                timeDelay--;
                mHandler.sendEmptyMessage(EVENT_VERIFY_CODE_RUNNING);
                Thread.sleep(1000);
                return;
            }
        }catch (Exception E){

        }
    }

    void end(){
        codeCallBack.onEnd();
        verfiyButton.setOnClickListener(listener);
    }

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==EVENT_VERIFY_CODE_END){
                end();
            }
            if(msg.what==EVENT_VERIFY_CODE_RUNNING){
                codeCallBack.onRunning(timeDelay);
            }
        }
    };

    public interface VerifyCodeCallBack {
        void onRunning(int i);
        void onEnd();
    }
}


