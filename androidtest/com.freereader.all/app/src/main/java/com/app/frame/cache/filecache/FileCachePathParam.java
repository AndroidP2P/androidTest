package com.app.frame.cache.filecache;

import android.content.pm.ApplicationInfo;
import android.os.Environment;

import com.app.kernel.AppEpplication.AppApplication;

/**
 * Created by kanxue on 2016/12/2.
 */
public class FileCachePathParam {

    public static final String BASE_FILE_CACHE_PATH= Environment.getExternalStorageDirectory().getAbsolutePath();
    private static final String BASE_FILE_CAHE_NAME= AppApplication.getInstance().getPackageName();
    public static  final String _GLOBAL_FILE_IMAGE_PATH=BASE_FILE_CAHE_NAME+"/image";
    public static  final String _GLOBAL_FILE_PDF_PATH=BASE_FILE_CAHE_NAME+"/pdf";
    public static  final String _GLOBAL_FILE_USR_PATH=BASE_FILE_CAHE_NAME+"/usr";
    public static  final String _GLOBAL_FILE_Exception_PATH=BASE_FILE_CAHE_NAME+"/exception";
    public static  final String _GLOBAL_FILE_JSON_PATH=BASE_FILE_CAHE_NAME+"/json";
    public static final String _GLOBAL_FILE_TEMP_PATH=BASE_FILE_CAHE_NAME+"/temp";

}
