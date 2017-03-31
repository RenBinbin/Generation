package com.bishe.renbinbin1.secret_master.module.weather.city;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bishe.renbinbin1.secret_master.R;
import com.bishe.renbinbin1.secret_master.module.weather.city.adapter.CityAdapter;
import com.bishe.renbinbin1.secret_master.module.weather.city.bean.City;
import com.bishe.renbinbin1.secret_master.uilts.CityGetUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bishe.renbinbin1.secret_master.uilts.CityGetUtils.transformPinYin;

public class CityActivity extends Activity implements MySlideView.onTouchListener,
        CityAdapter.onItemClickListener{

    @BindView(R.id.iv_city_back)
    ImageView ivCityBack;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.et_input)
    ClearEditText etInput;
    @BindView(R.id.my_slide_view)
    MySlideView mySlideView;
    @BindView(R.id.tv_sticky_header_view)
    TextView tvStickyHeaderView;
    @BindView(R.id.rv_sticky_example)
    RecyclerView rvStickyExample;
    @BindView(R.id.fl_fragment)
    FrameLayout flFragment;
    @BindView(R.id.my_circle_view)
    CircleTextView myCircleView;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.ll)
    LinearLayout ll;

    public static List<String> pinyinList = new ArrayList<>();
    private List<City> cityList = new ArrayList<>();
    private Set<String> firstPinYin = new LinkedHashSet<>();
    private PinyinComparator pinyinComparator;
    private int state;
    private CityAdapter adapter;
    private InputMethodManager imm;//软键盘管理
    private LinearLayoutManager layoutManager;
    private boolean isSearch;
    private List<City> cityDateList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        ButterKnife.bind(this);
        initView();
    }

    //初始化数据
    private void initView() {
        state = getIntent().getIntExtra("state",0);
        cityList.clear();
        firstPinYin.clear();
        pinyinList.clear();
        pinyinComparator = new PinyinComparator();

        InputStream inputStream =null;
        try {
            //获取城市
            inputStream = getAssets().open("city.json");
            String cityInfo = CityGetUtils.getCityInfo(inputStream);
            cityList = CityGetUtils.getCityList(cityInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Collections.sort(cityList, pinyinComparator);
        for (City city : cityList) {
            firstPinYin.add(city.getCityPinyin().substring(0, 1));
        }
        for (String string : firstPinYin) {
            pinyinList.add(string);
        }
        mySlideView.setListener(this);
        layoutManager=new LinearLayoutManager(this);
        rvStickyExample.setLayoutManager(layoutManager);
        adapter=new CityAdapter(getApplicationContext(),cityList);
        adapter.setListener(this);
        rvStickyExample.setAdapter(adapter);
        rvStickyExample.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                View stickyInfoView = recyclerView.findChildViewUnder(
                        tvStickyHeaderView.getMeasuredWidth() / 2, 5);

                if (stickyInfoView != null && stickyInfoView.getContentDescription() != null) {
                    tvStickyHeaderView.setText(String.valueOf(stickyInfoView
                            .getContentDescription()));
                }

                View transInfoView = recyclerView.findChildViewUnder(
                        tvStickyHeaderView.getMeasuredWidth() / 2, tvStickyHeaderView
                                .getMeasuredHeight() + 1);

                if (transInfoView != null && transInfoView.getTag() != null) {
                    int transViewStatus = (int) transInfoView.getTag();
                    int dealtY = transInfoView.getTop() - tvStickyHeaderView.getMeasuredHeight();
                    if (transViewStatus == CityAdapter.HAS_STICKY_VIEW) {
                        if (transInfoView.getTop() > 0) {
                            tvStickyHeaderView.setTranslationY(dealtY);
                        } else {
                            tvStickyHeaderView.setTranslationY(0);
                        }
                    } else if (transViewStatus == CityAdapter.NONE_STICKY_VIEW) {
                        tvStickyHeaderView.setTranslationY(0);
                    }
                }
            }
        });

        rvStickyExample.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                //监听滑动  隐藏软键盘
                hintKbTwo();
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        //返回
        ivCityBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hintKbTwo();
                finish();
            }
        });

        //搜索监听
        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isSearch=true;
                filterSearchCityList(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //输入完后按键盘上的搜索键【回车键改为了搜索键】
        etInput.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == android.view.KeyEvent.KEYCODE_ENTER) {
                    hintKbTwo();
                }
                return false;
            }
        });
    }



    //城市的搜索
    private void filterSearchCityList(String cityData) {
        cityDateList = new ArrayList<>();
        //如果输入框为空，则不变
        if (TextUtils.isEmpty(cityData)) {
            cityDateList = cityList;
        } else {
            cityDateList.clear();
            for (City city : cityList) {
                String name = city.getCityName();
                //拼音、字母的判断搜索
                if (name.indexOf(cityData.toString()) != -1 || transformPinYin(name).toLowerCase
                        ().startsWith(cityData.toString())) {
                    cityDateList.add(city);
                }
            }
        }
        // 根据a-z进行排序
        Collections.sort(cityDateList, pinyinComparator);
        //更新recyclerview
        adapter.updateListView(cityDateList);
    }

    //此方法只是关闭软键盘
    private void hintKbTwo() {
        imm = (InputMethodManager) getSystemService(Context
                .INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Override
    public void showTextView(String textView, boolean dismiss) {
        if (dismiss) {
            myCircleView.setVisibility(View.GONE);
        } else {
            myCircleView.setVisibility(View.VISIBLE);
            myCircleView.setText(textView);
        }

        int selectPosition = 0;
        for (int i = 0; i < cityList.size(); i++) {
            if (cityList.get(i).getFirstPinYin().equals(textView)) {
                selectPosition = i;
                break;
            }
        }
        scroll2Position(selectPosition);
    }

    private void scroll2Position(int index) {
        int firstPosition = layoutManager.findFirstVisibleItemPosition();
        int lastPosition = layoutManager.findLastVisibleItemPosition();
        if (index <= firstPosition) {
            rvStickyExample.scrollToPosition(index);
        } else if (index <= lastPosition) {
            int top = rvStickyExample.getChildAt(index - firstPosition).getTop();
            rvStickyExample.scrollBy(0, top);
        } else {
            rvStickyExample.scrollToPosition(index);
        }
    }

    //字母排序
    public class PinyinComparator implements Comparator<City> {
        @Override
        public int compare(City cityFirst, City citySecond) {
            return cityFirst.getCityPinyin().compareTo(citySecond.getCityPinyin());
        }
    }

    //选择城市
    @Override
    public void itemClick(int position) {
        Intent intent = new Intent();
        if(isSearch){
            intent.putExtra("fromcityname",cityDateList.get(position).getCityName());
            if(state==20){
                setResult(20,intent);
            }
        }else { //详细的城市列表
            intent.putExtra("fromcityname", cityList.get(position).getCityName());
            if(state==20){
                setResult(20,intent);
            }
        }
        finish();
    }

}
