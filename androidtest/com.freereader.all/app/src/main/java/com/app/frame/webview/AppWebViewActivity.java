package com.app.frame.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ClientCertRequest;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage.QuotaUpdater;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import com.app.frame.http.NormalRequest.AppNetRequestFactory;
import com.app.frame.ui.pageloading.AppLoadingView;
import com.app.frame.ui.speceffect.ParallaxSwipeBackActivity;
import com.app.frame.ui.topbar.AppTopBarView;
import com.app.kernel.AppEpplication.AppApplication;
import com.app.libs.FileUtils;
import com.freereader.all.R;

public class AppWebViewActivity extends ParallaxSwipeBackActivity {
    Context mContext = null;
    private static String webAPI;
    private WebView webView;
    private ProgressBar progressBar;
    private WebSettings webSetting;
    private String newWebPageContent = null;
    private long spaceNeeded = 1024 * 1024 * 8;
    private String jsonResultData = null;
    // 添加处理的算法对web网页修改成新的值。
    private String webPageContent = null;
    String actionBarTitle = "";
    private AppLoadingView mToolLoadingView;
    private View activityPageLayout;

    //记录一下原始url
    String first_url = null;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 2) {
                jsonResultData = WebviewModel.getDataFromJson((String) msg.obj);
                if (jsonResultData == null)
                    return;
                webPageContent = FileUtils.getDataFromAssets(
                        getApplicationContext(), "basic_info.html");
                if (webPageContent == null) {
                    return;
                }
                webPageContent = webPageContent.replace(
                        "{{jsonStringForShow}}", jsonResultData);
                webPageContent = webPageContent.replace(
                        "{{jsonStringForPostUrl}}", webAPI);
                webView.loadDataWithBaseURL(
                        "file:///android_asset/basic_info.html",
                        webPageContent, "text/html", "utf-8",
                        "file:///android_asset/basic_info.html");
            }
            if (msg.what == 3) {
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_layout);
        mContext = this;
        initViews();
        initListeners();
        initData();
    }

    public String netUrlApi = null, localUrlApi = null;
    public final static String _SHOW_ACTIONBAR_TITLE = " ";

    public void initViews() {
        // TODO Auto-generated method stub
        AppApplication.getInstance().addActivityToDataList(this);
        initTopBarView((AppTopBarView) findViewById(R.id.activity_top_bar));
        mToolLoadingView = (AppLoadingView) findViewById(R.id.activity_loadingView);
        mToolLoadingView.setStubViewLayoutResource(R.layout.medical_webviewlayout);
        activityPageLayout = mToolLoadingView.loadStubViewLayout();
        webView = (WebView) activityPageLayout.findViewById(R.id.patient_medicalWebView);
        getTopBarView().setTopBarRightOperateBtnVisualble(true);
        getTopBarView().setRightBtnTextTitle(getString(R.string.manbu_save_title));
        progressBar = (ProgressBar) activityPageLayout.findViewById(R.id.webProgressBar);
        if ((webAPI = getIntent().getStringExtra("webAPI")) == null) {
            webAPI = null;
        }
        actionBarTitle = getIntent().getStringExtra("actionBarTitle");
        if (actionBarTitle == null)
            return;
        getTopBarView().setTopBarTitle(actionBarTitle);
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

        //loading the netUrl and local url
        netUrlApi = getIntent().getStringExtra("netUrlApi");
        localUrlApi = getIntent().getStringExtra("localUrlApi");
        if (netUrlApi != null) {
            getTopBarView().setTopBarRightOperateBtnVisualble(false);
            webView.loadUrl(netUrlApi);
            first_url = netUrlApi;
            return;
        }

        if (localUrlApi != null) {
            getTopBarView().setTopBarRightOperateBtnVisualble(false);
            String loaclDataContent = FileUtils.getDataFromAssets(
                    getApplicationContext(), localUrlApi);
            if (loaclDataContent != null) {
                webView.loadDataWithBaseURL(null, loaclDataContent, "text/html", "utf-8", null);
                return;
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void initData() {
        if (TextUtils.isEmpty(webAPI))
            return;
    }

    @Override
    public void initListeners() {
        super.initListeners();
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (TextUtils.isEmpty(title)) {
                    title = "";
                }
                if (actionBarTitle.equals(_SHOW_ACTIONBAR_TITLE)) {
                    getTopBarView().setTopBarTitle(title);
                    return;
                }
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
                                                 long quota, QuotaUpdater quotaUpdater) {
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
    // 设置回退
    // 覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack(); // goBack()表示返回WebView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        switch (v.getId()) {
            case R.id.manbu_Back:
                boolean needExit = false;
                if (null != first_url) {
                    Log.i("对比url:", "first_url=" + first_url + ",geturl=" + webView.getUrl());
                    if (first_url.equals(webView.getUrl())) {
                        needExit = true;
                    } else {
                        if (!webView.canGoBack()) {
                            needExit = true;
                        }
                    }
                } else {
                    needExit = true;
                }

                if (needExit) {
                    webView.stopLoading();
                    webView = null;
                    System.gc();
                    System.exit(0);
                } else {
                    webView.goBack();
                    return;
                }
                break;
            case R.id.manbu_operateBtn:
                webView.loadUrl("javascript:save_click()");
                break;
            default:
                break;
        }
        super.onClick(v);
    }
}
