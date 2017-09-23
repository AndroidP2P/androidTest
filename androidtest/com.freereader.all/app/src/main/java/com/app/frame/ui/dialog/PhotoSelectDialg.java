package com.app.frame.ui.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.freereader.all.R;

/**
 * Created by kanxue on 2016/12/31.
 */
public class PhotoSelectDialg extends CustomProgressDialogView {
    private Context mContext;
    private Button selectFromPhotos;
    private Button selectFromCamera;
    private Button selectCancel;
    private TextView content;

    public PhotoSelectDialg(Context context) {
        super(context);
        this.mContext=context;
        init();
    }

    void init(){
        View exitPopView= LayoutInflater.from(mContext).inflate(R.layout.app_takephoto_dialog,null);
        this.setContentView(exitPopView);
        this.getWindow().getAttributes().gravity = Gravity.CENTER;
        getWindow().getAttributes().dimAmount=0.4f;
        selectFromCamera= (Button) exitPopView.findViewById(R.id.camera_camera);
        selectFromPhotos= (Button) exitPopView.findViewById(R.id.camera_phone);
        selectCancel= (Button) exitPopView.findViewById(R.id.camera_cancel);
        selectFromCamera.setOnClickListener(onSelectFromCameraListener);
        selectFromPhotos.setOnClickListener(onSelectFromPhotoListener);
        selectCancel.setOnClickListener(onSelectCancelListener);
    }

    public void setOnSelectFromCameraListener(View.OnClickListener listener){
        selectFromCamera.setOnClickListener(listener);
    }

    public void setOnSelectFromPhotoListener(View.OnClickListener listener){
        selectFromPhotos.setOnClickListener(listener);
    }

    public void setOnSelectCacelListener(View.OnClickListener listener){
        selectCancel.setOnClickListener(listener);
    }

    View.OnClickListener onSelectFromCameraListener =new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };

    View.OnClickListener onSelectFromPhotoListener =new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };

    View.OnClickListener onSelectCancelListener =new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };
}
