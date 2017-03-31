package com.weiruanit.lifepro.module.joke;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.weiruanit.lifepro.module.joke.bean.JokeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/14 0014.
 */

public class JokeFragmentViewPageAdapter extends FragmentPagerAdapter {
    List<JokeBean> fragment = new ArrayList<>();
    public JokeFragmentViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragment.get(position).getFragment();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragment.get(position).getTitle();
    }

    @Override
    public int getCount() {
        return fragment.size();
    }
    public void addFragment(List<JokeBean> fr){
        fragment.addAll(fr);
    }
}
