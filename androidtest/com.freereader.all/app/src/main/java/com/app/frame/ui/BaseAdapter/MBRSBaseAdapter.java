package com.app.frame.ui.BaseAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class MBRSBaseAdapter<T> extends BaseAdapter {

    private Context mContext;
    private List<T> mbrsBaseAdapterList;
    private int resID;
    private LayoutInflater mInflater;
    private int selecItemPos=-1;

    public MBRSBaseAdapter(List<T> listItem, Context con, int resID) {
        this.mContext = con;
        this.mInflater = LayoutInflater.from(mContext);
        this.mbrsBaseAdapterList = listItem;
        this.resID = resID;
    }

    @Override
    public int getCount() {
        return mbrsBaseAdapterList.size();
    }

    @Override
    public T getItem(int i) {
        return mbrsBaseAdapterList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void setSelectPos(int pos){
        this.selecItemPos=pos;
    }

    public int getSelectPos(){
        return selecItemPos;
    }
    public abstract void convertView(MBRSViewHolder holder, T item,int position);

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MBRSViewHolder viewHolder = getViewHolder(position, convertView,
                parent);
        convertView(viewHolder, getItem(position),position);
        return viewHolder.getConvertView();
    }

    private MBRSViewHolder getViewHolder(int position, View convertView,
                                         ViewGroup parent) {
        return MBRSViewHolder.get(mContext, convertView, parent, resID,
                position);
    }
}
