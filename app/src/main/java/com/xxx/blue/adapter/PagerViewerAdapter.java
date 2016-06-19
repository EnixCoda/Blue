package com.xxx.blue.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Burgess on 2016/6/19.
 */
public class PagerViewerAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> views;

    public PagerViewerAdapter(FragmentManager fm, ArrayList<Fragment> views) {
        super(fm);
        this.views = views;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public Fragment getItem(int position) {
        return views.get(position);
    }
}
