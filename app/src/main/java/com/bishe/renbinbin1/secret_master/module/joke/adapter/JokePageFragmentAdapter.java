package com.bishe.renbinbin1.secret_master.module.joke.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bishe.renbinbin1.secret_master.module.joke.bean.JokeTabBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by renbinbin1 on 2017/3/15.
 */

public class JokePageFragmentAdapter extends FragmentPagerAdapter{
    private List<JokeTabBean> mFragment=new ArrayList<>();
    public JokePageFragmentAdapter(FragmentManager fm) {
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
    public void addFr(List<JokeTabBean> fragment){
       mFragment.addAll(fragment);
    }
}
