package com.weiruanit.lifepro.module.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.weiruanit.lifepro.R;
import com.weiruanit.lifepro.base.BaseFragment;
import com.weiruanit.lifepro.module.joke.collect.JokeCollectActivity;
import com.weiruanit.lifepro.module.robot.ChattingRobotActivity;
import com.weiruanit.lifepro.module.travel.activity.TravelCollectionActivity;
import com.weiruanit.lifepro.module.wave.WaveActivity;
import com.weiruanit.lifepro.sqlbean.JokeText;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/14 0014.
 */

public class Test2Fragment extends BaseFragment {
    @BindView(R.id.btn_robot)
    Button btnRobot;
    @BindView(R.id.btn_wave)
    Button btnWeather;
    @BindView(R.id.btn_Scan)
    Button btnCity;
    @BindView(R.id.btn_test)
    Button btnTest;
    @BindView(R.id.btn_database)
    Button btnDatabase;
    @BindView(R.id.btn_password)
    Button btnPassword;
    @BindView(R.id.btn_joke_collect)
    Button btnJokeCollect;
    @BindView(R.id.btn_travel_collection)
    Button btnTravelCollection;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test2;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);

    }

    @Override
    protected void getData() {

    }


    //扫一扫回调
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //扫描二维码
        if (requestCode == 11) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(getActivity(), "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getActivity(), "解析二维码失败", Toast.LENGTH_LONG).show();

                }
            }
        }

        Intent intent = new Intent(getActivity(), ChattingRobotActivity.class);
        startActivity(intent);
    }


    @OnClick({R.id.btn_robot, R.id.btn_wave, R.id.btn_Scan, R.id.btn_test, R.id.btn_database, R.id.btn_password, R.id.btn_travel_collection,R.id.btn_joke_collect})

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_database:

                JokeText jokeText= new JokeText();
                jokeText.setTitle("这是title");
                jokeText.setTime("10-15-15");
                jokeText.setText("content");
                jokeText.setType("1");
                jokeText.save();




                break;

            case R.id.btn_password:
                //测试手势锁屏
//                Intent intent = new Intent(getActivity(), SetPasWordActivity.class);
//                startActivity(intent);
                break;

            case R.id.btn_robot:
                Intent intent2 = new Intent(getActivity(), ChattingRobotActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_wave:
                //调试摇一摇

                Intent intent1 = new Intent(getActivity(), WaveActivity.class);
                startActivity(intent1);

                break;
            case R.id.btn_Scan:
//                Goods loadGood =Goods.findById(Goods.class,1);
               List<JokeText> jokes =  JokeText.find(JokeText.class,"ID >?","0");
                Toast.makeText(getActivity(), ""+jokes.get(0).toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_test:
                //测试分享功能


                new ShareAction(getActivity()).setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                        .addButton("umeng_sharebutton_custom", "umeng_sharebutton_custom", "info_icon_1", "info_icon_1")
                        .setShareboardclickCallback(new ShareBoardlistener() {
                            @Override
                            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                                if (snsPlatform.mShowWord.equals("umeng_sharebutton_custom")) {
                                    Toast.makeText(getActivity(), "自定义按钮", Toast.LENGTH_LONG).show();
                                } else {
                                    new ShareAction(getActivity()).withText("自定义分享内容xxxxx  --来自LifePro分享")
                                            .setPlatform(share_media)
                                            .setCallback(new UMShareListener() {
                                                @Override
                                                public void onResult(SHARE_MEDIA share_media) {
                                                    Toast.makeText(getActivity(), "分享成功", Toast.LENGTH_SHORT).show();
                                                }

                                                @Override
                                                public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                                                    Toast.makeText(getActivity(), "分享失败", Toast.LENGTH_SHORT).show();
                                                }

                                                @Override
                                                public void onCancel(SHARE_MEDIA share_media) {
                                                    Toast.makeText(getActivity(), "取消分享", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .share();

                                }
                            }
                        }).open();

                break;



            //测试游记收藏
            case R.id.btn_travel_collection:
                Intent intent3 = new Intent(getActivity(), TravelCollectionActivity.class);
                startActivity(intent3);
                break;
            case R.id.btn_joke_collect:
                //笑话收藏测试
                Intent intentCollect = new Intent(getContext(), JokeCollectActivity.class);
                startActivity(intentCollect);
                break;
        }
    }







}
