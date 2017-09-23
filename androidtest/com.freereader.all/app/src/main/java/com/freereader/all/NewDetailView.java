package com.freereader.all;

import com.app.frame.mvp.BaseView;

/**
 * Created by Administrator on 2017/9/21.
 */
public interface NewDetailView extends BaseView{
    void onSuccess(NewsDetail mBook);
    void onError(String result);
}
