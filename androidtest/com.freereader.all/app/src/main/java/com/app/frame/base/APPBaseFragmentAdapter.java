package com.app.frame.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kanxue on 2016/1/13.
 */

public class APPBaseFragmentAdapter extends FragmentPagerAdapter
{

    private List<Fragment> fragmentList;
    private FragmentManager fm;
    private List<String> titleList;
    public APPBaseFragmentAdapter(FragmentManager manager, List<Fragment> list)
    {
        super(manager);
        fragmentList=new ArrayList<Fragment>();
        this.fragmentList=list;
        this.fm=manager;
    }

    public APPBaseFragmentAdapter(FragmentManager manager, List<Fragment> list,
                                  List<String> titleList)
    {
        super(manager);
        fragmentList=new ArrayList<Fragment>();
        this.fragmentList=list;
        this.fm=manager;
        this.titleList=titleList;
    }

    /**从新设置fragment集合
     * @param fragments
     */
    public void setFragments(List<Fragment> fragments)
    {
        if (this.fragmentList != null)
        {
            FragmentTransaction ft = fm.beginTransaction();
            for (Fragment f : this.fragmentList)
            {
                ft.remove(f);
            }
            ft.commit();
            ft = null;
            fm.executePendingTransactions();
        }
        if(fragments!=null)
            this.fragmentList = fragments;
        notifyDataSetChanged();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public Fragment getItem(int arg0) {
        return fragmentList.get(arg0);
    }

    private boolean canDestroyState=false;

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    public void setCanDestroyFragmentPage(boolean canDestroy){
        this.canDestroyState=canDestroy;
    }

    public void clearFragment()
    {
        setFragments(null);
    }

    @Override
    public int getCount()
    {
        return fragmentList.size();
    }

    // from this we can display the tab name
    @Override
    public CharSequence getPageTitle(int position)
    {
        return titleList.get(position % titleList.size());
    }
}

