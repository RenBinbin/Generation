package com.bishe.renbinbin1.secret_master.module.robot.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2016/11/2 0002.
 */

public abstract  class RobotBaseAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    protected ViewOnClickListener viewOnClickListener;

    public interface ViewOnClickListener{
        public void onClick(int position, View view, int state);
        public void onLongClick(int position, View view, int state);

    }

    public RobotBaseAdapter(ViewOnClickListener viewOnClickListener) {
        this.viewOnClickListener = viewOnClickListener;
    }

    public RobotBaseAdapter() {
    }

    public void setViewOnClickListener(ViewOnClickListener viewOnClickListener) {
        this.viewOnClickListener = viewOnClickListener;
    }
}
