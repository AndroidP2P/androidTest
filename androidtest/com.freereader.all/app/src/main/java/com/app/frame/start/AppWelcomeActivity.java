package com.app.frame.start;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.app.frame.base.APPBaseActivity;
import com.app.frame.login.ManBuLoginParamConstant;
import com.app.frame.login.ManbuLoginActivity;
import com.app.frame.start.navigation.views.ImageIndicatorView;
import com.app.frame.ui.toast.T;
import com.app.kernel.AppEpplication.AppApplication;
import com.app.libs.PermissionTool;
import com.freereader.all.HelloFreeReader;
import com.freereader.all.R;
import com.wevey.selector.dialog.NormalSelectionDialog;

public class AppWelcomeActivity extends APPBaseActivity {

    // definite navigationView
    private Context mContext;
    private ImageView welcomeNaviPage;
    private Animation welcomePageAnimation;
    private ImageIndicatorView mNavImageIndicatorView;
    private PopupWindow navigationWindow;
    private View naviViewPopView;
    private boolean appNavigateState;
    private boolean appLoginState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manbu_welcomepagelayout);
        initViews();
        initListeners();
        initData();
    }

    @Override
    public void initListeners() {

        welcomePageAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (appNavigateState) {
                    if (appLoginState) {
                        //go to mainActivity
                    } else {
                        startActivity(new Intent(mContext, HelloFreeReader.class));
                    }
                    finish();
                } else {
                    welcomeNaviPage.setVisibility(View.GONE);
                    showNaviPopWindow();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mNavImageIndicatorView.setOnItemClickListener(new ImageIndicatorView.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                navigationWindow.dismiss();
                AppStartParamConstant.setNavigationState(true);
                startActivity(new Intent(mContext, ManbuLoginActivity.class));
                finish();
            }
        });

        mNavImageIndicatorView.setOnPageEdgeDragListener(new ImageIndicatorView.OnPageEdgeDragListener() {
            @Override
            public void onDragEdge() {
                navigationWindow.dismiss();
                AppStartParamConstant.setNavigationState(true);
                startActivity(new Intent(mContext, ManbuLoginActivity.class));
                finish();
            }
        });
    }

    // 弹出轮播图界面
    void showNaviPopWindow() {
        navigationWindow.showAtLocation(naviViewPopView, Gravity.CENTER, 0, 0);
    }

    @Override
    public void initData() {

    }

    private Integer imageResource[] = null;

    @Override
    public void initViews() {
        // add activity to container
        mContext = this;
        AppApplication.getInstance().addActivityToDataList(this);
        welcomeNaviPage = (ImageView) findViewById(R.id.loginWelcomePage);
        // add image into list
        naviViewPopView = getLayoutInflater().inflate(R.layout.welcome_navigationlayout, null);
        navigationWindow = new PopupWindow(naviViewPopView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        mNavImageIndicatorView = (ImageIndicatorView) naviViewPopView.findViewById(R.id.startWelcomePage);
        mNavImageIndicatorView.setEdgeCanDrag(true);
        imageResource = new Integer[]{
                R.mipmap.ic_welcome_1,
                R.mipmap.ic_welcome_2,
                R.mipmap.ic_welcome_3,
                R.mipmap.ic_welcome_4
        };
        appNavigateState = AppStartParamConstant.getNavigationState();
        appLoginState = ManBuLoginParamConstant.getWhetherAppNeedToLoginState();
        mNavImageIndicatorView.setupLayoutByDrawable(imageResource);
        mNavImageIndicatorView.setIndicateStyle(ImageIndicatorView.INDICATE_USERGUIDE_STYLE);
        mNavImageIndicatorView.show();
        // load animation resource
        welcomePageAnimation = AnimationUtils.loadAnimation(mContext, R.anim.welcomepage_start);
        welcomeNaviPage.startAnimation(welcomePageAnimation);
    }
}
