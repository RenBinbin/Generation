package com.weiruanit.lifepro.module.joke;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.weiruanit.lifepro.R;
import com.weiruanit.lifepro.base.BaseFragment;
import com.weiruanit.lifepro.module.joke.bean.JokeBean;
import com.weiruanit.lifepro.module.joke.fragment.ImageJokeFragment;
import com.weiruanit.lifepro.module.joke.fragment.TextJokeFragment;
import com.weiruanit.lifepro.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import shanyao.tabpagerindictor.TabPageIndicator;

/**
 * Created by Administrator on 2016/11/14 0014.
 */

public class JokeFragment extends BaseFragment {
    TabPageIndicator indicator;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_joke;
    }

    @Override
    protected void initView(View view) {
        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.viewPage_joke);
        JokeFragmentViewPageAdapter mAdapter = new JokeFragmentViewPageAdapter(getChildFragmentManager());
        List<JokeBean> mFragment = new ArrayList<>();
        mFragment.add(new JokeBean("图文笑话",new ImageJokeFragment()));
        mFragment.add(new JokeBean("文字笑话",new TextJokeFragment()));
        mAdapter.addFragment(mFragment);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(mAdapter.getCount());
//        TabLayout mTabLayout = (TabLayout) view.findViewById(R.id.tablaout_joke);

        indicator =(TabPageIndicator) view.findViewById(R.id.indicator);
//        mTabLayout.setupWithViewPager(mViewPager);
//        indicator= (TabPageIndicator) view.findViewById(R.id.indicator);
        indicator.setViewPager(mViewPager);
        setTabPagerIndicator();
    }

    @Override
    protected void getData() {

    }

    private void setTabPagerIndicator() {
        indicator.setIndicatorMode(TabPageIndicator.IndicatorMode. MODE_WEIGHT_NOEXPAND_SAME);// 设置模式，一定要先设置模式
        indicator.setDividerColor(Color.parseColor("#969696"));// 设置分割线的颜色
        indicator.setDividerPadding(CommonUtils.px2dip(getActivity(),3));//设置
        indicator.setIndicatorColor(Color.parseColor("#1485E5"));// 设置底部导航线的颜色
        indicator.setTextColorSelected(Color.parseColor("#137DD5"));// 设置tab标题选中的颜色
        indicator.setTextColor(Color.parseColor("#9A9A9A"));// 设置tab标题未被选中的颜色
        indicator.setTextSize(CommonUtils.sp2px(getActivity(),16));// 设置字体大小
    }
}
