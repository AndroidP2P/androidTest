package com.app.frame.ui.BaseAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by kanxue on 2016/4/15.
 */
public class MBRSViewHolder {
    private final SparseArray<View> mViews;
    private View mConvertView;
    private MBRSViewHolder(Context context, ViewGroup parent, int layoutId,
                           int position) {
        this.mViews = new SparseArray<>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        //setTag
        mConvertView.setTag(this);
    }


    public MBRSViewHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public MBRSViewHolder setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public MBRSViewHolder setImageBitmap(int viewId, Bitmap bm) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }

    public static MBRSViewHolder get(Context context, View convertView,
                                     ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new MBRSViewHolder(context, parent, layoutId, position);
        }
        return (MBRSViewHolder) convertView.getTag();
    }

    public <T extends View> T getView(int viewId) {

        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }

}
