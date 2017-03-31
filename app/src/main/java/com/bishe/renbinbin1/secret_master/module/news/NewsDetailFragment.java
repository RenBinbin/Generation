package com.bishe.renbinbin1.secret_master.module.news;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bishe.renbinbin1.secret_master.R;
import com.bishe.renbinbin1.secret_master.base.BaseFragment;
import com.bishe.renbinbin1.secret_master.module.news.adapter.NewsDataAdapter;
import com.bishe.renbinbin1.secret_master.module.news.bean.NewsDataBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.xiawei.webviewlib.WebViewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

@SuppressLint("ValidFragment") public class NewsDetailFragment extends BaseFragment {

    @BindView(R.id.rv_new_detail)
    RecyclerView rvNewDetail;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    private NewsDataAdapter mAdapter;

    /**
     * 新闻数据类型
     */
    private String type;

    public NewsDetailFragment() {
    }

    public NewsDetailFragment(String type) {
        this.type = type;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new_detail;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);
        mAdapter = new NewsDataAdapter();
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);//显示效果

        /*************************** 设置下拉刷新 ***************************/
        srl.setColorSchemeColors(Color.RED, Color.RED);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateData();
            }
        });

        /*************************** recyclerView 初始化数据***************************/
        rvNewDetail.setAdapter(mAdapter);
        rvNewDetail.setLayoutManager(new LinearLayoutManager(getActivity()));

        rvNewDetail.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                WebViewActivity.startUrl(getActivity(),
                        ((NewsDataBean.ResultBean.DataBean) adapter.getItem(
                                position)).getUrl());
            }
        });

    }

    @Override
    protected void getData() {
        updateData();
    }

    public void updateData() {
        srl.setRefreshing(true);
        QClitent.getInstance()
                .create(QNewsService.class, Constants.BASE_JUHE_URL)
                .getNewsData(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<NewsDataBean>() {
                    @Override
                    public void accept(NewsDataBean newsDataBean) throws Exception {
                        mAdapter.setNewData(newsDataBean.getResult().getData());
                        srl.setRefreshing(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        srl.setRefreshing(false);

                    }
                });


    }
}
