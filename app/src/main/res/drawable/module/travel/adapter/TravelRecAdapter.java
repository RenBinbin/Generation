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
import com.weiruanit.lifepro.module.travel.adapter.base.TravelBaseAdapter;
import com.weiruanit.lifepro.module.travel.adapter.base.TravelBaseViewHolder;
import com.weiruanit.lifepro.module.travel.bean.TravelResponce;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.weiruanit.lifepro.R.id.tv_userName;
import static com.weiruanit.lifepro.R.mipmap.collection_normal;

/**
 * Created by Administrator on 2016/11/14 0014.
 */

public class TravelRecAdapter extends TravelBaseAdapter<TravelBaseViewHolder> {

    List<TravelResponce.DataBean.BooksBean> list = new ArrayList<>();
    ViewOnClickListener viewOnClickListener;
    Context context;

    // 步骤一
    private List<Integer> favourites  ;


    //-----------------------------------------------------------------------------------------------
        //解决红心问题的测试

    /**
     *  1 首先用一个list集合存在收藏的bean的下标
     *
     *  2 给控件设置Tag属性对应当前的控件
     *
     *  3 通过当前position与list集合进行比较，决定那个item需要显示收藏
     *
     *  4 对收藏的点击事件进行操作，将收藏以及取消收藏的bean的下标更新到list集合中
     *
     *  5 难点：如何实现第4部的操作！！！(可以尝试Adapter提供一个方法给外界，用来更新list集合)
     *
     *
     */

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
    public TravelRecAdapter(List<TravelResponce.DataBean.BooksBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public TravelBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_travel_item3, parent, false);
        return new TravelViewHolder(view, viewOnClickListener,list);
    }

    @Override
    public void onBindViewHolder(TravelBaseViewHolder holder, final int position) {
        TravelViewHolder viewHolder = (TravelViewHolder) holder;
        viewHolder.imHeade.setImageURI(Uri.parse(list.get(position).getUserHeadImg()));
        viewHolder.imBackground.setImageURI(Uri.parse(list.get(position).getHeadImage()));

        //imLikeCount  复用问题  步骤二、三
//        viewHolder.imLikeCount.setTag(new Integer(position));//设置tag 否则划回来时选中消失
        if (favourites != null&&favourites.size()!=0) {
            if (favourites.get(position)==1){
                viewHolder.imLikeCount.setImageResource( R.mipmap.collection_selected);
            }
            else {
                viewHolder.imLikeCount.setImageResource( R.mipmap.collection_normal);
            }
        } else {
            viewHolder.imLikeCount.setImageResource(collection_normal);
        }



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

    class TravelViewHolder extends TravelBaseViewHolder {

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

        public TravelViewHolder(View itemView, ViewOnClickListener viewOnClickListener,List<TravelResponce.DataBean.BooksBean> list) {
            super(itemView, viewOnClickListener,list);
            ButterKnife.bind(this, itemView);

        }
    }


    //步骤五、提供方法供外界更新

    public void setFavourites(List<Integer> favourites) {
        this.favourites = favourites;
    }

}
