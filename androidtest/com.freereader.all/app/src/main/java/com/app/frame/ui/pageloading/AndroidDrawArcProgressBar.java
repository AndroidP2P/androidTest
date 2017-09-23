package com.app.frame.ui.pageloading;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.app.libs.DensityUtils;


public class AndroidDrawArcProgressBar extends View {

    private int canvasWidth = 0;
    private int canvasHeight = 0;
    private String percentText = "";
    private int textSize = 0;
    private float percentValue = 0;
    private Canvas drawArcProgressBarCanvas = null;
    private Paint drawArcProgressBarPaint = null;
    private Paint drawTextPaint = null;
    private int backgroundColor = Color.WHITE;
    private int progressViewColor = Color.BLACK;
    private int progressTextColor = Color.BLACK;

    private int ColorDefaultText = Color.BLACK;
    private int ColorDefaultFill = Color.rgb(119, 186, 5);

    public void setColorDefaultFill(int color)
    {
        this.ColorDefaultFill = color;
        this.progressViewColor=color;
    }

    public void setText(String textTitle)
    {
        this.percentText = textTitle;
        invalidate();
    }

    private float maxProgressValue = 0.0f;
    private boolean isDynamicLoading = false;
    public static int EVENT_DYNAMIC_LOADING = 1;
    public static int EVENT_DYNAMIC_COLOR_GRAD = 2;
    private float colorProgressGrads[] = new float[] { 0.33f, 0.66f, 1.0f };
    private boolean colorGrads = false;
    public void setMaxProgressValue(float maxProgressValue)
    {
        this.maxProgressValue = maxProgressValue;
    }

    public void setColorGradsEnable(boolean flag)
    {
        this.colorGrads = flag;
    }

    private int colorGradsScheme[] = null;

    public void setColorGradSchme(int[] colorScheme)
    {
        this.colorGradsScheme = colorScheme;
    }
    public void setTextSize(int textSize)
    {
        this.textSize = textSize;
    }

    public void setDelayTimeDuration(int timeDuration)
    {
        // TODO Auto-generated method stub
        this.delayTimeDuration=timeDuration;
    }


    float vlaueForProgress = 0.01f;
    int percentForLoadProgress = 0;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == EVENT_DYNAMIC_LOADING) {
                if (percentForLoadProgress > 100) {
                    isDynamicLoading = false;
                    return;
                }

                initPercenBar("", 0, vlaueForProgress);
                vlaueForProgress += 0.01f;
                percentForLoadProgress += 1;

            } else if (msg.what == EVENT_DYNAMIC_COLOR_GRAD)
            {
                if (colorGradsScheme == null)
                    return;
                if (colorGradsScheme.length == 0)
                    return;

                if (vlaueForProgress > maxProgressValue)
                {
                    isDynamicLoading = false;
                    return;
                }

                if (vlaueForProgress < colorProgressGrads[0])
                {
                    setColorDefaultFill(colorGradsScheme[0]);
                }

                if (vlaueForProgress > colorProgressGrads[0]
                        && vlaueForProgress < colorProgressGrads[1])
                {
                    setColorDefaultFill(colorGradsScheme[1]);
                }

                if (vlaueForProgress > colorProgressGrads[1]
                        && vlaueForProgress < colorProgressGrads[2])
                {
                    setColorDefaultFill(colorGradsScheme[2]);
                }

                initPercenBar("", 0, vlaueForProgress);
                vlaueForProgress += 0.01f;
                percentForLoadProgress += 1;

            }
        };
    };


    public void restore()
    {
        percentForLoadProgress=0;
        vlaueForProgress=0.0f;
        invalidate();
    }

    public void setDefaultBackgroundColor(int colorForBackground)
    {
        this.backgroundColor = colorForBackground;
    }

    public void setIsDynamicLoad(boolean flag)
    {
        this.isDynamicLoading = flag;
        if (isDynamicLoading)
            dynamicLoad();
    }

    public void setMessageSendType(int messageType)
    {
        this.messageSendType = messageType;
    }

    public AndroidDrawArcProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private int messageSendType = EVENT_DYNAMIC_LOADING;

    private int delayTimeDuration=40;
    public void dynamicLoad()
    {
        // TODO Auto-generated method stub
        Object asynObj = new Object();
        synchronized (asynObj)
        {
            new Thread(new Runnable()
            {

                @Override
                public void run()
                {
                    // TODO Auto-generated method stub
                    while (isDynamicLoading)
                    {
                        try {
                            Thread.sleep(delayTimeDuration);
                            handler.sendEmptyMessage(messageSendType);
                        } catch (InterruptedException e)
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    };
                }
            }).start();
        }
        ;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    public static float px2dp(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    public static float px2sp(Context context, float pxVal) {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        initConfiguration(canvas);
        initData();
    }

    @Override
    public void invalidate() {
        // TODO Auto-generated method stub
        super.invalidate();
    }

    public void initPercenBar(String text, int textSize, float percent) {
        this.percentText = text;
        this.textSize = textSize;
        this.percentValue = percent;
        invalidate();
    }

    public void setBackGround(int bgColor, int progressBarColor, int progressPercentColor) {
        this.backgroundColor = bgColor;
        this.progressViewColor = progressBarColor;
        this.progressTextColor = progressPercentColor;
    }

    public void setBackgroundColor(int bgColor)
    {
        this.backgroundColor=bgColor;
    }

    private void initData()
    {
        RectF recF = new RectF(0, 0, (int) (canvasWidth * this.percentValue), canvasHeight);
        drawArcProgressBarCanvas.drawRect(recF, drawArcProgressBarPaint);
        float textXLeft = (getWidth() - drawTextPaint.measureText(percentText)) / 2;
        drawArcProgressBarCanvas.drawText(percentText, textXLeft
                , getHeight() / 2 + px2sp(getContext(), textSize) / 2, drawTextPaint);
    }

    private void initConfiguration(Canvas canvas) {
        drawArcProgressBarCanvas = canvas;
        drawArcProgressBarCanvas.drawColor(backgroundColor);
        drawTextPaint = new Paint();
        drawArcProgressBarPaint = new Paint();
        drawArcProgressBarPaint.setAntiAlias(true);
        drawArcProgressBarPaint.setStyle(Paint.Style.FILL);
        drawArcProgressBarPaint.setColor(progressViewColor);
        drawTextPaint.setColor(progressTextColor);
        drawTextPaint.setTextSize(DensityUtils.sp2px(getContext(),textSize));
        drawArcProgressBarPaint.setStrokeWidth(20);
        canvasWidth = getWidth();
        canvasHeight = getHeight();
    }

}
