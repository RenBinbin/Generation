package com.weiruanit.lifepro.module.joke.collect;

import android.graphics.Color;
import android.support.v4.view.ViewPager;

import com.weiruanit.lifepro.R;
import com.weiruanit.lifepro.base.BaseBackActivity;
import com.weiruanit.lifepro.module.joke.JokeFragmentViewPageAdapter;
import com.weiruanit.lifepro.module.joke.bean.JokeBean;
import com.weiruanit.lifepro.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import shanyao.tabpagerindictor.TabPageIndicator;

public class JokeCollectActivity extends BaseBackActivity {
    TabPageIndicator indicator;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_joke_collect;
    }
    @Override
    public void initData() {
        ViewPager mViewPager = (ViewPager)findViewById(R.id.viewPage_joke_collect);
        JokeFragmentViewPageAdapter mAdapter = new JokeFragmentViewPageAdapter(getSupportFragmentManager());
        List<JokeBean> mFragment = new ArrayList<>();
        mFragment.add(new JokeBean("图文笑话",new ImageJokeCollectFragment()));
        mFragment.add(new JokeBean("文字笑话",new TextJokeCollectFragment()));
        mAdapter.addFragment(mFragment);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(mAdapter.getCount());
        indicator = (TabPageIndicator)findViewById(R.id.tablaout_joke_collect);
        indicator.setViewPager(mViewPager);
        setTabPagerIndicator();
    }
    @Override
    protected void getData() {

    }
    private void setTabPagerIndicator() {
        indicator.setIndicatorMode(TabPageIndicator.IndicatorMode. MODE_WEIGHT_NOEXPAND_SAME);// 设置模式，一定要先设置模式
        indicator.setDividerColor(Color.parseColor("#969696"));// 设置分割线的颜色
        indicator.setDividerPadding(CommonUtils.px2dip(this,3));//设置
        indicator.setIndicatorColor(Color.parseColor("#1485E5"));// 设置底部导航线的颜色
        indicator.setTextColorSelected(Color.parseColor("#137DD5"));// 设置tab标题选中的颜色
        indicator.setTextColor(Color.parseColor("#9A9A9A"));// 设置tab标题未被选中的颜色
        indicator.setTextSize(CommonUtils.sp2px(this,16));// 设置字体大小
    }
}
