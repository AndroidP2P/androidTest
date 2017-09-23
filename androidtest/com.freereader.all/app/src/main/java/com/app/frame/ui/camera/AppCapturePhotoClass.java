package com.app.frame.ui.camera;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.app.frame.ui.dialog.PhotoSelectDialg;
import com.app.frame.ui.imageview.CropImageViewActivity;
/**
 * Created by kanxue on 2016/12/31.
 */
public class AppCapturePhotoClass {

    private static AppCapturePhotoClass instance;
    public static AppCapturePhotoClass getInstance() {
        if (instance == null) {
            instance = new AppCapturePhotoClass();
        }
        return instance;
    }

    private Context mContext;
    private PhotoSelectDialg progressDialogView;
    public static final int REQUEST_FOR_GET_CAPTURE=0x0001;
    public void popSourceSelectWindow(Context context) {
        mContext = context;
        progressDialogView = new PhotoSelectDialg(mContext);
        progressDialogView.setOnSelectFromCameraListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialogView.dismiss();
                Intent intent = new Intent(mContext, CropImageViewActivity.class);
                intent.putExtra(CropImageViewActivity.TAG_FOR_OPERATION_TYPE, CropImageViewActivity.TAKE_PIC_FROM_CAMERA);
                ((Activity)mContext).startActivityForResult(intent,REQUEST_FOR_GET_CAPTURE);
            }
        });

        progressDialogView.setOnSelectFromPhotoListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialogView.dismiss();
                Intent intent = new Intent(mContext, CropImageViewActivity.class);
                intent.putExtra(CropImageViewActivity.TAG_FOR_OPERATION_TYPE, CropImageViewActivity.TAKE_PIC_FROM_ALBUM);
                ((Activity)mContext).startActivityForResult(intent,REQUEST_FOR_GET_CAPTURE);
            }
        });
        progressDialogView.show();
    }
}
