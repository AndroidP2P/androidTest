package com.app.frame.ui.theme;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;

import com.app.libs.SPUtils;
import com.jaydenxiao.common.commonwidget.StatusBarCompat;
import com.jaydenxiao.common.daynightmodeutils.ChangeModeHelper;

/**
 * Created by kanxue on 2016/12/1.
 */
public class AppThemeControlManager {

    public static final int MODE_DAY = 1;

    public static final int MODE_NIGHT = 2;
    private static String Mode = "mode";

    public void setTheme(Context ctx, int Style_Day, int Style_Night) {
        if (ChangeModeHelper.getChangeMode(ctx) == ChangeModeHelper.MODE_DAY) {
            ctx.setTheme(Style_Day);
        } else if (ChangeModeHelper.getChangeMode(ctx) == ChangeModeHelper.MODE_NIGHT) {
            ctx.setTheme(Style_Night);
        }
    }

    public int getChangeMode() {
        int mode = (int) SPUtils.get("config_mode", MODE_DAY);
        return mode;
    }

    public static void setChangeMode(int mode) {
        SPUtils.put("config_mode", mode);
    }


    // define instance of manager
    public static AppThemeControlManager themeInstance;
    // define mContext
    private Context mThemeContext;

    public static AppThemeControlManager getInstance() {
        if (themeInstance == null) {
            themeInstance = new AppThemeControlManager();
        }
        return themeInstance;
    }

    private void setThemeContext(Context context) {
        this.mThemeContext = context;
    }


    //设置状态栏颜色 4.4 以上
    protected void SetStatusBarColor() {
        StatusBarCompat.setStatusBarColor((Activity) mThemeContext, ContextCompat.getColor(mThemeContext, com.jaydenxiao.common.R.color.main_color));
    }

    //4.4 以上
    protected void SetStatusBarColor(int color) {
        StatusBarCompat.setStatusBarColor((Activity) mThemeContext, color);
    }

    //设置状态栏 为沉浸式
    protected void SetTranslanteBar() {
        StatusBarCompat.translucentStatusBar((Activity) mThemeContext);
    }

    // set app background
    public void setAppBaseBackGround() {

    }

    public void setAppThemeForBlur() {
        // 10 代表模糊程度
//        View.setBackgroundDrawable(BlurImageView.BlurImages(null,null,10));
    }

    // set app alpha
    public void setAppAlpha() {


    }

    // set language
    public void setLanguage() {

    }

    // set font style
    public void setFontStyle() {

    }

}
