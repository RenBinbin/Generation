package com.weiruanit.lifepro.module.joke.bean;

/**
 * Created by Administrator on 2016/11/18 0018.
 */

public class ImageJokeControl {
    public ImageJokeControl(int position, int tag) {
        this.position = position;
        this.tag = tag;
    }

    private int position;
    private int tag;

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
