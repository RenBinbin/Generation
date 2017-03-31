package com.weiruanit.lifepro.module.joke.bean;

import android.support.v4.app.Fragment;

/**
 * Created by Administrator on 2016/10/25 0025.
 */

public class ImageJokeDetailsVpBean {
    private String url;
    private String title;
    private Fragment fragment;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public ImageJokeDetailsVpBean(String url, String title, Fragment fragment) {
        this.url = url;
        this.title = title;
        this.fragment = fragment;
    }

    @Override
    public String toString() {
        return "BannerVpBean{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", fragment=" + fragment +
                '}';
    }
}
