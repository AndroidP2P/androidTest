package com.app.frame.base;

import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.freereader.all.R;

/**
 * Created by kanxue on 2016/1/25.
 */
public class ViewHolder
{
    // <T extends View> any class extends View
    // <T super son> any class More than View example person.

    public static SparseArray<View> viewHolder;
    private static View convertView;
    public ViewHolder(View convertview)
    {
        this.convertView=convertview;
        viewHolder=getViewHolder(convertview);
    }

    // getView
    public <T extends View> T getHolderView(View convertView, int id)
    {
        if (viewHolder == null)
        {
            viewHolder = getViewHolder(convertView);
        }
        View childView = viewHolder.get(id);
        if (childView == null)
        {
            childView = convertView.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }

    // getHolder
    public SparseArray<View>  getViewHolder(View convertView)
    {
        viewHolder = (SparseArray<View>) convertView.getTag();
        if (viewHolder == null)
        {
            viewHolder = new SparseArray<View>();
            convertView.setTag(viewHolder);
        }
        return viewHolder;
    }

    public void setText(String textCharString,int id)
    {
        TextView tempText=getHolderView(convertView,id);
        tempText.setText(textCharString);
    }

    public void setBackgroundResource(int textCharString,int id)
    {
        ImageView view=getHolderView(convertView,id);
        view.setBackgroundResource(id);
    }
}
