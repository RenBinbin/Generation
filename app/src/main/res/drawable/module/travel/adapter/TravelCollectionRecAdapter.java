package com.weiruanit.lifepro.module.travel.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.weiruanit.lifepro.R;
import com.weiruanit.lifepro.module.travel.adapter.base.TravelCollectionBaseAdapter;
import com.weiruanit.lifepro.module.travel.adapter.base.TravelCollectionBaseViewHolder;
import com.weiruanit.lifepro.sqlbean.Book;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.weiruanit.lifepro.R.id.tv_userName;

/**
 * Created by Administrator on 2016/11/14 0014.
 */

public class TravelCollectionRecAdapter extends TravelCollectionBaseAdapter<TravelCollectionBaseViewHolder> {

    private List<Book> list = new ArrayList<>();

    ViewOnClickListener viewOnClickListener;
    Context context;

    @Override
    public ViewOnClickListener getViewOnClickListener() {
        return viewOnClickListener;
    }

    @Override
    public void setViewOnClickListener(ViewOnClickListener viewOnClickListener) {
        this.viewOnClickListener = viewOnClickListener;
    }


    /**
     * 一定要自己创建一个构造方法，用来接收外界传递进来的数据集合，就像我们使用BaseAdapter那样
     */
    public TravelCollectionRecAdapter(List<Book> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public TravelCollectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_travel_item3, parent, false);
        return new TravelCollectionViewHolder(view, viewOnClickListener,list);
    }

    @Override
    public void onBindViewHolder(TravelCollectionBaseViewHolder holder, int position) {
        TravelCollectionViewHolder viewHolder = (TravelCollectionViewHolder) holder;
        viewHolder.imHeade.setImageURI(Uri.parse(list.get(position).getUserHeadImg()));
        viewHolder.imBackground.setImageURI(Uri.parse(list.get(position).getHeadImage()));
        viewHolder.imLikeCount.setImageResource(R.mipmap.collection_selected);
        viewHolder.tvUserName.setText(list.get(position).getUserName());
        viewHolder.imHeade.setImageURI(Uri.parse(list.get(position).getUserHeadImg()));
        viewHolder.tvUserName.setText(list.get(position).getUserName());
        viewHolder.tvTitle.setText(list.get(position).getTitle());
        viewHolder.tvContent.setText(list.get(position).getText()+"");
        viewHolder.tvLikeCount.setText(list.get(position).getLikeCount() + "");
        viewHolder.tvViewCount.setText(list.get(position).getViewCount() + "");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class TravelCollectionViewHolder extends TravelCollectionBaseViewHolder {

        @BindView(R.id.im_background)
        SimpleDraweeView imBackground;
        @BindView(R.id.im_heade)
        SimpleDraweeView imHeade;
        @BindView(tv_userName)
        TextView tvUserName;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_likeCount)
        TextView tvLikeCount;
        @BindView(R.id.im_likeCount)
        ImageView imLikeCount;
        @BindView(R.id.line)
        View line;
        @BindView(R.id.tv_viewCount)
        TextView tvViewCount;
        @BindView(R.id.im_viewCount)
        ImageView imViewCount;
        @BindView(R.id.roort_background)
        RelativeLayout roortBackground;

        public TravelCollectionViewHolder(View itemView, ViewOnClickListener viewOnClickListener,List<Book> list) {
            super(itemView, viewOnClickListener,list);
            ButterKnife.bind(this, itemView);
        }
    }


}
