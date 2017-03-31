package com.weiruanit.lifepro.module.weather.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.weiruanit.lifepro.R;
import com.weiruanit.lifepro.base.BaseFragment;
import com.weiruanit.lifepro.module.weather.WeatherActivity;
import com.weiruanit.lifepro.module.weather.bean.WeatherDataBean;
import com.weiruanit.lifepro.module.weather.widget.MovingPictureView;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/11/17 0017.
 */

public class WeatherDetailFragment extends BaseFragment {


    @BindView(R.id.today_yubao)
    RelativeLayout today_yubao;
    public static ForecastHandler forecastHandler ;
    public static Timer weather_timer;
    private Day_Lei_Handler day_lei_handler;
    private Night_Qing_Handler night_qing_handler;
    private Day_Rain_Handler Day_Rain_Handler;
    private Day_Snow_Handler Day_Snow_Handler;
    private Day_RainSnow_Handler Day_RainSnow_Handler;
    private Day_Wu_Handler Day_Wu_Handler;
    private MovingPictureView
            w1_move1,w1_move2,w1_move3,w1_move4,w1_move5,
            w2_move1,w2_move2,w2_move3,w2_move4,w2_move5,
            w3_move1,w3_move2,w3_move3,w3_move4,w3_move5,
            w4_move1,w4_move2,w4_move3,w4_move4,w4_move5,
            w5_move1,w5_move2,w5_move3,w5_move4,w5_move5,
            w6_move1,w6_move2,w6_move3,w6_move4,w6_move5,
            w7_move1,w7_move2,w7_move3,w7_move4,w7_move5;
    private ImageView m1,m2,m3,m4,m5,m6,m7,m8,m9,m10;
    public static int imgIndex;

    private RelativeLayout weather_move1,weather_move2,weather_move3,weather_move4,weather_move5
            ,weather_move6,weather_move7,weather_move8,weather_move9,weather_move10;

    @BindView(R.id.weather_qing)
    RelativeLayout  weather_qing;
    @BindView(R.id.weather_day_duoyun)
    RelativeLayout weather_day_duoyun;
    @BindView(R.id.weather_day_yin)
    RelativeLayout weather_day_yin;
    @BindView(R.id.weather_night_yin)
    RelativeLayout weather_night_yin;
    @BindView(R.id.weather_wu)
    RelativeLayout weather_wu;
    @BindView(R.id.weather_mai)
    RelativeLayout weather_mai;
    @BindView(R.id.weather_sha)
    RelativeLayout weather_sha;

    private int nowindex=12;

    @BindView(R.id.weather_tmp)
    TextView mTmpTextView;
    @BindView(R.id.weather_txt_icon)
    SimpleDraweeView mTxtIconSimpleDraweeView;
    @BindView(R.id.weather_txt)
    TextView mTxtTextView;
    @BindView(R.id.weather_date)
    TextView mDateTextView;
    @BindView(R.id.weather_hum)
    TextView mHumTextView;
    @BindView(R.id.weather_pres)
    TextView mPresTextView;
    @BindView(R.id.weather_sc)
    TextView mScTextView;
    @BindView(R.id.weather_spd)
    TextView mSpdTextView;
    @BindView(R.id.weather_pcpn)
    TextView mPcpnTextView;
    @BindView(R.id.weather_fl)
    TextView mFlTextView;
    @BindView(R.id.weather_dir)
    TextView mDirTextView;

    private WeatherDataBean mWeatherDataBean;
    private WeatherDataBean.NowBean mNowBean;
    private WeatherDataBean.NowBean.CondBean mCondBean;
    private WeatherDataBean.BasicBean mBasicBean;
    private WeatherActivity weatherActivity;
    private int sign = 0;
    private int state;

