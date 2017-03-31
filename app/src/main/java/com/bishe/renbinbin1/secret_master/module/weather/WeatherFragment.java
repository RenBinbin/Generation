package com.bishe.renbinbin1.secret_master.module.weather;


import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bishe.renbinbin1.secret_master.R;
import com.bishe.renbinbin1.secret_master.base.BaseFragment;
import com.bishe.renbinbin1.secret_master.module.weather.bean.WeatherDataBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherFragment extends BaseFragment {

    @BindView(R.id.weather_txt_icon)
    SimpleDraweeView weatherTxtIcon;
    @BindView(R.id.weather_txt)
    TextView weatherTxt;
    @BindView(R.id.weather_tmp)
    TextView weatherTmp;
    @BindView(R.id.weather_fl)
    TextView weatherFl;
    @BindView(R.id.weather_hum)
    TextView weatherHum;
    @BindView(R.id.weather_dir)
    TextView weatherDir;
    @BindView(R.id.weather_sc)
    TextView weatherSc;
    @BindView(R.id.weather_spd)
    TextView weatherSpd;
    @BindView(R.id.weather_deg)
    TextView weatherDeg;
    @BindView(R.id.weather_more)
    TextView weatherMore;
    @BindView(R.id.weather_threetimeweatherTab)
    ThreeTimeWeatherTab weatherThreetimeweatherTab;
    @BindView(R.id.weather_weeksweathertab)
    WeeksWeatherTab weatherWeeksweathertab;
    @BindView(R.id.weather_weeks)
    LinearLayout weatherWeeks;

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
        ButterKnife.bind(this, view);
    }

    @Override
    protected void getData() {

    }

    private void initWeatherData() {
        //实时天气详情
        weatherTxt.setText(mCondBean.getTxt());
        weatherTmp.setText(mNowBean.getTmp() + "°");
        String condIcon = "http://files.heweather.com/cond_icon/" +
                mNowBean.getCond().getCode() + ".png";

        weatherTxtIcon.setColorFilter(Color.WHITE);
        weatherTxtIcon.setImageURI(Uri.parse(condIcon));

        weatherFl.setText("体感温度" + mNowBean.getFl());
        weatherHum.setText("相对湿度" + mNowBean.getHum() + "%");
        weatherDir.setText(mWindBean.getDir());
        weatherSc.setText(mWindBean.getSc() + "级");
        weatherSpd.setText("风速" + mWindBean.getSpd() + "kmph");
        weatherDeg.setText("风向" + mWindBean.getDeg() + "度");

        //三小时天气
        if(mHourlyForecastBean != null){
            weatherThreetimeweatherTab.setAdapter(mHourlyForecastBean,condIcon);
        }

        //七天天气
        if (mDailyForecastBean != null){
            weatherWeeksweathertab.setAdapter(mDailyForecastBean);
        }
    }

    private void getWeatherDataBean(WeatherDataBean weatherDataBean, int state) {
        if (state == 1) {
            mHourlyForecastBean.clear();
            mDailyForecastBean.clear();
            weatherWeeksweathertab.removeAllViews();
            weatherThreetimeweatherTab.removeAllViews();
        }
        mNowBean = weatherDataBean.getNow();
        mHourlyForecastBean.addAll(weatherDataBean.getHourly_forecast());
        mDailyForecastBean.addAll(weatherDataBean.getDaily_forecast());
        mCondBean = mNowBean.getCond();
        mWindBean = mNowBean.getWind();
        initWeatherData();

    }

    public void setWeatherData(WeatherDataBean weatherDataBean, int state) {
        mWeatherDataBean = weatherDataBean;
        getWeatherDataBean(mWeatherDataBean, state);
    }

}
