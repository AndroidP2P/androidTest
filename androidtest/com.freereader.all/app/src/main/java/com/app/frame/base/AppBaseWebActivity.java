package com.app.frame.base;

import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ClientCertRequest;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.app.frame.cache.filecache.FileCacheManager;
import com.app.frame.ui.pageloading.AppLoadingView;
import com.app.frame.ui.topbar.AppTopBarView;
import com.app.kernel.AppEpplication.AppApplication;
import com.app.libs.FileUtils;
import com.freereader.all.R;

/**
 * Created by kanxue on 2017/2/16.
 */
public class AppBaseWebActivity extends APPBaseSlidingActivity {
    public static final String TAG_FOR_PARAMS_EXTRAS ="url_extra_param";
    private Context mContext;
    AppLoadingView mToolLoadingView;
    private WebSettings webSetting;
    private long spaceNeeded = 1024 * 1024 * 8;
    //config
    private WebView webView;
    private ProgressBar progressBar;
    public static final String TAG_FOR_LOCAL_LOAD_URL = "local_url";
    public static final String TAG_FOR_NET_URL = "net_url";
    public static final String TAG_FOR_LOCAL_DYNAMIC_URL = "local_dynamic_url";
    public static final String TAG_FOR_LOCAL_DYNAMIC_CONTENT = "local_dynamic_content";
    public static final String TAG_FOR_LOCAL_DYNAMIC_FILE_NAME="";
    //URL_TYPE="0" localURL URL_TYPE="1" netURL
    public static final String URL_TYPE_FOR_LOCAL = "0";
    public static final String URL_TYPE_FOR_NET = "1";
    public static final String URL_TYPE_FOR_LOCAL_DYNAMIC = "2";
    public static final String URL_TYPE = "url_type";
    public static final String TAG_FPR_ACTION_BAR_TITLE="action_bar_title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_layout);
        initViews();
    }

    private void initIntent() {
        Intent webArg = getIntent();
        String urlType = webArg.getStringExtra(URL_TYPE);
        String barTitle=webArg.getStringExtra(TAG_FPR_ACTION_BAR_TITLE);
        getTopBarView().setTopBarTitle(barTitle);
        //网络地址
        if (urlType.equals(URL_TYPE_FOR_NET)) {
            String netURL = webArg.getStringExtra(TAG_FOR_NET_URL);
            if (TextUtils.isEmpty(netURL)) {
                mToolLoadingView.setLoadingEndForNoData();
                return;
            }
            loadNetUrl(netURL);
            return;
            //本地网页地址
        } else if (urlType.equals(URL_TYPE_FOR_LOCAL)) {
            String localUrl = webArg.getStringExtra(TAG_FOR_LOCAL_LOAD_URL);
            String localParam=webArg.getStringExtra(TAG_FOR_PARAMS_EXTRAS);
            if (TextUtils.isEmpty(localUrl)) {
                mToolLoadingView.setLoadingEndForNoData();
                return;
            }
            //如果携带参数
            if(!TextUtils.isEmpty(localParam)){
                loadLocalContentWithParam(localUrl,localParam);
                return;
            }
            loadLocalContent(localUrl);
            return;
        } else if (urlType.equals(URL_TYPE_FOR_LOCAL_DYNAMIC)) {
            //dynamicFileName 例如 basic_info.xml
            //dynamicContent 例如一段Json 文件，用来替换basic_info.xml里面的某个字符串。
            String dynamicFileName = webArg.getStringExtra(TAG_FOR_LOCAL_DYNAMIC_URL);
            String dynamicContent = webArg.getStringExtra(TAG_FOR_LOCAL_DYNAMIC_CONTENT);
            if (TextUtils.isEmpty(dynamicContent) || TextUtils.isEmpty(dynamicFileName)) {
                mToolLoadingView.setLoadingEndForNoData();
                return;
            }
            loadDynamicLocalUrl(dynamicFileName, dynamicContent);
            return;
        }
        mToolLoadingView.setLoadingEndForNoData();
        return;
    }

    //本地静态网页
    // load static page
    void loadLocalContent(String localURL) {
        initWebViews();
        String loaclDataContent = FileUtils.getDataFromAssets(
                AppApplication.getInstance().getApplicationContext(), localURL);
        if (loaclDataContent != null) {
            webView.loadDataWithBaseURL(null, loaclDataContent, "text/html", "utf-8", null);
            return;
        }
    }

    @Override
    // 设置回退
    // 覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack(); // goBack()表示返回WebView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    void loadLocalContentWithParam(String localURL,String params) {
        initWebViews();
        String loaclDataContent = FileUtils.getDataFromAssets(
                AppApplication.getInstance().getApplicationContext(), localURL);
        webView.loadDataWithBaseURL("file:///android_asset/" + localURL+params, loaclDataContent,
                "text/html", "utf-8", "file:///android_asset/" + localURL+params);
        return;
    }

    //网络地址
    void loadNetUrl(String netURL) {
        initWebViews();
        webView.loadUrl(netURL);
    }

    void initWebViews() {
        mToolLoadingView.loadStubViewLayout();
        progressBar = (ProgressBar) mToolLoadingView.findViewById(R.id.webProgressBar);
        webView = (WebView) mToolLoadingView.findViewById(R.id.patient_medicalWebView);
        initSettings();
        initListeners();
    }

    void initSettings() {
        // TODO Auto-generated method stub
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollbarFadingEnabled(false);
        webSetting = webView.getSettings();
        // problem it need API at last 16
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webSetting.setAllowUniversalAccessFromFileURLs(true);
        }
        webSetting.setDomStorageEnabled(true);
        // webSetting.setPluginsEnabled(true);
        webSetting.setAppCacheMaxSize(1024 * 1024 * 4);
        String appCacheDir = this.getApplicationContext()
                .getDir("webViewCache", Context.MODE_PRIVATE).getPath();
        webSetting.setAppCachePath(appCacheDir);
        webSetting.setAllowFileAccess(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);//js和android交互
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);//设置，可能的话使所有列的宽度不超过屏幕宽度
        webSetting.setLoadWithOverviewMode(true);//设置webview自适应屏幕大小
        webSetting.setSupportZoom(false);//关闭zoom按钮
        webSetting.setBuiltInZoomControls(false);//关闭zoom
    }

    //动态的加载内容
    void loadDynamicLocalUrl(String fileName, String webPageContent) {
        initWebViews();
        webView.loadDataWithBaseURL("file:///android_asset/" + fileName, webPageContent,
                "text/html", "utf-8", "file:///android_asset/" + fileName);
        return;
    }

    @Override
    public void initViews() {
        mContext =this;
        initTopBarView((AppTopBarView) findViewById(R.id.activity_top_bar));
        getTopBarView().setLeftBackButtonVisualble(true);
        mToolLoadingView = (AppLoadingView)findViewById(R.id.activity_loadingView);
        mToolLoadingView.setStubViewLayoutResource(R.layout.app_base_web_view);
        mToolLoadingView.setLoadingFrameState(true);
        mToolLoadingView.loadStubViewLayout();
        initIntent();
    }

    @Override
    public void initListeners() {
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    if (View.INVISIBLE == progressBar.getVisibility()) {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                    progressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReachedMaxAppCacheSize(long requiredStorage,
                                                 long quota, WebStorage.QuotaUpdater quotaUpdater) {
                // TODO Auto-generated method stub
                quotaUpdater.updateQuota(spaceNeeded * 2);
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                // TODO Auto-generated method stub
                webView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
                super.onReceivedClientCertRequest(view, request);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //信任所有证书
                handler.proceed();
            }
        });
    }


    @Override
    public void initData() {
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch(v.getId()) {
            case R.id.manbu_operateBtn:
                webView.loadUrl("javascript:save_click()");
                break;
            case R.id.manbu_Back:
                System.exit(0);
                break;
        }
    }
}
