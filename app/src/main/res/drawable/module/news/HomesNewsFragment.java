package com.weiruanit.lifepro.module.news;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.weiruanit.lifepro.R;
import com.weiruanit.lifepro.base.BaseLoadFragment;
import com.weiruanit.lifepro.module.news.adapter.SportsNewsListItemAdapter;
import com.weiruanit.lifepro.module.news.bean.NewsBean;
import com.weiruanit.lifepro.module.news.bean.SportsNewsBean;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/11/14 0014.
 */

public class HomesNewsFragment extends BaseLoadFragment {

    private RecyclerView mRecyclerView;
    private SportsNewsListItemAdapter mNewsListItemAdapter;
    private List<SportsNewsBean.NewslistBean> mNewslistBean = new ArrayList<>();

    private SpringView mSpringView;
    private int mPageIndex = 1;
    private boolean isFresh;

    public static HomesNewsFragment newInstance(){
        HomesNewsFragment homesNewsFragment = new HomesNewsFragment();
        return homesNewsFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_home;
    }

    @Override
    protected void initView(View view) {

        mSpringView = (SpringView)view.findViewById(R.id.news_springview);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.news_recycleview);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private static final int HIDE_THRESHOLD = 20;
            private int mScrolledDistance = 0;
            private boolean mControlsVisible = true;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager())
                        .findFirstVisibleItemPosition();
                if(firstVisibleItem == 0){
                    if (!mControlsVisible) {
                        mControlsVisible = true;
                    }else {
                        if (mScrolledDistance > HIDE_THRESHOLD && mControlsVisible) {
                            mControlsVisible = false;
                            mScrolledDistance = 0;
                        } else if (mScrolledDistance < -HIDE_THRESHOLD && !mControlsVisible) {
                            mControlsVisible = true;
                            mScrolledDistance = 0;
                        }
                    }
                    if ((mControlsVisible && dy > 0) || (!mControlsVisible && dy < 0)) {
                        mScrolledDistance += dy;
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mNewsListItemAdapter = new SportsNewsListItemAdapter(mNewslistBean);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL_LIST));

        mSpringView.setType(SpringView.Type.FOLLOW);
        mSpringView.setHeader(new DefaultHeader(getActivity()));
        mSpringView.setFooter(new DefaultFooter(getActivity()));

        mRecyclerView.setAdapter(mNewsListItemAdapter);
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isFresh){
                    return true;
                }
                else {
                    return false;

                }
            }
        });

        mSpringView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                mPageIndex = 1;
                mNewslistBean.clear();
                mNewsListItemAdapter.notifyDataSetChanged();
                isFresh  = true;
                getData();
            }
            @Override
            public void onLoadmore() {
                isFresh = true;
                getData();
            }
        });


        //设置条目点击事件和长按事件
        mNewsListItemAdapter.setOnItemClickLitener(new SportsNewsListItemAdapter.OnItemClickLitener() {

            /**
             * 点击事件
             * @param view
             * @param position
             */
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), mNewslistBean.get(position).getTitle() , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(),NewsDetailActivity.class);
                intent.putExtra("url",mNewslistBean.get(position).getUrl());
                startActivity(intent);
            }

            /**
             * 长按事件
             * @param view
             * @param position
             */
            @Override
            public void onItemLongClick(View view, int position) {
                Log.d("RecycleView","长按事件");
            }
        });

        setState(StateCode.LODING);
        getNews();
    }

    /**
     * 获取国际新闻数据
     */
    @Override
    protected void getData() {
        getNews();
    }

    private void getNews(){
        OkGo.get("http://apis.baidu.com/txapi/tiyu/tiyu")
                .tag(this)
                .headers("apikey","abcfe469f2ede2b495055162e97d8b82")
                .params("num",10)
                .params("page",mPageIndex)
                .execute(new StringCallback() {

                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try{
                            isFresh = false;
                            mPageIndex++;
                            setState(StateCode.CONTENT);

                            Gson gson = new Gson();
                            NewsBean newsBean = gson.fromJson(s,NewsBean.class);
                            if(newsBean.getNewslist() != null){
                                mNewslistBean.addAll(newsBean.getNewslist());
                                mNewsListItemAdapter.notifyDataSetChanged();
                                mSpringView.onFinishFreshAndLoad();
                            }else {
                                setState(StateCode.LODING);

                                //延迟一下再实现加载失败
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        setState(StateCode.ERROR);
                                    }
                                },3000);
                                mNewsListItemAdapter.notifyDataSetChanged();
                                mSpringView.onFinishFreshAndLoad();
                            }
                        }
                        catch (Exception e){
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
