package com.weiruanit.lifepro.module.news.bean;

import android.support.v4.app.Fragment;

/**
 * Created by Administrator on 2016/11/14 0014.
 */

public class NewsTabBean {
    private String title;
    private Fragment fragment;

    public NewsTabBean(String title, Fragment fragment) {
        this.title = title;
        this.fragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
