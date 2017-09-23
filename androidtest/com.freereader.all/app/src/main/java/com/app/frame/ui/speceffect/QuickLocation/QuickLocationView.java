package com.app.frame.ui.speceffect.QuickLocation;
import java.util.Collections;
import java.util.List;
import java.util.logging.Handler;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.frame.ui.listview.CustomListView;
import com.freereader.all.R;

public class QuickLocationView<T> extends RelativeLayout {
	private TextView textSelectDialog;
	private SideBar sideQuickBar;
	private CustomListView quickLocListview;
	private Context mContext;
	private CharacterParser characterParser;
	private PinyinComparator pinyinComparator;
	private QuickLocBaseAdapter<T> adapter;

	public QuickLocationView(Context context) {
		super(context);
		this.mContext = context;
		init();
	}
	
	 public QuickLocationView(Context context, AttributeSet attrs) {
	        super(context, attrs);
	        this.mContext=context;
	        init();
	    }

	    public QuickLocationView(Context context, AttributeSet attrs, int defStyleAttr) {
	        super(context, attrs, defStyleAttr);
	        this.mContext=context;
	        init();
	    }
	  
	    SideBar.OnTouchingLetterChangedListener onDefaultTouchListener=new SideBar.OnTouchingLetterChangedListener() {
			@Override
			public void onTouchingLetterChanged(String s) {
				// TODO Auto-generated method stub
				if(adapter==null){
					return;
				}
				int position = adapter.getPositionForSection(s.charAt(0));
				if(position != -1){
					quickLocListview.setSelection(position);
				}
			}
		};

	private void init() {
		LayoutInflater.from(mContext).inflate(R.layout.ui_listview_location_layout, this, true);
		quickLocListview = (CustomListView) findViewById(R.id.quickLocListview);
		quickLocListview.setDefaultDividerConfig();
		quickLocListview.setStateForScrollView(false);
		sideQuickBar = (SideBar) findViewById(R.id.sideQuickBar);
		textSelectDialog = (TextView) findViewById(R.id.textSelectDialog);
		sideQuickBar.setTextView(textSelectDialog);
		characterParser = CharacterParser.getInstance();
		pinyinComparator = new PinyinComparator();
		sideQuickBar.setOnTouchingLetterChangedListener(onDefaultTouchListener);
	}

	class QuickLocBaseAdapter<T> extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int pos) {
			// TODO Auto-generated method stub
			return list.get(pos);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		class ViewHolder {
			TextView tvLetter;
			TextView tvTitle;
		}

		@Override
		public View getView(int position, View convertview, ViewGroup arg2) {
			// TODO Auto-generated method stub
			ViewHolder viewHolder = null;
			if (convertview == null) {
				viewHolder = new ViewHolder();
				convertview = LayoutInflater.from(mContext).inflate(R.layout.contact_item_layout, null);
				viewHolder.tvLetter = (TextView) convertview.findViewById(R.id.catalog);
				convertview.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertview.getTag();
			}
			final SortModel mContent = list.get(position);
			int section = getSectionForPosition(position);
			if(position == getPositionForSection(section)){
				viewHolder.tvLetter.setVisibility(View.VISIBLE);
				viewHolder.tvLetter.setText(mContent.getSortLetters());
			}else{
				viewHolder.tvLetter.setVisibility(View.GONE);
			}
			return quickInterface.convertView(position, convertview);
		}

		public int getSectionForPosition(int position) {
			return list.get(position).getSortLetters().charAt(0);
		}

		public int getPositionForSection(int section) {
			for (int i = 0; i < getCount(); i++) {
				String sortStr = list.get(i).getSortLetters();
				char firstChar = sortStr.toUpperCase().charAt(0);
				if (firstChar == section) {
					return i;
				}
			}
			return -1;
		}
	}

	private SideBar.OnTouchingLetterChangedListener touchListener;
	public void setOnTouchSideBarChangeListener(
			SideBar.OnTouchingLetterChangedListener listener) {
		this.touchListener = listener;
	}

	public void setOnItemListener(OnItemClickListener listener) {
		quickLocListview.setOnItemClickListener(listener);
	}
	
	public interface QuickAdapterConvert {
		View convertView(int pos, View convertview);
	}

	private List<SortModel> list;
	private QuickAdapterConvert quickInterface;
	public void loadQuickLocView(List<T> mylist, QuickAdapterConvert convert) {
		this.list = (List<SortModel>) mylist;
		filledData(list);
		Collections.sort(list, pinyinComparator);
		adapter = new QuickLocBaseAdapter<T>();
		this.quickInterface = convert;
		quickLocListview.setAdapter(adapter);
	}

	public List<T> getSortQuickList(){
		return (List<T>) list;
	}

	private void filledData(List<SortModel>modelList){
		for(int i=0; i<modelList.size(); i++){
			SortModel sortModel =modelList.get(i);
			if(TextUtils.isEmpty(sortModel.getSortName())){
				sortModel.setSortLetters("#");
				continue;
			}
			String pinyin = characterParser.getSelling(sortModel.getSortName());
			String sortString = pinyin.substring(0, 1).toUpperCase();
			if(sortString.matches("[A-Z]")){
				sortModel.setSortLetters(sortString.toUpperCase());
			}else{
				sortModel.setSortLetters("#");
			}
		}
	}	
}