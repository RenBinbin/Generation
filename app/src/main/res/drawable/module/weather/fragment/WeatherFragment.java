package com.weiruanit.lifepro.module.weather.fragment;

import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.weiruanit.lifepro.R;
import com.weiruanit.lifepro.base.BaseFragment;
import com.weiruanit.lifepro.module.weather.bean.WeatherDataBean;
import com.weiruanit.lifepro.module.weather.widget.ThreeTimeWeatherTab;
import com.weiruanit.lifepro.module.weather.widget.WeeksWeatherTab;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/17 0017.
 */

public class WeatherFragment extends BaseFragment {

    @BindView(R.id.weather_txt)
    TextView mTxtTextView;
    @BindView(R.id.weather_tmp)
    TextView mTmpTextView;
    @BindView(R.id.weather_txt_icon)
    SimpleDraweeView mTxtIconSimpleDraweeView;
    @BindView(R.id.weather_fl)
    TextView mFlTextView;
    @BindView(R.id.weather_hum)
    TextView mHumTextView;
    @BindView(R.id.weather_dir)
    TextView mDirTextView;
    @BindView(R.id.weather_sc)
    TextView mScTextView;
    @BindView(R.id.weather_spd)
    TextView mSpdTextView;
    @BindView(R.id.weather_deg)
    TextView mDegTextView;
    @BindView(R.id.weather_more)
    TextView mMoreTextView;
    @BindView(R.id.weather_threetimeweatherTab)
    ThreeTimeWeatherTab mThreeTimeWeatherTab;
    @BindView(R.id.weather_weeksweathertab)
    WeeksWeatherTab mWeatherWeeksWeatherTab;


    private WeatherDataBean mWeatherDataBean;
    private WeatherDataBean.NowBean mNowBean;
    private WeatherDataBean.NowBean.CondBean mCondBean;
    private WeatherDataBean.NowBean.WindBean mWindBean;
    private List<WeatherDataBean.HourlyForecastBean> mHourlyForecastBean = new ArrayList<>();
    private List<WeatherDataBean.DailyForecastBean> mDailyForecastBean = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_weather;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this,view);
        mMoreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    @Override
    protected void getData() {
    }

    private void initWeatherData(){
        /**
         * 实时天气详情
         */
        mTxtTextView.setText(mCondBean.getTxt());
        mTmpTextView.setText(mNowBean.getTmp() + "°");
        String condIcon = "http://files.heweather.com/cond_icon/" +
                mNowBean.getCond().getCode() + ".png";

        mTxtIconSimpleDraweeView.setColorFilter(Color.WHITE);
        mTxtIconSimpleDraweeView.setImageURI(Uri.parse(condIcon));

        mFlTextView.setText("体感温度" + mNowBean.getFl());
        mHumTextView.setText("相对湿度" + mNowBean.getHum() + "%");
        mDirTextView.setText(mWindBean.getDir());
        mScTextView.setText(mWindBean.getSc() + "级");
        mSpdTextView.setText("风速" + mWindBean.getSpd() + "kmph");
        mDegTextView.setText("风向" + mWindBean.getDeg() + "度");

        //三小时天气
        if(mHourlyForecastBean != null){
                mThreeTimeWeatherTab.setAdapter(mHourlyForecastBean,condIcon);
        }

        //七天天气
        if (mDailyForecastBean != null){
            mWeatherWeeksWeatherTab.setAdapter(mDailyForecastBean);
        }
    }

    private void getWeatherDataBean(WeatherDataBean weatherDataBean,int state){
        if(state == 1){
            mHourlyForecastBean.clear();
            mDailyForecastBean.clear();
            mWeatherWeeksWeatherTab.removeAllViews();
            mThreeTimeWeatherTab.removeAllViews();
        }
        mNowBean = weatherDataBean.getNow();
        mHourlyForecastBean.addAll(weatherDataBean.getHourly_forecast());
        mDailyForecastBean.addAll(weatherDataBean.getDaily_forecast());
        mCondBean = mNowBean.getCond();
        mWindBean = mNowBean.getWind();
        initWeatherData();

    }

    public void setWeatherData(WeatherDataBean weatherDataBean,int state){
        mWeatherDataBean = weatherDataBean;
        getWeatherDataBean(mWeatherDataBean,state);
    }

}
