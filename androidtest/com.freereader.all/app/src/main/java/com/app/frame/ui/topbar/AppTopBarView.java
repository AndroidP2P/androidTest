package com.app.frame.ui.topbar;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.libs.DensityUtils;
import com.freereader.all.R;
/**
 * Created by kanxue on 2016/1/11.
 * this topbar view,we can treat them as a tool to operate activity. here we will use it
 * in any activities. and from this class we can set button click event and set the action
 * title,and the more operation.
 */
public class AppTopBarView extends RelativeLayout
{
    private ImageView topBarBackIcon;
    private TextView topBarTitleView;
    private Button topBarRightOperateBtn;
    private Context mContext = null;
    private OnClickListener mRightButtonListener;
    private RelativeLayout manbuNetstatelayout;
    private RelativeLayout manBu_NavigationBarView;
    private FrameLayout manbu_LoadingLayout;
    private ProgressBar manbu_ProgressBar;
    private Button manbu_RefreshBtn;
    private boolean isShown = false;

    //topBarConstruct
    public AppTopBarView(Context context)
    {
        super(context);
        this.mContext = context;
        init();
    }

    // topBarConstructor
    public AppTopBarView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.mContext = context;
        init();
    }

   public Button getTopBarRightOperateBtn(){
        return topBarRightOperateBtn;
    }

    /**
     * here is some code to initial view about actionbar
     * author kanxue
     */
    public void init() {
        LayoutInflater.from(mContext).inflate(R.layout.base_topbarviewlayout,this,true);
        //it is the net state layout
        manbuNetstatelayout = (RelativeLayout)findViewById(R.id.manbu_netstatelayout);
        manBu_NavigationBarView= (RelativeLayout) findViewById(R.id.manBu_ActionBar);
        topBarBackIcon = (ImageView) findViewById(R.id.manbu_Back);
        topBarRightOperateBtn = (Button) findViewById(R.id.manbu_operateBtn);
        topBarTitleView = (TextView) findViewById(R.id.manbu_TextTitle);
        manbu_ProgressBar = (ProgressBar) findViewById(R.id.manbu_ProgressBar);
        manbu_RefreshBtn = (Button) findViewById(R.id.manbu_RefreshBtn);
        manbu_LoadingLayout= (FrameLayout) findViewById(R.id.manbu_LoadingBaseLayout);
        initRightButtonExtraDelagate(80f);
    }

    private Rect defaultRect=null;
    private void initRightButtonExtraDelagate(final float dp) {
        setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                defaultRect=new Rect();
                getLocalVisibleRect(defaultRect);
                int right=defaultRect.right;
                defaultRect.left=right- DensityUtils.dp2px(mContext,dp);
                float leftClickEdge=event.getX();
                if(leftClickEdge>defaultRect.left){
                    if(getRightButtonVisualble()){
                        if(mRightButtonListener !=null){
                            getTopBarRightOperateBtn().performClick();
                        }
                    }
                }
                return false;
            }
        });

    }


    /**
     * we can use this function to setting the title,and set the operate btn
     * also we can control whether to show net state layout,and open operation,close operation
     */
    public void buildTopBarView(String topBarTitle, int leftBtnResource, int rightBtnResource, OnClickListener listener)
    {
        setTopBarTitle(topBarTitle);
        setLeftBackGroundResource(leftBtnResource);
        setRightBackgroundResource(rightBtnResource);
        this.mRightButtonListener = listener;
        if (mRightButtonListener == null)
        {
            topBarRightOperateBtn.setVisibility(View.GONE);
            return;
        }
        topBarRightOperateBtn.setOnClickListener(mRightButtonListener);
    }

    public void setTopBarRightOperateEvent(OnClickListener listener){
        this.mRightButtonListener =listener;
        topBarRightOperateBtn.setOnClickListener(listener);
    }

    public void buildTopBarView(String topBarTitle, int leftBtnResource, String rightBtnTitle, OnClickListener listener)
    {
        setTopBarTitle(topBarTitle);
        setLeftBackGroundResource(leftBtnResource);
        setRightBtnTextTitle(rightBtnTitle);
        this.mRightButtonListener = listener;
        if (mRightButtonListener == null)
        {
            topBarRightOperateBtn.setVisibility(View.GONE);
            return;
        }
        topBarRightOperateBtn.setOnClickListener(mRightButtonListener);
    }

    public void setRightBtnTextTitle(String rightBtnTitle)
    {
        if(rightBtnTitle==null|| rightBtnTitle.equals(""))
        {
            topBarRightOperateBtn.setVisibility(View.GONE);
            return;
        }

        topBarRightOperateBtn.setVisibility(View.VISIBLE);
        topBarRightOperateBtn.setText(rightBtnTitle);
    }


    //set TopBarBackClickEvent method
    public void setTopBarBackClickEvent(OnClickListener listener)
    {
        if(listener==null)
        {
            return;
        }

        topBarBackIcon.setOnClickListener(listener);
    }

    // set whether to display loadinglayout
    public void setdisplayLoadingLayoutVisibility(boolean flag) {
        if(flag) {
            manbu_LoadingLayout.setVisibility(View.VISIBLE);
            return;
        }
        manbu_LoadingLayout.setVisibility(View.GONE);
    }

    // set whether to loading progressBar layout
    public void setWhetherLoadingProgressBar(boolean isShownProgressBar, int refreshBtnRes, OnClickListener listener)
    {
        if (!isShownProgressBar)
        {
            manbu_ProgressBar.setVisibility(View.GONE);
            if (refreshBtnRes < 0)
            {
                return;
            }

            manbu_RefreshBtn.setBackgroundResource(refreshBtnRes);

            if(listener==null)
                return;

            manbu_RefreshBtn.setOnClickListener(listener);

        }
    }

    // set whether to display network broken layout
    public void setWhetherDisplayNetStateLayout(boolean flag)
    {
        this.isShown = flag;
        if (isShown)
        {
            manbuNetstatelayout.setVisibility(View.VISIBLE);
            return;
        }

        manbuNetstatelayout.setVisibility(View.GONE);
    }

    // set click event for topBarBackIcon
    public void setTopBarLeftButton(OnClickListener listener)
    {
        if (listener == null)
        {
            topBarBackIcon.setVisibility(View.GONE);
            return;
        }

        topBarBackIcon.setOnClickListener(listener);
    }

    // set TopBarTitle
    public void setTopBarTitle(String topBarTitle)
    {
        if (topBarTitle == null)
        {
            topBarTitleView.setVisibility(View.GONE);
            return;
        }

        if (topBarTitle.equals(""))
        {
            topBarTitleView.setVisibility(View.GONE);
            return;
        }

        topBarTitleView.setText(topBarTitle);

    }

    // set left image view background.
    public void setLeftBackGroundResource(int leftResource)
    {
        if (leftResource < 0)
        {
            topBarBackIcon.setVisibility(View.GONE);
            return;
        }

        topBarBackIcon.setBackgroundResource(leftResource);
    }


    //set right button background
    public void setRightBackgroundResource(int rightBackgroundRes)
    {
        if (rightBackgroundRes < 0) {
            topBarRightOperateBtn.setVisibility(View.GONE);
            return;
        }
        topBarRightOperateBtn.setVisibility(View.VISIBLE);
        topBarRightOperateBtn.setText(null);
        getButtonLayoutParams(topBarRightOperateBtn).setMargins(0,0,
                (int) getResources().getDimension(R.dimen.manbu_topbar_right_margin),0);
        getButtonLayoutParams(topBarRightOperateBtn).width=(int) getResources().getDimension(R.dimen.manbu_topbar_right_icon_width_height);
        getButtonLayoutParams(topBarRightOperateBtn).height=(int) getResources().getDimension(R.dimen.manbu_topbar_right_icon_width_height);
        topBarRightOperateBtn.setBackgroundResource(rightBackgroundRes);
    }

    RelativeLayout.LayoutParams getButtonLayoutParams(Button buttonMargin){
        RelativeLayout.LayoutParams layoutParams;
        layoutParams = (RelativeLayout.LayoutParams) buttonMargin.getLayoutParams();
        return layoutParams;
    }

    /**
     * hide actionbar
     * @param flag
     * true :show the topbarview
     * flase:hide the topbarView
     */

    public void setNavigationBarViewVisibility(boolean flag)
    {
        if(manBu_NavigationBarView!=null && flag)
            manBu_NavigationBarView.setVisibility(View.VISIBLE);

        if(manBu_NavigationBarView!=null)
            manBu_NavigationBarView.setVisibility(View.GONE);
    }

    boolean isRightButtonVisualBle=false;
    public void setTopBarRightOperateBtnVisualble(boolean flag){
        isRightButtonVisualBle=flag;
        if(flag){
            topBarRightOperateBtn.setVisibility(View.VISIBLE);
            return;
        }
        topBarRightOperateBtn.setVisibility(View.GONE);
    }

    boolean getRightButtonVisualble(){
        return isRightButtonVisualBle;
    }

    public void setLeftBackButtonVisualble(boolean flag){

        if(flag){
            topBarBackIcon.setVisibility(View.VISIBLE);
            return;
        }
        topBarBackIcon.setVisibility(View.GONE);
    }

}
