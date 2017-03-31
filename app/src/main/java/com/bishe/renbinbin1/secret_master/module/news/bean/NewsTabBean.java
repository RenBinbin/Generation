package com.bishe.renbinbin1.secret_master.module.news.bean;

import android.support.v4.app.Fragment;

/**
 * Created by renbinbin1 on 2017/3/15.
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
