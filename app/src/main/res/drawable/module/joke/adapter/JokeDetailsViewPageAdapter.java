package com.weiruanit.lifepro.module.joke.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.weiruanit.lifepro.module.joke.bean.ImageJokeDetailsVpBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/14 0014.
 */

public class JokeDetailsViewPageAdapter extends FragmentPagerAdapter {
    List<ImageJokeDetailsVpBean> fragment2 = new ArrayList<>();
    public JokeDetailsViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragment2.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return fragment2.size();
    }
    public void addFragment(List<ImageJokeDetailsVpBean> fr){
        this.fragment2 = fr;
    }
}