    @Override
    protected int getLayoutId() {

        return R.layout.fragment_weather_detail;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this,view);
        initMovingPictureView(view);
    }



    @Override
    protected void getData() {
    }

    private void initWeatherData(WeatherDataBean weatherDataBean){
        mNowBean = weatherDataBean.getNow();
        mCondBean = weatherDataBean.getNow().getCond();
        mBasicBean = weatherDataBean.getBasic();

        String data = mBasicBean.getUpdate().getLoc().substring(0,11);

        mTmpTextView.setText(mNowBean.getTmp() + "°");
        String code = mNowBean.getCond().getCode();
        String condIcon = "http://files.heweather.com/cond_icon/" + code + ".png";
        setBackgrouand(code);
        mTxtIconSimpleDraweeView.setColorFilter(Color.WHITE);
        mTxtIconSimpleDraweeView.setImageURI(Uri.parse(condIcon));

        mTxtTextView.setText(mCondBean.getTxt());
        mDateTextView.setText(data);
        mHumTextView.setText("相对湿度" + mNowBean.getHum()+"%");
        mPresTextView.setText("气压" + mNowBean.getPres());
        mScTextView.setText("风力" + mNowBean.getWind().getSc() + "级");
        mSpdTextView.setText("风速" + mNowBean.getWind().getSpd());
        mPcpnTextView.setText("降水量" + mNowBean.getPcpn());
        mFlTextView.setText("体感温度"+ mNowBean.getFl()+"°");
        mDirTextView.setText(mNowBean.getWind().getDir());


    }

    public void setWeatherData(WeatherDataBean weatherDataBean,int state){
        mWeatherDataBean = weatherDataBean;
        initWeatherData(mWeatherDataBean);
        this.state = state;
        Log.d("xxx","xxx");
    }


    //设置天气详情背景图片
    public void setBackgrouand(String code){
        forecastHandler = new ForecastHandler();


        if(code.equals("100")){
            nowindex = 10;      //晴
        }else if(code.equals("101")||code.equals("102")||code.equals("103")){
            nowindex = 11;      //多云
        } else if(code.equals("104")){
            nowindex = 12;       //阴天
        } else if(false){
            nowindex = 13;      //晚上阴天
        }else if(code.equals("500")||code.equals("501")){
            nowindex = 14;      //雾
        }else if(code.equals("502")){
            nowindex = 15;      //霾
        }else if(code.equals("400")||code.equals("401")||code.equals("402")||code.equals("402")){
            nowindex = 17;      //雪
        }else if(code.equals("404")||code.equals("405")||code.equals("406")||code.equals("407")){
            nowindex = 18;      //雨雪
        }else if(code.equals("302")||code.equals("303") ||code.equals("304")){
            nowindex = 19;      //雷
        }else if(false){
            nowindex = 20;      //晴天
        }else if(code.equals("300")||code.equals("301")||code.equals("305")||code.equals("306")||code.equals("307")
                ||code.equals("308")||code.equals("309")||code.equals("310")||code.equals("311")
                ||code.equals("312")||code.equals("313")){
            nowindex = 21;      //雨
        }else {
                nowindex = 16;
        }

        Message msg = new Message();
        Bundle b = new Bundle();
        msg.what = nowindex;
        msg.setData(b);
        forecastHandler.sendMessage(msg);

    }

    private void initMovingPictureView(View view){
        MovingPictureView.isRuning = true;
        weatherActivity = (WeatherActivity) getActivity();

        w1_move1 = new MovingPictureView(getContext(), R.drawable.yjjc_h_a3,-300,10,40);
        w1_move2 = new MovingPictureView(getContext(), R.drawable.yjjc_h_a3,250,10,40);
        w1_move3 = new MovingPictureView(getContext(), R.drawable.yjjc_h_a4,480,40,40);
        weather_qing.removeAllViews();
        weather_qing.addView(w1_move1);
        weather_qing.addView(w1_move2);
        weather_qing.addView(w1_move3);

        w3_move1 = new MovingPictureView(getContext(), R.drawable.yjjc_h_d2,-250,0,30);
        w3_move2 = new MovingPictureView(getContext(), R.drawable.yjjc_h_d3,180,60,40);
        weather_day_yin.addView(w3_move1);
        weather_day_yin.addView(w3_move2);

        weather_move1 = (RelativeLayout)view.findViewById(R.id.weather_move1);
        weather_move2 = (RelativeLayout)view.findViewById(R.id.weather_move2);
        weather_move3 = (RelativeLayout)view.findViewById(R.id.weather_move3);
        weather_move4 = (RelativeLayout)view.findViewById(R.id.weather_move4);
        weather_move5 = (RelativeLayout)view.findViewById(R.id.weather_move5);
        weather_move6 = (RelativeLayout)view.findViewById(R.id.weather_move6);
        weather_move7 = (RelativeLayout)view.findViewById(R.id.weather_move7);
        weather_move8 = (RelativeLayout)view.findViewById(R.id.weather_move8);
        weather_move9 = (RelativeLayout)view.findViewById(R.id.weather_move9);
        weather_move10 = (RelativeLayout)view.findViewById(R.id.weather_move10);
        m1 = (ImageView)view.findViewById(R.id.m1);
        m2 = (ImageView)view.findViewById(R.id.m2);
        m3 = (ImageView)view.findViewById(R.id.m3);
        m4 = (ImageView)view.findViewById(R.id.m4);
        m5 = (ImageView)view.findViewById(R.id.m5);
        m6 = (ImageView)view.findViewById(R.id.m6);
        m7 = (ImageView)view.findViewById(R.id.m7);
        m8 = (ImageView)view.findViewById(R.id.m8);
        m9 = (ImageView)view.findViewById(R.id.m9);
        m10 = (ImageView)view.findViewById(R.id.m10);

        day_lei_handler = new Day_Lei_Handler(getActivity());
        night_qing_handler = new Night_Qing_Handler(getActivity());
        Day_Rain_Handler = new Day_Rain_Handler(getActivity());
        Day_Snow_Handler = new Day_Snow_Handler(getActivity());
        Day_RainSnow_Handler = new Day_RainSnow_Handler(getActivity());
        Day_Wu_Handler = new Day_Wu_Handler(getActivity());

    }

    private void setbbb(){
        if (sign == 0 ){
            weatherActivity.setBackground(R.mipmap.weather_background);
        }

    }

    public void setSign(int sign){
        this.sign = sign;
    }

    class ForecastHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 10:
                    day_qing();
                    return;
                case 11:
                    day_duoyun();
                    return;
                case 12:
                    day_yin();
                    return;
                case 13:
                    night_yin();
                    return;
                case 14:
                    day_wu();
                    return;
                case 15:
                    day_mai();
                    return;
                case 16:
                    day_sha();
                    return;
                case 17:
                    day_snow();
                    return;
                case 18:
                    day_rainsnow();
                    return;
                case 19:
                    day_lei();
                    return;
                case 20:
                    night_qing();
                    return;
                case 21:
                    day_rain();
                    return;
                default:
                    break;
            }
        }
    }

    public void day_qing(){
        wordBlack();
        showweather("day_qing");
        weatherActivity.setBackground(R.drawable.yjjc_h_a1);
        weatherActivity.setBackgroundColor(0x21000000);
//        today_yubao.setBackgroundResource(R.drawable.yjjc_h_a1);
        if(!w1_move1.isstarted){
            new Thread(w1_move1).start();
            new Thread(w1_move2).start();
            new Thread(w1_move3).start();
        }
        setbbb();
    }

    public void day_duoyun(){
        wordBlack();
        showweather("day_duoyun");
        weatherActivity.setBackground(R.drawable.yjjc_h_c1);
//        today_yubao.setBackgroundResource(R.drawable.yjjc_h_c1);
        if(!w1_move1.isstarted){
            new Thread(w1_move1).start();
            new Thread(w1_move2).start();
            new Thread(w1_move3).start();
        }
        setbbb();
    }

    public void day_yin(){
        wordWhite();
        showweather("day_yin");
        weatherActivity.setBackground(R.drawable.yjjc_h_d1);
//        today_yubao.setBackgroundResource(R.drawable.yjjc_h_d1);
        if(!w3_move1.isstarted){
            new Thread(w3_move1).start();
            new Thread(w3_move2).start();
        }
        setbbb();
    }

    public void night_yin(){
        wordWhite();
        showweather("night_yin");
        weatherActivity.setBackground(R.drawable.yjjc_h_l1);
//        today_yubao.setBackgroundResource(R.drawable.yjjc_h_l1);
        setbbb();
    }

    public void day_wu(){
        wordBlack();
        showweather("day_wu");
        weatherActivity.setBackground(R.drawable.yjjc_h_i1);
//        today_yubao.setBackgroundResource(R.drawable.yjjc_h_i1);
        setbbb();
    }

    public void day_mai(){
        wordBlack();
        showweather("day_mai");
        weatherActivity.setBackground(R.drawable.yjjc_h_j1);
//        today_yubao.setBackgroundResource(R.drawable.yjjc_h_j1);
        setbbb();
    }

    public void day_sha(){
        wordBlack();
        showweather("day_sha");
        weatherActivity.setBackground(R.drawable.yjjc_h_k1);
//        today_yubao.setBackgroundResource(R.drawable.yjjc_h_k1);
        setbbb();
    }

    public void day_snow(){
        wordBlack();
        showweather("other");
        weatherActivity.setBackground(R.drawable.yjjc_h_g1);
//        today_yubao.setBackgroundResource(R.drawable.yjjc_h_g1);
        m1.setImageResource(R.drawable.yjjc_h_g2);
        m2.setImageResource(R.drawable.yjjc_h_g3);
        m3.setImageResource(R.drawable.yjjc_h_g4);
        m4.setImageResource(R.drawable.yjjc_h_g5);
        Day_Snow_Timer chage = new Day_Snow_Timer();
        Thread chageimg = new Thread(chage);
        chageimg.start();
        setbbb();
    }

    public void day_rainsnow(){
        wordWhite();
        showweather("other");
        weatherActivity.setBackground(R.drawable.yjjc_h_h1);
//        today_yubao.setBackgroundResource(R.drawable.yjjc_h_h1);
        m1.setImageResource(R.drawable.yjjc_h_h2);
        m2.setImageResource(R.drawable.yjjc_h_h3);
        m3.setImageResource(R.drawable.yjjc_h_h4);
        Day_RainSnow_Timer chage = new Day_RainSnow_Timer();
        Thread chageimg = new Thread(chage);
        chageimg.start();
        setbbb();
    }


    public void day_lei(){
        wordWhite();
        showweather("other");
        weatherActivity.setBackground(R.drawable.yjjc_h_h1);
//        today_yubao.setBackgroundResource(R.drawable.yjjc_h_f1);
        m1.setImageResource(R.drawable.yjjc_h_f2);
        m2.setImageResource(R.drawable.yjjc_h_f3);
        m3.setImageResource(R.drawable.yjjc_h_f4);
        m4.setImageResource(R.drawable.yjjc_h_f5);
        m5.setImageResource(R.drawable.yjjc_h_f6);
        m6.setImageResource(R.drawable.yjjc_h_f7);
        m7.setImageResource(R.drawable.yjjc_h_f8);
        Day_Lei_Timer chage = new Day_Lei_Timer();
        Thread chageimg = new Thread(chage);
        chageimg.start();
        setbbb();
    }

    public void night_qing(){
        wordWhite();
        showweather("other");
        weatherActivity.setBackground(R.drawable.yjjc_h_b1);
//        today_yubao.setBackgroundResource(R.drawable.yjjc_h_b1);
        m1.setImageResource(R.drawable.yjjc_h_b2);
        m2.setImageResource(R.drawable.yjjc_h_b3);
        m3.setImageResource(R.drawable.yjjc_h_b4);
        m4.setImageResource(R.drawable.yjjc_h_b5);
        m5.setImageResource(R.drawable.yjjc_h_b6);
        Night_Qing_Timer chage = new Night_Qing_Timer();
        Thread chageimg = new Thread(chage);
        chageimg.start();
        setbbb();
    }

    public void day_rain(){
        wordWhite();
        showweather("other");
        weatherActivity.setBackground(R.drawable.yjjc_h_e1);
//        today_yubao.setBackgroundResource(R.drawable.yjjc_h_e1);
        m1.setImageResource(R.drawable.yjjc_h_e2);
        m2.setImageResource(R.drawable.yjjc_h_e3);
        m3.setImageResource(R.drawable.yjjc_h_e4);
        m4.setImageResource(R.drawable.yjjc_h_e5);
        Day_Rain_Timer chage = new Day_Rain_Timer();
        Thread chageimg = new Thread(chage);
        chageimg.start();
        setbbb();
    }

    class Day_Rain_Timer implements Runnable{
        @Override
        public void run(){
            if(WeatherDetailFragment.this.weather_timer != null){
                WeatherDetailFragment.this.weather_timer.cancel();
            }
            WeatherDetailFragment.this.weather_timer = new Timer();
            TimerTask t = new TimerTask() {
                @Override
                public void run() {
                    if(WeatherDetailFragment.imgIndex > 3){
                        WeatherDetailFragment.imgIndex = 0;
                    }
                    Message msg = new Message();
                    Bundle b = new Bundle();
                    b.putString("index", String.valueOf(WeatherDetailFragment.imgIndex));
                    msg.setData(b);
                    WeatherDetailFragment.imgIndex += 1;
                    WeatherDetailFragment.this.Day_Rain_Handler.sendMessage(msg);
                }
            };
            WeatherDetailFragment.this.weather_timer.schedule(t, 0, 300);
        }
    }

    class Night_Qing_Timer implements Runnable{
        @Override
        public void run(){
            if(WeatherDetailFragment.this.weather_timer != null){
                WeatherDetailFragment.this.weather_timer.cancel();
            }
            WeatherDetailFragment.this.weather_timer = new Timer();
            TimerTask t = new TimerTask() {
                @Override
                public void run() {
                    if(WeatherDetailFragment.imgIndex > 4){
                        WeatherDetailFragment.imgIndex = 0;
                    }
                    Message msg = new Message();
                    Bundle b = new Bundle();
                    b.putString("index", String.valueOf(WeatherDetailFragment.imgIndex));
                    msg.setData(b);
                    WeatherDetailFragment.imgIndex += 1;
                    WeatherDetailFragment.this.night_qing_handler.sendMessage(msg);
                }
            };
            WeatherDetailFragment.this.weather_timer.schedule(t, 0, 1*500);
        }
    }
    //雾
    class Day_Wu_Timer implements Runnable{
        @Override
        public void run(){
            if(WeatherDetailFragment.this.weather_timer != null){
                WeatherDetailFragment.this.weather_timer.cancel();
            }
            WeatherDetailFragment.this.weather_timer = new Timer();
            TimerTask t = new TimerTask() {
                @Override
                public void run() {
                    if(WeatherDetailFragment.imgIndex > 4){
                        WeatherDetailFragment.imgIndex = 0;
                    }
                    Message msg = new Message();
                    Bundle b = new Bundle();
                    b.putString("index", String.valueOf(WeatherDetailFragment.imgIndex));
                    msg.setData(b);
                    WeatherDetailFragment.imgIndex += 1;
                    WeatherDetailFragment.this.Day_Wu_Handler.sendMessage(msg);
                }
            };
            WeatherDetailFragment.this.weather_timer.schedule(t, 0, 1*500);
        }
    }

    //雷雨天气
    class Day_Lei_Timer implements Runnable{
        @Override
        public void run(){
            if(WeatherDetailFragment.this.weather_timer != null){
                WeatherDetailFragment.this.weather_timer.cancel();
            }
            WeatherDetailFragment.this.weather_timer = new Timer();
            TimerTask t = new TimerTask() {
                @Override
                public void run() {
                    if(WeatherDetailFragment.imgIndex > 15){
                        WeatherDetailFragment.imgIndex = 0;
                    }
                    Message msg = new Message();
                    Bundle b = new Bundle();
                    b.putString("index", String.valueOf(WeatherDetailFragment.imgIndex));
                    msg.setData(b);
                    WeatherDetailFragment.imgIndex += 1;
                    WeatherDetailFragment.this.day_lei_handler.sendMessage(msg);
                }
            };
            WeatherDetailFragment.this.weather_timer.schedule(t, 0, 1*200);
        }
    }

    //雪
    class Day_Snow_Timer implements Runnable{
        @Override
        public void run(){
            if(WeatherDetailFragment.this.weather_timer != null){
                WeatherDetailFragment.this.weather_timer.cancel();
            }
            WeatherDetailFragment.this.weather_timer = new Timer();
            TimerTask t = new TimerTask() {
                @Override
                public void run() {
                    if(WeatherDetailFragment.imgIndex > 3){
                        WeatherDetailFragment.imgIndex = 0;
                    }
                    Message msg = new Message();
                    Bundle b = new Bundle();
                    b.putString("index", String.valueOf(WeatherDetailFragment.imgIndex));
                    msg.setData(b);
                    WeatherDetailFragment.imgIndex += 1;
                    WeatherDetailFragment.this.Day_Snow_Handler.sendMessage(msg);
                }
            };
            WeatherDetailFragment.this.weather_timer.schedule(t, 0, 300);
        }
    }
    //雨雪
    class Day_RainSnow_Timer implements Runnable{
        @Override
        public void run(){
            if(WeatherDetailFragment.this.weather_timer != null){
                WeatherDetailFragment.this.weather_timer.cancel();
                System.gc();
            }
            WeatherDetailFragment.this.weather_timer = new Timer();
            TimerTask t = new TimerTask() {
                @Override
                public void run() {
                    if(WeatherDetailFragment.imgIndex > 2){
                        WeatherDetailFragment.imgIndex = 0;
                    }
                    Message msg = new Message();
                    Bundle b = new Bundle();
                    b.putString("index", String.valueOf(WeatherDetailFragment.imgIndex));
                    msg.setData(b);
                    WeatherDetailFragment.imgIndex += 1;
                    WeatherDetailFragment.this.Day_RainSnow_Handler.sendMessage(msg);
                }
            };
            WeatherDetailFragment.this.weather_timer.schedule(t, 0, 300);
        }
    }
    //
    class Day_Snow_Handler extends Handler{
        private Activity context;
        public Day_Snow_Handler() {
        }
        public Day_Snow_Handler(Activity context) {
            this.context = context;
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int msgindex = 20;
            Bundle b = msg.getData();
            if(b.getString("index")!=null){
                msgindex = Integer.parseInt(b.getString("index"));
            }
            if(msgindex == 0){
                weather_move4.setVisibility(View.INVISIBLE);
                weather_move1.setVisibility(View.VISIBLE);
            }else if(msgindex == 1){
                weather_move1.setVisibility(View.INVISIBLE);
                weather_move2.setVisibility(View.VISIBLE);
            }else if(msgindex == 2){
                weather_move2.setVisibility(View.INVISIBLE);
                weather_move3.setVisibility(View.VISIBLE);
            }else if(msgindex == 3){
                weather_move3.setVisibility(View.INVISIBLE);
                weather_move4.setVisibility(View.VISIBLE);
            }else{// if(msgindex == 4){
                weather_move4.setVisibility(View.INVISIBLE);
                weather_move1.setVisibility(View.VISIBLE);
            }
        }
    }

    class Day_Wu_Handler extends Handler{
        private Activity context;
        public Day_Wu_Handler() {
        }
        public Day_Wu_Handler(Activity context) {
            this.context = context;
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int msgindex = 20;
            Bundle b = msg.getData();
            if(b.getString("index")!=null){
                msgindex = Integer.parseInt(b.getString("index"));
            }
            if(msgindex == 0){
                weather_move5.setVisibility(View.INVISIBLE);
                weather_move1.setVisibility(View.VISIBLE);
            }else if(msgindex == 1){
                weather_move1.setVisibility(View.INVISIBLE);
                weather_move2.setVisibility(View.VISIBLE);
            }else if(msgindex == 2){
                weather_move2.setVisibility(View.INVISIBLE);
                weather_move3.setVisibility(View.VISIBLE);
            }else if(msgindex == 3){
                weather_move3.setVisibility(View.INVISIBLE);
                weather_move4.setVisibility(View.VISIBLE);
            }else if(msgindex == 4){
                weather_move4.setVisibility(View.INVISIBLE);
                weather_move5.setVisibility(View.VISIBLE);
            }else{

            }
        }
    }

    class Day_RainSnow_Handler extends Handler{
        private Activity context;
        public Day_RainSnow_Handler() {
        }
        public Day_RainSnow_Handler(Activity context) {
            this.context = context;
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int msgindex = 20;
            Bundle b = msg.getData();
            if(b.getString("index")!=null){
                msgindex = Integer.parseInt(b.getString("index"));
            }
            if(msgindex == 0){
                weather_move3.setVisibility(View.INVISIBLE);
                weather_move1.setVisibility(View.VISIBLE);
            }else if(msgindex == 1){
                weather_move1.setVisibility(View.INVISIBLE);
                weather_move2.setVisibility(View.VISIBLE);
            }else if(msgindex == 2){
                weather_move2.setVisibility(View.INVISIBLE);
                weather_move3.setVisibility(View.VISIBLE);
            }else{

            }
        }
    }

    class Day_Rain_Handler extends Handler{
        private Activity context;
        public Day_Rain_Handler() {
        }
        public Day_Rain_Handler(Activity context) {
            this.context = context;
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int msgindex = 20;
            Bundle b = msg.getData();
            if(b.getString("index")!=null){
                msgindex = Integer.parseInt(b.getString("index"));
            }
            if(msgindex == 0){
                weather_move4.setVisibility(View.INVISIBLE);
                weather_move1.setVisibility(View.VISIBLE);
            }else if(msgindex == 1){
                weather_move1.setVisibility(View.INVISIBLE);
                weather_move2.setVisibility(View.VISIBLE);
            }else if(msgindex == 2){
                weather_move2.setVisibility(View.INVISIBLE);
                weather_move3.setVisibility(View.VISIBLE);
            }else if(msgindex == 3){
                weather_move3.setVisibility(View.INVISIBLE);
                weather_move4.setVisibility(View.VISIBLE);
            }else{// if(msgindex == 4){
                weather_move4.setVisibility(View.INVISIBLE);
                weather_move1.setVisibility(View.VISIBLE);
            }
        }
    }

    class Night_Qing_Handler extends Handler{
        private Activity context;
        public Night_Qing_Handler() {
        }
        public Night_Qing_Handler(Activity context) {
            this.context = context;
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int msgindex = 20;
            Bundle b = msg.getData();
            if(b.getString("index")!=null){
                msgindex = Integer.parseInt(b.getString("index"));
            }
            if(msgindex == 0){
                weather_move5.setVisibility(View.INVISIBLE);
                weather_move1.setVisibility(View.VISIBLE);
            }else if(msgindex == 1){
                weather_move1.setVisibility(View.INVISIBLE);
                weather_move2.setVisibility(View.VISIBLE);
            }else if(msgindex == 2){
                weather_move2.setVisibility(View.INVISIBLE);
                weather_move3.setVisibility(View.VISIBLE);
            }else if(msgindex == 3){
                weather_move3.setVisibility(View.INVISIBLE);
                weather_move4.setVisibility(View.VISIBLE);
            }else if(msgindex == 4){
                weather_move4.setVisibility(View.INVISIBLE);
                weather_move5.setVisibility(View.VISIBLE);
            }else{
                weather_move5.setVisibility(View.INVISIBLE);
                weather_move1.setVisibility(View.VISIBLE);
            }
        }
    }

    class Day_Lei_Handler extends Handler{
        private Activity context;
        public Day_Lei_Handler() {
        }
        public Day_Lei_Handler(Activity context) {
            this.context = context;
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            int msgindex = 20;
            Bundle b = msg.getData();
            if(b.getString("index")!=null){
                msgindex = Integer.parseInt(b.getString("index"));
            }
            if(msgindex == 0){
                weather_move7.setVisibility(View.INVISIBLE);
                weather_move1.setVisibility(View.VISIBLE);
            }else if(msgindex == 1){
                weather_move1.setVisibility(View.INVISIBLE);
                weather_move2.setVisibility(View.VISIBLE);
            }else if(msgindex == 2){
                weather_move2.setVisibility(View.INVISIBLE);
                weather_move3.setVisibility(View.VISIBLE);
            }else if(msgindex == 3){
                weather_move3.setVisibility(View.INVISIBLE);
                weather_move4.setVisibility(View.VISIBLE);
            }else if(msgindex == 4){
                weather_move4.setVisibility(View.INVISIBLE);
                weather_move5.setVisibility(View.VISIBLE);
            }else if(msgindex == 5){
                weather_move5.setVisibility(View.INVISIBLE);
                weather_move6.setVisibility(View.VISIBLE);
            }else if(msgindex == 6){
                weather_move6.setVisibility(View.INVISIBLE);
                weather_move7.setVisibility(View.VISIBLE);
            }else{
                weather_move7.setVisibility(View.INVISIBLE);
            }
        }
    }


    //字体颜色
    public void wordBlack(){
        int color = this.getResources().getColor(R.color.black);

    }
    //字体颜色
    public void wordWhite(){
        int color = this.getResources().getColor(R.color.white);

    }


    public void showweather(String weather){
        initWeatherLayout();
        if(weather.equals("day_qing")){
            weather_qing.setVisibility(View.VISIBLE);
        }else if(weather.equals("day_duoyun")){
            weather_qing.setVisibility(View.VISIBLE);
            weather_day_duoyun.setVisibility(View.VISIBLE);
        }else if(weather.equals("day_yin")){
            weather_day_yin.setVisibility(View.VISIBLE);
        }else if(weather.equals("night_yin")){
            weather_night_yin.setVisibility(View.VISIBLE);
        }else if(weather.equals("day_wu")){
            weather_wu.setVisibility(View.VISIBLE);
        }else if(weather.equals("day_mai")){
            weather_mai.setVisibility(View.VISIBLE);
        }else if(weather.equals("day_sha")){
            weather_sha.setVisibility(View.VISIBLE);
        }else{

        }
    }

    public void initWeatherLayout(){
        if(WeatherDetailFragment.this.weather_timer != null){
            WeatherDetailFragment.this.weather_timer.cancel();
        }
        weather_qing.setVisibility(View.INVISIBLE);
        weather_day_duoyun.setVisibility(View.INVISIBLE);
        weather_day_yin.setVisibility(View.INVISIBLE);
        weather_night_yin.setVisibility(View.INVISIBLE);
        weather_wu.setVisibility(View.INVISIBLE);
        weather_mai.setVisibility(View.INVISIBLE);
        weather_sha.setVisibility(View.INVISIBLE);
        weather_move1.setVisibility(View.INVISIBLE);
        weather_move2.setVisibility(View.INVISIBLE);
        weather_move3.setVisibility(View.INVISIBLE);
        weather_move4.setVisibility(View.INVISIBLE);
        weather_move5.setVisibility(View.INVISIBLE);
        weather_move6.setVisibility(View.INVISIBLE);
        weather_move7.setVisibility(View.INVISIBLE);
        weather_move8.setVisibility(View.INVISIBLE);
        weather_move9.setVisibility(View.INVISIBLE);
        weather_move10.setVisibility(View.INVISIBLE);
    }
}
