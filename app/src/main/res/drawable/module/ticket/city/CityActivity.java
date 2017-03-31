package com.weiruanit.lifepro.module.ticket.city;

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
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.promeg.pinyinhelper.Pinyin;
import com.weiruanit.lifepro.R;
import com.weiruanit.lifepro.module.CityGetUtils;
import com.weiruanit.lifepro.module.ticket.view.ClearEditText;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class CityActivity extends Activity implements MySlideView.onTouchListener, CityAdapter
        .onItemClickListener, View
        .OnClickListener {
    private static final int REQUEST_CODE_CITY = 3;//游记城市请求码
    private List<City> cityList = new ArrayList<>();
    private Set<String> firstPinYin = new LinkedHashSet<>();
    public static List<String> pinyinList = new ArrayList<>();
    private PinyinComparator pinyinComparator;

    private MySlideView mySlideView;
    private CircleTextView circleTxt;
    private RecyclerView recyclerView;
    private TextView tvStickyHeaderView;
    private CityAdapter adapter;
    private LinearLayoutManager layoutManager;
    private ClearEditText etInput;

    private ImageView cityBack;
    private int state;
    private Button btnBJ;
    private Button btnSH;
    private Button btnGZ;
    private Button btnHZ;

    private InputMethodManager imm;//软键盘管理

    private boolean isSearch;
    private List<City> cityDateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        initView();
    }

    //初始化数据
    private void initView() {

        state = getIntent().getIntExtra("state", 0);
        cityBack = (ImageView) findViewById(R.id.iv_city_back);
        etInput = (ClearEditText) findViewById(R.id.et_input);
        cityList.clear();
        firstPinYin.clear();
        pinyinList.clear();

        mySlideView = (MySlideView) findViewById(R.id.my_slide_view);
        circleTxt = (CircleTextView) findViewById(R.id.my_circle_view);
        pinyinComparator = new PinyinComparator();
        tvStickyHeaderView = (TextView) findViewById(R.id.tv_sticky_header_view);

        InputStream inputStream ;
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

        recyclerView = (RecyclerView) findViewById(R.id.rv_sticky_example);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CityAdapter(getApplicationContext(), cityList);
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
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
        cityBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hintKbTwo();
                finish();
//                overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
            }
        });

        addHotCity();//添加热门城市
        //搜索的监听
        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isSearch = true;
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
    //拼音的转换
    public String transformPinYin(String character) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < character.length(); i++) {
            buffer.append(Pinyin.toPinyin(character.charAt(i)));
        }
        return buffer.toString();
    }
    /**
     * 城市的搜索
     *
     * @param cityData
     */
    private void filterSearchCityList(String cityData) {
        cityDateList = new ArrayList<City>();
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
            circleTxt.setVisibility(View.GONE);
        } else {
            circleTxt.setVisibility(View.VISIBLE);
            circleTxt.setText(textView);
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
    //字母排序
    public class PinyinComparator implements Comparator<City> {
        @Override
        public int compare(City cityFirst, City citySecond) {
            return cityFirst.getCityPinyin().compareTo(citySecond.getCityPinyin());
        }
    }

    private void scroll2Position(int index) {
        int firstPosition = layoutManager.findFirstVisibleItemPosition();
        int lastPosition = layoutManager.findLastVisibleItemPosition();
        if (index <= firstPosition) {
            recyclerView.scrollToPosition(index);
        } else if (index <= lastPosition) {
            int top = recyclerView.getChildAt(index - firstPosition).getTop();
            recyclerView.scrollBy(0, top);
        } else {
            recyclerView.scrollToPosition(index);
        }
    }
    /**
     * 选择城市
     * @param position
     */
    @Override
    public void itemClick(int position) {
        Intent intent = new Intent();
        if (isSearch) { //如果是搜索的城市列表
            intent.putExtra("fromcityname", cityDateList.get(position).getCityName());
            if (state == 1) {//起始站的请求
                setResult(11, intent);
            } else if (state == 2) {//终点站的请求
                setResult(22, intent);
            } else if (state == 3) {            //游记界面的请求
                setResult(33, intent);
            }else if (state == REQUEST_CODE_CITY){
                setResult(44, intent);
            }else if(state == 20){         //天气页面的的请求
                setResult(20,intent);
            }
        } else {    //详细的城市列表
            Toast.makeText(this, cityList.get(position).getCityName().toString(), Toast
                    .LENGTH_SHORT).show();
            intent.putExtra("fromcityname", cityList.get(position).getCityName());
            if (state == 1) {//起始站的请求
                setResult(11, intent);
            } else if (state == 2) {//终点站的请求
                setResult(22, intent);
            } else if (state == 3) {            //游记界面的请求
                setResult(33, intent);
            }else if (state == REQUEST_CODE_CITY){     //mainActivity城市请求
                setResult(44, intent);
            }else if(state == 20){               //天气页面的请求
                setResult(20,intent);
            }
        }
        finish();
    }
    //热门城市的点击
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.btn_beijing:
                intent.putExtra("fromcityname", btnBJ.getText().toString());
                setResult(44, intent);
                finish();
                break;
            case R.id.btn_shanghai:
                intent.putExtra("fromcityname", btnSH.getText().toString());
                setResult(45, intent);
                finish();
                break;
            case R.id.btn_gz:
                intent.putExtra("fromcityname", btnGZ.getText().toString());
                setResult(46, intent);
                finish();
                break;
            case R.id.btn_hz:
                intent.putExtra("fromcityname", btnHZ.getText().toString());
                setResult(47, intent);
                finish();
                break;
        }
    }

    /**
     * 热门城市
     */
    private void addHotCity() {
        btnBJ = (Button) findViewById(R.id.btn_beijing);
        btnSH = (Button) findViewById(R.id.btn_shanghai);
        btnGZ = (Button) findViewById(R.id.btn_gz);
        btnHZ = (Button) findViewById(R.id.btn_hz);
        btnBJ.setOnClickListener(this);
        btnSH.setOnClickListener(this);
        btnGZ.setOnClickListener(this);
        btnHZ.setOnClickListener(this);
    }
    /**
     * view的隐藏动画
     */
    public void viewExitAnimation(View view) {
        //透明度从透明到不透明（1----0）
        AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0f);
        //设置动画执行时间
        alphaAnimation.setDuration(600);
        //设置动画结束后是否停留在动画结束状态
        alphaAnimation.setFillAfter(false);
        view.startAnimation(alphaAnimation);

        view.startAnimation(alphaAnimation);
        view.setVisibility(View.GONE);
    }
}
