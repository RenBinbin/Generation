package com.weiruanit.lifepro.module.joke.collect;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weiruanit.lifepro.R;
import com.weiruanit.lifepro.sqlbean.JokeText;

import java.util.List;

/**
 * Created by Administrator on 2016/11/17 0017.
 */

public class TextJokeCollectFragmentAdapter extends RecyclerView.Adapter<TextJokeCollectFragmentAdapter.MyViewHolder> {
     private List<JokeText> jokeTextList;
    public TextJokeCollectFragmentAdapter(List<JokeText> jokeTextList) {
        this.jokeTextList = jokeTextList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = View.inflate(parent.getContext(), R.layout.item_collect_joke_image,parent);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collect_joke_text,parent,false);
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collect_joke_image,parent);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }
    public interface MyItemClickListener {
        public void onItemClick(View view,int postion);
    }
    private MyItemClickListener mListener;

    public void setmListener(MyItemClickListener mListener) {
        this.mListener = mListener;
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tvTitle.setText(jokeTextList.get(position).getTitle());
        holder.tvContent.setText(Html.fromHtml(jokeTextList.get(position).getText()));
        String[] str = jokeTextList.get(position).getTime().split(":");
        String time = str[0];
        String[] str1 = str[1].split(":");
        time = time+":"+str1[0];
        holder.tvData.setText(time);
        holder.itemView.setTag(jokeTextList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(holder.itemView,holder.getLayoutPosition());
            }
        });
        holder.llItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onLongClickListener.onLongClick(holder.getLayoutPosition());
                return false;
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
        return jokeTextList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle,tvContent, tvData;
        LinearLayout llItem;
        public MyViewHolder(final View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title_collect_textjoke);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content_collect_textjoke);
            tvData = (TextView) itemView.findViewById(R.id.tv_data_collect_textjoke);
            llItem = (LinearLayout) itemView.findViewById(R.id.id_ll_item_collect_text);
        }
    }
}
