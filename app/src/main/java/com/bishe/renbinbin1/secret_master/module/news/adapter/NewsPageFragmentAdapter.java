package com.bishe.renbinbin1.secret_master.module.news.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bishe.renbinbin1.secret_master.module.news.bean.NewsTabBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by renbinbin1 on 2017/3/15.
 */

public class NewsPageFragmentAdapter extends FragmentPagerAdapter{
    private List<NewsTabBean> mFragment=new ArrayList<>();
    public NewsPageFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragment.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return mFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragment.get(position).getTitle();
    }
    public void addFr(List<NewsTabBean> fragment){
       mFragment.addAll(fragment);
    }
}
