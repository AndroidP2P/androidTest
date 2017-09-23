package com.app.kernel.Exception;

import android.content.Context;

/**
 * Created by kanxue on 2016/1/10.
 */
public class AppUnCaughtExceptionManager implements Thread.UncaughtExceptionHandler {
    /**
     * this function definite the software
     * dialysis record
     */

    private static AppUnCaughtExceptionManager instance;
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
    private Context mContext;

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    //get Instance
    public static AppUnCaughtExceptionManager getInstance() {
        if (instance == null) {
            instance = new AppUnCaughtExceptionManager();
        }
        return instance;
    }

    public void init(Context context) {
        this.mContext = context;
        uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public void recordSoftWareExceptionDialysisInfo() {

    }

    /**
     * definite exception type and state code
     */
    public static class ErrorCode {
        public static String ERROR = "MBRS_ERROR_CODE";
        public static String ERROCODE_DELTETABLE_FALSE = "0";
        public static String Error_1 = "you cann't find the resource,and resouce id<0";
        public static String Error_2 = "you cann't find the resource,and the view is null";
        public static String Error_3=" the json data  has analysis false ";
    }


}
