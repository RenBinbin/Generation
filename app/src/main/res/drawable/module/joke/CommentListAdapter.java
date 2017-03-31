package com.weiruanit.lifepro.module.joke;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weiruanit.lifepro.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/21 0021.
 */

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.MyViewHolder> {
    ArrayList<String> commentList;

    public CommentListAdapter(ArrayList<String> commentList) {
        this.commentList = commentList;
    }

    @Override
    public CommentListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_textjoke_comment,parent,false);
        CommentListAdapter.MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_content.setText(commentList.get(position));
    }


    @Override
    public int getItemCount() {
        return commentList.size();
    }
//
//    @Override
//    public int getCount() {
//        return commentList.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return commentList.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View view;
//        MyViewHolder myViewHolder = null;
//        if (convertView == null) {
//            view =View.inflate(mContext,R.layout.item_textjoke_comment, null);
//            myViewHolder.tv_commentContent = (TextView) view.findViewById(R.id.tv_commentContent);
//            view.setTag(myViewHolder);
//        } else {
//            view = convertView;
//            myViewHolder = (MyViewHolder) view.getTag();
//        }
//        myViewHolder.tv_commentContent.setText(commentList.get(position));
//        return view;
//    }

    class MyViewHolder  extends RecyclerView.ViewHolder{
        TextView tv_content;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_content= (TextView) itemView.findViewById(R.id.tv_commentContent);
        }
    }
}
