package com.bishe.renbinbin1.secret_master.module.weather;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bishe.renbinbin1.secret_master.R;
import com.bishe.renbinbin1.secret_master.module.weather.bean.WeatherDataBean;

import java.util.List;

/**
 * Created by renbinbin1 on 2017/3/29.
 */

public class ThreeTimeWeatherTab extends LinearLayout {
    public ThreeTimeWeatherTab(Context context) {
        this(context, null);
    }

    public ThreeTimeWeatherTab(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThreeTimeWeatherTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setAdapter(List<WeatherDataBean.HourlyForecastBean> hourlyForecastBeenList, String condIcon){

        int count = 0;
        if(hourlyForecastBeenList.size() > 3){
            count = 3;
        }else {
            count = hourlyForecastBeenList.size();
        }

        for(int i = 0; i < count; i++){
            if(i > 0 && i < count){
                TextView textView = new TextView(getContext());
                LayoutParams textViewLayoutParams = new LayoutParams(1, ViewGroup.
                        LayoutParams.MATCH_PARENT);
                textViewLayoutParams.leftMargin = 35;
                textViewLayoutParams.rightMargin = 35;
                textView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                addView(textView,textViewLayoutParams);
            }
            TimeWeatherTab timeWeatherTab = new TimeWeatherTab(getContext(),
                    hourlyForecastBeenList.get(i),condIcon);
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER;
            addView(timeWeatherTab, layoutParams);
        }
    }
}
