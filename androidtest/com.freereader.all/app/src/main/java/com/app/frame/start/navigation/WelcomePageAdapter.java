package com.app.frame.start.navigation;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by kanxue on 2016/1/16.
 */
public class WelcomePageAdapter extends PagerAdapter
{

    private List<ImageView> list;
    public WelcomePageAdapter(List<ImageView> imageViewList)
    {
       this.list=imageViewList;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        container.addView(list.get(position));
        return list.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView(list.get(position));
    }

    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {

        return view==object;
    }
}
