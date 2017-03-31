package com.weiruanit.lifepro.module.joke.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.weiruanit.lifepro.R;
import com.weiruanit.lifepro.module.joke.bean.TextJokeBeanResponse;
import com.weiruanit.lifepro.module.joke.bean.TextJokeHeaderBean;
import com.weiruanit.lifepro.module.joke.periscope.FavorLayout;
import com.weiruanit.lifepro.module.travel.bean.TravelResponce;
import com.weiruanit.lifepro.utils.DBUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/11/14 0014.
 */

public class TextJokeFragmentAdapter extends RecyclerView.Adapter<TextJokeFragmentAdapter.MyViewHolder> implements View.OnClickListener {
    private  List<TextJokeBeanResponse.ShowapiResBodyBean.ContentlistBean> mContentlistBeen;
    private ArrayList<TextJokeHeaderBean> headerBeanList;
    private ArrayList<TravelResponce.DataBean.BooksBean> list;
    private Context context;
    private String title;
    public TextJokeFragmentAdapter(List<TextJokeBeanResponse.ShowapiResBodyBean.ContentlistBean> contentlistBeen,ArrayList<TravelResponce.DataBean.BooksBean> list, Context context) {
        this.mContentlistBeen = contentlistBeen;
        this.list = list;
        this.context = context;
    }

    public interface MyItemClickListener {
        public void onItemClick(View view, int postion);

    }
    private MyItemClickListener mListener;

    public void setmListener(MyItemClickListener mListener) {
        this.mListener = mListener;
    }
    public interface MyCollectClickListener{
        public void onCollectClick(View view,int position);
    }

    private  MyCollectClickListener mCollectListener;
    public void setmCollectListener(MyCollectClickListener listener){
        this.mCollectListener = listener;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_joke_text,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.svPic.setImageURI(Uri.parse(list.get(position).getUserHeadImg()));
        holder.tvName.setText(list.get(position).getUserName());
        title = mContentlistBeen.get(position).getTitle();
        holder.tvTitle.setText(title);
        holder.tvContent.setText(Html.fromHtml(mContentlistBeen.get(position).getText()));
        String[] str = mContentlistBeen.get(position).getCt().split(":");
        String time = str[0];
        String[] str1 = str[1].split(":");
        time = time+":"+str1[0];
        holder.tvData.setText(time);
        String pubDate = mContentlistBeen.get(position).getCt();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(pubDate);
            date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long systemTime = System.currentTimeMillis();
        long times = systemTime - date.getTime();
        if (times > 6*60*60*1000){
            holder.llxinxian.setVisibility(View.GONE);
        }
        if (position%5 == 0){
            holder.llhot.setVisibility(View.GONE);
        }
        //  holder.itemView.setAnimation(AnimationUtils.loadAnimation(context,R.anim.alpha_in));
        holder.itemView.setTag(mContentlistBeen.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //                holder.getLayoutPosition()  才是真是Position

                mListener.onItemClick(holder.itemView,holder.getLayoutPosition());
            }
        });
        if (favourites!=null){
            if (favourites.get(position)==0){
                holder.ivCollect.setImageResource(R.mipmap.joke_collection_normal);

            }
            else {
                holder.ivCollect.setImageResource(R.mipmap.collection_selected);
            }
        }
        else {
            holder.ivCollect.setImageResource(R.mipmap.joke_collection_normal);
        }

        holder.ivCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCollectListener.onCollectClick(holder.ivCollect,position);
                if (favourites!=null){
                    if (favourites.get(position).equals(1)){
                        holder.favorLayout.addFavor();
                        holder.favorLayout.addFavor();
                        holder.ivCollect.setImageResource(R.mipmap.collection_selected);
                        DBUtils.SaveTextJoke(mContentlistBeen.get(position));
                        favourites.set(position,1);
                        Toast.makeText(context, "收藏成功", Toast.LENGTH_SHORT).show();
                    }else if (favourites.get(position).equals(0)){
                        holder.ivCollect.setImageResource(R.mipmap.joke_collection_normal);
                        DBUtils.DeleteTextJoke(mContentlistBeen.get(position));
                        favourites.set(position,0);
                        Toast.makeText(context, "取消收藏", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        holder.ivShare.setOnClickListener(this);
        holder.linearLayoutItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemLongClickListener.onItemClick( v,holder.getLayoutPosition());
                return false;
            }
        });
    }

    public interface OnItemLongClickListener{
        void onItemClick(View v,int position);
    }
    private  OnItemLongClickListener onItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }



    private List<Integer> favourites ;
    public void setFavourites(List<Integer> favourites ){
        this.favourites = favourites;

    }
    @Override
    public int getItemCount() {
        return mContentlistBeen.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        FavorLayout favorLayout;
        SimpleDraweeView svPic;
        TextView tvTitle,tvContent,tvData,tvName;
        RelativeLayout linearLayoutItem;
        ImageView ivCollect,ivShare;
        LinearLayout llxinxian,llhot;


        public MyViewHolder(final View itemView) {
            super(itemView);
            svPic = (SimpleDraweeView) itemView.findViewById(R.id.header_text_joke);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title_textjoke);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content_textjoke);
            tvData = (TextView) itemView.findViewById(R.id.tv_data_textjoke);
            tvName = (TextView) itemView.findViewById(R.id.name_text_joke);
            ivCollect = (ImageView) itemView.findViewById(R.id.collect_joke_text);
            ivShare = (ImageView) itemView.findViewById(R.id.share_joke_text);
            linearLayoutItem = (RelativeLayout) itemView.findViewById(R.id.ll_item_textjoke);
            llxinxian = (LinearLayout) itemView.findViewById(R.id.llxinxian_text_joke);
            llhot = (LinearLayout) itemView.findViewById(R.id.llhot_text_joke);
            favorLayout = (FavorLayout) itemView.findViewById(R.id.favor);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.share_joke_text:
                new ShareAction((Activity) context).setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                        .addButton("umeng_sharebutton_custom", "umeng_sharebutton_custom", "info_icon_1", "info_icon_1")
                        .setShareboardclickCallback(new ShareBoardlistener() {
                            @Override
                            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                                if (snsPlatform.mShowWord.equals("umeng_sharebutton_custom")) {
                                    Toast.makeText( context, "自定义按钮", Toast.LENGTH_LONG).show();
                                } else {
                                    new ShareAction((Activity) context).withText(title+"每日博君一笑  --来自LifePro分享")
                                            .setPlatform(share_media)
                                            .setCallback(new UMShareListener() {
                                                @Override
                                                public void onResult(SHARE_MEDIA share_media) {
                                                    Toast.makeText(context, "分享成功", Toast.LENGTH_SHORT).show();
                                                }

                                                @Override
                                                public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                                                    Toast.makeText(context, "分享失败", Toast.LENGTH_SHORT).show();
                                                }

                                                @Override
                                                public void onCancel(SHARE_MEDIA share_media) {
                                                    Toast.makeText(context, "取消分享", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .share();

                                }
                            }
                        }).open();
                break;
            default:
                break;
        }
    }

}
