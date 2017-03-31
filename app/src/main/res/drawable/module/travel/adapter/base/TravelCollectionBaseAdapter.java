package com.weiruanit.lifepro.module.travel.adapter.base;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.weiruanit.lifepro.sqlbean.Book;

/**
 * Created by Administrator on 2016/11/2 0002.
 */

public abstract  class TravelCollectionBaseAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    public ViewOnClickListener viewOnClickListener;


    public interface ViewOnClickListener{
//        public void onClick(int position, View view);

          //单击整个item跳转到用户界面
        public void onItemClick(int position, View view);
//        //收藏按钮,需要更新按钮的状态
        public void onFollowStatusChange(ImageView view, Book booksBean,int position);

    }

    public TravelCollectionBaseAdapter(ViewOnClickListener viewOnClickListener) {
        this.viewOnClickListener = viewOnClickListener;
    }

    public TravelCollectionBaseAdapter() {
    }

    public ViewOnClickListener getViewOnClickListener() {
        return viewOnClickListener;
    }

    public void setViewOnClickListener(ViewOnClickListener viewOnClickListener) {
        this.viewOnClickListener = viewOnClickListener;
    }
}
