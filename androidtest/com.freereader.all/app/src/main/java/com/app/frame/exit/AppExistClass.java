package com.app.frame.exit;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.app.frame.login.ManBuLoginParamConstant;
import com.app.frame.login.ManbuLoginActivity;
import com.app.frame.ui.dialog.AppExitPopDialog;

/**
 * Created by kanxue on 2016/12/16.
 */
public class AppExistClass {
    private static final String EXIST_FROM_APP="";
    //退出登录 改变登录参数一
    public static void doExistApp(){
        ManBuLoginParamConstant.setWhetherAppNeedToLoginState(false);
        postExitStatusToServer();
    }

    private static void postExitStatusToServer(){

    }

    //退出弹框
    public static void doExistForPopDialog(final Context mContext, String message){
        final AppExitPopDialog appExitPopDialog=new AppExitPopDialog(mContext);
        appExitPopDialog.setMessage(message);
        appExitPopDialog.setCancelable(false);
        appExitPopDialog.setCanceledOnTouchOutside(false);
        appExitPopDialog.setOnSureButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doExistApp();
                Intent intent = new Intent();
                intent.setClass(mContext,ManbuLoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT|Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                mContext.startActivity(intent);
                //防止窗口句柄泄露
                appExitPopDialog.dismiss();
            }
        });
        appExitPopDialog.show();
    }

}
