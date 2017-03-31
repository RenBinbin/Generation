package com.weiruanit.lifepro.module.weather.bean;

/**
 * Created by Administrator on 2016/11/17 0017.
 */

public class ThreeTimeWeatherDataBean {
    private String date;
    private String temperature;

    public ThreeTimeWeatherDataBean(String date, String temperature) {
        this.date = date;
        this.temperature = temperature;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
