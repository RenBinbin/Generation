package com.weiruanit.lifepro.module.weather.widget;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.weiruanit.lifepro.R;
import com.weiruanit.lifepro.module.weather.bean.WeatherDataBean;
import com.weiruanit.lifepro.module.weather.util.DayForWeek;

/**
 * Created by Administrator on 2016/11/16 0016.
 */

public class WeekWeatherTab extends LinearLayout {

    private TextView mWeek;
    private TextView mDate;
    private SimpleDraweeView mWeatherIcon;
//    private ImageView mWeatherIcon;
    private TextView mTemperature;
    private WeatherDataBean.DailyForecastBean mDailyForecastBean;


    public WeekWeatherTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public WeekWeatherTab(Context context, WeatherDataBean.DailyForecastBean dailyForecastBaen) {
        super(context);
        setOrientation(HORIZONTAL);
        this.mDailyForecastBean = dailyForecastBaen;
        init();
    }

    public WeekWeatherTab(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void init(){
        LinearLayout linearLayout = new LinearLayout(getContext());
        LayoutParams lllayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        linearLayout.setLayoutParams(lllayoutParams);
        linearLayout.setOrientation(VERTICAL);
        linearLayout.setGravity(Gravity.CENTER);


        //设置星期
        LayoutParams mWeekLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mWeek = new TextView(getContext());
        mWeek.setLayoutParams(mWeekLayoutParams);
        mWeek.setTextColor(ContextCompat.getColor(getContext(),R.color.white));
        try {
            String week = DayForWeek.dayForWeek(mDailyForecastBean.getDate());
            mWeek.setText(week);
        } catch (Exception e) {
            e.printStackTrace();
        }

        linearLayout.addView(mWeek);

        //设置日期
        LayoutParams mDateLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mDateLayoutParams.topMargin = 8;
        mDate = new TextView(getContext());
        mDate.setLayoutParams(mDateLayoutParams);
        mDate.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        String date = mDailyForecastBean.getDate().substring(5);
        mDate.setText(date);
        linearLayout.addView(mDate);

        //设置天气icon
        int imageSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30,
                getContext().getResources().getDisplayMetrics());
        LayoutParams mWeatherIconLayoutParams = new LayoutParams(imageSize,imageSize);
        mWeatherIconLayoutParams.topMargin = 30;
        mWeatherIcon = new SimpleDraweeView(getContext());
        mWeatherIcon.setLayoutParams(mWeatherIconLayoutParams);
        String condIcon = "http://files.heweather.com/cond_icon/" +
                mDailyForecastBean.getCond().getCode_d() + ".png";
        mWeatherIcon.setImageURI(Uri.parse(condIcon));
        mWeatherIcon.setColorFilter(Color.WHITE);
        linearLayout.addView(mWeatherIcon);

        //设置温度
        LayoutParams mTemperatureLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mTemperatureLayoutParams.topMargin = 20;
        mTemperature = new TextView(getContext());
        mTemperature.setLayoutParams(mTemperatureLayoutParams);
        mTemperature.setTextColor(ContextCompat.getColor(getContext(),R.color.white));
        mTemperature.setTextSize(20);
        mTemperature.setText(mDailyForecastBean.getTmp().getMax() + "°");
        linearLayout.addView(mTemperature);


        addView(linearLayout);
    }
}
