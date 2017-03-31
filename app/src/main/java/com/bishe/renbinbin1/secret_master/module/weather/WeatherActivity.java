package com.bishe.renbinbin1.secret_master.module.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bishe.renbinbin1.secret_master.MainActivity;
import com.bishe.renbinbin1.secret_master.R;
import com.bishe.renbinbin1.secret_master.base.BaseActivity;
import com.bishe.renbinbin1.secret_master.base.BaseFragment;
import com.bishe.renbinbin1.secret_master.module.weather.adapter.WeatherFragmentPagerAdapter;
import com.bishe.renbinbin1.secret_master.module.weather.bean.WeatherDataBean;
import com.bishe.renbinbin1.secret_master.module.weather.city.CityActivity;
import com.bishe.renbinbin1.secret_master.uilts.PreferencesUtils;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class WeatherActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.weather_back)
    ImageButton weatherBack;
    @BindView(R.id.city_click)
    ImageView cityClick;
    @BindView(R.id.weather_city)
    TextView weatherCity;
    @BindView(R.id.city)
    LinearLayout city;
    @BindView(R.id.weather_share)
    ImageView weatherShare;
    @BindView(R.id.weather_viewpager)
    ViewPager weatherViewpager;
    @BindView(R.id.background_qing)
    LinearLayout backgroundQing;
    @BindView(R.id.weather_background)
    RelativeLayout weatherBackground;

    private WeatherFragment mWeatherFragment;
    private int state;
    private String mCity;
    private String code;
    private WeatherDataBean weatherDataBean;
    private WeatherDetailFragment mWeatherDetailFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_weather;
    }

    @Override
    public void initData() {
        ButterKnife.bind(this);
        List<BaseFragment> baseFragments = new ArrayList<>();
        mWeatherFragment=new WeatherFragment();
        mWeatherDetailFragment=new WeatherDetailFragment();
        baseFragments.add(mWeatherFragment);
        baseFragments.add(mWeatherDetailFragment);
        WeatherFragmentPagerAdapter adapter=new WeatherFragmentPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(baseFragments);
        weatherViewpager.setAdapter(adapter);
        weatherBack.setOnClickListener(this);
        weatherCity.setOnClickListener(this);
        cityClick.setOnClickListener(this);

        weatherViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0){
                    weatherBackground.setBackgroundResource(R.mipmap.weather_background);
                    mWeatherDetailFragment.setSign(0);
                }else if(position == 1){
                    if(weatherDataBean != null){
                        mWeatherDetailFragment.setBackgrouand(weatherDataBean.
                                getNow().getCond().getCode());
                        mWeatherDetailFragment.setSign(1);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected void getData() {
        if (state == 0){
            mCity = PreferencesUtils.getString(this,"city");
            if (TextUtils.isEmpty(mCity)){
                mCity = "苏州";
            }
        }
        OkGo.get("https://free-api.heweather.com/v5/weather?key=b478f335a5114ba3b6013f6dd92bd422")
                .tag(this)
                .headers("key","b478f335a5114ba3b6013f6dd92bd422")
                .params("city",mCity)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        String weatherRequestJson = s.substring(s.indexOf('[') + 1,s.lastIndexOf(']'));
                        Gson gson=new Gson();
                        weatherDataBean = gson.fromJson(weatherRequestJson, WeatherDataBean.class);
                        if(weatherDataBean!=null){
                            mWeatherFragment.setWeatherData(weatherDataBean,state);
                            mWeatherDetailFragment.setWeatherData(weatherDataBean,state);
                            initWeatherData(weatherDataBean);
                        }
                    }
                });
    }

    public void setBackground(int drawable){
        weatherBackground.setBackgroundResource(drawable);

    }

    public void setBackgroundColor(int color){
        backgroundQing.setBackgroundColor(color);
    }

    private void initWeatherData(WeatherDataBean weatherDataBean) {
        String city = weatherDataBean.getBasic().getCity();
        weatherCity.setText(city);
        backWeatherToActivity();
    }

    private void backWeatherToActivity() {
        WeatherDataBean.NowBean nowBean = weatherDataBean.getNow();
        Intent intent = new Intent();
        intent = intent.setClass(this, MainActivity.class);
        Bundle bundle = new Bundle();
        if (state == 0){
            code = nowBean.getCond().getCode();
        }
        bundle.putString("code",code);
        intent.putExtras(bundle);
        setResult(20, intent);   //RESULT_OK是返回状态码
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 20) {
            String cityName = data.getStringExtra("fromcityname");
            if (cityName != null) {
                weatherCity.setText(cityName);
                mCity = cityName;
                weatherDataBean = null;
                state = 1;
                getData();

            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.weather_back:
                finish();
                break;
            case R.id.weather_city:
                Intent intent=new Intent(this, CityActivity.class);
                intent.putExtra("state",20);
                startActivityForResult(intent,10);
                break;
        }
    }


}
