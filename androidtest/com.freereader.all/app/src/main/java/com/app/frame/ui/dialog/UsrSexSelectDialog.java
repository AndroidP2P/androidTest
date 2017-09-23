package com.app.frame.ui.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.freereader.all.R;

/**
 * Created by kanxue on 2017/1/6.
 */
public class UsrSexSelectDialog extends CustomProgressDialogView{

    private Context mContext;
    private onSelectGenderListener selectGenderListener;
    public UsrSexSelectDialog(Context context) {
        super(context);
        this.mContext=context;
        init();
    }

    void init(){
        View basicInfoSexLayout= LayoutInflater.from(mContext).inflate(R.layout.more_genderselectlayout,null);
        this.setContentView(basicInfoSexLayout);
        this.getWindow().getAttributes().gravity = Gravity.CENTER;
        getWindow().getAttributes().dimAmount=0.4f;
        setTitle(mContext.getString(R.string.manbuMoreFragmentUserSex));
        basicInfoSexLayout.findViewById(R.id.basicInfo_genderMaleLayout).setOnClickListener(onClickManListener);
        basicInfoSexLayout.findViewById(R.id.basicInfo_genderFemaleLayout).setOnClickListener(onClickWoManListener);
    }

    View.OnClickListener onClickManListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            selectGenderListener.loadGender(GENDER_MALE);
            dismiss();
        }
    };

    public void setOnCheckGenderListener(onSelectGenderListener listener){
        selectGenderListener=listener;
    }

    View.OnClickListener onClickWoManListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            selectGenderListener.loadGender(GENDER_FEMALE);
            dismiss();
        }
    };

    public interface onSelectGenderListener{
        void loadGender(int type);
    }

    public static final int GENDER_MALE=1;
    public static final int GENDER_FEMALE=2;
}
