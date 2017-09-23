package com.app.frame.ui.pageloading;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.freereader.all.R;

import butterknife.ButterKnife;

/**
 * Created by kanxue on 2016/12/2.
 */
public class AppLoadingView extends RelativeLayout {

    private Context mContext = null;
    //网络状态
    private TextView netStateView;
    //懒加载view
    private ViewStub mLoadingStub;
    //动画图片
    private ImageView animImageView;
    //动画Animation
    private AnimationDrawable mLoadingAnimationDrawable;
    private TextView loadingInfoTitle;
    private String LOADING_START = "正在加载中...";
    private String LOADIN_FAIL_FOR_NO_NETWORKE = "网络走丢了，点我重试";
    private String LOADING_FAIL_FOR_SERVER_WARONG = "哎呀妈呀，服务器挂了，点我重试";
    private String LOADING_FOR_NO_DATA = "木有找到任何东东，再试一次";
    private RelativeLayout app_netstatelayout;

    //loading layout
    private RelativeLayout loadingContentLayout;

    public AppLoadingView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public AppLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public AppLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    public void setLoadingNetState(boolean state) {
        if (state) {
            app_netstatelayout.setVisibility(View.VISIBLE);
        } else {
            app_netstatelayout.setVisibility(View.GONE);
        }
    }

    private void init() {
        LayoutInflater.from(mContext).inflate(R.layout.app_loading_layout, this, true);
        loadingContentLayout = (RelativeLayout) findViewById(R.id.loadingContentLayout);
        animImageView = (ImageView) findViewById(R.id.loadingAnimationImg);
        mLoadingStub = (ViewStub) findViewById(R.id.app_activity_stub);
        netStateView = (TextView) findViewById(R.id.app_netStateTittle);
        netStateView.setOnClickListener(mWhenTitleBarClickListener);
        loadingInfoTitle = (TextView) findViewById(R.id.loadingInfoTitle);
        app_netstatelayout = (RelativeLayout) findViewById(R.id.app_netstatelayout);
        initAnimation();
    }

    OnClickListener mWhenTitleBarClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (netTitleBarCallBack == null) {
                return;
            }
            netTitleBarCallBack.onSettingNetWork();
        }
    };

    private void initAnimation() {
        mLoadingAnimationDrawable = getAnimationDrawable();
    }

    private AnimationDrawable getAnimationDrawable() {
        if (mLoadingAnimationDrawable == null) {
            mLoadingAnimationDrawable = (AnimationDrawable) animImageView.getDrawable();
        }
        return mLoadingAnimationDrawable;
    }

    OnClickListener mLoadingTitleClcikListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (refreshClick == null) {
                return;
            }
            refreshClick.onRefreshData();
        }
    };

    public void setLoadingInfo(String loadTitle) {
        if (loadTitle != null) {
            if (loadTitle.length() > 25) {
                loadTitle = loadTitle.substring(0, 24);
            }
        }
        loadingInfoTitle.setText(loadTitle);
    }

    //set layout resource
    public void setStubViewLayoutResource(int layoutRes) {
        mLoadingStub.setLayoutResource(layoutRes);
    }

    public ViewStub getLoadingViewStub() {
        return mLoadingStub;
    }

    //正在加载中
    public void loadingStart() {
        loadingInfoTitle.setOnClickListener(null);
        loadingInfoTitle.setText(LOADING_START);
        if (!mLoadingAnimationDrawable.isRunning()) {
            loadAnimation();
        }
    }

    private View mLoadingStubView = null;

    public View loadStubViewLayout() {
        setLoadingFrameState(false);
        if (mLoadingStub == null) {
            return null;
        }
        if (mLoadingStubView == null) {
            mLoadingStubView = mLoadingStub.inflate();
        }
        //支持butterknife注解 by 看雪
        if(mLoadingStubView!=null){
            ButterKnife.bind((Activity)mContext);
        }
        return mLoadingStubView;
    }

    private void loadAnimation() {
        getAnimationDrawable().start();
    }

    public void setLoadingAnimation(int resAnim) {
        animImageView.setImageResource(resAnim);
    }

    // onLoadingFail
    private void loadingEnd() {
        if (mLoadingAnimationDrawable == null) {
            return;
        }
        //主UI上停止动画。
        mLoadingAnimationDrawable.stop();
        loadingInfoTitle.setOnClickListener(mLoadingTitleClcikListener);
    }

    //改变加载失败时的显示方式
    public void setLoadingFailForNetWork() {
        loadingEnd();
        loadingInfoTitle.setText(LOADIN_FAIL_FOR_NO_NETWORKE);
    }

    public void setLoadingFailForServerWrong() {
        loadingEnd();
        loadingInfoTitle.setText(LOADING_FAIL_FOR_SERVER_WARONG);
    }

    public void setLoadingEndForNoData() {
        loadingEnd();
        loadingInfoTitle.setText(LOADING_FOR_NO_DATA);
    }

    //当加载成功
    public void loadingSuccess() {
        loadingEnd();
        loadingContentLayout.setVisibility(View.GONE);
        loadingInfoTitle.setOnClickListener(null);
    }

    //改变网络加载成功时显示方式
    public void setLoadingSuccess() {
        loadingSuccess();
    }

    //loading框架状态 true open; false close
    public void setLoadingFrameState(boolean flag) {
        if (flag) {
            loadingContentLayout.setVisibility(View.VISIBLE);
        } else {
            loadingContentLayout.setVisibility(View.GONE);
            loadingEnd();
        }
    }

    public LoadingClickClass refreshClick;

    public void setLoadingRefreshData(LoadingClickClass loadItem) {
        this.refreshClick = loadItem;
    }

    public interface LoadingClickClass {
        void onRefreshData();
    }

    public LoadingNetTitleBarCallBack netTitleBarCallBack;

    public void setLoadingNetTitleBarListener(LoadingNetTitleBarCallBack titleBarListener) {
        netTitleBarCallBack = titleBarListener;
    }

    public interface LoadingNetTitleBarCallBack {
        void onSettingNetWork();
    }

}
