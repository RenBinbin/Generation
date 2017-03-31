package com.weiruanit.lifepro.module.weather;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.weiruanit.lifepro.MainActivity;
import com.weiruanit.lifepro.R;
import com.weiruanit.lifepro.base.BaseBackActivity;
import com.weiruanit.lifepro.base.BaseFragment;
import com.weiruanit.lifepro.module.ticket.city.CityActivity;
import com.weiruanit.lifepro.module.weather.adapter.WeatherFragmentPagerAdapter;
import com.weiruanit.lifepro.module.weather.bean.WeatherDataBean;
import com.weiruanit.lifepro.module.weather.fragment.WeatherDetailFragment;
import com.weiruanit.lifepro.module.weather.fragment.WeatherFragment;
import com.weiruanit.lifepro.module.weather.util.ScreenUtils;
import com.weiruanit.lifepro.utils.PreferencesUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/11/15 0015.
 */

public class WeatherActivity extends BaseBackActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_CITY = 10;
    @BindView(R.id.weather_background)
    RelativeLayout mWeatherRelativeLayout;
    @BindView(R.id.weather_back)
    ImageView mBackImageView;
    @BindView(R.id.city)
    LinearLayout mCityLinearLayout;
    @BindView(R.id.weather_city)
    TextView mCityTextView;
    @BindView(R.id.weather_city_area)
    TextView mAreaCityTextView;
    @BindView(R.id.weather_share)
    ImageView mShareImageView;
    @BindView(R.id.weather_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.background_qing)
    LinearLayout mQingLinearLayout;
    @BindView(R.id.city_click)
    ImageView mCityClickImageView;

    private WeatherFragment mWeatherFragment;
    private WeatherDetailFragment mWeatherDetailFragment;
    private WeatherDataBean mWeatherDataBean;
    private String mCity;
    private int state;
    private String code;

    @Override
    protected int getLayoutId() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        return R.layout.activity_weather;
    }

    @Override
    public void initData() {

        ButterKnife.bind(this);
        List<BaseFragment> baseFragments = new ArrayList<>();
        mWeatherFragment = new WeatherFragment();
        baseFragments.add(mWeatherFragment);
        mWeatherDetailFragment = new WeatherDetailFragment();
        baseFragments.add(mWeatherDetailFragment);
        final WeatherFragmentPagerAdapter weatherFragmentPagerAdapter = new WeatherFragmentPagerAdapter(
                getSupportFragmentManager());
        weatherFragmentPagerAdapter.addFragment(baseFragments);
        mViewPager.setAdapter(weatherFragmentPagerAdapter);

        mBackImageView.setOnClickListener(this);
        mCityClickImageView.setOnClickListener(this);
        mCityTextView.setOnClickListener(this);
        mShareImageView.setOnClickListener(this);



        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0){
                    mWeatherRelativeLayout.setBackgroundResource(R.mipmap.weather_background);
                    mWeatherDetailFragment.setSign(0);
                }else if(position == 1){
                    if(mWeatherDataBean != null){
                        mWeatherDetailFragment.setBackgrouand(mWeatherDataBean.
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
                mCity = "南京";
            }
        }


        OkGo.get("http://apis.baidu.com/heweather/weather/free")
                .tag(this)
                .headers("apikey","abcfe469f2ede2b495055162e97d8b82")
                .params("city",mCity)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {

                        String weatherRequestJson = s.substring(s.indexOf('[') + 1,s.lastIndexOf(']'));
                        Log.d("Weather : ", weatherRequestJson);
                        Gson gson = new Gson();
                        try{
                            mWeatherDataBean = gson.fromJson(weatherRequestJson,
                                    WeatherDataBean.class);
                            if(mWeatherDataBean != null){
                                mWeatherFragment.setWeatherData(mWeatherDataBean,state);
                                mWeatherDetailFragment.setWeatherData(mWeatherDataBean,state);
                                initWeatherData(mWeatherDataBean);

                            }
                        }catch (Exception e){

                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }

    public void setBackground(int drawable){
        mWeatherRelativeLayout.setBackgroundResource(drawable);

    }

    public void setBackgroundColor(int color){
        mQingLinearLayout.setBackgroundColor(color);
    }


    private void initWeatherData(WeatherDataBean weatherDataBean){
        String city = weatherDataBean.getBasic().getCity();
        mCityTextView.setText(city);
        backWeatherToActivity();
    }

    private void backWeatherToActivity(){

        WeatherDataBean.NowBean nowBean = mWeatherDataBean.getNow();
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


        //点击城市刷新游记界面
        if (requestCode == REQUEST_CODE_CITY && resultCode == 20) {
            String cityName = data.getStringExtra("fromcityname");
            if (cityName != null) {
                mCityTextView.setText(cityName);
                mCity = cityName;
                mWeatherDataBean = null;
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
            case R.id.weather_share:
                new ShareAction(this).setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                        .addButton("umeng_sharebutton_custom", "umeng_sharebutton_custom", "info_icon_1", "info_icon_1")
                        .setShareboardclickCallback(new ShareBoardlistener() {
                            @Override
                            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                                if (snsPlatform.mShowWord.equals("umeng_sharebutton_custom")) {
                                    Toast.makeText(WeatherActivity.this, "自定义按钮", Toast.LENGTH_LONG).show();
                                } else {
//                                    Bitmap  bitmap = ScreenshotTool.captureScreen(WeatherActivity.this);
                                    Bitmap  bitmap = ScreenUtils.snapShotWithoutStatusBar(WeatherActivity.this);

                                    UMImage umImage = new UMImage(WeatherActivity.this,bitmap);
                                    new ShareAction(WeatherActivity.this)
                                            .withText("自定义分享内容xxxxx  --来自LifePro分享")
//                                            .withTargetUrl("http://www.baidu.com")
                                            .withMedia(umImage)
                                            .setPlatform(share_media)
                                            .setCallback(new UMShareListener() {
                                                @Override
                                                public void onResult(SHARE_MEDIA share_media) {
                                                    Toast.makeText(WeatherActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
                                                }

                                                @Override
                                                public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                                                    Toast.makeText(WeatherActivity.this, "分享失败", Toast.LENGTH_SHORT).show();
                                                }

                                                @Override
                                                public void onCancel(SHARE_MEDIA share_media) {
                                                    Toast.makeText(WeatherActivity.this, "取消分享", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .share();

                                }
                            }
                        }).open();

                break;
            case R.id.city_click:
            case R.id.weather_city:
                Intent intent = new Intent(this, CityActivity.class);
                intent.putExtra("state",20);
                startActivityForResult(intent,10);
                break;
            default:
                break;
        }
    }
}
