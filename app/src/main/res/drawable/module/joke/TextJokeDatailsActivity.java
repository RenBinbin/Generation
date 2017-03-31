package com.weiruanit.lifepro.module.joke;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.weiruanit.lifepro.R;
import com.weiruanit.lifepro.base.BaseActivity;
import com.weiruanit.lifepro.module.joke.adapter.JokeDetailsViewPageAdapter;
import com.weiruanit.lifepro.module.joke.bean.ImageJokeDetailsVpBean;
import com.weiruanit.lifepro.module.joke.bean.TextJokeBeanResponse;
import com.weiruanit.lifepro.module.joke.fragment.TextJokeDetailsFragment;
import com.weiruanit.lifepro.module.travel.bean.TravelResponce;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TextJokeDatailsActivity extends BaseActivity {
    @BindView(R.id.ivBack_text_joke)
    ImageView ivBackTextJoke;
    @BindView(R.id.vp_textjoke_details)
    ViewPager vpTextjokeDetails;
    private Fragment mFragment;
    private JokeDetailsViewPageAdapter mAdapter;
    private List<ImageJokeDetailsVpBean> textJokeDetailsVpBeanList = new ArrayList<>();
    private ArrayList<TextJokeBeanResponse.ShowapiResBodyBean.ContentlistBean> contentlistBeanArrayList;
    private ArrayList<TravelResponce.DataBean.BooksBean> list = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_text_joke_datails;
    }

    @Override
    public void initData() {
        ButterKnife.bind(this);
        mAdapter = new JokeDetailsViewPageAdapter(getSupportFragmentManager());
        mAdapter.addFragment(textJokeDetailsVpBeanList);
        Bundle bundle = getIntent().getExtras();
        int id = getIntent().getExtras().getInt("postion");
//        String uri = getIntent().getExtras().getString("uri");
//        String name = getIntent().getExtras().getString("name");
        ArrayList<Integer> isCollect = bundle.getIntegerArrayList("isCollect");
        contentlistBeanArrayList = bundle.getParcelableArrayList("list");
        list = bundle.getParcelableArrayList("headerList");
        for (int i = 0; i < contentlistBeanArrayList.size(); i++) {
            mFragment = new TextJokeDetailsFragment();
            Bundle bundle1 = new Bundle();
            bundle1.putParcelableArrayList("contentlist",contentlistBeanArrayList);
            bundle1.putInt("id",i);
            bundle1.putIntegerArrayList("iscollect",isCollect);
            bundle1.putParcelableArrayList("headerImageList",list);
            bundle1.putString("text", contentlistBeanArrayList.get(i).getText());
            bundle1.putString("title", contentlistBeanArrayList.get(i).getTitle());
//            bundle1.putString("imageUri",uri);
//            bundle1.putString("userName",name);
            mFragment.setArguments(bundle1);
            ImageJokeDetailsVpBean imageJokeDetailsVpBean = new ImageJokeDetailsVpBean(contentlistBeanArrayList.get(i).getText(),
                    contentlistBeanArrayList.get(i).getTitle(), mFragment);
            textJokeDetailsVpBeanList.add(imageJokeDetailsVpBean);
        }

        vpTextjokeDetails.setAdapter(mAdapter);
        vpTextjokeDetails.setOffscreenPageLimit(mAdapter.getCount());
        vpTextjokeDetails.setCurrentItem(id);
    }

    @Override
    protected void getData() {

    }

    private InputMethodManager imm;
    public void hideSoftInput(){
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && imm.isActive()) {        //判断是否已弹出

            //隐藏软键盘
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),
                    0);
        }
    }
    @OnClick(R.id.ivBack_text_joke)
    public void onClick() {
        finish();
        hideSoftInput();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        contentlistBeanArrayList.clear();
    }
}
