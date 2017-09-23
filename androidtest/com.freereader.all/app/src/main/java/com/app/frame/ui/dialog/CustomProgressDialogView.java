/*
 *kanxue2016-3-9下午5:01:382016 
 */
package com.app.frame.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

import com.app.frame.ui.toast.T;
import com.freereader.all.R;

public class CustomProgressDialogView extends Dialog {

    public Object mExtras;

    public Object getmExtras() {
        return mExtras;
    }

    public void setmExtras(Object mExtras) {
        this.mExtras = mExtras;
    }
    private Context mCtx;
    /**
     * 消息TextView
     */
    private TextView tvMsg;
    static final long MAX_DELAY_TIME = 8000;

    public CustomProgressDialogView(Context context) {
        super(context,R.style.CustomProgressDialog);
        this.mCtx = context;
    }

    boolean isRunnableRun = false;
    final int DISSMISS_MESSAGE_TYPE=0x0001;
    Runnable timeDelayRunnable = new Runnable() {
        @Override
        public void run() {
            if (isRunnableRun) {
                try {
                    Thread.sleep(MAX_DELAY_TIME);
                    dialogDissmissHandle.sendEmptyMessage(DISSMISS_MESSAGE_TYPE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    Handler dialogDissmissHandle=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(isShowing()) {
                T.showLong(delayTitle);
                dismiss();
            }
        }
    };

    public void setDelayDismissDialog(boolean isDelay) {
        this.isRunnableRun = isDelay;
    }

    private String delayTitle="";
    public void setDelayTitle(String title){
        this.delayTitle=title;
    }
    @Override
    public void show() {
        super.show();
        if (isRunnableRun) {
            new Thread(timeDelayRunnable).start();
        }
    }

    public CustomProgressDialogView(Context context, String strMessage) {
        this(context, R.style.CustomProgressDialog, strMessage);
    }

    public CustomProgressDialogView(Context context, int theme, String strMessage) {
        super(context, theme);
        this.setContentView(R.layout.view_progress_dialog);
        this.getWindow().getAttributes().gravity = Gravity.CENTER;
        tvMsg = (TextView) this.findViewById(R.id.tv_msg);
        setMessage(strMessage);
    }

    /**
     * 设置进度条消息
     *
     * @param strMessage 消息文本
     */
    public void setMessage(String strMessage) {
        if (tvMsg != null) {
            tvMsg.setText(strMessage);
        }
    }

    //global display dialog
    public static void createDialog(Context context, String message, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setTitle(title)
                .setCancelable(false)
                .setPositiveButton("确定", new OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        //添加"全局对话框"
        alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alert.show();
    }

    //global display dialog
    public static void createCustomDialog(Context context, String message, String title, OnClickListener sureListener,
                                          OnClickListener cancleListener, boolean isForce) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setTitle(title);
        if (isForce) {
            builder.setCancelable(false);
        }
        builder.setPositiveButton("确定", sureListener);
        if (!isForce) {
            builder.setNegativeButton("取消", cancleListener);
        }
        AlertDialog alert = builder.create();
        //添加"全局对话框"
        alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alert.show();
    }
}
