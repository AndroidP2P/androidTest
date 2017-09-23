package com.app.frame.cache.filecache;

import android.os.Environment;

import com.app.kernel.AppEpplication.AppApplication;

/**
 * Created by kanxue on 2016/12/2.
 */
public class FileCachePathClass {

    private static final String BASE_FILE_CACHE_PATH= getExternalStorePath();
    public static final String BASE_FILE_CACHE_ROOT = BASE_FILE_CACHE_PATH+"/"+ AppApplication.getInstance().getPackageName(); //根包名
    public static  final String _GLOBAL_FILE_IMAGE_PATH= BASE_FILE_CACHE_ROOT +"/image";//图片缓存地址
    public static  final String _GLOBAL_FILE_PDF_PATH= BASE_FILE_CACHE_ROOT +"/pdf";//pdf 文件
    public static  final String _GLOBAL_FILE_USR_PATH= BASE_FILE_CACHE_ROOT +"/usr";//用户信息
    public static  final String _GLOBAL_FILE_Exception_PATH= BASE_FILE_CACHE_ROOT +"/exception";//软件异常
    public static  final String _GLOBAL_FILE_JSON_PATH= BASE_FILE_CACHE_ROOT +"/json";//缓存json文件
    public static final String _GLOBAL_FILE_TEMP_PATH= BASE_FILE_CACHE_ROOT +"/temp";
    public static final String _GLOBAL_GROUP_CACHE_PATH=BASE_FILE_CACHE_ROOT+"/groupInfo";//群组信息
    public static final String _GLOBALE_AUDIO_FILE_PATH=BASE_FILE_CACHE_ROOT+"/audio";//音频文件
    public static final String _GLOBALE_MOVIE_FILE_PATH=BASE_FILE_CACHE_ROOT+"/movie"; //视频文件
    public static final String _GLOBAL_NET_CACHE_PATH=BASE_FILE_CACHE_ROOT+"/netCache";

    public static String getExternalStorePath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    //deprecate
    public static boolean isExistExternalStore() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

}
