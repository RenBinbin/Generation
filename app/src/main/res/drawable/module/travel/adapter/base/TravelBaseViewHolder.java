package com.weiruanit.lifepro.module.travel.adapter.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.weiruanit.lifepro.R;
import com.weiruanit.lifepro.module.travel.bean.TravelResponce;

import java.util.List;

/**
 * Created by Administrator on 2016/11/2 0002.
 */

public class TravelBaseViewHolder extends RecyclerView.ViewHolder {



    public TravelBaseViewHolder(View itemView) {
        super(itemView);
    }

    public TravelBaseViewHolder(final View itemView, final TravelBaseAdapter.ViewOnClickListener viewOnClickListener, final List<TravelResponce.DataBean.BooksBean> list) {
        super(itemView);
        RelativeLayout roortBackground = (RelativeLayout) itemView.findViewById(R.id.roort_background);
        final ImageView imLikeCount = (ImageView) itemView.findViewById(R.id.im_likeCount);

        imLikeCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null!=viewOnClickListener) {
//                     int position = getAdapterPosition();
//                    Log.e("position",position+"");
//                    TravelResponce.DataBean.BooksBean booksBean = list.get(position);
////                    Log.e("position",.toString());
                    viewOnClickListener.onFollowStatusChange(imLikeCount,list.get(getAdapterPosition()),getAdapterPosition());
                }
            }
        });
        roortBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null!=viewOnClickListener) {
                    viewOnClickListener.onItemClick(getAdapterPosition(), itemView);
                }
            }
        });
    }
}
