package com.app.frame.ui.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.freereader.all.R;

/**
 * Created by kanxue on 2016/12/16.
 */
public class AppExitPopDialog extends CustomProgressDialogView {
    private Context mContext;
    private Button sureButton;
    private Button cancelButton;
    private TextView existTitle;
    private TextView content;

    public AppExitPopDialog(Context context) {
        super(context);
        this.mContext=context;
        init();
    }

    void init(){
        View exitPopView= LayoutInflater.from(mContext).inflate(R.layout.app_exit_pop_layout,null);
        this.setContentView(exitPopView);
        this.getWindow().getAttributes().gravity = Gravity.CENTER;
        getWindow().getAttributes().dimAmount=0.75f;
        existTitle = (TextView) this.findViewById(R.id.manbu_indicateTextTitle);
        sureButton= (Button) findViewById(R.id.Sure);
        cancelButton= (Button) findViewById(R.id.Cancel);
        content= (TextView) findViewById(R.id.manbu_popWindowIndicate);
        sureButton.setOnClickListener(onClickSureButtonListener);
        cancelButton.setOnClickListener(onClickCancleButtonListener);
    }

    public void showCancleButton(boolean flag){
        if(flag) {
            cancelButton.setVisibility(View.VISIBLE);
        }else{
            cancelButton.setVisibility(View.GONE);
        }
    }

    public void showSureButton(boolean flag){
        if(flag) {
            sureButton.setVisibility(View.VISIBLE);
        }else{
            sureButton.setVisibility(View.GONE);
        }
    }


    public void setOnSureButtonClickListener(View.OnClickListener listener){
        sureButton.setOnClickListener(listener);
    }

    public void setOnCancleButtonClickListener(View.OnClickListener listener){
        cancelButton.setOnClickListener(listener);
    }

    View.OnClickListener onClickSureButtonListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };

    View.OnClickListener onClickCancleButtonListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };

   public void setSureButtonTitle(String title){
        sureButton.setText(title);
    }

   public void setCancleButtonTitle(String title){
        cancelButton.setText(title);
    }

    public void setExistTitle(String title){
        existTitle.setText(title);
    }

    @Override
    public void setMessage(String strMessage) {
        if(content==null){
            return;
        }
        content.setText(strMessage);
    }
}
