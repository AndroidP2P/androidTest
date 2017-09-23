package com.freereader.all;

import android.content.Context;

import com.app.frame.http.ApiStore;
import com.app.frame.http.RxjavaNetworkRequest.HttpBaseConfig;
import com.app.frame.http.RxjavaNetworkRequest.Rx.RxSchedulers;
import com.app.frame.http.RxjavaNetworkRequest.Rx.RxSubscriber;
import com.app.frame.mvp.BasePresenter;
import com.app.frame.mvp.BaseView;

import java.util.Map;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2017/9/21.
 */
public class NewTestPresenter extends BasePresenter implements NewInfo{

    private NewDetailView mView;
    @Override
    public void attachView(BaseView view, Context context) {
        mView= (NewDetailView) view;
        super.mBaseCtx=context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStop() {

    }


    @Override
    public void loadNewsData(String id) {
        //这里mBaseView 会自动回调数据
        mBaseManager.add(getNewsDetailInfo(id).subscribe(new RxSubscriber<NewsDetail>(mBaseCtx) {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            protected void _onNext(NewsDetail newsDetail) {
                mView.onSuccess(newsDetail);
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }

    @Override
    public void loadNextPageNews(int page) {

    }

    private Observable<NewsDetail> getNewsDetailInfo(String newsID) {
        return HttpBaseConfig.getDefault(0).getNewDetail("", "")
                .map(new Func1<Map<String, NewsDetail>, NewsDetail>() {
                    @Override
                    public NewsDetail call(Map<String, NewsDetail> stringNewsDetailMap) {
                        return null;
                    }
                }).compose(RxSchedulers.<NewsDetail>io_main());
    }
}