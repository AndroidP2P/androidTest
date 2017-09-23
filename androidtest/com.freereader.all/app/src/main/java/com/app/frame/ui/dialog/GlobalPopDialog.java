package com.app.frame.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by kanxue on 2017/1/4.
 */
public class GlobalPopDialog extends CustomProgressDialogView {
    public GlobalPopDialog(Context context) {
        super(context);
    }

    public GlobalPopDialog(Context context, String strMessage) {
        super(context, strMessage);
    }

    //global display dialog
    public static void createDialog(Context context,String message,String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setTitle(title)
                .setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
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
    public static void createDialog(Context context, String message, String title, DialogInterface.OnClickListener sureListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setTitle(title)
                .setCancelable(false)
                .setPositiveButton("确定",sureListener);
        AlertDialog alert = builder.create();
        //添加"全局对话框"
        alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alert.show();
    }
}
