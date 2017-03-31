package com.weiruanit.lifepro.module.travel.bean;

/**
 * Created by Administrator on 2016/11/18 0018.
 */

public class TravelControler {
    private  int position;
    private  int tag;


    public TravelControler(int position, int tag) {
        this.position = position;
        this.tag = tag;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }
}
