package com.app.frame.base;

import android.content.Context;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ClientCertRequest;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.app.frame.cache.filecache.FileCacheManager;
import com.app.frame.ui.pageloading.AppLoadingView;
import com.app.kernel.AppEpplication.AppApplication;
import com.app.libs.FileUtils;
import com.freereader.all.R;
/**
 * Created by kanxue on 2017/1/17.
 */
public class AppBaseWebFragment extends APPBaseFragment {

    public static final String TAG_FOR_PARAMS_EXTRAS ="url_extra_param";
    private Context mContext;
    private RelativeLayout baseFragmentLayout;
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
    //URL_TYPE="0" localURL URL_TYPE="1" netURL
    public static final String URL_TYPE_FOR_LOCAL = "0";
    public static final String URL_TYPE_FOR_NET = "1";
    public static final String URL_TYPE_FOR_LOCAL_DYNAMIC = "2";
    public static final String URL_TYPE = "url_type";
    final String TAG_REPLCAE = "{{jsonStringForShow}}";

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        baseFragmentLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_loading_layout, container, false);
        return baseFragmentLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        initData();
    }

    private void initIntent() {
        Bundle webArg = getArguments();
        String urlType = webArg.getString(URL_TYPE);
        if (urlType.equals(URL_TYPE_FOR_NET)) {
            String netURL = webArg.getString(TAG_FOR_NET_URL);
            if (TextUtils.isEmpty(netURL)) {
                mToolLoadingView.setLoadingEndForNoData();
                return;
            }
            loadNetUrl(netURL);
            return;
        } else if (urlType.equals(URL_TYPE_FOR_LOCAL)) {
            String localUrl = webArg.getString(TAG_FOR_LOCAL_LOAD_URL);
            String localParam=webArg.getString(TAG_FOR_PARAMS_EXTRAS);
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
            String dynamicFileName = webArg.getString(TAG_FOR_LOCAL_DYNAMIC_URL);
            String dynamicContent = webArg.getString(TAG_FOR_LOCAL_DYNAMIC_CONTENT);
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

    //动态地址
    void loadDynamicLocalUrl(String fileName, String replaceContent) {
        initWebViews();
        String webPageContent = FileUtils.getDataFromAssets(
                AppApplication.getInstance().getApplicationContext(), fileName);
        if (webPageContent == null) {
            return;
        }
        webPageContent = webPageContent.replace(TAG_REPLCAE, replaceContent);
        webView.loadDataWithBaseURL("file:///android_asset/" + fileName, webPageContent,
                "text/html", "utf-8", "file:///android_asset/" + fileName);
        return;
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
        String appCacheDir = FileCacheManager.getInstance().getPublicWebViewPath();
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


    @Override
    public void initViews() {
        mContext = getActivity();
        mToolLoadingView = (AppLoadingView) baseFragmentLayout.findViewById(R.id.appLoadingLayout);
        mToolLoadingView.setStubViewLayoutResource(R.layout.app_base_web_view);
        mToolLoadingView.setLoadingFrameState(true);
        mToolLoadingView.loadingStart();
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
        initIntent();
    }
}