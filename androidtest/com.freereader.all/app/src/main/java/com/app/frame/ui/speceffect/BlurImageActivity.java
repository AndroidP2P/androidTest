package com.app.frame.ui.speceffect;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import com.app.frame.base.APPBaseActivity;
import com.freereader.all.R;

/**
 * Created by kanxue on 2016/12/8.
 */
public class BlurImageActivity extends APPBaseActivity {

    private static Bitmap mBackgroundBitmap=null;
    private ImageView blurImageView;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_blurpage_layout);
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        if(mBackgroundBitmap!=null){
            BlurImageView.BlurImages(mBackgroundBitmap,mContext,8);
        }

        blurImageView.setImageBitmap(mBackgroundBitmap);
    }

    @Override
    public void initViews() {
        mContext=this;
        blurImageView= (ImageView) findViewById(R.id.blurImageView);
        mBackgroundBitmap=BitmapFactory.decodeResource(getResources(),R.mipmap.ic_start_page);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mBackgroundBitmap!=null){
            mBackgroundBitmap.recycle();
            mBackgroundBitmap=null;
        }
    }
}
