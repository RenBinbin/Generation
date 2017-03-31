package com.bishe.renbinbin1.secret_master.module.joke;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.bishe.renbinbin1.secret_master.R;
import com.bishe.renbinbin1.secret_master.base.BaseFragment;
import com.bishe.renbinbin1.secret_master.module.joke.adapter.GifAdapter;
import com.bishe.renbinbin1.secret_master.module.joke.bean.GifBean;
import com.bishe.renbinbin1.secret_master.module.news.Constants;
import com.bishe.renbinbin1.secret_master.module.news.QClitent;
import com.bishe.renbinbin1.secret_master.module.news.QNewsService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class Giffragment extends BaseFragment {
    @BindView(R.id.rv_gif)
    RecyclerView rvGif;

    private GifAdapter adapter;
    private List<GifBean.ResultBean> gifBeen=new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gif;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);
        adapter=new GifAdapter(getContext(),gifBeen);
        rvGif.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));
        rvGif.setAdapter(adapter);
    }

    private void updateData() {
        QClitent.getInstance()
                .create(QNewsService.class, Constants.BASE_JUHE_URL)
                .getGIFRandomData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GifBean>() {
                    @Override
                    public void accept(GifBean gifBean) throws Exception {
                        List<GifBean.ResultBean> data = gifBean.getResult();
                            adapter = new GifAdapter(getContext(), data);
                            rvGif.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getActivity(), "获取数据失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void getData() {
        updateData();
    }
}
