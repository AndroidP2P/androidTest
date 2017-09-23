package com.app.kernel.Exception.Log;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import com.app.frame.cache.filecache.FileCacheManager;
import com.app.kernel.AppEpplication.AppApplication;
import com.app.libs.DateTools;
import com.app.libs.FileUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * Created by kanxue on 2016/12/6.
 */
public class AppExceptionLogUtils {
    //设置没有log
    static int LEVEL_NO_LOG= 0;
    //设置运行系统程序输出log日志
    static int LEVEL_JUST_DEV=1;
    static int LOG_LEVEL=LEVEL_NO_LOG;
    static int getLogLevel(){
        return LOG_LEVEL;
    }

    static boolean isNeedToOpenExceptionOutputStream=true;
    public static void setEnableException(boolean flag){
        isNeedToOpenExceptionOutputStream=false;
    }
    public static void LOG_FOR_STL(String exception){
        if(!isNeedToOpenExceptionOutputStream){
            return;
        }
        saveExeptionToLocal(exception);
    }

    public static void LOG_FOR_STL(Exception ex){
        if(!isNeedToOpenExceptionOutputStream){
            return;
        }
        String exception=ex.getLocalizedMessage();
        LOG_FOR_STL(exception);
    }

    public static void LOG_FOR_STL(Exception ex,boolean isPostToServer){
        if(!isNeedToOpenExceptionOutputStream){
            return;
        }
    }

    public static void LOG_FOR_STL(Exception ex,boolean whetherToSaveToLocal,boolean whetherToPostToServer){
        if(!isNeedToOpenExceptionOutputStream){
            return;
        }
    }

    public static void LOG_FOR_STL(String ex,boolean isPostToServer){
        if(!isNeedToOpenExceptionOutputStream){
            return;
        }
    }

    public static void LOG_FOR_STL(String ex,boolean whetherToSaveToLocal,boolean whetherToPostToServer){
        if(!isNeedToOpenExceptionOutputStream){
            return;
        }
    }

    protected synchronized static void saveExeptionToLocal(String exceptionData){
        try {
            String filePath = FileCacheManager.getInstance().getPublicExceptionCachePath();
            String usrID = "log_id";
            String dateTime = "time:" + DateTools.getStringCurrentDate();
            String phoDeviceInfo = collectCrashDeviceInfo(AppApplication.getInstance().getApplicationContext());
            String timeStamp = DateTools.getStringLoginDate2();
            StringBuilder builder = new StringBuilder();
            builder.append(usrID + "\n" + phoDeviceInfo + "\n" + dateTime + "\n"+"exception:"+exceptionData);
            FileUtils.saveFile(filePath + File.separator + timeStamp + ".exc", builder.toString().getBytes(Charset.forName("UTF-8")));
        }catch (Exception e){}
    }

    protected void postExceptionToServer(){
        // 定义时间。
        // 手机信息。
        // 软件版本号。

    }

    /**
     * 收集程序崩溃的设备信息
     *
     * @param ctx
     */
    private static Properties mDeviceCrashInfo = new Properties();
    private static final String VERSION_NAME = "version_name";
    private static final String VERSION_CODE = "version_code";
    private static final String STACK_TRACE = "STACK_TRACE";
    public static String collectCrashDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                mDeviceCrashInfo.put(VERSION_NAME,
                        pi.versionName == null ? "not set" : pi.versionName);
                mDeviceCrashInfo.put(VERSION_CODE, "" + pi.versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        // 使用反射来收集设备信息.在Build类中包含各种设备信息,
        // 例如: 系统版本号,设备生产商 等帮助调试程序的有用信息
        // 具体信息请参考后面的截图
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                mDeviceCrashInfo.put(field.getName(), "" + field.get(null));
            } catch (Exception e) {
            }
        }
        return mDeviceCrashInfo.toString();
    }
}
