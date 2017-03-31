package com.weiruanit.lifepro.module.joke.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.weiruanit.lifepro.R;
import com.weiruanit.lifepro.base.BaseLoadFragment;
import com.weiruanit.lifepro.module.joke.JokeDatailsActivity;
import com.weiruanit.lifepro.module.joke.adapter.ImageJokeFragmentAdapter;
import com.weiruanit.lifepro.module.joke.bean.ImageJokeControl;
import com.weiruanit.lifepro.module.joke.bean.JokeBeanResponse;
import com.weiruanit.lifepro.sqlbean.JokeImg;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/11/14 0014.
 */

public class ImageJokeFragment extends BaseLoadFragment {

    private ArrayList<JokeBeanResponse.ShowapiResBodyBean.ContentlistBean> mJokeBeanResponseList;
    private RecyclerView mRecyclerView;
    private ImageJokeFragmentAdapter mAdapter;
    private int page = 1;
    private SpringView mSpringView;
    ArrayList<Integer> favourites = new ArrayList<>();


    private boolean isFresh;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_joke_image;
    }

    @Override
    protected void initView(View view) {

        EventBus.getDefault().register(this);
        mJokeBeanResponseList = new ArrayList<>();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_image_joke);
        mSpringView = (SpringView) view.findViewById(R.id.sv_image_joke);
        mAdapter = new ImageJokeFragmentAdapter(mJokeBeanResponseList,getContext());
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
        StaggeredGridLayoutManager.VERTICAL));
//        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mSpringView.setHeader(new DefaultHeader(getActivity()));
        mSpringView.setFooter(new DefaultFooter(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mSpringView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                isFresh = true;
                page = 1;
                if (isFresh){
                    mJokeBeanResponseList.clear();
                    favourites.clear();
                    isFresh = false;
                }
                getImageJoke();

            }
            @Override
            public void onLoadmore() {
                //下拉刷新应该对pageIndex 进行重置
                page++;
                getImageJoke();
            }
        });

        mAdapter.setOnItemLongClickListener(new  ImageJokeFragmentAdapter.OnItemLongClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                mJokeBeanResponseList.remove(position);
                favourites.remove(position);
                mAdapter.notifyItemRemoved(position);
            }
        });
        mAdapter.setmListener(new ImageJokeFragmentAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Intent intent = new Intent(getActivity(), JokeDatailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("list",mJokeBeanResponseList);
                bundle.putIntegerArrayList("favourites",favourites);

//                intent.putExtra("id",mJokeBeanResponseList.get(postion));
                intent.putExtra("postion",postion);
//                Log.e("sss", "onItemClick: "+ mJokeBeanResponseList.get(postion));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        setState(StateCode.LODING);
        getImageJoke();
    }
    @Override
    protected void getData() {
        getImageJoke();
    }
    @Subscribe
    public void getImageJokeControl(ImageJokeControl control){
        favourites.set(control.getPosition(),control.getTag());

    }

    public void getImageJoke() {
        String url ="http://apis.baidu.com/showapi_open_bus/showapi_joke/joke_pic";
        String apikey = "abcfe469f2ede2b495055162e97d8b82";
        OkGo.get(url)
                .tag(getContext())
                .headers("apikey",apikey)
                .params("page", page)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("xxx",s.toString());
                        setState(StateCode.CONTENT);
                        try{
                                Gson gson = new Gson();
                                JokeBeanResponse jokeBeanResponse = gson.fromJson(s,JokeBeanResponse.class);
                                if (jokeBeanResponse.getShowapi_res_body().getContentlist()!=null){
                                    mJokeBeanResponseList.addAll(jokeBeanResponse.getShowapi_res_body().getContentlist());
                                    for (int i = 0; i < jokeBeanResponse.getShowapi_res_body().getContentlist().size(); i++) {
                                        if (JokeImg.find(JokeImg.class,"img = ? ",jokeBeanResponse.getShowapi_res_body().getContentlist().get(i).getImg()).size()==0){
                                            favourites.add(0);

                                        }else {
                                            favourites.add(1);

                                        }
                                    }


                                    mAdapter.notifyDataSetChanged();
                                }
                            }catch (Exception e){
                                e.printStackTrace();

                            }
                    }
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        setState(StateCode.LODING);
                        //延迟一下再实现加载失败

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setState(StateCode.ERROR);
                            }
                        },3000);
                        mSpringView.onFinishFreshAndLoad();
                    }
                });
    }
}
