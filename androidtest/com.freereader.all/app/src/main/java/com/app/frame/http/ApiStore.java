package com.app.frame.http;

import com.freereader.all.NewsDetail;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2017/9/20.
 */
public interface ApiStore {
    @GET("doLogin")
    Observable<Map<String,NewsDetail>> getNewDetail(
            @Header("Cache-Control")String cacheControl,
            @Path("newsID")String newID);
}
