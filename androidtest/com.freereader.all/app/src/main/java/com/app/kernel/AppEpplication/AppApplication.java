package com.app.kernel.AppEpplication;

import android.app.Activity;
import android.app.Application;

import com.app.frame.cache.filecache.FileCacheManager;
import com.app.frame.http.RxjavaNetworkRequest.HttpBaseConfig;
import com.app.frame.ui.ImageLoader.GlideLoaderConfig;
import com.app.kernel.Exception.AppUnCaughtExceptionManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kanxue on 2016/1/10.
 */
public class AppApplication extends Application
{

    public static AppApplication instance;
    private  List<Activity> activityList;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //全局异常处理器
        registerUncaughtExceptionManager();
        //所有activity数组
        initActivityCachList();
        //文件系统初始化接口
        FileCacheManager.getInstance().init(this);
        //网络接口初始化
        HttpBaseConfig.initConfig(this);

    }

    private void registerUncaughtExceptionManager() {
         AppUnCaughtExceptionManager.getInstance().init(this);
    }

    public void initActivityCachList() {
        activityList=new ArrayList<Activity>();
    }

    // invoke application to stop app running.
    public void logOut() {
        for(Activity activity:activityList)
        {
            activity.finish();
        }
        System.exit(0);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    // add activities to dataList
    public void addActivityToDataList(Activity activity) {
        if(activityList==null){
            return;
        }
        activityList.add(activity);
    }

    // get MBRSApplicationInstance
    public static AppApplication getInstance() {
       if(instance==null) {
         instance=new AppApplication();
       }
       return instance;
    }

}
