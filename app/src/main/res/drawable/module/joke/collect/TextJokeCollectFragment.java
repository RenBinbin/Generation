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
import com.weiruanit.lifepro.sqlbean.JokeText;
import com.weiruanit.lifepro.utils.DBUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/17 0017.
 */

public class TextJokeCollectFragment extends BaseLazyFragment {
    private List<JokeText> jokeTextList = new ArrayList<>();
    private TextJokeCollectFragmentAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private RelativeLayout imShow;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_collect_text_joke;
    }

    @Override
    protected void initView(View view) {
        setState(StateCode.CONTENT);

        imShow = (RelativeLayout) view.findViewById(R.id.im_show1);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_collect_text_joke);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mAdapter = new TextJokeCollectFragmentAdapter(jokeTextList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        mAdapter.setmListener(new TextJokeCollectFragmentAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Intent intent = new Intent(getContext(),TextJokeCollectDetailsActivity.class);
                intent.putExtra("title",jokeTextList.get(postion).getTitle());
                intent.putExtra("content",jokeTextList.get(postion).getText());
                startActivity(intent);
            }
        });
        mAdapter.setOnLongClickListener(new TextJokeCollectFragmentAdapter.OnLongClickListener() {
            @Override
            public void onLongClick(int position) {
                JokeText.deleteAll(JokeText.class,"title = ? ",jokeTextList.get(position).getTitle());
                jokeTextList.remove(position);
                mAdapter.notifyItemRemoved(position);
                Toast.makeText(getActivity(), "取消收藏", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void getData() {
        List<JokeText> jokeTexts = DBUtils.findAllTextJoke();
        jokeTextList.addAll(jokeTexts);
      //  setState(StateCode.CONTENT);
        if (jokeTextList != null && !jokeTextList.isEmpty()) {
            mAdapter.notifyDataSetChanged();
        } else {
            mAdapter.notifyDataSetChanged();        //没数据的时候又更新一次界面是因为：在有多个数据的情况下全部取消收藏始终会保留一个(即使数据库没有数据了),所以就得再更新一次界面
//            Toast.makeText(this, "无数据", Toast.LENGTH_SHORT).show();
            imShow.setVisibility(View.VISIBLE);
        }
    }
}
