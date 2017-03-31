package com.weiruanit.lifepro.module.travel.adapter.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.weiruanit.lifepro.R;
import com.weiruanit.lifepro.sqlbean.Book;

import java.util.List;

/**
 * Created by Administrator on 2016/11/2 0002.
 */

public class TravelCollectionBaseViewHolder extends RecyclerView.ViewHolder {



    public TravelCollectionBaseViewHolder(View itemView) {
        super(itemView);
    }

    public TravelCollectionBaseViewHolder(final View itemView, final TravelCollectionBaseAdapter.ViewOnClickListener viewOnClickListener, final List<Book> list) {
        super(itemView);
        RelativeLayout roortBackground = (RelativeLayout) itemView.findViewById(R.id.roort_background);
        final ImageView imLikeCount = (ImageView) itemView.findViewById(R.id.im_likeCount);

        imLikeCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null!=viewOnClickListener) {
                    viewOnClickListener.onFollowStatusChange(imLikeCount,list.get(getAdapterPosition()),getLayoutPosition());
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
