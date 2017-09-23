package com.app.frame.ui.imageview;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.app.frame.base.APPBaseActivity;
import com.app.frame.base.APPBaseSlidingActivity;
import com.app.frame.cache.filecache.FileCacheManager;
import com.app.frame.ui.toast.T;
import com.app.frame.ui.topbar.AppTopBarView;
import com.app.kernel.Exception.Log.AppExceptionLogUtils;
import com.app.libs.FileUtils;
import com.app.libs.ImageUtils;
import com.app.libs.PermissionTool;
import com.freereader.all.R;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by kanxue on 2016/12/31.
 */
public class CropImageViewActivity extends APPBaseActivity {

    public static final int TAKE_PIC_FROM_ALBUM = 0x0001;
    public static final int TAKE_PIC_FROM_CAMERA = 0x0002;
    public static final int DISPLAY_PIC_FROM_URI = 0x0005;
    public static final String TAG_FOR_OPERATION_TYPE = "operation_type";
    static final String TEMP_PHOTO_NAME = "temp_photo_name.jpg";
    static final int RESULT_FOR_ALBULM = 0x0003;
    static final int RESULT_FOR_CAMERA = 0x0004;
    static Bitmap bitmapTemp = null;
    private CropImageView cropImageView;
    private Context mContext;

    private String cacheImgFilePath = null;

    void setSelectedImgPath(String path) {
        cacheImgFilePath = path;
    }

