package com.weiruanit.lifepro.module.travel.adapter.base;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.weiruanit.lifepro.module.travel.bean.TravelResponce;

/**
 * Created by Administrator on 2016/11/2 0002.
 */

public abstract  class TravelBaseAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    public ViewOnClickListener viewOnClickListener;


    public interface ViewOnClickListener{
//        public void onClick(int position, View view);

          //单击整个item跳转到用户界面
        public void onItemClick(int position, View view);
//        //收藏按钮,需要更新按钮的状态
        public void onFollowStatusChange(ImageView view, TravelResponce.DataBean.BooksBean booksBean,int position);

    }

    public TravelBaseAdapter(ViewOnClickListener viewOnClickListener) {
        this.viewOnClickListener = viewOnClickListener;
    }

    public TravelBaseAdapter() {
    }

    public ViewOnClickListener getViewOnClickListener() {
        return viewOnClickListener;
    }

    public void setViewOnClickListener(ViewOnClickListener viewOnClickListener) {
        this.viewOnClickListener = viewOnClickListener;
    }
}
