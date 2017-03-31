package com.weiruanit.lifepro.module.robot.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.weiruanit.lifepro.module.robot.adapter.RobotBaseAdapter;

/**
 * Created by Administrator on 2016/11/1 0001.
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder{

    public BaseViewHolder(final View itemView) {
        super(itemView);
    }


    public BaseViewHolder(final View itemView, final RobotBaseAdapter.ViewOnClickListener
            viewOnClickListener, final int state) {
        super(itemView);

        itemView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {


                if(null!=viewOnClickListener) {
                    viewOnClickListener.onClick(getAdapterPosition(), itemView,state);
                }
                return false;
            }
        });




    }

}
