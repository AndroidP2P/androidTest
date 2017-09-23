package com.app.frame.ui.search;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.libs.KeyBoardUtils;
import com.freereader.all.R;


/**
 * Created by kanxue on 2016/8/23.
 */
public class SearchBarView extends RelativeLayout implements TextWatcher{

    private Context mContext;
    public SearchBarView(Context context) {
        super(context);
        this.mContext=context;
    }

    public SearchBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;
        init();
    }

    private ImageButton searchIcon;
    private ImageButton delButton;
    private EditText search_content;
    private Button do_search;
    private void init() {
        LayoutInflater.from(mContext).inflate(R.layout.layout_search_bar,this,true);
        initViews();
        initListeners();
        initData();
    }

    public void setDoSearchButtonTitle(String content)
    {
       if(TextUtils.isEmpty(content))
       {
           return;
       }
       if(content.length()>2)
           return;
       do_search.setText(content);
    }

    private boolean isSearchBarAlive;
    // at this moment,user can input sth into seachBar
    public void setSearchAlive(boolean isAlive)
    {
        isSearchBarAlive=isAlive;
        if(isAlive)
        {
            search_content.setLayoutParams(new LinearLayout.LayoutParams
                    (LayoutParams.FILL_PARENT,LayoutParams.MATCH_PARENT,1f));
            isStartFocous(true);
            //setDoSearchButtonVisualble(true);
            do_search.setVisibility(View.VISIBLE);
        }else
        {
            search_content.setText(null);
            search_content.setLayoutParams(new LinearLayout.LayoutParams
                    (LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT,0f));
            isStartFocous(false);
            //setDoSearchButtonVisualble(false);
            do_search.setVisibility(View.GONE);
            KeyBoardUtils.closeKeybord(search_content,mContext);
        }
    }

    private void clearContent()
    {
        if(search_content==null)
        {
            return;
        }
        search_content.setText(null);
    }

    public String getSearchViewContent()
    {
        if(search_content==null)
            return null;
        String contentSearch=search_content.getText().toString();
        if(TextUtils.isEmpty(contentSearch)) {
            return null;
        }
        return contentSearch.trim();
    }

    private OnClickListener onDelButtonClick=new OnClickListener() {
        @Override
        public void onClick(View view) {
            clearContent();
        }
    };

    private void initData() {
        configur_input_method();
    }

    private void configur_input_method() {
        // nothing
        search_content.setInputType(InputType.TYPE_NULL);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if(ev.getAction()==MotionEvent.ACTION_DOWN)
        {
            if(!isSearchBarAlive)
            {
                search_content.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
                search_content.setInputType(EditorInfo.TYPE_CLASS_TEXT);
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setSearchAlive(true);
                    }
                },200);
                return false;
            }
        }

        return super.dispatchTouchEvent(ev);
    }

    private void initListeners() {
        search_content.addTextChangedListener(this);
        delButton.setOnClickListener(onDelButtonClick);
        do_search.setOnClickListener(onDoSearchClick);
        search_content.setOnEditorActionListener(onKeyBoardClickListener);
    }

    private void initViews() {
        searchIcon= (ImageButton) findViewById(R.id.search_icon);
        delButton= (ImageButton) findViewById(R.id.search_cancel_icon);
        search_content= (EditText) findViewById(R.id.search_content);
        do_search= (Button) findViewById(R.id.do_search);
    }

    private EditText.OnEditorActionListener onKeyBoardClickListener=new EditText.OnEditorActionListener()
    {

        @Override
        public boolean onEditorAction(TextView textView, int actionID, KeyEvent keyEvent) {
            System.out.print("actopmOD="+actionID);
            // do search
            int keyBoardActionID=actionID;
            if(keyBoardActionID==EditorInfo.IME_ACTION_SEARCH)
            {
                doSearch();
            }else if(keyBoardActionID==EditorInfo.IME_ACTION_DONE)
            {
                setSearchAlive(false);
            }
            return false;
        }
    };

    private OnClickListener keyBoardKeyClickListener=null;
    public void setOnKeyBoardKeyClickListener(OnClickListener listener)
    {
        this.keyBoardKeyClickListener=listener;
    }

    // search keyclick
    private void doSearch()
    {
        if(keyBoardKeyClickListener==null)
            return;
        keyBoardKeyClickListener.onClick(null);
    }

    private void isStartFocous(boolean isFoucse)
    {
        if(!isFoucse)
        {
            search_content.setCursorVisible(false);
            search_content.clearFocus();
        }else
        {
            search_content.setCursorVisible(true);
            search_content.requestFocus();
            KeyBoardUtils.openKeybord(search_content,mContext);
        }
    }

    public void setSearchIconVisualble(boolean isVisual)
    {
        if(!isVisual)
        {
            searchIcon.setVisibility(View.GONE);
        }else {
            searchIcon.setVisibility(View.VISIBLE);
        }
    }

    public void setSearchIconBackground(int res)
    {
        if(res<=-1)
            return;
        searchIcon.setBackgroundResource(res);
    }

    public void setCancelButtonBackground(int res)
    {
        delButton.setBackgroundResource(res);
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {


    }

    private OnClickListener doSearchButtonClickListener=null;
    public void setOnDoSearchClickListener(OnClickListener listener)
    {
        this.doSearchButtonClickListener=listener;
    }

    private OnClickListener onDoSearchClick=new OnClickListener() {
        @Override
        public void onClick(View view) {
            search_content.setInputType(InputType.TYPE_NULL);
            clearContent();
            setSearchAlive(false);
            KeyBoardUtils.closeKeybord(search_content,mContext);
            if(doSearchButtonClickListener!=null)
            {
                doSearchButtonClickListener.onClick(view);
            }
        }
    };
}
