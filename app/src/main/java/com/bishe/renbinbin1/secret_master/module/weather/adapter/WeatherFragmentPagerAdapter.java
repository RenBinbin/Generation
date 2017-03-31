package com.bishe.renbinbin1.secret_master.module.weather.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bishe.renbinbin1.secret_master.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/17 0017.
 */

public class WeatherFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> mFragments = new ArrayList<>();

    public WeatherFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }


    public void addFragment(List<BaseFragment> fragments){
        mFragments.addAll(fragments);
    }
}
