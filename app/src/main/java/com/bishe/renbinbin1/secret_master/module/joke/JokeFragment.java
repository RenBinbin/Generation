package com.bishe.renbinbin1.secret_master.module.joke;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.bishe.renbinbin1.secret_master.uilts.CommonUtils;
import com.bishe.renbinbin1.secret_master.R;
import com.bishe.renbinbin1.secret_master.base.BaseFragment;
import com.bishe.renbinbin1.secret_master.module.joke.adapter.JokePageFragmentAdapter;
import com.bishe.renbinbin1.secret_master.module.joke.bean.JokeTabBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import shanyao.tabpagerindictor.TabPageIndicator;

public class JokeFragment extends BaseFragment {

    @BindView(R.id.indicator_Joke)
    TabPageIndicator indicatorJoke;
    @BindView(R.id.vp_joke)
    ViewPager vpJoke;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_joke;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);
        List<JokeTabBean> jokeTabBeen = new ArrayList<>();
        jokeTabBeen.add(new JokeTabBean("段子", new TextJokeFragment()));
        jokeTabBeen.add(new JokeTabBean("趣图", new Giffragment()));
        JokePageFragmentAdapter jokePageFragmentAdapter = new JokePageFragmentAdapter(getChildFragmentManager());
        jokePageFragmentAdapter.addFr(jokeTabBeen);
        vpJoke.setAdapter(jokePageFragmentAdapter);
        vpJoke.setOffscreenPageLimit(jokePageFragmentAdapter.getCount());
        indicatorJoke.setViewPager(vpJoke);//绑定indicator
        setTabPagerIndicator();
    }

    private void setTabPagerIndicator() {
        indicatorJoke.setIndicatorMode(TabPageIndicator.IndicatorMode.MODE_WEIGHT_NOEXPAND_SAME);// 设置模式，一定要先设置模式
        indicatorJoke.setDividerColor(Color.parseColor("#000000"));// 设置分割线的颜色
        indicatorJoke.setDividerPadding(CommonUtils.px2dip(getActivity(), 3));//设置
        indicatorJoke.setIndicatorColor(Color.parseColor("#1485E5"));// 设置底部导航线的颜色
        indicatorJoke.setTextColorSelected(Color.parseColor("#137DD5"));// 设置tab标题选中的颜色
        indicatorJoke.setTextColor(Color.parseColor("#000000"));// 设置tab标题未被选中的颜色
        indicatorJoke.setTextSize(CommonUtils.sp2px(getActivity(), 16));// 设置字体大小
    }

    @Override
    protected void getData() {

    }

}
