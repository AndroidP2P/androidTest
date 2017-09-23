package com.freereader.all;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;

import com.app.frame.base.APPBaseActivity;
import com.app.frame.ui.button.LongTouchBtn;
import com.app.frame.ui.dialog.CustomProgressDialogView;
import com.app.frame.ui.eiditview.ClearEditText;
import com.app.frame.ui.pageloading.AppLoadingView;
import com.app.frame.ui.topbar.AppTopBarView;
import com.app.kernel.AppEpplication.AppApplication;
import com.jaydenxiao.common.base.BaseActivity;
import com.zhl.userguideview.HighLightGuideView;

import butterknife.Bind;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by Administrator on 2017/8/23.
 */
public class HelloFreeReader extends APPBaseActivity{

    private Context mContext;
    private AppLoadingView mToolLoadingView;
    private ViewStub viewStub;
    private Button manBuLoginButton,manbuRegisterButton;
    private TextView manBuProtocalTv,manBuForgetPwdTv;
    private ClearEditText manBuUsrName,ManbuUsrPwd;
    private View LoginPageLayout;
    private LongTouchBtn developerTouchButton;
    private CustomProgressDialogView mProgressDialogView;
    NewTestPresenter mPresenter;

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

    }

    //这里面加入很多 业务处理的东西
    NewDetailView detailView=new NewDetailView() {

        @Override
        public void onSuccess(NewsDetail mBook) {

        }

        @Override
        public void onError(String result) {

        }
    };

    @Override
    public void initData() {
        mPresenter=new NewTestPresenter();
        mPresenter.attachView(detailView,this);
        mPresenter.loadNewsData("1");//请求网络数据
    }

    //注释注解必须在loadStubLayout 之后才能生效。因为使用了stubviewlayout
    @Bind(R.id.register)
    Button mRegister;
    @Override
    public void initViews() {
        mContext=this;
        initTopBarView((AppTopBarView) findViewById(R.id.activity_top_bar));
        getTopBarView().setTopBarTitle(getString(R.string.manbu_login));
        mToolLoadingView= (AppLoadingView) findViewById(R.id.activity_loadingView);
        mToolLoadingView.setStubViewLayoutResource(R.layout.manbu_loginpage);
        viewStub=mToolLoadingView.getLoadingViewStub();
        //loading login page 在支持loadingPage的形式界面必须要在loadStubViewLayout 之后view 才能生效。
        LoginPageLayout=mToolLoadingView.loadStubViewLayout();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
