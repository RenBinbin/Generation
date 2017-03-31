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

/**
 * Created by Administrator on 2016/11/17 0017.
 */

public class TimeWeatherTab extends LinearLayout {

    private TextView mTime;
    private SimpleDraweeView mIcon;
    private TextView mTemperature;
    private String condIcon;
    private WeatherDataBean.HourlyForecastBean mHourlyForecastBean;

    public TimeWeatherTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TimeWeatherTab(Context context, WeatherDataBean.HourlyForecastBean hourlyForecastBean,String condIcon) {
        super(context);
        setOrientation(HORIZONTAL);
        this.mHourlyForecastBean = hourlyForecastBean;
        this.condIcon = condIcon;
        init();
    }

    public TimeWeatherTab(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void init(){
        LinearLayout linearLayout = new LinearLayout(getContext());
        LayoutParams lllayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        linearLayout.setLayoutParams(lllayoutParams);
        linearLayout.setOrientation(VERTICAL);
        linearLayout.setGravity(Gravity.CENTER);


        /**
         * 设置时间
         */
        LayoutParams mTimeLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mTime = new TextView(getContext());
        mTime.setLayoutParams(mTimeLayoutParams);
        mTime.setTextSize(20);
        mTime.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        String date = mHourlyForecastBean.getDate().substring(11);
        mTime.setText(date);
        linearLayout.addView(mTime);

        /**
         *  设置天气icon
         */
        int imageSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30,
                getContext().getResources().getDisplayMetrics());
        LayoutParams mIconLayoutParams = new LayoutParams(imageSize,imageSize);
        mIconLayoutParams.topMargin = 20;
        mIcon = new SimpleDraweeView(getContext());
        mIcon.setLayoutParams(mIconLayoutParams);
        mIcon.setColorFilter(Color.WHITE);
        mIcon.setImageURI(Uri.parse(condIcon));
        linearLayout.addView(mIcon);


        /**
         * 设置温度
         */
        LayoutParams mTemperatureLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mTemperatureLayoutParams.topMargin = 10;
        mTemperature = new TextView(getContext());
        mTemperature.setLayoutParams(mTemperatureLayoutParams);
        mTemperature.setTextSize(30);
        mTemperature.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        mTemperature.setText(mHourlyForecastBean.getTmp() + "°");
        linearLayout.addView(mTemperature);


        addView(linearLayout);
    }
}
