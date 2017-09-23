package com.app.frame.http.RxjavaNetworkRequest;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;

import com.app.frame.cache.filecache.FileCacheManager;
import com.app.frame.http.AppProtocalParamConstant;
import com.app.frame.http.ApiStore;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/9/20.
 */
public class HttpBaseConfig {

    private static SparseArray<HttpBaseConfig> sRetrofitManager = new SparseArray<>(HostType.TYPE_COUNT);
    //设置读取超时时间
    private static final int READ_TIME_OUT_LENGHT=80000;
    //设置网络链接时长
    public static final int CONNET_TIME_OUT_LENGHT=80000;
    private static Context mBaseCtx; //全局application
    private Retrofit mBaseRetrofitClient;
    public OkHttpClient mBaseRequestClient;
    private ApiStore retrofitService;
    //设置缓存时间
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;

    //设置conctx
    public static void initConfig(Context context){
        mBaseCtx=context;
    }

    private HttpBaseConfig(int typeID){
        //设置缓存，如果有网络数据存储空间。
        File netRequestCache=new File(FileCacheManager.getInstance().getNetCachePath());
        //设置缓存大小为100mb
        Cache cache=new Cache(netRequestCache,1024*1024*100);
        mBaseRequestClient=new OkHttpClient.Builder()
                .readTimeout(READ_TIME_OUT_LENGHT, TimeUnit.MILLISECONDS)
                .addInterceptor(headDescibeInterceptor)
                .addInterceptor(getLoggingInterceptor())
                .addInterceptor(mNetCacheIntercepterConfig)
                .addNetworkInterceptor(mNetCacheIntercepterConfig)
                .cache(cache)
                .build();
        Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
        mBaseRetrofitClient=new Retrofit.Builder()
                .client(mBaseRequestClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(AppProtocalParamConstant.getBaseUrl(typeID))
                .build();
    }

    public static ApiStore getDefault(int baseUrlType){
        HttpBaseConfig config=sRetrofitManager.get(baseUrlType);
        if(config==null){
            config=new HttpBaseConfig(baseUrlType);
            sRetrofitManager.put(baseUrlType,config);
        }
        return config.retrofitService;
    }

    //log 拦截器
    public HttpLoggingInterceptor getLoggingInterceptor(){
        //开启log 拦截器
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }

    //头部信息筛选拦截器
    Interceptor headDescibeInterceptor=new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request requestBuilder=chain.request()
                    .newBuilder()
                    .addHeader("Content-Type","application/json") //告诉服务器接受什么类型的数据。
                    .build();
            return chain.proceed(requestBuilder);
        }
    };

    //配置缓存策略 拦截器
    private final Interceptor mNetCacheIntercepterConfig=new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request=chain.request();
            String cacheConctrol=request.cacheControl().toString();
            if(!NetWorkUtils.isNetConnected(mBaseCtx)){
                //网络没有时加载本地换缓存。
                request=request
                        .newBuilder()
                        .cacheControl(TextUtils.isDigitsOnly(cacheConctrol)? CacheControl.FORCE_NETWORK:CacheControl.FORCE_CACHE)
                        .build();
            }
            Response response=chain.proceed(request);
            if(NetWorkUtils.isNetConnected(mBaseCtx)){
                return response.newBuilder().header("Cache-Control",cacheConctrol)
                        .removeHeader("Pragma")
                        .build();
            }else{
                return response.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };

    /**
     * max-stale 指示客户机可以接收超出超时期间的响应消息。如果指定max-stale消息的值，那么客户机可接收超出超时期指定值之内的响应消息。
     */
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    /**
     * (假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)时则不会使用缓存而请求服务器
     */
    private static final String CACHE_CONTROL_AGE = "max-age=0";
    public static String getCacheControl(){
        return NetWorkUtils.isNetConnected(mBaseCtx)? CACHE_CONTROL_AGE : CACHE_CONTROL_CACHE;
    }

}