    String getSelectedImgPath() {
        return cacheImgFilePath;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_crop_imageview_layout);
        initViews();
        initListeners();
        initData();
    }

    @Override
    public void initData() {
        //执行指令
        if (tag_operation == TAKE_PIC_FROM_ALBUM) {
            systemPhoto();
            return;
        } else if (tag_operation == TAKE_PIC_FROM_CAMERA) {
            cameraPhoto();
            return;
        } else if (tag_operation == DISPLAY_PIC_FROM_URI) {
            return;
        }
    }

    private int tag_operation = -1;

    @Override
    public void initViews() {
        mContext = this;
        Intent intent = getIntent();
        if (intent == null) {
            finish();
            return;
        }
        tag_operation = intent.getIntExtra(TAG_FOR_OPERATION_TYPE, -1);
        if (tag_operation == -1) {
            finish();
            return;
        }
        cropImageView = (CropImageView) findViewById(R.id.cropImage);
        initTopBarView((AppTopBarView) findViewById(R.id.cropImageTopBar));
        getTopBarView().setLeftBackButtonVisualble(true);
        getTopBarView().setTopBarRightOperateBtnVisualble(true);
        getTopBarView().setRightBtnTextTitle(getString(R.string.manbu_save_title));
        getTopBarView().setTopBarTitle(getString(R.string.usrBasicInfoReview));
    }

    @Override
    public void initListeners() {

    }

    public Uri getImageCacheUri() {
        try {
            File directoryForImage = new File(FileCacheManager.getInstance().
                    getPublicImageCachePath() + File.separator + TEMP_PHOTO_NAME);
            if (!directoryForImage.exists()) {
                directoryForImage.createNewFile();
            }
            return Uri.fromFile(directoryForImage);
        } catch (Exception e) {
            AppExceptionLogUtils.LOG_FOR_STL(e);
            try {
                File outDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                if (!outDir.exists()) {
                    outDir.mkdirs();
                }
                File outFile = new File(outDir, TEMP_PHOTO_NAME);
                return Uri.fromFile(outFile);
            } catch (Exception ex) {
                AppExceptionLogUtils.LOG_FOR_STL(ex);
            }
            return null;
        }
    }

    /**
     * 打开系统相册
     */
    private void systemPhoto() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK);
        // 如果朋友们要限制上传到服务器的图片类型时可以直接写如：image/jpeg 、 image/png等的类型
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(pickIntent, RESULT_FOR_ALBULM);
    }

    /**
     * 调用相机拍照
     */

    private void cameraPhoto() {
        if (!PermissionTool.isCameraCanUse()) {
            T.showShort("未获得调用摄像头权限,请到手机的[设置]->[权限管理]->[调用摄像头]中允许“漫步人生”调用摄像头的权限");
            if(!PermissionTool.getBasePermission(PermissionTool.PERMISSION_CAMERA,this)){
                PermissionTool.requestPermission(this, PermissionTool.CODE_CAMERA, new PermissionTool.PermissionGrant() {
                    @Override
                    public void onPermissionGranted(int requestCode) {
                    }
                });
                finish();
            }
            return;
        }
        String sdStatus = Environment.getExternalStorageState();
        /* 检测sd是否可用 */
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//action is capture
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getImageCacheUri());
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, RESULT_FOR_CAMERA);
    }

    void dealWithResultCamera() {
        try {
            bitmapTemp = BitmapFactory.decodeStream(getContentResolver().openInputStream(getImageCacheUri()));
            display(bitmapTemp);
        } catch (Exception e) {
            AppExceptionLogUtils.LOG_FOR_STL(e);
        }
    }

    void display(Bitmap bitmap) {
        Drawable drawable = new BitmapDrawable(bitmap);
        //设置资源和默认长宽
        cropImageView.setDrawable(drawable, 300, 300);
    }

    void dealWithResultFromAlbum(Intent data) {
        try {
            Uri uri = data.getData();
            // the first Check
            if (bitmapTemp == null) {
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = mContext.getContentResolver().query(uri, filePathColumn, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String publicImageFilePath = cursor.getString(columnIndex);
                    //图片地址
                    setSelectedImgPath(publicImageFilePath);
                    cursor.close();
                    // 由于 选择图片带来的路径读取问题，这里将会为图片创建路径
                    FileInputStream fis = new FileInputStream(publicImageFilePath);
                    bitmapTemp = BitmapFactory.decodeStream(fis);
                    display(bitmapTemp);
                } else {
                    ContentResolver cr = getContentResolver();
                    if (bitmapTemp != null) {
                        bitmapTemp.recycle();
                    }
                    bitmapTemp = BitmapFactory.decodeStream(cr.openInputStream(uri));
                    display(bitmapTemp);
                }
            }
        } catch (Exception e) {
            AppExceptionLogUtils.LOG_FOR_STL(e);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.manbu_operateBtn:
                getTopBarView().setTopBarRightOperateEvent(null);
                doDealBackData();
                break;
        }
    }

    public static final String TAG_FOR_CROP_RESULT_PATH = "result_for_bitmap_path";

    private void doDealBackData() {
        if (cropImageView.getCropImage() == null) {
            getTopBarView().setTopBarRightOperateEvent(this);
            T.showShort("图片不存在，或裁剪失败");
            return;
        }
        //检测参数是否合理。
        if (TextUtils.isEmpty(getSelectedImgPath())) {
            getTopBarView().setTopBarRightOperateEvent(this);
            T.showShort("裁剪路径不存在");
            return;
        }
        //裁剪图片保存到本地。
        cropCustumInches();
        //把图片地址，传出去。比如上一个Activity.
        Intent mAvatorPathIntent = new Intent();
        mAvatorPathIntent.putExtra(TAG_FOR_CROP_RESULT_PATH, getSelectedImgPath());
        setResult(RESULT_OK, mAvatorPathIntent);
        //关闭界面
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (bitmapTemp != null) {
                bitmapTemp.recycle();
                bitmapTemp = null;
            }
        } catch (Exception e) {
            AppExceptionLogUtils.LOG_FOR_STL(e);
        }
    }

    //裁剪自定义尺寸
    void cropCustumInches() {
        try {
            Bitmap mBitmap = cropImageView.getCropImage();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            mBitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
            byte[] byteArray = stream.toByteArray();
            setSelectedImgPath(FileCacheManager.getInstance().getPublicImageCachePath() + File.separator + System.currentTimeMillis() + ".png");
            FileUtils.saveFile(getSelectedImgPath(), byteArray);
        }catch (Exception e){
            AppExceptionLogUtils.LOG_FOR_STL(e);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_FOR_ALBULM && resultCode == RESULT_OK && data != null) {
            dealWithResultFromAlbum(data);
        }else if (requestCode == RESULT_FOR_CAMERA && resultCode == RESULT_OK) {
            dealWithResultCamera();
            setSelectedImgPath(getImageCacheUri().getPath());
        }else{
            finish();
        }
    }
}
