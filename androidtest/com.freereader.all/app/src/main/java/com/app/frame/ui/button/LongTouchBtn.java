package com.app.frame.ui.button;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Created by ezdigit on 2016/8/26.
 */
public class LongTouchBtn extends Button {
    public int count=0;
    /**
     * 记录当前自定义Btn是否按下
     */
    private boolean clickdown = false;

    /**
     * 下拉刷新的回调接口
     */
    private LongTouchListener mListener;

    /**
     * 按钮长按时 间隔多少毫秒来处理 回调方法
     */
    private int mtime;

    /**
     * 构造函数
     * @param context
     * @param attrs
     */
    public LongTouchBtn(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    /**
     * 处理touch事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            clickdown = true;
            new LongTouchTask().execute();
        }
        else if(event.getAction() == MotionEvent.ACTION_UP)
        {
            clickdown = false;
        }
        return super.onTouchEvent(event);
    }


    /**
     * 处理长按的任务
     */
    class  LongTouchTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            while(clickdown)
            {
                try {
                    Thread.sleep(mtime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(0);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if(clickdown==true) {
                mListener.onLongTouch();
            }
        }

    }

    /**
     * 给长按btn控件注册一个监听器。
     *
     * @param listener
     *            监听器的实现。
     * @param time
     *            多少毫秒时间间隔 来处理一次回调方法
     */
    public void setOnLongTouchListener(LongTouchListener listener, int time) {
        mListener = listener;
        mtime = time;

    }

    /**
     * 长按监听接口，使用按钮长按的地方应该注册此监听器来获取回调。
     */
    public interface LongTouchListener {

        /**
         * 处理长按的回调方法
         */
        void onLongTouch();
    }
}