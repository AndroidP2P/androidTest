package com.app.frame.ui.ImageLoader;

import android.content.Context;
import android.graphics.Color;

import com.app.frame.cache.filecache.FileCachePathClass;
import com.app.kernel.AppEpplication.AppApplication;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.GlideModule;
import java.io.File;
/**
 * Created by kanxue on 2016/12/9.
 * 本类会在 app启动时加载这个。
 */
public class GlideLoaderConfig implements GlideModule{
    private static Context mContext= AppApplication.getInstance().getApplicationContext();
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // 定义图片路径 FileCachePathClass._GLOBAL_FILE_IMAGE_PATH
        int CACHE_SIZE=200*1024*1024;
        File cacheDir = new File(FileCachePathClass._GLOBAL_FILE_IMAGE_PATH+File.separator);//配置图片存储位置。
        GlideBuilder glideBuilder=new GlideBuilder(mContext);
        glideBuilder.setDiskCache(new DiskLruCacheFactory(cacheDir.getAbsolutePath(), CACHE_SIZE));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        
    }
}
