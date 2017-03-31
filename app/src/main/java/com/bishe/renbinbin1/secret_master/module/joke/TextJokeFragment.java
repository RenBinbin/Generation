package com.bishe.renbinbin1.secret_master.module.joke;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bishe.renbinbin1.secret_master.R;
import com.bishe.renbinbin1.secret_master.base.BaseFragment;
import com.bishe.renbinbin1.secret_master.module.joke.adapter.JokeAdapter;
import com.bishe.renbinbin1.secret_master.module.joke.bean.JokeBean;
import com.bishe.renbinbin1.secret_master.module.news.Constants;
import com.bishe.renbinbin1.secret_master.module.news.QClitent;
import com.bishe.renbinbin1.secret_master.module.news.QNewsService;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class TextJokeFragment extends BaseFragment {
    @BindView(R.id.ll_loading)
    LinearLayout llLoading;
    @BindView(R.id.tv_joke_load_again)
    TextView tvJokeLoadAgain;
    @BindView(R.id.ll_error)
    LinearLayout llError;
    @BindView(R.id.rv_joke)
    RecyclerView rvJoke;
    @BindView(R.id.srl_joke)
    SwipeRefreshLayout srlJoke;

    List<JokeBean.ResultBean.DataBean> mData;
    private JokeAdapter mJokeAdapter;
    private int mCurrentCounter;
    private int mTotalCounter = 5;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_joke_text;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);
        //数据初始化
        mData = new ArrayList<>();
        mJokeAdapter = new JokeAdapter();
        mJokeAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);

        //设置下拉刷新
        srlJoke.setColorSchemeColors(Color.RED, Color.GREEN, Color.YELLOW);
        srlJoke.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateDate();
            }
        });
        rvJoke.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvJoke.setAdapter(mJokeAdapter);
        mJokeAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                rvJoke.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mCurrentCounter >= mTotalCounter) {
                            //数据加载完成
                            mJokeAdapter.loadMoreEnd();
                        } else {

                            if (mJokeAdapter.getItem(0) == null) {
                                return;
                            }
                            long unixtime = mJokeAdapter.getItem(mJokeAdapter.getItemCount() - 2)
                                    .getUnixtime();
                            QClitent.getInstance()
                                    .create(QNewsService.class, Constants.BASE_JOKE_URL)//创建服务
                                    .getAssignJokeData(unixtime, 1, 5, QNewsService.DESC)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Consumer<JokeBean>() {
                                        @Override
                                        public void accept(JokeBean jokeBean) throws Exception {
                                            List<JokeBean.ResultBean.DataBean> data =
                                                    jokeBean.getResult().getData();
                                            mJokeAdapter.addData(data);
                                            mCurrentCounter = mTotalCounter;
                                            mTotalCounter += 5;
                                            mJokeAdapter.loadMoreComplete();
                                        }
                                    }, new Consumer<Throwable>() {
                                        @Override
                                        public void accept(Throwable throwable) throws Exception {
                                            mJokeAdapter.loadMoreFail();
                                        }
                                    });
                        }
                    }
                },1000);
            }
        });
        updateDate();
    }

    private void updateDate() {
        srlJoke.setVisibility(View.VISIBLE);
        llLoading.setVisibility(View.VISIBLE);
        llError.setVisibility(View.GONE);
        srlJoke.setRefreshing(true);    // 让SwipeRefreshLayout开启刷新
        QClitent.getInstance().create(QNewsService.class, Constants.BASE_JOKE_URL) // 创建服务
                .getCurrentJokeData(1, 8)   // 查询查询
                .subscribeOn(Schedulers.io())   //  指定被观察者的操作在io线程中完成
                .observeOn(AndroidSchedulers.mainThread())  // 指定观察者接收到数据，然后在Main线程中完成
                .subscribe(new Consumer<JokeBean>() {
                    @Override
                    public void accept(JokeBean jokeBean) throws Exception {
                        // 成功获取数据
                        llLoading.setVisibility(View.GONE);
                        llError.setVisibility(View.GONE);
                        mJokeAdapter.setNewData(jokeBean.getResult().getData());    // 给适配器设置数据
                        srlJoke.setRefreshing(false);       // 让SwipeRefreshLayout关闭刷新
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        // 获取数据失败
                        Toast.makeText(getActivity(), "获取数据失败!" + "访问次数上限", Toast.LENGTH_SHORT)
                                .show();
                        srlJoke.setRefreshing(false);
                        llError.setVisibility(View.VISIBLE);
                        llLoading.setVisibility(View.GONE);
                        srlJoke.setVisibility(View.GONE);
                    }
                });

    }

    @Override
    protected void getData() {
        updateDate();
    }
}
