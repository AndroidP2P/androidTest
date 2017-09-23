package com.freereader.all;

import rx.Observable;

/**
 * Created by Administrator on 2017/9/21.
 */
public interface NewInfo {
    void loadNewsData(String id);
    void loadNextPageNews(int page);
}
