package com.app.frame.mvp;

import android.content.Context;

import com.app.frame.http.RxjavaNetworkRequest.Rx.RxManager;

/**
 * Created by Administrator on 2017/9/21.
 */

/**
 * mvp 的模式就是为了把 activity 与 model 分开去写。
 * model 提供数据，也就是数据结构。
 * view 就是activity
 * presenter 就是 把activity 与model 业务逻辑给做了。
 * 在presenter里面获取数据也是可以的。
 *
 */


public abstract class BasePresenter {
    public Context mBaseCtx;
    public RxManager mBaseManager = new RxManager();
    public abstract void attachView(BaseView view,Context context); //设置外部presenter
    public void onDestroy() {
        mBaseManager.clear();
    }
   public abstract void onCreate();
   public abstract void onStop();
}
