package com.bishe.renbinbin1.secret_master.widge;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bishe.renbinbin1.secret_master.bean.TabBean;

import java.util.ArrayList;

/**
 * 底部导航View
 * 这里对底部导航的tab 和viewpager 进行联动，起到一个中间人的作用
 */
public class MainBottomTabLayout extends LinearLayout {
    private static final String TAG = MainBottomTabLayout.class.toString();
    ViewPager mViewPager;

    public MainBottomTabLayout(Context context) {
        this(context, null);
    }

    public MainBottomTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainBottomTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化方法，如果有需要，可以在里面进行初始化
     */
    private void init() {
//        viewPager.getAdapter().getCount();
    }
    /**
     * 该方法可以进行ViewPager 【关联】
     * 根据传进来的ArrayList<TabBean>进行底部导航【tab】的创建
     *
     * @param viewPager
     * @param tabBeen
     */
    public void setViewPager(ViewPager viewPager, ArrayList<TabBean> tabBeen) {
        this.mViewPager = viewPager;
        initTabView(tabBeen);

        //监听ViewPager 的滑动
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {


//              如果type有大图则需要处理滑动事件 跳过一个

//                for (int i = 0; i < getChildCount(); i++) {
//                    //循环获取所有的子View
//                    //判断ViewPager 当前选中的Item 是否为循环的i
//
//                    if (position >= conversionIndex) {
//                        for (int j = 0; j < getChildCount(); j++) {
//                            getChildAt(j).setSelected(false);
//                        }
//
//                        getChildAt(position + 1).setSelected(true);
//                    } else {
//                        getChildAt(i).setSelected(position == i ? true : false);
//                    }
//                }

                for (int i = 0; i < getChildCount(); i++) {
                   getChildAt(i).setSelected(position==i?true:false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    //传进来一个对象
    //或者设置一个东西
    class TabClick implements OnClickListener {
        TabBean tabBean;

        public TabClick(TabBean tabBean) {
            this.tabBean = tabBean;
        }

        @Override
        public void onClick(View view) {
            mViewPager.setCurrentItem(tabBean.getIndex());
//            for (int i = 0; i < getChildCount(); i++) {
//
//                //判断点击的View 是否等于拿到的View
//                if (view == getChildAt(i)) {
//                    mViewPager.setCurrentItem(i);
//                    if (myOnClickListener != null) {
//                        myOnClickListener.onSelector(view, i);
//                    }
//                    return;
//                }
//            }
        }
    }

    //给外界设置MyOnClickListener 对象
    public void setMyOnClickListener(MyOnClickListener myOnClickListener) {
        this.myOnClickListener = myOnClickListener;
    }

    public MyOnClickListener myOnClickListener;

    public interface MyOnClickListener {
        void onSelector(View v, int position);
    }

    int conversionIndex;
    BottomTab otherView;
    /**
     * 进行底部导航【tab】的创建
     *
     * @param tabBeen
     */
    public void initTabView(ArrayList<TabBean> tabBeen) {

        int index = 0;
        for (int i = 0; i < tabBeen.size(); i++) {
            if (tabBeen.get(i).getTabType() != 1) {
                tabBeen.get(i).setIndex(index++);
            } else {
                conversionIndex = i;
            }
        }

        for (int i = 0; i < tabBeen.size(); i++) {
            int type = tabBeen.get(i).getTabType();

            //创建【图标+文字】控件
            BottomTab bottomTab = new BottomTab(getContext(), tabBeen.get(i));

            if (tabBeen.get(i).getTabType() == 1) {
                otherView = bottomTab;
//                if(otherClick!=null){
                    bottomTab.setOnClickListener(otherClick);
//                }
            } else {
                //设置底部导航【tab】的点击事件
                bottomTab.setOnClickListener(new TabClick(tabBeen.get(i)));
            }
            //这里需要注意，设置添加到当前控件的参数【layoutParams】
            LayoutParams layoutParams = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.weight = 1;
            layoutParams.gravity = Gravity.CENTER;
            addView(bottomTab, layoutParams);
            //默认选择第一个
            if (i == mViewPager.getCurrentItem()) {
                bottomTab.setSelected(true);
            }
        }
    }

    private OnClickListener otherClick;


    public void setOtherClick(OnClickListener otherClick) {
        this.otherClick = otherClick;
        if(otherClick != null){
            otherView.setOnClickListener(otherClick);
        }
    }
}
