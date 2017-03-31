package com.weiruanit.lifepro.module.joke.collect;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.weiruanit.lifepro.R;
import com.weiruanit.lifepro.base.BaseLazyFragment;
import com.weiruanit.lifepro.sqlbean.JokeImg;
import com.weiruanit.lifepro.utils.DBUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/17 0017.
 */

public class ImageJokeCollectFragment extends BaseLazyFragment {
    private ImageJokeCollectFragmentAdapter mAdapter;
    private List<JokeImg> jokeImgList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RelativeLayout imShow;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_collect_image_joke;
    }

    @Override
    protected void initView(View view) {
        imShow = (RelativeLayout) view.findViewById(R.id.im_show);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_collect_image_joke);
        mAdapter = new ImageJokeCollectFragmentAdapter(jokeImgList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter.setmListener(new ImageJokeCollectFragmentAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Intent intent = new Intent(getContext(),ImageJokeCollectDetailsActivity.class);
                intent.putExtra("uri",jokeImgList.get(postion).getImg());
                intent.putExtra("title",jokeImgList.get(postion).getTitle());
                startActivity(intent);
            }
        });
        mAdapter.setOnLongClickListener(new ImageJokeCollectFragmentAdapter.OnLongClickListener() {
            @Override
            public void onLongClick(int position) {
                Toast.makeText(getActivity(), "取消收藏", Toast.LENGTH_SHORT).show();
                JokeImg.deleteAll(JokeImg.class,"TITLE = ? ",jokeImgList.get(position).getTitle());
                jokeImgList.remove(position);
                mAdapter.notifyItemRemoved(position);

            }


        });
    }

    @Override
    protected void getData() {
        setState(StateCode.CONTENT);
        List<JokeImg> jokeImgs  = DBUtils.findAllImgJoke();
        jokeImgList.addAll(jokeImgs);
        if (jokeImgList != null && !jokeImgList.isEmpty()) {
            mAdapter.notifyDataSetChanged();
        } else {
            mAdapter.notifyDataSetChanged();        //没数据的时候又更新一次界面是因为：在有多个数据的情况下全部取消收藏始终会保留一个(即使数据库没有数据了),所以就得再更新一次界面
//            Toast.makeText(this, "无数据", Toast.LENGTH_SHORT).show();
            imShow.setVisibility(View.VISIBLE);
        }
//        Log.d("ssss", "getData: ");
//        mAdapter.notifyDataSetChanged();


    }
}
