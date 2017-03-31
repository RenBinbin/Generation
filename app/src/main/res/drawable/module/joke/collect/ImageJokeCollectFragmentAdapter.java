package com.weiruanit.lifepro.module.joke.collect;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.weiruanit.lifepro.R;
import com.weiruanit.lifepro.sqlbean.JokeImg;

import java.util.List;

/**
 * Created by Administrator on 2016/11/17 0017.
 */

public class ImageJokeCollectFragmentAdapter extends RecyclerView.Adapter<ImageJokeCollectFragmentAdapter.MyViewHolder> {
     private List<JokeImg> jokeImgList;

    public ImageJokeCollectFragmentAdapter(List<JokeImg> jokeImgList) {
        this.jokeImgList = jokeImgList;
    }
    public interface MyItemClickListener {
        public void onItemClick(View view,int postion);
    }
    private MyItemClickListener mListener;

    public void setmListener(MyItemClickListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = View.inflate(parent.getContext(), R.layout.item_collect_joke_image,parent);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collect_joke_image,parent,false);
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collect_joke_image,parent);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.ivJoke.setImageURI(Uri.parse(jokeImgList.get(position).getImg()));
        holder.tvTitle.setText(jokeImgList.get(position).getTitle());
//        2016-11-14 17:32:34.508
        String[] str = jokeImgList.get(position).getTime().split(":");
        String time = str[0];
        String[] str1 = str[1].split(":");
        time = time+":"+str1[0];
        holder.tvData.setText(time);
        holder.itemView.setTag(jokeImgList.get(position));
        holder.llitem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onLongClickListener.onLongClick(holder.getLayoutPosition());
                return false;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(holder.itemView,holder.getLayoutPosition());
            }
        });
    }
    public interface OnLongClickListener{
        void onLongClick(int position);
    }
    private  OnLongClickListener onLongClickListener;

    public void setOnLongClickListener(OnLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }

    @Override
    public int getItemCount() {
        return jokeImgList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView ivJoke;
        TextView tvTitle, tvData;
        LinearLayout llitem;
        public MyViewHolder(final View itemView) {
            super(itemView);
            ivJoke = (SimpleDraweeView) itemView.findViewById(R.id.iv_collect_joke_image);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title_collect_joke_image);
            tvData = (TextView) itemView.findViewById(R.id.tv_data_collect_joke_image);
            llitem  = (LinearLayout) itemView.findViewById(R.id.ll_item_joke_collect_image);
        }
    }
}
