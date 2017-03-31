package com.weiruanit.lifepro.module.joke;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.weiruanit.lifepro.R;
import com.weiruanit.lifepro.base.BaseActivity;
import com.weiruanit.lifepro.module.joke.adapter.JokeDetailsViewPageAdapter;
import com.weiruanit.lifepro.module.joke.bean.ImageJokeDetailsVpBean;
import com.weiruanit.lifepro.module.joke.bean.JokeBeanResponse;
import com.weiruanit.lifepro.module.joke.fragment.JokeDetailsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JokeDatailsActivity extends BaseActivity {


    @BindView(R.id.vp_joke_details)
    ViewPager vpJokeDetails;
    @BindView(R.id.ivBack_image_joke)
    ImageView ivBackImageJoke;
    private List<ImageJokeDetailsVpBean> imageJokeDetailsVpBeanList = new ArrayList<>();
    private JokeDetailsViewPageAdapter mAdapter;
    private JokeDetailsFragment mFragment;
    private ArrayList<JokeBeanResponse.ShowapiResBodyBean.ContentlistBean> contentlistBeanList;
    private JokeBeanResponse.ShowapiResBodyBean.ContentlistBean contentlistBeen;
    private List<Integer> favourites;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_joke_datails;
    }

    @Override
    public void initData() {
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        int id = getIntent().getExtras().getInt("postion");
//        Log.e("sss", "onCreate: "+id );
        contentlistBeanList = bundle.getParcelableArrayList("list");
        favourites = bundle.getIntegerArrayList("favourites");

        mAdapter = new JokeDetailsViewPageAdapter(getSupportFragmentManager());
        mAdapter.addFragment(imageJokeDetailsVpBeanList);
        for (int i = 0; i < contentlistBeanList.size(); i++) {
            Bundle bundle1 = new Bundle();
            bundle1.putParcelableArrayList("contentlist", contentlistBeanList);
            bundle1.putInt("id", i);
//            bundle1.putString("uri", contentlistBeanList.get(i).getImg());
//            bundle1.putString("title", contentlistBeanList.get(i).getTitle());
            mFragment = new JokeDetailsFragment();
            mFragment.setArguments(bundle1);
            mFragment.setFavourites(favourites);
            ImageJokeDetailsVpBean imageJokeDetailsVpBean = new ImageJokeDetailsVpBean(contentlistBeanList.get(i).getImg(),
                    contentlistBeanList.get(i).getTitle(), mFragment);
            imageJokeDetailsVpBeanList.add(imageJokeDetailsVpBean);
        }
        vpJokeDetails.setAdapter(mAdapter);
        vpJokeDetails.setOffscreenPageLimit(mAdapter.getCount());
        vpJokeDetails.setCurrentItem(id);
    }

    @Override
    protected void getData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        contentlistBeanList.clear();
    }

    @OnClick(R.id.ivBack_image_joke)
    public void onClick() {
        finish();
    }
}
