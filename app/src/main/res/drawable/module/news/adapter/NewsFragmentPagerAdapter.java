package com.weiruanit.lifepro.module.news.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.weiruanit.lifepro.module.news.bean.NewsTabBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/14 0014.
 */

public class NewsFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<NewsTabBean> mFragments = new ArrayList<>();

    public NewsFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragments.get(position).getTitle();
    }

    public void addFragment(List<NewsTabBean> fragments){
        mFragments.addAll(fragments);
    }
}
