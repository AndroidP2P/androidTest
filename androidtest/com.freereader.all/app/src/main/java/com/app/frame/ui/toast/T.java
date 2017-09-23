package com.app.frame.ui.toast;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.kernel.AppEpplication.AppApplication;
import com.freereader.all.R;

//Toast统一管理类
public class T {
    private static Handler handler = new Handler(Looper.getMainLooper());

    private static Toast toast = null;

    private static Object synObj = new Object();
    private static Context mContext= AppApplication.getInstance().getApplicationContext();
    private T() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 显示一个文本并且设置时长
     *
     * @param msg
     * @param len
     */
    public static void showMessageText(final CharSequence msg, final int len)
    {
        if (msg == null || msg.equals("")) {
            return;
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                synchronized (synObj) { //加上同步是为了每个toast只要有机会显示出来
                    if (toast != null) {
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.setView(getCustomToastView(msg));
                        toast.setDuration(len);
                    } else {
                        toast =new Toast(mContext);
                        toast.setView(getCustomToastView(msg));
                        toast.setGravity(Gravity.CENTER, 0, 0);
                    }
                    toast.show();
                }
            }
        });
    }

    private static View getCustomToastView(CharSequence mContent){
        View inflater=LayoutInflater.from(mContext).inflate(R.layout.app_base_toast_view,null);
        TextView mTempTextView= (TextView) inflater.findViewById(R.id.toast_textview);
        mTempTextView.setText(mContent);
        return inflater;
    }
    /**
     * 短时间显示Toast
     *
     * @param message
     */


    public static void showShort(CharSequence message) {
        showMessageText(message, Toast.LENGTH_SHORT);
    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong(CharSequence message) {
        showMessageText(message, Toast.LENGTH_LONG);
    }


    /**
     * 自定义显示Toast时间
     *  @param message
     * @param duration
     */
    public static void show(CharSequence message, int duration) {
        showMessageText(message, duration);
    }



}