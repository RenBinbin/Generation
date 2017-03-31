package com.weiruanit.lifepro.module.news;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.weiruanit.lifepro.R;
import com.weiruanit.lifepro.base.BaseFragment;
import com.weiruanit.lifepro.module.news.adapter.NewsFragmentPagerAdapter;
import com.weiruanit.lifepro.module.news.bean.NewsTabBean;
import com.weiruanit.lifepro.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import shanyao.tabpagerindictor.TabPageIndicator;

/**
 * Created by Administrator on 2016/11/14 0014.
 */

public class NewsFragment extends BaseFragment {

    @BindView(R.id.news_viewpager)
    ViewPager mViewPager;
    TabPageIndicator indicator;

    private List<NewsTabBean> mNewsTabBean = new ArrayList<>();

    public static NewsFragment newInstance(){
        NewsFragment newsFragment = new NewsFragment();
        return newsFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this,view);
        mNewsTabBean.add(new NewsTabBean("国际新闻",WorldNewsFragment.newInstance()));
        mNewsTabBean.add(new NewsTabBean("国内新闻",HomesNewsFragment.newInstance()));
        NewsFragmentPagerAdapter adapter = new NewsFragmentPagerAdapter(getChildFragmentManager());
        adapter.addFragment(mNewsTabBean);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(adapter.getCount());
        indicator =(TabPageIndicator) view.findViewById(R.id.indicator_News);
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
