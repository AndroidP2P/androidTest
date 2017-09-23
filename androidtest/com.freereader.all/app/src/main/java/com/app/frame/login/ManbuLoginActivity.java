package com.app.frame.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;

import com.app.frame.base.APPBaseActivity;
import com.app.frame.http.NormalRequest.HttpUtils;
import com.app.frame.login.token.ManbuTokenClass;
import com.app.frame.modifyuserpwd.ManbuModifyPwdActivity;
import com.app.frame.ui.button.LongTouchBtn;
import com.app.frame.ui.dialog.CustomProgressDialogView;
import com.app.frame.ui.eiditview.ClearEditText;
import com.app.frame.ui.pageloading.AppLoadingView;
import com.app.frame.ui.toast.T;
import com.app.frame.ui.topbar.AppTopBarView;
import com.app.kernel.AppEpplication.AppApplication;
import com.app.libs.LayoutRejustClass;
import com.app.libs.PermissionTool;
import com.freereader.all.R;

/**
 * Created by kanxue on 2016/12/6.
 */
public class ManbuLoginActivity extends APPBaseActivity{

    private Context mContext;
    private AppLoadingView mToolLoadingView;
    private ViewStub viewStub;
    private Button manBuLoginButton,manbuRegisterButton;
    private TextView manBuProtocalTv,manBuForgetPwdTv;
    private ClearEditText manBuUsrName,ManbuUsrPwd;
    private View LoginPageLayout;
    private LongTouchBtn developerTouchButton;
    private CustomProgressDialogView mProgressDialogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_layout);
        initViews();
        initListeners();
        initData();
    }

    @Override
    public void initListeners() {
        manBuForgetPwdTv.setOnClickListener(this);
        manBuProtocalTv.setOnClickListener(this);
        manbuRegisterButton.setOnClickListener(this);
        manBuLoginButton.setOnClickListener(this);
        developerTouchButton.setOnLongTouchListener(mLongTouchListener,3000);
    }

    private LongTouchBtn.LongTouchListener mLongTouchListener=new LongTouchBtn.LongTouchListener() {
        @Override
        public void onLongTouch() {
            T.showShort("Test");
        }
    };

    @Override
    public void initData() {
        //检测权限
        if(!PermissionTool.getBasePermission(PermissionTool.PERMISSION_WRITE_EXTERNAL_STORAGE,this)){
            PermissionTool.requestPermission(this, PermissionTool.CODE_WRITE_EXTERNAL_STORAGE, new PermissionTool.PermissionGrant() {
                @Override
                public void onPermissionGranted(int requestCode) {

                }
            });
        }
    }

    @Override
    public void initViews() {
        mContext=this;
        AppApplication.getInstance().addActivityToDataList(this);
        initTopBarView((AppTopBarView) findViewById(R.id.activity_top_bar));
        getTopBarView().setTopBarTitle(getString(R.string.manbu_login));
        mToolLoadingView= (AppLoadingView) findViewById(R.id.activity_loadingView);
        mToolLoadingView.setStubViewLayoutResource(R.layout.manbu_loginpage);
        viewStub=mToolLoadingView.getLoadingViewStub();
        //loading login page
        LoginPageLayout=mToolLoadingView.loadStubViewLayout();
        manBuLoginButton= (Button) LoginPageLayout.findViewById(R.id.login);
        manbuRegisterButton= (Button) LoginPageLayout.findViewById(R.id.register);
        manBuUsrName= (ClearEditText) LoginPageLayout.findViewById(R.id.
                manbu_login_phoneNumber);
        ManbuUsrPwd= (ClearEditText) LoginPageLayout.
                findViewById(R.id.manbu_login_password);
        manBuUsrName.setCancleIconVisible(true);
        ManbuUsrPwd.setCancleIconVisible(false);
        manBuProtocalTv= (TextView) LoginPageLayout.findViewById(R.id.protocalTextTitle);
        manBuForgetPwdTv= (TextView) LoginPageLayout.findViewById(R.id.changePasswordTitle);
        closeInputMethodWhenClickOutSide(LoginPageLayout,mContext);
        LayoutRejustClass.rejustLayoutView(LoginPageLayout,manBuLoginButton,mContext);
        mProgressDialogView=new CustomProgressDialogView(mContext,getString(R.string.manbu_loginprogresstitle));
        mProgressDialogView.setCancelable(true);
        mProgressDialogView.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.protocalTextTitle:
                showProtocalActivity();
                break;
            case R.id.changePasswordTitle:
                startActivity(new Intent(mContext, ManbuModifyPwdActivity.class));
                break;
            case R.id.login:
                doLogin();
                break;
            case R.id.register:
                doRegister();
                break;
        }
    }

    void doLogin(){
        if(!HttpUtils.isNetworkConnected(mContext)){
            T.showShort(getString(R.string.app_net_state_isback));
            return;
        }

        mProgressDialogView.show();
        final String mUsrPwd,mUsrName;
        mUsrName=manBuUsrName.getText().toString().trim();
        mUsrPwd=ManbuUsrPwd.getText().toString().trim();
        final String cid="";
        if(!ManbuLoginModel.checkUsrLoginParam(mUsrName,mUsrPwd,cid)){
            mProgressDialogView.dismiss();
            return;
        }
        ManbuTokenClass.getLoginToken(new HttpUtils.doPostCallBack() {
            @Override
            public void onRequestComplete(String result) {
                String loginToken=result;
                if(!ManbuTokenClass.checkTokenValid(loginToken)){
                    mProgressDialogView.dismiss();
                    return;
                }
                ManbuLoginModel.doLogin(mUsrName, ManbuTokenClass.generateLoginPostToken(mUsrPwd, loginToken),
                        cid, new HttpUtils.doPostCallBack() {
                    @Override
                    public void onRequestComplete(String result) {
                        if(result.equals(ManbuLoginModel.RESULT_SUCCESS)){
                            mProgressDialogView.dismiss();
                            finish();
                            return;
                        }else if(result.equals(ManbuLoginModel.RESULT_FALSE_USR_NOT_EXIST)){
                            mProgressDialogView.dismiss();
                            T.showShort("该用户不存在");
                        }else if(result.equals(ManbuLoginModel.RESULT_FALSE_USR_PWD_WRONG)){
                            mProgressDialogView.dismiss();
                            T.showShort("用户名，或密码错误");
                        }else  if(result.equals(ManbuLoginModel.RESULT_FALSE)){
                            mProgressDialogView.dismiss();
                            T.showShort("登录失败");
                        }
                    }
                });
            }
        },"?loginName="+mUsrName);
    }

    void doRegister(){
    }

    void showProtocalActivity(){
    }
}
