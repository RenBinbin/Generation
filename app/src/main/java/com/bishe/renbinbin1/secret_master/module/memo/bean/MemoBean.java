package com.bishe.renbinbin1.secret_master.module.memo.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by renbinbin1 on 2017/3/15.
 */

public class MemoBean implements Parcelable{
    private String text;
    private String time;
    private int id;

    public MemoBean(){}
    public MemoBean(String text, int id, String time) {
        this.text = text;
        this.id = id;
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    protected MemoBean(Parcel in) {
        text = in.readString();
        time = in.readString();
        id = in.readInt();
    }

    public static final Creator<MemoBean> CREATOR = new Creator<MemoBean>() {
        @Override
        public MemoBean createFromParcel(Parcel in) {
            return new MemoBean(in);
        }

        @Override
        public MemoBean[] newArray(int size) {
            return new MemoBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
        dest.writeString(time);
        dest.writeInt(id);
    }
}
