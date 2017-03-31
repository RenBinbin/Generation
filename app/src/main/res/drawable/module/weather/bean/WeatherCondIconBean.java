package com.weiruanit.lifepro.module.weather.bean;

/**
 * Created by Administrator on 2016/11/18 0018.
 */

public class WeatherCondIconBean {

    private int id;
    private String txt;
    private String condIcon;

    public WeatherCondIconBean(int id, String txt, String condIcon) {
        this.id = id;
        this.txt = txt;
        this.condIcon = condIcon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getCondIcon() {
        return condIcon;
    }

    public void setCondIcon(String condIcon) {
        this.condIcon = condIcon;
    }
}
