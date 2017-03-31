package com.weiruanit.lifepro.module.joke.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/11/19 0019.
 */

public class TextJokeHeaderBean implements Parcelable{
    private String uri;
    private String name;
    private int sex;

    public TextJokeHeaderBean(String uri, String name, int sex) {
        this.uri = uri;
        this.name = name;
        this.sex = sex;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
