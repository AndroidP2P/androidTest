package com.app.frame.cache.filecache;
import android.content.Context;
import java.io.File;

/**
 * Created by kanxue on 2016/12/2.
 */
public class FileCacheManager{

    private Context mContext;
    public void init(Context mCtx){
        this.mContext=mCtx;
        initFileCacheCatalog();
    }

    public  void initFileCacheCatalog() {
        File BaseFileRoot=new File(FileCachePathClass.BASE_FILE_CACHE_ROOT);
        File imageRoot=new File(FileCachePathClass._GLOBAL_FILE_IMAGE_PATH);
        File jsonRoot=new File(FileCachePathClass._GLOBAL_FILE_JSON_PATH);
        File tempRoot=new File(FileCachePathClass._GLOBAL_FILE_TEMP_PATH);
        File exceptionRoot=new File(FileCachePathClass._GLOBAL_FILE_Exception_PATH);
        File pdfRoot=new File(FileCachePathClass._GLOBAL_FILE_PDF_PATH);
        File usrRoot=new File(FileCachePathClass._GLOBAL_FILE_USR_PATH);
        File groupRoot=new File(FileCachePathClass._GLOBAL_GROUP_CACHE_PATH);
        File audioRoot=new File(FileCachePathClass._GLOBALE_AUDIO_FILE_PATH);
        File mvRoot=new File(FileCachePathClass._GLOBALE_MOVIE_FILE_PATH);
        File netRoot=new File(FileCachePathClass._GLOBAL_NET_CACHE_PATH);
        if(!BaseFileRoot.exists()){
            BaseFileRoot.mkdir();
        }if(!imageRoot.exists()){
            imageRoot.mkdir();
        }if(!jsonRoot.exists()){
            jsonRoot.mkdir();
        }if(!tempRoot.exists()){
            tempRoot.mkdir();
        }if(!exceptionRoot.exists()){
            exceptionRoot.mkdir();
        }if(!pdfRoot.exists()){
            pdfRoot.mkdir();
        }if(!usrRoot.exists()){
            usrRoot.mkdir();
        }if(!groupRoot.exists()){
            groupRoot.mkdir();
        }if(!audioRoot.exists()){
            audioRoot.mkdir();
        }if(!mvRoot.exists()){
            mvRoot.mkdir();
        }if(!netRoot.exists()){
            netRoot.mkdir();
        }
    }

    private static FileCacheManager instance;
    public static FileCacheManager getInstance(){
        if(instance==null){
            instance=new FileCacheManager();
        }
        return instance;
    }

    public String getPublicJsonCachePath(){
        return FileCachePathClass._GLOBAL_FILE_JSON_PATH;
    }

    public String getPublicExceptionCachePath(){
        return FileCachePathClass._GLOBAL_FILE_Exception_PATH;
    }

    public String getPublicImageCachePath(){
        return FileCachePathClass._GLOBAL_FILE_IMAGE_PATH;
    }

    public String getPublicPdfCachePath(){
        return FileCachePathClass._GLOBAL_FILE_PDF_PATH;
    }

    public String getPublicWebViewPath(){
        return FileCachePathClass._GLOBAL_FILE_TEMP_PATH;
    }

    public String getNetCachePath(){
        return FileCachePathClass._GLOBAL_NET_CACHE_PATH;
    }

    //下载文件

    //删除文件

    //读文件

    //获取临时群信息文件
    public String getPublicGroupInfoPath(){
        return FileCachePathClass._GLOBAL_GROUP_CACHE_PATH;
    }

}
