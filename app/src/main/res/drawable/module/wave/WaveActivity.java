package com.weiruanit.lifepro.module.wave;

import android.content.Intent;
import android.graphics.LightingColorFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.weiruanit.lifepro.R;
import com.weiruanit.lifepro.base.BaseActivity;
import com.weiruanit.lifepro.module.joke.collect.ImageJokeCollectDetailsActivity;
import com.weiruanit.lifepro.module.joke.collect.TextJokeCollectDetailsActivity;
import com.weiruanit.lifepro.module.travel.activity.TravelDetailActivity3;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/13 0013.
 */

public class WaveActivity extends BaseActivity {


    @BindView(R.id.wavepic1)
    ImageView wavepic1;
    @BindView(R.id.wavepic2)
    ImageView wavepic2;
    @BindView(R.id.ib_travel)
    ImageButton ibTravel;
    @BindView(R.id.ib_joke)
    ImageButton ibJoke;
    private SensorManager sensorManager;
    private Vibrator vibrator;
    private boolean isStart = false;

    private int searchTag = 0; //0 游记  1 笑话


    private Animation myAnimation;
    private Animation myAnimation2;

    @Override
    protected void setWindow() {
        super.setWindow();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wave;
    }

    @Override
    public void initData() {
        ButterKnife.bind(this);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        ibJoke.setColorFilter(new LightingColorFilter(0x1585e4, 0x1585e4));
        ibTravel.setColorFilter(new LightingColorFilter(0xB7BABE, 0xB7BABE));

    }
    @OnClick({R.id.ib_travel, R.id.ib_joke})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_travel:
                Toast.makeText(this, "摇一则游记", Toast.LENGTH_SHORT).show();
                ibTravel.setColorFilter(new LightingColorFilter(0x1585e4, 0x1585e4));
                ibJoke.setColorFilter(new LightingColorFilter(0xB7BABE, 0xB7BABE));
                searchTag = 1;
                break;
            case R.id.ib_joke:
                Toast.makeText(this, "摇一则笑话", Toast.LENGTH_SHORT).show();
                searchTag = 0;

                ibJoke.setColorFilter(new LightingColorFilter(0x1585e4, 0x1585e4));
                ibTravel.setColorFilter(new LightingColorFilter(0xB7BABE, 0xB7BABE));
                break;
        }
    }

    @Override
    protected void getData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sensorManager != null) {// 注册监听器
            sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
            // 第一个参数是Listener，第二个参数是所得传感器类型，第三个参数值获取传感器信息的频率
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensorManager != null) {// 取消监听器
            sensorManager.unregisterListener(sensorEventListener);
        }
    }


    private SensorEventListener sensorEventListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            // 传感器信息改变时执行该方法
            float[] values = event.values;
            float x = values[0]; // x轴方向的重力加速度，向右为正
            float y = values[1]; // y轴方向的重力加速度，向前为正
            float z = values[2]; // z轴方向的重力加速度，向上为正
            //  Log.i(TAG, "x轴方向的重力加速度" + x + "；y轴方向的重力加速度" + y + "；z轴方向的重力加速度" + z);
            // 一般在这三个方向的重力加速度达到40就达到了摇晃手机的状态。
            int medumValue = 19;// 三星 i9250怎么晃都不会超过20，没办法，只设置19了
            if (Math.abs(x) > medumValue || Math.abs(y) > medumValue || Math.abs(z) > medumValue) {
                vibrator.vibrate(200);

                if (!isStart){
                    yaoyiyao();

                }else {

                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    public void yaoyiyao() {

        isStart = true; //已经摇了
//        private Animation myAnimation;
        myAnimation = AnimationUtils.loadAnimation(this,
                R.anim.translate_anim);
        wavepic1.startAnimation(myAnimation);
        myAnimation2 = AnimationUtils.loadAnimation(this,
                R.anim.translate_anim_down);
        wavepic2.startAnimation(myAnimation2);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (searchTag == 1) {
                    //跳游记详情页，随机展示一则游记
                    Intent intent = new Intent(WaveActivity.this, TravelDetailActivity3.class);
                    startActivity(intent);
                    isStart =false;
                }
                else {
                    //跳笑话详情页，随机展示一则笑话
                    int random = (int) (Math.random()*2);
                    if (random==0){
                        //图
                        Intent intent = new Intent(WaveActivity.this, ImageJokeCollectDetailsActivity.class);

                        startActivity(intent);
                        isStart = false;
                    }
                    else {
                        //文
                        Intent intent = new Intent(WaveActivity.this, TextJokeCollectDetailsActivity.class);

                        startActivity(intent);
                        isStart = false;

                    }
                }

            }
        }, 2000);
    }




}
