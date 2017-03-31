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

public class WeeksWeatherTab extends LinearLayout {
    public WeeksWeatherTab(Context context) {
        this(context, null);
    }

    public WeeksWeatherTab(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeeksWeatherTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setAdapter(List<WeatherDataBean.DailyForecastBean> dailyForecastBean) {
        for(int i = 0; i < dailyForecastBean.size(); i++){
            if(i > 0 && i < dailyForecastBean.size()){
                TextView textView = new TextView(getContext());
                LayoutParams textViewLayoutParams = new LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT);
                textView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white_transparent));
                addView(textView,textViewLayoutParams);
            }
            WeekWeatherTab weekWeatherTab = new WeekWeatherTab(getContext(),dailyForecastBean.get(i));
            LayoutParams layoutParams = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.weight = 1;
            layoutParams.gravity = Gravity.CENTER;
            addView(weekWeatherTab, layoutParams);
        }
    }
}
